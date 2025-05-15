package edu.udel.bioinformatics.pirsr.uniprot.io;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.uniprot.model.EntryAccessionNumber;
import org.proteininformationresource.pirsr.uniprot.model.EntryComment;
import org.proteininformationresource.pirsr.uniprot.model.EntryCommentType;
import org.proteininformationresource.pirsr.uniprot.model.EntryDataFactory;
import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseCrossReference;
import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseType;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureType;
import org.proteininformationresource.pirsr.uniprot.model.EntryIdentifier;
import org.proteininformationresource.pirsr.uniprot.model.EntryKeyword;
import org.proteininformationresource.pirsr.uniprot.model.EntryOrganelle;
import org.proteininformationresource.pirsr.uniprot.model.EntryOrganismClassification;
import org.proteininformationresource.pirsr.uniprot.model.EntryOrganismSpecies;
import org.proteininformationresource.pirsr.uniprot.model.EntrySequence;
import org.proteininformationresource.pirsr.uniprot.model.EntryType;
import org.proteininformationresource.pirsr.uniprot.model.UniProtEntry;

import edu.udel.bioinformatics.pirsr.uniprot.EntryUtil;

public class UniProtFlatFileParser {

	static boolean debug = false;
	static EntryDataFactory dataFactory;
	static int currentLineNum = 0;
	UniProtFlatFileParserLog parserLog;
	static UniProtEntry entry;
	static EntryIdentifier entryIdentifier;
	static String accessionNumbers;
	static String keywords;
	static String lineage;
	static String organelle;
	static String organismSpecies;
	
	static List<EntryDatabaseCrossReference> entryDatabaseCrossReferences;
	static List<EntryComment> entryComments;
	static List<EntryFeatureTable> entryFeatures;
	static EntrySequence entrySequence;

	public static UniProtEntry parse(String entryText, EntryDataFactory entryDataFactory) {
		dataFactory = entryDataFactory;
		entry = entryDataFactory.buildUniProtEntry();
		accessionNumbers = "";
		keywords = "";
		lineage = "";
		organelle = "";
		organismSpecies = "";
		entryDatabaseCrossReferences = new ArrayList<EntryDatabaseCrossReference>();
		entryComments = new ArrayList<EntryComment>();
		entryFeatures = new ArrayList<EntryFeatureTable>();
		String[] lines = entryText.split(System.getProperty("line.separator"));
		for (String line : lines) {
			if (debug) {
				// System.out.println(line);
			}
			currentLineNum++;
			if (line != null && line.length() > 0) {
				if (line.startsWith(UniProtFlatFileConstants.ID_LINE_START)) {
					parseIDLine(line);
				} else if (line.startsWith(UniProtFlatFileConstants.AC_LINE_START)) {
					parseACLine(line);
				} else if (line.startsWith(UniProtFlatFileConstants.CC_LINE_START)) {
					parseCCLine(line);
				} else if (line.startsWith(UniProtFlatFileConstants.DR_LINE_START)) {
					parseDRLine(line);
				} else if (line.startsWith(UniProtFlatFileConstants.FT_LINE_START)) {
					parseFTLine(line);
					
				} else if (line.startsWith(UniProtFlatFileConstants.KW_LINE_START)) {
					parseKWLine(line);
				} else if (line.startsWith(UniProtFlatFileConstants.OC_LINE_START)) {
					parseOCLine(line);
				} else if (line.startsWith(UniProtFlatFileConstants.OG_LINE_START)) {
					parseOGLine(line);
					//System.out.println(line);
				} else if (line.startsWith(UniProtFlatFileConstants.OS_LINE_START)) {
					parseOSLine(line);
				} else if (line.startsWith(UniProtFlatFileConstants.SD_LINE_START)) {
					parseSDLine(line);
				} else if (line.startsWith(UniProtFlatFileConstants.SQ_LINE_START)) {
					parseSQLine(line);
				} else if (line.startsWith(UniProtFlatFileConstants.SEPARATOR)) {
					parseSeparatorLine(line);
				}
			}
		}
		return entry;
	}

	private static void parseOSLine(String line) {
		if(organismSpecies.equals("")) {
			organismSpecies += line.replace(UniProtFlatFileConstants.OS_LINE_START, "");
		}
		else {
			organismSpecies += " " +line.replace(UniProtFlatFileConstants.OS_LINE_START, "");
		}
	}

