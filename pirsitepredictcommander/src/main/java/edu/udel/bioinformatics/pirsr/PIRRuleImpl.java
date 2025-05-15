package edu.udel.bioinformatics.pirsr;


import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.model.AnnotationSection;
import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.CCNoneLine;
import org.proteininformationresource.pirsr.model.CaseStatement;
import org.proteininformationresource.pirsr.model.CommentsLine;
import org.proteininformationresource.pirsr.model.ComputingSection;
import org.proteininformationresource.pirsr.model.ControlBlock;
import org.proteininformationresource.pirsr.model.ControlStatement;
import org.proteininformationresource.pirsr.model.DELine;
import org.proteininformationresource.pirsr.model.DRLine;
import org.proteininformationresource.pirsr.model.DuplicateLine;
import org.proteininformationresource.pirsr.model.ElseCaseStatement;
import org.proteininformationresource.pirsr.model.ElseStatement;
import org.proteininformationresource.pirsr.model.EndCaseStatement;
import org.proteininformationresource.pirsr.model.FTFromLine;
import org.proteininformationresource.pirsr.model.FTLine;
import org.proteininformationresource.pirsr.model.FTNoneLine;
import org.proteininformationresource.pirsr.model.FusionBlock;
import org.proteininformationresource.pirsr.model.GOLine;
import org.proteininformationresource.pirsr.model.HeaderSection;
import org.proteininformationresource.pirsr.model.HistoryInfo;
import org.proteininformationresource.pirsr.model.HistorySection;
import org.proteininformationresource.pirsr.model.InternalCommentsLine;
import org.proteininformationresource.pirsr.model.KWLine;
import org.proteininformationresource.pirsr.model.Line;
import org.proteininformationresource.pirsr.model.LineType;
import org.proteininformationresource.pirsr.model.PIRRule;
import org.proteininformationresource.pirsr.model.PIRRuleManager;
import org.proteininformationresource.pirsr.model.PlasmidLine;
import org.proteininformationresource.pirsr.model.RelatedLine;
import org.proteininformationresource.pirsr.model.RuleSection;
import org.proteininformationresource.pirsr.model.ScopeBlock;
import org.proteininformationresource.pirsr.model.SizeLine;
import org.proteininformationresource.pirsr.model.TemplateLine;
import org.proteininformationresource.pirsr.model.XXLine;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br><br>
 *
 * PIR Ruleis a format describing conditional annotation templates (rules) used by UniProtKB automated annotation projects. 
 * It defines annotation which will be generated for (selected) predicted features.
 * Each rule entry consists of the following parts.
 * 1. The Header section contains the rule accession number, the data class, identifier(s) of the feature(s) that trigger(s) the rule, as well as some basic information on the rule.
 * 2. The Annotation section. All UniProtKB annotation relevant for a PIR Rule is indicated. Not all annotation lines apply to all true hits. Therefore, case statements restrict all or part of the annotation to further conditions like the taxonomic group or the size of a protein.
 * 3. The Computing section covers information on a protein, domain or site relevant to programs. 
 * 4. The History section indicates the version number, date and author of the modification.
 * 
 * A PIR Rule cannot be modified directly.  Changes must be applied via its <code>SiteRuleManager</code>.
 */

public class PIRRuleImpl implements PIRRule {
		public PIRRuleImpl() {
		super();
	}

		PIRRuleManager pirruleManager;
		HeaderSection headerSection;
		AnnotationSection annotationSection;
		ComputingSection computingSection;
		HistorySection historySection;
		List<ControlBlock> controlBlocks;
		RuleSection currentSection = null;
		List<Line> ruleLines = new ArrayList<Line>();

		public PIRRuleImpl(PIRRuleManager pirruleManager,
				HeaderSection headerSection,
				AnnotationSection annotationSection,
				ComputingSection computingSection, HistorySection historySection) {
			super();
			this.pirruleManager = pirruleManager;
			this.headerSection = headerSection;
			this.annotationSection = annotationSection;
			this.computingSection = computingSection;
			this.historySection = historySection;
		}

