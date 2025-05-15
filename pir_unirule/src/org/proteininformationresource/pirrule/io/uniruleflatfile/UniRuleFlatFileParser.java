package org.proteininformationresource.pirrule.io.uniruleflatfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.proteininformationresource.pirrule.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirrule.model.ACLine;
import org.proteininformationresource.pirrule.model.AnnotationSection;
import org.proteininformationresource.pirrule.model.CCLine;
import org.proteininformationresource.pirrule.model.CCNoneLine;
import org.proteininformationresource.pirrule.model.RuleCommentType;
import org.proteininformationresource.pirrule.model.CaseStatement;
import org.proteininformationresource.pirrule.model.CommentsLine;
import org.proteininformationresource.pirrule.model.ComputingSection;
import org.proteininformationresource.pirrule.model.ControlBlock;
import org.proteininformationresource.pirrule.model.ControlStatement;
import org.proteininformationresource.pirrule.model.DCLine;
import org.proteininformationresource.pirrule.model.DELine;
import org.proteininformationresource.pirrule.model.DRLine;
import org.proteininformationresource.pirrule.model.PIRRuleDataFactory;
import org.proteininformationresource.pirrule.model.DuplicateLine;
import org.proteininformationresource.pirrule.model.ElseCaseStatement;
import org.proteininformationresource.pirrule.model.ElseStatement;
import org.proteininformationresource.pirrule.model.EndCaseStatement;
import org.proteininformationresource.pirrule.model.FeatureType;
import org.proteininformationresource.pirrule.model.FTFromLine;
import org.proteininformationresource.pirrule.model.FTNoneLine;
import org.proteininformationresource.pirrule.model.FeatureDescriptionLine;
import org.proteininformationresource.pirrule.model.FusionBlock;
import org.proteininformationresource.pirrule.model.GOLine;
import org.proteininformationresource.pirrule.model.HeaderSection;
import org.proteininformationresource.pirrule.model.HistoryInfo;
import org.proteininformationresource.pirrule.model.HistorySection;
import org.proteininformationresource.pirrule.model.InternalCommentsLine;
import org.proteininformationresource.pirrule.model.KWLine;
import org.proteininformationresource.pirrule.model.KingdomLine;
import org.proteininformationresource.pirrule.model.Line;
import org.proteininformationresource.pirrule.model.PIRRule;
import org.proteininformationresource.pirrule.model.PIRRuleManager;
import org.proteininformationresource.pirrule.model.PlasmidLine;
import org.proteininformationresource.pirrule.model.RelatedLine;
import org.proteininformationresource.pirrule.model.RuleSection;
import org.proteininformationresource.pirrule.model.ScopeBlock;
import org.proteininformationresource.pirrule.model.SizeLine;
import org.proteininformationresource.pirrule.model.TRLine;
import org.proteininformationresource.pirrule.model.TemplateLine;
import org.proteininformationresource.pirrule.model.XXLine;
import org.proteininformationresource.pirrule.model.expression.AndOperatorExpression;
import org.proteininformationresource.pirrule.model.expression.ConditionExpression;
import org.proteininformationresource.pirrule.model.expression.DefinedOperatorExpression;
import org.proteininformationresource.pirrule.model.expression.Expression;
import org.proteininformationresource.pirrule.model.expression.ExpressionValue;
import org.proteininformationresource.pirrule.model.expression.FTGroupConditionExpression;
import org.proteininformationresource.pirrule.model.expression.FeatureConditionExpression;
import org.proteininformationresource.pirrule.model.expression.NotOperatorExpression;
import org.proteininformationresource.pirrule.model.expression.OrOperatorExpression;
import org.proteininformationresource.pirrule.model.expression.OrganismConditionType;

import edu.udel.bioinformatics.pirrule.PIRRuleUtil;
import edu.udel.bioinformatics.pirrule.ScopeKingdom;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: Jan 31, 2014<br>
 * <br>
 * 
 * A <code>UniRuleParser</code> parses rules in UniRule flat file format.
 */
public class UniRuleFlatFileParser {

	PIRRuleManager pirruleManager;
	PIRRuleDataFactory dataFactory;
	boolean isStrict = false;
	List<PIRRule> pirrules = new ArrayList<PIRRule>();
	BufferedReader br;
	int currentLineCount = 0;
	int currentRuleLineCount = 0;
	String currentLine = null;
	String previousLine = null;

	// ArrayList<UniRuleFlatFileParserLog> parserLogs = new
	// ArrayList<UniRuleFlatFileParserLog>();
	Map<String, ArrayList<ErrorMessage>> parserLogs = new HashMap<String, ArrayList<ErrorMessage>>();
	String currentRuleAC = null;
	boolean ruleStart = false;
	boolean caseBlockStart = false;
	boolean debug = false;
	RuleSection currentSection = null;
	ControlBlock controlBlock = null;
	List<ControlBlock> controlBlocks = null;
	List<Line> ruleLines = null;

	HeaderSection headerSection = null;
	AnnotationSection annotationSection = null;
	ComputingSection computingSection = null;
	HistorySection historySection = null;
	List<String> controlStatementFTGroupNumbers = null;
	
	int start = 0;
	int end = 0;

	public UniRuleFlatFileParser(PIRRuleManager pirruleManager, boolean isStrict, boolean debug, BufferedReader br) {
		super();
		this.pirruleManager = pirruleManager;
		this.isStrict = isStrict;
		this.br = br;
		this.dataFactory = this.pirruleManager.getDataFactory();
		this.debug = debug;
	}

	public void parse() throws UniRuleFlatFileParserException, IOException {
		PIRRule pirrule = null;
		String line = null;

		// PrintWriter writer = new PrintWriter(System.out);

		while ((line = br.readLine()) != null) {
			currentLineCount++;
			currentRuleLineCount++;
			if (debug) {
				String lineNumber = String.format("%1$-" + 6 + "s", currentLineCount);
				System.out.println(lineNumber + line);
			}

			if (line.length() > 0) {
				line = line.replaceAll("\\s+$", "");
				previousLine = currentLine;
				currentLine = line;
				if (line.startsWith(UniRuleFlatFileConstants.AC_LINE_START)) {
					pirrule = this.dataFactory.buildPIRRule(pirruleManager);
					controlStatementFTGroupNumbers = new ArrayList<String>();
					if (pirrules.size() > 0 && !previousLine.equals(UniRuleFlatFileConstants.RULE_SEPARATOR)) {
						String message = " Missing rule separater for rule " + this.currentRuleAC;
						ErrorMessage errorMessage = new ErrorMessage((currentRuleLineCount - 1), (currentLineCount - 1), message);
						errorHandler(errorMessage);
					}

					parseACLine(line);
					headerSection = (HeaderSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.DC_LINE_START)) {
					parseDCLine(line);
					headerSection = (HeaderSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.TR_LINE_START)) {
					parseTRLine(line);
					headerSection = (HeaderSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.INTERNAL_COMMENTS_START)) {
					if (!line.startsWith(UniRuleFlatFileConstants.HISTORY_FIRST_CREATE) && !line.startsWith(UniRuleFlatFileConstants.HISTORY_LAST_MODIFIED)) {
						parseInternalComments(line);
					} else if (line.startsWith(UniRuleFlatFileConstants.HISTORY_FIRST_CREATE)) {
						parseHistoryFirstCreate(line);
						historySection = (HistorySection) currentSection;
					} else if (line.startsWith(UniRuleFlatFileConstants.HISTORY_LAST_MODIFIED)) {
						parseHistoryLastModified(line);
						historySection = (HistorySection) currentSection;
					}
				} else if (line.startsWith(UniRuleFlatFileConstants.XX_LINE_START)) {
					parseXXLine(line);
				} else if (line.startsWith(UniRuleFlatFileConstants.DE_LINE_START)) {
					parseDELine(line);
					annotationSection = (AnnotationSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.KW_LINE_START)) {
					parseKWLine(line);
					annotationSection = (AnnotationSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.GO_LINE_START)) {
					parseGOLine(line);
					annotationSection = (AnnotationSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.CC_LINE_START)) {
					parseCCLine(line);
					annotationSection = (AnnotationSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.FT_LINE_START)) {
					parseFTLine(line);
					annotationSection = (AnnotationSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.DR_LINE_START)) {
					parseDRLine(line);
					annotationSection = (AnnotationSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.SIZE_LINE_START)) {
					parseSizeLine(line);
					computingSection = (ComputingSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.RELATED_LINE_START)) {
					parseRelatedLine(line);
					computingSection = (ComputingSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.TEMPLATE_LINE_START)) {
					parseTemplateLine(line);
					computingSection = (ComputingSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.PLASMID_LINE_START)) {
					parsePlasmidLine(line);
					computingSection = (ComputingSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.DUPLICATE_LINE_START)) {
					parseDuplicateLine(line);
					computingSection = (ComputingSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.COMMENTS_START)) {
					parseCommentLine(line);
					computingSection = (ComputingSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.SCOPE_BLOCK_START)) {
					parseScopeBlock(line);
					computingSection = (ComputingSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.FUSION_BLOCK_START)) {
					parseFusionBlock(line);
					computingSection = (ComputingSection) currentSection;
				} else if (line.startsWith(" ")) {
					parseScopeFusionBlockContinousLine(line);
					computingSection = (ComputingSection) currentSection;
				} else if (line.startsWith(UniRuleFlatFileConstants.CASE_START)) {
					parseCaseStatement(line);
				} else if (line.startsWith(UniRuleFlatFileConstants.ELSE_CASE_START)) {
					parseElseCaseStatement(line);
				} else if (!line.startsWith(UniRuleFlatFileConstants.ELSE_CASE_START) && line.startsWith(UniRuleFlatFileConstants.ELSE_START)) {
					parseElseStatement(line);
				} else if (line.startsWith(UniRuleFlatFileConstants.END_CASE)) {
					parseEndCaseStatement(line);
				} else if (line.startsWith(UniRuleFlatFileConstants.RULE_SEPARATOR)) {
					if (headerSection == null) {
						String message = " Missing 'HeaderSection'";
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
					} else {
						pirrule.setHeaderSection(headerSection);
					}
					if (annotationSection == null) {
						String message = " Missing 'AnnotationSection'";
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
					} else {
						pirrule.setAnnotationSection(annotationSection);
					}
					if (computingSection == null) {
						String message = " Missing 'ComputingSection'";
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
					} else {
						pirrule.setComputingSection(computingSection);
					}
					if (historySection == null) {
						String message = "Missing 'HistorySection', no ZA or ZB line";
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
					} else {
						pirrule.setHistorySection(historySection);
					}
					if (controlBlocks != null && controlBlocks.size() > 0) {
						pirrule.setControlBlocks(controlBlocks);
					}
					pirrule.setRuleLines(ruleLines);

					pirrules.add(pirrule);

					String checkFTGroupNumbersNote = null;
					if (pirrule.getRuleAC().contains("PIRSR")) {
						checkFTGroupNumbersNote = checkFTGroupNumbers(pirrule);
					}
					if (checkFTGroupNumbersNote != null) {
						String message = checkFTGroupNumbersNote;
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
						// System.out.println(this.currentRuleAC);
					}
					// System.out.println(checkFTGroupNumbersNote);
					controlStatementFTGroupNumbers = new ArrayList<String>();

					this.ruleStart = false;
					this.currentRuleAC = null;
					headerSection = null;
					annotationSection = null;
					computingSection = null;
					historySection = null;
					controlBlocks = null;
					currentSection = null;
					controlBlock = null;

					if (line.indexOf(UniRuleFlatFileConstants.AC_LINE_START) > 0) {
						String acLine = line.substring(line.indexOf(UniRuleFlatFileConstants.AC_LINE_START));
						// System.out.println("????" + acLine + "????");
						parseACLine(acLine);
						headerSection = (HeaderSection) currentSection;
						// currentLineCount = currentLineCount+2;
						// currentRuleLineCount = currentRuleLineCount+2;
						String message = "Missing newline after rule separator '" + UniRuleFlatFileConstants.RULE_SEPARATOR + "'";
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
						currentRuleLineCount = 1;
					} else {
						currentRuleLineCount = 0;
					}

				}
			}
		}

		// if (parserLogs.size() > 0) {
		// List<String> keys = new ArrayList<String>(parserLogs.keySet());
		// Collections.sort(keys);
		// for (String key : keys) {
		// System.err.println("\nSyntax Error: ");
		// ArrayList<String> messages = parserLogs.get(key);
		// for (int i = 0; i < messages.size(); i++) {
		// System.err.println(key + " " + messages.get(i));
		// }
		// }
		// System.err.println();
		// }
	}

