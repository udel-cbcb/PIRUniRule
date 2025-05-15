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
 * Utility methods associated with PIRRule.
 */

public class PIRRuleUtil {
		public PIRRuleUtil() {
		super();
	}

		PIRRule rule;

		public PIRRuleUtil(PIRRule rule) {
			super();
			this.rule = rule;
		}

		private List<ControlBlock> getLineControlBlocks(LineType lineType) {
			List<ControlBlock> lineControlBlocks = null;
			List<ControlBlock> controlBlocks = this.rule.getControlBlocks();
			if( controlBlocks != null && controlBlocks.size() > 0) {
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
		
		
	
		private List<Line> getNonControlBlockLines(RuleSection currentSection, LineType lineType) {
			
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
			return this.getLineControlBlocks(LineType.DE);
		}
		
		public List<Line> getNonControlBlockDELines() {
			return this.getNonControlBlockLines(this.rule.getAnnotationSection(), LineType.DE);
		}

		
		public List<ControlBlock> getCCLineControlBlocks() {
			return this.getLineControlBlocks(LineType.CC);
		}
		
		
		
		public List<Line> getNonControlBlockCCLines() {
			return getNonControlBlockLines(this.rule.getAnnotationSection(),LineType.CC);
		}
		

	
		public List<ControlBlock> getKWLineControlBlocks() {
			return this.getLineControlBlocks(LineType.KW);
		}

		
		public List<Line> getNonControlBlockKWLines() {
			return getNonControlBlockLines(this.rule.getAnnotationSection(), LineType.KW);
		}

		
		public List<ControlBlock> getGOLineControlBlocks() {
			return this.getLineControlBlocks(LineType.GO);
		}

		
		public List<Line> getNonControlBlockGOLines() {
			return getNonControlBlockLines(this.rule.getAnnotationSection(), LineType.GO);
		}

		
		public List<ControlBlock> getFTLineControlBlocks() {
			return this.getLineControlBlocks(LineType.FT);
		}

		
		public List<Line> getNonControlBlockFTLines() {
			return getNonControlBlockLines(this.rule.getAnnotationSection(),LineType.FT);
		}

		
		public List<ControlBlock> getSizeLineControlBlocks() {
			return this.getLineControlBlocks(LineType.SIZE);
		}

		
		public List<Line> getNonControlBlockSizeLines() {
			return getNonControlBlockLines(this.rule.getComputingSection(), LineType.SIZE);
		}

		
		public List<ControlBlock> getRelatedLineControlBlocks() {
			return this.getLineControlBlocks(LineType.RELATED);
		}

		
		public List<Line> getNonControlBlockRelatedLines() {
			return getNonControlBlockLines(this.rule.getComputingSection(), LineType.RELATED);
		}

		
		public List<ControlBlock> getTemplateLineControlBlocks() {
			return this.getLineControlBlocks(LineType.TEMPLATE);
		}

		
		public List<Line> getNonControlBlockTemplateLines() {
			return getNonControlBlockLines(this.rule.getComputingSection(), LineType.TEMPLATE);
		}

		
		public List<ControlBlock> getScopeBlockControlBlocks() {
			return this.getLineControlBlocks(LineType.SCOPEBLOCK);
		}

		
		public List<Line> getNonControlBlockScopeBlockLines() {
			return getNonControlBlockLines(this.rule.getComputingSection(), LineType.SCOPEBLOCK);
		}

		
		public List<ControlBlock> getFusionBlockControlBlocks() {
			return this.getLineControlBlocks(LineType.FUSIONBLOCK);
		}

		
		public List<Line> getNonControlBlockFusionBlockLines() {
			return getNonControlBlockLines(this.rule.getComputingSection(), LineType.FUSIONBLOCK);
		}

		
		public List<ControlBlock> getDuplicateLineControlBlocks() {
			return this.getLineControlBlocks(LineType.DUPLICATE);
		}

		
		public List<Line> getNonControlBlockDuplicateLines() {
			return getNonControlBlockLines(this.rule.getComputingSection(), LineType.DUPLICATE);
		}

		
		public List<ControlBlock> getPlasmidLineControlBlocks() {
			return this.getLineControlBlocks(LineType.PLASMID);
		}

		
		public List<Line> getNonControlBlockPlasmidLines() {
			return getNonControlBlockLines(this.rule.getComputingSection(), LineType.PLASMID);
		}

		
		public List<ControlBlock> getCommentsLineControlBlocks() {
			return this.getLineControlBlocks(LineType.COMMENTS);
		}

		
		public List<Line> getNonControlBlockCommentsLines() {
			return getNonControlBlockLines(this.rule.getComputingSection(), LineType.COMMENTS);
		}

		
		public List<ControlBlock> getDRLineControlBlocks() {
			return this.getLineControlBlocks(LineType.DR);
		}

		
		public List<Line> getNonControlBlockDRLines() {
			return getNonControlBlockLines(this.rule.getComputingSection(), LineType.DR);
		}

		
	
}