	private static void parseOGLine(String line) {
		if(organelle.equals("")) {
			organelle += line.replace(UniProtFlatFileConstants.OG_LINE_START, "");
		}
		else {
			organelle += " "+line.replace(UniProtFlatFileConstants.OG_LINE_START, "");
		}
	}

	private static void parseSeparatorLine(String line) {
		entry.setIdentifier(entryIdentifier);
		if (debug) {
			// System.out.println(" " + entryIdentifier);
		}

		entry.setAccessionNumber(getEntryAccessionNumber());
		if (debug) {
			// System.out.println(" " + getEntryAccessionNumber());
		}

		entry.setOrganismClassifcation(getEntryOrganismClassification());
		if (debug) {
			// System.out.println(" " + getEntryOrganismClassification());
		}
		
		if(!organelle.equals("")) {
			entry.setOrganelle(getEntryOrganelle());
		}
		
		
		entry.setOrganismSpecies(getOrganismSpecies());
		entry.setKeywords(getEntryKeywords());
		List<EntryKeyword> keywordList = getEntryKeywords();
		if (debug) {
			String report = (UniProtFlatFileConstants.KW_LINE_START);
			for (int i = 0; i < keywordList.size(); i++) {
				if (i == 0) {
					report += keywordList.get(i);
				} else {
					report += ", " + keywordList.get(i);
				}
			}
			// System.out.println(" " + report);

		}

		entry.setDatabaseCrossReference(entryDatabaseCrossReferences);

		if (debug) {
			for (EntryDatabaseCrossReference databaseCrossReference : entryDatabaseCrossReferences) {
				// System.out.println(" " + databaseCrossReference);
			}
		}

		entry.setComments(getEntryComments());

		if (debug) {
			for (EntryComment comment : entryComments) {
				System.out.println(" " + comment.toReport());
			}
		}

		entry.setFeatures(getEntryFeatures());
		if (debug) {
			for (EntryFeatureTable feature : entryFeatures) {
				// System.out.println(" " +feature);
			}
		}
		entry.setSequece(entrySequence);
		if (debug) {
			// System.out.println(" " + entrySequence);
		}
		if (debug) {
			// System.out.println(" " + UniProtFlatFileConstants.SEPARATOR);
		}
		if(debug) {
		//if(entry.getOrganelle()!=null && entry.getOrganelle().getOrganelle().length() > 0) {
			System.out.println(entry);
		//}
		}
	}


	private static List<EntryFeatureTable> getEntryFeatures() {
		for(EntryFeatureTable feature : entryFeatures) {
			//System.out.println("??"+feature.getDescription());
			feature.setFeatureDescription(EntryUtil.cleanup(feature.getFeatureDescription()));
			//System.out.println("!!"+feature.getDescription());
		}
		return entryFeatures;
	}

	private static List<EntryComment> getEntryComments() {
		
		for(EntryComment entryComment: entryComments) {
			entryComment.setCommentDescription(EntryUtil.cleanup(entryComment.getCommentDescription()));
		}
		return entryComments;
	}

	private static List<EntryKeyword> getEntryKeywords() {
		List<EntryKeyword> keywordList = new ArrayList<EntryKeyword>();
		keywords = keywords.replaceAll("\\.$", "");
		String[] keywordArr = keywords.split(";");
		for (String keyword : keywordArr) {
			if (keyword.length() > 0) {
				EntryKeyword entryKeyword = dataFactory.buildEntryKeyword(EntryUtil.cleanup(keyword.trim()));
				keywordList.add(entryKeyword);
			}
		}

		return keywordList;
	}


	private static EntryOrganismSpecies getOrganismSpecies() {
		return  dataFactory.buildEntryOrganismSpecies(EntryUtil.cleanup(organismSpecies));
	}
	
	private static EntryOrganelle getEntryOrganelle() {
//		if(!organelle.equals("")) {
//			System.out.println(organelle+"???");
//			System.out.println(EntryUtil.cleanup(organelle)+"!!!");
//		}
		return  dataFactory.buildEntryOrganelle(EntryUtil.cleanup(organelle));
	}
	
	private static EntryOrganismClassification getEntryOrganismClassification() {
		List<String> taxonList = new ArrayList<String>();
		lineage = lineage.replaceAll("\\.$", "");
		// System.out.println("?? " + lineage);
		String[] taxons = lineage.split(";");
		for (String taxon : taxons) {
			if (taxon.length() > 0) {
				taxonList.add(taxon.trim());
			}
		}
		EntryOrganismClassification organismClassification = dataFactory.buildEntryOrganismClassification(taxonList);
		return organismClassification;
	}

