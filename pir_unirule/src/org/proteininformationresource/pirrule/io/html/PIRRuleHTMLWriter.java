package org.proteininformationresource.pirrule.io.html;

import java.util.List;

import org.proteininformationresource.pirrule.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirrule.model.CCLine;
import org.proteininformationresource.pirrule.model.CaseStatement;
import org.proteininformationresource.pirrule.model.ControlBlock;
import org.proteininformationresource.pirrule.model.ControlStatement;
import org.proteininformationresource.pirrule.model.DRLine;
import org.proteininformationresource.pirrule.model.ElseCaseStatement;
import org.proteininformationresource.pirrule.model.ElseStatement;
import org.proteininformationresource.pirrule.model.EndCaseStatement;
import org.proteininformationresource.pirrule.model.FTFromLine;
import org.proteininformationresource.pirrule.model.FTNoneLine;
import org.proteininformationresource.pirrule.model.FeatureDescriptionLine;
import org.proteininformationresource.pirrule.model.GOLine;
import org.proteininformationresource.pirrule.model.HeaderSection;
import org.proteininformationresource.pirrule.model.HistoryInfo;
import org.proteininformationresource.pirrule.model.HistorySection;
import org.proteininformationresource.pirrule.model.KWLine;
import org.proteininformationresource.pirrule.model.Line;
import org.proteininformationresource.pirrule.model.LineType;
import org.proteininformationresource.pirrule.model.PIRRule;
import org.proteininformationresource.pirrule.model.RelatedLine;
import org.proteininformationresource.pirrule.model.SizeLine;
import org.proteininformationresource.pirrule.model.TRLine;
import org.proteininformationresource.pirrule.model.TemplateLine;

import edu.udel.bioinformatics.pirrule.PIRRuleUtil;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 27, 2014<br>
 * 
 * Create the table view of PIR Rule. <br>
 */
public class PIRRuleHTMLWriter {
	private PIRRule pirrule;
	private PIRRuleUtil pirruleUtil;
	
	public PIRRuleHTMLWriter() {
		super();
	}

	public PIRRuleHTMLWriter(PIRRule pirrule) {
		this.pirrule = pirrule;
		this.pirruleUtil = pirrule.getPIRRuleManager().getDataFactory().buidPIRRuleUtil(pirrule);
	}

	private String getHeaderSection() {
		HeaderSection headerSectionData = this.pirrule.getHeaderSection();
		String headerSection = "";
		
		headerSection += "<table width=\"100%\" bgcolor=\"#ffffff\" cellpadding=\"8\" cellspacing=\"0\" width=\"100%\" class=boxTable>\n"
			          +  "	<tr>\n"
			          +  "		<th class=\"right\" align=center style=\"font-size: 12pt;\">Header Section</th>\n"
			          +  "	</tr>\n"	
			          +  "	<tr>\n"
			          +  "		<td>\n"
			          +  "			<div class=\"margin\" style=\"font-size: 10pt;\">\n"
			          +  "				<table width=\"100%\">\n"
			          +	 "					<tr>\n"
			          +  "						<td width=\"15%\"><b>Accession</b></td>\n"
			          +  "						<td width=\"85%\" style=\"font-size: 10pt;\">"+ headerSectionData.getACLine().getAccessionNumber()+ "</td>\n"
			          +  "					</tr>\n"
			          +	 "					<tr>\n"
			          +  "						<td width=\"15%\"><b>Dates</b></td>\n"
			          +  "						<td width=\"85%\" valign=\"top\" style=\"font-size: 10pt;\">" + getDateInfo()+ "</td>\n"
			          +  "					</tr>\n"
			          +	 "					<tr>\n"
			          +  "						<td width=\"15%\"><b>Data Class</b></td>\n"
			          +  "						<td width=\"85%\" valign=\"top\" style=\"font-size: 10pt;\">"+ headerSectionData.getDCLine().getDataClass() + "</td>\n"
			          +  "					</tr>\n"
			          +	 "					<tr>\n"
			          +  "						<td width=\"15%\"><b>Predictors</b></td>\n"
			          +  "						<td width=\"85%\" valign=\"top\" style=\"font-size: 10pt;\">"+ getTRLineInfo() + "</td>\n"
			          +  "					</tr>\n"
			          +  "				</table>\n"
			          +  "			</div>\n"
			          +  "		</td>\n"
			          +  "	</tr>\n"
			          +  "</table>\n";
		

		return headerSection;
	}

