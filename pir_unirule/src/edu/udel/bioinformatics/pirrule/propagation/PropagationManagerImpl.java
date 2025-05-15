package edu.udel.bioinformatics.pirrule.propagation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.proteininformationresource.pirrule.model.CCLine;
import org.proteininformationresource.pirrule.model.ControlBlock;
import org.proteininformationresource.pirrule.model.ControlStatement;
import org.proteininformationresource.pirrule.model.EndCaseStatement;
import org.proteininformationresource.pirrule.model.FeatureDescriptionLine;
import org.proteininformationresource.pirrule.model.FeatureType;
import org.proteininformationresource.pirrule.model.KWLine;
import org.proteininformationresource.pirrule.model.Line;
import org.proteininformationresource.pirrule.model.PIRRule;
import org.proteininformationresource.pirrule.model.PIRRuleManager;
import org.proteininformationresource.pirrule.model.RuleCommentType;
import org.proteininformationresource.pirrule.model.expression.ExpressionValue;
import org.proteininformationresource.pirrule.propagation.Alignment;
import org.proteininformationresource.pirrule.propagation.PropagationDataFactory;
import org.proteininformationresource.pirrule.propagation.PropagationManager;
import org.proteininformationresource.pirrule.propagation.PropagationRecord;
import org.proteininformationresource.pirrule.propagation.PropagationStats;
import org.proteininformationresource.pirrule.propagation.annotation.EntryAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.EntryCommentAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.EntryFeatureTableAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.EntryKeywordAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.RuleAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.RuleCommentAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.RuleFeatureTableAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.RuleKeywordAnnotation;
import org.proteininformationresource.pirrule.uniprot.model.EntryComment;
import org.proteininformationresource.pirrule.uniprot.model.EntryCommentType;
import org.proteininformationresource.pirrule.uniprot.model.EntryDataFactory;
import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureType;
import org.proteininformationresource.pirrule.uniprot.model.EntryKeyword;
import org.proteininformationresource.pirrule.uniprot.model.EntryType;
import org.proteininformationresource.pirrule.uniprot.model.UniProtEntry;

import edu.udel.bioinformatics.pirrule.PIRRuleUtil;
import edu.udel.bioinformatics.pirrule.uniprot.EntryDataFactoryImpl;
import edu.udel.bioinformatics.pirrule.uniprot.io.UniProtFlatFileParser;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 8, 2014<br>
 * <br>
 * 
 * The implementation of <code>AnnotationManager</code>.
 */
public class PropagationManagerImpl implements PropagationManager {

	private boolean debug = false;
	private PropagationDataFactory propagateionDataFactory;
	private List<Alignment> alignmentList;
	private List<UniProtEntry> entryList;
	private PIRRuleManager pirRuleManager;

	EntryDataFactory factory = EntryDataFactoryImpl.getInstance();

	public PropagationManagerImpl(
			PropagationDataFactory propagationDataFactory,
			PIRRuleManager pirRuleManager) {
		super();
		this.propagateionDataFactory = propagationDataFactory;
		this.pirRuleManager = pirRuleManager;
	}

	@Override
	public PropagationDataFactory getPropagationDataFactory() {
		return this.propagateionDataFactory;
	}

