package edu.udel.bioinformatics.pirsr.prediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.proteininformationresource.pirsr.model.ControlBlock;
import org.proteininformationresource.pirsr.model.ControlStatement;
import org.proteininformationresource.pirsr.model.FTFromLine;
import org.proteininformationresource.pirsr.model.FeatureDescriptionLine;
import org.proteininformationresource.pirsr.model.FeatureType;
import org.proteininformationresource.pirsr.model.KingdomLine;
import org.proteininformationresource.pirsr.model.Line;
import org.proteininformationresource.pirsr.model.PIRRule;
import org.proteininformationresource.pirsr.model.ScopeBlock;
import org.proteininformationresource.pirsr.prediction.Alignment;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinAnnotation;
import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseCrossReference;
import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseType;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirsr.uniprot.model.UniProtEntry;

import edu.udel.bioinformatics.pirsr.PIRRuleUtil;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 27, 2015<br>
 * <br>
 * 
 * 
 * Some utility methods for prediction.
 */
public class PredictionUtil {

	static boolean debug = false;

	public static String convertTemplatePositionToTargetPosition(Alignment alignment, String position) {
		// System.out.println(alignment.getEntryId() + " ?? " +
		// alignment.getRuleAC());
		String convertedPosition = null;

		if (position.equals("Nter")) {
			// convertedPosition = "Nter";
			convertedPosition = "1";
		} else if (position.equals("Cter")) {
			convertedPosition = Integer.toString(alignment.getAlignedSeq().replaceAll("-", "").length());
		} else {

			SeqResidue[] templateResidues = alignment.getSeqResidues(alignment.getAlignedTemplateSeq());
			SeqResidue[] targetResidues = alignment.getSeqResidues(alignment.getAlignedSeq());
			// if (debug) {
			// System.out.println("Template:");
			// for (int i = 0; i < templateResidues.length; i++) {
			// System.out.print(templateResidues[i].getResidue());
			// }
			// //System.out.println("\n");
			// System.out.println("\nTarget:");
			// for (int i = 0; i < targetResidues.length; i++) {
			// System.out.print(targetResidues[i].getResidue());
			// }
			// System.out.println();
			// }
			int positionInAlignment = -1;
			if (templateResidues.length > 0) {
				for (int i = 0; i < templateResidues.length; i++) {
					SeqResidue residue = templateResidues[i];
					// if (debug) {
					// System.out.println(residue.getResidue() + " | " +
					// residue.getPositionInAlignment() + " | " +
					// residue.getPositionInSequence());
					// }
					if (residue.getPositionInSequence() == Integer.parseInt(position)) {
						positionInAlignment = i;
						break;
					}
				}
				// if (debug) {
				// System.out.println(alignment.getEntryId() + " " + position +
				// " ?? " + positionInAlignment);
				// }
				convertedPosition = Integer.toString(targetResidues[positionInAlignment].getPositionInSequence());
			} else {
				System.err.println("Template " + alignment.getTemplateEntryAC() + " sequence is missing, program quit.");
				System.exit(1);
			}
		}
		return convertedPosition;
	}

	public static String convertTargetPositionToTemplatePosition(Alignment alignment, String position) {
		String convertedPosition = null;

		if (position.equals("Nter")) {
			// convertedPosition = "Nter";
			convertedPosition = "1";
		} else if (position.equals("Cter")) {
			convertedPosition = Integer.toString(alignment.getAlignedTemplateSeq().replaceAll("-", "").length());
		} else {
			SeqResidue[] templateResidues = alignment.getSeqResidues(alignment.getAlignedTemplateSeq());
			SeqResidue[] targetResidues = alignment.getSeqResidues(alignment.getAlignedSeq());
			int positionInAlignment = -1;
			for (int i = 0; i < targetResidues.length; i++) {
				SeqResidue residue = targetResidues[i];
				if (residue.getPositionInSequence() == Integer.parseInt(position)) {
					positionInAlignment = i;
					break;
				}
			}

			convertedPosition = Integer.toString(templateResidues[positionInAlignment].getPositionInSequence());
		}
		return convertedPosition;
	}