	private String getTRLineInfo() {
		TRLine trLine = pirrule.getHeaderSection().getTRLine();
		return trLine.getDBName() + "; " + trLine.getIdentifier1() + "; " + 
				trLine.getIdentifier2() + "; " + trLine.getNBHits()+ "; level=" + trLine.getLevel() + "\n";
	}
	
	
	

	private String getDateInfo() {
		String dateInfo = "";
		HistorySection historySectionData = this.pirrule.getHistorySection();
		List<HistoryInfo> historyInfos = historySectionData.getHistoryInfos();
		if (historyInfos != null && historyInfos.size() > 0) {
			for (int i = 0; i < historyInfos.size(); i++) {
				if (i == 0) {
					dateInfo += historyInfos.get(i).getDateTime() + " (" + historyInfos.get(i).getType() + ")";
				} else {
					dateInfo += "<br/>" + historyInfos.get(i).getDateTime() + " (" + historyInfos.get(i).getType() + ")";
				}
			}
		}
		return dateInfo;
	}

	private String getAnnotationSection() {
		String annotationSection = "";
		annotationSection += "<table width=\"100%\" bgcolor=\"#ffffff\" cellpadding=\"8\" cellspacing=\"0\" width=\"100%\" class=boxTable>\n"
		          	      +  "	<tr>\n"
		                  +  "		<th class=\"right\" align=center style=\"font-size: 12pt;\">Annotation Section</th>\n"
		                  +  "	</tr>\n";
		if(!getDEControlBlocks().equals("") || !getNonControlBlockDELines().equals("") ) {
			annotationSection +=  getDELinesInfo();
		}
		if(!getCCControlBlocks().equals("") || !getNonControlBlockCCLines().equals("") ) {
			annotationSection +=  getCCLinesInfo();
		}
		if(!getDRControlBlocks().equals("") || !getNonControlBlockDRLines().equals("") ) {
			annotationSection +=  getDRLinesInfo();
		}
		if(!getKWControlBlocks().equals("") || !getNonControlBlockKWLines().equals("") ) {
			annotationSection +=  getKWLinesInfo();
		}
		if(!getGOControlBlocks().equals("") || !getNonControlBlockGOLines().equals("") ) {
			annotationSection +=  getGOLinesInfo();
		}
		if(!getFTControlBlocks().equals("") || !getNonControlBlockFTLines().equals("") ) {
			annotationSection +=  getFTLinesInfo();
		}

		annotationSection +=  "</table>\n";
		
		return annotationSection;
	}

	