	private String checkFTGroupNumbers(PIRRule pirrule) {
		PIRRuleUtil pirruleUtil = this.pirruleManager.getDataFactory().buidPIRRuleUtil(pirrule);
		List<ControlBlock> ftControlBlocks = pirruleUtil.getFTLineControlBlocks();

		List<String> ftBlockFTGroupNumbers = new ArrayList<String>();
		for (ControlBlock ftControlBlock : ftControlBlocks) {
			List<ControlStatement> controlStatements = ftControlBlock.getControlStatements();
			for (ControlStatement controlStatement : controlStatements) {
				List<Line> applicableLines = controlStatement.getApplicableLines();
				for (Line line : applicableLines) {
					if (line instanceof FeatureDescriptionLine) {
						FeatureDescriptionLine featureDescriptionLine = (FeatureDescriptionLine) line;
						String ftGroupNumber = Integer.toString(featureDescriptionLine.getFeatureGroupNumber());
						if (!ftBlockFTGroupNumbers.contains(ftGroupNumber)) {
							ftBlockFTGroupNumbers.add(ftGroupNumber);
						}
					}
				}

			}
		}
		if (controlStatementFTGroupNumbers.size() > 0 && ftBlockFTGroupNumbers.size() > 0) {
			Collections.sort(controlStatementFTGroupNumbers);
			Collections.sort(ftBlockFTGroupNumbers);
			String note = "";
			for (String ftGroupNumber : controlStatementFTGroupNumbers) {
				if (!ftBlockFTGroupNumbers.contains(ftGroupNumber)) {
					if (note.equals("")) {
						note += ftGroupNumber;
					} else {
						note += ", " + ftGroupNumber;
					}
				}
			}
			if (!note.equals("")) {
				return "FTGroup number inconsistency (FTGroups " + note + " in 'case' statement, but not in FTBlock: " + ftBlockFTGroupNumbers + ")";
			}
			// if(!ftBlockFTGroupNumbers.contains(controlStatementFTGroupNumbers))
			// {
			// //if
			// (!controlStatementFTGroupNumbers.equals(ftBlockFTGroupNumbers)) {
			// return "FTGroup numbers inconsistency (Case Statement: " +
			// controlStatementFTGroupNumbers + "; FTBlock: " +
			// ftBlockFTGroupNumbers
			// + ")";
			// }
		}
		return null;
	}

	private void parseElseStatement(String line) {
		ElseStatement elseStatement = null;
		if (controlBlock != null) {
			List<ControlStatement> controlStatements = controlBlock.getControlStatements();
			if (!(controlStatements.get(0) instanceof CaseStatement)) {
				String message = " Missing proper case statement to start with.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {
				elseStatement = this.dataFactory.buildElseStatement();
				controlStatements.add(elseStatement);
			}
		} else {
			String message = " Missing proper case statement to start with.";
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		}
		currentSection.getLines().add(elseStatement);
		ruleLines.add(elseStatement);
	}

	private void parseElseCaseStatement(String line) {
		ElseCaseStatement elseCaseStatement = null;
		if (controlBlock != null) {
			List<ControlStatement> controlStatements = controlBlock.getControlStatements();
			if (!(controlStatements.get(0) instanceof CaseStatement)) {
				String message = "Missing proper case statement to start with.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {

				elseCaseStatement = this.dataFactory.buildElseCaseStatement();
				line = line.replace(UniRuleFlatFileConstants.ELSE_CASE_START, "").trim();
				if (this.isBalanced(line)) {
					elseCaseStatement.setExpression(parseExpression(line));
				} else {
					String message = "Parenthese in '" + line + "' is not balanced.";
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				}
				controlStatements.add(elseCaseStatement);
			}
		} else {
			String message = " Missing proper case statement to start with.";
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		}
		currentSection.getLines().add(elseCaseStatement);
		ruleLines.add(elseCaseStatement);
	}

	private void parseXXLine(String line) {
		XXLine xxLine = this.dataFactory.buildXXLine();
		if (currentSection == null) {
			String message = "Missing rule AC";
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		} else {
			this.currentSection.getLines().add(xxLine);
			if (currentSection.getLastLine() instanceof FTNoneLine) {
				if (this.computingSection == null) {
					this.computingSection = this.dataFactory.buildComputingSection();
				}
				currentSection = this.computingSection;
			}
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(xxLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(xxLine);
			}
			ruleLines.add(xxLine);
		}
	}