	private static EntryAccessionNumber getEntryAccessionNumber() {
		List<String> accessionNumberList = new ArrayList<String>();
		String[] accessions = accessionNumbers.split(";");
		for (String accession : accessions) {
			if (accession.length() > 0) {
				accessionNumberList.add(accession.trim());
			}
		}
		EntryAccessionNumber entryAccessionNumber = dataFactory.buildEntryAccessionNumber(accessionNumberList);
		return entryAccessionNumber;
	}

	private static void parseSDLine(String line) {
		// if(line.startsWith(UniProtFlatFileConstants.SD_LINE_START)) {
		String seqData = line.replace(" ", "");
		String entrySeqData = entrySequence.getSequenceData();
		if (entrySeqData == null) {
			entrySequence.setSequenceData(seqData);
		} else {
			entrySequence.setSequenceData(entrySeqData + seqData);
		}
		// }

	}

	private static void parseSQLine(String line) {
		// SQ SEQUENCE 97 AA; 9110 MW; E3C20C259858B830 CRC64;

		// if(line.startsWith(UniProtFlatFileConstants.SQ_LINE_START)) {
		// System.out.println(line);
		line = line.replace(UniProtFlatFileConstants.SQ_LINE_START, "");
		String[] fields = line.split("\\s+");
		int length = Integer.parseInt(fields[0]);
		int molecularWeight = Integer.parseInt(fields[2]);
		String crc64 = fields[4];
		entrySequence = dataFactory.buildEntrySequence(crc64, length, molecularWeight);
		// }
	}

	private static void parseOCLine(String line) {
		if(lineage.equals("")) {
			lineage += line.replace(UniProtFlatFileConstants.OC_LINE_START, "");
		}
		else {
			lineage += " "+line.replace(UniProtFlatFileConstants.OC_LINE_START, "");
		}
	}

	private static void parseKWLine(String line) {
		if(keywords.equals("")) {
			keywords += line.replace(UniProtFlatFileConstants.KW_LINE_START, "");
		}
		else {
			keywords += " "+ line.replace(UniProtFlatFileConstants.KW_LINE_START, "");
		}
	}

	private static void parseFTLine(String line) {
		// System.out.println(line);
		// FT CHAIN 1 352 Putative KilA-N domain-containing protein
		// FT 006L.
		// FT /FTId=PRO_0000377958.
		// FT DOMAIN 15 123 KilA-N. {ECO:0000255|PROSITE-
		// FT ProRule:PRU00631}.
		if (line.startsWith(UniProtFlatFileConstants.FT_ID_START)) {
			String featureId = line.replace(UniProtFlatFileConstants.FT_ID_START, "");
			featureId = featureId.replaceAll("\\.$", "");
			EntryFeatureTable lastEntryFeatureTable = entryFeatures.get(entryFeatures.size() - 1);
			lastEntryFeatureTable.setFeatureIdentifier(featureId);
		} else if (line.startsWith(UniProtFlatFileConstants.FT_DESCRIPTION_START)) {
			String featureDescription = line.replace(UniProtFlatFileConstants.FT_DESCRIPTION_START, "");
			EntryFeatureTable lastEntryFeature = entryFeatures.get(entryFeatures.size() - 1);
			String lastFeatureDescription = lastEntryFeature.getFeatureDescription();
			if (lastFeatureDescription.length() > 0) {
				if(lastFeatureDescription.endsWith("-")) {
					lastFeatureDescription += featureDescription.trim();
				}
				else {
					lastFeatureDescription += " " + featureDescription.trim();
				}
			} else if (lastFeatureDescription.length() == 0) {
				lastFeatureDescription = featureDescription.trim();
			}
			lastEntryFeature.setFeatureDescription(lastFeatureDescription);
		} else if (line.startsWith(UniProtFlatFileConstants.FT_LINE_START)) {
			// System.out.println(line);
			String[] fields = line.split("\\s+", 5);
			String featureKey = fields[1];
			String fromEndPoint = fields[2];
			String toEndPoint = fields[3];

			String description = "";
			if (fields.length > 4) {
				description = fields[4].trim();
				//System.out.println("???" + description+"!!!");
			}

			EntryFeatureType featureType = EntryFeatureType.valueOf(featureKey);
			EntryFeatureTable feature = dataFactory.buildEntryFeatureTable(featureType, fromEndPoint, toEndPoint);
			
			feature.setFeatureDescription(description);
			if (entryFeatures == null) {
				entryFeatures = new ArrayList<EntryFeatureTable>();
			}
			entryFeatures.add(feature);
			//System.out.println(" "+feature);
		}

	}