	@Override
	public List<Alignment> loadAlignment(URL url) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));

			AlignmentParser s = new AlignmentParser(this, br);

			s.parse();
			this.alignmentList = s.getAlignments();

			br.close();
		} catch (IOException e) {
			Logger.getLogger(PropagationManager.class.getName()).log(
					Level.SEVERE, null, e);
		}
		return this.alignmentList;
	}

	@Override
	public List<Alignment> loadAlignment(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			AlignmentParser s = new AlignmentParser(this, br);

			s.parse();
			this.alignmentList = s.getAlignments();

			br.close();
		} catch (IOException e) {
			Logger.getLogger(PropagationManager.class.getName()).log(
					Level.SEVERE, null, e);
		}
		return this.alignmentList;
	}

	@Override
	public List<Alignment> loadAlignment(InputStream inputStream) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));

			AlignmentParser s = new AlignmentParser(this, br);

			s.parse();
			this.alignmentList = s.getAlignments();

			br.close();
		} catch (IOException e) {
			Logger.getLogger(PropagationManager.class.getName()).log(
					Level.SEVERE, null, e);
		}
		return this.alignmentList;
	}

	@Override
	public Alignment getAlignment(String ruleAC, String entryId,
			String templateAC) {
		Alignment alignment = null;
		for (int i = 0; i < this.alignmentList.size(); i++) {
			Alignment tempAlignment = alignmentList.get(i);
			if (tempAlignment.getRuleAC().equals(ruleAC)) {
				alignment = tempAlignment;
			}
		}
		return alignment;
	}

	@Override
	// public List<UniProtEntry> loadEntry(URL url) {
	// DefaultUniProtFactory factory =DefaultUniProtFactory.getInstance();
	// List<UniProtEntry> entryList = new ArrayList<UniProtEntry>();
	// EntryIterator entryIterator = UniProtParser.parseAll(url, factory);
	// while(entryIterator.hasNext()) {
	// UniProtEntry entry = entryIterator.next();
	// entryList.add(entry);
	// }
	// return entryList;
	// }
	public List<UniProtEntry> loadEntry(URL url) {
		this.entryList = new ArrayList<UniProtEntry>();
		BufferedReader br;
		StringBuffer entryBuffer = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			while ((line = br.readLine()) != null) {
				entryBuffer.append(line + "\n");
				if (line.startsWith("//")) {
					UniProtEntry entry = UniProtFlatFileParser.parse(
							entryBuffer.toString(), factory);
					this.entryList.add(entry);
					entryBuffer = new StringBuffer();
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.entryList;
	}

	@Override
	// public List<UniProtEntry> loadEntry(File file) {
	// DefaultUniProtFactory factory = DefaultUniProtFactory.getInstance();
	// List<UniProtEntry> entryList = new ArrayList<UniProtEntry>();
	// EntryIterator entryIterator = (EntryIterator)
	// UniProtParser.parseAll(file, factory);
	// while (entryIterator.hasNext()) {
	// UniProtEntry entry = entryIterator.next();
	// entryList.add(entry);
	// }
	// return entryList;
	// }
	public List<UniProtEntry> loadEntry(File file) {
		this.entryList = new ArrayList<UniProtEntry>();
		BufferedReader br;
		StringBuffer entryBuffer = new StringBuffer();
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			long count = 0;
			while ((line = br.readLine()) != null) {
				// if(line.startsWith("DR   ExpressionAtlas")) {
				//
				// }
				// else {
				entryBuffer.append(line + "\n");
				// }

				if (line.startsWith("//")) {
					UniProtEntry entry = UniProtFlatFileParser.parse(
							entryBuffer.toString(), factory);
					this.entryList.add(entry);
					count++;
					if (debug) {
						//System.out.println("load " + entry.getPrimiaryAccessionNumber());
						if (count % 10000 == 0) {
							System.out.println("Load entry: " +count);
						}
						
					}
					entryBuffer = new StringBuffer();
				}
			}
			if (debug) {
				System.out.println("Load entry: " + count);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.entryList;
	}

	@Override
	// public List<UniProtEntry> loadEntry(InputStream inputStream) {
	// DefaultUniProtFactory factory = DefaultUniProtFactory.getInstance();
	// List<UniProtEntry> entryList = new ArrayList<UniProtEntry>();
	// EntryIterator entryIterator = null;
	// try {
	// entryIterator = UniProtParser.parseAll(inputStream, factory);
	// } catch (UniProtParserException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// while (entryIterator.hasNext()) {
	// UniProtEntry entry = entryIterator.next();
	// entryList.add(entry);
	// }
	// return entryList;
	// }
	public List<UniProtEntry> loadEntry(InputStream inputStream) {
		this.entryList = new ArrayList<UniProtEntry>();
		BufferedReader br;
		StringBuffer entryBuffer = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = br.readLine()) != null) {
				entryBuffer.append(line + "\n");
				if (line.startsWith("//")) {
					UniProtEntry entry = UniProtFlatFileParser.parse(
							entryBuffer.toString(), factory);
					this.entryList.add(entry);
					entryBuffer = new StringBuffer();
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.entryList;
	}

	@Override
	public UniProtEntry getEntryByPrimaryAC(String entryPrimaryAC) {
		for (UniProtEntry entry : this.entryList) {
			if (entry.getPrimiaryAccessionNumber().equals(entryPrimaryAC)) {
				return entry;
			}
		}
		return null;
	}

	@Override
	public UniProtEntry getEntryById(String entryId) {
		for (UniProtEntry entry : this.entryList) {
			if (entry.getEntryId().equals(entryId)) {
				return entry;
			}
		}
		return null;
	}

	@Override
	public Alignment getAlignmentByEntryAndRule(String entryId, String ruleAC) {
		for (Alignment alignment : alignmentList) {
			if (alignment.getEntryId().equals(entryId)
					&& alignment.getRuleAC().equals(ruleAC)) {
				return alignment;
			}
		}
		return null;
	}

	@Override
	public PIRRuleManager getPIRRuleManager() {
		return this.pirRuleManager;
	}

	@Override
	public void propagate(String propagationDirectory) {
		String[] dataFiles = checkDataFiles(propagationDirectory);
		File ruleFile = new File(dataFiles[0]);
		List<PIRRule> ruleList = this.pirRuleManager.parsePIRRules(ruleFile,
				PIRRuleManager.Format.UNIRULE, false, debug, "Source");
		if (pirRuleManager.getParserLogs() != null
				&& pirRuleManager.getParserLogs().length() > 0) {
			System.err.println(pirRuleManager.getParserLogs());
			System.err.println("\"" + ruleFile
					+ "\" has syntax error, program quit.\n");
			System.exit(1);
		}
		// if (debug) {
		System.out.println("Total rules: " + ruleList.size());
		// }
		File dataFile = new File(dataFiles[1]);
		List<UniProtEntry> entryList = this.loadEntry(dataFile);

		// if (debug) {
		System.out.println("Total entries: " + entryList.size());
		// }
		File alignmentFile = new File(dataFiles[2]);
		List<Alignment> alignmentList = this.loadAlignment(alignmentFile);
		// if (debug) {
		System.out.println("Total alignments: " + alignmentList.size());

		checkTemplateInDatFile(ruleList, entryList);
		// }
		// Map<String, List<PropagationRecord>> propagationResultsMap = new
		// HashMap<String, List<PropagationRecord>>();
		// checkTemplate(ruleList, entryList);
		Map<String, Alignment> alignmentMap = new HashMap<String, Alignment>();
		for (Alignment alignment : alignmentList) {
			alignmentMap.put(alignment.getEntryId()+"_"+alignment.getRuleAC(), alignment);
		}
		for (PIRRule rule : ruleList) {
			// System.out.println(rule.getHeaderSection().getACLine().getAccessionNumber());
			String ruleAC = rule.getRuleAC();
			String templateAC = rule.getTemplateAC();
			
			List<PropagationReport> propagationReports = new ArrayList<PropagationReport>();
			int swissProtEntryCount = 0;
			int tremblEntryCount = 0;
			long count = 0;
			for (UniProtEntry entry : entryList) {
				count++;
				List<PropagationRecord> propagationResults = new ArrayList<PropagationRecord>();
				String entryID = entry.getEntryId();
				// System.out.println(entryID);

				//Alignment alignment = this.getAlignmentByEntryAndRule(entryID,
				//		ruleAC);
				Alignment alignment = alignmentMap.get(entryID+"_"+ruleAC);
				String entryAC = entry.getPrimiaryAccessionNumber();
				if (entry.getEntryType().equals(EntryType.SWISSPROT)) {
					swissProtEntryCount++;
				}
				if (entry.getEntryType().equals(EntryType.TREMBL)) {
					tremblEntryCount++;
					// System.out.println(tremblEntryCount + ": ??" +
					// entry.getPrimiaryAccessionNumber());
				}
				String note = "";
				if (!PropagationUtil.checkTrigger(rule, entry)) {
					note += "Trigger does not match; ";
				}
				if (!PropagationUtil.checkTemplate(alignment, rule)) {
					note += "Template is missing in alignment; ";
				}
				PIRRuleUtil pirruleUtil = this.pirRuleManager.getDataFactory()
						.buidPIRRuleUtil(rule);
				List<ControlBlock> scopeControlBlocks = pirruleUtil
						.getScopeBlockControlBlocks();
				if (scopeControlBlocks == null
						|| scopeControlBlocks.size() == 0) {
					if (!PropagationUtil.checkNonControlBlockScope(rule, entry)) {
						note += "Taxonomic scope does not match; ";
					}
				}
				if (note.length() > 0) {
					PropagationRecord propagationRecord = this.propagateionDataFactory
							.buildPropagationRecord();
					EntryAnnotation entryAnnotation = this.propagateionDataFactory
							.buildEntryAnnotation(entryAC, entryID,
									entry.getEntryType());
					RuleAnnotation ruleAnnotation = this.propagateionDataFactory
							.buildRuleAnnotation(ruleAC, templateAC);
					propagationRecord.setEntryAnnotation(entryAnnotation);
					propagationRecord.setRuleAnnotation(ruleAnnotation);
					propagationRecord.setNote(note);
					propagationRecord.setPropagated(false);
					propagationRecord
							.setPropagationStatistics(PropagationStats.NA);
					long startTime = System.currentTimeMillis();
					propagationResults.add(propagationRecord);
					long estimatedTime = System.currentTimeMillis() - startTime;
					//System.out.println("Propagate " + entryAC + ": " + estimatedTime);
				} else {
					// propagationResults.addAll(propagate(alignment, entry,
					// rule));
					long startTime = System.currentTimeMillis();
					propagationResults.addAll(propagateEntry(alignment, entry,
							rule));
					long estimatedTime = System.currentTimeMillis() - startTime;
					//System.out.println("Propagte and add results " + entryAC + ": " + estimatedTime);
				}
				propagationReports.add(generateReport(entry, rule,
						propagationResults));
				//if(debug) {
					if(count % 10000 == 0) {
						System.out.println(new Timestamp(new java.util.Date().getTime()) + " Propagate: " + count);
					}
				//}
			}
			printReports(rule, propagationReports, propagationDirectory,
					swissProtEntryCount, tremblEntryCount);
			//if(debug) {
			System.out.println(new Timestamp(new java.util.Date().getTime()) + " Propagate: " + count);
					
			//}
			// if (debug) {
			// for (PropagationRecord propagationRecord : propagationResults) {
			//
			// System.out.println(propagationRecord.toString());
			// }
			// System.out.println("Total: " + propagationResults.size());
			// }
		}
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	private void checkTemplateInDatFile(List<PIRRule> ruleList,
			List<UniProtEntry> entryList) {
		for (PIRRule rule : ruleList) {
			String templateAC = rule.getTemplateAC();
			boolean found = false;
			for (UniProtEntry entry : entryList) {
				if (entry.getPrimiaryAccessionNumber().equals(templateAC)) {
					found = true;
				}
			}
			if (!found) {
				System.err.println("Template entry " + templateAC
						+ " for rule " + rule.getRuleAC()
						+ " does not exist in .dat file, program quit.\n");
				System.exit(1);
			}

		}
	}



	
	private void printReports(PIRRule rule,
			List<PropagationReport> propagationReports,
			String propagationDirectory, int swissProtEntryCount,
			int tremblEntryCount) {
		String ruleAC = rule.getRuleAC();
		int truePositiveCount = 0;
		int falsePositiveCount = 0;
		int falseNegativeCount = 0;
		StringBuffer swissProtTruePositiveAnnotations = new StringBuffer();
		StringBuffer swissProtFalsePositiveAnnotations = new StringBuffer();
		StringBuffer swissProtFalseNegativeAnnotations = new StringBuffer();

		StringBuffer tremblTruePositiveAnnotations = new StringBuffer();
		StringBuffer tremblFalsePositiveAnnotations = new StringBuffer();
		StringBuffer tremblFalseNegativeAnnotations = new StringBuffer();

		StringBuffer otherAnnotations = new StringBuffer();
		StringBuffer finalSwissProtKrakenAnnotations = new StringBuffer();
		StringBuffer finalTremblKrakenAnnotations = new StringBuffer();
		String swissProtTruePositiveAnnotationsFile = propagationDirectory
				+ System.getProperty("file.separator") + ruleAC + "_SP_TP.txt";
		writeToFile(swissProtTruePositiveAnnotationsFile,
				swissProtTruePositiveAnnotations.toString());
		String swissProtFalsePositiveAnnotationsFile = propagationDirectory
				+ System.getProperty("file.separator") + ruleAC + "_SP_FP.txt";
		writeToFile(swissProtFalsePositiveAnnotationsFile,
				swissProtFalsePositiveAnnotations.toString());
		String swissProtFalseNegativeAnnotationsFile = propagationDirectory
				+ System.getProperty("file.separator") + ruleAC + "_SP_FN.txt";
		writeToFile(swissProtFalseNegativeAnnotationsFile,
				swissProtFalseNegativeAnnotations.toString());

		String tremblTruePositiveAnnotationsFile = propagationDirectory
				+ System.getProperty("file.separator") + ruleAC + "_TR_TP.txt";
		writeToFile(tremblTruePositiveAnnotationsFile,
				tremblTruePositiveAnnotations.toString());
		String tremblFalsePositiveAnnotationsFile = propagationDirectory
				+ System.getProperty("file.separator") + ruleAC + "_TR_FP.txt";
		writeToFile(tremblFalsePositiveAnnotationsFile,
				tremblFalsePositiveAnnotations.toString());
		String tremblFalseNegativeAnnotationsFile = propagationDirectory
				+ System.getProperty("file.separator") + ruleAC + "_TR_FN.txt";
		writeToFile(tremblFalseNegativeAnnotationsFile,
				tremblFalseNegativeAnnotations.toString());

		String otherAnnotationsFile = propagationDirectory
				+ System.getProperty("file.separator") + ruleAC + "_others.txt";
		writeToFile(otherAnnotationsFile, otherAnnotations.toString());

		String swissProtKrakenFile = propagationDirectory
				+ System.getProperty("file.separator") + ruleAC
				+ "_SP_kraken.txt";
		writeToFile(swissProtKrakenFile,
				finalSwissProtKrakenAnnotations.toString());

		String tremblKrakenFile = propagationDirectory
				+ System.getProperty("file.separator") + ruleAC
				+ "_TR_kraken.txt";
		writeToFile(tremblKrakenFile, finalTremblKrakenAnnotations.toString());
		String summaryFile = propagationDirectory
				+ System.getProperty("file.separator") + ruleAC
				+ "_summary.txt";
		writeToFile(summaryFile, "");

		List<PropagationRecord> allSwissProtKrakenRecords = new ArrayList<PropagationRecord>();
		List<PropagationRecord> allTremblKrakenRecords = new ArrayList<PropagationRecord>();
		int count = 0;
		for (PropagationReport report : propagationReports) {
			count++;
			truePositiveCount += report.getSwissProtTruePositiveCount();
			falsePositiveCount += report.getSwissProtFalsePositiveCount();
			falseNegativeCount += report.getSwissProtFalseNegativeCount();
			List<PropagationRecord> swissProtkrakenRecords = report
					.getSwissProtKrakenRecord();
			if (swissProtkrakenRecords != null
					&& swissProtkrakenRecords.size() > 0) {
				allSwissProtKrakenRecords.addAll(swissProtkrakenRecords);
			}

			List<PropagationRecord> tremblkrakenRecords = report
					.getTremblKrakenRecord();
			if (tremblkrakenRecords != null && tremblkrakenRecords.size() > 0) {
				allTremblKrakenRecords.addAll(tremblkrakenRecords);
			}
			if (report.getOtherAnnotations() != null
					&& report.getOtherAnnotations().length() > 0) {
				otherAnnotations.append(report.getOtherAnnotations() + "\n");
				if(count % 100000 == 0) {
					appendToFile(otherAnnotationsFile, otherAnnotations.toString());
					otherAnnotations.setLength(0);
				}
			}
			if (report.getSwissProtTruePositiveAnnotations() != null
					&& report.getSwissProtTruePositiveAnnotations().length() > 0) {
				swissProtTruePositiveAnnotations.append(report
						.getSwissProtTruePositiveAnnotations() + "\n");
				if(count % 100000 == 0) {
					appendToFile(swissProtTruePositiveAnnotationsFile, swissProtTruePositiveAnnotations.toString());
					swissProtTruePositiveAnnotations.setLength(0);
				}
			}
			if (report.getSwissProtFalsePositiveAnnotations() != null
					&& report.getSwissProtFalsePositiveAnnotations().length() > 0) {
				swissProtFalsePositiveAnnotations.append(report
						.getSwissProtFalsePositiveAnnotations() + "\n");
				if(count % 100000 == 0) {
					appendToFile(swissProtFalsePositiveAnnotationsFile, swissProtFalsePositiveAnnotations.toString());
					swissProtFalsePositiveAnnotations.setLength(0);
				}
			}
			if (report.getSwissProtFalseNegativeAnnotations() != null
					&& report.getSwissProtFalseNegativeAnnotations().length() > 0) {
				swissProtFalseNegativeAnnotations.append(report
						.getSwissProtFalseNegativeAnnotations() + "\n");
				if(count % 100000 == 0) {
					appendToFile(swissProtFalseNegativeAnnotationsFile, swissProtFalseNegativeAnnotations.toString());
					swissProtFalseNegativeAnnotations.setLength(0);
				}
			}

			if (report.getTremblTruePositiveAnnotations() != null
					&& report.getTremblTruePositiveAnnotations().length() > 0) {
				tremblTruePositiveAnnotations.append(report
						.getTremblTruePositiveAnnotations() + "\n");
				if(count % 100000 == 0) {
					appendToFile(tremblTruePositiveAnnotationsFile, tremblTruePositiveAnnotations.toString());
					tremblTruePositiveAnnotations.setLength(0);
				}
			}
			if (report.getTremblFalsePositiveAnnotations() != null
					&& report.getTremblFalsePositiveAnnotations().length() > 0) {
				tremblFalsePositiveAnnotations.append(report
						.getTremblFalsePositiveAnnotations() + "\n");
				if(count % 100000 == 0) {
					appendToFile(tremblFalsePositiveAnnotationsFile, tremblFalsePositiveAnnotations.toString());
					tremblFalsePositiveAnnotations.setLength(0);
				}
			}
			if (report.getTremblFalseNegativeAnnotations() != null
					&& report.getTremblFalseNegativeAnnotations().length() > 0) {
				tremblFalseNegativeAnnotations.append(report
						.getTremblFalseNegativeAnnotations() + "\n");
				if(count % 100000 == 0) {
					appendToFile(tremblFalseNegativeAnnotationsFile, tremblFalseNegativeAnnotations.toString());
					tremblFalseNegativeAnnotations.setLength(0);
				}
			}
		}
		appendToFile(otherAnnotationsFile, otherAnnotations.toString());
		appendToFile(swissProtTruePositiveAnnotationsFile, swissProtTruePositiveAnnotations.toString());
		appendToFile(swissProtFalsePositiveAnnotationsFile, swissProtFalsePositiveAnnotations.toString());
		appendToFile(swissProtFalseNegativeAnnotationsFile, swissProtFalseNegativeAnnotations.toString());
		appendToFile(tremblTruePositiveAnnotationsFile, tremblTruePositiveAnnotations.toString());
		appendToFile(tremblFalsePositiveAnnotationsFile, tremblFalsePositiveAnnotations.toString());
		appendToFile(tremblFalseNegativeAnnotationsFile, tremblFalseNegativeAnnotations.toString());
		
		otherAnnotations.setLength(0);
		swissProtTruePositiveAnnotations.setLength(0);
		swissProtFalsePositiveAnnotations.setLength(0);
		swissProtFalseNegativeAnnotations.setLength(0);
		tremblTruePositiveAnnotations.setLength(0);
		tremblFalsePositiveAnnotations.setLength(0);
		tremblFalseNegativeAnnotations.setLength(0);
		
		double z = 1.96;
		int n = truePositiveCount + falsePositiveCount;

		
		double p = (truePositiveCount + 0.0) / n;
		// System.out.println("p = " + truePositiveCount + "/" + n);

		
		double c = (p + z * z / (2 * n) - z
				* Math.sqrt(p / n - p * p / n + z * z / (4 * n * n)))
				/ (1 + z * z / n);

		List<PropagationRecord> finalSwissProtKrakenRecords = addPrecisionAndConfidence(
				allSwissProtKrakenRecords, p, c);
		List<String> swissProtKrakenACs = new ArrayList<String>();
		int swissProtKrakenAnnotationCount = 0;

		

		for (PropagationRecord swissProtKrakenRecord : finalSwissProtKrakenRecords) {
			if (!swissProtKrakenACs.contains(swissProtKrakenRecord
					.getEntryAnnotation().getEntryAC())) {
				swissProtKrakenACs.add(swissProtKrakenRecord
						.getEntryAnnotation().getEntryAC());
			}
			swissProtKrakenAnnotationCount++;
			finalSwissProtKrakenAnnotations.append(swissProtKrakenRecord
					.toKraken() + "\n");
			if(swissProtKrakenAnnotationCount % 100000 == 0) {
				
				appendToFile(swissProtKrakenFile, finalSwissProtKrakenAnnotations.toString());
				finalSwissProtKrakenAnnotations.setLength(0);;
				System.out.println(new Timestamp(new java.util.Date().getTime()) + " Writing SwissProt Kraken: " + swissProtKrakenAnnotationCount);
			}
		}
		appendToFile(swissProtKrakenFile, finalSwissProtKrakenAnnotations.toString());
		System.out.println(new Timestamp(new java.util.Date().getTime()) +" Writing SwissProt Kraken: " + swissProtKrakenAnnotationCount);
		finalSwissProtKrakenAnnotations.setLength(0);
		
		int swissProtKrakenACCount = swissProtKrakenACs.size();

		List<PropagationRecord> finalTremblKrakenRecords = addPrecisionAndConfidence(
				allTremblKrakenRecords, p, c);
		List<String> tremblKrakenACs = new ArrayList<String>();
		int tremblKrakenAnnotationCount = 0;

		

		for (PropagationRecord tremblKrakenRecord : finalTremblKrakenRecords) {
			if (!tremblKrakenACs.contains(tremblKrakenRecord
					.getEntryAnnotation().getEntryAC())) {
				tremblKrakenACs.add(tremblKrakenRecord.getEntryAnnotation()
						.getEntryAC());
			}
			tremblKrakenAnnotationCount++;
			finalTremblKrakenAnnotations.append(tremblKrakenRecord.toKraken()
					+ "\n");
			if(tremblKrakenAnnotationCount % 100000 == 0) {
				appendToFile(tremblKrakenFile, finalTremblKrakenAnnotations.toString());
				finalTremblKrakenAnnotations.setLength(0);;
				System.out.println(new Timestamp(new java.util.Date().getTime()) + " Writing TrEMBL Kraken: " + tremblKrakenAnnotationCount);
			}
		}
		appendToFile(tremblKrakenFile, finalTremblKrakenAnnotations.toString());
		System.out.println(new Timestamp(new java.util.Date().getTime()) +" Writing TrEMBL Kraken: " + tremblKrakenAnnotationCount);
		finalTremblKrakenAnnotations.setLength(0);
		
		int tremblKrakenACCount = tremblKrakenACs.size();

		if (debug) {
			System.out.println("Total swissprot entries: "
					+ swissProtKrakenACCount);
			System.out.println("Total swissprot annotations: "
					+ swissProtKrakenAnnotationCount);
			for (PropagationRecord krakenRecord : finalSwissProtKrakenRecords) {
				System.out.println(krakenRecord.toKraken());
			}
			System.out.println("\n");
			System.out.println("Total trembl entries: " + tremblKrakenACCount);
			System.out.println("Total trembl annotations: "
					+ tremblKrakenAnnotationCount);
			for (PropagationRecord krakenRecord : finalTremblKrakenRecords) {
				System.out.println(krakenRecord.toKraken());
			}
		}

		String summary = "";
		summary += ruleAC + "\t" + truePositiveCount + "\t"
				+ falsePositiveCount + "\t" + "0\t" + falseNegativeCount + "\t"
				+ (truePositiveCount + falsePositiveCount) + "\t"
				+ String.format("%.2f", p) + "\t" + String.format("%.2f", c)
				+ "\t" + swissProtEntryCount + "\t" + tremblEntryCount + "\t"
				+ (swissProtEntryCount + tremblEntryCount) + "\t"
				+ swissProtKrakenACCount + "\t" + tremblKrakenACCount+"\t"
				+ (swissProtKrakenACCount + tremblKrakenACCount);
				//+ tremblKrakenACCount;
		
		System.out.println("\n" + summary + "\n");
		
		appendToFile(summaryFile, summary+"\n");
		
	}
	

	private void writeToFile(String file, String content) {
		FileWriter fw;
		try {
			fw = new FileWriter(file);

			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void appendToFile(String file, String content) {
		FileWriter fw;
		try {
			fw = new FileWriter(file, true);

			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<PropagationRecord> addPrecisionAndConfidence(
			List<PropagationRecord> allKrakenRecords, double precision,
			double confidence) {
		List<PropagationRecord> finalKrakenRecords = new ArrayList<PropagationRecord>();
		for (PropagationRecord propagationRecord : allKrakenRecords) {
			propagationRecord.setPrecision(precision);
			propagationRecord.setConfidence(confidence);
			finalKrakenRecords.add(propagationRecord);
		}
		return finalKrakenRecords;
	}

	private PropagationReport generateReport(UniProtEntry entry, PIRRule rule,
			List<PropagationRecord> propagationResults) {
		String header = ">" + entry.getPrimiaryAccessionNumber() + " ";
		header += entry.getEntryId() + " | ";
		// header += (entry.getType().equals(UniProtEntryType.SWISSPROT) ? "sp"
		// : "tr") + " | ";
		header += entry.getEntryType() + " | ";
		header += rule.getRuleAC() + " | " + rule.getTrigger() + " | "
				+ rule.getTemplateAC();
		if (debug) {
			System.out.println(header);
		}

		List<String> swissProtTruePositiveAnnotations = new ArrayList<String>();
		List<String> swissProtFalsePositiveAnnotations = new ArrayList<String>();
		List<String> swissProtFalseNegativeAnnotations = new ArrayList<String>();
		List<String> tremblKrakenAnnotations = new ArrayList<String>();
		List<String> swissProtKrakenAnnotations = new ArrayList<String>();
		List<String> otherAnnotations = new ArrayList<String>();

		StringBuffer swissProtTruePositivesBuffer = new StringBuffer();
		StringBuffer swissProtFalsePositivesBuffer = new StringBuffer();
		StringBuffer swissProtFalseNegativesBuffer = new StringBuffer();
		StringBuffer tremblTruePositivesBuffer = new StringBuffer();
		StringBuffer tremblFalsePositivesBuffer = new StringBuffer();
		StringBuffer tremblFalseNegativesBuffer = new StringBuffer();

		List<PropagationRecord> tremblKrakenReports = new ArrayList<PropagationRecord>();
		List<PropagationRecord> swissProtKrakenReports = new ArrayList<PropagationRecord>();

		StringBuffer otherAnnotationsBuffer = new StringBuffer();

		// String truePositivesFilePath = propagationDirectory +
		// System.getProperty("file.separator")+ rule.getRuleAC()+"_TP.txt";
		for (PropagationRecord propagationRecord : propagationResults) {

			EntryAnnotation entryAnnotation = propagationRecord
					.getEntryAnnotation();
			RuleAnnotation ruleAnnotation = propagationRecord
					.getRuleAnnotation();
			// if (entryAnnotation != null && ruleAnnotation != null) {
			if (debug) {
				System.out.println(propagationRecord);
				// if(entryAnnotation.getEntryType().equals(EntryType.TREMBL)) {
				// System.out.println(propagationRecord.getNote());
				// }
			}
			if (propagationRecord.getPropagationStats().equals(
					PropagationStats.TP)) {
				if (entryAnnotation.getEntryType().equals(EntryType.SWISSPROT)) {
					swissProtTruePositivesBuffer.append(propagationRecord
							.toReport() + "\n");
					if (!swissProtTruePositiveAnnotations
							.contains(entryAnnotation.toReport() + "|"
									+ ruleAnnotation.toReport())) {
						swissProtTruePositiveAnnotations.add(entryAnnotation
								.toReport() + "|" + ruleAnnotation.toReport());
					}
				} else {
					tremblTruePositivesBuffer.append(propagationRecord
							.toReport() + "\n");
					if (propagationRecord.isPropagated()) {
						if (!tremblKrakenAnnotations.contains(entryAnnotation
								.toKraken(ruleAnnotation))) {
							tremblKrakenAnnotations.add(entryAnnotation
									.toKraken(ruleAnnotation));
							tremblKrakenReports.add(propagationRecord);
						}
					}
				}
			} else if (propagationRecord.getPropagationStats().equals(
					PropagationStats.FP)) {
				if (entryAnnotation.getEntryType().equals(EntryType.SWISSPROT)) {
					swissProtFalsePositivesBuffer.append(propagationRecord
							.toReport() + "\n");
					if (!swissProtFalsePositiveAnnotations
							.contains(entryAnnotation.toReport() + "|"
									+ ruleAnnotation.toReport())) {
						swissProtFalsePositiveAnnotations.add(entryAnnotation
								.toReport() + "|" + ruleAnnotation.toReport());
					}
					if (propagationRecord.isPropagated()) {
						if (!swissProtKrakenAnnotations
								.contains(entryAnnotation
										.toKraken(ruleAnnotation))) {
							swissProtKrakenAnnotations.add(entryAnnotation
									.toKraken(ruleAnnotation));
							swissProtKrakenReports.add(propagationRecord);
						}
					}
				} else {
					tremblFalsePositivesBuffer.append(propagationRecord
							.toReport() + "\n");
					if (propagationRecord.isPropagated()) {
						if (!tremblKrakenAnnotations.contains(entryAnnotation
								.toKraken(ruleAnnotation))) {
							tremblKrakenAnnotations.add(entryAnnotation
									.toKraken(ruleAnnotation));
							tremblKrakenReports.add(propagationRecord);
						}
					}
				}
			} else if (propagationRecord.getPropagationStats().equals(
					PropagationStats.FN)) {
				if (entryAnnotation.getEntryType().equals(EntryType.SWISSPROT)) {
					swissProtFalseNegativesBuffer.append(propagationRecord
							.toReport() + "\n");
					if (!swissProtFalseNegativeAnnotations
							.contains(entryAnnotation.toReport() + "|"
									+ ruleAnnotation.toReport())) {
						swissProtFalseNegativeAnnotations.add(entryAnnotation
								.toReport() + "|" + ruleAnnotation.toReport());
					}
				} else {
					tremblFalseNegativesBuffer.append(propagationRecord
							.toReport() + "\n");
				}
			} else {
				if (!otherAnnotations.contains(propagationRecord.toReport())) {
					otherAnnotations.add(propagationRecord.toReport());
					otherAnnotationsBuffer.append(propagationRecord.toReport()
							+ "\n");
				}
			}
		}

		PropagationReport report = new PropagationReport();

		report.setTremblKrakenRecord(tremblKrakenReports);
		report.setSwissProtKrakenRecord(swissProtKrakenReports);
		if (otherAnnotationsBuffer.toString().length() > 0) {
			report.setOtherAnnotations(header + "\n"
					+ otherAnnotationsBuffer.toString());
		}
		if (swissProtFalseNegativesBuffer.toString().length() > 0) {
			report.setSwissProtFalseNegativeAnnotations(header + "\n"
					+ swissProtFalseNegativesBuffer.toString());
			if (debug) {
				System.out.println("FN: " + swissProtFalseNegativeAnnotations);
			}
		}
		report.setSwissProtFalseNegativeCount(swissProtFalseNegativeAnnotations
				.size());
		if (swissProtFalsePositivesBuffer.toString().length() > 0) {
			report.setSwissProtFalsePositiveAnnotations(header + "\n"
					+ swissProtFalsePositivesBuffer.toString());
			if (debug) {
				System.out.println("FP: " + swissProtFalsePositiveAnnotations);
			}
		}
		report.setSwissProtFalsePositiveCount(swissProtFalsePositiveAnnotations
				.size());
		if (swissProtTruePositivesBuffer.toString().length() > 0) {
			report.setSwissProtTruePositiveAnnotations(header + "\n"
					+ swissProtTruePositivesBuffer.toString());
			report.setSwissProtTruePositiveCount(swissProtTruePositiveAnnotations
					.size());
			if (debug) {
				System.out.println("TP: " + swissProtTruePositiveAnnotations);
			}
		}
		if (tremblFalseNegativesBuffer.toString().length() > 0) {
			report.setTremblFalseNegativeAnnotations(header + "\n"
					+ tremblFalseNegativesBuffer.toString());
		}

		if (tremblFalsePositivesBuffer.toString().length() > 0) {
			report.setTremblFalsePositiveAnnotations(header + "\n"
					+ tremblFalsePositivesBuffer.toString());
		}
		if (tremblTruePositivesBuffer.toString().length() > 0) {
			report.setTremblTruePositiveAnnotations(header + "\n"
					+ tremblTruePositivesBuffer.toString());
		}

		if (debug) {
			System.out.println(entry.getPrimiaryAccessionNumber() + "\t"
					+ swissProtTruePositiveAnnotations.size() + "\t"
					+ swissProtFalsePositiveAnnotations.size() + "\t"
					+ swissProtFalseNegativeAnnotations.size() + "\n");
		}

		return report;
	}

	private List<PropagationRecord> propagateEntry(Alignment alignment,
			UniProtEntry entry, PIRRule rule) {
		long startTime = System.currentTimeMillis();
		// ... do something ...
		
		// Map<Integer, Boolean> ftGroupMatches =
		// PropagationUtil.checkFTGroupMatch(entry, alignment, rule);
		FTGroupMatch ftGroupMatchResult = PropagationUtil.getFTGroupMatch(
				entry, alignment, rule);
		Map<Integer, Boolean> ftGroups = ftGroupMatchResult.getFtGroups();
		String ftGroupMatchNote = ftGroupMatchResult.getNote();
		ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator(
				entry, rule, ftGroups);

		List<PropagationRecord> entryPropagationResults = new ArrayList<PropagationRecord>();
		if (debug) {
			System.out.println("\n" + entry.getPrimiaryAccessionNumber()
					+ " | " + entry.getEntryId() + " | " + entry.getEntryType()
					+ " | " + rule.getRuleAC() + " | " + rule.getTrigger()
					+ " | " + rule.getTemplateAC());
		}
		// Map<Integer, Boolean> ftGroups =
		// PropagationUtil.checkFTGroupMatch(entry, alignment, rule);
		// String ftGroupMatchNote = PropagationUtil.getFTGroupMatchNote(entry,
		// alignment, rule);

		if (debug) {
			System.out.println(entry.getPrimiaryAccessionNumber() + "| "
					+ entry.getEntryId() + " | " + ftGroups + "\n\n");
		}
		List<ControlBlock> controlBlocks = rule.getControlBlocks();
		if (controlBlocks != null) {
			for (ControlBlock controlBlock : controlBlocks) {
				List<ControlStatement> controlStatements = controlBlock
						.getControlStatements();
				for (ControlStatement controlStatement : controlStatements) {
					if (!(controlStatement instanceof EndCaseStatement)) {
						ExpressionValue controlStatementEval = expressionEvaluator
								.evaluateExpression(controlStatement
										.getExpression());
						// System.out.println(entry.getPrimiaryAccessionNumber()
						// + " | "+controlStatement + " | " +
						// controlStatementEval);
						List<Line> applicableLines = controlStatement
								.getApplicableLines();
						PropagationRecord propagationRecord = null;
						for (Line ruleLine : applicableLines) {
							if (ruleLine instanceof CCLine) {
								// System.out.println(ruleLine);
								startTime = System.currentTimeMillis();
								propagationRecord = propagateCCLine(alignment,
										entry, rule, ruleLine,
										controlStatement, controlStatementEval,
										ftGroupMatchResult);
								long estimatedTime = System.currentTimeMillis() - startTime;
								//System.out.println("CCLine: " + estimatedTime);
							} else if (ruleLine instanceof FeatureDescriptionLine) {
								startTime = System.currentTimeMillis();
								propagationRecord = propagateFeatureDescriptionLine(
										alignment, entry, rule, ruleLine,
										controlStatement, controlStatementEval,
										ftGroupMatchNote, ftGroups);
								long estimatedTime = System.currentTimeMillis() - startTime;
								//System.out.println("FTLine: " + ruleLine + " "+ estimatedTime);
							} else if (ruleLine instanceof KWLine) {
								// System.out.println(ruleLine);
								startTime = System.currentTimeMillis();
								propagationRecord = propagateKWLine(alignment,
										entry, rule, ruleLine,
										controlStatement, controlStatementEval,
										ftGroupMatchResult);
								long estimatedTime = System.currentTimeMillis() - startTime;
								//System.out.println("KWLine: " + estimatedTime);
							}
							if (propagationRecord != null) {

								entryPropagationResults.add(propagationRecord);
							}
						}
					}
				}
			}
		}

		entryPropagationResults = checkFalseNegatives(entry, rule, alignment,
				entryPropagationResults);
		long estimatedTime = System.currentTimeMillis() - startTime;
		//System.out.println("Real propagate " + entry.getPrimiaryAccessionNumber() + ": " + estimatedTime);
		return entryPropagationResults;
	}

	private List<PropagationRecord> checkFalseNegatives(UniProtEntry entry,
			PIRRule rule, Alignment alignment,
			List<PropagationRecord> entryPropagationResults) {
		List<PropagationRecord> entryPropagationResultsAfterCheckFalseNegatives = checkKeywordFalseNegatives(
				entry, rule, entryPropagationResults);

		entryPropagationResultsAfterCheckFalseNegatives = checkCommentFalseNegatives(
				entry, rule, entryPropagationResultsAfterCheckFalseNegatives);

		entryPropagationResultsAfterCheckFalseNegatives = checkFeatureTableFalseNegatives(
				entry, rule, entryPropagationResultsAfterCheckFalseNegatives);

		return entryPropagationResultsAfterCheckFalseNegatives;
	}

	private List<PropagationRecord> checkKeywordFalseNegatives(
			UniProtEntry entry, PIRRule rule,
			List<PropagationRecord> entryPropagationResults) {
		List<PropagationRecord> entryPropagationResultsAfterCheckFalseNegatives = entryPropagationResults;

		List<EntryKeyword> propagatedKeywordList = new ArrayList<EntryKeyword>();

		for (PropagationRecord propagationRecord : entryPropagationResults) {
			if (propagationRecord.getEntryAnnotation() != null) {
				if (propagationRecord.getEntryAnnotation() instanceof EntryKeywordAnnotation) {
					EntryKeyword keyword = ((EntryKeywordAnnotation) propagationRecord
							.getEntryAnnotation()).getKeyword();
					if (keyword != null) {
						propagatedKeywordList.add(keyword);
					}
				}
			}
		}
		List<EntryKeyword> entryKeywordList = entry.getKeywords();
		for (EntryKeyword keyword : entryKeywordList) {
			if (!propagatedKeywordList.contains(keyword)
					&& ruleHasKeyword(rule, keyword)) {

				String entryAC = entry.getPrimiaryAccessionNumber();
				String entryID = entry.getEntryId();
				PropagationRecord propagationRecord = this.propagateionDataFactory
						.buildPropagationRecord();
				RuleKeywordAnnotation ruleKeywordAnnotation = this.propagateionDataFactory
						.buildRuleKeywordAnnotation(rule.getRuleAC(),
								rule.getTemplateAC(), null);
				EntryKeywordAnnotation entryKeywordAnnotation = this.propagateionDataFactory
						.buildEntryKeywordAnnotation(entryAC, entryID,
								entry.getEntryType(), keyword);
				propagationRecord.setEntryAnnotation(entryKeywordAnnotation);
				propagationRecord.setRuleAnnotation(ruleKeywordAnnotation);
				propagationRecord.setPropagated(false);
				propagationRecord.setPropagationStatistics(PropagationStats.FN);
				if (propagationRecord.getNote() != null) {
					propagationRecord
							.setNote((propagationRecord.getNote() + " Annotation exists in the entry, but is not predicated; ")
									.trim());
				} else {
					propagationRecord
							.setNote("Annotation exists in the entry, but is not predicated; ");
				}
				entryPropagationResultsAfterCheckFalseNegatives
						.add(propagationRecord);
			}
		}

		return entryPropagationResultsAfterCheckFalseNegatives;
	}

	private boolean ruleHasKeyword(PIRRule rule, EntryKeyword keyword) {
		for (Line line : rule.getAnnotationSection().getLines()) {
			if (line instanceof KWLine) {
				KWLine kwLine = (KWLine) line;
				if (kwLine.getKeyword().equals(keyword.getKeyword())) {
					return true;
				}
			}
		}
		return false;
	}

	private List<PropagationRecord> checkFeatureTableFalseNegatives(
			UniProtEntry entry, PIRRule rule,
			List<PropagationRecord> entryPropagationResults) {
		List<PropagationRecord> entryPropagationResultsAfterCheckFalseNegatives = entryPropagationResults;

		List<EntryFeatureTable> propagatedFeatureList = new ArrayList<EntryFeatureTable>();

		for (PropagationRecord propagationRecord : entryPropagationResults) {
			if (propagationRecord.getEntryAnnotation() != null) {
				if (propagationRecord.getEntryAnnotation() instanceof EntryFeatureTableAnnotation) {
					EntryFeatureTable feature = ((EntryFeatureTableAnnotation) propagationRecord
							.getEntryAnnotation()).getFeature();
					if (feature != null) {
						propagatedFeatureList.add(feature);
					}
				}
			}
		}
		// System.out.println(entry.getPrimiaryAccessionNumber() + " | " +
		// propagatedFeatureList);

		List<EntryFeatureTable> entryFeatureList = entry.getFeatures();
		// System.out.println("???"+entry.getPrimiaryAccessionNumber() +
		// " | "+entryFeatureList);
		for (EntryFeatureTable feature : entryFeatureList) {
			if (!propagatedFeatureList.contains(feature)
					&& ruleHasFeatureDescription(rule, feature)) {

				String entryAC = entry.getPrimiaryAccessionNumber();
				String entryID = entry.getEntryId();
				PropagationRecord propagationRecord = this.propagateionDataFactory
						.buildPropagationRecord();
				RuleFeatureTableAnnotation ruleFeatureTableAnnotation = this.propagateionDataFactory
						.buildRuleFeatureTableAnnotation(rule.getRuleAC(),
								rule.getTemplateAC(), null);
				EntryFeatureTableAnnotation entryFeatureTableAnnotation = this.propagateionDataFactory
						.buildEntryFeatureTableAnnotation(entryAC, entryID,
								entry.getEntryType(), feature, "");

				// String note = PropagationUtil.getMatchInfo(entry, alignment,
				// ruleFeatureTableAnnotation.getFeatureDescriptionLine()) +
				// "<-|->";

				propagationRecord
						.setEntryAnnotation(entryFeatureTableAnnotation);
				propagationRecord.setRuleAnnotation(ruleFeatureTableAnnotation);
				propagationRecord.setPropagated(false);
				propagationRecord.setPropagationStatistics(PropagationStats.FN);
				if (propagationRecord.getNote() != null) {
					propagationRecord
							.setNote((propagationRecord.getNote() + " Annotation exists in the entry, but is not predicated<-|->")
									.trim());
				} else {
					propagationRecord
							.setNote("Annotation exists in the entry, but is not predicated<-|->");
				}

				entryPropagationResultsAfterCheckFalseNegatives
						.add(propagationRecord);
			}
		}

		return entryPropagationResultsAfterCheckFalseNegatives;
	}

	private boolean ruleHasFeatureDescription(PIRRule rule,
			EntryFeatureTable feature) {
		for (Line line : rule.getAnnotationSection().getLines()) {
			if (line instanceof FeatureDescriptionLine) {
				FeatureDescriptionLine featureDescriptionLine = (FeatureDescriptionLine) line;
				if (featureDescriptionLine.getFeatureKey().name()
						.equals(feature.getFeatureType().name())) {
					return true;
				}
			}
		}
		return false;
	}

	private List<PropagationRecord> checkCommentFalseNegatives(
			UniProtEntry entry, PIRRule rule,
			List<PropagationRecord> entryPropagationResults) {
		List<PropagationRecord> entryPropagationResultsAfterCheckFalseNegatives = entryPropagationResults;

		List<EntryComment> propagatedCommentList = new ArrayList<EntryComment>();

		for (PropagationRecord propagationRecord : entryPropagationResults) {
			if (propagationRecord.getEntryAnnotation() != null) {
				if (propagationRecord.getEntryAnnotation() instanceof EntryCommentAnnotation) {
					EntryComment comment = ((EntryCommentAnnotation) propagationRecord
							.getEntryAnnotation()).getComment();
					if (comment != null) {
						propagatedCommentList.add(comment);
					}
				}
			}
		}
		Collection<EntryComment> entryCommentList = entry.getComments();
		for (EntryComment comment : entryCommentList) {
			if (!propagatedCommentList.contains(comment)
					&& ruleHasComment(rule, comment)) {

				String entryAC = entry.getPrimiaryAccessionNumber();
				String entryID = entry.getEntryId();
				PropagationRecord propagationRecord = this.propagateionDataFactory
						.buildPropagationRecord();
				RuleCommentAnnotation ruleCommentAnnotation = this.propagateionDataFactory
						.buildRuleCommentAnnotation(rule.getRuleAC(),
								rule.getTemplateAC(), null);
				EntryCommentAnnotation entryCommentAnnotation = this.propagateionDataFactory
						.buildEntryCommentAnnotation(entryAC, entryID,
								entry.getEntryType(), comment);
				propagationRecord.setEntryAnnotation(entryCommentAnnotation);
				propagationRecord.setRuleAnnotation(ruleCommentAnnotation);
				propagationRecord.setPropagated(false);
				propagationRecord.setPropagationStatistics(PropagationStats.FN);
				if (propagationRecord.getNote() != null) {
					propagationRecord
							.setNote((propagationRecord.getNote() + " Annotation exists in the entry, but is not predicated; ")
									.trim());
				} else {
					propagationRecord
							.setNote("Annotation exists in the entry, but is not predicated; ");
				}

				entryPropagationResultsAfterCheckFalseNegatives
						.add(propagationRecord);
			}
		}

		return entryPropagationResultsAfterCheckFalseNegatives;
	}

	private boolean ruleHasComment(PIRRule rule, EntryComment comment) {
		for (Line line : rule.getAnnotationSection().getLines()) {
			if (line instanceof CCLine) {
				CCLine ccLine = (CCLine) line;
				if (ccLine.getTopic().name()
						.equals(comment.getCommentType().name())) {
					return true;
				}
			}
		}
		return false;
	}

	private PropagationRecord propagateFeatureDescriptionLine(
			Alignment alignment, UniProtEntry entry, PIRRule rule,
			Line ruleLine, ControlStatement controlStatement,
			ExpressionValue controlStatementEval, String ftGroupMatchNote,
			Map<Integer, Boolean> ftGroups) {
		PropagationRecord propagationRecord = this.propagateionDataFactory
				.buildPropagationRecord();
		propagationRecord.setControlStatement(controlStatement);
		propagationRecord.setControlStatementEvalution(controlStatementEval);

		String entryAC = entry.getPrimiaryAccessionNumber();
		String entryID = entry.getEntryId();
		EntryType entryType = entry.getEntryType();
		// boolean isSwissProt = entryType.equals(EntryType.SWISSPROT);

		String ruleAC = rule.getRuleAC();
		String templateAC = rule.getTemplateAC();

		String note = "";
		RuleFeatureTableAnnotation ruleFeatureTableAnnotation = this.propagateionDataFactory
				.buildRuleFeatureTableAnnotation(ruleAC, templateAC,
						(FeatureDescriptionLine) ruleLine);
		EntryFeatureTableAnnotation entryFeatureTableAnnotation = this.propagateionDataFactory
				.buildEntryFeatureTableAnnotation(entryAC, entryID, entryType,
						null, "");
		if(debug) {
			System.out.println(rule.getRuleAC() + " ftGroupOK: " + ftGroupOK(ftGroups) + ftGroups);
		}
		if (ftGroupOK(ftGroups)) {
			if (controlStatementEval.equals(ExpressionValue.TRUE)) {
				entryFeatureTableAnnotation = checkMatchedEntryFeature(
						alignment, entry, ruleFeatureTableAnnotation);
				if (entryFeatureTableAnnotation != null) {
					if (entryFeatureTableAnnotation.getMissmatchReason()
							.equals("")) {
						propagationRecord.setMatched(true);
						// if (isSwissProt) {
						// propagationRecord.setPropagated(false);
						// note += "Feature match<-|->";
						// propagationRecord.setPropagationStatistics(PropagationStats.TP);
						// } else {

						propagationRecord.setPropagated(true);
						note += "Feature match<-|->";
						propagationRecord
								.setPropagationStatistics(PropagationStats.TP);
						// }
					} else {
						note += PropagationUtil.getMatchInfo(entry, alignment,
								ruleFeatureTableAnnotation
										.getFeatureDescriptionLine())
								+ "; ";
						propagationRecord.setMatched(false);
						// if (isSwissProt) {
						// propagationRecord.setPropagated(false);
						// note += "No matched entry feature found<-|->" +
						// entryFeatureTableAnnotation.getMissmatchReason();
						// propagationRecord.setPropagationStatistics(PropagationStats.FP);
						// } else {
						propagationRecord.setPropagated(true);
						note += "Predicated feature<-|->";
						propagationRecord
								.setPropagationStatistics(PropagationStats.FP);
						// }
					}
				} else {
					if (PropagationUtil.checkFeaturePatternMatch(entry,
							alignment, ruleFeatureTableAnnotation
									.getFeatureDescriptionLine())) {
						EntryFeatureTable entryFeature = getEntryFeature(
								alignment, ruleFeatureTableAnnotation);

						entryFeatureTableAnnotation = this.propagateionDataFactory
								.buildEntryFeatureTableAnnotation(entryAC,
										entryID, entryType, entryFeature, "");

						note += "Predicated feature<-|->"
								+ entryFeatureTableAnnotation
										.getMissmatchReason();
						note += PropagationUtil.getMatchInfo(entry, alignment,
								ruleFeatureTableAnnotation
										.getFeatureDescriptionLine())
								+ "; ";

						if (note.contains("Template start or end residue does not match")) {
							propagationRecord
									.setPropagationStatistics(PropagationStats.NA);
							propagationRecord.setPropagated(false);
							propagationRecord.setMatched(false);
							propagationRecord.setNote(note);
						} else {
							if (!note
									.contains("Residue pattern does not match<-|->")) {
								propagationRecord
										.setPropagationStatistics(PropagationStats.FP);
								propagationRecord.setPropagated(true);
								propagationRecord.setMatched(false);
								propagationRecord.setNote(note);
							} else {
								propagationRecord.setPropagated(false);
								propagationRecord.setMatched(false);
								propagationRecord.setNote(note);
								propagationRecord
										.setPropagationStatistics(PropagationStats.NA);
							}
						}
					} else {

						EntryFeatureTable entryFeature = factory
								.buildEntryFeatureTable(EntryFeatureType
										.valueOf(ruleFeatureTableAnnotation
												.getFeatureType().name()), "",
										"");
						entryFeature.setFeatureDescription("");
						entryFeatureTableAnnotation = this.propagateionDataFactory
								.buildEntryFeatureTableAnnotation(entryAC,
										entryID, entryType, entryFeature, "");

						note += "No matched entry feature found<-|->"
								+ entryFeatureTableAnnotation
										.getMissmatchReason();
						note += PropagationUtil.getMatchInfo(entry, alignment,
								ruleFeatureTableAnnotation
										.getFeatureDescriptionLine())
								+ "; ";
						propagationRecord.setPropagated(false);
						propagationRecord.setMatched(false);
						propagationRecord
								.setPropagationStatistics(PropagationStats.NA);
					}
				}
			} else {
				propagationRecord.setPropagated(false);
				propagationRecord.setMatched(false);
				propagationRecord.setPropagationStatistics(PropagationStats.NA);
				note += "Control statement evaluted to be false, no matched feature<-|->"
						+ ftGroupMatchNote;
			}

			propagationRecord.setEntryAnnotation(entryFeatureTableAnnotation);
			propagationRecord.setRuleAnnotation(ruleFeatureTableAnnotation);
			propagationRecord.setNote(note.trim());

		} else {
			if(debug) {
				System.out.println("not ok "+entryFeatureTableAnnotation);
			}
			propagationRecord.setNote(ftGroupMatchNote);
			propagationRecord.setMatched(false);
			propagationRecord.setPropagated(false);
			propagationRecord.setPropagationStatistics(PropagationStats.NA);
			propagationRecord.setEntryAnnotation(entryFeatureTableAnnotation);
			propagationRecord.setRuleAnnotation(ruleFeatureTableAnnotation);
			propagationRecord.setNote(ftGroupMatchNote.trim());
		}
		return propagationRecord;
	}

	private boolean ftGroupOK(Map<Integer, Boolean> ftGroups) {
//		if (ftGroups.size() == 1 && ftGroups.get(1) == false) {
//			return false;
//		}
		for (Integer key : ftGroups.keySet()) {
		    if(ftGroups.get(key)) {
		    		return true;
		    }
		}
		return false;
	}

	private EntryFeatureTable getEntryFeature(Alignment alignment,
			RuleFeatureTableAnnotation ruleFeatureTableAnnotation) {
		EntryFeatureTable feature = null;

		FeatureDescriptionLine featureDescriptionLine = ruleFeatureTableAnnotation
				.getFeatureDescriptionLine();
		String ruleFeatureDescription = "";

		for (int i = 0; i < featureDescriptionLine.getDescriptions().size(); i++) {

			if (i == 0) {
				ruleFeatureDescription = featureDescriptionLine
						.getDescriptions().get(i).trim();
			} else {
				ruleFeatureDescription += " "
						+ featureDescriptionLine.getDescriptions().get(i)
								.trim();
			}
		}
		ruleFeatureDescription = ruleFeatureDescription.trim();
		String ruleFeatureStart = ruleFeatureTableAnnotation
				.getFeatureDescriptionLine().getFeatureFromPosition();
		String ruleFeatureEnd = ruleFeatureTableAnnotation
				.getFeatureDescriptionLine().getFeatureToPosition();
		String entryFeatureStart = PropagationUtil
				.convertTemplatePositionToTargetPosition(alignment,
						ruleFeatureStart);
		String entryFeatureEnd = PropagationUtil
				.convertTemplatePositionToTargetPosition(alignment,
						ruleFeatureEnd);

		feature = factory.buildEntryFeatureTable(EntryFeatureType
				.valueOf(ruleFeatureTableAnnotation.getFeatureType().name()),
				entryFeatureStart, entryFeatureEnd);
		feature.setFeatureDescription(ruleFeatureDescription);

		return feature;
	}

	private PropagationRecord propagateCCLine(Alignment alignment,
			UniProtEntry entry, PIRRule rule, Line ruleLine,
			ControlStatement controlStatement,
			ExpressionValue controlStatementEval,
			FTGroupMatch ftGroupMatchResult) {
		PropagationRecord propagationRecord = this.propagateionDataFactory
				.buildPropagationRecord();
		propagationRecord.setControlStatement(controlStatement);
		propagationRecord.setControlStatementEvalution(controlStatementEval);
		RuleCommentAnnotation ruleCommentAnnotation = null;
		EntryCommentAnnotation entryCommentAnnotation = null;

		String entryAC = entry.getPrimiaryAccessionNumber();
		String entryID = entry.getEntryId();
		EntryType entryType = entry.getEntryType();
		// boolean isSwissProt = entryType.equals(EntryType.SWISSPROT);

		String ruleAC = rule.getRuleAC();
		String templateAC = rule.getTemplateAC();

		String note = "";
		ruleCommentAnnotation = this.propagateionDataFactory
				.buildRuleCommentAnnotation(ruleAC, templateAC,
						(CCLine) ruleLine);
		entryCommentAnnotation = this.propagateionDataFactory
				.buildEntryCommentAnnotation(entryAC, entryID, entryType, null);

		if (controlStatementEval.equals(ExpressionValue.TRUE)) {
			entryCommentAnnotation = checkMatchedEntryComment(alignment, entry,
					ruleCommentAnnotation);
			// System.out.println(entryCommentAnnotation);
			if (entryCommentAnnotation != null) {
				propagationRecord.setMatched(true);
				// if (isSwissProt) {
				// propagationRecord.setPropagated(false);
				// note += "Comment match<-|->";
				// propagationRecord.setPropagationStatistics(PropagationStats.TP);
				// } else {
				propagationRecord.setPropagated(true);
				note += "Comment match<-|->";
				propagationRecord.setPropagationStatistics(PropagationStats.TP);
				// }
			} else {

				EntryComment comment = getEntryComment(alignment,
						ruleCommentAnnotation);
				// System.out.println(comment+"?");
				// System.out.println(comment.getCommentDescription()+"!");
				// comment.setCommentDescription("");
				// System.out.println(comment.get);
				entryCommentAnnotation = this.propagateionDataFactory
						.buildEntryCommentAnnotation(entryAC, entryID,
								entryType, comment);

				// System.out.println(entryCommentAnnotation+"??");
				propagationRecord.setMatched(false);
				// if (isSwissProt) {
				// // propagationRecord.setPropagated(false);
				// propagationRecord.setPropagated(true);
				// note += "Comment mismatch<-|->Predicated comment<-|->" +
				// "Rule: " + ruleCommentAnnotation.getCommentType() + " ["
				// + ruleCommentAnnotation.getCommentDescription() +
				// "]<-|->Entry: "
				// + this.getEntryCommentByType(entry,
				// ruleCommentAnnotation.getCommentType().name());
				// propagationRecord.setPropagationStatistics(PropagationStats.FP);
				// } else {

				propagationRecord.setPropagated(true);
				//note += "Predicated comment<-|->Comment mismatch<-|->"
						note += "Predicated comment<-|->Comment not found<-|->"
						+ "Rule: "
						+ ruleCommentAnnotation.getCommentType()
						+ " ["
						+ ruleCommentAnnotation.getCommentDescription()
						+ "]<-|->Entry: "
						+ this.getEntryCommentByType(entry,
								ruleCommentAnnotation.getCommentType().name());
				propagationRecord.setPropagationStatistics(PropagationStats.FP);
				// }
			}
		} else {
			propagationRecord.setPropagated(false);
			propagationRecord.setMatched(false);
			EntryComment comment = getEntryComment(alignment,
					ruleCommentAnnotation);
			comment.setCommentDescription("");
			entryCommentAnnotation = this.propagateionDataFactory
					.buildEntryCommentAnnotation(entryAC, entryID, entryType,
							comment);

			propagationRecord.setPropagationStatistics(PropagationStats.NA);
			note += "Control statement evaluted to be false, no match comment<-|->"
					+ ftGroupMatchResult.getNote();
		}

		propagationRecord.setEntryAnnotation(entryCommentAnnotation);
		propagationRecord.setRuleAnnotation(ruleCommentAnnotation);
		propagationRecord.setNote(note.trim());
		// System.out.println(propagationRecord);
		return propagationRecord;
	}

	private EntryComment getEntryComment(Alignment alignment,
			RuleCommentAnnotation ruleCommentAnnotation) {
		EntryCommentType commentType = EntryCommentType
				.valueOf(ruleCommentAnnotation.getCommentType().name());
		// String commentDescription =
		// ruleCommentAnnotation.getCommentDescription();
		// String ruleComment = ruleCommentAnnotation.getCommentType() + " | " +
		// ruleCommentAnnotation.getCommentDescription();
		String ruleComment = ruleCommentAnnotation.getCommentDescription();

		EntryComment comment = factory.buildEntryComment(commentType,
				ruleCommentAnnotation.getComment().getDescription());

		Matcher matcher = checkPlaceHolder(ruleComment);
		String newRuleComment = ruleComment;
		// String entryComment = comment.getCommentType() + " | " +
		// entryCommentAnnotation.getCommentDescription();

		if (matcher != null) {
			String residue = matcher.group(1);
			String site = matcher.group(2);
			String alignedSeqStart = PropagationUtil
					.convertTemplatePositionToTargetPosition(alignment, site);
			String alignedSeqEnd = PropagationUtil
					.convertTemplatePositionToTargetPosition(alignment, site);
			String alignedSeqResidues = PropagationUtil
					.getAlignedEntryResidues(alignment, alignedSeqStart,
							alignedSeqEnd);
			String replacement = PropagationUtil
					.getThreeLettersAminoAcid(alignedSeqResidues.toUpperCase())
					+ "-" + alignedSeqStart;

			newRuleComment = matcher.replaceAll(replacement);
			comment.setCommentDescription(newRuleComment);
			// System.out.println(newRuleComment+ "!!!");
		}
		// System.out.println(comment+"!!");
		return comment;
	}

	private EntryFeatureTableAnnotation checkMatchedEntryFeature(
			Alignment alignment, UniProtEntry entry,
			RuleFeatureTableAnnotation ruleFeatureTableAnnotation) {
		EntryFeatureTableAnnotation entryFeatureTableAnnotation = null;

		String entryAC = entry.getPrimiaryAccessionNumber();
		String entryID = entry.getEntryId();
		EntryType entryType = entry.getEntryType();
		// System.out.println("Checking " + entryAC);
		String ruleFeatureStart = ruleFeatureTableAnnotation
				.getFeatureDescriptionLine().getFeatureFromPosition();
		String ruleFeatureEnd = ruleFeatureTableAnnotation
				.getFeatureDescriptionLine().getFeatureToPosition();

		// System.out.println(entry.getPrimiaryAccessionNumber() + " | " +
		// ruleFeatureTableAnnotation.getRuleAC());
		// System.out.println(ruleFeatureStart + " | "+ruleFeatureEnd);
		// String templateAlign =
		// PropagationUtil.getTemplateResiduesInAlignment(alignment,
		// ruleFeatureStart, ruleFeatureEnd);
		// System.out.println(templateAlign);

		String entryFeatureStart = PropagationUtil
				.convertTemplatePositionToTargetPosition(alignment,
						ruleFeatureStart);
		String entryFeatureEnd = PropagationUtil
				.convertTemplatePositionToTargetPosition(alignment,
						ruleFeatureEnd);

		// System.out.println(entryFeatureStart + " | "+entryFeatureEnd);
		// String targetAlign =
		// PropagationUtil.getTargetResiduesInAlignment(alignment,
		// entryFeatureStart, entryFeatureEnd);
		// System.out.println(targetAlign);

		// int templateAlignLength = templateAlign.replaceAll("\\-",
		// "").length();
		// int targetAlignLength = targetAlign.replaceAll("\\-", "").length();
		// int diff = templateAlignLength - targetAlignLength;
		// if(diff < 0) {
		// System.out.println("gaps in template");
		// }
		//
		// System.out.println();

		String ruleFeatureDescription = ruleFeatureTableAnnotation
				.getDescription();
		String ruleFeatureType = ruleFeatureTableAnnotation.getFeatureType()
				.name();

		List<EntryFeatureTable> featureList = entry.getFeatures();
		for (EntryFeatureTable feature : featureList) {
			entryFeatureTableAnnotation = this.propagateionDataFactory
					.buildEntryFeatureTableAnnotation(entryAC, entryID,
							entryType, feature, "");

			String entryFeatureDescription = entryFeatureTableAnnotation
					.getFeatureDescription();
			String entryFeatureType = feature.getFeatureType().name();

			if (ruleFeatureType.equals(entryFeatureType)
					&& feature.getFromEndPoint().endsWith(entryFeatureStart)
					&& feature.getToEndPoint().endsWith(entryFeatureEnd)) {
				if (ruleFeatureDescription.replaceAll("\\.$", "").equals(
						entryFeatureDescription.replaceAll("\\.$", ""))) {
					// System.out.println(entryFeatureTableAnnotation.getFeature()+"!!!");
					return this.propagateionDataFactory
							.buildEntryFeatureTableAnnotation(entryAC, entryID,
									entryType, feature, "");
				}
			}
		}
		return null;
	}

	private EntryCommentAnnotation checkMatchedEntryComment(
			Alignment alignment, UniProtEntry entry,
			RuleCommentAnnotation ruleCommentAnnotation) {
		EntryCommentAnnotation entryCommentAnnotation = null;
		String entryAC = entry.getPrimiaryAccessionNumber();
		String entryID = entry.getEntryId();
		EntryType entryType = entry.getEntryType();

		String ruleComment = ruleCommentAnnotation.getCommentDescription();
		// System.out.println(ruleComment);
		// String ruleComment = ruleCommentAnnotation.getCommentType() + " | " +
		// ruleCommentAnnotation.getCommentDescription();

		List<EntryComment> entryComments = entry.getComments();
		for (EntryComment comment : entryComments) {
			// System.out.println(comment);
			entryCommentAnnotation = this.propagateionDataFactory
					.buildEntryCommentAnnotation(entryAC, entryID, entryType,
							comment);
			// String entryComment = comment.getCommentType() + " | " +
			// entryCommentAnnotation.getCommentDescription();

			String entryComment = entryCommentAnnotation
					.getCommentDescription();

			String newRuleComment = ruleComment;
			Matcher matcher = checkPlaceHolder(ruleComment);
			if (matcher != null) {
				String residue = matcher.group(1);
				String site = matcher.group(2);
				String alignedSeqStart = PropagationUtil
						.convertTemplatePositionToTargetPosition(alignment,
								site);
				String alignedSeqEnd = PropagationUtil
						.convertTemplatePositionToTargetPosition(alignment,
								site);
				String alignedSeqResidues = PropagationUtil
						.getAlignedEntryResidues(alignment, alignedSeqStart,
								alignedSeqEnd);
				String replacement = PropagationUtil
						.getThreeLettersAminoAcid(alignedSeqResidues
								.toUpperCase())
						+ "-" + alignedSeqStart;

				newRuleComment = matcher.replaceAll(replacement);

				if (comment
						.getCommentType()
						.toString()
						.equals(ruleCommentAnnotation.getCommentType()
								.toString())
						&& newRuleComment.replaceAll("\\.$", "").equals(
								entryComment.replaceAll("\\.$", ""))) {
					comment.setCommentDescription(newRuleComment);
					// System.out.println(newRuleComment.replaceAll(comment.getCommentType()
					// + " \\| ", ""));
					entryCommentAnnotation.setComment(comment);
					return entryCommentAnnotation;

				}
			} else {
				// System.out.println(entry.getPrimiaryAccessionNumber()+"\n Rule:"
				// + newRuleComment+"\nEntry:"+entryComment+"\n\n");

				if (comment
						.getCommentType()
						.toString()
						.equals(ruleCommentAnnotation.getCommentType()
								.toString())
						&& newRuleComment.replaceAll("\\.$", "").equals(
								entryComment.replaceAll("\\.$", ""))) {
					comment.setCommentDescription(newRuleComment);

					// System.out.println(newRuleComment.replaceAll(comment.getCommentType()
					// + " \\| ", ""));
					entryCommentAnnotation.setComment(comment);
					return entryCommentAnnotation;
				}
			}
			// System.out.println(entry.getPrimiaryAccessionNumber()+"\n Rule:"
			// + ruleComment+"\nEntry:"+entryComment+"\n\n");

		}

		return null;
	}

	private Matcher checkPlaceHolder(String text) {
		String pattern = "\\#\\{([A-Z][a-z][a-z])\\-(\\d+)\\}";
		Pattern placeHolderLinePattern = Pattern.compile(pattern);

		Matcher placeHolderLineMatcher = placeHolderLinePattern.matcher(text);
		if (/* text.matches(placeHolderLinePattern.toString()) && */placeHolderLineMatcher
				.find()) {
			// System.out.println(text);
			return placeHolderLineMatcher;
		} else {
			return null;
		}
	}

	String getEntryCommentByType(UniProtEntry entry, String type) {
		String description = "";
		// System.out.println("\n");
		for (EntryComment comment : entry.getComments()) {
			// System.out.println(comment.getCommentType()+"|"+comment.getCommentDescription());
			if (comment.getCommentType().name().equals(type)) {
				if (description.equals("")) {
					description = type + " [" + comment.getCommentDescription()
							+ "]";
				} else {
					description += "; " + type + " ["
							+ comment.getCommentDescription() + "]";
				}

			}
		}
		// System.out.println(entry.getComments());
		// System.out.println(entry.getPrimiaryAccessionNumber()+" " +
		// description);
		return description;
	}

	private PropagationRecord propagateKWLine(Alignment alignment,
			UniProtEntry entry, PIRRule rule, Line ruleLine,
			ControlStatement controlStatement,
			ExpressionValue controlStatementEval,
			FTGroupMatch ftGroupMatchResult) {
		PropagationRecord propagationRecord = this.propagateionDataFactory
				.buildPropagationRecord();
		propagationRecord.setControlStatement(controlStatement);
		propagationRecord.setControlStatementEvalution(controlStatementEval);

		RuleKeywordAnnotation ruleKeywordAnnotation = null;
		// List<Keyword> keywordList = entry.getKeywords();
		EntryKeywordAnnotation entryKeywordAnnotation = null;

		String entryAC = entry.getPrimiaryAccessionNumber();
		String entryID = entry.getEntryId();
		EntryType entryType = entry.getEntryType();
		// boolean isSwissProt = entryType.equals(EntryType.SWISSPROT);

		String ruleAC = rule.getRuleAC();
		String templateAC = rule.getTemplateAC();

		String note = "";
		ruleKeywordAnnotation = this.propagateionDataFactory
				.buildRuleKeywordAnnotation(ruleAC, templateAC,
						(KWLine) ruleLine);
		entryKeywordAnnotation = this.propagateionDataFactory
				.buildEntryKeywordAnnotation(entryAC, entryID, entryType, null);
		// System.out.println(entryKeywordAnnotation.getEntryAC() + " | " +
		// entryKeywordAnnotation.isSwissProt());
		if (controlStatementEval.equals(ExpressionValue.TRUE)) {
			entryKeywordAnnotation = checkMatchedEntryKeyword(entry,
					(KWLine) ruleLine);

			if (entryKeywordAnnotation != null) {
				propagationRecord.setMatched(true);
				// if (isSwissProt) {
				// propagationRecord.setPropagated(false);
				// // note +=
				// // "Keyword match; Not propagateable, for TrEMBL only; ";
				// note += "Keyword match<-|->";
				// propagationRecord.setPropagationStatistics(PropagationStats.TP);
				// } else {
				propagationRecord.setPropagated(true);
				note += "Keyword match<-|->";
				propagationRecord.setPropagationStatistics(PropagationStats.TP);
				// }
			} else {
				EntryKeyword keyword = factory
						.buildEntryKeyword(ruleKeywordAnnotation.getKeyword()
								.getKeyword());

				entryKeywordAnnotation = this.propagateionDataFactory
						.buildEntryKeywordAnnotation(entryAC, entryID,
								entryType, keyword);

				propagationRecord.setMatched(false);
				// if (isSwissProt) {
				// // propagationRecord.setPropagated(false);
				// propagationRecord.setPropagated(true);
				// note += "Predicated keyword<-|->";
				// propagationRecord.setPropagationStatistics(PropagationStats.FP);
				// } else {
				propagationRecord.setPropagated(true);
				note += "Predicated keyword<-|->";
				propagationRecord.setPropagationStatistics(PropagationStats.FP);
				// }
			}
		} else {
			propagationRecord.setPropagated(false);
			propagationRecord.setMatched(false);
			propagationRecord.setPropagationStatistics(PropagationStats.NA);
			// System.out.println("?? " + entryKeywordAnnotation);
			// System.out.println(controlStatement.toString().trim());

			note += "Control statement evaluted to be false, no matched keyword<-|->"
					+ ftGroupMatchResult.getNote();
		}

		propagationRecord.setEntryAnnotation(entryKeywordAnnotation);
		propagationRecord.setRuleAnnotation(ruleKeywordAnnotation);
		propagationRecord.setNote(note.trim());
		// System.out.println(note);

		return propagationRecord;
	}

	private EntryKeywordAnnotation checkMatchedEntryKeyword(UniProtEntry entry,
			KWLine ruleLine) {
		EntryKeywordAnnotation entryKeywordAnnotation = null;
		List<EntryKeyword> keywordList = entry.getKeywords();
		String entryAC = entry.getPrimiaryAccessionNumber();
		String entryID = entry.getEntryId();
		EntryType entryType = entry.getEntryType();
		String ruleKeyword = ruleLine.getKeyword();
		for (EntryKeyword entryKeyword : keywordList) {
			if (entryKeyword.getKeyword().equals(ruleKeyword)) {
				entryKeywordAnnotation = this.propagateionDataFactory
						.buildEntryKeywordAnnotation(entryAC, entryID,
								entryType, entryKeyword);
				break;
			}
		}
		// if(entryKeywordAnnotation == null) {
		// System.out.println(" I am here");
		// }
		return entryKeywordAnnotation;
	}

	private String[] checkDataFiles(String propagationDirectory) {
		String[] dataFiles = null;
		String checkFilesErrors = "";
		File dir = new File(propagationDirectory);
		File[] listOfFiles = dir.listFiles();
		boolean hasRuleFile = false;
		boolean hasDataFile = false;
		boolean hasAlignmentFile = false;
		String ruleFilePath = null;
		String dataFilePath = null;
		String alignmentFilePath = null;

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName();
				// System.out.println(fileName);
				if (fileName.endsWith(".uru")) {
					if (hasRuleFile == true) {
						checkFilesErrors += "Multiple rule (.uru) files exist, only one is allowed.\n";
					} else {
						hasRuleFile = true;
					}
					ruleFilePath = propagationDirectory
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
					dataFilePath = propagationDirectory
							+ System.getProperty("file.separator") + fileName;
					File file = new File(dataFilePath);
					if (file.length() == 0) {
						checkFilesErrors += "'" + dataFilePath
								+ "' is empty.\n";
					}
				}
				if (fileName.endsWith(".gff")) {
					if (hasAlignmentFile == true) {
						checkFilesErrors += "Multiple alignment (.gff) files exist, only one is allowed.\n";
					} else {
						hasAlignmentFile = true;
					}
					alignmentFilePath = propagationDirectory
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
			System.err.println(checkFilesErrors);
			System.exit(1);
		} else {
			dataFiles = new String[3];
			dataFiles[0] = ruleFilePath;
			dataFiles[1] = dataFilePath;
			dataFiles[2] = alignmentFilePath;
		}

		return dataFiles;
	}

}