	private String getFTLinesInfo() {
		String info = "";
		List<ControlBlock> ftControlBlocks = this.pirruleUtil.getFTLineControlBlocks();
		List<Line> ftNonControlBlockLines = this.pirruleUtil.getNonControlBlockFTLines();
		if(ftNonControlBlockLines.size() == 1 && ftNonControlBlockLines.get(0) instanceof FTNoneLine) {
			ftNonControlBlockLines = null;
		}
		if( ftControlBlocks != null || ftNonControlBlockLines!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Features</div>\n"
				 +  getFTControlBlocks()
				 +  getNonControlBlockFTLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}

	

	private String getGOLinesInfo() {
		String info = "";
		if(this.pirruleUtil.getGOLineControlBlocks() != null || this.pirruleUtil.getNonControlBlockGOLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Gene Ontology</div>\n"
				 +  getGOControlBlocks()
				 +  getNonControlBlockGOLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}

	

	private String getKWLinesInfo() {
		String info = "";
		if(this.pirruleUtil.getKWLineControlBlocks() != null || this.pirruleUtil.getNonControlBlockKWLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Keywords</div>\n"
				 +  getKWControlBlocks()
				 +  getNonControlBlockKWLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}
	
	private String getDRLinesInfo() {
		String info = "";
		if(this.pirruleUtil.getDRLineControlBlocks() != null || this.pirruleUtil.getNonControlBlockDRLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Cross-references</div>\n"
				 +  getDRControlBlocks()
				 +  getNonControlBlockDRLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}

	

	private String getCCLinesInfo() {
		String info = "";
		
		List<ControlBlock> ccControlBlocks = this.pirruleUtil.getCCLineControlBlocks();
		List<Line> ccNonControlBlockLines = this.pirruleUtil.getNonControlBlockCCLines();
		if(ccNonControlBlockLines.size() == 1 && ccNonControlBlockLines.get(0) instanceof FTNoneLine) {
			ccNonControlBlockLines = null;
		}
		if(ccControlBlocks != null || ccNonControlBlockLines!=null) {
		//if(this.pirrule.getCCLineControlBlocks() != null || this.pirrule.getNonControlBlockCCLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Comments</div>\n"
				 +  getCCControlBlocks()
				 +  getNonControlBlockCCLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}

	

	private String getDELinesInfo() {
		String info = "";
		if(this.pirruleUtil.getDELineControlBlocks() != null || this.pirruleUtil.getNonControlBlockDELines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Names</div>\n"
				 +  getDEControlBlocks()
				 +  getNonControlBlockDELines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}

	
	private String getNonControlBlockKWLines() {
		String str = "";
		List<Line> nonControlBlockKWLines = this.pirruleUtil.getNonControlBlockKWLines();
		if( nonControlBlockKWLines != null && nonControlBlockKWLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getKWBlockView(nonControlBlockKWLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}
	
	private String getNonControlBlockDRLines() {
		String str = "";
		List<Line> nonControlBlockDRLines = this.pirruleUtil.getNonControlBlockDRLines();
		if( nonControlBlockDRLines != null && nonControlBlockDRLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getDRBlockView(nonControlBlockDRLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}
	
	
	private String getDRBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		str +="<table width=\"100%\" style=\"font-size: 10pt\">\n";
		str += "		<tr>\n";
		for(int i = 0; i < lines.size(); i++) {
			DRLine drLine = (DRLine) lines.get(i);
			str += drLine.getDBName() +"; " + drLine.getFeatureName() +"; " + drLine.getIdentifier()+"; "
			+ drLine.getNBHits()+"; "+drLine.getTriggerLevel();
		}
		str += "	</tr>\n";
		str += "</table>\n";
		str += "</div>\n";
		return str;
	}

	private String getKWBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		str +="<table width=\"100%\" style=\"font-size: 10pt\">\n";
		str += "		<tr>\n";
		for(int i = 0; i < lines.size(); i++) {
			KWLine kwLine = (KWLine) lines.get(i);
			if(i == 0) {
				str += "<a href=\"http://www.uniprot.org/keywords/?query=name:"+kwLine.getKeyword()+"\">"+ kwLine.getKeyword()+"</a>";
			}
			else {
				str += "; <a href=\"http://www.uniprot.org/keywords/?query=name:"+kwLine.getKeyword()+"\">"+ kwLine.getKeyword()+"</a>";
			}

		}
		str += "	</tr>\n";
		str += "</table>\n";
		str += "</div>\n";
		return str;
	}
	
	private String getTemplateBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		str +="<table width=\"100%\" style=\"font-size: 10pt\">\n";
		str += "		<tr><td>\n";
		for(int i = 0; i < lines.size(); i++) {
			TemplateLine templateLine = (TemplateLine) lines.get(i);
			List<String> templateACs = templateLine.getTemplateAccessionNumbers();
			//str +="&nbsp;";
			for(String templateAC : templateACs) {
				str += "<a href=\"http://www.uniprot.org/uniprot/"+templateAC+"\">"+templateAC +"</a>; ";
			}

		}
		str += "	</td></tr>\n";
		str += "</table>\n";
		str += "</div>\n";
		return str;
	}

	private String getKWControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getKWLineControlBlocks(), LineType.KW);
	}
	
	private String getDRControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getDRLineControlBlocks(), LineType.DR);
	}
	
//	private String getNonControlBlockFTLines() {
//		return getControlBlocks(this.pirrule.getGOLineControlBlocks(), LineType.GO);
//	}
	
	private String getNonControlBlockFTLines() {
		String str = "";
		List<Line> nonControlBlockFTLines = this.pirruleUtil.getNonControlBlockFTLines();
		if( nonControlBlockFTLines != null && nonControlBlockFTLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getFTBlockView(nonControlBlockFTLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}

	private String getFTControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getFTLineControlBlocks(), LineType.FT);
	}
	
	private String getNonControlBlockGOLines() {
		String str = "";
		List<Line> nonControlBlockGOLines = this.pirruleUtil.getNonControlBlockGOLines();
		if( nonControlBlockGOLines != null && nonControlBlockGOLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getGOBlockView(nonControlBlockGOLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}

	private String getGOControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getGOLineControlBlocks(), LineType.GO);
	}
	
	private String getNonControlBlockDELines() {
		String str = "";
		List<Line> nonControlBlockDELines = this.pirruleUtil.getNonControlBlockDELines();
		if( nonControlBlockDELines != null && nonControlBlockDELines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getDEBlockView(nonControlBlockDELines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}

	private String getDEBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		str += "<pre>";
		for(Line line : lines) {
			str += line.toString().replace(UniRuleFlatFileConstants.DE_LINE_START, "");
		}
		//str += "\n";
		str += "</pre>\n";
		str += "</div>\n";
		return str;
	}
	
	private String getDuplicateBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		//str += "<pre>";
		for(Line line : lines) {
			str += line.toString().replace(UniRuleFlatFileConstants.DUPLICATE_LINE_START, "");
		}
		//str += "</pre>\n";
		str += "</div>\n";
		return str;
	}
	
	private String getPlasmidBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		//str += "<pre>";
		for(Line line : lines) {
			str += line.toString().replace(UniRuleFlatFileConstants.PLASMID_LINE_START, "");
		}
		//str += "</pre>\n";
		str += "</div>\n";
		return str;
	}
	
	private String getCommentsBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		//str += "<pre>";
		for(Line line : lines) {
			str += line.toString().replace(UniRuleFlatFileConstants.COMMENTS_START, "").replace("\n", "<br/>&nbsp;");
		}
		//str += "</pre>\n";
		str += "</div>\n";
		return str;
	}
	
	private String getScopeBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		str += "<pre class=\"taxon\">";
		for(Line line : lines) {
			str += line.toString().replace(UniRuleFlatFileConstants.SCOPE_BLOCK_START, "");
		}
		str += "</pre>\n";
		str += "</div>\n";
		return str;
	}
	