	public static String getAlignedEntryResidues(Alignment alignment, String start, String end) {
		int alignedResiduesStart = -1;
		int alignedResiduesEnd = -1;

		if (start.equals("Nter")) {
			alignedResiduesStart = 1;
		} else {
			alignedResiduesStart = Integer.parseInt(start);
		}
		if (end.equals("Cter")) {
			alignedResiduesEnd = alignment.getAlignedSeq().replaceAll("-", "").length();
		} else {
			alignedResiduesEnd = Integer.parseInt(end);
		}
		// char[] residues = new char[templateResidueEnd - templateResidueStart
		// + 1];
		String residues = "";
		// char[] templateSeqChars =
		// alignment.getAlignedTemplateSeq().toCharArray();
		char[] alignedEntrySeqChars = alignment.getAlignedSeq().toCharArray();
		int residueIndex = 0;
		for (int i = 0; i < alignedEntrySeqChars.length; i++) {
			if (alignedEntrySeqChars[i] != '-') {
				residueIndex++;
				if (residueIndex >= alignedResiduesStart && residueIndex <= alignedResiduesEnd) {
					residues += alignedEntrySeqChars[i];
				}
			}
		}
		return residues;
	}

	public static String getTargetResiduesInAlignment(Alignment alignment, String start, String end) {
		int templateResiduesStart = -1;
		int templateResiduesEnd = -1;

		if (start.equals("Nter")) {
			templateResiduesStart = 1;
		} else {
			templateResiduesStart = Integer.parseInt(start);
		}
		if (end.equals("Cter")) {
			templateResiduesEnd = alignment.getAlignedSeq().replaceAll("-", "").length();
		} else {
			templateResiduesEnd = Integer.parseInt(end);
		}
		String residues = "";
		char[] templateSeqChars = alignment.getAlignedSeq().toCharArray();

		int residueIndex = 0;
		boolean seqStart = false;
		for (int i = 0; i < templateSeqChars.length; i++) {
			if (templateSeqChars[i] != '-') {
				residueIndex++;
				if (residueIndex >= templateResiduesStart && residueIndex <= templateResiduesEnd) {
					seqStart = true;
					// residues += templateSeqChars[i];
				} else {
					seqStart = false;
				}
			}
			if (seqStart) {
				residues += templateSeqChars[i];
			}
		}
		return residues;
	}

	public static String getTemplateResiduesInAlignment(Alignment alignment, String start, String end) {
		int templateResiduesStart = -1;
		int templateResiduesEnd = -1;

		if (start.equals("Nter")) {
			templateResiduesStart = 1;
		} else {
			templateResiduesStart = Integer.parseInt(start);
		}
		if (end.equals("Cter")) {
			templateResiduesEnd = alignment.getAlignedTemplateSeq().replaceAll("-", "").length();
		} else {
			templateResiduesEnd = Integer.parseInt(end);
		}
		String residues = "";
		char[] templateSeqChars = alignment.getAlignedTemplateSeq().toCharArray();

		int residueIndex = 0;
		boolean seqStart = false;
		for (int i = 0; i < templateSeqChars.length; i++) {
			if (templateSeqChars[i] != '-') {
				residueIndex++;
				if (residueIndex >= templateResiduesStart && residueIndex <= templateResiduesEnd) {
					seqStart = true;
					// residues += templateSeqChars[i];
				} else {
					seqStart = false;
				}
			}
			if (seqStart) {
				residues += templateSeqChars[i];
			}
		}
		return residues;
	}

	public static String getTemplateResidues(Alignment alignment, String start, String end) {
		int templateResiduesStart = -1;
		int templateResiduesEnd = -1;

		if (start.equals("Nter")) {
			templateResiduesStart = 1;
		} else {
			templateResiduesStart = Integer.parseInt(start);
		}
		if (end.equals("Cter")) {
			templateResiduesEnd = alignment.getAlignedTemplateSeq().replaceAll("-", "").length();
		} else {
			templateResiduesEnd = Integer.parseInt(end);
		}
		String residues = "";
		char[] templateSeqChars = alignment.getAlignedTemplateSeq().toCharArray();

		int residueIndex = 0;
		for (int i = 0; i < templateSeqChars.length; i++) {
			if (templateSeqChars[i] != '-') {
				residueIndex++;
				if (residueIndex >= templateResiduesStart && residueIndex <= templateResiduesEnd) {
					residues += templateSeqChars[i];
				}
			}
		}
		return residues;
	}

