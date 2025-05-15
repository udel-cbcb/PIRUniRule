package org.proteininformationresource.pirsr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.atteo.xmlcombiner.XmlCombiner;
import org.proteininformationresource.pirsr.interproscan5.model.Hmmer2MatchType;
import org.proteininformationresource.pirsr.interproscan5.model.Hmmer3MatchType;
import org.proteininformationresource.pirsr.interproscan5.model.NucleicAcidMatchesType;
import org.proteininformationresource.pirsr.interproscan5.model.NucleotideSequenceXrefType;
import org.proteininformationresource.pirsr.interproscan5.model.NucleotideType;
import org.proteininformationresource.pirsr.interproscan5.model.OrfType;
import org.proteininformationresource.pirsr.interproscan5.model.ProteinMatchesType;
import org.proteininformationresource.pirsr.interproscan5.model.ProteinType;
import org.proteininformationresource.pirsr.interproscan5.model.ProteinXrefType;
import org.proteininformationresource.pirsr.model.PIRRule;
import org.proteininformationresource.pirsr.model.PIRRuleDataFactory;
import org.proteininformationresource.pirsr.model.PIRRuleManager;
import org.proteininformationresource.pirsr.model.PIRRuleManager.Format;
import org.proteininformationresource.pirsr.prediction.PredictionDataFactory;
import org.proteininformationresource.pirsr.prediction.PredictionManager;
import org.xml.sax.SAXException;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import edu.udel.bioinformatics.pirsr.PIRRuleDataFactoryImpl;
import edu.udel.bioinformatics.pirsr.prediction.PredictionDataFactoryImpl;

public class PIRSitePredictCommander {