		public PIRRuleImpl(PIRRuleManager manager) {
			super();
			this.pirruleManager = manager;
		}

		
		public PIRRuleManager getPIRRuleManager() {
			return this.pirruleManager;
		}

		
		public HeaderSection getHeaderSection() {
			return this.headerSection;
		}

		
		public AnnotationSection getAnnotationSection() {
			return this.annotationSection;
		}

		
		public ComputingSection getComputingSection() {
			return this.computingSection;
		}

		
		public HistorySection getHistorySection() {
			return this.historySection;
		}

		
		public void setPIRRuleMananger(PIRRuleManager pirruleManager) {
			this.pirruleManager = pirruleManager;
		}

		
		public void setHeaderSection(HeaderSection headerSection) {
			this.headerSection = headerSection;
		}

		
		public void setAnnotationSection(AnnotationSection annotationSection) {
			this.annotationSection = annotationSection;
		}

		
		public void setComputingSection(ComputingSection computingSection) {
			this.computingSection = computingSection;
		}

		
		public void setHistorySection(HistorySection historySection) {
			this.historySection = historySection;
		}

		
		public List<ControlBlock> getControlBlocks() {
			return this.controlBlocks;
		}

		
		public void setControlBlocks(List<ControlBlock> controlBlocks) {
			this.controlBlocks = controlBlocks;
		}

		
	/*
		private List<ControlBlock> getLineControlBlocks(LineType lineType) {
			List<ControlBlock> lineControlBlocks = null;
			if(this.controlBlocks != null && this.controlBlocks.size() > 0) {
				for(ControlBlock controlBlock : controlBlocks) {
					ControlBlock lineControlBlock = getControlBlockLines(controlBlock, lineType);
					if(lineControlBlock != null) {
						if(lineControlBlocks == null) {
							lineControlBlocks = new ArrayList<ControlBlock>();
						}
						lineControlBlocks.add(lineControlBlock);
					}
				}
			}
			return lineControlBlocks;
		}

		
		private ControlBlock getControlBlockLines(ControlBlock controlBlock, LineType lineType) {
			ControlBlock lineControlBlock = new ControlBlockImpl();
			List<ControlStatement> lineControlStatements = new ArrayList<ControlStatement>();
			boolean hasLine = false;
			for(ControlStatement controlStatement : controlBlock.getControlStatements()) {
				ControlStatement lineControlStatement = null;
				if(controlStatement instanceof CaseStatement) {
					lineControlStatement = new CaseStatementImpl();
				}
				else if(controlStatement instanceof ElseCaseStatement) {
					lineControlStatement = new ElseCaseStatementImpl();
				}
				else if(controlStatement instanceof ElseStatement) {
					lineControlStatement = new ElseStatementImpl();
				}
				else if(controlStatement instanceof EndCaseStatement) {
					lineControlStatement = new EndCaseStatementImpl();
				}
				lineControlStatement.setExpression(controlStatement.getExpression());
				List<Line> applicableLines = controlStatement.getApplicableLines();
				if(applicableLines != null && applicableLines.size() > 0) {
					ArrayList<Line> lineApplicableLines = (ArrayList<Line>) lineControlStatement.getApplicableLines();
					if( lineApplicableLines == null) {
						lineApplicableLines = new ArrayList<Line>();
					}
					for(Line line : applicableLines) {
						
						if(lineType.equals(LineType.DE) && line instanceof DELine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.CC) && line instanceof CCLine && !(line instanceof CCNoneLine)) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.DR) && line instanceof DRLine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.KW) && line instanceof KWLine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.GO) && line instanceof GOLine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.FT) && line instanceof FTLine && !(line instanceof FTNoneLine)) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.XX) && line instanceof XXLine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.INTERNALCOMMENTS) && line instanceof InternalCommentsLine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.SIZE) && line instanceof SizeLine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.RELATED) && line instanceof RelatedLine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.TEMPLATE) && line instanceof TemplateLine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.SCOPEBLOCK) && line instanceof ScopeBlock) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.FUSIONBLOCK) && line instanceof FusionBlock) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.DUPLICATE) && line instanceof DuplicateLine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.PLASMID) && line instanceof PlasmidLine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
						else if(lineType.equals(LineType.COMMENTS) && line instanceof CommentsLine) {
							lineApplicableLines.add(line);
							hasLine = true;
						}
					}
				}
				lineControlStatements.add(lineControlStatement);
			}
			if(hasLine) {
				lineControlBlock.setControlStatements(lineControlStatements);
				return lineControlBlock;
			}
			else {
				return null;
			}
		}
		
		
	
		private List<Line> getNonControlBlockLines(LineType lineType) {
			
			List<Line> nonControlBlockLines = null;
			List<Line> allNonControlBlockLines = currentSection.getNonControlBlockLines();
			if(allNonControlBlockLines != null && allNonControlBlockLines.size() > 0) {
				if(nonControlBlockLines == null) {
					nonControlBlockLines = new ArrayList<Line>();
				}
				for(Line line : allNonControlBlockLines) {
					if(lineType.equals(LineType.DE) && line instanceof DELine) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.CC) && line instanceof CCLine) {
						//if(!(line instanceof CCLine)) {
							nonControlBlockLines.add(line);
						//}
					}
					else if(lineType.equals(LineType.DR) && line instanceof DRLine) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.KW) && line instanceof KWLine) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.GO) && line instanceof GOLine) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.FT) && line instanceof FTLine) {
						//if(!(line instanceof FTNoneLine)) {
							nonControlBlockLines.add(line);
						//}
					}
					else if(lineType.equals(LineType.XX) && line instanceof XXLine) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.INTERNALCOMMENTS) && line instanceof InternalCommentsLine) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.SIZE) && line instanceof SizeLine) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.RELATED) && line instanceof RelatedLine) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.TEMPLATE) && line instanceof TemplateLine) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.SCOPEBLOCK) && line instanceof ScopeBlock) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.FUSIONBLOCK) && line instanceof FusionBlock) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.DUPLICATE) && line instanceof DuplicateLine) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.PLASMID) && line instanceof PlasmidLine) {
						nonControlBlockLines.add(line);
					}
					else if(lineType.equals(LineType.COMMENTS) && line instanceof CommentsLine) {
						nonControlBlockLines.add(line);
					}
				}
			}
			return nonControlBlockLines;
		}
		
		
		public List<ControlBlock> getDELineControlBlocks() {
			currentSection = this.annotationSection;
			return this.getLineControlBlocks(LineType.DE);
		}
		
		public List<Line> getNonControlBlockDELines() {
			currentSection = this.annotationSection;
			return this.getNonControlBlockLines(LineType.DE);
		}

		
		public List<ControlBlock> getCCLineControlBlocks() {
			currentSection = this.annotationSection;
			return this.getLineControlBlocks(LineType.CC);
		}
		
		
		
		public List<Line> getNonControlBlockCCLines() {
			currentSection = this.annotationSection;
			return getNonControlBlockLines(LineType.CC);
		}
		

		
		public List<ControlBlock> getKWLineControlBlocks() {
			currentSection = this.annotationSection;
			return this.getLineControlBlocks(LineType.KW);
		}

		
		public List<Line> getNonControlBlockKWLines() {
			currentSection = this.annotationSection;
			return getNonControlBlockLines(LineType.KW);
		}

		
		public List<ControlBlock> getGOLineControlBlocks() {
			currentSection = this.annotationSection;
			return this.getLineControlBlocks(LineType.GO);
		}

		
		public List<Line> getNonControlBlockGOLines() {
			currentSection = this.annotationSection;
			return getNonControlBlockLines(LineType.GO);
		}

		
		public List<ControlBlock> getFTLineControlBlocks() {
			currentSection = this.annotationSection;
			return this.getLineControlBlocks(LineType.FT);
		}

		
		public List<Line> getNonControlBlockFTLines() {
			currentSection = this.annotationSection;
			return getNonControlBlockLines(LineType.FT);
		}

		
		public List<ControlBlock> getSizeLineControlBlocks() {
			currentSection = this.computingSection;
			return this.getLineControlBlocks(LineType.SIZE);
		}

		
		public List<Line> getNonControlBlockSizeLines() {
			currentSection = this.computingSection;
			return getNonControlBlockLines(LineType.SIZE);
		}

		
		public List<ControlBlock> getRelatedLineControlBlocks() {
			currentSection = this.computingSection;
			return this.getLineControlBlocks(LineType.RELATED);
		}

		
		public List<Line> getNonControlBlockRelatedLines() {
			currentSection = this.computingSection;
			return getNonControlBlockLines(LineType.RELATED);
		}

		
		public List<ControlBlock> getTemplateLineControlBlocks() {
			currentSection = this.computingSection;
			return this.getLineControlBlocks(LineType.TEMPLATE);
		}

		
		public List<Line> getNonControlBlockTemplateLines() {
			currentSection = this.computingSection;
			return getNonControlBlockLines(LineType.TEMPLATE);
		}

		
		public List<ControlBlock> getScopeBlockControlBlocks() {
			currentSection = this.computingSection;
			return this.getLineControlBlocks(LineType.SCOPEBLOCK);
		}

		
		public List<Line> getNonControlBlockScopeBlockLines() {
			currentSection = this.computingSection;
			return getNonControlBlockLines(LineType.SCOPEBLOCK);
		}

		
		public List<ControlBlock> getFusionBlockControlBlocks() {
			currentSection = this.computingSection;
			return this.getLineControlBlocks(LineType.FUSIONBLOCK);
		}

		
		public List<Line> getNonControlBlockFusionBlockLines() {
			currentSection = this.computingSection;
			return getNonControlBlockLines(LineType.FUSIONBLOCK);
		}

		
		public List<ControlBlock> getDuplicateLineControlBlocks() {
			currentSection = this.computingSection;
			return this.getLineControlBlocks(LineType.DUPLICATE);
		}

		
		public List<Line> getNonControlBlockDuplicateLines() {
			currentSection = this.computingSection;
			return getNonControlBlockLines(LineType.DUPLICATE);
		}

		
		public List<ControlBlock> getPlasmidLineControlBlocks() {
			currentSection = this.computingSection;
			return this.getLineControlBlocks(LineType.PLASMID);
		}

		
		public List<Line> getNonControlBlockPlasmidLines() {
			currentSection = this.computingSection;
			return getNonControlBlockLines(LineType.PLASMID);
		}

		
		public List<ControlBlock> getCommentsLineControlBlocks() {
			currentSection = this.computingSection;
			return this.getLineControlBlocks(LineType.COMMENTS);
		}

		
		public List<Line> getNonControlBlockCommentsLines() {
			currentSection = this.computingSection;
			return getNonControlBlockLines(LineType.COMMENTS);
		}

		
		public List<ControlBlock> getDRLineControlBlocks() {
			currentSection = this.annotationSection;
			return this.getLineControlBlocks(LineType.DR);
		}

		
		public List<Line> getNonControlBlockDRLines() {
			currentSection = this.annotationSection;
			return getNonControlBlockLines(LineType.DR);
		}
*/
		
