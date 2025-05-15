package org.proteininformationresource.pirsr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.ControlBlock;
import org.proteininformationresource.pirsr.model.ControlStatement;
import org.proteininformationresource.pirsr.model.FTFromLine;
import org.proteininformationresource.pirsr.model.FTLine;
import org.proteininformationresource.pirsr.model.FeatureDescriptionLine;
import org.proteininformationresource.pirsr.model.KWLine;
import org.proteininformationresource.pirsr.model.Line;
import org.proteininformationresource.pirsr.model.PIRRule;
import org.proteininformationresource.pirsr.model.PIRRuleDataFactory;
import org.proteininformationresource.pirsr.model.PIRRuleManager;
import org.proteininformationresource.pirsr.profilehmm.ProfileHMMManager;
import org.proteininformationresource.pirsr.profilehmm.Template;
import org.proteininformationresource.pirsr.profilehmm.TemplateResidue;
import org.proteininformationresource.pirsr.propagation.PropagationDataFactory;
import org.proteininformationresource.pirsr.propagation.PropagationManager;
import org.proteininformationresource.pirsr.uniprot.model.EntryComment;
import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseType;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirsr.uniprot.model.EntryKeyword;
import org.proteininformationresource.pirsr.uniprot.model.UniProtEntry;

import edu.udel.bioinformatics.pirsr.PIRRuleDataFactoryImpl;
import edu.udel.bioinformatics.pirsr.PIRRuleManagerImpl;
import edu.udel.bioinformatics.pirsr.profilehmm.ProfileHMMManagerImpl;
import edu.udel.bioinformatics.pirsr.propagation.PropagationDataFactoryImpl;
import edu.udel.bioinformatics.pirsr.propagation.PropagationUtil;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 */
public class PIRUniRule {
	static boolean debug = false;