	private String getFusionBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		//str += "<pre>";
		for(Line line : lines) {
			str += line.toString().replace(UniRuleFlatFileConstants.FUSION_BLOCK_START+"\n", "").replace("\n", "<br/>");
		}
		//str += "</pre>\n";
		str += "</div>\n";
		return str;
	}
	
	private String getNonControlBlockCCLines() {
		String str = "";
		List<Line> nonControlBlockCCLines = this.pirruleUtil.getNonControlBlockCCLines();
		if( nonControlBlockCCLines != null && nonControlBlockCCLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
					 + getCCBlockView(nonControlBlockCCLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}

	private String getCCBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		str +="<table width=\"100%\" style=\"font-size: 10pt\">\n";
		
		for(Line line : lines) {
			str += "		<tr>\n";
			CCLine ccLine = (CCLine) line;
			str += "		<td valign=\"top\" width=\"20%\" style=\"font-size: 10pt; font-style: italic;\">" + ccLine.getTopic()+"</td>\n";
			str += "		<td valign=\"top\" width=\"80%\" style=\"font-size: 10pt;\">";
			List<String> descs = ccLine.getDescription();
			for(int i = 0; i < descs.size(); i++) {
				if(i == 0) {
					str += descs.get(i);
				}
				else {
					str += "<br/>" + descs.get(i);
				}
			}
			str +="		<br/></td>\n";
			str += "	</tr>\n";
			//str += "	<tr><td colspan=2>&nbsp;</td></tr>\n";
		}
		str += "</table>\n";
		str += "</div>\n";
		return str;
	}
	
	private String getCCControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getCCLineControlBlocks(), LineType.CC);
	}
	
	private String getControlBlocks(List<ControlBlock> controlBlocks, LineType lineType) {
		String block ="";
		if(controlBlocks != null){
		  for(ControlBlock controlBlock : controlBlocks) {
			  List<ControlStatement> controlStatements = controlBlock.getControlStatements();
			  block += "						<div class=\"margin\">\n"
						 +  "							<table width=\"100%\">\n"
						 +  "								<tr>\n"
						 +  "									<td>\n";
			  for(ControlStatement controlStatement : controlStatements) {
				  if(controlStatement instanceof CaseStatement) {
					  block += "										"+getCaseBlockLines(controlStatement, lineType);
				  }
				  else if(controlStatement instanceof ElseCaseStatement) {
					  block += "										"+getElseCaseBlockLines(controlStatement, lineType);
				  }
				  else if(controlStatement instanceof ElseStatement) {
					  block += "										"+getElseBlockLines(controlStatement, lineType);
				  }
				  else if(controlStatement instanceof EndCaseStatement) {
					  block += "										"+getEndCaseStatement();
				  }
			  }
		  
		  
		   block += "									</td>\n"
				 +  "								</tr>\n"
				 +  "							</table>\n"
				 +  " 						</div>\n";
		  }
		}
		return block;
	}

	

	private String getElseBlockLines(ControlStatement controlStatement, LineType lineType) {
		String elseBlock = "";

		
		if(lineType.equals(LineType.DE)) {
			elseBlock += getDEBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.CC)) {
			elseBlock += getCCBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.DR)) {
			elseBlock += getDRBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.KW)) {
			elseBlock += getKWBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.GO)) {
			elseBlock += getGOBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.FT)) {
			elseBlock += getFTBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.SIZE)) {
			elseBlock += getSizeBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.RELATED)) {
			elseBlock += getRelatedBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.TEMPLATE)) {
			elseBlock += getTemplateBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.SCOPEBLOCK)) {
			elseBlock += getScopeBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.FUSIONBLOCK)) {
			elseBlock += getFusionBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.DUPLICATE)) {
			elseBlock += getDuplicateBlockView(controlStatement.getApplicableLines());
		}
		
		else if (lineType.equals(LineType.PLASMID)) {
			elseBlock += getPlasmidBlockView(controlStatement.getApplicableLines());
		}
		
		else if (lineType.equals(LineType.COMMENTS)) {
			elseBlock += getCommentsBlockView(controlStatement.getApplicableLines());
		}
		if(controlStatement.getApplicableLines().size() > 0) {
			elseBlock = getElseStatement(controlStatement) + elseBlock;
		}
		return elseBlock;
	}

	private String getSizeBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		str +="<table width=\"100%\" style=\"font-size: 10pt\">\n";
		
		for(Line line : lines) {
			str += "		<tr><td>\n";
			SizeLine sizeLine = (SizeLine) line;
			
			str += sizeLine.getSizeLimit();
			str += "        </td>\n";
			str += "	</tr>\n";
		}
		str += "</table>\n";
		str += "</div>\n";
		return str;
	}
	
	private String getRelatedBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		str +="<table width=\"100%\" style=\"font-size: 10pt\">\n";
		
		for(Line line : lines) {
			str += "		<tr><td>\n";
			RelatedLine relatedLine = (RelatedLine) line;
			List<String> relatedPIRRuleACs = relatedLine.getRelatedPIRRuleAccessionNumbers();
			if(relatedPIRRuleACs != null && relatedPIRRuleACs.size() > 0) {
				for(String ac : relatedPIRRuleACs) {
					if(ac.equals("None")) {
						str += "None";
					}
					else {
						str += ac +"; ";
					}
				}
			}
			else {
				str += "None";
			}
			str += "        </td>\n";
			str += "	</tr>\n";
		}
		str += "</table>\n";
		str += "</div>\n";
		return str;
	}

	private String getFTBlockView(List<Line> lines) {
		String str = "";
		
		if(lines.size() == 1 && lines.get(0) instanceof FTNoneLine) {
			
		}
		else {
			str += "<div class=\"margin\">\n";
			str +="<table width=\"100%\" style=\"font-size: 10pt\">\n";
			for(int i = 0; i < lines.size(); i++) {
				if(i == 0) {
					FTFromLine ftFromLine = (FTFromLine) lines.get(i);
					str += "<tr colspan=6>\n"
							+ "<td><strong>Template</strong>: &nbsp;" + "<a href=\"http://www.uniprot.org/uniprot/"+ftFromLine.getFTTemplateAccessionNumber()
							+"\">" + ftFromLine.getFTTemplateAccessionNumber()+"</a></td>\n"
							+ "</tr>\n";
					str += "<tr>\n"
							+ "<td><strong>Key</strong></td>\n"
							+ "<td><strong>From</strong></td>\n"
							+ "<td><strong>To</strong></td>\n"
							+ "<td><strong>Description</strong></td>\n"
							+ "<td><strong>Condition Pattern</strong></td>\n"
							+ "<td><strong>FTGroup</td>\n"
							+ "</tr>\n";
				}
				else {
					FeatureDescriptionLine featureDescriptionLine = (FeatureDescriptionLine) lines.get(i);
					str += "<tr>\n";
					str += "<td>" + featureDescriptionLine.getFeatureKey()+"</td>\n";
					str += "<td>" + featureDescriptionLine.getFeatureFromPosition()+"</td>\n";
					str += "<td>" + featureDescriptionLine.getFeatureToPosition()+"</td>\n";
					List<String> featureDescriptions = featureDescriptionLine.getDescriptions();
					String descStr = "";
					for(String desc: featureDescriptions) {
						descStr += desc +" ";
					}
					str += "<td>" + descStr+"</td>\n";
					str += "<td>" + featureDescriptionLine.getFeatureConditionPattern()+"</td>\n";
					str += "<td>" + featureDescriptionLine.getFeatureGroupNumber()+"</td>\n";
					str += "</tr>\n";
				}
			}

			str += "</table>\n";
			str += "</div>\n";
		}
		return str;
	}

	private String getGOBlockView(List<Line> lines) {
		String str = "";
		str += "<div class=\"margin\">\n";
		str +="<table width=\"100%\" style=\"font-size: 10pt\">\n";
		
		for(Line line : lines) {
			str += "		<tr>\n";
			GOLine goLine = (GOLine) line;
			
			str += "		<td>" + "<a href=\"http://amigo.geneontology.org/cgi-bin/amigo/term-details.cgi?term="+goLine.getGeneOntologyAccessionNumber()+"\">"
					+ goLine.getGeneOntologyAccessionNumber()+"</a>&nbsp;";
			str += goLine.getGeneOntologyAspect()+":"+goLine.getGeneOntologyTerm();
			str += "        </td>\n";
			str += "	</tr>\n";
			//str += "	<tr><td colspan=2><br/></td></tr>\n";
		}
		str += "</table>\n";
		str += "</div>\n";
		return str;
	}

	private String getElseCaseBlockLines(ControlStatement controlStatement, LineType lineType) {
		String elseCaseBlock = "";

		
		if(lineType.equals(LineType.DE)) {
			elseCaseBlock += getDEBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.CC)) {
			elseCaseBlock += getCCBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.DR)) {
			elseCaseBlock += getDRBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.KW)) {
			elseCaseBlock += getKWBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.GO)) {
			elseCaseBlock += getGOBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.FT)) {
			elseCaseBlock += getFTBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.SIZE)) {
			elseCaseBlock += getSizeBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.RELATED)) {
			elseCaseBlock += getRelatedBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.TEMPLATE)) {
			elseCaseBlock += getTemplateBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.SCOPEBLOCK)) {
			elseCaseBlock += getScopeBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.FUSIONBLOCK)) {
			elseCaseBlock += getFusionBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.DUPLICATE)) {
			elseCaseBlock += getDuplicateBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.PLASMID)) {
			elseCaseBlock += getPlasmidBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.COMMENTS)) {
			elseCaseBlock += getCommentsBlockView(controlStatement.getApplicableLines());
		}
		if(controlStatement.getApplicableLines().size() > 0) {
			elseCaseBlock = getElseStatement(controlStatement) + elseCaseBlock;
		}
		return elseCaseBlock;
	}

	private String getCaseBlockLines(ControlStatement controlStatement, LineType lineType) {
		String caseBlock = "";

		caseBlock += getCaseStatement(controlStatement);
		if(lineType.equals(LineType.DE)) {
			caseBlock += getDEBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.CC)) {
			caseBlock += getCCBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.DR)) {
			caseBlock += getDRBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.KW)) {
			caseBlock += getKWBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.GO)) {
			caseBlock += getGOBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.FT)) {
			caseBlock += getFTBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.SIZE)) {
			caseBlock += getSizeBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.RELATED)) {
			caseBlock += getRelatedBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.TEMPLATE)) {
			caseBlock += getTemplateBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.SCOPEBLOCK)) {
			caseBlock += getScopeBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.FUSIONBLOCK)) {
			caseBlock += getFusionBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.DUPLICATE)) {
			caseBlock += getDuplicateBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.PLASMID)) {
			caseBlock += getPlasmidBlockView(controlStatement.getApplicableLines());
		}
		else if (lineType.equals(LineType.COMMENTS)) {
			caseBlock += getCommentsBlockView(controlStatement.getApplicableLines());
		}
		return caseBlock;
	}

	private String getDEControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getDELineControlBlocks(), LineType.DE);
	}

	

	
	private String getEndCaseStatement() {
		return "<div class=\"end_case\">" + UniRuleFlatFileConstants.END_CASE+"</div>\n";
	}
	
	private String getElseStatement(ControlStatement controlStatement) {
		String wellFormedLine = "" +controlStatement.toString().replace("\n", "");
		wellFormedLine = wellFormedLine.replace("<", "&lt;");
		wellFormedLine = wellFormedLine.replace(">", "&gt;");
		return "<div class=\"else\">" + wellFormedLine+"</div>\n";
	}
	
	private String getCaseStatement(ControlStatement controlStatement) {
		String wellFormedLine = "" +controlStatement.toString().replace("\n", "");
		wellFormedLine = wellFormedLine.replace("<", "&lt;");
		wellFormedLine = wellFormedLine.replace(">", "&gt;");
		return "<div class=\"case\">" + wellFormedLine+"</div>\n";
	}



	private String getComputingSection() {
		String computingSection = "";
		computingSection += "<table width=\"100%\" bgcolor=\"#ffffff\" cellpadding=\"8\" cellspacing=\"0\" width=\"100%\" class=boxTable>\n"
		          	      +  "	<tr>\n"
		                  +  "		<th class=\"right\" align=center style=\"font-size: 12pt;\">Computing Section</th>\n"
		                  +  "	</tr>\n";
		if(!getSizeControlBlocks().equals("") || !getNonControlBlockSizeLines().equals("") ) {
			computingSection +=  getSizeLinesInfo();
		}
		if(!getRelatedControlBlocks().equals("") || !getNonControlBlockRelatedLines().equals("") ) {
			computingSection +=  getRelatedLinesInfo();
		}
		
		if(!getTemplateControlBlocks().equals("") || !getNonControlBlockTemplateLines().equals("") ) {
			computingSection +=  getTemplateLinesInfo();
		}
		
		if(!getScopeBlockControlBlocks().equals("") || !getNonControlBlockScopeBlockLines().equals("") ) {
			computingSection +=  getScopeBlockLinesInfo();
		}
		
		if(!getFusionBlockControlBlocks().equals("") || !getNonControlBlockFusionBlockLines().equals("") ) {
			computingSection +=  getFusionBlockLinesInfo();
		}
		
		if(!getDuplicateControlBlocks().equals("") || !getNonControlBlockDuplicateLines().equals("") ) {
			computingSection +=  getDuplicateLinesInfo();
		}
		
		if(!getPlasmidControlBlocks().equals("") || !getNonControlBlockPlasmidLines().equals("") ) {
			computingSection +=  getPlasmidLinesInfo();
		}
		
		if(!getCommentsControlBlocks().equals("") || !getNonControlBlockCommentsLines().equals("") ) {
			computingSection +=  getCommentsLinesInfo();
		}
		computingSection +=  "</table>\n";
		
		return computingSection;
	}

	private String getNonControlBlockSizeLines() {
		String str = "";
		List<Line> nonControlBlockSizeLines = this.pirruleUtil.getNonControlBlockSizeLines();
		if( nonControlBlockSizeLines != null && nonControlBlockSizeLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getSizeBlockView(nonControlBlockSizeLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}
	
	private String getNonControlBlockScopeBlockLines() {
		String str = "";
		List<Line> nonControlBlockScopeBlockLines = this.pirruleUtil.getNonControlBlockScopeBlockLines();
		if( nonControlBlockScopeBlockLines != null && nonControlBlockScopeBlockLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getScopeBlockView(nonControlBlockScopeBlockLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}
	
	private String getNonControlBlockFusionBlockLines() {
		String str = "";
		List<Line> nonControlBlockFusionBlockLines = this.pirruleUtil.getNonControlBlockFusionBlockLines();
		if( nonControlBlockFusionBlockLines != null && nonControlBlockFusionBlockLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getFusionBlockView(nonControlBlockFusionBlockLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}
	
	private String getNonControlBlockTemplateLines() {
		String str = "";
		List<Line> nonControlBlockTemplateLines = this.pirruleUtil.getNonControlBlockTemplateLines();
		if( nonControlBlockTemplateLines != null && nonControlBlockTemplateLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getTemplateBlockView(nonControlBlockTemplateLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}
	
	private String getNonControlBlockDuplicateLines() {
		String str = "";
		List<Line> nonControlBlockDuplicateLines = this.pirruleUtil.getNonControlBlockDuplicateLines();
		if( nonControlBlockDuplicateLines != null && nonControlBlockDuplicateLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getDuplicateBlockView(nonControlBlockDuplicateLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}
	
	private String getNonControlBlockPlasmidLines() {
		String str = "";
		List<Line> nonControlBlockPlasmidLines = this.pirruleUtil.getNonControlBlockPlasmidLines();
		if( nonControlBlockPlasmidLines != null && nonControlBlockPlasmidLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getPlasmidBlockView(nonControlBlockPlasmidLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}
	
	private String getNonControlBlockCommentsLines() {
		String str = "";
		List<Line> nonControlBlockCommentsLines = this.pirruleUtil.getNonControlBlockCommentsLines();
		if( nonControlBlockCommentsLines != null && nonControlBlockCommentsLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getCommentsBlockView(nonControlBlockCommentsLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}
	
	
	private String getNonControlBlockRelatedLines() {
		String str = "";
		List<Line> nonControlBlockRelatedLines = this.pirruleUtil.getNonControlBlockRelatedLines();
		if( nonControlBlockRelatedLines != null && nonControlBlockRelatedLines.size() > 0) {
			str += "						<div class=\"margin\">\n"
					 +  "							<table width=\"100%\">\n"
					 +  "								<tr>\n"
					 +  "									<td>\n"
				     +  getRelatedBlockView(nonControlBlockRelatedLines)
				     +  "									</td>\n"
					 +  "								</tr>\n"
					 +  "							</table>\n"
					 +  " 						</div>\n";
		}
		return str;
	}
	
	

	private String getSizeLinesInfo() {
		String info = "";
		if(this.pirruleUtil.getSizeLineControlBlocks() != null || this.pirruleUtil.getNonControlBlockSizeLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Size</div>\n"
				 +  getSizeControlBlocks()
				 +  getNonControlBlockSizeLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}
	
	private String getRelatedLinesInfo() {
		String info = "";
		if(this.pirruleUtil.getRelatedLineControlBlocks() != null || this.pirruleUtil.getNonControlBlockRelatedLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Related Rules</div>\n"
				 +  getRelatedControlBlocks()
				 +  getNonControlBlockRelatedLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}
	
	private String getTemplateLinesInfo() {
		String info = "";
		if(this.pirruleUtil.getTemplateLineControlBlocks() != null || this.pirruleUtil.getNonControlBlockTemplateLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Templates</div>\n"
				 +  getTemplateControlBlocks()
				 +  getNonControlBlockTemplateLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}
	
	private String getDuplicateLinesInfo() {
		String info = "";
		if(this.pirruleUtil.getDuplicateLineControlBlocks() != null || this.pirruleUtil.getNonControlBlockDuplicateLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Duplicates</div>\n"
				 +  getDuplicateControlBlocks()
				 +  getNonControlBlockDuplicateLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}
	
	
	private String getPlasmidLinesInfo() {
		String info = "";
		if(this.pirruleUtil.getPlasmidLineControlBlocks() != null || this.pirruleUtil.getNonControlBlockPlasmidLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Plasmid</div>\n"
				 +  getPlasmidControlBlocks()
				 +  getNonControlBlockPlasmidLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}
	
	private String getCommentsLinesInfo() {
		String info = "";
		if(this.pirruleUtil.getCommentsLineControlBlocks() != null || this.pirruleUtil.getNonControlBlockCommentsLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Comments</div>\n"
				 +  getCommentsControlBlocks()
				 +  getNonControlBlockCommentsLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}
	
	
	
	private String getScopeBlockLinesInfo() {
		String info = "";
		if(this.pirruleUtil.getScopeBlockControlBlocks() != null || this.pirruleUtil.getNonControlBlockScopeBlockLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Taxonomic Scope</div>\n"
				 +  getScopeBlockControlBlocks()
				 +  getNonControlBlockScopeBlockLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}
	
	private String getFusionBlockLinesInfo() {
		String info = "";
		if(this.pirruleUtil.getFusionBlockControlBlocks() != null || this.pirruleUtil.getNonControlBlockFusionBlockLines()!=null) {
			info += "	<tr>\n"
				 +  "		<td>\n"
				 +  "			<table style=\"margin-left: 20px; margin-right: 20px;\" width=\"95%\" border=0>\n"
				 +  "				<tr>\n"
				 +  "					<td><div class=\"section\">Fusion</div>\n"
				 +  getFusionBlockControlBlocks()
				 +  getNonControlBlockFusionBlockLines()
				 +  "					</td>\n"
				 +  "				<tr>\n"
				 +  "			</table>\n"
				 +  "		</td>\n"
				 +  "	</tr>\n";	
			
		}
		return info;
	}

	private String getSizeControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getSizeLineControlBlocks(), LineType.SIZE);
	}

	private String getRelatedControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getRelatedLineControlBlocks(), LineType.RELATED);
	}
	
	private String getTemplateControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getTemplateLineControlBlocks(), LineType.TEMPLATE);
	}
	
	private String getDuplicateControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getDuplicateLineControlBlocks(), LineType.DUPLICATE);
	}
	
	private String getPlasmidControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getPlasmidLineControlBlocks(), LineType.PLASMID);
	}
	
	private String getCommentsControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getCommentsLineControlBlocks(), LineType.COMMENTS);
	}
	private String getScopeBlockControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getScopeBlockControlBlocks(), LineType.SCOPEBLOCK);
	}
	
	private String getFusionBlockControlBlocks() {
		return getControlBlocks(this.pirruleUtil.getFusionBlockControlBlocks(), LineType.FUSIONBLOCK);
	}
	
	public String getHTML() {
		String html = "";
		html += this.getHeaderSection() + "<br/>\n";
		html += this.getAnnotationSection() + "<br/>\n";
		html += this.getComputingSection() + "<br/>\n";
		//System.out.println(html);
		return html;
	}
}