	public static void main(String[] args) throws Exception {
		String usage = "java -Xms512M -Xmx2048M -jar PIRSitePredictCommander.jar [options]";
		String header = "Available options:\n";
		// header +=
		// "--------------------------------------------------------------------------------------------\n";

		// Create the command line parser
		CommandLineParser parser = new BasicParser();

		// Create the options
		Options options = createOptions();

		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();

		String commandLineArguments = "Current working directory: " + System.getProperty("user.dir")+"\n"+"Command options: \n";
		PrintWriter writer = new PrintWriter(System.out, true);

		try {
			for (int i = 0; i < args.length; i++) {
				commandLineArguments += args[i] + " ";
			}

			// parse the command line arguments
			CommandLine commandLine = parser.parse(options, args);
			if (commandLine.getOptions().length == 0
					|| commandLine.hasOption("h")) {
				// // formatter.printHelp(120, usage, header, options, "");
				//System.out.println("Welcome to PIRSitePredict");
				formatter.printHelp(writer, 120, usage, header, options, 10,
						10, "");
			} else {

				String task = null;
				String outputDir = new java.io.File(".").getCanonicalPath()
						+ System.getProperty("file.separator") + "outputDir";
				String iprScanXML = null;
				String pirsr = null;
				String logFile = null;
				String hmmsearch = null;
				String hmmalign = null;
				String organism = "Eukaryota";
				String srHMMDir = null;
				String templateDir = null;
				String pirsrDataDir = null;
				String[] formats = "TSV".split("\\s+\\,");
				boolean forceOverwrite = false;

				double eval = 0.0001;

				if (commandLine.hasOption("e")) {
					eval = Double.parseDouble(commandLine.getOptionValue("e"));
				}
				if (commandLine.hasOption("d")) {
					pirsrDataDir = commandLine.getOptionValue("d");
					pirsr = pirsrDataDir + System.getProperty("file.separator")
							//+ "data" + System.getProperty("file.separator")
							+ "PIRSR.uru";
					templateDir = pirsrDataDir
							//+ System.getProperty("file.separator") + "data"
							+ System.getProperty("file.separator") + "sr_tp";
					srHMMDir = pirsrDataDir
							//+ System.getProperty("file.separator") + "data"
							+ System.getProperty("file.separator") + "sr_hmm";
				}

				if (commandLine.hasOption("O")) {
					organism = commandLine.getOptionValue("O");
				}
				if (commandLine.hasOption("F")) {
					forceOverwrite = true;
				}
				if (commandLine.hasOption("S")) {
					hmmsearch = commandLine.getOptionValue("S");
				}
				if (commandLine.hasOption("A")) {
					hmmalign = commandLine.getOptionValue("A");
				}
				// if (commandLine.hasOption("t")) {
				// task = commandLine.getOptionValue("t");
				// }
				if (commandLine.hasOption("o")) {
					outputDir = commandLine.getOptionValue("o");
				}
				if (commandLine.hasOption("i")) {
					iprScanXML = commandLine.getOptionValue("i");
				}

				if (commandLine.hasOption("l")) {
					logFile = commandLine.getOptionValue("l");
				}

				if (commandLine.hasOption("f")) {
					formats = commandLine.getOptionValue("f").trim()
							.toUpperCase().split(",");
					// System.out.println(commandLine.getOptionValue("f"));
				}

				if (logFile != null) {
					writer = new PrintWriter(new File(logFile));
				} else {
					// writer = new PrintWriter(new BufferedWriter(new
					// OutputStreamWriter(System.out)));
					writer = new PrintWriter(System.out, true);
				}

				String currentDateTime = getCurrentDateTime();
				writer.append(currentDateTime
						+ "\tWelcome to PIRSitePredict!\n\n");
				writer.append(commandLineArguments + "\n\n");
				writer.flush();

				if (pirsrDataDir == null) {
					throw new ParseException(
							"\tMissing PIRSR data directory (-d --pirsr-data-dir). -d -i -S -A are required.");
				}
				if (pirsr == null) {
					throw new ParseException("\tMissing PIRSR rule file.");
				} else {
					File file = new File(pirsr);
					file = new File(file.getAbsoluteFile().toString());
					boolean empty = !file.exists() || file.length() == 0;
					if (empty) {
						throw new ParseException("\t" + file.getAbsoluteFile().toString() + " does not exist.");
					}
				}
				if (iprScanXML == null) {
					throw new ParseException(
							"\tMissing InterProScan XML file (-i --iprscan-xml). -d -i -S -A are required.");
				}
				 else {
					File file = new File(iprScanXML);
					if(!file.isAbsolute()) {
//						file = new File(System.getProperty("user.dir")+System.getProperty("file.separator") + iprScanXML);
						file = new File(file.getAbsoluteFile().toString());
						boolean empty = !file.exists() || file.length() == 0;
						if (empty) {
							//throw new ParseException("\t" + iprScanXML + " with respect to \""+ System.getProperty("user.dir") +"\" (current working directory) does not exist.");
							throw new ParseException("\t" + file.getAbsoluteFile().toString()+" does not exist.");
						}
					}
					else {
						boolean empty = !file.exists() || file.length() == 0;
						if (empty) {
							throw new ParseException("\t" + iprScanXML + " does not exist.");
						}
					}
				}
				

				if (hmmsearch == null) {
					throw new ParseException(
							"\tMissing HMMER 3 hmmsearch command (-S --hmmsearch). -d -i -S -A are required.");
				}
				else {
					File file = new File(hmmsearch);
					file = new File(file.getAbsoluteFile().toString());
					boolean empty = !file.exists() || file.length() == 0;
					if (empty) {
						throw new ParseException("\t" + file.getAbsoluteFile().toString()
								+ " command does not exist.\n");
					}
				}

				if (hmmalign == null) {
					throw new ParseException(
							"\tMissing HMMER 3 hmmalign command (-A --hmmalign). -d -i -S -A are required.");
				}
				else {
					File file = new File(hmmalign);
					file = new File(file.getAbsoluteFile().toString());
					boolean empty = !file.exists() || file.length() == 0;
					if (empty) {
						throw new ParseException("\t" + file.getAbsoluteFile().toString()
								+ " command does not exist.\n");
					}
				}


				extractIPRScanMatchInfo(outputDir, forceOverwrite, pirsr,
						iprScanXML, writer);
				scanSRHMM(outputDir, hmmsearch, hmmalign, srHMMDir,
						templateDir, eval, writer);
				predictSiteFeature(outputDir, pirsr, organism, formats, writer);

				writer.flush();
				writer.close();
			}

		} catch (ParseException e) {
			//e.printStackTrace();
			writer.append("Command line error:\n" + e.getMessage() + "\n");
			formatter.printHelp(writer, 120, usage, header, options, 5, 5, "");
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			writer.append(e.getMessage() + "\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			writer.append(e.getMessage() + "\n");
			writer.flush();
			writer.close();
		} catch (XMLStreamException e) {
			//e.printStackTrace();
			writer.append(e.getMessage() + "\n");
			writer.flush();
			writer.close();
		} catch (JAXBException e) {
			//e.printStackTrace();
			writer.append(e.getMessage() + "\n");
			writer.flush();
			writer.close();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			writer.append(e.getMessage() + "\n");
			writer.flush();
			writer.close();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			writer.append(e.getMessage() + "\n");
			writer.flush();
			writer.close();
		}
	}

	private static void predictSiteFeature(String outputDir, String pirsr,
			String organism, String[] formats, PrintWriter writer)
			throws ParseException, IOException, IllegalAccessException,
			InvocationTargetException {
		if (outputDir == null) {
			throw new ParseException(
					"\tMissing output directory (-o --output-dir).");
		}
		if (pirsr == null) {
			throw new ParseException("\tMissing PIRSR rule file.");
		} else {
			File file = new File(pirsr);
			boolean empty = !file.exists() || file.length() == 0;
			if (empty) {
				throw new ParseException("\t" + pirsr + " does not exist.");
			}
		}

		// if (seqType == null) {
		// throw new
		// ParseException("\tMissing type of input sequence to InterProScan (-S --seq-type).\n");
		// }
		if (organism == null) {
			throw new ParseException(
					"\tThe taxonomic classification (-O --organism).\n");
		}

		PIRRuleDataFactory pirRuleDataFactory = PIRRuleDataFactoryImpl
				.getInstance();
		PIRRuleManager pirRuleManager = pirRuleDataFactory
				.buildPIRRuleManager(pirRuleDataFactory);
		pirRuleManager.parsePIRRules(new File(pirsr),
				PIRRuleManager.Format.UNIRULE, true, "Source");
		String ruleParseLog = pirRuleManager.getParserLogs();
		if (ruleParseLog != null && ruleParseLog.length() > 0) {
			writer.print(pirRuleManager.getParserLogs());
			System.exit(0);
		}

		PredictionDataFactory predictionDataFactory = new PredictionDataFactoryImpl();
		PredictionManager predictionManager = predictionDataFactory
				.buildPredictionManager(pirRuleDataFactory,
						predictionDataFactory, pirRuleManager, organism);

		List<String> predictRules = prepareWorkingDirectories(outputDir,
				pirRuleManager);
		for (String ruleAC : predictRules) {
			String predictionDirectory = outputDir
					+ System.getProperty("file.separator") + "prediction"
					+ System.getProperty("file.separator") + ruleAC;
			String fastaDirectory = outputDir
					+ System.getProperty("file.separator") + "iprscan"
					+ System.getProperty("file.separator") + "seq";

			String predictionReadyStatus = checkPredictionDirectory(predictionDirectory);
			if (predictionReadyStatus.equals("READY")) {
				String predictAction = getCurrentDateTime()
						+ "\tPIRSitePredict (" + ruleAC
						+ ": functional site feature predicted)\n\n";
				writer.print(predictAction);
				predictionManager.predict(predictionDirectory, fastaDirectory);
			} else {
				String predictAction = getCurrentDateTime()
						+ "\tPIRSitePrePIRSitePredict (" + ruleAC + ": no prediction)\n"
						+ predictionReadyStatus;
				writer.print(predictAction);
			}
			writer.flush();

		}
		String predictionResultsDir = outputDir
				+ System.getProperty("file.separator") + "prediction"
				+ System.getProperty("file.separator");

		savePredictionResults(outputDir, predictRules, predictionResultsDir,
				formats, writer);
		writer.append(getCurrentDateTime() + "\tPrediction is finished. Results are in: \"" + (new File(predictionResultsDir)).getAbsolutePath()+"\"\n");
	}

	private static String checkPredictionDirectory(String predictionDirectory) {

		String[] dataFiles = null;
		String checkFilesErrors = "";
		File dir = new File(predictionDirectory);
		File[] listOfFiles = dir.listFiles();

		boolean hasRuleFile = false;
		boolean hasDataFile = false;
		boolean hasAlignmentFile = false;
		String ruleFilePath = "";
		String dataFilePath = "";
		String alignmentFilePath = "";

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName();
				if (fileName.endsWith(".uru")) {
					if (hasRuleFile == true) {
						checkFilesErrors += "Multiple rule (.uru) files exist, only one is allowed.\n";
					} else {
						hasRuleFile = true;
					}
					ruleFilePath = predictionDirectory
							+ System.getProperty("file.separator") + fileName;
					File file = new File(ruleFilePath);
					if (file.length() == 0) {
						checkFilesErrors += ruleFilePath + " is empty.\n";
					}
				}
				if (fileName.endsWith(".dat")) {
					if (hasDataFile == true) {
						checkFilesErrors += "Multiple data (.dat) files exist, only one is allowed.\n";
					} else {
						hasDataFile = true;
					}
					dataFilePath = predictionDirectory
							+ System.getProperty("file.separator") + fileName;
					File file = new File(dataFilePath);
					if (file.length() == 0) {
						checkFilesErrors += "'" + dataFilePath
								+ "' is empty.\n";
					}
				}
				if (fileName.endsWith(".gff3")) {
					if (hasAlignmentFile == true) {
						checkFilesErrors += "Multiple alignment (.gff3) files exist, only one is allowed.\n";
					} else {
						hasAlignmentFile = true;
					}
					alignmentFilePath = predictionDirectory
							+ System.getProperty("file.separator") + fileName;
					File file = new File(alignmentFilePath);
					if (file.length() == 0) {
						checkFilesErrors += "'" + alignmentFilePath
								+ "' is empty.\n";
					}
				}
			}
		}
		if (checkFilesErrors != null && checkFilesErrors.length() > 0) {
			// System.err.println(checkFilesErrors);
			// System.exit(1);
		} else {
			dataFiles = new String[3];
			dataFiles[0] = ruleFilePath;
			dataFiles[1] = dataFilePath;
			dataFiles[2] = alignmentFilePath;
		}
		if (checkFilesErrors != null && checkFilesErrors.length() > 0) {
			return checkFilesErrors + "\n";
		} else {
			return "READY";
		}
	}

	private static void savePredictionResults(String outputDir,
			List<String> predictRules, String predictionResultsDir,
			String[] formats, PrintWriter writer) throws IOException {
		String predictionTSVResultsFile = predictionResultsDir
				+ "pirsr_prediction.tsv";
		String predictionXMLResultsFile = predictionResultsDir
				+ "pirsr_prediction.xml";
		String predictionGFF3ResultsFile = predictionResultsDir
				+ "pirsr_prediction.gff3";
		if (Arrays.asList(formats).contains("TSV")) {
			createTSVResults(outputDir, predictionTSVResultsFile, predictRules,
					writer);
		}
		if (Arrays.asList(formats).contains("XML")) {
			createXMLResults(outputDir, predictionXMLResultsFile, predictRules,
					writer);
		}
		if (Arrays.asList(formats).contains("GFF3")) {
			createGFF3Results(outputDir, predictionGFF3ResultsFile,
					predictRules, writer);
		}
	}

	private static void createGFF3Results(String outputDir,
			String predictionGFF3ResultsFile, List<String> predictRules,
			PrintWriter writer) throws IOException {
		StringBuffer gff3 = new StringBuffer();
		gff3.append("##gff-version 3\n##feature-ontology http://song.cvs.sourceforge.net/viewvc/song/ontology/sofa.obo?revision=1.275\n");
		// StringBuffer sequenceRegion = new StringBuffer();
		// StringBuffer fastas = new StringBuffer();
		// fastas.append("##FASTA\n");
		// StringBuffer records = new StringBuffer();
		Map<String, String> fastaNuclSeqMap = new HashMap<String, String>();
		Map<String, String> fastaProSeqMap = new HashMap<String, String>();
		List<String> sequenceRegionList = new ArrayList<String>();
		List<String> recordList = new ArrayList<String>();
		String fastaSeqId = "";
		for (String ruleAC : predictRules) {
			String predictionFile = outputDir
					+ System.getProperty("file.separator") + "prediction"
					+ System.getProperty("file.separator") + ruleAC
					+ System.getProperty("file.separator") + ruleAC
					+ "_report.gff3";
			File file = new File(predictionFile);
			boolean empty = !file.exists() || file.length() == 0;
			if (!empty) {
				String[] results = readFile(predictionFile).split("\n");
				boolean fastaStart = false;
				boolean fastaNuclStart = false;
				boolean fastaProStart = false;
				for (int i = 2; i < results.length; i++) {
					String record = results[i];
					if (record.startsWith("##")) {
						if (record.startsWith("##FASTA")) {
							fastaStart = true;
							if (record.startsWith("##FASTA (nucleic_acid)")) {
								fastaNuclStart = true;
								fastaProStart = false;
							}
							if (record.startsWith("##FASTA (protein)")) {
								fastaNuclStart = false;
								fastaProStart = true;
							}
						}
						if (!fastaStart) {
							if (!record.startsWith("##seqid")) {
								if (!sequenceRegionList.contains(record)) {
									sequenceRegionList.add(record);
								}
							}
						}
					} else {
						if (fastaNuclStart) {
							if (record.startsWith(">")) {
								fastaSeqId = record;
							} else {
								fastaNuclSeqMap.put(fastaSeqId, record);
							}
						} else if (fastaProStart) {
							if (record.startsWith(">")) {
								fastaSeqId = record;
							} else {
								fastaProSeqMap.put(fastaSeqId, record);
							}
						} else {
							if (!recordList.contains(record)) {
								recordList.add(record);
							}
							// else {
							// System.out.println("|"+record+"|");
							// }
						}
					}
				}
			}
		}
		for (String seqRegion : sequenceRegionList) {
			gff3.append(seqRegion + "\n");
		}
		gff3.append("##seqid|source|type|start|end|score|strand|phase|attributes\n");
		// gff3.append(records.toString());
		for (String record : recordList) {
			gff3.append(record + "\n");
		}
		if (fastaNuclSeqMap.size() > 0) {
			gff3.append("##FASTA (nucleic_acid)\n");
			for (String fastaSeq : fastaNuclSeqMap.keySet()) {
				gff3.append(fastaSeq + "\n");
				gff3.append(fastaNuclSeqMap.get(fastaSeq) + "\n");
			}
		}
		if (fastaProSeqMap.size() > 0) {
			gff3.append("##FASTA (protein)\n");
			for (String fastaSeq : fastaProSeqMap.keySet()) {
				gff3.append(fastaSeq + "\n");
				gff3.append(fastaProSeqMap.get(fastaSeq) + "\n");
			}
		}
		writeToFile(predictionGFF3ResultsFile, gff3.toString(), true);
		File file = new File(predictionGFF3ResultsFile);
		boolean empty = !file.exists() || file.length() == 0;
		if (!empty) {

			writer.print(getCurrentDateTime() + "\tPrediction results in GFF3 format is at: "
					+ predictionGFF3ResultsFile + "\n\n");
		} else {
			writer.print(getCurrentDateTime() + "\tNo prediction results in GFF3 format.");
		}

	}

	private static void createXMLResults(String outputDir,
			String predictionXMLResultsFile, List<String> predictRules,
			PrintWriter writer) throws IOException {
		String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<pirsr-predictions xmlns=\"http://research.bioinformatics.udel.edu/schemas/PIRSRPredict\">\n";
		writeToFile(predictionXMLResultsFile, header, true);
		for (String ruleAC : predictRules) {
			String predictionFile = outputDir
					+ System.getProperty("file.separator") + "prediction"
					+ System.getProperty("file.separator") + ruleAC
					+ System.getProperty("file.separator") + ruleAC
					+ "_report.xml";

			File file = new File(predictionFile);
			boolean empty = !file.exists() || file.length() == 0;
			if (!empty) {
				String[] results = readFile(predictionFile).split("\n");
				StringBuffer batch = new StringBuffer();
				for (int i = 2; i < results.length - 1; i++) {
					batch.append(results[i] + "\n");
				}
				writeToFile(predictionXMLResultsFile, batch.toString(), true);
			}
		}

		writeToFile(predictionXMLResultsFile, "	</pirsr-predictions>\n", true);

		File file = new File(predictionXMLResultsFile);
		boolean empty = !file.exists() || file.length() == 0;
		if (!empty) {
			writer.print(getCurrentDateTime() + "\tPrediction results in XML format is at: "
					+ predictionXMLResultsFile + "\n\n");
		} else {
			writer.print(getCurrentDateTime() + "\tNo prediction results in XML format.");
		}
	}

	

	private static void createTSVResults(String outputDir,
			String predictionResultsFile, List<String> predictRules,
			PrintWriter writer) throws IOException {
		String header = "PIRSRID\tPeptideID/ProteinID\tStart\tEnd\tType\tCategory\tDescription\tNucleotideID\tNucleotideORFStart\tNucleotideORFEnd\tNucleotideORFStrand\n";
		writeToFile(predictionResultsFile, header, true);
		for (String ruleAC : predictRules) {
			String predictionFile = outputDir
					+ System.getProperty("file.separator") + "prediction"
					+ System.getProperty("file.separator") + ruleAC
					+ System.getProperty("file.separator") + ruleAC
					+ "_report.tsv";

			File file = new File(predictionFile);
			boolean empty = !file.exists() || file.length() == 0;
			if (!empty) {
				writeToFile(predictionResultsFile, readFile(predictionFile),
						true);
			}
		}

		File file = new File(predictionResultsFile);
		boolean empty = !file.exists() || file.length() == 0;
		if (!empty) {
			writer.print(getCurrentDateTime() + "\tPrediction results in TSV format is at: "
					+ predictionResultsFile + "\n\n");
		} else {
			writer.print(getCurrentDateTime() + "\tNo prediction results in TSV format.");
		}

	}

	static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	private static List<String> prepareWorkingDirectories(String outputDir,
			PIRRuleManager pirRuleManager) throws IOException {
		// TODO Auto-generated method stub
		String iprScanMatchInfoFile = outputDir
				+ System.getProperty("file.separator") + "iprscan"
				+ System.getProperty("file.separator") + "seq"
				+ System.getProperty("file.separator") + "IPRScanMatchInfo.txt";
		List<String> predictRules = new ArrayList<String>();
		Map<String, String> ruleMatchProteinInfoMap = new HashMap<String, String>();
		FileReader fileReader = new FileReader(iprScanMatchInfoFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		// m.12940 PIRSF000108 PIRSR000108-4 O75874
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {

			String[] fields = line.split("\t");
			if (ruleMatchProteinInfoMap.get(fields[2]) == null) {
				ruleMatchProteinInfoMap.put(fields[2], line + "\n");
			} else {
				ruleMatchProteinInfoMap.put(fields[2],
						ruleMatchProteinInfoMap.get(fields[2]) + line + "\n");
			}
		}

		SortedSet<String> ruleACs = new TreeSet<String>(
				ruleMatchProteinInfoMap.keySet());
		for (String ruleAC : ruleACs) {
			String ruleFile = outputDir + System.getProperty("file.separator")
					+ "prediction" + System.getProperty("file.separator")
					+ ruleAC + System.getProperty("file.separator") + ruleAC
					+ ".uru";
			String dataFile = outputDir + System.getProperty("file.separator")
					+ "prediction" + System.getProperty("file.separator")
					+ ruleAC + System.getProperty("file.separator") + ruleAC
					+ ".dat";
			PIRRule rule = pirRuleManager.getRuleByAC(ruleAC);
			// System.out.println(ruleFile + " " + rule.getRuleAC());
			PrintWriter writer = new PrintWriter(new File(ruleFile));
			pirRuleManager.printPIRRule(rule, Format.UNIRULE, writer);
			writer.close();
			writeToFile(dataFile, ruleMatchProteinInfoMap.get(ruleAC), false);
			predictRules.add(ruleAC);
		}

		bufferedReader.close();
		return predictRules;

	}

	private static void writeToFile(String file, String content, boolean append) {
		FileWriter fw;
		try {
			fw = new FileWriter(file, append);

			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String getCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private static void scanSRHMM(String outputDir, String hmmsearchCommand,
			String hmmalignCommand, String srHMMDir, String templateDir,
			double eval, PrintWriter writer) throws Exception {
		// System.out.println("I am here");
		// String hmmsearchCommand = null;
		// String hmmalignCommand = null;
		// Properties prop = new Properties();
		//
		//
		// try {
		//
		// InputStream inputStream =
		// PIRSiteScan.class.getClassLoader().getResourceAsStream("PIRSiteScan.properties");
		//
		// prop.load(inputStream);
		// hmmsearchCommand = prop.getProperty("binary.hmmer3.hmmsearch.path");
		// hmmalignCommand = prop.getProperty("binary.hmmer3.hmmalign.path");
		//
		// } catch (IOException e) {
		// throw new Exception("PIRSiteScan.properties is missing.");
		//
		// }
		//

		if (outputDir == null) {
			throw new ParseException(
					"\tMissing output directory (-d --output-dir).\n");
		}
		// if (hmmer3BinDir == null) {
		// throw new
		// ParseException("\tMissing directory to HMMER 3 binaries (-H --hmmer3-bin-dir).\n");
		// } else {
		// hmmsearchCommand = hmmer3BinDir +
		// System.getProperty("file.separator") + "hmmsearch";
		File file = new File(hmmsearchCommand);
		boolean empty = !file.exists() || file.length() == 0;
		if (empty) {
			throw new ParseException("\t" + hmmsearchCommand
					+ " command does not exist.\n");
		}

		// hmmalignCommand = hmmer3BinDir + System.getProperty("file.separator")
		// + "hmmalign";
		file = new File(hmmalignCommand);
		empty = !file.exists() || file.length() == 0;
		if (empty) {
			throw new ParseException("\t" + hmmalignCommand
					+ " command does not exist.\n");
		}
		// }
		if (srHMMDir == null) {
			throw new ParseException("\tMissing directory to SRHMM models.\n");
		} else {
			file = new File(srHMMDir);
			if (file.isDirectory()) {
				if (file.list().length == 0) {
					throw new ParseException("\t" + srHMMDir + " is empty.\n");
				}
			} else {
				throw new ParseException("\t" + srHMMDir
						+ " is not a directory.\n");
			}
		}
		if (templateDir == null) {
			throw new ParseException(
					"\tMissing directory to PIRSR template sequences.\n");
		} else {
			file = new File(templateDir);
			if (file.isDirectory()) {
				if (file.list().length == 0) {
					throw new ParseException("\t" + templateDir
							+ " is empty.\n");
				}
			} else {
				throw new ParseException("\t" + templateDir
						+ " is not a directory.\n");
			}
		}
		String iprScanMatchInfoFile = outputDir
				+ System.getProperty("file.separator") + "iprscan"
				+ System.getProperty("file.separator") + "seq"
				+ System.getProperty("file.separator") + "IPRScanMatchInfo.txt";
		// if (iprScanMatchInfoFile == null) {

		file = new File(iprScanMatchInfoFile);
		empty = !file.exists() || file.length() == 0;
		if (!file.exists()) {
			throw new FileNotFoundException(
					"\t"
							+ iprScanMatchInfoFile
							+ " does not exist. You need to either specify the path to it or run 'ExtractIPRScanMatchInfo' step first.\n");
		}
		if (file.length() == 0) {
			throw new FileNotFoundException(
					"\t"
							+ "WARNING: Input sequence does not match PIRSF family profiles in InterPro. No prediction, program stops!\n");
		}
		// } else {
		// File file = new File(iprScanMatchInfoFile);
		// boolean empty = !file.exists() || file.length() == 0;
		// if (empty) {
		// throw new ParseException("\t" + iprScanMatchInfoFile +
		// " does not exist or is empty.\n");
		// }
		// }

		String matchedSeqDir = outputDir + System.getProperty("file.separator")
				+ "iprscan" + System.getProperty("file.separator") + "seq";
		file = new File(matchedSeqDir);
		if (file.isDirectory()) {
			if (file.list().length == 0) {
				throw new ParseException(
						"\t"
								+ matchedSeqDir
								+ " is empty. Please run 'ExtractIPRScanMatchInfo' first.\n");
			}
		} else {
			throw new ParseException(
					"\t"
							+ matchedSeqDir
							+ " is not a directory. Please run 'ExtractIPRScanMatchInfo' first.\n");
		}
		runSRHMM(outputDir, iprScanMatchInfoFile, hmmsearchCommand,
				hmmalignCommand, srHMMDir, templateDir, matchedSeqDir, eval,
				writer);
	}

	private static void runSRHMM(String outputDir, String iprScanMatchInfoFile,
			String hmmsearchCommand, String hmmalignCommand, String srHMMDir,
			String templateDir, String matchedSeqDir, double eval,
			PrintWriter writer) throws IOException {
		Map<String, String> iprScanMatchedRuleToTemplateMap = getIPRScanMatchedRuleToTemplateMap(iprScanMatchInfoFile);
		for (String ruleAC : iprScanMatchedRuleToTemplateMap.keySet()) {
			String templateAC = iprScanMatchedRuleToTemplateMap.get(ruleAC);
			String searchPath = outputDir
					+ System.getProperty("file.separator") + "hmm"
					+ System.getProperty("file.separator") + ruleAC
					+ System.getProperty("file.separator") + "search";
			mkdirs(searchPath, true);
			String alnPath = outputDir + System.getProperty("file.separator")
					+ "hmm" + System.getProperty("file.separator") + ruleAC
					+ System.getProperty("file.separator") + "aln";
			mkdirs(alnPath, true);
			String seqPath = outputDir + System.getProperty("file.separator")
					+ "hmm" + System.getProperty("file.separator") + ruleAC
					+ System.getProperty("file.separator") + "seq";
			mkdirs(seqPath, true);
			String predictionDir = outputDir
					+ System.getProperty("file.separator") + "prediction"
					+ System.getProperty("file.separator") + ruleAC;
			mkdirs(predictionDir, true);

			String srHMMModelFile = srHMMDir
					+ System.getProperty("file.separator") + ruleAC + ".hmm";
			String matchedSeqFile = matchedSeqDir
					+ System.getProperty("file.separator") + ruleAC
					+ "_iprscan.fasta";
			String searchOutFile = outputDir
					+ System.getProperty("file.separator") + "hmm"
					+ System.getProperty("file.separator") + ruleAC
					+ System.getProperty("file.separator") + "search"
					+ System.getProperty("file.separator") + ruleAC + ".out";
			String templateSeqFile = templateDir
					+ System.getProperty("file.separator") + templateAC
					+ ".fasta";

			String combinedSeqFile = outputDir
					+ System.getProperty("file.separator") + "hmm"
					+ System.getProperty("file.separator") + ruleAC
					+ System.getProperty("file.separator") + "seq"
					+ System.getProperty("file.separator") + ruleAC + ".fasta";
			String alignmentFile = outputDir
					+ System.getProperty("file.separator") + "hmm"
					+ System.getProperty("file.separator") + ruleAC
					+ System.getProperty("file.separator") + "aln"
					+ System.getProperty("file.separator") + ruleAC + ".aln";
			// String alignmentMessageFile = outputDir +
			// System.getProperty("file.separator") + "hmm" +
			// System.getProperty("file.separator") + ruleAC
			// + System.getProperty("file.separator") + "aln" +
			// System.getProperty("file.separator") + ruleAC + ".msg";

			String gffFile = predictionDir
					+ System.getProperty("file.separator") + ruleAC + ".gff3";

			String hmmsearchCommandLine = hmmsearchCommand + " -o "
					+ searchOutFile + " " + srHMMModelFile + " "
					+ matchedSeqFile;
			writer.append(getCurrentDateTime() + "\t" + ruleAC + " (hmmsearch) ");
			writer.append(hmmsearchCommandLine + "\n");
			writer.append(executeCommand(hmmsearchCommandLine) + "\n");
			writer.flush();

			concateTwoFiles(matchedSeqFile, templateSeqFile, combinedSeqFile);
			Map<String, Integer> seqLengthMap = getSeqLengthMap(combinedSeqFile);

			String hmmalignCommandLine = hmmalignCommand + " -o "
					+ alignmentFile + " " + srHMMModelFile + " "
					+ combinedSeqFile /*
																																 */;
			writer.append(getCurrentDateTime() + "\t" + ruleAC + " (hmmalign) ");
			writer.append(hmmalignCommandLine + "\n");
			writer.append(executeCommand(hmmalignCommandLine) + "\n");
			writer.flush();

			List<String> hitIds = getSRHMMHitIds(searchOutFile, eval);
			Map<String, String> alignmentMap = getSRHMMAlignments(
					alignmentFile, hitIds, templateAC);

			String gffContent = createGffContent(alignmentMap, ruleAC,
					templateAC, seqLengthMap);
			saveGffFile(gffFile, gffContent);

		}

		// mkdir -p outputDir/tmp/PIRSR001065-1/search
		// mkdir -p outputDir/tmp/PIRSR001065-1/aln
		// mkdir -p outputDir/tmp/PIRSR001065-1/seq
		// hmmsearch SR-InterPro-2015_06/data/sr_hmm/PIRSR001065-1.hmm
		// outputDir/iprscan/seq/PIRSR001065-1_iprscan.fasta >
		// outputDir/tmp/PIRSR001065-1/search/PIRSR001065-1.out
		// cat outputDir/iprscan/seq/PIRSR001065-1_iprscan.fasta
		// SR-InterPro-2015_06/data/sr_tp/PIRSR001065-1_tp.fasta >
		// outputDir/tmp/PIRSR001065-1/seq/PIRSR001065-1.fasta
		// hmmalign -o outputDir/tmp/PIRSR001065-1/aln/PIRSR001065-1.aln
		// SR-InterPro-2015_06/data/sr_hmm/PIRSR001065-1.hmm
		// outputDir/tmp/PIRSR001065-1/seq/PIRSR001065-1.fasta >
		// outputDir/tmp/PIRSR001065-1/aln/PIRSR001065-1.msg
		//
	}

	private static void saveGffFile(String gffFile, String gffContent)
			throws IOException {
		FileWriter fileWriter = new FileWriter(gffFile, true);
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		bufferWriter.append(gffContent);
		bufferWriter.flush();
		bufferWriter.close();
	}

	private static String createGffContent(Map<String, String> alignmentMap,
			String ruleAC, String templateAC, Map<String, Integer> seqLengthMap) {
		String gffContent = "";
		for (String id : alignmentMap.keySet()) {
			if (!id.equals(templateAC)) {
				gffContent += id;
				gffContent += "\tInterPro";
				String srHMM = ruleAC.replace("PIRSR", "SRHMM");
				gffContent += "\t" + srHMM;
				gffContent += "\t1";
				gffContent += "\t" + seqLengthMap.get(id);
				gffContent += "\t0\t.\t.";
				gffContent += "\tSelected 1";
				gffContent += "\tSequence \"" + alignmentMap.get(id)
						+ "\" ; FeatureTemplate \"" + templateAC + "\" \""
						+ alignmentMap.get(templateAC) + "\" ;\n";
			}
		}
		return gffContent;
	}

	private static Map<String, Integer> getSeqLengthMap(String combinedSeqFile)
			throws IOException {
		FileReader fileReader = new FileReader(combinedSeqFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		Map<String, Integer> seqLengthMap = new HashMap<String, Integer>();
		String line = null;
		int seqLength = 0;
		String id = "";
		while ((line = bufferedReader.readLine()) != null) {
			if (line.startsWith(">")) {
				if (seqLength > 0) {
					seqLengthMap.put(id, seqLength);
				}
				String[] fields = line.split("\\s+");
				id = fields[0];
				id = id.replace(">", "");
				seqLength = 0;
			} else {
				seqLength += line.length();
			}
		}
		if (seqLength > 0) {
			seqLengthMap.put(id, seqLength);
		}
		bufferedReader.close();
		return seqLengthMap;
	}

	private static Map<String, String> getSRHMMAlignments(String alignmentFile,
			List<String> hitIds, String templateAC) throws IOException {
		// # STOCKHOLM 1.0
		//
		// #=GS m.18971 DE PIRSF038189 PIRSR038189-1
		// #=GS m.18970 DE PIRSF038189 PIRSR038189-1
		// #=GS m.18973 DE PIRSF038189 PIRSR038189-1
		// #=GS Q9NWZ3 DE PIRSR038189-2
		//
		// m.18971
		// MSTSIDPNSYIRFLNHKFIHELADMLDPQDGWKKLAVNIKKTTGEPRYSQLHIRRFEGVVhM.GKSPTAELLFDWGTTNATVHELVEILIQNHFLAAASLLLP......GEVSKTNPTCsrtAIADTTVSCKSSDTStcrsMIQVSER-ESsilgrKCKTqetallTDCSNEESIVSDSTETNLQSFSYYELMQKTSNFD
		// #=GR m.18971 PP
		// 8999*****************************************************97637.***************************************9......876666555422146666555544433200102222222.2212222443311112145666666677788999*****************
		// m.18970
		// MSTSIDPNSYIRFLNHKFIHELADMLDPQDGWKKLAVNIKKTTGEPRYSQLHIRRFEGVVhM.GKSPTAELLFDWGTTNATVHELVEILIQNHFLAAASLLLP......GEVSKTNPTCsrtAIADTTVSCKSSDTStcrsMIQVSER-ESsilgrKCKTqetallTDCSNEESIVSDSTETNLQSFSYYELMQKTSNFD
		// #=GR m.18970 PP
		// 8999*****************************************************97637.***************************************9......876666555422146666555544433200102222222.22122224433
		// return null;
		FileReader fileReader = new FileReader(alignmentFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		Map<String, String> alignmentMap = new HashMap<String, String>();
		String line = null;
		//System.out.println(hitIds);
		while ((line = bufferedReader.readLine()) != null) {
			if (line.length() > 0 && !line.startsWith("#")) {
				String[] fields = line.split("\\s+");

				if (hitIds.contains(fields[0]) || fields[0].equals(templateAC)) {
					fields[1] = fields[1].replaceAll("\\.", "-");
					if (alignmentMap.get(fields[0]) == null) {
						alignmentMap.put(fields[0], fields[1]);
					} else {
						alignmentMap.put(fields[0], alignmentMap.get(fields[0])
								+ fields[1]);
					}
				}
			}
		}
		bufferedReader.close();
		return alignmentMap;
	}

	private static List<String> getSRHMMHitIds(String searchOutFile, double eval)
			throws IOException {
		// Scores for complete sequences (score includes all domains):
		// --- full sequence --- --- best 1 domain --- -#dom-
		// E-value score bias E-value score bias exp N Sequence Description
		// ------- ------ ----- ------- ------ ----- ---- -- --------
		// -----------
		// 5.6e-129 417.5 0.0 1.1e-128 416.6 0.0 1.4 1 m.18970 PIRSF038189
		// PIRSR038189-1
		// 5.6e-129 417.5 0.0 1.1e-128 416.6 0.0 1.4 1 m.18971 PIRSF038189
		// PIRSR038189-1
		// 5.6e-129 417.5 0.0 1.1e-128 416.6 0.0 1.4 1 m.18973 PIRSF038189
		// PIRSR038189-1
		FileReader fileReader = new FileReader(searchOutFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> hitIds = new ArrayList<String>();
		boolean start = false;
		boolean end = false;
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			if (line.startsWith("Scores for complete sequences")) {
				start = true;
			}
			if (line.startsWith("Domain annotation for each sequence")) {
				end = true;
			}
			if (start && !end) {
				String trimmedLine = line.trim();
				if (!trimmedLine
						.equals("[No hits detected that satisfy reporting thresholds]")) {
					if (trimmedLine.length() > 0
							&& !(trimmedLine.startsWith("Scores")
									|| trimmedLine.startsWith("---") || trimmedLine
										.startsWith("E-value"))) {
						String[] fields = trimmedLine.split("\\s+");
						double hitEvalue = Double.parseDouble(fields[0]);
						if (hitEvalue <= eval) {
							hitIds.add(fields[8]);
						}
					}
				}
			}

		}
		bufferedReader.close();

		return hitIds;
	}

	private static void concateTwoFiles(String filePath1, String filePath2,
			String filePath3) throws IOException {
		// Files to read
		File file1 = new File(filePath1);
		File file2 = new File(filePath2);

		// File to write
		File file3 = new File(filePath3);

		// Read the file as string
		String file1Str = FileUtils.readFileToString(file1);
		String file2Str = FileUtils.readFileToString(file2);

		// Write the file
		FileUtils.write(file3, file1Str);
		FileUtils.write(file3, file2Str, true); // true for append
	}

	private static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

	private static Map<String, String> getIPRScanMatchedRuleToTemplateMap(
			String iprScanMatchInfoFile) throws IOException {
		FileReader fileReader = new FileReader(iprScanMatchInfoFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		Map<String, String> ruleToTemplateMap = new TreeMap<String, String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			String[] fields = line.split("\t");
			ruleToTemplateMap.put(fields[2], fields[3]);
		}
		bufferedReader.close();

		return ruleToTemplateMap;
	}

	private static void extractIPRScanMatchInfo(String outputDir,
			boolean forceOverwrite, String pirsr, String iprScanXML,
			PrintWriter writer) throws IOException, XMLStreamException,
			JAXBException, ParseException {

		if (outputDir == null) {
			throw new ParseException(
					"\tMissing output directory (-o --output-dir).");
		} else {
			mkdirs(outputDir, forceOverwrite);
		}
		
//		if (iprScanXML == null) {
//			throw new ParseException(
//					"\tMissing InterProScan XML file (-i --iprscan-xml).");
//		} else {
//			File file = new File(iprScanXML);
//			if(!file.isAbsolute()) {
//				file = new File(System.getProperty("user.dir")+System.getProperty("file.separator") + iprScanXML);
//			}
//			
//			boolean empty = !file.exists() || file.length() == 0;
//			if (empty) {
//				throw new ParseException("\t" + file.getPath() + " does not exist.");
//			}
//		}
		// if (seqType == null) {
		// throw new
		// ParseException("\tMissing type of input sequence to InterProScan (-S --seq-type).\n");
		// }

		String iprScanMatchedSeqDir = makeIPRScanMatchedSeqDir(outputDir);
		Map<String, ArrayList<String>> triggerToRuleMap = getTriggerToRuleMap(
				pirsr, writer);
		Map<String, String> ruleToTemplateMap = getRuleToTemplateMap(pirsr,
				writer);
		// if (seqType.equals("p")) {
		// extractMatchedProteinSeqs(ruleToTemplateMap, triggerToRuleMap,
		// iprScanXML, iprScanMatchedSeqDir);
		// }
		// if (seqType.equals("n")) {
		// extractMatchedNucleotideSeqs(ruleToTemplateMap, triggerToRuleMap,
		// iprScanXML, iprScanMatchedSeqDir);
		// }

		if (isNucleotideSequences(iprScanXML)) {
			String action = getCurrentDateTime()
					+ "\tPIRSitePredict (Extract match info from InterProScan XML)\n\n";
			writer.print(action);
			writer.flush();
			extractMatchedNucleotideSeqs(ruleToTemplateMap, triggerToRuleMap,
					iprScanXML, iprScanMatchedSeqDir, writer);
		} else {
			String action = getCurrentDateTime()
					+ "\tPIRSitePredict (Extract match info from InterProScan XML)\n\n";
			writer.print(action);
			writer.flush();
			extractMatchedProteinSeqs(ruleToTemplateMap, triggerToRuleMap,
					iprScanXML, iprScanMatchedSeqDir, writer);
		}
		writer.flush();
	}

	private static boolean isNucleotideSequences(String iprScanXML)
			throws IOException {
		// /<nucleotide-sequence-matches
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				iprScanXML));

		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			if (line.trim().startsWith("<nucleotide-sequence-matches")) {
				return true;
			}
		}

		return false;
	}

	private static Map<String, String> getRuleToTemplateMap(String pirsr,
			PrintWriter writer) {
		PIRRuleDataFactory pirRuleDataFactory = PIRRuleDataFactoryImpl
				.getInstance();
		PIRRuleManager pirruleManager = pirRuleDataFactory
				.buildPIRRuleManager(pirRuleDataFactory);
		List<PIRRule> rules = null;
		rules = pirruleManager.parsePIRRules(new File(pirsr),
				PIRRuleManager.Format.UNIRULE, true, "Source");
		String ruleParseLog = pirruleManager.getParserLogs();
		if (ruleParseLog != null && ruleParseLog.length() > 0) {
			writer.print(pirruleManager.getParserLogs());
			System.exit(0);
		}
		String action = getCurrentDateTime()
				+ "\tPIRSitePredict (Get Rule Template Info)\n\n";
		writer.print(action);
		writer.flush();
		Map<String, String> ruleToTemplateMap = new HashMap<String, String>();
		for (PIRRule rule : rules) {
			String templateAC = rule.getTemplateAC();
			String ruleAC = rule.getRuleAC();
			ruleToTemplateMap.put(ruleAC, templateAC);
		}

		return ruleToTemplateMap;
	}

	private static void extractMatchedNucleotideSeqs(
			Map<String, String> ruleToTemplateMap,
			Map<String, ArrayList<String>> triggerToRuleMap, String iprScanXML,
			String iprScanMatchedSeqDir, PrintWriter writer) throws XMLStreamException,
			JAXBException, IOException {
		// System.out.println("i h ");
		File interProScan5XML = new File(iprScanXML);
		XMLInputFactory xif = XMLInputFactory.newInstance();

		StreamSource xml = new StreamSource(interProScan5XML);
		XMLStreamReader xsr = xif.createXMLStreamReader(xml);
		JAXBContext jc = JAXBContext.newInstance(NucleicAcidMatchesType.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		List<String> matchedInfo = new ArrayList<String>();
		Map<String, String> matchedNucleotideSeqs = new HashMap<String, String>();
		Map<String, String> matchedProteinSeqs = new HashMap<String, String>();
		while (xsr.hasNext()) {
			if (xsr.isStartElement()
					&& xsr.getLocalName().equals("nucleotide-sequence")) {
				//System.out.println(xsr);
				NucleotideType nucleotide = (NucleotideType) unmarshaller
						.unmarshal(xsr);
				String nucleotideSeq = nucleotide.getSequence().getValue();
				List<NucleotideSequenceXrefType> xrefList = nucleotide
						.getXref();
				//System.out.println("Nucl: "+nucleotide.getSequence().getMd5());
				
				List<OrfType> orfList = nucleotide.getOrf();
				for (OrfType orf : orfList) {
					ProteinType protein = orf.getProtein();
					//System.out.println(nucleotide.getSequence().getMd5() + " "+protein.getSequence().getMd5());
					if (protein != null && protein.getMatches() != null) {
						if (protein.getMatches().getHmmer3Match() != null) {
							for (Hmmer3MatchType hmmer3Match : protein
									.getMatches().getHmmer3Match()) {
								if (hmmer3Match.getSignature().getEntry() != null
										&& hmmer3Match.getSignature()
												.getEntry().getAc()
												.startsWith("IPR")) {

									String iprSignature = hmmer3Match
											.getSignature().getEntry().getAc();
									// System.out.println(iprSignature);
									List<String> ruleACs = triggerToRuleMap
											.get(iprSignature);
									if (ruleACs != null && ruleACs.size() > 0) {
										for (String ruleAC : ruleACs) {
											for (ProteinXrefType xrefType : protein
													.getXref()) {
												String matchId = xrefType
														.getId();
												String proteinSeq = protein
														.getSequence()
														.getValue();
												String matchInfo = matchId
														+ "\t"
														+ iprSignature
														+ "\t"
														+ ruleAC
														+ "\t"
														+ ruleToTemplateMap
																.get(ruleAC)
														+ "\t";
												String matchedNucleotideIds = "";
												for (NucleotideSequenceXrefType nucleotideXref : xrefList) {
													if (matchedNucleotideIds
															.length() > 0) {
														matchedNucleotideIds += ","
																+ nucleotideXref
																		.getId();
													} else {
														matchedNucleotideIds = nucleotideXref
																.getId();
													}
													matchInfo += matchedNucleotideIds;
													matchedNucleotideSeqs.put(
															nucleotideXref
																	.getId(),
															nucleotideSeq);
												}
												matchInfo += "\t"
														+ orf.getStart() + "\t"
														+ orf.getEnd() + "\t"
														+ orf.getStrand();
												if (!matchedInfo
														.contains(matchInfo)) {
													matchedInfo.add(matchInfo);
												}
												matchedProteinSeqs.put(
														matchInfo, proteinSeq);
												saveMatchProteinSeqs(
														iprScanMatchedSeqDir,
														ruleAC, matchInfo,
														proteinSeq, writer);

												// matchedProteinSeqs.put(matchId,
												// proteinSeq);

												// saveMatchProteinSeqs(iprScanMatchedSeqDir,
												// ruleAC, matchId,
												// iprSignature, proteinSeq);
											}
										}
									}
								}
								if (hmmer3Match.getSignature() != null
										&& hmmer3Match.getSignature().getAc()
												.startsWith("PIRSF")) {
									String pirsfSignature = hmmer3Match
											.getSignature().getAc();

									List<String> ruleACs = triggerToRuleMap
											.get(pirsfSignature);
									if (ruleACs != null && ruleACs.size() > 0) {
										for (String ruleAC : ruleACs) {
											for (ProteinXrefType xrefType : protein
													.getXref()) {
												String matchId = xrefType
														.getId();
												String proteinSeq = protein
														.getSequence()
														.getValue();

												String matchInfo = matchId
														+ "\t"
														+ pirsfSignature
														+ "\t"
														+ ruleAC
														+ "\t"
														+ ruleToTemplateMap
																.get(ruleAC)
														+ "\t";
												String matchedNucleotideIds = "";
												for (NucleotideSequenceXrefType nucleotideXref : xrefList) {
													if (matchedNucleotideIds
															.length() > 0) {
														matchedNucleotideIds += ","
																+ nucleotideXref
																		.getId();
													} else {
														matchedNucleotideIds = nucleotideXref
																.getId();
													}
													matchInfo += matchedNucleotideIds;
													matchedNucleotideSeqs.put(
															nucleotideXref
																	.getId(),
															nucleotideSeq);
												}
												matchInfo += "\t"
														+ orf.getStart() + "\t"
														+ orf.getEnd() + "\t"
														+ orf.getStrand();
												if (!matchedInfo
														.contains(matchInfo)) {
													matchedInfo.add(matchInfo);
												}
												matchedProteinSeqs.put(
														matchInfo, proteinSeq);
												saveMatchProteinSeqs(
														iprScanMatchedSeqDir,
														ruleAC, matchInfo,
														proteinSeq, writer);
											}
										}
									}
								}
							}
						}
						if (protein.getMatches().getHmmer2Match() != null) {
							for (Hmmer2MatchType hmmer2Match : protein
									.getMatches().getHmmer2Match()) {
								if (hmmer2Match.getSignature() != null
										&& hmmer2Match.getSignature().getAc()
												.startsWith("PIRSF")) {
									String pirsfSignature = hmmer2Match
											.getSignature().getAc();
									List<String> ruleACs = triggerToRuleMap
											.get(pirsfSignature);
									if (ruleACs != null && ruleACs.size() > 0) {
										for (String ruleAC : ruleACs) {
											for (ProteinXrefType xrefType : protein
													.getXref()) {
												String matchId = xrefType
														.getId();
												String proteinSeq = protein
														.getSequence()
														.getValue();

												String matchInfo = matchId
														+ "\t"
														+ pirsfSignature
														+ "\t"
														+ ruleAC
														+ "\t"
														+ ruleToTemplateMap
																.get(ruleAC)
														+ "\t";
												String matchedNucleotideIds = "";
												for (NucleotideSequenceXrefType nucleotideXref : xrefList) {
													if (matchedNucleotideIds
															.length() > 0) {
														matchedNucleotideIds += ","
																+ nucleotideXref
																		.getId();
													} else {
														matchedNucleotideIds = nucleotideXref
																.getId();
													}
													matchInfo += matchedNucleotideIds;
													matchedNucleotideSeqs.put(
															nucleotideXref
																	.getId(),
															nucleotideSeq);
												}
												matchInfo += "\t"
														+ orf.getStart() + "\t"
														+ orf.getEnd() + "\t"
														+ orf.getStrand();
												if (!matchedInfo
														.contains(matchInfo)) {
													matchedInfo.add(matchInfo);
												}
												matchedProteinSeqs.put(
														matchInfo, proteinSeq);
												saveMatchProteinSeqs(
														iprScanMatchedSeqDir,
														ruleAC, matchInfo,
														proteinSeq, writer);
											}
										}
									}
								}

								if (hmmer2Match.getSignature().getEntry() != null
										&& hmmer2Match.getSignature()
												.getEntry().getAc()
												.startsWith("IPR")) {
									String iprSignature = hmmer2Match
											.getSignature().getEntry().getAc();
									List<String> ruleACs = triggerToRuleMap
											.get(iprSignature);
									if (ruleACs != null && ruleACs.size() > 0) {
										for (String ruleAC : ruleACs) {
											for (ProteinXrefType xrefType : protein
													.getXref()) {
												String matchId = xrefType
														.getId();
												String proteinSeq = protein
														.getSequence()
														.getValue();

												String matchInfo = matchId
														+ "\t"
														+ iprSignature
														+ "\t"
														+ ruleAC
														+ "\t"
														+ ruleToTemplateMap
																.get(ruleAC)
														+ "\t";
												String matchedNucleotideIds = "";
												for (NucleotideSequenceXrefType nucleotideXref : xrefList) {
													if (matchedNucleotideIds
															.length() > 0) {
														matchedNucleotideIds += ","
																+ nucleotideXref
																		.getId();
													} else {
														matchedNucleotideIds = nucleotideXref
																.getId();
													}
													matchInfo += matchedNucleotideIds;
													matchedNucleotideSeqs.put(
															nucleotideXref
																	.getId(),
															nucleotideSeq);
												}
												matchInfo += "\t"
														+ orf.getStart() + "\t"
														+ orf.getEnd() + "\t"
														+ orf.getStrand();
												if (!matchedInfo
														.contains(matchInfo)) {
													matchedInfo.add(matchInfo);
												}
												matchedProteinSeqs.put(
														matchInfo, proteinSeq);
												saveMatchProteinSeqs(
														iprScanMatchedSeqDir,
														ruleAC, matchInfo,
														proteinSeq, writer);
											}
										}
									}
								}
							}
						}
					}
				}
			}

			xsr.next();
		}
		//System.out.println("I am here");
		saveMatchInfo(matchedInfo, iprScanMatchedSeqDir);
		saveMatchedProteinSeqs(matchedProteinSeqs, iprScanMatchedSeqDir);
		saveMatchedNucleotideSeqs(matchedNucleotideSeqs, iprScanMatchedSeqDir);
		xsr.close();

	}

	private static void extractMatchedProteinSeqs(
			Map<String, String> ruleToTemplateMap,
			Map<String, ArrayList<String>> triggerToRuleMap, String iprScanXML,
			String iprScanMatchedSeqDir, PrintWriter writer) throws XMLStreamException,
			JAXBException, IOException {

		File interProScan5XML = new File(iprScanXML);
		XMLInputFactory xif = XMLInputFactory.newInstance();
		StreamSource xml = new StreamSource(interProScan5XML);
		XMLStreamReader xsr = xif.createXMLStreamReader(xml);
		JAXBContext jc = JAXBContext.newInstance(ProteinMatchesType.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		List<String> matchIdTriggerRuleACList = new ArrayList<String>();
		Map<String, String> matchedProteinSeqs = new HashMap<String, String>();
		while (xsr.hasNext()) {
			if (xsr.isStartElement() && xsr.getLocalName().equals("protein")) {
				ProteinType protein = (ProteinType) unmarshaller.unmarshal(xsr);
				// System.out.println(xsr);
				if (protein.getMatches() != null
						&& protein.getMatches().getHmmer3Match() != null) {
					for (Hmmer3MatchType hmmer3Match : protein.getMatches()
							.getHmmer3Match()) {
						if (hmmer3Match.getSignature().getEntry() != null
								&& hmmer3Match.getSignature().getEntry()
										.getAc().startsWith("IPR")) {
							String iprSignature = hmmer3Match.getSignature()
									.getEntry().getAc();
							List<String> ruleACs = triggerToRuleMap
									.get(iprSignature);
							if (ruleACs != null && ruleACs.size() > 0) {
								for (String ruleAC : ruleACs) {
									for (ProteinXrefType xrefType : protein
											.getXref()) {
										String matchId = xrefType.getId();
										String proteinSeq = protein
												.getSequence().getValue();
										String matchInfo = matchId + "\t"
												+ iprSignature + "\t" + ruleAC
												+ "\t"
												+ ruleToTemplateMap.get(ruleAC);
										if (!matchIdTriggerRuleACList
												.contains(matchInfo)) {
											matchIdTriggerRuleACList
													.add(matchInfo);
										}
										matchedProteinSeqs.put(matchInfo,
												proteinSeq);
										saveMatchProteinSeqs(
												iprScanMatchedSeqDir, ruleAC,
												matchInfo, proteinSeq, writer);
									}
								}
							}
						}
						if (hmmer3Match.getSignature() != null
								&& hmmer3Match.getSignature().getAc()
										.startsWith("PRISF")) {
							String pirsfSignature = hmmer3Match.getSignature()
									.getEntry().getAc();
							List<String> ruleACs = triggerToRuleMap
									.get(pirsfSignature);
							if (ruleACs != null && ruleACs.size() > 0) {
								for (String ruleAC : ruleACs) {
									for (ProteinXrefType xrefType : protein
											.getXref()) {
										String matchId = xrefType.getId();
										String proteinSeq = protein
												.getSequence().getValue();
										String matchInfo = matchId + "\t"
												+ pirsfSignature + "\t"
												+ ruleAC + "\t"
												+ ruleToTemplateMap.get(ruleAC);
										if (!matchIdTriggerRuleACList
												.contains(matchInfo)) {
											matchIdTriggerRuleACList
													.add(matchInfo);
										}
										matchedProteinSeqs.put(matchInfo,
												proteinSeq);
										saveMatchProteinSeqs(
												iprScanMatchedSeqDir, ruleAC,
												matchInfo, proteinSeq, writer);
									}
								}
							}
						}
					}
				}

				if (protein.getMatches() != null
						&& protein.getMatches().getHmmer2Match() != null) {
					for (Hmmer2MatchType hmmer2Match : protein.getMatches()
							.getHmmer2Match()) {
						if (hmmer2Match.getSignature().getAc() != null
								&& hmmer2Match.getSignature().getAc()
										.startsWith("PIRSF")) {
							String pirsfSignature = hmmer2Match.getSignature()
									.getAc();
							List<String> ruleACs = triggerToRuleMap
									.get(pirsfSignature);
							if (ruleACs != null && ruleACs.size() > 0) {
								for (String ruleAC : ruleACs) {
									for (ProteinXrefType xrefType : protein
											.getXref()) {
										String matchId = xrefType.getId();
										String proteinSeq = protein
												.getSequence().getValue();
										String matchInfo = matchId + "\t"
												+ pirsfSignature + "\t"
												+ ruleAC + "\t"
												+ ruleToTemplateMap.get(ruleAC);

										if (!matchIdTriggerRuleACList
												.contains(matchInfo)) {
											matchIdTriggerRuleACList
													.add(matchInfo);
										}
										matchedProteinSeqs.put(matchInfo,
												proteinSeq);
										saveMatchProteinSeqs(
												iprScanMatchedSeqDir, ruleAC,
												matchInfo, proteinSeq, writer);
									}
								}
							}
						}
						if (hmmer2Match.getSignature().getEntry() != null
								&& hmmer2Match.getSignature().getEntry()
										.getAc().startsWith("IPR")) {
							String iprSignature = hmmer2Match.getSignature()
									.getEntry().getAc();
							List<String> ruleACs = triggerToRuleMap
									.get(iprSignature);
							if (ruleACs != null && ruleACs.size() > 0) {
								for (String ruleAC : ruleACs) {
									for (ProteinXrefType xrefType : protein
											.getXref()) {
										String matchId = xrefType.getId();
										String proteinSeq = protein
												.getSequence().getValue();
										String matchInfo = matchId + "\t"
												+ iprSignature + "\t" + ruleAC
												+ "\t"
												+ ruleToTemplateMap.get(ruleAC);
										if (!matchIdTriggerRuleACList
												.contains(matchInfo)) {
											matchIdTriggerRuleACList
													.add(matchInfo);
										}
										matchedProteinSeqs.put(matchInfo,
												proteinSeq);
										saveMatchProteinSeqs(
												iprScanMatchedSeqDir, ruleAC,
												matchInfo, proteinSeq, writer);
									}
								}
							}
						}
					}
				}

			}
			xsr.next();
		}
		saveMatchInfo(matchIdTriggerRuleACList, iprScanMatchedSeqDir);
		saveMatchedProteinSeqs(matchedProteinSeqs, iprScanMatchedSeqDir);
		xsr.close();
	}

	private static void saveMatchedProteinSeqs(Map<String, String> matchedSeqs,
			String iprScanMatchedSeqDir) throws IOException {
		String matchedSeqsFile = iprScanMatchedSeqDir
				+ System.getProperty("file.separator")
				+ "IPRScanMatchedProteinSeqs.fasta";
		FileWriter fileWriter = new FileWriter(matchedSeqsFile, true);
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		for (String matchInfo : matchedSeqs.keySet()) {
			bufferWriter.append(">" + matchInfo + "\n");
			bufferWriter.append(matchedSeqs.get(matchInfo) + "\n");
		}
		bufferWriter.close();
	}

	private static void saveMatchedNucleotideSeqs(
			Map<String, String> matchedNucleotideSeqs,
			String iprScanMatchedSeqDir) throws IOException {
		String matchedSeqsFile = iprScanMatchedSeqDir
				+ System.getProperty("file.separator")
				+ "IPRScanMatchedNucleotideSeqs.fasta";
		FileWriter fileWriter = new FileWriter(matchedSeqsFile, true);
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		for (String matchInfo : matchedNucleotideSeqs.keySet()) {
			bufferWriter.append(">" + matchInfo + "\n");
			bufferWriter.append(matchedNucleotideSeqs.get(matchInfo) + "\n");
		}
		bufferWriter.close();
	}

	private static void saveMatchInfo(List<String> matchIdTriggerRuleACList,
			String iprScanMatchedSeqDir) throws IOException {
		String matchInfoFile = iprScanMatchedSeqDir
				+ System.getProperty("file.separator") + "IPRScanMatchInfo.txt";
		FileWriter fileWriter = new FileWriter(matchInfoFile, true);
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		for (String matchInfo : matchIdTriggerRuleACList) {
			bufferWriter.write(matchInfo + "\n");
		}
		bufferWriter.close();
	}

	private static void saveMatchProteinSeqs(String iprScanMatchedSeqDir,
			String ruleAC, String matchInfo, String proteinSeq, PrintWriter writer)
			throws IOException {
		String seqFileName = iprScanMatchedSeqDir
				+ System.getProperty("file.separator") + ruleAC
				+ "_iprscan.fasta";
		FileWriter fileWritter = new FileWriter(seqFileName, true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		String seq = ">" + matchInfo + "\n" + proteinSeq + "\n";
		bufferWritter.write(seq);
		bufferWritter.close();
		
		String action = getCurrentDateTime()
				+ "\tPIRSitePredict (save matched protein sequences) "+seqFileName+"\n\n";
		writer.print(action);
		writer.flush();
	}

	private static String makeIPRScanMatchedSeqDir(String outputDir)
			throws IOException {
		String iprScanMatchedSeqDir = outputDir
				+ System.getProperty("file.separator") + "iprscan"
				+ System.getProperty("file.separator") + "seq";
		// FileUtils.deleteDirectory(new File(iprScanMatchedSeqDir));
		// new File(iprScanMatchedSeqDir).mkdirs();
		if (mkdirs(iprScanMatchedSeqDir, true)) {
			return iprScanMatchedSeqDir;
		} else {
			return null;
		}
	}

	private static boolean mkdirs(String pathToDir, boolean deleteIfExists)
			throws IOException {
		if (deleteIfExists) {
			FileUtils.deleteDirectory(new File(pathToDir));
			new File(pathToDir).mkdirs();
		} else {
			File file = new File(pathToDir);
			boolean empty = !file.exists() || file.length() == 0;
			if (!empty && !deleteIfExists) {
				throw new IOException(
						file.getAbsolutePath()
								+ " already exists. Please specify another directory or use -F to overwrite existing directory.");
			} else {
				new File(pathToDir).mkdirs();
			}
		}
		return true;
	}

	private static Map<String, ArrayList<String>> getTriggerToRuleMap(
			String pirsr, PrintWriter writer) {
		PIRRuleDataFactory pirRuleDataFactory = PIRRuleDataFactoryImpl
				.getInstance();
		PIRRuleManager pirruleManager = pirRuleDataFactory
				.buildPIRRuleManager(pirRuleDataFactory);
		List<PIRRule> rules = null;
		rules = pirruleManager.parsePIRRules(new File(pirsr),
				PIRRuleManager.Format.UNIRULE, true, "Source");
		String ruleParseLog = pirruleManager.getParserLogs();
		if (ruleParseLog != null && ruleParseLog.length() > 0) {
			writer.print(pirruleManager.getParserLogs());
			System.exit(0);
		}

		String action = getCurrentDateTime()
				+ "\tPIRSitePredict (Get Rule Trigger Info)\n\n";
		writer.print(action);
		writer.flush();
		Map<String, ArrayList<String>> triggerToRuleMap = new HashMap<String, ArrayList<String>>();
		for (PIRRule rule : rules) {
			String trigger = rule.getTrigger();
			String ruleAC = rule.getRuleAC();
			ArrayList<String> ruleACs;
			if (triggerToRuleMap.get(trigger) == null) {
				ruleACs = new ArrayList<String>();
				ruleACs.add(ruleAC);
				triggerToRuleMap.put(trigger, ruleACs);
			} else {
				ruleACs = triggerToRuleMap.get(trigger);
				ruleACs.add(ruleAC);
				triggerToRuleMap.put(trigger, ruleACs);
			}
		}

		return triggerToRuleMap;
	}

	protected static Options createOptions() {
		// Create the options
		Options options = new Options();

		// Create "help" option
		OptionBuilder.withLongOpt("help");
		OptionBuilder.hasArg(false);
		OptionBuilder.withDescription("Show help.");
		Option helpOption = OptionBuilder.create("h");
		options.addOption(helpOption);

		// Create "task" option
		// OptionBuilder.withLongOpt("task");
		// OptionBuilder.hasArg(true);
		// OptionBuilder.withArgName("TASK-NAME");
		// //OptionBuilder.isRequired();
		// OptionBuilder.withDescription("Name of the task to run (i.e. 'ExtractIPRScanMatchInfo', 'ScanSiteHMM', 'PredictSiteFeature'). If nothing is specified, the program will run the tasks of 'ExtractIPRScanMatchInfo', 'ScanSiteHMM', 'PredictSiteFeature' automatically in that order to streamline the whole process.");
		// Option taskOption = OptionBuilder.create("t");
		// options.addOption(taskOption);

		// Create "output-dir" option
		OptionBuilder.withLongOpt("output-dir");
		OptionBuilder.hasArg(true);
		OptionBuilder.withArgName("PATH/TO/OUTPUT/DIR");
		// OptionBuilder.isRequired();
		OptionBuilder
				.withDescription("Output directory. It will be created automatically if it does not exist. Default: 'outputDir' in the current directory.");
		/* It will also overwrite existing sub-directories and files. */
		Option outputDirOption = OptionBuilder.create("o");
		options.addOption(outputDirOption);

		OptionBuilder.withLongOpt("force-overwrite");
		OptionBuilder.hasArg(false);
		OptionBuilder.withDescription("Force overwrite output directory.");
		Option forceOverwriteOption = OptionBuilder.create("F");
		options.addOption(forceOverwriteOption);

		// Create "data" option
		OptionBuilder.withLongOpt("pirsr-data-dir");
		OptionBuilder.hasArg(true);
		OptionBuilder.withArgName("PATH/TO/PIRSR/Data/DIR");
		// OptionBuilder.isRequired();
		OptionBuilder
				.withDescription("Required, the directory where the PIR Site Rule data (Site Rule, Site HMM models and Rule template sequence) files are located.");
		Option dataDirOption = OptionBuilder.create("d");
		options.addOption(dataDirOption);

		// Create "rule" option
		// OptionBuilder.withLongOpt("rule");
		// OptionBuilder.hasArg(true);
		// OptionBuilder.withArgName("PIRSR/RULE/FILE");
		// OptionBuilder.withDescription("Path to PIRSR.uru file.");
		// Option ruleOption = OptionBuilder.create("r");
		// options.addOption(ruleOption);

		// Create "iprScanXML" option
		OptionBuilder.withLongOpt("iprscan-xml");
		OptionBuilder.hasArg(true);
		// OptionBuilder.isRequired();
		OptionBuilder.withArgName("PATH/TO/IPRSCAN/XML/FILE");
		OptionBuilder
				.withDescription("Required, the path to InterProScan XML file.");
		Option iprxmlOption = OptionBuilder.create("i");
		options.addOption(iprxmlOption);

		// Create "logFile" option
		OptionBuilder.withLongOpt("log-file");
		OptionBuilder.hasArg(true);
		OptionBuilder.withArgName("PATH/TO/LOG/FILE");
		OptionBuilder.withDescription("Path to log file.");
		Option logFileOption = OptionBuilder.create("l");
		options.addOption(logFileOption);

		// Create "hmmsearch" option
		OptionBuilder.withLongOpt("hmmsearch");
		OptionBuilder.hasArg(true);
		// OptionBuilder.isRequired();
		OptionBuilder.withArgName("PATH/TO/HMMSEARCH/COMMAND");
		OptionBuilder.withDescription("Path to the HMMer3 hmmsearch command.");
		Option hmmsearchOption = OptionBuilder.create("S");
		options.addOption(hmmsearchOption);

		// Create "hmmalign" option
		OptionBuilder.withLongOpt("hmmalign");
		OptionBuilder.hasArg(true);
		// OptionBuilder.isRequired();
		OptionBuilder.withArgName("PATH/TO/HMMALIGN/COMMAND");
		OptionBuilder.withDescription("Path to the HMMer3 hmmalign command.");
		Option hmmalignOption = OptionBuilder.create("A");
		options.addOption(hmmalignOption);

		// Create "iprScanMatch" option
		// OptionBuilder.withLongOpt("iprscan-match");
		// OptionBuilder.hasArg(true);
		// OptionBuilder.withArgName("MATCH/INFO/File");
		// OptionBuilder
		// .withDescription("Path to extracted InterProScan match information file in 'ExtractIPRScanMatchInfo' step (Default: {output-dir}/iprscan/seq/IPRScanMatchInfo.txt).");
		// Option iprScanMatch = OptionBuilder.create("m");
		// options.addOption(iprScanMatch);

		// Create "srhmm" option
		// OptionBuilder.withLongOpt("srhmm-dir");
		// OptionBuilder.hasArg(true);
		// OptionBuilder.withArgName("SRHMM/MODEL/DIR");
		// OptionBuilder.withDescription("Path to the directory where the SRHMM hmm models are located.");
		// Option srhmm = OptionBuilder.create("s");
		// options.addOption(srhmm);

		// Create "template" option
		// OptionBuilder.withLongOpt("template-dir");
		// OptionBuilder.hasArg(true);
		// OptionBuilder.withArgName("PIRSR/TEMPLATE/DIR");
		// OptionBuilder.withDescription("Path to the directory where the PIRSR template seqeunces are located.");
		// Option template = OptionBuilder.create("T");
		// options.addOption(template);

		// Create "organism" option
		OptionBuilder.withLongOpt("organism");
		OptionBuilder.hasArg(true);
		// OptionBuilder.isRequired();
		OptionBuilder.withArgName("TAXONOMY");
		OptionBuilder
				.withDescription("The taxonomic classification is composed of the kingdom, optionally followed by the name of a sub-taxon, to further limit the application of the UniRule to any taxonomic level. Valid values for kingdom are: 'Eukaryota', 'Bacteria', 'Archaea', 'Viruses', 'Bacteriophage', 'Plastid' and 'Mitochondrion'. Default: 'Eukaryota'.");
		Option organism = OptionBuilder.create("O");
		options.addOption(organism);

		// Create "eval" option
		OptionBuilder.withLongOpt("eval");
		OptionBuilder.hasArg(true);
		OptionBuilder.withArgName("E-VALUE");
		OptionBuilder
				.withDescription("The e-value cutoff of matches to SRHMM models (default: 0.0001).");
		Option eval = OptionBuilder.create("e");
		options.addOption(eval);

		// -t,--seqtype <SEQUENCE-TYPE> Optional, the type of the
		// input sequences (dna/rna (n)
		// or protein (p)). The default
		// sequence type is protein.
		// OptionBuilder.withLongOpt("seq-type");
		// OptionBuilder.hasArg(true);
		// OptionBuilder.withArgName("SEQUENCE-TYPE");
		// OptionBuilder.withDescription("The type of input sequence to InterProScan [nucleotide (n) or protein (p)].");
		// Option seqType = OptionBuilder.create("S");
		// options.addOption(seqType);

		OptionBuilder.withLongOpt("formats");
		OptionBuilder.hasArg(true);
		OptionBuilder.withArgName("PREDICTION-OUTPUT-FORMATS");
		OptionBuilder
				.withDescription("Comma separated list of output formats. Supported formats are TSV, GFF3, XML.");
		Option formats = OptionBuilder.create("f");
		options.addOption(formats);

		return options;
	}

}