	private void parseDRLine(String line) {
		DRLine drLine = null;
		String drContentsStr = line.replace(UniRuleFlatFileConstants.DR_LINE_START, "");
		String[] drContents = drContentsStr.split(UniRuleFlatFileConstants.DR_LINE_SEPARATOR);
		if (drContents.length != 5) {
			String message = "Invalid DR line";
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		} else {
			String dbName = drContents[0];
			String featureName = drContents[1];
			String identifier = drContents[2];
			String nbHits = drContents[3];
			String level = drContents[4];
			// level = level.replace(UniRuleFlatFileConstants.DR_LINE_LEVEL,
			// "");
			drLine = this.dataFactory.buildDRLine(dbName, featureName, identifier, nbHits, level);
		}
		this.currentSection.getLines().add(drLine);

		if (controlBlock != null) {
			if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				lastControlStatement.addLine(drLine);
			}
		} else {
			currentSection.getNonControlBlockLines().add(drLine);
		}
		ruleLines.add(drLine);
	}

	private void parseDuplicateLine(String line) {
		String[] duplicateIds = null;
		List<String> duplicateIdsList = null;
		if (line.startsWith(UniRuleFlatFileConstants.DUPLICATE_LINE_IN)) {
			duplicateIds = (line.replaceFirst(UniRuleFlatFileConstants.DUPLICATE_LINE_IN, "").trim() + " ").split(", ");
			duplicateIdsList = new ArrayList<String>(Arrays.asList(duplicateIds));
		}

		DuplicateLine duplicateLine = this.dataFactory.buildDuplicateLine(duplicateIdsList);
		if (currentSection.getLines() == null) {
			List<Line> lines = new ArrayList<Line>();
			lines.add(duplicateLine);
			currentSection.setLines(lines);
		} else {
			currentSection.getLines().add(duplicateLine);
		}

		if (controlBlock != null) {
			if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				lastControlStatement.addLine(duplicateLine);
			}
		} else {
			currentSection.getNonControlBlockLines().add(duplicateLine);
		}
		ruleLines.add(duplicateLine);
	}

	private void parsePlasmidLine(String line) {
		String[] plasmidIds = null;
		List<String> plasmidIdsList = null;
		if (line.startsWith(UniRuleFlatFileConstants.PLASMID_LINE_IN)) {
			plasmidIds = (line.replaceFirst(UniRuleFlatFileConstants.PLASMID_LINE_IN, "").trim() + " ").split(", ");
			plasmidIdsList = new ArrayList<String>(Arrays.asList(plasmidIds));
		}

		PlasmidLine plasmidLine = this.dataFactory.buildPlasmidLine(plasmidIdsList);
		if (currentSection.getLines() == null) {
			List<Line> lines = new ArrayList<Line>();
			lines.add(plasmidLine);
			currentSection.setLines(lines);
		} else {
			currentSection.getLines().add(plasmidLine);
		}

		if (controlBlock != null) {
			if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				lastControlStatement.addLine(plasmidLine);
			}
		} else {
			currentSection.getNonControlBlockLines().add(plasmidLine);
		}
		ruleLines.add(plasmidLine);
	}

	private void parseTemplateLine(String line) {
		String[] templateACs = (line.replaceFirst(UniRuleFlatFileConstants.TEMPLATE_LINE_START, "").trim() + " ").split("; ");
		List<String> templateACsList = new ArrayList<String>(Arrays.asList(templateACs));
		TemplateLine templateLine = this.dataFactory.buildTemplateLine(templateACsList);
		if (currentSection.getLines() == null) {
			List<Line> lines = new ArrayList<Line>();
			lines.add(templateLine);
			currentSection.setLines(lines);
		} else {
			currentSection.getLines().add(templateLine);
		}

		if (controlBlock != null) {
			if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				lastControlStatement.addLine(templateLine);
			}
		} else {
			currentSection.getNonControlBlockLines().add(templateLine);
		}
		ruleLines.add(templateLine);
	}

	private void parseEndCaseStatement(String line) {
		EndCaseStatement endCaseStatement = null;
		if (controlBlock != null) {
			List<ControlStatement> controlStatements = controlBlock.getControlStatements();
			if (!(controlStatements.get(0) instanceof CaseStatement)) {
				String message = "'end case' without proper case statement to start with.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {
				endCaseStatement = (EndCaseStatement) this.dataFactory.buildEndCaseStatement();
				controlStatements.add(endCaseStatement);
			}
		} else {
			String message = "'end case' without proper case statement to start with.";
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		}
		currentSection.getLines().add(endCaseStatement);
		controlBlock = null;
		ruleLines.add(endCaseStatement);
	}

	private void parseScopeFusionBlockContinousLine(String line) {
		if (line.startsWith(UniRuleFlatFileConstants.FUSION_NTER_START)) {
			String nTerDescription = line.replace(UniRuleFlatFileConstants.FUSION_NTER_START, "");
			if (!nTerDescription.equals("None")) {
				String[] proteins = nTerDescription.split("; ");
				FusionBlock fusionBlock = (FusionBlock) currentSection.getLastLine();
				ArrayList<String> ntList = (ArrayList<String>) Arrays.asList(proteins);
				fusionBlock.setNTList(ntList);
				currentSection.setLastLine(fusionBlock);
				if (controlBlock != null) {
					if (controlBlock instanceof ControlBlock) {
						ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
						FusionBlock lastApplicableLine = (FusionBlock) lastControlStatement.getLastApplicableLine();
						lastApplicableLine.setNTList(ntList);
					}
				} else {
					currentSection.setLastNonControlBlockLine(fusionBlock);
				}
			}
		} else if (line.startsWith(UniRuleFlatFileConstants.FUSION_CTER_START)) {
			String cTerDescription = line.replace(UniRuleFlatFileConstants.FUSION_CTER_START, "");
			if (!cTerDescription.equals("None")) {
				String[] proteins = cTerDescription.split("; ");
				FusionBlock fusionBlock = (FusionBlock) currentSection.getLastLine();
				ArrayList<String> ctList = (ArrayList<String>) Arrays.asList(proteins);
				fusionBlock.setCTList(ctList);
				currentSection.setLastLine(fusionBlock);
				if (controlBlock != null) {
					if (controlBlock instanceof ControlBlock) {
						ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
						FusionBlock lastApplicableLine = (FusionBlock) lastControlStatement.getLastApplicableLine();
						lastApplicableLine.setNTList(ctList);
					}
				} else {
					currentSection.setLastNonControlBlockLine(fusionBlock);
				}
			}
		} else if (line.startsWith(UniRuleFlatFileConstants.SCOPE_EXCEPT_START)) {
			String exceptTaxon = line.replaceFirst(UniRuleFlatFileConstants.SCOPE_EXCEPT_START, "");
			ScopeBlock scopeBlock = (ScopeBlock) currentSection.getLastLine();
			if (scopeBlock.getKingdomLines() == null) {
				String message = "Missing kingdom line for 'except' in Scope block.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {
				KingdomLine lastKingdomLine = scopeBlock.getKingdomLines().get(scopeBlock.getKingdomLines().size() - 1);
				List<String> exceptTaxonomicGroups = lastKingdomLine.getExceptTaxonomicGroups();
				if (exceptTaxonomicGroups == null) {
					exceptTaxonomicGroups = new ArrayList<String>();
				}
				exceptTaxonomicGroups.add(exceptTaxon);
				lastKingdomLine.setExceptTaxonomicGroups(exceptTaxonomicGroups);
			}
			currentSection.setLastLine(scopeBlock);
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.setLastApplicableLine(scopeBlock);
				}
			} else {
				currentSection.setLastNonControlBlockLine(scopeBlock);
			}
		} else if (line.startsWith(UniRuleFlatFileConstants.SCOPE_NOT_IN_START)) {
			String notInLine = line.replaceFirst(UniRuleFlatFileConstants.SCOPE_NOT_IN_START, "");
			notInLine = notInLine.trim() + " ";
			String[] notInTaxonCodes = notInLine.split(UniRuleFlatFileConstants.SCOPE_NOT_IN_COMPLETE_PROTEOME_SEPARATOR);
			ScopeBlock scopeBlock = (ScopeBlock) currentSection.getLastLine();
			if (scopeBlock.getKingdomLines() == null) {
				String message = "Missing kingdom line for 'not in' in Scope block.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {
				KingdomLine lastKingdomLine = scopeBlock.getKingdomLines().get(scopeBlock.getKingdomLines().size() - 1);
				List<String> completeProteomeNotInTaxCodes = lastKingdomLine.getCompleteProteomeNotInTaxCodes();
				if (completeProteomeNotInTaxCodes == null) {
					completeProteomeNotInTaxCodes = new ArrayList<String>();
				}
				for (int i = 0; i < notInTaxonCodes.length; i++) {
					completeProteomeNotInTaxCodes.add(notInTaxonCodes[i]);
				}
				lastKingdomLine.setCompleteProteomeNotInTaxCodes(completeProteomeNotInTaxCodes);
			}
			currentSection.setLastLine(scopeBlock);
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.setLastApplicableLine(scopeBlock);
				}
			} else {
				currentSection.setLastNonControlBlockLine(scopeBlock);
			}
		} else if (line.startsWith(" ")) {
			Line lastLine = currentSection.getLastLine();
			if (lastLine instanceof ScopeBlock) {
				String[] kingdoms = line.trim().split("; ");
				String kingdom = null;
				String kingdomSubTaxon = null;
				if (kingdoms.length == 1) {
					kingdom = kingdoms[0];
				}
				if (kingdoms.length == 2) {
					kingdom = kingdoms[0];
					kingdomSubTaxon = kingdoms[1];
				}
				ScopeBlock scopeBlock = (ScopeBlock) currentSection.getLastLine();
				if (kingdom.contains(";")) {
					String message = "Missing space between Kingdom and subKingdom: " + kingdom;
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				} else if (ScopeKingdom.valueOf(kingdom) == null) {
					String message = "Invalid kingdom: " + kingdom;
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				} else {
					KingdomLine kingdomLine = this.dataFactory.buildKingdomLine(kingdom, kingdomSubTaxon, null, null);
					if (scopeBlock.getKingdomLines() == null) {
						List<KingdomLine> kingdomLines = new ArrayList<KingdomLine>();
						kingdomLines.add(kingdomLine);
						scopeBlock.setKingdomLines(kingdomLines);
					} else {
						scopeBlock.getKingdomLines().add(kingdomLine);
					}
					currentSection.setLastLine(scopeBlock);
					if (controlBlock != null) {
						if (controlBlock instanceof ControlBlock) {
							ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
							lastControlStatement.setLastApplicableLine(scopeBlock);
						}
					} else {
						currentSection.setLastNonControlBlockLine(scopeBlock);
					}
				}
			} else if (lastLine instanceof CommentsLine) {
				CommentsLine commentsLine = (CommentsLine) lastLine;
				commentsLine.setCommentText(commentsLine.getCommentText().trim() + "\n" + line.trim());
				currentSection.setLastLine(commentsLine);
				if (controlBlock != null) {
					if (controlBlock instanceof ControlBlock) {
						ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
						lastControlStatement.setLastApplicableLine(commentsLine);
					}
				} else {
					currentSection.setLastNonControlBlockLine(commentsLine);
				}
			}
		}

	}

	private void parseRelatedLine(String line) {
		String[] relatedACs = (line.replaceFirst(UniRuleFlatFileConstants.RELATED_LINE_START, "").trim() + " ").split("; ");
		List<String> relatedRuleACs = new ArrayList<String>(Arrays.asList(relatedACs));
		RelatedLine relatedLine = this.dataFactory.buildRelatedLine(relatedRuleACs);
		if (currentSection.getLines() == null) {
			List<Line> lines = new ArrayList<Line>();
			lines.add(relatedLine);
			currentSection.setLines(lines);
		} else {
			currentSection.getLines().add(relatedLine);
		}

		if (controlBlock != null) {
			if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				lastControlStatement.addLine(relatedLine);
			}
		} else {
			currentSection.getNonControlBlockLines().add(relatedLine);
		}
		ruleLines.add(relatedLine);
	}

	private void parseSizeLine(String line) {
		if (currentSection == null || !(currentSection instanceof ComputingSection)) {
			if (this.computingSection == null) {
				this.computingSection = this.dataFactory.buildComputingSection();
			}
			currentSection = this.computingSection;
		}
		String sizeLimit = line.replaceFirst(UniRuleFlatFileConstants.SIZE_LINE_START, "");
		sizeLimit = sizeLimit.replace(UniRuleFlatFileConstants.SIZE_LINE_END, "");
		SizeLine sizeLine = this.dataFactory.buildSizeLine(sizeLimit);
		if (currentSection.getLines() == null) {
			List<Line> lines = new ArrayList<Line>();
			lines.add(sizeLine);
			currentSection.setLines(lines);
		} else {
			currentSection.getLines().add(sizeLine);
		}

		if (controlBlock != null) {
			if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				lastControlStatement.addLine(sizeLine);
			}
		} else {
			currentSection.getNonControlBlockLines().add(sizeLine);
		}
		ruleLines.add(sizeLine);
	}

	private void parseCommentLine(String line) {
		// if (!(currentSection instanceof ComputingSection)) {
		// ComputingSection computingSection =
		// this.dataFactory.getComputingSection();
		// currentSection = computingSection;
		// }
		String commentText = line.replaceFirst(UniRuleFlatFileConstants.COMMENTS_START, "");
		CommentsLine commentLine = this.dataFactory.buildCommentLine(commentText);

		if (currentSection.getLines() == null) {
			List<Line> lines = new ArrayList<Line>();
			lines.add(commentLine);
			currentSection.setLines(lines);
		} else {
			currentSection.getLines().add(commentLine);
		}
		if (controlBlock != null) {
			if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				lastControlStatement.addLine(commentLine);
			}
		} else {
			currentSection.getNonControlBlockLines().add(commentLine);
		}
		ruleLines.add(commentLine);
	}

	private void parseKWLine(String line) {
		if (currentSection == null || !(currentSection instanceof AnnotationSection)) {
			if (this.annotationSection == null) {
				this.annotationSection = this.dataFactory.buildAnnotationSection();
			}
			currentSection = this.annotationSection;
		}
		if (line.replace(UniRuleFlatFileConstants.KW_LINE_START, "").contains(";")) {
			String message = "Invalid KW line, ONE keyword only";
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		} else {
			KWLine kwLine = this.dataFactory.buildKWLine(line.replace(UniRuleFlatFileConstants.KW_LINE_START, ""));
			if (currentSection.getLines() == null) {
				List<Line> lines = new ArrayList<Line>();
				lines.add(kwLine);
				currentSection.setLines(lines);
			} else {
				currentSection.getLines().add(kwLine);
			}
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(kwLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(kwLine);
			}
			ruleLines.add(kwLine);
		}
	}

	private void parseGOLine(String line) {
		if (currentSection == null || !(currentSection instanceof AnnotationSection)) {
			if (this.annotationSection == null) {
				this.annotationSection = this.dataFactory.buildAnnotationSection();
			}
			currentSection = this.annotationSection;
		}

		/* #{Arg-101} */
		Pattern goLinePattern = Pattern.compile("(^GO\\s+)(GO:\\d+);\\s+(\\w):(.*)");
		Matcher goLineMatcher = goLinePattern.matcher(line);
		if (line.matches(goLinePattern.toString()) && goLineMatcher.find()) {
			String geneOntologyAccessionNumber = goLineMatcher.group(2);
			String geneOntologyAspect = goLineMatcher.group(3);
			String geneOntologyTerm = goLineMatcher.group(4);

			GOLine goLine = this.dataFactory.buildGOLine(geneOntologyAccessionNumber, geneOntologyAspect, geneOntologyTerm);
			if (currentSection.getLines() == null) {
				List<Line> lines = new ArrayList<Line>();
				lines.add(goLine);
				currentSection.setLines(lines);
			} else {
				currentSection.getLines().add(goLine);
			}

			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(goLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(goLine);
			}
			ruleLines.add(goLine);
		} else {
			String message = "Invalid GO line";
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		}

	}

	private void parseInternalComments(String line) {
		String comments = line.replaceFirst("\\*\\*", "").trim();
		InternalCommentsLine internalCommentsLine = this.dataFactory.buildInternalCommentsLine(comments);
		if (currentSection.getLines() == null) {
			List<Line> lines = new ArrayList<Line>();
			lines.add(internalCommentsLine);
			currentSection.setLines(lines);
		} else {
			currentSection.getLines().add(internalCommentsLine);
		}
		if (controlBlock != null) {
			if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				lastControlStatement.addLine(internalCommentsLine);
			}
		} else {
			currentSection.getNonControlBlockLines().add(internalCommentsLine);
		}
		ruleLines.add(internalCommentsLine);
	}

	private void parseScopeBlock(String line) {
		if (currentSection == null || !(currentSection instanceof ComputingSection)) {
			if (this.computingSection == null) {
				this.computingSection = this.dataFactory.buildComputingSection();
			}
			currentSection = this.computingSection;
		}
		ScopeBlock scopeBlock = this.dataFactory.buildScopeBlock();
		if (currentSection.getLines() == null) {
			List<Line> lines = new ArrayList<Line>();
			lines.add(scopeBlock);
			currentSection.setLines(lines);
		} else {
			currentSection.getLines().add(scopeBlock);
		}

		if (controlBlock != null) {
			if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				lastControlStatement.addLine(scopeBlock);
			}
		} else {
			currentSection.getNonControlBlockLines().add(scopeBlock);
		}
		ruleLines.add(scopeBlock);
	}

	private void parseFusionBlock(String line) {
		if (currentSection == null || !(currentSection instanceof ComputingSection)) {
			if (this.computingSection == null) {
				this.computingSection = this.dataFactory.buildComputingSection();
			}
			currentSection = this.computingSection;
		}
		FusionBlock fusionBlock = this.dataFactory.buildFusionBlock();
		if (currentSection.getLines() == null) {
			List<Line> lines = new ArrayList<Line>();
			lines.add(fusionBlock);
			currentSection.setLines(lines);
		} else {
			currentSection.getLines().add(fusionBlock);
		}

		if (controlBlock != null) {
			if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				lastControlStatement.addLine(fusionBlock);
			}
		} else {
			currentSection.getNonControlBlockLines().add(fusionBlock);
		}
		ruleLines.add(fusionBlock);
	}

	private void parseHistoryFirstCreate(String line) {
		line = line.replaceFirst("\\*\\*ZA ", "").trim();
		String[] history = line.split(" ");
		String curator = history[0];
		String date = history[1];
		String time = "";
		if (history.length == 3) {
			time = history[2];
		}
		HistoryInfo firstCreation = this.dataFactory.buildHistoryInfo(curator, date + " " + time, "", "creation");
		List<HistoryInfo> historyInfos = null;
		if (currentSection == null || !(currentSection instanceof HistorySection)) {
			if (this.historySection == null) {
				this.historySection = this.dataFactory.buildHistorySection(historyInfos);
			}
			currentSection = this.historySection;
			historyInfos = new ArrayList<HistoryInfo>();
			historyInfos.add(firstCreation);
		} else {
			historyInfos = ((HistorySection) currentSection).getHistoryInfos();
			if (historyInfos == null) {
				historyInfos = new ArrayList<HistoryInfo>();
			}
			historyInfos.add(firstCreation);
		}
		((HistorySection) currentSection).setHistoryInfos(historyInfos);
		ruleLines.add(firstCreation);
	}

	private void parseHistoryLastModified(String line) {
		line = line.replaceFirst("\\*\\*ZB ", "").trim();
		String[] history = line.split(" ");
		String curator = "";
		String date = "";
		String time = "";
		if (history.length == 3) {
			curator = history[0];
			date = history[1];
			time = history[2];
		} else if (history.length == 2) {
			curator = history[0];
			date = history[1];
		} else if (history.length == 1) {
			curator = history[0];
		}
		HistoryInfo lastModification = this.dataFactory.buildHistoryInfo(curator, date + " " + time, "", "lastModification");
		List<HistoryInfo> historyInfos = null;
		if (currentSection == null || !(currentSection instanceof HistorySection)) {
			if (this.historySection == null) {
				this.historySection = this.dataFactory.buildHistorySection(historyInfos);
			}
			currentSection = this.historySection;
			historyInfos = new ArrayList<HistoryInfo>();
			historyInfos.add(lastModification);
		} else {
			historyInfos = ((HistorySection) currentSection).getHistoryInfos();
			if (historyInfos == null) {
				historyInfos = new ArrayList<HistoryInfo>();
			}
			HistoryInfo lastInfo = null;
			if (historyInfos.size() > 0) {
				lastInfo = historyInfos.get(historyInfos.size() - 1);
				if (lastInfo.getType().equals("lastModification")) {
					historyInfos.set(historyInfos.size() - 1, lastModification);
				} else {
					historyInfos.add(lastModification);
				}
			} else {
				historyInfos.add(lastModification);
			}
		}
		((HistorySection) currentSection).setHistoryInfos(historyInfos);
		ruleLines.add(lastModification);
	}

	private void parseCaseStatement(String line) {
		if (currentSection == null) {
			String acMessage = "Missing rule AC";
			ErrorMessage acErrorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, acMessage);
			errorHandler(acErrorMessage);
		} else {
			CaseStatement caseStatement = this.dataFactory.buildCaseStatement();
			if (!line.startsWith(UniRuleFlatFileConstants.CASE_START + " ")) {
				String message = "Missing space after 'case' for rule " + this.currentRuleAC;
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {
				line = line.substring(5).trim();
				if (this.isBalanced(line)) {
					caseStatement.setExpression(parseExpression(line));
				} else {
					String message = "Parenthese in '" + line + "' is not balanced.";
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				}
			}

			ArrayList<ControlStatement> controlStatements = new ArrayList<ControlStatement>();
			controlStatements.add(caseStatement);
			controlBlock = this.dataFactory.buildControlBlock(controlStatements);
			if (controlBlocks != null) {
				controlBlocks.add(controlBlock);
			} else {
				controlBlocks = this.dataFactory.buildControlBlocks();
				controlBlocks.add(controlBlock);
			}
			currentSection.getLines().add(caseStatement);
			ruleLines.add(caseStatement);
		}
	}

	private Expression parseExpression(String line) {

		Expression expression = null;

		if (checkInvalidOperator(line) != null) {
			String message = checkInvalidOperator(line);
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		} else if (line.indexOf(" " + UniRuleFlatFileConstants.OPERATOR_AND + " ") != -1) {
			AndOperatorExpression andOperatorExpression = parseAndOperatorExpression(line);
			expression = andOperatorExpression;
		} else if (line.indexOf(" " + UniRuleFlatFileConstants.OPERATOR_OR + " ") != -1) {
			OrOperatorExpression orOperatorExpression = parseOrOperatorExpression(line);
			expression = orOperatorExpression;
		} else if (line.startsWith(UniRuleFlatFileConstants.OPERATOR_NOT)) {
			NotOperatorExpression notOperatorExpression = parseNotOperatorExpression(line);
			expression = notOperatorExpression;
		} else if (line.startsWith(UniRuleFlatFileConstants.OPERATOR_DEFINED)) {
			DefinedOperatorExpression definedOperatorExpression = parseDefinedOperatorExpression(line);
			expression = definedOperatorExpression;
		} else {
			ConditionExpression conditionExpression = parseConditionExpression(line);
			expression = conditionExpression;
		}

		return expression;
	}

	private String checkInvalidOperator(String line) {
		String msg = null;
		if (line.indexOf(UniRuleFlatFileConstants.RIGHT_PARENTHESIS + UniRuleFlatFileConstants.OPERATOR_AND) != -1) {
			msg = "Missing space between '" + UniRuleFlatFileConstants.RIGHT_PARENTHESIS + "' and '" + UniRuleFlatFileConstants.OPERATOR_AND + "'";
		}
		if (line.indexOf(UniRuleFlatFileConstants.RIGHT_ANGLE_BRAKET + UniRuleFlatFileConstants.OPERATOR_AND) != -1) {
			msg = "Missing space between '" + UniRuleFlatFileConstants.RIGHT_ANGLE_BRAKET + "' and '" + UniRuleFlatFileConstants.OPERATOR_AND + "'";
		}

		if (line.indexOf(UniRuleFlatFileConstants.OPERATOR_AND + UniRuleFlatFileConstants.LEFT_PARENTHESIS) != -1) {
			msg = "Missing space between '" + UniRuleFlatFileConstants.OPERATOR_AND + "' and '" + UniRuleFlatFileConstants.LEFT_PARENTHESIS + "'";
		}

		if (line.indexOf(UniRuleFlatFileConstants.OPERATOR_AND + UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET) != -1) {
			msg = "Missing space between '" + UniRuleFlatFileConstants.OPERATOR_AND + "' and '" + UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET + "'";
		}

		if (line.indexOf(UniRuleFlatFileConstants.RIGHT_PARENTHESIS + UniRuleFlatFileConstants.OPERATOR_OR) != -1) {
			msg = "Missing space between '" + UniRuleFlatFileConstants.RIGHT_PARENTHESIS + "' and '" + UniRuleFlatFileConstants.OPERATOR_OR + "'";
		}
		if (line.indexOf(UniRuleFlatFileConstants.RIGHT_ANGLE_BRAKET + UniRuleFlatFileConstants.OPERATOR_OR) != -1) {
			msg = "Missing space between '" + UniRuleFlatFileConstants.RIGHT_ANGLE_BRAKET + "' and '" + UniRuleFlatFileConstants.OPERATOR_OR + "'";
		}

		if (line.indexOf(UniRuleFlatFileConstants.OPERATOR_OR + UniRuleFlatFileConstants.LEFT_PARENTHESIS) != -1) {
			msg = "Missing space between '" + UniRuleFlatFileConstants.OPERATOR_OR + " and '" + UniRuleFlatFileConstants.LEFT_PARENTHESIS + "'";
		}

		if (line.indexOf(UniRuleFlatFileConstants.OPERATOR_OR + UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET) != -1) {
			msg = "Missing space between '" + UniRuleFlatFileConstants.OPERATOR_OR + " and '" + UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET + "'";
		}
		return msg;
	}

	private DefinedOperatorExpression parseDefinedOperatorExpression(String line) {
		DefinedOperatorExpression definedOperatorExpression = null;

		String leftParenthesis = null;
		String rightParenthesis = null;
		if (this.isBalanced(line) && line.startsWith(UniRuleFlatFileConstants.LEFT_PARENTHESIS) && line.endsWith(UniRuleFlatFileConstants.RIGHT_PARENTHESIS)) {
			leftParenthesis = UniRuleFlatFileConstants.LEFT_PARENTHESIS;
			rightParenthesis = UniRuleFlatFileConstants.RIGHT_PARENTHESIS;
			line = line.substring(1, line.length() - 1);
			line = line.trim();
		}

		line = line.replaceFirst(UniRuleFlatFileConstants.OPERATOR_DEFINED, "");
		line = line.trim();
		Expression definedExpression = parseExpression(line);
		definedOperatorExpression = this.dataFactory.buildDefinedOperatorExpression(leftParenthesis, definedExpression, rightParenthesis);
		return definedOperatorExpression;
	}

	private NotOperatorExpression parseNotOperatorExpression(String line) {
		NotOperatorExpression notOperatorExpression = null;

		String leftParenthesis = null;
		String rightParenthesis = null;
		if (this.isBalanced(line) && line.startsWith(UniRuleFlatFileConstants.LEFT_PARENTHESIS) && line.endsWith(UniRuleFlatFileConstants.RIGHT_PARENTHESIS)) {
			leftParenthesis = UniRuleFlatFileConstants.LEFT_PARENTHESIS;
			rightParenthesis = UniRuleFlatFileConstants.RIGHT_PARENTHESIS;
			line = line.substring(1, line.length() - 1);
			line = line.trim();
		}

		line = line.replaceFirst(UniRuleFlatFileConstants.OPERATOR_NOT, "");
		line = line.trim();
		Expression notExpression = parseExpression(line);
		notOperatorExpression = this.dataFactory.buildNotOperatorExpression(leftParenthesis, notExpression, rightParenthesis);
		return notOperatorExpression;
	}

	private ConditionExpression parseConditionExpression(String line) {
		ConditionExpression conditionExpression = null;
		String leftParenthesis = null;
		String rightParenthesis = null;
		if (this.isBalanced(line) && line.startsWith(UniRuleFlatFileConstants.LEFT_PARENTHESIS) && line.endsWith(UniRuleFlatFileConstants.RIGHT_PARENTHESIS)) {
			leftParenthesis = UniRuleFlatFileConstants.LEFT_PARENTHESIS;
			rightParenthesis = UniRuleFlatFileConstants.RIGHT_PARENTHESIS;
			line = line.substring(1, line.length() - 1);
			line = line.trim();
		}

		if (line.startsWith(UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET + UniRuleFlatFileConstants.FEATURE_CONDITION)) {
			conditionExpression = parseFeatureConditionExpression(line);
		} else if (line.startsWith(UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET + UniRuleFlatFileConstants.FTGROUP_CONDITION)) {
			conditionExpression = parseFTGroupConditionExpression(line);
		} else if (line.startsWith(UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET + UniRuleFlatFileConstants.CASE_CONDITION_OC)) {
			conditionExpression = parseOrganismConditionExpression(line);
		} else if (line.startsWith(UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET + UniRuleFlatFileConstants.CASE_CONDITION_OS)) {
			conditionExpression = parseOrganismConditionExpression(line);
		} else if (line.startsWith(UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET + UniRuleFlatFileConstants.CASE_CONDITION_OG)) {
			conditionExpression = parseOrganismConditionExpression(line);
		} else {
			String message = "Invalid condition expression";
			//System.out.println(line);
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		}

		if (conditionExpression != null) {
			conditionExpression.setLeftParenthesis(leftParenthesis);
			conditionExpression.setRightParenthesis(rightParenthesis);
		}
		return conditionExpression;
	}

	private ConditionExpression parseOrganismConditionExpression(String line) {
		ConditionExpression expression = null;
		if (this.isBalanced(line) && line.startsWith(UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET) && line.endsWith(UniRuleFlatFileConstants.RIGHT_ANGLE_BRAKET)) {
			line = line.substring(1, line.length() - 1);
			line = line.trim();
		}

		if (line.startsWith(UniRuleFlatFileConstants.CASE_CONDITION_OC)) {
			String organismName = line.replaceFirst(UniRuleFlatFileConstants.CASE_CONDITION_OC, "");
			expression = this.dataFactory.buildOrganismConditionExpression(OrganismConditionType.OC, organismName);
		}
		if (line.startsWith(UniRuleFlatFileConstants.CASE_CONDITION_OS)) {
			String organismName = line.replaceFirst(UniRuleFlatFileConstants.CASE_CONDITION_OS, "");
			expression = this.dataFactory.buildOrganismConditionExpression(OrganismConditionType.OS, organismName);
		}
		if (line.startsWith(UniRuleFlatFileConstants.CASE_CONDITION_OG)) {
			String organismName = line.replaceFirst(UniRuleFlatFileConstants.CASE_CONDITION_OG, "");
			expression = this.dataFactory.buildOrganismConditionExpression(OrganismConditionType.OG, organismName);
		}

		return expression;
	}

	private ConditionExpression parseFeatureConditionExpression(String line) {
		if (this.isBalanced(line) && line.startsWith(UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET) && line.endsWith(UniRuleFlatFileConstants.RIGHT_ANGLE_BRAKET)) {
			line = line.substring(1, line.length() - 1);
			line = line.trim();
		}
		String featureConditionDescription = line.replaceFirst(UniRuleFlatFileConstants.FEATURE_CONDITION, "");
		FeatureConditionExpression featureConditionExpression = this.dataFactory.buildFeatureConditionExpression(featureConditionDescription);
		if (this.currentRuleAC.contains("PIRSR")) {
			if (!featureConditionDescription.replaceAll(UniRuleFlatFileConstants.SRHMM, UniRuleFlatFileConstants.PIRSR).equals(this.currentRuleAC)) {
				String message = "Invalid feature condition \"" + featureConditionDescription + "\" for rule \"" + this.currentRuleAC + "\"";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			}
		}
		return featureConditionExpression;
	}

	private ConditionExpression parseFTGroupConditionExpression(String line) {

		if (this.isBalanced(line) && line.startsWith(UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET) && line.endsWith(UniRuleFlatFileConstants.RIGHT_ANGLE_BRAKET)) {
			line = line.substring(1, line.length() - 1);
			line = line.trim();
		}
		String ftGroupConditionDescription = line.replaceFirst(UniRuleFlatFileConstants.FTGROUP_CONDITION, "");
		int ftGroupNumber = Integer.parseInt(ftGroupConditionDescription);
		if (!controlStatementFTGroupNumbers.contains(ftGroupConditionDescription)) {
			controlStatementFTGroupNumbers.add(ftGroupConditionDescription);
		}
		FTGroupConditionExpression ftGroupConditionExpression = this.dataFactory.buildFeatureConditionExpression(ftGroupNumber, ExpressionValue.FALSE);
		return ftGroupConditionExpression;
	}

	private OrOperatorExpression parseOrOperatorExpression(String line) {
		String leftParenthesis = null;
		String rightParenthesis = null;
		Expression leftExpression = null;
		Expression rightExpression = null;

		if (this.isBalanced(line) && line.startsWith(UniRuleFlatFileConstants.LEFT_PARENTHESIS) && line.endsWith(UniRuleFlatFileConstants.RIGHT_PARENTHESIS)) {
			leftParenthesis = UniRuleFlatFileConstants.LEFT_PARENTHESIS;
			rightParenthesis = UniRuleFlatFileConstants.RIGHT_PARENTHESIS;
			line = line.substring(1, line.length() - 1);
			line = line.trim();

		}
		String[] expressions = line.split(" " + UniRuleFlatFileConstants.OPERATOR_OR + " ", 2);
		leftExpression = parseExpression(expressions[0]);
		rightExpression = parseExpression(expressions[1]);
		OrOperatorExpression orOperatorExpression = this.dataFactory.buildOrOperatorExpression(leftParenthesis, leftExpression, rightExpression,
				rightParenthesis);
		return orOperatorExpression;
	}

	private AndOperatorExpression parseAndOperatorExpression(String line) {
		String leftParenthesis = null;
		String rightParenthesis = null;
		Expression leftExpression = null;
		Expression rightExpression = null;

		line = line.trim();
		if (this.isBalanced(line) && line.startsWith(UniRuleFlatFileConstants.LEFT_PARENTHESIS) && line.endsWith(UniRuleFlatFileConstants.RIGHT_PARENTHESIS)) {
			leftParenthesis = UniRuleFlatFileConstants.LEFT_PARENTHESIS;
			rightParenthesis = UniRuleFlatFileConstants.RIGHT_PARENTHESIS;

			line = line.substring(1, line.length() - 1);
			line = line.trim();
		}

		// int occurrenceOfAnd = countSubstring(" " +
		// UniRuleFlatFileConstants.OPERATOR_AND + " ", line);

		String[] expressions = line.split(" " + UniRuleFlatFileConstants.OPERATOR_AND + " ", 2);
		leftExpression = parseExpression(expressions[0]);

		rightExpression = parseExpression(expressions[1]);

		AndOperatorExpression andOperatorExpression = this.dataFactory.buildAndOperatorExpression(leftParenthesis, leftExpression, rightExpression,
				rightParenthesis);
		return andOperatorExpression;
	}

	// private int countSubstring(String subStr, String str) {
	// return (str.length() - str.replace(subStr, "").length()) /
	// subStr.length();
	// }

	private boolean isBalanced(String expression)
	// Postcondition: A true return value indicates that the parentheses in the
	// given expression are balanced. Otherwise the return value is false.
	// Note that characters other than ( ) { } and [ ] are ignored.
	{
		// Meaningful names for characters
		final char LEFT_NORMAL = '(';
		final char RIGHT_NORMAL = ')';
		final char LEFT_CURLY = '{';
		final char RIGHT_CURLY = '}';
		final char LEFT_SQUARE = '[';
		final char RIGHT_SQUARE = ']';
		final char LEFT_ANGLE = '<';
		final char RIGHT_ANGLE = '>';

		Stack<Character> store = new Stack<Character>(); // Stores parens
		int i; // An index into the string
		boolean failed = false; // Change to true for a mismatch

		for (i = 0; !failed && (i < expression.length()); i++) {
			switch (expression.charAt(i)) {
			case LEFT_NORMAL:
			case LEFT_CURLY:
			case LEFT_SQUARE:
			case LEFT_ANGLE:
				store.push(expression.charAt(i));
				break;
			case RIGHT_NORMAL:
				if (store.isEmpty() || (store.pop() != LEFT_NORMAL))
					failed = true;
				break;
			case RIGHT_CURLY:
				if (store.isEmpty() || (store.pop() != LEFT_CURLY))
					failed = true;
				break;
			case RIGHT_SQUARE:
				if (store.isEmpty() || (store.pop() != LEFT_SQUARE))
					failed = true;
				break;
			case RIGHT_ANGLE:
				if (store.isEmpty() || (store.pop() != LEFT_ANGLE))
					failed = true;
				break;
			}
		}

		return (store.isEmpty() && !failed);
	}

	private void parseDELine(String line) {
		if (currentSection == null || !(currentSection instanceof AnnotationSection)) {
			if (this.annotationSection == null) {
				this.annotationSection = this.dataFactory.buildAnnotationSection();
			}
			currentSection = this.annotationSection;
			// currentSection.setLines(this.annotationSection.getLines());
		}
		// System.out.println(line);
		Pattern recNameFullLinePattern = Pattern.compile("(^DE\\s+RecName:\\s+Full=)(.*);");
		Pattern secondaryFullLinePattern = Pattern.compile("(^DE\\s+Full=)(.*);");

		Pattern recNameShortLinePattern = Pattern.compile("(^DE\\s+RecName:\\s+Short=)(.*);");
		Pattern secondaryShortLinePattern = Pattern.compile("(^DE\\s+Short=)(.*);");

		Pattern recNameECLinePattern = Pattern.compile("(^DE\\s+RecName:\\s+EC=)(.*);");
		Pattern secondaryECLinePattern = Pattern.compile("(^DE\\s+EC=)(.*);");

		Pattern altNameFullLinePattern = Pattern.compile("(^DE\\s+AltName:\\s+Full=)(.*);");
		Pattern altNameShortLinePattern = Pattern.compile("(^DE\\s+AltName:\\s+Short=)(.*);");
		Pattern altNameECLinePattern = Pattern.compile("(^DE\\s+AltName:\\s+EC=)(.*);");

		Matcher recNameFullLineMatcher = recNameFullLinePattern.matcher(line);
		Matcher recNameShortLineMatcher = recNameShortLinePattern.matcher(line);
		Matcher recNameECLineMatcher = recNameECLinePattern.matcher(line);

		Matcher altNameFullLineMatcher = altNameFullLinePattern.matcher(line);
		Matcher altNameShortLineMatcher = altNameShortLinePattern.matcher(line);
		Matcher altNameECLineMatcher = altNameECLinePattern.matcher(line);

		Matcher secondaryFullLineMatcher = secondaryFullLinePattern.matcher(line);
		Matcher secondaryShortLineMatcher = secondaryShortLinePattern.matcher(line);
		Matcher secondaryECLineMatcher = secondaryECLinePattern.matcher(line);

		if (line.matches(secondaryFullLinePattern.toString()) && secondaryFullLineMatcher.find()) {
			String fullName = secondaryFullLineMatcher.group(2);
			if (currentSection == null || !(currentSection instanceof AnnotationSection)) {
				String message = "Missing corresponding 'RecName' or 'AltName' DE line.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {
				DELine lastLine = null;
				if (currentSection.getLastLine() instanceof DELine) {
					lastLine = (DELine) currentSection.getLastLine();
				} else {
					lastLine = (DELine) currentSection.getLines().get(currentSection.getLines().size() - 2);
				}
				if (lastLine.getFullNames() == null) {
					List<String> fullNames = new ArrayList<String>();
					fullNames.add(fullName);
				} else {
					lastLine.getFullNames().add(fullName);
				}
				currentSection.setLastLine(lastLine);
				if (controlBlock != null) {
					if (controlBlock instanceof ControlBlock) {
						ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);

						lastControlStatement.setLastApplicableLine(lastLine);
					}
				} else {
					currentSection.setLastNonControlBlockLine(lastLine);
				}
				int lastLineIndex = ruleLines.size() - 1;
				ruleLines.set(lastLineIndex, lastLine);
			}
		} else if (line.matches(secondaryShortLinePattern.toString()) && secondaryShortLineMatcher.find()) {
			String shortName = secondaryShortLineMatcher.group(2);
			if (currentSection == null || !(currentSection instanceof AnnotationSection)) {
				String message = "Missing corresponding 'RecName' or 'AltName' DE line.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {
				DELine lastLine = null;
				if (currentSection.getLastLine() instanceof DELine) {
					lastLine = (DELine) currentSection.getLastLine();
				} else {
					lastLine = (DELine) currentSection.getLines().get(currentSection.getLines().size() - 2);
				}

				if (lastLine.getShortNames() == null) {
					List<String> shortNames = new ArrayList<String>();
					shortNames.add(shortName);
				} else {
					lastLine.getShortNames().add(shortName);
				}
				currentSection.setLastLine(lastLine);
				if (controlBlock != null) {
					if (controlBlock instanceof ControlBlock) {
						ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);

						lastControlStatement.setLastApplicableLine(lastLine);
					}
				} else {
					currentSection.setLastNonControlBlockLine(lastLine);
				}
				int lastLineIndex = ruleLines.size() - 1;
				ruleLines.set(lastLineIndex, lastLine);
			}
		} else if (line.matches(secondaryECLinePattern.toString()) && secondaryECLineMatcher.find()) {
			String ecNumber = secondaryECLineMatcher.group(2);
			if (currentSection == null || !(currentSection instanceof AnnotationSection)) {
				String message = "Missing corresponding 'RecName' or 'AltName' DE line.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {
				DELine lastLine = null;
				if (currentSection.getLastLine() instanceof DELine) {
					lastLine = (DELine) currentSection.getLastLine();
				} else {

					lastLine = findNearestDELine();
					if (lastLine == null) {
						String message = "Missing corresponding 'RecName' or 'AltName' DE line.";
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
					}
				}
				if (lastLine != null) {
					if (lastLine.getECs() == null) {
						List<String> ecNumbers = new ArrayList<String>();
						ecNumbers.add(ecNumber);
						lastLine.setECs(ecNumbers);
					} else {
						lastLine.getECs().add(ecNumber);
					}
					currentSection.setLastLine(lastLine);

					if (controlBlock != null) {
						if (controlBlock instanceof ControlBlock) {
							ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);

							lastControlStatement.setLastApplicableLine(lastLine);
						}
					} else {
						currentSection.setLastNonControlBlockLine(lastLine);
					}
				}
				int lastLineIndex = ruleLines.size() - 1;
				ruleLines.set(lastLineIndex, lastLine);
			}
		} else if (line.matches(recNameFullLinePattern.toString()) && recNameFullLineMatcher.find()) {
			String recNameFull = recNameFullLineMatcher.group(2);
			DELine deLine = this.dataFactory.buildDELine();
			deLine.setNameType("RecName");
			List<String> fullRecNames = new ArrayList<String>();
			fullRecNames.add(recNameFull);
			deLine.setFullNames(fullRecNames);
			if (currentSection.getLines() == null) {
				ArrayList<Line> deLines = new ArrayList<Line>();
				deLines.add(deLine);
				currentSection = this.dataFactory.buildAnnotationSection();
				currentSection.setLines(deLines);
			} else {
				currentSection.getLines().add(deLine);
			}
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(deLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(deLine);
			}
			ruleLines.add(deLine);

		} else if (line.matches(altNameFullLinePattern.toString()) && altNameFullLineMatcher.find()) {
			String altNameFull = altNameFullLineMatcher.group(2);
			DELine deLine = this.dataFactory.buildDELine();
			deLine.setNameType("AltName");
			List<String> fullAltNames = new ArrayList<String>();
			fullAltNames.add(altNameFull);
			deLine.setFullNames(fullAltNames);
			if (currentSection == null) {
				ArrayList<Line> deLines = new ArrayList<Line>();
				deLines.add(deLine);
				if (this.annotationSection == null) {
					this.annotationSection = this.dataFactory.buildAnnotationSection();
				}
				currentSection = this.annotationSection;
				currentSection.setLines(deLines);
			} else {
				currentSection.getLines().add(deLine);
			}
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(deLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(deLine);
			}
			ruleLines.add(deLine);
		} else if (line.matches(recNameShortLinePattern.toString()) && recNameShortLineMatcher.find()) {
			String recNameShort = recNameShortLineMatcher.group(2);
			DELine deLine = this.dataFactory.buildDELine();
			deLine.setNameType("RecName");
			List<String> shortRecNames = new ArrayList<String>();
			shortRecNames.add(recNameShort);
			deLine.setShortNames(shortRecNames);
			if (currentSection == null) {
				ArrayList<Line> deLines = new ArrayList<Line>();
				deLines.add(deLine);
				if (this.annotationSection == null) {
					this.annotationSection = this.dataFactory.buildAnnotationSection();
				}
				currentSection = this.annotationSection;
				currentSection.setLines(deLines);
			} else {
				currentSection.getLines().add(deLine);
			}
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(deLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(deLine);
			}
			ruleLines.add(deLine);
		} else if (line.matches(altNameShortLinePattern.toString()) && altNameShortLineMatcher.find()) {
			String altNameShort = altNameShortLineMatcher.group(2);
			DELine deLine = this.dataFactory.buildDELine();
			deLine.setNameType("AltName");
			List<String> shortAltNames = new ArrayList<String>();
			shortAltNames.add(altNameShort);
			deLine.setShortNames(shortAltNames);
			if (currentSection == null) {
				ArrayList<Line> deLines = new ArrayList<Line>();
				deLines.add(deLine);
				if (this.annotationSection == null) {
					this.annotationSection = this.dataFactory.buildAnnotationSection();
				}
				currentSection = this.annotationSection;
				currentSection.setLines(deLines);
			} else {
				currentSection.getLines().add(deLine);
			}
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(deLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(deLine);
			}
			ruleLines.add(deLine);
		} else if (line.matches(recNameECLinePattern.toString()) && recNameECLineMatcher.find()) {
			// System.out.println(recNameECLinePattern.toString());
			String recNameEC = recNameECLineMatcher.group(2);
			DELine deLine = this.dataFactory.buildDELine();
			deLine.setNameType("RecName");
			List<String> ecRecNames = new ArrayList<String>();
			ecRecNames.add(recNameEC);
			deLine.setECs(ecRecNames);
			if (currentSection == null) {
				ArrayList<Line> deLines = new ArrayList<Line>();
				deLines.add(deLine);
				if (this.annotationSection == null) {
					this.annotationSection = this.dataFactory.buildAnnotationSection();
				}
				currentSection = this.annotationSection;
				currentSection.setLines(deLines);
			} else {
				currentSection.getLines().add(deLine);
			}
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(deLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(deLine);
			}
			ruleLines.add(deLine);
		} else if (line.matches(altNameECLinePattern.toString()) && altNameECLineMatcher.find()) {
			String altNameEC = altNameShortLineMatcher.group(2);
			DELine deLine = this.dataFactory.buildDELine();
			deLine.setNameType("AltName");
			List<String> ecAltNames = new ArrayList<String>();
			ecAltNames.add(altNameEC);
			deLine.setECs(ecAltNames);
			if (currentSection == null) {
				ArrayList<Line> deLines = new ArrayList<Line>();
				deLines.add(deLine);
				if (this.annotationSection == null) {
					this.annotationSection = this.dataFactory.buildAnnotationSection();
				}
				currentSection = this.annotationSection;
				currentSection.setLines(deLines);
			} else {
				currentSection.getLines().add(deLine);
			}
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(deLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(deLine);
			}
			ruleLines.add(deLine);
		} else {
			// System.out.println(line);
			// if (line.trim().endsWith(";")) {
			// String message = "Invalid DE line.";
			// ErrorMessage errorMessage = new
			// ErrorMessage(currentRuleLineCount, currentLineCount, message);
			// errorHandler(errorMessage);
			// } else {
			// String message =
			// "Invalid DE line, missing ';' at the end of line.";
			// ErrorMessage errorMessage = new
			// ErrorMessage(currentRuleLineCount, currentLineCount, message);
			// errorHandler(errorMessage);
			// }
		}

	}

	private DELine findNearestDELine() {
		DELine deLine = null;
		if (this.currentSection.getLines() != null) {
			if (this.currentSection.getLines().size() > 0) {
				for (int i = this.currentSection.getLines().size() - 1; i >= 0; i--) {
					if (this.currentSection.getLines().get(i) instanceof DELine) {
						return (DELine) this.currentSection.getLines().get(i);
					}
				}
			}
		}
		return deLine;

	}

	private void parseFTLine(String line) {
		Pattern featureNoneLinePattern = Pattern.compile("(^FT\\s+None)");
		Pattern featureFromLinePattern = Pattern.compile("(^FT\\s+From:\\s+)(\\w+)");
		// Pattern featureLinePattern =
		// Pattern.compile("(^FT\\s+)(\\w+)\\s+(Nter|Cter|\\d+)\\s+(Nter|Cter|\\d+)\\s*(.*)");
		Pattern featureLinePattern = Pattern.compile("(^FT\\s+)(\\w+)\\s+(Nter|Nter\\+1|Cter|\\d+)\\s+(Nter|Nter\\+1|Cter|\\d+)\\s*(.*)");

		Pattern missingFeatureTypePattern = Pattern.compile("(^FT\\s+)(\\d+)\\s+(\\d+)\\s+(.*)");
		Pattern featureContinuousLinePattern = Pattern.compile("(^FT        \\s+)(.+)");
		Pattern featureConstraintLinePattern = Pattern.compile("(^FT\\s+Group:\\s+)(\\d+)\\;\\s+Condition:\\s+(.+)");
		Pattern featureConstraintLineNoGroupPattern = Pattern.compile("(^FT\\s+)Condition:\\s+(.+)");

		// if(line.indexOf(UniRuleFlatFileConstants.FT_FROM_LINE_START) != -1) {
		Matcher featureNoneLineMatcher = featureNoneLinePattern.matcher(line);
		Matcher featureFromLineMatcher = featureFromLinePattern.matcher(line);
		Matcher featureLineMatcher = featureLinePattern.matcher(line);
		Matcher featureConstraintLineMatcher = featureConstraintLinePattern.matcher(line);
		Matcher featureContinuousLineMatcher = featureContinuousLinePattern.matcher(line);
		Matcher missingFeatureTypeMatcher = missingFeatureTypePattern.matcher(line);
		Matcher featureConstraintLineNoGroupMatcher = featureConstraintLineNoGroupPattern.matcher(line);

		if (currentSection == null || !(currentSection instanceof AnnotationSection)) {
			if (this.annotationSection == null) {
				this.annotationSection = this.dataFactory.buildAnnotationSection();
			}
			currentSection = this.annotationSection;
		}
		// Feature None line
		if (line.matches(featureNoneLinePattern.toString()) && featureNoneLineMatcher.find()) {
			// System.out.println(" FT : none ??");

			FTNoneLine ftNoneLine = this.dataFactory.buildFTNoneLine();
			currentSection.getLines().add(ftNoneLine);
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(ftNoneLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(ftNoneLine);
			}
			ruleLines.add(ftNoneLine);
		}
		// From line
		else if (line.matches(featureFromLinePattern.toString()) && featureFromLineMatcher.find()) {
			String templateAccessionNumber = featureFromLineMatcher.group(featureFromLineMatcher.groupCount());
			FTFromLine ftFromLine = this.dataFactory.buildFTFromLine(templateAccessionNumber);
			currentSection.getLines().add(ftFromLine);
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					if (lastControlStatement.getExpression() instanceof AndOperatorExpression) {
						System.out.println(lastControlStatement.getExpression());
						String message = "No FTGroup expression in Feature Table Block";
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
					}
					lastControlStatement.addLine(ftFromLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(ftFromLine);
			}
			ruleLines.add(ftFromLine);
		}
		// FT description line
		else if (line.matches(featureLinePattern.toString()) && featureLineMatcher.find()) {
			String featureType = featureLineMatcher.group(2);
			if (FeatureType.valueOf(featureType) == null) {
				String message = "Invalid feature type";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			}
			String featureStart = featureLineMatcher.group(3);
			String featureEnd = featureLineMatcher.group(4);
			if(isInteger(featureStart) && isInteger(featureEnd)) {
				start = Integer.parseInt(featureStart);
				end = Integer.parseInt(featureEnd);
				if(start > end) {
					String message = "Residue start position ("+featureStart+") should be less than or equal to residue end position ("+featureEnd+")  ";
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				}
				
			}
			
			String featureDescription = featureLineMatcher.group(5).trim();
			if (featureDescription == null) {
				featureDescription = "";
			}
			List<String> featureDescriptions = new ArrayList<String>();
			featureDescriptions.add(featureDescription);
			FeatureDescriptionLine featureDescriptionLine = this.dataFactory.buildFeatureDescription(FeatureType.valueOf(featureType), featureStart,
					featureEnd, featureDescriptions);
			currentSection.getLines().add(featureDescriptionLine);
			if (controlBlock == null) {
				String message = "Missing FT From line.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			}
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(featureDescriptionLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(featureDescriptionLine);
			}
			ruleLines.add(featureDescriptionLine);
		}
		// FT description continuous line
		else if (line.matches(featureContinuousLinePattern.toString()) && featureContinuousLineMatcher.find()) {
			String continuousDecsription = featureContinuousLineMatcher.group(2).trim();
			if (controlBlock == null) {
				String message = "Missing FT feature description line.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			}

			if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				if (lastControlStatement.getApplicableLines() == null) {
					String message = "Missing FT feature description line.";
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				} else {
					int applicableLinesSize = lastControlStatement.getApplicableLines().size();
					Line lastLine = lastControlStatement.getApplicableLines().get(applicableLinesSize - 1);
					if (!(lastLine instanceof FeatureDescriptionLine)) {
						String message = " [Line: " + currentRuleLineCount + " (" + currentLineCount + ")]: " + " Missing FT feature description line.";
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
					} else {
						List<String> descriptions = ((FeatureDescriptionLine) lastLine).getDescriptions();
						descriptions.add(continuousDecsription);
					}
				}
			}
			Line lastLine = currentSection.getLastLine();
			FeatureDescriptionLine lastFeatureDescriptionLine = (FeatureDescriptionLine) lastLine;
			if (lastLine == null || !(lastLine instanceof FeatureDescriptionLine)) {
				String message = "Missing FT feature description line.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			}
			int lastLineIndex = ruleLines.size() - 1;
			ruleLines.set(lastLineIndex, lastFeatureDescriptionLine);
		}
		// FT constraint line
		else if (line.matches(featureConstraintLinePattern.toString()) && featureConstraintLineMatcher.find()) {
			int groupNumber = Integer.parseInt(featureConstraintLineMatcher.group(2));
			String conditionDesc = featureConstraintLineMatcher.group(3).trim();
			// FeatureConstraintLine featureConstraintLine =
			// this.dataFactory.getFeatureConstraintLine(groupNumber,
			// conditionDesc);
			
			Pattern disulfideDescPattern = Pattern.compile("([A-Z])\\-x\\((\\d+)\\)-([A-Z])");
			Matcher disulfideDescPatternMatcher = disulfideDescPattern.matcher(conditionDesc); 
			if(conditionDesc.matches(disulfideDescPattern.toString()) && disulfideDescPatternMatcher.find()) {
				//System.out.println(conditionDesc);
				int interval = Integer.parseInt(disulfideDescPatternMatcher.group(2));
				if(interval != (end -start -1)) {
					String message = "Residue interval "+interval + " is not correct: "+end+"(end) - "+start+"(start) - 1 = "+(end-start-1);
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				}
			}
			if (controlBlock == null) {
				String message = "Missing FT feature description line.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				if (lastControlStatement.getApplicableLines() == null) {
					String message = "Missing FT feature descrption line.";
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				} else {
					int applicableLinesSize = lastControlStatement.getApplicableLines().size();
					Line lastLine = lastControlStatement.getApplicableLines().get(applicableLinesSize - 1);

					if (!(lastLine instanceof FeatureDescriptionLine)) {
						// System.out.println("|"+lastLine+"|");
						String message = "Missing FT feature description line.";
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
					} else {
						// lastControlStatement.getApplicableLines().add(featureConstraintLine);
						((FeatureDescriptionLine) lastLine).setFeatureGroupNumber(groupNumber);
						((FeatureDescriptionLine) lastLine).setFeatureConditionPattern(conditionDesc);
						lastControlStatement.getApplicableLines().set(applicableLinesSize - 1, lastLine);
					}
					int lastLineIndex = ruleLines.size() - 1;
					ruleLines.set(lastLineIndex, lastLine);
				}
			}
			Line lastLine = currentSection.getLastLine();
			if (lastLine == null || !(lastLine instanceof FeatureDescriptionLine)) {
				String message = "Missing FT feature description line.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {
				FeatureDescriptionLine lastFeatureDescriptionLine = (FeatureDescriptionLine) lastLine;
				lastFeatureDescriptionLine.setFeatureGroupNumber(groupNumber);
				lastFeatureDescriptionLine.setFeatureConditionPattern(conditionDesc);
				currentSection.setLastLine(lastFeatureDescriptionLine);
				int lastLineIndex = ruleLines.size() - 1;
				ruleLines.set(lastLineIndex, lastLine);
			}
		}
		// FT constraint no group
		else if (line.matches(featureConstraintLineNoGroupPattern.toString()) && featureConstraintLineNoGroupMatcher.find()) {
			int groupNumber = 1;
			String conditionDesc = featureConstraintLineNoGroupMatcher.group(2);

			if (controlBlock == null) {
				String message = "Missing FT feature descrption line.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else if (controlBlock instanceof ControlBlock) {
				ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
				if (lastControlStatement.getApplicableLines() == null) {
					String message = "Missing FT feature description line.";
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				} else {
					int applicableLinesSize = lastControlStatement.getApplicableLines().size();
					Line lastLine = lastControlStatement.getApplicableLines().get(applicableLinesSize - 1);

					if (!(lastLine instanceof FeatureDescriptionLine)) {
						String message = "Missing FT feature description line.";
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
					} else {
						// lastControlStatement.getApplicableLines().add(featureConstraintLine);
						((FeatureDescriptionLine) lastLine).setFeatureGroupNumber(groupNumber);
						((FeatureDescriptionLine) lastLine).setFeatureConditionPattern(conditionDesc);
						lastControlStatement.getApplicableLines().set(applicableLinesSize - 1, lastLine);
					}
					int lastLineIndex = ruleLines.size() - 1;
					ruleLines.set(lastLineIndex, lastLine);
				}
			}
			Line lastLine = currentSection.getLastLine();
			FeatureDescriptionLine lastFeatureDescriptionLine = (FeatureDescriptionLine) lastLine;
			if (lastLine == null || !(lastLine instanceof FeatureDescriptionLine)) {
				String message = "Missing FT feature description line.";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {

				lastFeatureDescriptionLine.setFeatureGroupNumber(groupNumber);
				lastFeatureDescriptionLine.setFeatureConditionPattern(conditionDesc);
				currentSection.setLastLine(lastFeatureDescriptionLine);
				int lastLineIndex = ruleLines.size() - 1;
				ruleLines.set(lastLineIndex, lastLine);
			}
		} else if (line.matches(missingFeatureTypePattern.toString()) && missingFeatureTypeMatcher.find()) {
			String message = "Missing FT feature type.";
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		} else {
			String message = "Invalid FT line.";
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		}
	}

	private void parseCCLine(String line) {
		if (currentSection == null || !(currentSection instanceof AnnotationSection)) {
			if (this.annotationSection == null) {
				this.annotationSection = this.dataFactory.buildAnnotationSection();
			}
			currentSection = this.annotationSection;
		}

		if (line.equals(UniRuleFlatFileConstants.CC_LINE_START + "None")) {
			CCNoneLine ccNoneLine = this.dataFactory.buildCCNoneLine();
			currentSection.getLines().add(ccNoneLine);
			if (controlBlock != null) {
				if (controlBlock instanceof ControlBlock) {
					ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
					lastControlStatement.addLine(ccNoneLine);
				}
			} else {
				currentSection.getNonControlBlockLines().add(ccNoneLine);
			}
			ruleLines.add(ccNoneLine);
		} else {
			// CC Topic line;
			String message = null;
			if (line.startsWith(UniRuleFlatFileConstants.CC_LINE_TOPIC_START)) {
				String CCTopicAndDescriptionStr = line.replace(UniRuleFlatFileConstants.CC_LINE_TOPIC_START, "");
				String[] CCTopicAndDescription = CCTopicAndDescriptionStr.split(": ", 2);
				if (CCTopicAndDescription == null || CCTopicAndDescription.length != 2) {
					CCTopicAndDescription = CCTopicAndDescriptionStr.split(":", 2);
				}
				RuleCommentType topic = RuleCommentType.valueOf(CCTopicAndDescription[0].replaceAll(" ", "_"));
				String description = CCTopicAndDescription[1];

				if (checkCCTopic(CCTopicAndDescription[0]) == null) {
					message = "Unknown CC topic: " + topic + ".";
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				} else if (description.indexOf(topic.getTopic()) != -1) {
					message = "CC topic duplicated: " + topic + ".";
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				} else {
					ArrayList<String> descs = new ArrayList<String>();
					descs.add(description);
					CCLine ccLine = this.dataFactory.buildCCLine(topic, descs);
					if (currentSection.getLines() == null) {
						ArrayList<Line> lines = new ArrayList<Line>();
						lines.add(ccLine);
						currentSection.setLines(lines);
					} else {
						currentSection.getLines().add(ccLine);
					}
					if (controlBlock != null) {
						if (controlBlock instanceof ControlBlock) {
							ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
							lastControlStatement.addLine(ccLine);
						}
					} else {
						currentSection.getNonControlBlockLines().add(ccLine);
					}
					ruleLines.add(ccLine);
				}
			} else {
				String CCDescription = line.replace(UniRuleFlatFileConstants.CC_LINE_START, "").trim();
				if (line.indexOf(CCDescription) != 9) {
					message = "Starting of CC line description is not aligned with its topic start, please remove tab character if there is any.";
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				}
				if (currentSection == null) {
					message = "Missing topic for CC line description: '" + CCDescription + "'.";
					ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
					errorHandler(errorMessage);
				} else {
					Line lastLine = currentSection.getLastLine();
					if (!(lastLine instanceof CCLine)) {
						message = "Missing topic for CC line description: '" + CCDescription + "'.";
						ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
						errorHandler(errorMessage);
					} else {
						CCLine lastCCLine = (CCLine) lastLine;
						if (lastCCLine.getTopic() == null || lastCCLine.getTopic().getTopic().length() == 0) {
							message = "Missing topic for CC line description: '" + CCDescription + "'.";
							ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
							errorHandler(errorMessage);
						} else {
							lastCCLine.getDescription().add(CCDescription);
						}
					}
					currentSection.setLastLine(lastLine);

					if (controlBlock != null) {
						// System.out.println(line);
						if (controlBlock instanceof ControlBlock) {
							ControlStatement lastControlStatement = controlBlock.getControlStatements().get(controlBlock.getControlStatements().size() - 1);
							Line lastApplicableLine = lastControlStatement.getLastApplicableLine();
							if (lastApplicableLine == null) {
								message = "Missing topic for CC line description: '" + CCDescription + "'.";
								ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
								errorHandler(errorMessage);
							} else {
								lastApplicableLine = lastLine;
							}
						}
					} else {
						currentSection.setLastNonControlBlockLine(lastLine);
					}
					int lastLineIndex = ruleLines.size() - 1;
					ruleLines.set(lastLineIndex, lastLine);
				}
			}
		}
	}

	private RuleCommentType checkCCTopic(String topic) {
		for (RuleCommentType ccTopic : RuleCommentType.values()) {
			if (ccTopic.name().equals(topic.replace(" ", "_"))) {
				// if (ccTopic.name().equals(topic)) {
				return ccTopic;
			}
		}
		return null;
	}

	private void parseTRLine(String line) {
		if (currentSection == null || !(currentSection instanceof HeaderSection)) {
			String message = "Missing rule AC";
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		} else {
			// TR dbname; identifier1; identifier2; nbhits; level=level
			TRLine trLine = null;
			String trContentsStr = line.replace(UniRuleFlatFileConstants.TR_LINE_START, "");
			String[] trContents = trContentsStr.split(UniRuleFlatFileConstants.TR_LINE_SEPARATOR);
			if (trContents.length != 5) {
				String message = "Invalid TR line";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			} else {
				String dbName = trContents[0];
				String identifier1 = trContents[1];
				String identifier2 = trContents[2];
				String nbHits = trContents[3];
				String level = trContents[4];
				level = level.replace(UniRuleFlatFileConstants.TR_LINE_LEVEL, "");
				trLine = this.dataFactory.buildTRLine(dbName, identifier1, identifier2, nbHits, level);
			}
			this.currentSection.getLines().add(trLine);
			ruleLines.add(trLine);
		}
	}

	private void parseDCLine(String line) {
		if (currentSection == null || !(currentSection instanceof HeaderSection)) {
			String message = "Missing rule AC";
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		} else {
			String dataClass = line.replaceFirst(UniRuleFlatFileConstants.DC_LINE_START, "");
			dataClass = dataClass.replace(UniRuleFlatFileConstants.DC_LINE_END, "");
			DCLine dcLine = this.dataFactory.buildDCLine(dataClass);
			if (!dataClass.equals("Domain")) {
				String message = "Invalid DC line";
				ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
				errorHandler(errorMessage);
			}
			this.currentSection.getLines().add(dcLine);
			ruleLines.add(dcLine);
		}

	}

	private void errorHandler(ErrorMessage message) {
		ArrayList<ErrorMessage> messages = parserLogs.get(this.currentRuleAC);
		if (messages == null) {
			messages = new ArrayList<ErrorMessage>();
		}
		if (!messages.contains(message) && message != null) {
			messages.add(message);
		}
		// System.out.println(this.currentRuleAC);
		// System.out.println(messages.toString());
		if (this.currentRuleAC != null) {
			parserLogs.put(this.currentRuleAC, messages);
		}

		if (isStrict) {
			System.err.println(message.toString());
			System.exit(1);
		}
	}

	private void parseACLine(String line) {
		if (currentSection == null || !(currentSection instanceof HeaderSection)) {
			if (this.headerSection == null) {
				this.headerSection = this.dataFactory.buildHeaderSection();
			}
			currentSection = this.headerSection;
		}
		String ruleAC = line.replaceFirst(UniRuleFlatFileConstants.AC_LINE_START, "");
		ruleAC = ruleAC.replace(UniRuleFlatFileConstants.AC_LINE_END, "");

		if (!ruleAC.startsWith("PIRNR") && !ruleAC.startsWith("PIRSR")) {
			String message = "Invalid rule AC " + ruleAC;
			ErrorMessage errorMessage = new ErrorMessage(currentRuleLineCount, currentLineCount, message);
			errorHandler(errorMessage);
		} else {
			ruleLines = new ArrayList<Line>();
			ACLine acLine = this.dataFactory.buildACLine(ruleAC);
			this.currentRuleAC = acLine.getAccessionNumber();
			if (currentSection.getLines() == null) {
				ArrayList<Line> lines = new ArrayList<Line>();
				lines.add(acLine);
				currentSection.setLines(lines);
			} else {
				currentSection.getLines().add(acLine);
			}
			ruleLines.add(acLine);
			controlBlocks = null;
		}
	}

	public List<PIRRule> getPIRRules() {
		return this.pirrules;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}

	public String getParserLogs(String lineNumberType) {
		StringBuffer log = new StringBuffer();
		if (parserLogs.size() > 0) {
			List<String> keys = new ArrayList<String>(parserLogs.keySet());
			// System.out.println(keys);
			// keys.remove(null);
			Collections.sort(keys);
			log.append("\nSyntax Error: \n\n");
			for (String key : keys) {
				ArrayList<ErrorMessage> messages = parserLogs.get(key);
				for (int i = 0; i < messages.size(); i++) {
					log.append(key + " ");
					if (lineNumberType.equals("Rule")) {
						log.append("[Line: " + messages.get(i).getLineNumberInRule() + "]: ");
					} else if (lineNumberType.equals("Source")) {
						log.append("[Line: " + messages.get(i).getLineNumberInSource() + "]: ");
					} else {
						log.append("[Line: " + messages.get(i).getLineNumberInRule() + " (" + messages.get(i).getLineNumberInSource() + ")]: ");
					}
					log.append(messages.get(i).message + "\n");
				}
				log.append("\n");
			}
		}
		return log.toString();
	}
}
