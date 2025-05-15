package edu.udel.bioinformatics.pirsr.prediction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.ControlBlock;
import org.proteininformationresource.pirsr.model.ControlStatement;
import org.proteininformationresource.pirsr.model.EndCaseStatement;
import org.proteininformationresource.pirsr.model.FeatureDescriptionLine;
import org.proteininformationresource.pirsr.model.KWLine;
import org.proteininformationresource.pirsr.model.Line;
import org.proteininformationresource.pirsr.model.PIRRule;
import org.proteininformationresource.pirsr.model.PIRRuleDataFactory;
import org.proteininformationresource.pirsr.model.PIRRuleManager;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;
import org.proteininformationresource.pirsr.prediction.Alignment;
import org.proteininformationresource.pirsr.prediction.PredictionDataFactory;
import org.proteininformationresource.pirsr.prediction.PredictionManager;
import org.proteininformationresource.pirsr.prediction.PredictionRecord;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinCommentAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinFeatureTableAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinKeywordAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleCommentAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleFeatureTableAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleKeywordAnnotation;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureType;

import edu.udel.bioinformatics.pirsr.PIRRuleUtil;
import edu.udel.bioinformatics.pirsr.prediction.annotation.ProteinCommentAnnotationImpl;
import edu.udel.bioinformatics.pirsr.propagation.PropagationUtil;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 28, 2015<br>
 * <br>
 * 
 * The implementation of <code>PredictionManager</code>.
 */
public class PredictionManagerImpl implements PredictionManager {

	private boolean debug = false;
	private PredictionDataFactory predictionDataFactory;
	private PIRRuleDataFactory pirRuleDataFactory;
	private List<Alignment> alignmentList;
	private List<ProteinAnnotation> proteinList;
	private PIRRuleManager pirRuleManager;
	private String organism;

	public PredictionManagerImpl(PIRRuleDataFactory pirRuleDataFactory,
			PredictionDataFactory predictionDataFactory,
			PIRRuleManager pirRuleManager, String organism) {
		this.predictionDataFactory = predictionDataFactory;
		this.pirRuleManager = pirRuleManager;
		this.organism = organism;
		this.pirRuleDataFactory = pirRuleDataFactory;
	}

	public PredictionDataFactory getPredictionDataFactory() {
		return this.predictionDataFactory;
	}