	public static void main(String[] args) {
		String usage = "java -jar PIRUniRule_1.0.jar [options]\n";
		usage += "Available options:\n";
		usage += "------------------\n";

		// Create the command line parser
		CommandLineParser parser = new BasicParser();

		// Create the options
		Options options = new Options();
		options.addOption("h", "help", false, "Print this message.");
		
//		Option logfile   = OptionBuilder.withArgName( "file" )
//                .hasArg()
//                .withDescription(  "use given file for log" )
//                .create( "logfile" );
		@SuppressWarnings("static-access")
		Option actionOpt =OptionBuilder.withArgName("arg")
				.hasArg(true)
				.withLongOpt("action")
				.isRequired()
				.withDescription("The action to perform (i.e. 'printRule', 'checkRuleSyntax', 'checkRuleTemplate', 'checkHMM3Model', 'propagate').")
				.create("a");
		options.addOption(actionOpt);
//		options.addOption("a", "action", true,
//				"The action to perform (i.e. 'printRule', 'checkRuleSyntax', 'checkRuleTemplate', 'checkHMM3Model', 'propagate').");
		options.addOption("F", "format", true, "The format of output file (i.e. UniRule, HTML.");
		options.addOption("f", "ruleFile", true, "The path to a UniRule flat file, which can contain multiple rules.");
		options.addOption("r", "ruleId", true, "Rule accession number to act upon.");
		options.addOption("l", "ruleIdList", true, "The path to a file contains a list of Rule accession numbers, one per line.");
		options.addOption("o", "outputFile", true, "The path to output file (default: stdout).");
		options.addOption("n", "lineNumber", true, "The line number relative to 'rule' or 'source', default is both.");
		options.addOption("s", "SwissProtDatFile", true, "The path to SwissProt dat file");
		options.addOption("p", "propagationDirectory", true, "The path to a directory with rule file, data file and alignment file.");
		options.addOption("t", "templateToModelSearchOutputFile", true, "The path to the template search against HMMER3 model output file.");

		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();

		String action = null;
		String format = null;
		String ruleFile = null;
		String ruleId = null;
		String ruleIdList = null;
		String outputFile = null;
		String lineNumber = null;
		String swissProtDatFile = null;
		String propagationDirectory = null;
		String templateToModelSearchOutputFile = null;
		List<UniProtEntry> swissProtEntries = null;

		boolean hasCommandLineError = false;
		PrintWriter writer = new PrintWriter(System.out, true);

		String commandLineArguments = "Command line options: ";
		try {
			for (int i = 0; i < args.length; i++) {
				commandLineArguments += args[i] + " ";
			}
			// System.err.println(commandLineArguments);

			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			if (line.getOptions().length == 0 || line.hasOption("h")) {
//				printHelp(int width, String cmdLineSyntax, String header, Options options, String footer) 
				formatter.printHelp(400, usage, "", options, "");
			} else {
				if (line.hasOption('a')) {
					action = line.getOptionValue("a");
				}

				if (line.hasOption('f')) {
					ruleFile = line.getOptionValue('f');
				}

				if (line.hasOption('o')) {
					outputFile = line.getOptionValue('o');
				}

				if (line.hasOption('F')) {
					format = line.getOptionValue('F');
				}

				if (line.hasOption('r')) {
					ruleId = line.getOptionValue('r');
				}

				if (line.hasOption('l')) {
					ruleIdList = line.getOptionValue('l');
				}

				if (line.hasOption('n')) {
					lineNumber = line.getOptionValue('n');
				}

				if (line.hasOption('s')) {
					swissProtDatFile = line.getOptionValue('s');
				}

				if (line.hasOption('p')) {
					propagationDirectory = line.getOptionValue('p');
				}
				if (line.hasOption('t')) {
					templateToModelSearchOutputFile = line.getOptionValue('t');
				}
				if (action == null) {
					System.err.println("Command line parsing failed.\nReason: action (-a, --action) is required.");
					hasCommandLineError = true;
				}

				PIRRuleDataFactory pirRuleDataFactory = PIRRuleDataFactoryImpl.getInstance();
				PropagationDataFactory propagationDataFactory = PropagationDataFactoryImpl.getInstance();
				// PIRRuleDataFactory dataFactory = new
				// PIRRuleDataFactoryImpl();
				if (!hasCommandLineError) {
					if (action.equals("checkRuleSyntax")) {
						if (ruleFile == null) {
							System.err.println("Command line parsing failed.\nReason: ruleFile (-f, --ruleFile) is required.");
							hasCommandLineError = true;
						} else {
							if (outputFile != null) {
								writer = new PrintWriter(new File(outputFile));
							} else {
								// writer = new PrintWriter(System.out, true);
								writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
							}
							// PIRRuleManager pirruleManager = new
							// PIRRuleManagerImpl(pirRuleDataFactory);
							PIRRuleManager pirruleManager = pirRuleDataFactory.buildPIRRuleManager(pirRuleDataFactory);
							PropagationManager propagationManager = propagationDataFactory.buildPropagationManager(propagationDataFactory,
									pirruleManager);

							// if(swissProtDatFile != null) {
							// swissProtEntries =
							// propagationManager.loadEntry(new
							// File(swissProtDatFile));
							// }
							List<PIRRule> rules = null;

							if (lineNumber != null && lineNumber.toUpperCase().equals("SOURCE")) {
								rules = pirruleManager.parsePIRRules(new File(ruleFile), PIRRuleManager.Format.UNIRULE, false, "Source");

								writer.print(pirruleManager.getParserLogs());
							} else if (lineNumber != null && lineNumber.toUpperCase().equals("RULE")) {
								rules = pirruleManager.parsePIRRules(new File(ruleFile), PIRRuleManager.Format.UNIRULE, false, "Rule");
								writer.print(pirruleManager.getParserLogs());
							} else {
								// System.out.println(" I am here");
								rules = pirruleManager.parsePIRRules(new File(ruleFile), PIRRuleManager.Format.UNIRULE, false, "");
								writer.print(pirruleManager.getParserLogs());
							}
						}
					} else if (action.equals("checkRuleTemplate")) {
						if (ruleFile == null) {
							System.err.println("Command line parsing failed.\nReason: ruleFile (-f, --ruleFile) is required.");
							hasCommandLineError = true;
						}
						if (swissProtDatFile == null) {
							System.err.println("Command line parsing failed.\nReason: swissProtDatFile (-s, --swissProtDatFile) is required.");
							hasCommandLineError = true;
						}
						if (!hasCommandLineError) {
							if (outputFile != null) {
								writer = new PrintWriter(new File(outputFile));
							} else {
								// writer = new PrintWriter(System.out, true);
								writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
							}

							PIRRuleManager pirruleManager = pirRuleDataFactory.buildPIRRuleManager(pirRuleDataFactory);
							PropagationManager propagationManager = propagationDataFactory.buildPropagationManager(propagationDataFactory,
									pirruleManager);

							if (swissProtDatFile != null) {
								swissProtEntries = propagationManager.loadEntry(new File(swissProtDatFile));
							}
							List<PIRRule> rules = pirruleManager.parsePIRRules(new File(ruleFile), PIRRuleManager.Format.UNIRULE, false, "");
							checkRuleTemplate(rules, swissProtEntries, writer);
						}
					} else if (action.equals("checkHMM3Model")) {
						if (ruleFile == null) {
							System.err.println("Command line parsing failed.\nReason: ruleFile (-f, --ruleFile) is required.");
							hasCommandLineError = true;
						}
						if (ruleId == null) {
							System.err.println("Command line parsing failed.\nReason: ruleId (-r, --ruleId) is required.");
							hasCommandLineError = true;
						}
						if (templateToModelSearchOutputFile == null) {
							System.err
									.println("Command line parsing failed.\nReason: templateToModelSearchOutputFile (-t, --templateToModelSearchOutputFile) is required.");
							hasCommandLineError = true;
						}
						if (!hasCommandLineError) {
							if (outputFile != null) {
								writer = new PrintWriter(new File(outputFile));
							} else {
								// writer = new PrintWriter(System.out, true);
								writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
							}

							PIRRuleManager pirruleManager = pirRuleDataFactory.buildPIRRuleManager(pirRuleDataFactory);
							// PropagationManager propagationManager =
							// propagationDataFactory.buildPropagationManager(propagationDataFactory,
							// pirruleManager);
							//
							// if (swissProtDatFile != null) {
							// swissProtEntries =
							// propagationManager.loadEntry(new
							// File(swissProtDatFile));
							// }
							List<PIRRule> rules = pirruleManager.parsePIRRules(new File(ruleFile), PIRRuleManager.Format.UNIRULE, false, "");
							PIRRule rule = pirruleManager.getRuleByAC(ruleId);
							checkHMM3Model(rule, templateToModelSearchOutputFile, writer);
						}
					}

					else if (action.equals("printRule")) {

						if (ruleFile == null) {
							System.err.println("Command line parsing failed.\nReason: ruleFile (-f, --ruleFile) is required.");
							hasCommandLineError = true;
						} else {
							PIRRuleManager pirruleManager = pirRuleDataFactory.buildPIRRuleManager(pirRuleDataFactory);
							List<PIRRule> rules = pirruleManager.parsePIRRules(new File(ruleFile), PIRRuleManager.Format.UNIRULE, false, "");
							rules = pirruleManager.getPIRRulesOrderedByAC("ASC");
							List<String> ruleACs = new ArrayList<String>();
							if (ruleIdList != null) {
								ruleACs = readFileLineByLine(ruleIdList);
							} else if (ruleId != null) {
								if (!ruleACs.contains(ruleId)) {
									ruleACs.add(ruleId);
								}
							}
							if (outputFile != null) {
								writer = new PrintWriter(new File(outputFile));
							} else {
								writer = new PrintWriter(System.out, true);
							}
							for (PIRRule rule : rules) {
								String ac = rule.getHeaderSection().getACLine().getAccessionNumber();
								if (ruleACs.size() > 0) {
									if (ruleACs.contains(ac)) {
										if (format == null) {
											pirruleManager.printPIRRule(rule, PIRRuleManager.Format.UNIRULE, writer);
										} else if (format.toUpperCase().equals("UNIRULE")) {
											pirruleManager.printPIRRule(rule, PIRRuleManager.Format.UNIRULE, writer);
										} else if (format.toUpperCase().equals("HTML")) {
											pirruleManager.printPIRRule(rule, PIRRuleManager.Format.HTML, writer);
										} else {
											System.err.println("Command line parsing failed.\nReason: invalid format: " + format);
											hasCommandLineError = true;
										}
									}
								} else {
									if (format == null) {
										pirruleManager.printPIRRule(rule, PIRRuleManager.Format.UNIRULE, writer);
									} else if (format.toUpperCase().equals("UNIRULE")) {
										pirruleManager.printPIRRule(rule, PIRRuleManager.Format.UNIRULE, writer);
									} else if (format.toUpperCase().equals("HTML")) {
										pirruleManager.printPIRRule(rule, PIRRuleManager.Format.HTML, writer);
									} else {
										System.err.println("Command line parsing failed.\nReason: invalid format: " + format);
										hasCommandLineError = true;
									}
								}
							}
						}
					} else if (action.equals("propagate")) {
						if (propagationDirectory == null) {
							System.err
									.println("Command line parsing failed.\nReason: propagationDirectory (-p, --propagationDirectory) is required.");
							hasCommandLineError = true;
						} else {
							File cwd = new File(propagationDirectory);
							if (!cwd.exists()) {
								System.err.println("Command line parsing failed.\nReason: direcotry '" + propagationDirectory
										+ "' does not exist or is not accessible.");
								hasCommandLineError = true;
							} else {
								PIRRuleManager pirruleManager = pirRuleDataFactory.buildPIRRuleManager(pirRuleDataFactory);
								PropagationManager propagationManager = propagationDataFactory.buildPropagationManager(propagationDataFactory,
										pirruleManager);
								System.out.println("CWD: " + propagationDirectory);
								Date date = new java.util.Date();
								System.out.println("Start: " + new Timestamp(date.getTime()));
								long startTime = System.currentTimeMillis();

								propagationManager.propagate(propagationDirectory);
								date = new java.util.Date();
								System.out.println("End:   " + new Timestamp(date.getTime()));
								long estimatedTime = System.currentTimeMillis() - startTime;

								System.out.println("Time in seconds: " + estimatedTime / 1000.0 + "\n");
							}
						}
					} else {
						System.err.println("Command line parsing failed.\nReason: invalid action: " + action);
						hasCommandLineError = true;
					}

				}
				writer.flush();
				writer.close();
			}
		} catch (ParseException exp) {
			System.err.println("Command line parsing failed.\nReason: " + exp.getMessage());
			hasCommandLineError = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (hasCommandLineError) {
			formatter.setWidth(400);
			formatter.printHelp(400, usage, null, options, null);
		}
	}

	private static void checkHMM3Model(PIRRule rule, String templateToModelSearchOutputFile, PrintWriter writer) {
		ProfileHMMManager profileManager = new ProfileHMMManagerImpl();
		Template template = profileManager.getTemplateFromHMMSearch(templateToModelSearchOutputFile);
		// System.out.println(rule.getAnnotationSection().getLines());
		//System.out.println(rule.getRuleAC());
		String result = "";
		String ftFrom = "";
		for (Line line : rule.getAnnotationSection().getLines()) {
			if(line instanceof FTFromLine) {
				ftFrom += ((FTFromLine)line).getFTTemplateAccessionNumber();
			}
			if (line instanceof FeatureDescriptionLine) {
				//System.out.println(line);
				FeatureDescriptionLine featureDescriptionLine = (FeatureDescriptionLine) line;
				// checkTemplateProfileMatchState(featureDescriptionLine,
				// template);
				String from = featureDescriptionLine.getFeatureFromPosition();
				if(from.equals("Nter")) {
					from = "1";
				}
				
				String to = featureDescriptionLine.getFeatureToPosition();
				if(to.equals("Cter")) {
					to = Integer.toString(template.getLength());
				}
				String matchState = "";
//				if (from.equals(to)) {
//					matchState = checkTemplateProfileMatchState(Integer.parseInt(from), template);
//				} else {
//					for (int i = Integer.parseInt(from); i <= Integer.parseInt(to); i++) {
//						matchState += checkTemplateProfileMatchState(i, template);
//					}
//					matchState += checkTemplateProfileMatchState(Integer.parseInt(from), template);
//					matchState += checkTemplateProfileMatchState(Integer.parseInt(to), template);
					matchState = checkMatchState(Integer.parseInt(from), featureDescriptionLine.getFeatureConditionPattern(), template);
				//}
				if(matchState != null && matchState.length() > 0) {
					result += line+"\n";
					result +=matchState;
				}
			}
		}
		if(result.length() > 0) {
			//System.out.println(rule.getRuleAC()+"\n");
			System.out.println("Template: <a href=\"http://www.uniprot.org/uniprot/"+ftFrom+"\">"+ ftFrom+"</a><br/>\n");
			System.out.println("<pre>"+result+"</pre>\n");
		}
	}

	private static String checkMatchState(int start, String featureConditionPattern, Template template) {
		String matchState = "";
		//System.out.println(featureConditionPattern);
		String[] pattern = featureConditionPattern.split("-");
		int position = start;
		for(int i = 0; i < pattern.length; i++) {
			if(pattern[i].equals("x")) {
				position++;
			}
			else if(pattern[i].startsWith("x(")) {
				String repeatStr = pattern[i];
				repeatStr = repeatStr.replace("x(", "");
				repeatStr = repeatStr.replace(")", "");
				int repeatNum = Integer.parseInt(repeatStr);
				position += repeatNum;
			}
			else {
				//System.out.println(position);
				matchState += checkTemplateProfileMatchState(position, template);
				position++;
			}
		}
		return matchState;
	}

	private static String checkTemplateProfileMatchState(int position, Template template) {
		LinkedHashMap<String, List<TemplateResidue>> domainAlignments = template.getDomainAlignments();
		String matchState = "";
		for (String key : domainAlignments.keySet()) {
			// System.out.println(key);
			for (TemplateResidue residue : domainAlignments.get(key)) {
				if(residue.getPosition() == position) {
					if(residue.getMatchStatus() == false) {
						matchState += residue.toReport();
					}
				}
			}
		}
		if(matchState != null && matchState.length() > 0) {
			matchState += "\n";
		}
		return matchState;
	}

	private static void checkRuleTemplate(List<PIRRule> rules, List<UniProtEntry> swissProtEntries, PrintWriter writer) {
		writer.println("Rule template checking...\n");
		for (PIRRule rule : rules) {
			String ruleAC = rule.getRuleAC();
			String templateAC = rule.getTemplateAC();

			boolean foundTemplate = false;
			String note = "";
			for (UniProtEntry entry : swissProtEntries) {
				if (entry.getPrimiaryAccessionNumber().equals(templateAC)) {
					foundTemplate = true;
					if (entry.getDatabaseCrossReferences(EntryDatabaseType.PIRSF) == null) {
						note += "Template \"" + entry.getPrimiaryAccessionNumber() + "\" has no PIRSF DR line.\n";
					} else {
						note += checkAnnotation(rule, entry);
					}
				}
			}
			if (!foundTemplate) {
				note += "Template \"" + templateAC + "\" is not in the pre-release SwissProt .dat file\n\n";
			}
			if (note.equals("")) {
				// writer.println("--------------------------------------------------------------------------------");

				// writer.println("Rule "+ ruleAC+
				// ", Template "+templateAC+", "+ rule.getLastModifyCurator()+
				// " ("+ rule.getLastModifyDateTime()+"): OK\n\n");
			} else {
				writer.println("--------------------------------------------------------------------------------");

				writer.println("Rule " + ruleAC + ", Template " + templateAC + ", " + rule.getLastModifyCurator() + " ("
						+ rule.getLastModifyDateTime() + "): \n\n" + note + "\n");
			}
			// writer.println("\n");
		}

	}

	private static String checkAnnotation(PIRRule rule, UniProtEntry entry) {
		String note = "";
		List<EntryKeyword> entryKeywords = entry.getKeywords();
		List<EntryComment> entryComments = entry.getComments();
		List<EntryFeatureTable> entryFeatures = entry.getFeatures();

		List<Line> ruleLines = rule.getRuleLines();
		for (Line ruleLine : ruleLines) {
			if (ruleLine instanceof KWLine) {
				note += checkKeyword((KWLine) ruleLine, entryKeywords);
			} else if (ruleLine instanceof CCLine) {
				note += checkComment((CCLine) ruleLine, entryComments);
			} else if (ruleLine instanceof FeatureDescriptionLine) {
				note += checkFeatureResidue((FeatureDescriptionLine) ruleLine, entryFeatures, entry, rule);
				note += checkFeatureDescription((FeatureDescriptionLine) ruleLine, entryFeatures, entry);
			}
		}
		return note;
	}

	private static String checkFeatureResidue(FeatureDescriptionLine ruleLine, List<EntryFeatureTable> entryFeatures, UniProtEntry entry, PIRRule rule) {
		boolean foundFeatureResidues = false;
		String entryFeatureResidues = "";
		String note = "";
		for (EntryFeatureTable entryFeature : entryFeatures) {
			String ruleStart = "";
			if (ruleLine.getFeatureFromPosition().equals("Nter")) {
				ruleStart = "1";
			} else {
				ruleStart = ruleLine.getFeatureFromPosition();
			}
			String ruleEnd = "";
			if (ruleLine.getFeatureToPosition().equals("Cter")) {
				ruleEnd = "" + (entry.getSequence().getSequenceData().length());
			} else {
				ruleEnd = ruleLine.getFeatureToPosition();
			}
			if (entryFeature.getFromEndPoint().equals(ruleStart) && entryFeature.getToEndPoint().equals(ruleEnd)) {
				int start = Integer.parseInt(entryFeature.getFromEndPoint());
				int end = Integer.parseInt(entryFeature.getToEndPoint());
				String entrySeqResidues = entry.getSequence().getSequenceData().substring(start - 1, end);
				if (debug) {
					System.out.println(rule.getRuleAC() + " | " + ruleLine.getFeatureFromPosition() + " | " + ruleLine.getFeatureToPosition() + " | "
							+ ruleLine.getFeatureConditionPattern() + " ; " + entry.getPrimiaryAccessionNumber() + " | " + start + " | " + end
							+ " | " + entrySeqResidues);
				}
				if (PropagationUtil.checkPatternMatch(entrySeqResidues, ruleLine.getFeatureConditionPattern())) {
					foundFeatureResidues = true;
				} else {
					// EntryFeatureTable entryFeatureTmp = entryFeature;
					// entryFeatureTmp.setFeatureDescription(entrySeqResidues);
					// if(!entryFeatureResidues.contains(entryFeature.toReport()
					// + " (" + entrySeqResidues+")")) {
					// entryFeatureResidues += entryFeature.toReport() + " (" +
					// entrySeqResidues+")\n";
					// }

					if (!entryFeatureResidues.contains(entryFeature.toReport())) {
						entryFeatureResidues += entryFeature.toReport() + "\n";
					}
				}
			}
		}
		if (foundFeatureResidues) {
			return note;
		} else {
			// System.out.println(rule.getRuleAC() + " | "+
			// entry.getPrimiaryAccessionNumber());
			note += "Feature residues in the rule does not match the one in the template:\n";
			note += "[Rule]\n" + ruleLine.toReport() + "\n";
			note += "[Template]\n" + entryFeatureResidues + "\n\n";
			return note;
		}
	}

	private static String checkFeatureDescription(FeatureDescriptionLine ruleLine, List<EntryFeatureTable> entryFeatures, UniProtEntry entry) {
		boolean foundFeatureDescription = false;
		String featureDescription = "";
		String note = "";
		for (EntryFeatureTable entryFeature : entryFeatures) {
			if (entryFeature.getFeatureType().name().equals(ruleLine.getFeatureKey().name())) {
				String ruleStart = "";
				if (ruleLine.getFeatureFromPosition().equals("Nter")) {
					ruleStart = "1";
				} else {
					ruleStart = ruleLine.getFeatureFromPosition();
				}
				String ruleEnd = "";
				if (ruleLine.getFeatureToPosition().equals("Cter")) {
					ruleEnd = "" + (entry.getSequence().getSequenceData().length());
				} else {
					ruleEnd = ruleLine.getFeatureToPosition();
				}
				if (entryFeature.getFromEndPoint().equals(ruleStart)) {
					if (entryFeature.getToEndPoint().equals(ruleEnd)) {
						// if(entryFeature.getDescription().replaceAll("\\.$",
						// "").equals(ruleLine.getFeatureDescription().replaceAll("\\.$",
						// ""))) {
						if (entryFeature.getFeatureDescription().equals(ruleLine.getFeatureDescription())) {
							foundFeatureDescription = true;
						} else {
							featureDescription += entryFeature.toReport() + "\n";
						}
					}
				}
			}

		}

		if (foundFeatureDescription) {
			return note;
		} else {
			note += "Feature description in the rule does not match the one in the template:\n";
			note += "[Rule]\n" + ruleLine.toReport() + "\n";
			note += "[Template]\n" + featureDescription + "\n\n";
			return note;
		}
	}

	private static String checkComment(CCLine ruleLine, List<EntryComment> entryComments) {
		boolean found = false;
		String note = "";
		String comment = "";
		for (EntryComment entryComment : entryComments) {
			if (entryComment.getCommentType().name().equals(ruleLine.getTopic().name())) {
				// System.out.println(entryComment.getCommentType());
				// System.out.println(entryComment.getCommentDescription()+"|"+ruleLine.getCommentDescription()+"|");
				// System.out.println();
				// if(entryComment.getCommentDescription().replaceAll("\\.$",
				// "").equals(ruleLine.getCommentDescription().replaceAll("\\.$",
				// ""))) {
				if (entryComment.getCommentDescription().equals(ruleLine.getCommentDescription())) {
					found = true;
					break;
				} else {
					// System.out.println("====");
					// System.out.println(entryComment.getCommentDescription().replaceAll("\\.$",
					// "")+"|");
					// System.out.println(ruleLine.getCommentDescription().replaceAll("\\.$",
					// "")+"|");
					// System.out.println(ruleLine.getDescription());
					// System.out.println("====");
					// if(entryComment.getCommentDescription().contains(ruleLine.getCommentDescription()))
					// {
					comment += entryComment.toReport() + "\n";
					// }
				}
			}
		}
		if (found) {
			return note;
		} else {
			note += "Comment in the rule does not match the one in the template:\n";
			note += "[Rule]\n" + ruleLine.toReport() + "\n";
			note += "[Template]\n" + comment + "\n\n";
			return note;
		}
	}

	private static String checkKeyword(KWLine ruleLine, List<EntryKeyword> entryKeywords) {
		boolean found = false;
		for (EntryKeyword entryKeyword : entryKeywords) {
			if (entryKeyword.getKeyword().equals(ruleLine.getKeyword())) {
				found = true;
			}
		}
		if (found) {
			return "";
		} else {
			return "Keyword does not match in the Template:\n" + ruleLine.getKeyword() + "\n\n";
		}
	}

	private static List<String> readFileLineByLine(String filePath) {
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));

			String line = null;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				lines.add(line);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

}
