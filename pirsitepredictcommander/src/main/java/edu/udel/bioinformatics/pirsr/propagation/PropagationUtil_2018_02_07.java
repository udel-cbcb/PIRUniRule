package edu.udel.bioinformatics.pirsr.propagation;

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
import org.proteininformationresource.pirsr.propagation.Alignment;
import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseCrossReference;
import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseType;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirsr.uniprot.model.UniProtEntry;

import edu.udel.bioinformatics.pirsr.PIRRuleUtil;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 10, 2014<br>
 * <br>
 * 
 * 
 * Some utility methods for propagation.
 */
public class PropagationUtil_2018_02_07 {

	static boolean debug = false;

	public static String convertTemplatePositionToTargetPosition(Alignment alignment, String position) {

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

	/*
	 * public static int getAlignedEntryResiduePosition(Alignment alignment,
	 * String pos) { int alignedPos = -1;
	 * 
	 * if (pos.equals("Nter")) { return 1; } if (pos.equals("Cter")) { return
	 * alignment.getAlignedSeq().replaceAll("-", "").length(); }
	 * 
	 * char[] templateSeqChars =
	 * alignment.getAlignedTemplateSeq().toCharArray(); int residueIndex = 0;
	 * 
	 * for (int i = 0; i < templateSeqChars.length; i++) { if
	 * (templateSeqChars[i] != '-') { residueIndex++; if (residueIndex ==
	 * Integer.parseInt(pos)) { alignedPos =
	 * alignment.getAlignedSeq().substring(0, i + 1).replaceAll("-",
	 * "").length(); // System.out.println((i+1)+" | " + //
	 * alignment.getAlignedSeq().substring(0, // (i+1)).replaceAll("-", "") +
	 * " | " + // alignment.getAlignedSeq().substring(0, //
	 * (i+1)).replaceAll("-", "").length()); break; } } } return alignedPos; }
	 */
	// public static String getAlignedEntryResidues(Alignment alignment, String
	// start, String end) {
	// SeqResidue[] residues =
	// alignment.getSeqResidues(alignment.getAlignedSeq());
	//
	// }

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
		// System.out.println(alignment.getAlignedTemplateSeq().replaceAll("-",
		// ""));

		int residueIndex = 0;
		for (int i = 0; i < templateSeqChars.length; i++) {
			if (templateSeqChars[i] != '-') {
				residueIndex++;
				if (residueIndex >= templateResiduesStart && residueIndex <= templateResiduesEnd) {
					residues += templateSeqChars[i];
				}
			}
		}
		if (debug) {
			System.out.println(start + "|" + end + "|" + residues + "|");
		}
		return residues;
	}