	private static FeatureDescriptionMatch getFeatureDescriptionMatch(ProteinAnnotation protein, Alignment alignment,
			FeatureDescriptionLine featureDescriptionLine, Integer ftGroupNumber) {
		FeatureDescriptionMatch featureDescriptionMatch = new FeatureDescriptionMatch();
		String note = "";

		String alignedSeqStart = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureFromPosition());
		String alignedSeqEnd = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureToPosition());
		String alignedSeqResidues = getAlignedEntryResidues(alignment, alignedSeqStart, alignedSeqEnd);

		String conditionPattern = featureDescriptionLine.getFeatureConditionPattern();

		boolean patternMatch = checkPatternMatch(alignedSeqResidues, conditionPattern);

		note += "Template (FTGroup " + ftGroupNumber + "): " + featureDescriptionLine.getFeatureFromPosition() + "| "
				+ featureDescriptionLine.getFeatureToPosition() + " | "
				+ getTemplateResidues(alignment, featureDescriptionLine.getFeatureFromPosition(), featureDescriptionLine.getFeatureToPosition())
				+ " | " + alignment.getTemplateEntryAC() + " | " + featureDescriptionLine.getFeatureConditionPattern() + "<-|->";
		note += "Target: " + alignedSeqStart + "| " + alignedSeqEnd + " | " + alignedSeqResidues + " | " + protein.getProteinId() + " | "
				+ patternMatch + "<-|->";

		if (alignedSeqStart.equals("-1") || alignedSeqEnd.equals("-1")) {
			note += "Template start or end residue does not match<-|->";
			featureDescriptionMatch.setFeatureDescriptionMatch(false);
		} else {
			featureDescriptionMatch.setFeatureDescriptionMatch(patternMatch);
		}
		featureDescriptionMatch.setNote(note);
		return featureDescriptionMatch;

	}

	public static boolean checkFeaturePatternMatch(ProteinAnnotation protein, Alignment alignment, FeatureDescriptionLine featureDescriptionLine) {
		// System.out.println(protein.getProteinId()+"????");
		String alignedSeqStart = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureFromPosition());
		String alignedSeqEnd = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureToPosition());
		String alignedSeqResidues = getAlignedEntryResidues(alignment, alignedSeqStart, alignedSeqEnd);

		String conditionPattern = featureDescriptionLine.getFeatureConditionPattern();
		if (debug) {
			System.out.println(featureDescriptionLine.getFeatureFromPosition() + "| " + featureDescriptionLine.getFeatureToPosition() + " | "
					+ getTemplateResidues(alignment, featureDescriptionLine.getFeatureFromPosition(), featureDescriptionLine.getFeatureToPosition())
					+ " | " + alignment.getTemplateEntryAC() + " | " + featureDescriptionLine.getFeatureConditionPattern());
			System.out.println(alignedSeqStart + "| " + alignedSeqEnd + " | " + alignedSeqResidues + " | " + protein.getProteinId());

		}

		return checkPatternMatch(alignedSeqResidues, conditionPattern);
	}

	public static Boolean checkPatternMatch(String alignedSeqResidues, String conditionPattern) {
		String[] patterns = conditionPattern.split("-");
		String matchPattern = "";
		for (int i = 0; i < patterns.length; i++) {
			String pattern = patterns[i];
			if (pattern.contains("x")) {
				// pattern = pattern.replaceAll("\\(", "{");
				// pattern = pattern.replaceAll("\\)", ",}");
				pattern = pattern.replaceAll("\\(\\d+\\)", "*");
				pattern = pattern.replaceAll("x", "[A-Z]");
			}
			matchPattern += pattern;
		}
		if (debug) {
			System.out.println(matchPattern + " | " + alignedSeqResidues + " | " + alignedSeqResidues.matches(matchPattern));
			System.out.println();
		}
		return alignedSeqResidues.matches(matchPattern);
	}

	public static FTGroupMatch getFTGroupMatch(ProteinAnnotation protein, Alignment alignment, PIRRule rule) {
		FTGroupMatch ftGroupMatchResult = new FTGroupMatch();
		String note = "";
		Map<Integer, Boolean> ftGroupMatches = new HashMap<Integer, Boolean>();
		PIRRuleUtil pirruleUtil = rule.getPIRRuleManager().getDataFactory().buidPIRRuleUtil(rule);
		List<ControlBlock> ftControlBlocks = pirruleUtil.getFTLineControlBlocks();

		for (ControlBlock ftControlBlock : ftControlBlocks) {
			List<ControlStatement> controlStatements = ftControlBlock.getControlStatements();
			for (ControlStatement controlStatement : controlStatements) {
				List<Line> applicableLines = controlStatement.getApplicableLines();
				for (Line line : applicableLines) {
					if (line instanceof FeatureDescriptionLine) {
						FeatureDescriptionLine featureDescriptionLine = (FeatureDescriptionLine) line;
						Integer ftGroupNumber = new Integer(featureDescriptionLine.getFeatureGroupNumber());

						FeatureDescriptionMatch featureDescriptionMatchResult = getFeatureDescriptionMatch(protein, alignment,
								featureDescriptionLine, ftGroupNumber);
						Boolean featureDescriptionMatch = featureDescriptionMatchResult.isFeatureDescriptionMatch();
						String featureDescriptionMatchNote = featureDescriptionMatchResult.getNote();
						note += featureDescriptionMatchNote;
						if (ftGroupMatches.get(ftGroupNumber) == null) {
							ftGroupMatches.put(ftGroupNumber, featureDescriptionMatch);
						} else {
							ftGroupMatches.put(ftGroupNumber, featureDescriptionMatch && ftGroupMatches.get(ftGroupNumber));
						}
					}
				}

			}
		}
		ftGroupMatchResult.setFtGroups(ftGroupMatches);
		ftGroupMatchResult.setNote(note);
		return ftGroupMatchResult;
	}

	public static Map<Integer, Boolean> checkFTGroupMatch(ProteinAnnotation protein, Alignment alignment, PIRRule rule) {
		Map<Integer, Boolean> ftGroupMatches = new HashMap<Integer, Boolean>();
		PIRRuleUtil pirruleUtil = rule.getPIRRuleManager().getDataFactory().buidPIRRuleUtil(rule);
		List<ControlBlock> ftControlBlocks = pirruleUtil.getFTLineControlBlocks();

		for (ControlBlock ftControlBlock : ftControlBlocks) {
			List<ControlStatement> controlStatements = ftControlBlock.getControlStatements();
			for (ControlStatement controlStatement : controlStatements) {
				List<Line> applicableLines = controlStatement.getApplicableLines();
				for (Line line : applicableLines) {
					if (line instanceof FeatureDescriptionLine) {
						FeatureDescriptionLine featureDescriptionLine = (FeatureDescriptionLine) line;
						Integer ftGroupNumber = new Integer(featureDescriptionLine.getFeatureGroupNumber());
						Boolean featureDescriptionMatch = checkFeaturePatternMatch(protein, alignment, featureDescriptionLine);

						if (ftGroupMatches.get(ftGroupNumber) == null) {
							ftGroupMatches.put(ftGroupNumber, featureDescriptionMatch);
						} else {
							ftGroupMatches.put(ftGroupNumber, featureDescriptionMatch && ftGroupMatches.get(ftGroupNumber));
						}
					}
				}

			}
		}
		return ftGroupMatches;
	}

	public static boolean checkNonControlBlockScope(PIRRule rule, ProteinAnnotation protein) {
		boolean eVal = true;

		List<String> ncbiTaxonList = protein.getOrganismClassification();
		// System.out.println(protein.getProteinId() + " | " + ncbiTaxonList);
		List<String> ncbiTaxonStrList = new ArrayList<String>();
		for (String ncbiTaxon : ncbiTaxonList) {
			ncbiTaxonStrList.add(ncbiTaxon);
		}
		PIRRuleUtil pirruleUtil = rule.getPIRRuleManager().getDataFactory().buidPIRRuleUtil(rule);
		List<Line> scopeBlocks = pirruleUtil.getNonControlBlockScopeBlockLines();

		List<KingdomLine> kingdomLines = new ArrayList<KingdomLine>();
		for (Line line : scopeBlocks) {
			if (line instanceof ScopeBlock) {
				kingdomLines.addAll(((ScopeBlock) line).getKingdomLines());

			}
		}
		
		for (KingdomLine kingdomLine : kingdomLines) {
			eVal = false;
			if (debug) {
				System.out.println(protein.getProteinId() + ": " + kingdomLine.getKingdom() + "|" + kingdomLine.getKingdomSubTaxon() + "|"
						+ kingdomLine.getExceptTaxonomicGroups());
			}

			if (kingdomLine.getKingdom() != null) {
				if (kingdomLine.getKingdomSubTaxon() != null) {
					if (ncbiTaxonStrList.contains(kingdomLine.getKingdom()) && ncbiTaxonStrList.contains(kingdomLine.getKingdomSubTaxon())) {
						eVal = true;
					}
				}
				if (ncbiTaxonStrList.contains(kingdomLine.getKingdom())) {
					eVal = true;
				}
				if (kingdomLine.getExceptTaxonomicGroups() != null) {
					eVal = eVal && checkExceptTaxonomicGroups(ncbiTaxonStrList, kingdomLine.getExceptTaxonomicGroups());
				}
			}

			if (eVal) {
				if (debug) {
					System.out.println(protein.getProteinId() + " Final eVal: " + eVal);
				}
				return eVal;
			}
		}

		// System.out.println(protein.getProteinId() + " Final eVal: " + eVal);
		return eVal;
	}

	private static boolean checkExceptTaxonomicGroups(List<String> ncbiTaxonStrList, List<String> exceptTaxonomicGroups) {
		boolean eVal = true;

		for (String exceptTaxonGroup : exceptTaxonomicGroups) {
			if (ncbiTaxonStrList.contains(exceptTaxonGroup)) {
				eVal = false;
			}
		}
		return eVal;
	}

	public static boolean checkTrigger(PIRRule rule, UniProtEntry entry) {
		boolean eVal = true;
		String trigger = rule.getHeaderSection().getTRLine().getIdentifier1();
		if (trigger == null) {
			trigger = rule.getHeaderSection().getTRLine().getIdentifier2();
		}
		if (trigger == null) {
			eVal = false;
		} else {
			List<EntryDatabaseCrossReference> pirsfs = entry.getDatabaseCrossReferences(EntryDatabaseType.PIRSF);
			for (EntryDatabaseCrossReference pirsf : pirsfs) {
				if (pirsf.getIdentifier().equals(trigger)) {
					eVal = true;
				}
			}
		}

		return eVal;

	}

	public static boolean checkTemplate(Alignment alignment, PIRRule rule) {
		boolean eVal = false;
		String ruleTemplate = null;

		List<Line> lines = rule.getAnnotationSection().getLines();
		for (Line line : lines) {
			if (line instanceof FTFromLine) {
				FTFromLine ftFromLine = (FTFromLine) line;
				ruleTemplate = ftFromLine.getFTTemplateAccessionNumber();
			}
		}
		// System.out.println(alignment.getEntryId());
		if (alignment != null && alignment.getTemplateEntryAC() != null && alignment.getTemplateEntryAC().equals(ruleTemplate)) {
			eVal = true;
		}
		return eVal;
	}

	public static String getMatchInfo(ProteinAnnotation protein, Alignment alignment, FeatureDescriptionLine featureDescriptionLine) {
		String alignedSeqStart = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureFromPosition());
		String alignedSeqEnd = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureToPosition());
		String alignedSeqResidues = getAlignedEntryResidues(alignment, alignedSeqStart, alignedSeqEnd);
		String conditionPattern = featureDescriptionLine.getFeatureConditionPattern();
		String match = "";
		match += "Rule: " + alignment.getTemplateEntryAC() + " | " + featureDescriptionLine.getFeatureFromPosition() + "(s) | "
				+ featureDescriptionLine.getFeatureToPosition() + "(e) | "
				+ getTemplateResidues(alignment, featureDescriptionLine.getFeatureFromPosition(), featureDescriptionLine.getFeatureToPosition())
				+ "(Residue) | " + conditionPattern + "(Pattern) | " + featureDescriptionLine.getFeatureKey() + "(Key) | "
				+ featureDescriptionLine.getFeatureDescription() + "<-|->" + "Entry: " + protein.getProteinId() + " | " + alignedSeqStart + "(s) | "
				+ alignedSeqEnd + "(e) | " + alignedSeqResidues + "(Residue) | ";
		String note = "";
		// if (!getFeatureType(protein, alignedSeqStart, alignedSeqEnd,
		// featureDescriptionLine.getFeatureKey()).equals("")) {
		// if (!checkPatternMatch(alignedSeqResidues, conditionPattern)) {
		// note += "Residue pattern does not match<-|->";
		// } else {
		// if (!getFeatureType(protein, alignedSeqStart, alignedSeqEnd,
		// featureDescriptionLine.getFeatureKey()).equals(
		// featureDescriptionLine.getFeatureKey().name())) {
		// note += "Feature key does not match<-|->";
		// } else {
		// if (!getFeatureDescription(protein, alignedSeqStart, alignedSeqEnd,
		// featureDescriptionLine.getFeatureKey()).contains(
		// featureDescriptionLine.getFeatureDescription())) {
		// note += "Feature description does not match<-|->";
		// }
		// }
		// }
		// } else
		{
			if (!checkPatternMatch(alignedSeqResidues, conditionPattern)) {
				note += "Residue pattern does not match<-|->";
			}
			// note += "Feature Key \"" + featureDescriptionLine.getFeatureKey()
			// + "\" not found at (" + alignedSeqStart + ", " + alignedSeqEnd
			// + ")<-|->";
			if (alignedSeqStart.equals("-1") || alignedSeqEnd.equals("-1")) {
				note += "Template start or end residue does not match<-|->";
			}
		}
		return note + match;
	}

	private static String getFeatureType(ProteinAnnotation protein, String alignedSeqStart, String alignedSeqEnd, FeatureType ruleFeatureType) {
		String featureType = "";
		if (debug) {
			System.out.println(protein.getProteinId() + " | " + alignedSeqStart + " | " + alignedSeqEnd);
		}
		// for (EntryFeatureTable entryFeatureTable : entry.getFeatures()) {
		// if (entryFeatureTable.getFromEndPoint().equals(alignedSeqStart) &&
		// entryFeatureTable.getToEndPoint().equals(alignedSeqEnd)) {
		// if
		// (entryFeatureTable.getFeatureType().name().equals(ruleFeatureType.name()))
		// {
		// return ruleFeatureType.name();
		// } else {
		// featureType = entryFeatureTable.getFeatureType().name();
		// }
		// }
		// }

		return featureType;
	}

	private static String getFeatureDescription(ProteinAnnotation protein, String alignedSeqStart, String alignedSeqEnd, FeatureType featureType) {
		String description = "";
		// for (EntryFeatureTable entryFeatureTable : entry.getFeatures()) {
		// if (entryFeatureTable.getFromEndPoint().equals(alignedSeqStart) &&
		// entryFeatureTable.getToEndPoint().equals(alignedSeqEnd)) {
		// if
		// (entryFeatureTable.getFeatureType().name().equals(featureType.name()))
		// {
		// if (description.equals("")) {
		// description += entryFeatureTable.getFeatureDescription();
		// } else {
		// description += " ^|^ " + entryFeatureTable.getFeatureDescription();
		// }
		// }
		// }
		// }
		return description;
	}

}
