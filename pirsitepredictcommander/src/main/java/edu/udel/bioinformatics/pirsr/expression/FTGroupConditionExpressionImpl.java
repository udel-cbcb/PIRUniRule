package edu.udel.bioinformatics.pirsr.expression;

import org.proteininformationresource.pirsr.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;
import org.proteininformationresource.pirsr.model.expression.FTGroupConditionExpression;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * Condition on the fact that a feature group was propagated: (<FTGroup:n>)
 */
public class FTGroupConditionExpressionImpl implements FTGroupConditionExpression {
	private ExpressionValue expressionValue;
	private int ftGroupNumber;
	private String leftParenthesis;
	private String rightParenthesis;

	public FTGroupConditionExpressionImpl(int ftGroupNumber, ExpressionValue expressionValue) {
		super();
		this.expressionValue = expressionValue;
		this.ftGroupNumber = ftGroupNumber;
	}

	public FTGroupConditionExpressionImpl() {
		super();
		this.expressionValue = ExpressionValue.FALSE;
		this.ftGroupNumber = -1;
	}

//	
//	public ExpressionValue getExpressionValue() {
//		return this.expressionValue;
//	}

	public ExpressionValue getExpressionValue(int ftGroupNumber) {
		if (this.ftGroupNumber != ftGroupNumber) {
			this.expressionValue = ExpressionValue.FALSE;
		}
		return this.expressionValue;
	}

	
	public int getFTGroupNumber() {
		return this.ftGroupNumber;
	}

	
	public void setFTGroupNumnber(int ftGroupNumber) {
		this.ftGroupNumber = ftGroupNumber;
	}

	
	public String getLeftParenthesis() {
		return this.leftParenthesis;
	}

	
	public void setLeftParenthesis(String leftParenthesis) {
		this.leftParenthesis = leftParenthesis;
	}

	
	public String getRightParenthesis() {
		return this.rightParenthesis;
	}

	
	public void setRightParenthesis(String rightParenthesis) {
		this.rightParenthesis = rightParenthesis;
	}

	public String toString() {
		String str = "";
		if(this.leftParenthesis != null) {
			str += UniRuleFlatFileConstants.LEFT_PARENTHESIS;
		}
		
		str += UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET+UniRuleFlatFileConstants.FTGROUP_CONDITION+this.ftGroupNumber+ UniRuleFlatFileConstants.RIGHT_ANGLE_BRAKET;
		if(this.rightParenthesis != null) {
			str += UniRuleFlatFileConstants.RIGHT_PARENTHESIS;
		}
		return str;
	}

	public void setExpressionValue(ExpressionValue expressionValue) {
		this.expressionValue = expressionValue;
	}
}