	private static FeatureDescriptionMatch getFeatureDescriptionMatch(UniProtEntry entry, Alignment alignment, FeatureDescriptionLine featureDescriptionLine,
			Integer ftGroupNumber) {
		FeatureDescriptionMatch featureDescriptionMatch = new FeatureDescriptionMatch();
		String note = "";

		String alignedSeqStart = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureFromPosition());
		String alignedSeqEnd = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureToPosition());
		String conditionPattern = featureDescriptionLine.getFeatureConditionPattern();

		if (featureDescriptionLine.getFeatureKey().toString().equals("CARBOHYD") && conditionPattern.equals("N-x-T")) {
			String adjustedAlignedSeqEnd = String.valueOf(Integer.parseInt(alignedSeqStart) + 2);

			String alignedSeqResidues = getAlignedEntryResidues(alignment, alignedSeqStart, adjustedAlignedSeqEnd);

			boolean patternMatch = checkPatternMatch(alignedSeqResidues, conditionPattern);

			note += "Template (FTGroup " + ftGroupNumber + "): " + featureDescriptionLine.getFeatureFromPosition() + "| "
					+ featureDescriptionLine.getFeatureToPosition() + " | "
					+ getTemplateResidues(alignment, featureDescriptionLine.getFeatureFromPosition(), featureDescriptionLine.getFeatureToPosition()) + " | "
					+ alignment.getTemplateEntryAC() + " | " + featureDescriptionLine.getFeatureConditionPattern() + "<-|->";
			note += "Target: " + alignedSeqStart + "| " + alignedSeqEnd + " | " + alignedSeqResidues + " | " + entry.getPrimiaryAccessionNumber() + " | "
					+ "pattern match: " + patternMatch + "<-|->";

			if (alignedSeqStart.equals("-1") || alignedSeqEnd.equals("-1")) {
				note += "Template start or end residue does not match<-|->";
				featureDescriptionMatch.setFeatureDescriptionMatch(false);
			} else {
				featureDescriptionMatch.setFeatureDescriptionMatch(patternMatch);
			}
			featureDescriptionMatch.setNote(note);
		} else {

			String alignedSeqResidues = getAlignedEntryResidues(alignment, alignedSeqStart, alignedSeqEnd);

			boolean patternMatch = checkPatternMatch(alignedSeqResidues, conditionPattern);

			note += "Template (FTGroup " + ftGroupNumber + "): " + featureDescriptionLine.getFeatureFromPosition() + "| "
					+ featureDescriptionLine.getFeatureToPosition() + " | "
					+ getTemplateResidues(alignment, featureDescriptionLine.getFeatureFromPosition(), featureDescriptionLine.getFeatureToPosition()) + " | "
					+ alignment.getTemplateEntryAC() + " | " + featureDescriptionLine.getFeatureConditionPattern() + "<-|->";
			note += "Target: " + alignedSeqStart + "| " + alignedSeqEnd + " | " + alignedSeqResidues + " | " + entry.getPrimiaryAccessionNumber() + " | "
					+ "pattern match: " + patternMatch + "<-|->";

			if (alignedSeqStart.equals("-1") || alignedSeqEnd.equals("-1")) {
				note += "Template start or end residue does not match<-|->";
				featureDescriptionMatch.setFeatureDescriptionMatch(false);
			} else {
				featureDescriptionMatch.setFeatureDescriptionMatch(patternMatch);
			}
			featureDescriptionMatch.setNote(note);
		}
		return featureDescriptionMatch;

	}

	public static boolean checkFeaturePatternMatch(UniProtEntry entry, Alignment alignment, FeatureDescriptionLine featureDescriptionLine) {

		String alignedSeqStart = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureFromPosition());
		String alignedSeqEnd = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureToPosition());
		String conditionPattern = featureDescriptionLine.getFeatureConditionPattern();
		String alignedSeqResidues = "";
		if (featureDescriptionLine.getFeatureKey().toString().equals("CARBOHYD") && conditionPattern.equals("N-x-T")) {
			String adjustedAlignedSeqEnd = String.valueOf(Integer.parseInt(alignedSeqStart) + 2);

			alignedSeqResidues = getAlignedEntryResidues(alignment, alignedSeqStart, adjustedAlignedSeqEnd);

			if (debug) {
				System.out.println(featureDescriptionLine.getFeatureFromPosition() + "| " + featureDescriptionLine.getFeatureToPosition() + " | "
						+ getTemplateResidues(alignment, featureDescriptionLine.getFeatureFromPosition(), featureDescriptionLine.getFeatureToPosition())
						+ " | " + alignment.getTemplateEntryAC() + " | " + featureDescriptionLine.getFeatureConditionPattern());
				System.out.println(alignedSeqStart + "| " + alignedSeqEnd + " | " + alignedSeqResidues + " | " + entry.getPrimiaryAccessionNumber());

			}
		}
		else {
			alignedSeqResidues = getAlignedEntryResidues(alignment, alignedSeqStart, alignedSeqEnd);

			if (debug) {
				System.out.println(featureDescriptionLine.getFeatureFromPosition() + "| " + featureDescriptionLine.getFeatureToPosition() + " | "
						+ getTemplateResidues(alignment, featureDescriptionLine.getFeatureFromPosition(), featureDescriptionLine.getFeatureToPosition())
						+ " | " + alignment.getTemplateEntryAC() + " | " + featureDescriptionLine.getFeatureConditionPattern());
				System.out.println(alignedSeqStart + "| " + alignedSeqEnd + " | " + alignedSeqResidues + " | " + entry.getPrimiaryAccessionNumber());

			}
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

	public static FTGroupMatch getFTGroupMatch(UniProtEntry entry, Alignment alignment, PIRRule rule) {
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

						FeatureDescriptionMatch featureDescriptionMatchResult = getFeatureDescriptionMatch(entry, alignment, featureDescriptionLine,
								ftGroupNumber);
						Boolean featureDescriptionMatch = featureDescriptionMatchResult.isFeatureDescriptionMatch();
						String featureDescriptionMatchNote = featureDescriptionMatchResult.getNote();
						// if(debug) {
						// System.out.println("feataure description match note: "
						// + featureDescriptionMatchNote);
						// }
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
		if(debug) {
			System.out.println("ftGroupMatches: " +ftGroupMatches);
		}
		ftGroupMatchResult.setFtGroups(ftGroupMatches);
		ftGroupMatchResult.setNote(note);
		return ftGroupMatchResult;
	}

	public static Map<Integer, Boolean> checkFTGroupMatch(UniProtEntry entry, Alignment alignment, PIRRule rule) {
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
						Boolean featureDescriptionMatch = checkFeaturePatternMatch(entry, alignment, featureDescriptionLine);

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

	public static boolean checkNonControlBlockScope(PIRRule rule, UniProtEntry entry) {
		boolean eVal = true;

		List<String> ncbiTaxonList = entry.getOrganismClassification().getOrganismClassification();
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
				System.out.println(entry.getPrimiaryAccessionNumber() + ": " + kingdomLine.getKingdom() + "|" + kingdomLine.getKingdomSubTaxon() + "|"
						+ kingdomLine.getExceptTaxonomicGroups());
			}
			// if (kingdomLine.getKingdom() != null) {
			// eVal = eVal ||
			// ncbiTaxonStrList.contains(kingdomLine.getKingdom());
			// }
			// if (kingdomLine.getKingdomSubTaxon() != null) {
			// eVal = eVal ||
			// ncbiTaxonStrList.contains(kingdomLine.getKingdomSubTaxon());
			// }
			// if (kingdomLine.getExceptTaxonomicGroups() != null) {
			// eVal = eVal || checkExceptTaxonomicGroups(ncbiTaxonStrList,
			// kingdomLine.getExceptTaxonomicGroups());
			// }
			if (kingdomLine.getKingdom() != null) {
				if (kingdomLine.getKingdomSubTaxon() != null) {
					if (ncbiTaxonStrList.contains(kingdomLine.getKingdom()) && ncbiTaxonStrList.contains(kingdomLine.getKingdomSubTaxon())) {
						eVal = true;
					}
				} else if (ncbiTaxonStrList.contains(kingdomLine.getKingdom())) {
					eVal = true;
				}
				if (kingdomLine.getExceptTaxonomicGroups() != null) {
					eVal = eVal && checkExceptTaxonomicGroups(ncbiTaxonStrList, kingdomLine.getExceptTaxonomicGroups());
				}
			}

			if (eVal) {
				if (debug) {
					System.out.println(entry.getPrimiaryAccessionNumber() + " Final eVal: " + eVal);
				}
				return eVal;
			}
		}
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

	public static String getMatchInfo(UniProtEntry entry, Alignment alignment, FeatureDescriptionLine featureDescriptionLine) {
		String alignedSeqStart = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureFromPosition());
		String alignedSeqEnd = convertTemplatePositionToTargetPosition(alignment, featureDescriptionLine.getFeatureToPosition());
		String conditionPattern = featureDescriptionLine.getFeatureConditionPattern();
		String note = "";
		String match = "";
		if (featureDescriptionLine.getFeatureKey().toString().equals("CARBOHYD") && conditionPattern.equals("N-x-T")) {
			String adjustedAlignedSeqEnd = String.valueOf(Integer.parseInt(alignedSeqStart) + 2);

			String alignedSeqResidues = getAlignedEntryResidues(alignment, alignedSeqStart, adjustedAlignedSeqEnd);
			match += "Rule: " + alignment.getTemplateEntryAC() + " | " + featureDescriptionLine.getFeatureFromPosition() + "(s) | "
					+ featureDescriptionLine.getFeatureToPosition() + "(e) | "
					+ getTemplateResidues(alignment, featureDescriptionLine.getFeatureFromPosition(), featureDescriptionLine.getFeatureToPosition())
					+ "(Residue) | " + conditionPattern + "(Pattern) | " + featureDescriptionLine.getFeatureKey() + "(Key) | "
					+ featureDescriptionLine.getFeatureDescription() + "<-|->" + "Entry: " + entry.getPrimiaryAccessionNumber() + " | " + alignedSeqStart
					+ "(s) | " + alignedSeqEnd + "(e) | " + alignedSeqResidues + "(Residue) | "
					+ getFeatureType(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey()) + "(Key) | "
					+ getFeatureDescription(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey());

			if (!getFeatureType(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey()).equals("")) {
				if (!checkPatternMatch(alignedSeqResidues, conditionPattern)) {
					note += "Residue pattern does not match<-|->";
				} else {
					if (!getFeatureType(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey()).equals(
							featureDescriptionLine.getFeatureKey().name())) {
						note += "Feature key does not match<-|->";
					} else {
						String featureDescriptionStr = getFeatureDescription(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey());
						boolean found = false;
						if (featureDescriptionStr.contains(" ^|^ ")) {
							String[] featureDescriptionArr = featureDescriptionStr.split(" ^|^ ");
							for (String fd : featureDescriptionArr) {
								if (fd.equals(featureDescriptionLine.getFeatureDescription())) {
									found = true;
								}
							}
						} else {
							if (getFeatureDescription(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey()).equals(
									featureDescriptionLine.getFeatureDescription())) {
								found = true;
							}
						}
						if (!found) {
							note += "Feature description does not match<-|->";
						}
					}
				}
			} else {
				if (!checkPatternMatch(alignedSeqResidues, conditionPattern)) {
					note += "Residue pattern does not match<-|->";
				}
				note += "Feature Key \"" + featureDescriptionLine.getFeatureKey() + "\" not found at (" + alignedSeqStart + ", " + alignedSeqEnd + ")<-|->";
				if (alignedSeqStart.equals("-1") || alignedSeqEnd.equals("-1")) {
					note += "Template start or end residue does not match<-|->";
				}
			}
		} else {

			String alignedSeqResidues = getAlignedEntryResidues(alignment, alignedSeqStart, alignedSeqEnd);
			match += "Rule: " + alignment.getTemplateEntryAC() + " | " + featureDescriptionLine.getFeatureFromPosition() + "(s) | "
					+ featureDescriptionLine.getFeatureToPosition() + "(e) | "
					+ getTemplateResidues(alignment, featureDescriptionLine.getFeatureFromPosition(), featureDescriptionLine.getFeatureToPosition())
					+ "(Residue) | " + conditionPattern + "(Pattern) | " + featureDescriptionLine.getFeatureKey() + "(Key) | "
					+ featureDescriptionLine.getFeatureDescription() + "<-|->" + "Entry: " + entry.getPrimiaryAccessionNumber() + " | " + alignedSeqStart
					+ "(s) | " + alignedSeqEnd + "(e) | " + alignedSeqResidues + "(Residue) | "
					+ getFeatureType(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey()) + "(Key) | "
					+ getFeatureDescription(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey());

			if (!getFeatureType(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey()).equals("")) {
				if (!checkPatternMatch(alignedSeqResidues, conditionPattern)) {
					note += "Residue pattern does not match<-|->";
				} else {
					if (!getFeatureType(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey()).equals(
							featureDescriptionLine.getFeatureKey().name())) {
						note += "Feature key does not match<-|->";
					} else {
						String featureDescriptionStr = getFeatureDescription(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey());
						boolean found = false;
						if (featureDescriptionStr.contains(" ^|^ ")) {
							String[] featureDescriptionArr = featureDescriptionStr.split(" ^|^ ");
							for (String fd : featureDescriptionArr) {
								if (fd.equals(featureDescriptionLine.getFeatureDescription())) {
									found = true;
								}
							}
						} else {
							if (getFeatureDescription(entry, alignedSeqStart, alignedSeqEnd, featureDescriptionLine.getFeatureKey()).equals(
									featureDescriptionLine.getFeatureDescription())) {
								found = true;
							}
						}
						if (!found) {
							note += "Feature description does not match<-|->";
						}
					}
				}
			} else {
				if (!checkPatternMatch(alignedSeqResidues, conditionPattern)) {
					note += "Residue pattern does not match<-|->";
				}
				note += "Feature Key \"" + featureDescriptionLine.getFeatureKey() + "\" not found at (" + alignedSeqStart + ", " + alignedSeqEnd + ")<-|->";
				if (alignedSeqStart.equals("-1") || alignedSeqEnd.equals("-1")) {
					note += "Template start or end residue does not match<-|->";
				}
			}

		}
		return note + match;
	}

	private static String getFeatureType(UniProtEntry entry, String alignedSeqStart, String alignedSeqEnd, FeatureType ruleFeatureType) {
		String featureType = "";
		if (debug) {
			System.out.println(entry.getPrimiaryAccessionNumber() + " | " + alignedSeqStart + " | " + alignedSeqEnd);
		}
		for (EntryFeatureTable entryFeatureTable : entry.getFeatures()) {
			if (entryFeatureTable.getFromEndPoint().equals(alignedSeqStart) && entryFeatureTable.getToEndPoint().equals(alignedSeqEnd)) {
				if (entryFeatureTable.getFeatureType().name().equals(ruleFeatureType.name())) {
					return ruleFeatureType.name();
				} else {
					featureType = entryFeatureTable.getFeatureType().name();
				}
			}
		}

		return featureType;
	}

	private static String getFeatureDescription(UniProtEntry entry, String alignedSeqStart, String alignedSeqEnd, FeatureType featureType) {
		String description = "";
		for (EntryFeatureTable entryFeatureTable : entry.getFeatures()) {
			if (entryFeatureTable.getFromEndPoint().equals(alignedSeqStart) && entryFeatureTable.getToEndPoint().equals(alignedSeqEnd)) {
				if (entryFeatureTable.getFeatureType().name().equals(featureType.name())) {
					if (description.equals("")) {
						description += entryFeatureTable.getFeatureDescription();
					} else {
						description += " ^|^ " + entryFeatureTable.getFeatureDescription();
					}
				}
			}
		}
		return description;
	}

	public static String getThreeLettersAminoAcid(String oneLetterAminoAcid) {
		Map<String, String> oneToThreeLettersAminoAcidMap = new HashMap<String, String>();

		// System.out.println(oneLetterAminoAcid + "??");
		oneToThreeLettersAminoAcidMap.put("A", "Ala");
		oneToThreeLettersAminoAcidMap.put("R", "Arg");
		oneToThreeLettersAminoAcidMap.put("N", "Asn");
		oneToThreeLettersAminoAcidMap.put("D", "Asp");
		oneToThreeLettersAminoAcidMap.put("B", "Asx");
		oneToThreeLettersAminoAcidMap.put("C", "Cys");
		oneToThreeLettersAminoAcidMap.put("E", "Glu");
		oneToThreeLettersAminoAcidMap.put("Q", "Gln");
		oneToThreeLettersAminoAcidMap.put("Z", "Glx");
		oneToThreeLettersAminoAcidMap.put("G", "Gly");
		oneToThreeLettersAminoAcidMap.put("H", "His");
		oneToThreeLettersAminoAcidMap.put("I", "Ile");
		oneToThreeLettersAminoAcidMap.put("L", "Leu");
		oneToThreeLettersAminoAcidMap.put("K", "Lys");
		oneToThreeLettersAminoAcidMap.put("M", "Met");
		oneToThreeLettersAminoAcidMap.put("F", "Phe");
		oneToThreeLettersAminoAcidMap.put("P", "Pro");
		oneToThreeLettersAminoAcidMap.put("S", "Ser");
		oneToThreeLettersAminoAcidMap.put("T", "Thr");
		oneToThreeLettersAminoAcidMap.put("W", "Trp");
		oneToThreeLettersAminoAcidMap.put("Y", "Try");
		oneToThreeLettersAminoAcidMap.put("V", "Val");
		return oneToThreeLettersAminoAcidMap.get(oneLetterAminoAcid);
	}

	public static String getOneLettersAminoAcid(String threeLettersAminoAcid) {
		Map<String, String> threeToOneLetterAminoAcidMap = new HashMap<String, String>();

		threeToOneLetterAminoAcidMap.put("Ala", "A");
		threeToOneLetterAminoAcidMap.put("Arg", "R");
		threeToOneLetterAminoAcidMap.put("Asn", "N");
		threeToOneLetterAminoAcidMap.put("Asp", "D");
		threeToOneLetterAminoAcidMap.put("Asx", "B");
		threeToOneLetterAminoAcidMap.put("Cys", "C");
		threeToOneLetterAminoAcidMap.put("Glu", "E");
		threeToOneLetterAminoAcidMap.put("Gln", "Q");
		threeToOneLetterAminoAcidMap.put("Glx", "Z");
		threeToOneLetterAminoAcidMap.put("Gly", "G");
		threeToOneLetterAminoAcidMap.put("His", "H");
		threeToOneLetterAminoAcidMap.put("Ile", "I");
		threeToOneLetterAminoAcidMap.put("Leu", "L");
		threeToOneLetterAminoAcidMap.put("Lys", "K");
		threeToOneLetterAminoAcidMap.put("Met", "M");
		threeToOneLetterAminoAcidMap.put("Phe", "F");
		threeToOneLetterAminoAcidMap.put("Pro", "P");
		threeToOneLetterAminoAcidMap.put("Ser", "S");
		threeToOneLetterAminoAcidMap.put("Thr", "T");
		threeToOneLetterAminoAcidMap.put("Trp", "W");
		threeToOneLetterAminoAcidMap.put("Try", "Y");
		threeToOneLetterAminoAcidMap.put("Val", "V");
		return threeToOneLetterAminoAcidMap.get(threeLettersAminoAcid);
	}
}