	public List<Alignment> loadAlignment(URL url) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));

			AlignmentParser s = new AlignmentParser(this, br);

			s.parse();
			this.alignmentList = s.getAlignments();

			br.close();
		} catch (IOException e) {
			Logger.getLogger(PredictionManager.class.getName()).log(
					Level.SEVERE, null, e);
		}
		return this.alignmentList;
	}

	public List<Alignment> loadAlignment(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			AlignmentParser s = new AlignmentParser(this, br);

			s.parse();
			this.alignmentList = s.getAlignments();

			br.close();
		} catch (IOException e) {
			Logger.getLogger(PredictionManager.class.getName()).log(
					Level.SEVERE, null, e);
		}
		return this.alignmentList;
	}

	public List<ProteinAnnotation> loadProteinInfo(File file,
			String fastaDirectory, String organism) {
		List<ProteinAnnotation> proteins = new ArrayList<ProteinAnnotation>();
		// System.out.println(organism+"!!!");
		List<String> lineage = new ArrayList<String>();
		lineage.add(organism);
		Map<String, String> nucleotideSequences = getNucleotideSequences(fastaDirectory);
		Map<String, String> proteinSequences = getProteinSequences(fastaDirectory);

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split("\t");
				if (fields.length > 0) {
					ProteinAnnotation protein = this.predictionDataFactory
							.buildProteinAnnotation(fields[0]);
					if (fields.length == 4) {
						protein.setTrigger(fields[1]);
						protein.setRuleAC(fields[2]);
						protein.setTemplateAC(fields[3]);
						// System.out.println(lineage+"??");
						protein.setOrganismClassification(lineage);
						// System.out.println(protein.getOrganismClassification()+"??");
					}
					if (fields.length == 8) {
						protein.setTrigger(fields[1]);
						protein.setRuleAC(fields[2]);
						protein.setTemplateAC(fields[3]);
						protein.setNucleotideSequenceId(fields[4]);
						protein.setNucleotideORFStart(Integer
								.parseInt(fields[5]));
						protein.setNucleotideORFEnd(Integer.parseInt(fields[6]));
						protein.setNucleotideStrand(fields[7]);
						protein.setOrganismClassification(lineage);
					}
					// System.out.println(protein.getProteinId()+ "\t" +
					// protein.getNucleotideSequenceId());
					if (protein.getNucleotideSequenceId() != null) {
						String nucleotideSequence = nucleotideSequences
								.get(protein.getNucleotideSequenceId());
						if (nucleotideSequence != null) {
							protein.setNucleotideSequence(nucleotideSequence);
						}
					}
					if (protein.getProteinId() != null) {
						String proteinSequence = proteinSequences.get(protein
								.getProteinId());
						if (proteinSequence != null) {
							protein.setProteinSequence(proteinSequence);
						}
					}
					if (proteins == null) {
						proteins = new ArrayList<ProteinAnnotation>();
					}
					proteins.add(protein);
				}
			}
			br.close();
		} catch (IOException e) {
			Logger.getLogger(PredictionManager.class.getName()).log(
					Level.SEVERE, null, e);
		}
		// System.out.println(proteinList);
		return proteins;
	}

	private Map<String, String> getProteinSequences(String fastaDirectory) {
		Map<String, String> proteinSequenceMap = new HashMap<String, String>();
		String proteinSequenceFile = fastaDirectory
				+ System.getProperty("file.separator")
				+ "IPRScanMatchedProteinSeqs.fasta";
		File file = new File(proteinSequenceFile);
		boolean empty = !file.exists() || file.length() == 0;
		if (!empty) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = null;
				String seqId = "";
				String seq = "";
				while ((line = br.readLine()) != null) {
					if (line.startsWith(">")) {
						if (seq.length() > 0) {
							proteinSequenceMap.put(seqId, seq);
							seq = "";
						}
						String[] fields = line.split("\\s+");
						seqId = fields[0].replaceAll(">", "");
					} else if (line.length() > 0) {
						seq += line;
					}
				}
				if (seq.length() > 0) {
					proteinSequenceMap.put(seqId, seq);
					seq = "";
				}
				br.close();
			} catch (IOException e) {
				Logger.getLogger(PredictionManager.class.getName()).log(
						Level.SEVERE, null, e);
			}
		}
		return proteinSequenceMap;
	}

	private Map<String, String> getNucleotideSequences(String fastaDirectory) {
		Map<String, String> nucleotideSequenceMap = new HashMap<String, String>();
		String nucleotideSequenceFile = fastaDirectory
				+ System.getProperty("file.separator")
				+ "IPRScanMatchedNucleotideSeqs.fasta";
		File file = new File(nucleotideSequenceFile);
		boolean empty = !file.exists() || file.length() == 0;
		if (!empty) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = null;
				String seqId = "";
				String seq = "";
				while ((line = br.readLine()) != null) {
					if (line.startsWith(">")) {
						if (seq.length() > 0) {
							nucleotideSequenceMap.put(seqId, seq);
							seq = "";
						}
						String[] fields = line.split("\\s+");
						seqId = fields[0].replaceAll(">", "");
					} else if (line.length() > 0) {
						seq += line;
					}
				}
				if (seq.length() > 0) {
					nucleotideSequenceMap.put(seqId, seq);
					seq = "";
				}
				br.close();
			} catch (IOException e) {
				Logger.getLogger(PredictionManager.class.getName()).log(
						Level.SEVERE, null, e);
			}
		}
		return nucleotideSequenceMap;
	}

	private String createMD5Sum(String input) {

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");

			md.update(input.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Alignment> loadAlignment(InputStream inputStream) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));

			AlignmentParser s = new AlignmentParser(this, br);

			s.parse();
			this.alignmentList = s.getAlignments();

			br.close();
		} catch (IOException e) {
			Logger.getLogger(PredictionManager.class.getName()).log(
					Level.SEVERE, null, e);
		}
		return this.alignmentList;
	}

	public Alignment getAlignment(String ruleAC, String proteinId,
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

	public Alignment getAlignmentByProteinAndRule(String proteinId,
			String ruleAC) {
		for (Alignment alignment : alignmentList) {
			if (alignment.getEntryId().equals(proteinId)
					&& alignment.getRuleAC().equals(ruleAC)) {
				return alignment;
			}
		}
		return null;
	}

	public PIRRuleManager getPIRRuleManager() {
		return this.pirRuleManager;
	}

	public void predict(String predictionDirectory, String fastaDirectory)
			throws IllegalAccessException, InvocationTargetException {
		String[] dataFiles = checkDataFiles(predictionDirectory);
		if (dataFiles != null && dataFiles.length > 0) {
			File ruleFile = new File(dataFiles[0]);
			// System.out.println(ruleFile);
			List<PIRRule> ruleList = this.pirRuleManager.parsePIRRules(
					ruleFile, PIRRuleManager.Format.UNIRULE, false, "Source");
			if (pirRuleManager.getParserLogs() != null
					&& pirRuleManager.getParserLogs().length() > 0) {
				System.err.println(pirRuleManager.getParserLogs());
				System.err.println("\"" + ruleFile
						+ "\" has syntax error, program quit.\n");
				System.exit(1);
			}
			if (debug) {
				System.out.println("Total rules: " + ruleList.size());
			}
			File dataFile = new File(dataFiles[1]);
			List<ProteinAnnotation> proteinList = this.loadProteinInfo(
					dataFile, fastaDirectory, organism);
			if (debug) {
				System.out.println("Total proteins: " + proteinList.size());
			}
			File alignmentFile = new File(dataFiles[2]);
			List<Alignment> alignmentList = this.loadAlignment(alignmentFile);
			if (debug) {
				System.out.println("Total alignments: " + alignmentList.size());
			}
			String note = "";

			for (PIRRule rule : ruleList) {
				String ruleAC = rule.getRuleAC();
				String templateAC = rule.getTemplateAC();
				List<PredictionRecord> predictionResults = new ArrayList<PredictionRecord>();
				for (ProteinAnnotation protein : proteinList) {
					// System.out.println(protein.getProteinId());
					String proteinId = protein.getProteinId();
					Alignment alignment = this.getAlignmentByProteinAndRule(
							proteinId, ruleAC);
					// System.out.println(proteinId + " | " + ruleAC + " " +
					// alignment);
					PIRRuleUtil pirruleUtil = this.pirRuleManager
							.getDataFactory().buidPIRRuleUtil(rule);
					List<ControlBlock> scopeControlBlocks = pirruleUtil
							.getScopeBlockControlBlocks();
					note = "";
					if (scopeControlBlocks == null
							|| scopeControlBlocks.size() == 0) {
						if (!PredictionUtil.checkNonControlBlockScope(rule,
								protein)) {
							note += "Taxonomic scope does not match; ";
						}
					}
					// System.out.println(proteinId + " | " + rule.getRuleAC() +
					// " | " + note+"|");
					if (note.length() > 0) {
						PredictionRecord predictionRecord = this.predictionDataFactory
								.buildPredictionRecord();
						ProteinAnnotation proteinAnnotation = this.predictionDataFactory
								.buildProteinAnnotation(proteinId);
						RuleAnnotation ruleAnnotation = this.predictionDataFactory
								.buildRuleAnnotation(ruleAC, templateAC);
						predictionRecord
								.setProteinAnnotation(proteinAnnotation);
						predictionRecord.setRuleAnnotation(ruleAnnotation);
						predictionRecord.setNote(note);
						predictionRecord.setPredicted(false);
						predictionResults.add(predictionRecord);
					} else {
						if (alignment != null) {
							predictionResults
									.addAll(predictProteinFunctionalSite(
											alignment, protein, rule));
						}
					}
				}
				printResults(rule, predictionResults, predictionDirectory,
						fastaDirectory);
			}
		}
	}

	private void printResults(PIRRule rule,
			List<PredictionRecord> predictionResults,
			String predictionDirectory, String fastaDirectory) {
		// System.out.println("TSV: "+predictionResults.size());
		String ruleAC = rule.getRuleAC();
		String reportFile = predictionDirectory
				+ System.getProperty("file.separator") + ruleAC + "_report.tsv";
		String reportXMLFile = predictionDirectory
				+ System.getProperty("file.separator") + ruleAC + "_report.xml";
		String reportGFF3File = predictionDirectory
				+ System.getProperty("file.separator") + ruleAC
				+ "_report.gff3";
		String detailsFile = predictionDirectory
				+ System.getProperty("file.separator") + ruleAC
				+ "_details.txt";
		StringBuffer results = new StringBuffer();
		StringBuffer reports = new StringBuffer();
		for (PredictionRecord predictionRecord : predictionResults) {
			if (predictionRecord != null) {
				reports.append(predictionRecord.toReport() + "\n");
			}
		}
		writeToFile(detailsFile, reports.toString());

		for (PredictionRecord predictionRecord : predictionResults) {
			if (predictionRecord != null
					&& predictionRecord.getProteinAnnotation() != null
					&& predictionRecord.isPredicted()) {
				results.append(predictionRecord.toString() + "\n");
			}
		}
		writeToFile(reportFile, results.toString());

		createXMLResults(rule, predictionResults, reportXMLFile);

		createGFF3Results(rule, predictionResults, reportGFF3File,
				fastaDirectory);

	}

	private void createGFF3Results(PIRRule rule,
			List<PredictionRecord> predictionResults, String reportGFF3File,
			String fastaDirectory) {
		// System.out.println("GFF3 :"+predictionResults.size());
		Map<String, List<PredictionRecord>> proteinToPredictionsMap = new HashMap<String, List<PredictionRecord>>();
		Map<String, ProteinAnnotation> proteinAnnotationMap = new HashMap<String, ProteinAnnotation>();
		for (PredictionRecord predictionRecord : predictionResults) {
			if (predictionRecord != null
					&& predictionRecord.getProteinAnnotation() != null
					&& predictionRecord.isPredicted()) {
				ProteinAnnotation protein = predictionRecord
						.getProteinAnnotation();
				List<PredictionRecord> records = proteinToPredictionsMap
						.get(protein.getProteinId());
				if (records == null) {
					records = new ArrayList<PredictionRecord>();
					records.add(predictionRecord);
				} else {
					records.add(predictionRecord);
				}
				proteinToPredictionsMap.put(protein.getProteinId(), records);
				proteinAnnotationMap.put(protein.getProteinId(), protein);
			}
		}

		StringBuffer gff3 = new StringBuffer();
		gff3.append("##gff-version 3\n##feature-ontology http://song.cvs.sourceforge.net/viewvc/song/ontology/sofa.obo?revision=1.275\n");
		StringBuffer sequenceRegionNucl = new StringBuffer();
		StringBuffer sequenceRegionPro = new StringBuffer();
		StringBuffer fastaNucl = new StringBuffer();
		// fastaNucl.append("##FASTA (nucleic_acid)\n");
		StringBuffer fastaPro = new StringBuffer();
		// fastaPro.append("##FASTA (protein)\n");

		StringBuffer records = new StringBuffer();

		if (proteinToPredictionsMap.keySet().size() > 0) {
			for (String proteinId : proteinToPredictionsMap.keySet()) {
				ProteinAnnotation protein = proteinAnnotationMap.get(proteinId);
				if (protein.getNucleotideSequence() != null
						&& protein.getNucleotideSequence().length() > 0) {
					records.append(getNucleotideSequenceGFF3Record(protein));
					records.append(getORFGFF3Record(protein));
					fastaNucl.append(">" + protein.getNucleotideSequenceId()
							+ "\n");
					fastaNucl.append(protein.getNucleotideSequence() + "\n");
					sequenceRegionNucl.append("##"
							+ protein.getNucleotideSequenceId() + " 1 "
							+ protein.getNucleotideSequence().length() + "\n");
				}
				sequenceRegionPro.append("##" + protein.getProteinId() + " 1 "
						+ protein.getProteinSequence().length() + "\n");

				records.append(getProteinGFF3Record(protein));
				records.append(getPredicatedFeatureGFF3Record(protein,
						proteinToPredictionsMap));
				fastaPro.append(">" + protein.getProteinId() + "\n");
				fastaPro.append(protein.getProteinSequence() + "\n");
			}
			gff3.append(sequenceRegionNucl.toString());
			gff3.append(sequenceRegionPro.toString());
			gff3.append("##seqid|source|type|start|end|score|strand|phase|attributes\n");
			gff3.append(records.toString());

			if (fastaNucl.length() > 0 /* .equals("##FASTA (nucleic_acid)\n") */) {
				gff3.append("##FASTA (nucleic_acid)\n");
				gff3.append(fastaNucl.toString());
			}
			if (fastaPro.length() > 0) {
				gff3.append("##FASTA (protein)\n");
				gff3.append(fastaPro.toString());
			}
		}

		writeToFile(reportGFF3File, gff3.toString());
	}

	private String getPredicatedFeatureGFF3Record(ProteinAnnotation protein,
			Map<String, List<PredictionRecord>> proteinToPredictionsMap) {
		StringBuffer records = new StringBuffer();
		String commentAttributes = getCommentAttributes(proteinToPredictionsMap
				.get(protein.getProteinId()));
		String keywordAttributes = getKeywordAttributes(proteinToPredictionsMap
				.get(protein.getProteinId()));
		for (PredictionRecord predictionRecord : proteinToPredictionsMap
				.get(protein.getProteinId())) {
			ProteinAnnotation proteinAnnotation = predictionRecord
					.getProteinAnnotation();
			if (proteinAnnotation instanceof ProteinFeatureTableAnnotation) {
				ProteinFeatureTableAnnotation proteinFeatureTableAnnotation = (ProteinFeatureTableAnnotation) proteinAnnotation;
				records.append(protein.getProteinId() + "\t");
				records.append("PIRSitePredict" + "\t");
				records.append("feature_prediction" + "\t");
				FeatureDescriptionLine featureDescriptionLine = proteinFeatureTableAnnotation
						.getFeatureDescriptionLine();
				String start = featureDescriptionLine.getFeatureFromPosition();
				if (start.toUpperCase().equals("NTER")) {
					start = "1";
				}
				records.append(start + "\t");
				String end = featureDescriptionLine.getFeatureToPosition();
				if (end.toUpperCase().equals("CTER")) {
					end = "" + protein.getProteinSequence().length();
				}
				records.append(end + "\t");
				records.append("." + "\t");
				records.append("+" + "\t");
				records.append("." + "\t");
				records.append("Name=" + "feature_" + protein.getProteinId()
						+ "_"
						+ featureDescriptionLine.getFeatureKey().toString()
						+ "_" + start + "_" + end + ";");
				records.append("type="
						+ featureDescriptionLine.getFeatureKey().toString()
						+ ";");
				String featureDescription = featureDescriptionLine
						.getFeatureDescription().replaceAll("\\.$", "");
				if (featureDescription != null
						&& featureDescription.length() > 0) {
					records.append("feature_description="
							+ this.escapeForGFF3Attribute(featureDescription)
							+ ";");
				}
				records.append("ID=" + "feature_" + protein.getProteinId()
						+ "_"
						+ featureDescriptionLine.getFeatureKey().toString()
						+ "_" + start + "_" + end + ";");
				records.append("rule=" + protein.getRuleAC() + ";");
				records.append("trigger=" + protein.getTrigger() + ";");
				records.append("template=" + protein.getTemplateAC() + ";");
				records.append(commentAttributes);
				records.append(keywordAttributes);
				records.append("\n");
			}
		}
		return records.toString().replaceAll("\\;$", "");
	}

	private String getKeywordAttributes(List<PredictionRecord> predictionRecords) {
		String record = "";
		for (PredictionRecord predictionRecord : predictionRecords) {
			ProteinAnnotation proteinAnnotation = predictionRecord
					.getProteinAnnotation();
			if (proteinAnnotation instanceof ProteinKeywordAnnotation) {
				ProteinKeywordAnnotation proteinKeywordAnnotation = ((ProteinKeywordAnnotation) proteinAnnotation);
				String keyword = escapeForGFF3Attribute(proteinKeywordAnnotation
						.getKeyword().getKeyword());
				if (record.length() == 0) {
					record += keyword;
				} else {
					record += keyword + ",";
				}
			}
		}
		if (record.length() > 0) {
			return "keyword=" + record + ";";
		}
		return "";
	}

	private String getCommentAttributes(List<PredictionRecord> predictionRecords) {
		String record = "";
		for (PredictionRecord predictionRecord : predictionRecords) {
			ProteinAnnotation proteinAnnotation = predictionRecord
					.getProteinAnnotation();
			if (proteinAnnotation instanceof ProteinCommentAnnotation) {
				ProteinCommentAnnotation proteinCommentAnnotation = ((ProteinCommentAnnotation) proteinAnnotation);
				record += "comment_"
						+ proteinCommentAnnotation.getCommentType()
						+ "="
						+ escapeForGFF3Attribute(proteinCommentAnnotation
								.getCommentDescription().replaceAll("\\.$", ""))
						+ ";";
			}
		}
		return record;
	}

	private String escapeForGFF3Attribute(String description) {
		/*
		 * ; semicolon (%3B) = equals (%3D) & ampersand (%26) , comma (%2C)
		 */
		return description.replaceAll(";", "%3B").replaceAll("=", "%3D")
				.replaceAll("&", "%26").replaceAll(",", "%2C");
	}

	private String getProteinGFF3Record(ProteinAnnotation protein) {
		StringBuffer records = new StringBuffer();
		records.append(protein.getProteinId() + "\t");
		records.append("getorf" + "\t");
		records.append("polypeptide" + "\t");
		records.append("1" + "\t");
		int proteinSeqLength = protein.getProteinSequence().length();
		records.append(proteinSeqLength + "\t");
		records.append("." + "\t");
		records.append("+" + "\t");
		records.append("." + "\t");
		records.append("Name=" + protein.getProteinId() + "_1_"
				+ proteinSeqLength + ";");
		records.append("md5=" + this.createMD5Sum(protein.getProteinSequence())
				+ ";");
		records.append("ID=" + protein.getProteinId() + "_1_"
				+ proteinSeqLength + "\n");
		return records.toString();
	}

	private String getORFGFF3Record(ProteinAnnotation protein) {
		StringBuffer records = new StringBuffer();
		records.append(protein.getNucleotideSequenceId() + "\t");
		records.append("getorf" + "\t");
		records.append("ORF" + "\t");
		int orfStart = protein.getNucleotideORFStart();
		int orfEnd = protein.getNucleotideORFEnd();
		records.append(orfStart + "\t");
		records.append(orfEnd + "\t");
		records.append("." + "\t");
		if (protein.getNucleotideStrand().equals("SENSE")) {
			records.append("+" + "\t");
		} else {
			records.append("-" + "\t");
		}
		records.append("." + "\t");
		int proteinSeqLength = protein.getProteinSequence().length();
		records.append("Name=" + protein.getNucleotideSequenceId() + "_"
				+ orfStart + "_" + orfEnd + ";");
		records.append("Target=" + protein.getProteinId() + "_1_"
				+ proteinSeqLength + " 1 " + proteinSeqLength + ";");
		records.append("md5=" + this.createMD5Sum(protein.getProteinSequence())
				+ ";");
		records.append("ID=orf_" + protein.getNucleotideSequenceId() + "_"
				+ orfStart + "_" + orfEnd + "\n");
		return records.toString();
	}

	private String getNucleotideSequenceGFF3Record(ProteinAnnotation protein) {
		StringBuffer records = new StringBuffer();
		records.append(protein.getNucleotideSequenceId() + "\t");
		records.append("provided_by_user" + "\t");
		records.append("nucleic_acid" + "\t");
		records.append("1" + "\t");
		records.append(protein.getNucleotideSequence().length() + "\t");
		records.append("." + "\t");
		records.append("+" + "\t");
		records.append("." + "\t");
		records.append("Name=" + protein.getNucleotideSequenceId() + ";");
		records.append("md5="
				+ this.createMD5Sum(protein.getNucleotideSequence()) + ";");
		records.append("ID=" + protein.getNucleotideSequenceId() + "\n");
		return records.toString();
	}

	void createXMLResults(PIRRule rule,
			List<PredictionRecord> predictionResults, String reportXMLFile) {
		// System.out.println("XML:  "+predictionResults.size());
		Map<String, List<PredictionRecord>> proteinToPredictionsMap = new HashMap<String, List<PredictionRecord>>();
		Map<String, ProteinAnnotation> proteinAnnotationMap = new HashMap<String, ProteinAnnotation>();
		for (PredictionRecord predictionRecord : predictionResults) {
			if (predictionRecord != null
					&& predictionRecord.getProteinAnnotation() != null
					&& predictionRecord.isPredicted()) {
				ProteinAnnotation protein = predictionRecord
						.getProteinAnnotation();
				List<PredictionRecord> records = proteinToPredictionsMap
						.get(protein.getProteinId());
				if (records == null) {
					records = new ArrayList<PredictionRecord>();
				}
				//
				// } else {
				// records.add(predictionRecord);
				// }
				records.add(predictionRecord);
				proteinToPredictionsMap.put(protein.getProteinId(), records);
				proteinAnnotationMap.put(protein.getProteinId(), protein);
			}
		}

		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		xml.append("<pirsr-predictions xmlns=\"http://research.bioinformatics.udel.edu/schemas/PIRSRPredictor\">\n");
		if (proteinToPredictionsMap.keySet().size() > 0) {
			xml.append("  <pirsr id=\"" + rule.getRuleAC() + "\" trigger=\""
					+ rule.getTrigger() + "\" template=\""
					+ rule.getTemplateAC() + "\">\n");
			for (String proteinId : proteinToPredictionsMap.keySet()) {
				ProteinAnnotation protein = proteinAnnotationMap.get(proteinId);
				String proteinSequence = protein.getProteinSequence();
				// System.out.println(rule.getRuleAC() + " " +proteinId +
				// " ? ");
				// System.out.println(protein.getProteinId());
				// System.out.println(proteinSequence.length());

				xml.append("\t<protein id=\"" + protein.getProteinId());
				xml.append("\" length=\"" + proteinSequence.length() + "\">\n");
				xml.append("\t\t<sequence md5=\""
						+ this.createMD5Sum(proteinSequence) + "\">"
						+ proteinSequence + "</sequence>\n");
				if (protein.getNucleotideSequence() != null) {
					String nucleotideSequence = protein.getNucleotideSequence();
					xml.append("\t\t<nucleotide-sequence id=\""
							+ protein.getNucleotideSequenceId() + "\" md5=\""
							+ this.createMD5Sum(nucleotideSequence)
							+ "\" length=\"" + nucleotideSequence.length()
							+ "\" orf-start=\""
							+ protein.getNucleotideORFStart() + "\" orf-end=\""
							+ protein.getNucleotideORFEnd()
							+ "\" orf-strand=\""
							+ protein.getNucleotideStrand() + "\">");
					xml.append(nucleotideSequence + "</nucleotide-sequence>\n");
				} else {
					xml.append("\t\t<nucleotide-sequence/>\n");
				}

				List<PredictionRecord> predictions = proteinToPredictionsMap
						.get(protein.getProteinId());
				// System.out.println(rule.getRuleAC()+" " +
				// predictions.size());
				if (predictions.size() > 0) {
					xml.append("\t\t<predictions>\n");
					for (PredictionRecord prediction : predictions) {
						ProteinAnnotation proteinAnnotation = prediction
								.getProteinAnnotation();
						if (proteinAnnotation instanceof ProteinCommentAnnotation) {
							xml.append(((ProteinCommentAnnotation) proteinAnnotation)
									.toXML());
						}
						if (proteinAnnotation instanceof ProteinKeywordAnnotation) {
							xml.append(((ProteinKeywordAnnotation) proteinAnnotation)
									.toXML());
						}
						if (proteinAnnotation instanceof ProteinFeatureTableAnnotation) {
							xml.append(((ProteinFeatureTableAnnotation) proteinAnnotation)
									.toXML());
						}
					}
					xml.append("\t\t</predictions>\n");
				} else {
					xml.append("\t\t<predictions/>\n");
				}
				xml.append("\t</protein>\n");
			}
			xml.append("  </pirsr>\n");
		}
		// else {
		// xml.append("\t<protein/>\n");
		// }

		xml.append("</pirsr-predictions>\n");

		writeToFile(reportXMLFile, xml.toString());

	}

	private List<PredictionRecord> predictProteinFunctionalSite(
			Alignment alignment, ProteinAnnotation protein, PIRRule rule)
			throws IllegalAccessException, InvocationTargetException {
		FTGroupMatch ftGroupMatchResult = PredictionUtil.getFTGroupMatch(
				protein, alignment, rule);
		Map<Integer, Boolean> ftGroups = ftGroupMatchResult.getFtGroups();
		String ftGroupMatchNote = ftGroupMatchResult.getNote();
		ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator(
				protein, rule, ftGroups);

		List<PredictionRecord> predictionResults = new ArrayList<PredictionRecord>();
		if (debug) {
			System.out.println("\n" + protein.getProteinId() + " | "
					+ rule.getRuleAC() + " | " + rule.getTrigger() + " | "
					+ rule.getTemplateAC());
		}

		if (debug) {
			System.out.println(protein.getProteinId() + " | " + ftGroups
					+ "\n\n");
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

						List<Line> applicableLines = controlStatement
								.getApplicableLines();
						PredictionRecord predictionRecord = null;
						for (Line ruleLine : applicableLines) {
							if (ruleLine instanceof CCLine) {

								predictionRecord = predictCCLine(alignment,
										protein, rule, ruleLine,
										controlStatement, controlStatementEval,
										ftGroupMatchResult);
							} else if (ruleLine instanceof FeatureDescriptionLine) {
								predictionRecord = predictFeatureDescriptionLine(
										alignment, protein, rule, ruleLine,
										controlStatement, controlStatementEval,
										ftGroupMatchNote, ftGroups);
							} else if (ruleLine instanceof KWLine) {
								predictionRecord = predictKWLine(alignment,
										protein, rule, ruleLine,
										controlStatement, controlStatementEval,
										ftGroupMatchResult);
							}
							if (predictionRecord != null) {
								// &&
								// }
								// predictionRecord.getProteinAnnotation() !=
								// null
								// && predictionRecord.isPredicted()) {
								predictionResults.add(predictionRecord);
							}
						}
					}
				}
			}
		}
		return predictionResults;
	}

	private PredictionRecord predictKWLine(Alignment alignment,
			ProteinAnnotation protein, PIRRule rule, Line ruleLine,
			ControlStatement controlStatement,
			ExpressionValue controlStatementEval,
			FTGroupMatch ftGroupMatchResult) throws IllegalAccessException,
			InvocationTargetException {
		PredictionRecord predictionRecord = this.predictionDataFactory
				.buildPredictionRecord();
		predictionRecord.setControlStatement(controlStatement);
		predictionRecord.setControlStatementEvalution(controlStatementEval);

		RuleKeywordAnnotation ruleKeywordAnnotation = null;
		ProteinKeywordAnnotation proteinKeywordAnnotation = null;
		String proteinId = protein.getProteinId();
		String ruleAC = rule.getRuleAC();
		String templateAC = rule.getTemplateAC();
		String note = "";

		ruleKeywordAnnotation = this.predictionDataFactory
				.buildRuleKeywordAnnotation(ruleAC, templateAC,
						(KWLine) ruleLine);

		if (controlStatementEval.equals(ExpressionValue.TRUE)) {
			proteinKeywordAnnotation = this.predictionDataFactory
					.buildProteinKeywordAnnotation(proteinId,
							ruleKeywordAnnotation.getKeyword());
			BeanUtils.copyProperties(proteinKeywordAnnotation, protein);
			predictionRecord.setMatched(true);
			predictionRecord.setPredicted(true);
			note += "Keyword prediction<-|->";
		} else {
			predictionRecord.setMatched(false);
			predictionRecord.setPredicted(false);
			note += "Control statement evaluted to be false, no predicated keyword<-|->"
					+ ftGroupMatchResult.getNote();
		}
		predictionRecord.setNote(note.trim());
		predictionRecord.setRuleAnnotation(ruleKeywordAnnotation);
		predictionRecord.setProteinAnnotation(proteinKeywordAnnotation);
		return predictionRecord;
	}

	private PredictionRecord predictFeatureDescriptionLine(Alignment alignment,
			ProteinAnnotation protein, PIRRule rule, Line ruleLine,
			ControlStatement controlStatement,
			ExpressionValue controlStatementEval, String ftGroupMatchNote,
			Map<Integer, Boolean> ftGroups) throws IllegalAccessException,
			InvocationTargetException {
		PredictionRecord predictionRecord = this.predictionDataFactory
				.buildPredictionRecord();
		predictionRecord.setControlStatement(controlStatement);
		predictionRecord.setControlStatementEvalution(controlStatementEval);

		String proteinId = protein.getProteinId();
		String ruleAC = rule.getRuleAC();
		String templateAC = rule.getTemplateAC();
		String note = "";
		RuleFeatureTableAnnotation ruleFeatureTableAnnotation = this.predictionDataFactory
				.buildRuleFeatureTableAnnotation(ruleAC, templateAC,
						(FeatureDescriptionLine) ruleLine);
		ProteinFeatureTableAnnotation proteinFeatureTableAnnotation = this.predictionDataFactory
				.buildProteinFeatureTableAnnotation(proteinId,
						ruleFeatureTableAnnotation.getFeatureDescriptionLine());

		if (ftGroupOK(ftGroups)) {
			if (controlStatementEval.equals(ExpressionValue.TRUE)) {
				if (PredictionUtil.checkFeaturePatternMatch(protein, alignment,
						ruleFeatureTableAnnotation.getFeatureDescriptionLine())) {
					proteinFeatureTableAnnotation = getEntryFeature(
							protein.getProteinId(), alignment,
							ruleFeatureTableAnnotation);
					BeanUtils.copyProperties(proteinFeatureTableAnnotation,
							protein);

					note += "Predicted feature <-|->";
					predictionRecord.setPredicted(true);
					predictionRecord.setMatched(true);
					predictionRecord.setNote(note);

				} else {
					predictionRecord.setPredicted(false);
					predictionRecord.setMatched(false);
					note += "No match pattern found <-|->"
							+ proteinFeatureTableAnnotation
									.getMissMatchReason();
					note += PredictionUtil.getMatchInfo(
							proteinFeatureTableAnnotation, alignment,
							ruleFeatureTableAnnotation
									.getFeatureDescriptionLine());
					predictionRecord.setNote(note);
				}
			} else {
				predictionRecord.setPredicted(false);
				predictionRecord.setMatched(false);
				note += "Control statement evaluted to be false, no matched feature<-|->"
						+ ftGroupMatchNote;
				predictionRecord.setNote(note.trim());
			}
		} else {
			predictionRecord.setNote(ftGroupMatchNote.trim());
			predictionRecord.setPredicted(false);
			predictionRecord.setMatched(false);
		}
		predictionRecord.setProteinAnnotation(proteinFeatureTableAnnotation);
		predictionRecord.setRuleAnnotation(ruleFeatureTableAnnotation);
		return predictionRecord;
	}

	private ProteinFeatureTableAnnotation getEntryFeature(String proteinId,
			Alignment alignment,
			RuleFeatureTableAnnotation ruleFeatureTableAnnotation) {

		ProteinFeatureTableAnnotation feature = null;
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
		String entryFeatureStart = PredictionUtil
				.convertTemplatePositionToTargetPosition(alignment,
						ruleFeatureStart);
		String entryFeatureEnd = PredictionUtil
				.convertTemplatePositionToTargetPosition(alignment,
						ruleFeatureEnd);

		FeatureDescriptionLine proteinFeatureDescriptionLine = pirRuleDataFactory
				.buildFeatureDescription(
						featureDescriptionLine.getFeatureKey(),
						entryFeatureStart, entryFeatureEnd,
						featureDescriptionLine.getDescriptions());

		feature = this.predictionDataFactory
				.buildProteinFeatureTableAnnotation(proteinId,
						proteinFeatureDescriptionLine);
		return feature;
	}

	// private ProteinFeatureTableAnnotation
	// checkMatchedProteinFeature(Alignment alignment, ProteinAnnotation
	// protein,
	// RuleFeatureTableAnnotation ruleFeatureTableAnnotation) {
	// ProteinFeatureTableAnnotation proteinFeatureTableAnnotation = null;
	// String proteinId = protein.getProteinId();
	//
	// String ruleFeatureStart =
	// ruleFeatureTableAnnotation.getFeatureDescriptionLine().getFeatureFromPosition();
	// String ruleFeatureEnd =
	// ruleFeatureTableAnnotation.getFeatureDescriptionLine().getFeatureToPosition();
	//
	// String proteinFeatureStart =
	// PredictionUtil.convertTemplatePositionToTargetPosition(alignment,
	// ruleFeatureStart);
	// String proteinFeatureEnd =
	// PredictionUtil.convertTemplatePositionToTargetPosition(alignment,
	// ruleFeatureEnd);
	//
	// String ruleFeatureDescription =
	// ruleFeatureTableAnnotation.getDescription();
	// String ruleFeatureType =
	// ruleFeatureTableAnnotation.getFeatureType().name();
	//
	// return null;
	// }

	private boolean ftGroupOK(Map<Integer, Boolean> ftGroups) {
		if (ftGroups.size() == 1 && ftGroups.get(1) == false) {
			return false;
		}
		return true;
	}

	private PredictionRecord predictCCLine(Alignment alignment,
			ProteinAnnotation protein, PIRRule rule, Line ruleLine,
			ControlStatement controlStatement,
			ExpressionValue controlStatementEval,
			FTGroupMatch ftGroupMatchResult) throws IllegalAccessException,
			InvocationTargetException {
		PredictionRecord predictionRecord = this.predictionDataFactory
				.buildPredictionRecord();
		predictionRecord.setControlStatement(controlStatement);
		predictionRecord.setControlStatementEvalution(controlStatementEval);
		RuleCommentAnnotation ruleCommentAnnotation = null;
		ProteinCommentAnnotation proteinCommentAnnotation = null;

		String proteinId = protein.getProteinId();
		String ruleAC = rule.getRuleAC();
		String templateAC = rule.getTemplateAC();
		String note = "";

		ruleCommentAnnotation = this.predictionDataFactory
				.buildRuleCommentAnnotation(ruleAC, templateAC,
						(CCLine) ruleLine);
		if (controlStatementEval.equals(ExpressionValue.TRUE)) {
			proteinCommentAnnotation = this.predictionDataFactory
					.buildProteinCommentAnnotation(proteinId,
							ruleCommentAnnotation.getComment());
			BeanUtils.copyProperties(proteinCommentAnnotation, protein);
			predictionRecord.setMatched(true);
			predictionRecord.setPredicted(true);
			note += "Comment predicted<-|->";

		} else {
			predictionRecord.setMatched(false);
			predictionRecord.setPredicted(false);
			note += "Control statement evaluted to be false, no predicated comment<-|->"
					+ ftGroupMatchResult.getNote();
		}

		predictionRecord.setRuleAnnotation(ruleCommentAnnotation);
		predictionRecord.setProteinAnnotation(proteinCommentAnnotation);
		predictionRecord.setNote(note.trim());
		return predictionRecord;
	}

	private String[] checkDataFiles(String predictionDirectory) {
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
		return dataFiles;
	}

	public String getOrganism() {
		return organism;
	}

	public void setOrganism(String organism) {
		this.organism = organism;
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
}
