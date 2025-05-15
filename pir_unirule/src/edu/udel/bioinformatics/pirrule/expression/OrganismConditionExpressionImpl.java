package edu.udel.bioinformatics.pirrule.expression;

import org.proteininformationresource.pirrule.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirrule.model.expression.ExpressionValue;
import org.proteininformationresource.pirrule.model.expression.OrganismConditionExpression;
import org.proteininformationresource.pirrule.model.expression.OrganismConditionType;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 24, 2014<br>
 * <br>
 * 
 * OS/OC/OG: on taxonomy and organelle (OS, OC and OG lines):
 *
 * case <OG:Chloroplast> or <OC:Cyanobacteria>
 *
 * case not <OG:Chloroplast> and not <OG:Cyanelle>
 *
 * case <OC:Archaea>
 *
 * case <OC:Bacteria>
 * 
 * case <OS:Staphylococcus aureus>
 *
 * Note for conditions on organism names ('case <OS:taxon>'): the organism name
 * matches also subspecies, i.e. organisms with the same name followed by a
 * space and then any text. For example, 'Staphylococcus aureus' matches
 * 'Staphylococcus aureus RF122', but 'Salmonella typhi' does not match
 * 'Salmonella typhimurium'.
 *
 */
public class OrganismConditionExpressionImpl implements OrganismConditionExpression {

	private ExpressionValue expressionValue;
	private String leftParenthesis;
	private String rightParenthesis;
	private OrganismConditionType organismConditionType;
	private String organismName;

	public OrganismConditionExpressionImpl() {
		super();
	}
//	@Override
//	public ExpressionValue getExpressionValue() {
//		return this.expressionValue;
//	}

	@Override
	public String getLeftParenthesis() {
		return this.leftParenthesis;
	}

	@Override
	public void setLeftParenthesis(String leftParenthesis) {
		this.leftParenthesis = leftParenthesis;
	}

	@Override
	public String getRightParenthesis() {
		return this.rightParenthesis;
	}

	@Override
	public void setRightParenthesis(String rightParenthesis) {
		this.rightParenthesis = rightParenthesis;
	}

	

	@Override
	public String getOrganismName() {
		return this.organismName;
	}

	@Override
	public void setOrganismName(String organismName) {
		this.organismName = organismName;
	}

	@Override
	public OrganismConditionType getOrganimsConditionType() {
		return this.organismConditionType;
	}

	@Override
	public void setOrganismConditionType(OrganismConditionType organismConditionType) {
		this.organismConditionType = organismConditionType;
	}

	public String toString() {
		String str = "";
		if(this.leftParenthesis != null) {
			str += UniRuleFlatFileConstants.LEFT_PARENTHESIS;
		}
		
		str += UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET+ this.organismConditionType+":"+this.organismName+ UniRuleFlatFileConstants.RIGHT_ANGLE_BRAKET;
		if(this.rightParenthesis != null) {
			str += UniRuleFlatFileConstants.RIGHT_PARENTHESIS;
		}
		return str;
	}
	
//	@Override
//	public void setExpressionValue(ExpressionValue expressionValue) {
//		this.expressionValue = expressionValue;
//	}
}