	private static void parseCCLine(String line) {
		if (line.startsWith(UniProtFlatFileConstants.CC_LINE_TOPIC_START)) {
			// CC -!- SUBUNIT: Monomer. {ECO:0000255|HAMAP-Rule:MF_01589}.
			String[] fields = line.split(": ", 2);
			String topic = null;
			List<String> commentDescriptionList = new ArrayList<String>();
			String commentDescription = "";
			if (fields.length > 1) {
				topic = fields[0].replace(UniProtFlatFileConstants.CC_LINE_TOPIC_START, "");
				commentDescription = fields[1].trim();
			} else {
				topic = fields[0].replace(UniProtFlatFileConstants.CC_LINE_TOPIC_START, "");
				topic = topic.replaceAll("\\:$", "");
			}
			commentDescriptionList.add(commentDescription);
			EntryCommentType commentType = EntryCommentType.valueOf(topic.replaceAll(" ", "_").trim());
//			System.out.println("!! "+commentDescription);
//			System.out.println("?? "+EntryUtil.cleanup(commentDescription));
			EntryComment comment = dataFactory.buildEntryComment(commentType, commentDescriptionList);
			if (entryComments == null) {
				entryComments = new ArrayList<EntryComment>();
			}
			entryComments.add(comment);
		}
		if (line.startsWith(UniProtFlatFileConstants.CC_LINE_DESCRIPTION_START)) {
			EntryComment lastComment = entryComments.get(entryComments.size() - 1);
			String commentDescription = line.replace(UniProtFlatFileConstants.CC_LINE_DESCRIPTION_START, "");
			lastComment.getDescripitonList().add(commentDescription);
//			String lastCommentDescription = lastComment.getCommentDescription();
//			if (lastCommentDescription.length() > 0) {
//				if(lastCommentDescription.endsWith("-")) {
//					lastCommentDescription += commentDescription.trim();
//				}
//				else {
//					lastCommentDescription += " " + commentDescription.trim();
//				}
//			} else if (lastCommentDescription.length() == 0) {
//				lastCommentDescription = commentDescription.trim();
//			}
//			lastComment.setCommentDescription(lastCommentDescription);
		}

	}

	private static void parseACLine(String line) {
		accessionNumbers += line.replace(UniProtFlatFileConstants.AC_LINE_START, "");
	}

	private static void parseDRLine(String line) {
		line = line.replace(UniProtFlatFileConstants.DR_LINE_START, "");
		line = line.replaceAll("\\.$", "");
		String[] fields = line.split("; ");
		try {
		EntryDatabaseType databaseType = EntryDatabaseType.valueOf(fields[0].replaceAll("-", "_").trim());
		String identifier = fields[1];
		String optionalInfo1 = null;
		String optionalInfo2 = null;
		String optionalInfo3 = null;
		if (fields.length == 3) {
			optionalInfo1 = fields[2];
		}
		if (fields.length == 4) {
			optionalInfo1 = fields[3];
		}
		if (fields.length == 5) {
			optionalInfo1 = fields[4];
		}
		EntryDatabaseCrossReference entryDatabaseCrossReference = dataFactory.buildEntryDatabaseCrossReference(databaseType, identifier,
				optionalInfo1, optionalInfo2, optionalInfo3);
		if (entryDatabaseCrossReferences == null) {
			entryDatabaseCrossReferences = new ArrayList<EntryDatabaseCrossReference>();
		}
		entryDatabaseCrossReferences.add(entryDatabaseCrossReference);
		}
		catch (Exception e) {
			
		}
	}

	private static void parseIDLine(String line) {
		// ID EntryName Status; SequenceLength.
		String[] fields = line.split("\\s+");
		String entryName = fields[1];
		String entryStatus = fields[2].replace(";", "");
		EntryType entryType;
		if (entryStatus.equals(UniProtFlatFileConstants.SP_STATUS)) {
			entryType = EntryType.SWISSPROT;
		} else if (entryStatus.equals(UniProtFlatFileConstants.TR_STATUS)) {
			entryType = EntryType.TREMBL;
		} else {
			entryType = EntryType.UNKNOWN;
		}
		int sequenceLength = Integer.parseInt(fields[3]);
		entryIdentifier = dataFactory.buildEntryIdentifier(entryName, entryType, sequenceLength);

	}

}
