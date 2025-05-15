package edu.udel.bioinformatics.pirrule.expression;

import org.proteininformationresource.pirrule.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirrule.model.expression.ExpressionValue;
import org.proteininformationresource.pirrule.model.expression.FTGroupConditionExpression;

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

//	@Override
//	public ExpressionValue getExpressionValue() {
//		return this.expressionValue;
//	}

	public ExpressionValue getExpressionValue(int ftGroupNumber) {
		if (this.ftGroupNumber != ftGroupNumber) {
			this.expressionValue = ExpressionValue.FALSE;
		}
		return this.expressionValue;
	}

	@Override
	public int getFTGroupNumber() {
		return this.ftGroupNumber;
	}

	@Override
	public void setFTGroupNumnber(int ftGroupNumber) {
		this.ftGroupNumber = ftGroupNumber;
	}

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