		public List<Line> getRuleLines() {
			return this.ruleLines;
		}

		
		public void setRuleLines(List<Line> lines) {
			this.ruleLines = lines;
		}

		
		public String getRuleAC() {
			return this.headerSection.getACLine().getAccessionNumber();
		}

		
		public String getTemplateAC() {
			List<Line> lines = this.getAnnotationSection().getLines();
			for(Line line: lines) {
				if(line instanceof FTFromLine) {
					return ((FTFromLine)line).getFTTemplateAccessionNumber();
				}
			}
			return null;
		}

		
		public String getTrigger() {
			return this.getHeaderSection().getTRLine().getIdentifier1();
		}

		
		public String getLastModifyDateTime() {
			String lastModifyDateTime = "";
			List<HistoryInfo> historyInfos = this.getHistorySection().getHistoryInfos();
			if(historyInfos != null) {
				HistoryInfo lastHistoryInfo = historyInfos.get(historyInfos.size()-1);
				lastModifyDateTime = lastHistoryInfo.getDateTime();
			}
			return lastModifyDateTime;
		}

		
		public String getLastModifyCurator() {
			String lastModifyCurator = "";
			List<HistoryInfo> historyInfos = this.getHistorySection().getHistoryInfos();
			if(historyInfos != null) {
				HistoryInfo lastHistoryInfo = historyInfos.get(historyInfos.size()-1);
				lastModifyCurator = lastHistoryInfo.getCurator();
			}
			return lastModifyCurator;
		}

		

		
		
	
}
