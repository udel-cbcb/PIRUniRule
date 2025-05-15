package edu.udel.bioinformatics.pirrule.expression;

import org.proteininformationresource.pirrule.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirrule.model.expression.AndOperatorExpression;
import org.proteininformationresource.pirrule.model.expression.Expression;
import org.proteininformationresource.pirrule.model.expression.ExpressionValue;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * 
 */
public class AndOperatorExpressionImpl implements AndOperatorExpression {

	private String leftParenthesis;
	private Expression leftExpression;
	private Expression rightExpression;
	private String rightParenthesis;
	private ExpressionValue expressionValue;

	public AndOperatorExpressionImpl(String leftParenthesis, Expression leftExpression, Expression rightExpression, String rightParenthesis) {
		super();
		this.leftParenthesis = leftParenthesis;
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
		this.rightParenthesis = rightParenthesis;
	}

	public AndOperatorExpressionImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getLeftParenthesis() {
		return this.leftParenthesis;
	}

	@Override
	public Expression getLeftExpression() {
		return this.leftExpression;
	}

	@Override
	public Expression getRightExpression() {
		return this.rightExpression;
	}

	@Override
	public String getRightParenthesis() {
		return this.rightParenthesis;
	}

	//@Override
//	public ExpressionValue getExpressionValue() {
//		ExpressionValue eVal = ExpressionValue.TRUE;
//		if (this.leftExpression.getExpressionValue().equals(ExpressionValue.TRUE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.TRUE)) {
//			eVal = ExpressionValue.TRUE;
//		} else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.TRUE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.FALSE)) {
//			eVal = ExpressionValue.FALSE;
//		} else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.TRUE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.UNDEF)) {
//			eVal = ExpressionValue.UNDEF;
//		} else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.FALSE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.TRUE)) {
//			eVal = ExpressionValue.FALSE;
//		} else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.FALSE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.FALSE)) {
//			eVal = ExpressionValue.FALSE;
//		} else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.FALSE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.UNDEF)) {
//			eVal = ExpressionValue.FALSE;
//		} else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.UNDEF) && this.rightExpression.getExpressionValue().equals(ExpressionValue.TRUE)) {
//			eVal = ExpressionValue.UNDEF;
//		} else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.UNDEF) && this.rightExpression.getExpressionValue().equals(ExpressionValue.FALSE)) {
//			eVal = ExpressionValue.UNDEF;
//		} else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.UNDEF) && this.rightExpression.getExpressionValue().equals(ExpressionValue.UNDEF)) {
//			eVal = ExpressionValue.UNDEF;
//		}
//		this.expressionValue = eVal;
//		return eVal;
//	}

	@Override
	public void setLeftParenthesis(String leftParenthesis) {
		this.leftParenthesis = leftParenthesis;
	}

	@Override
	public void setLeftExpression(Expression leftExpression) {
		this.leftExpression = leftExpression;
	}

	@Override
	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}

	@Override
	public void setRightParenthesis(String rightParenthesis) {
		this.rightParenthesis = rightParenthesis;
	}

	public String toString() {
		String str = "";
		if (this.leftParenthesis != null) {
			str += UniRuleFlatFileConstants.LEFT_PARENTHESIS;
		}
		
		if(this.leftExpression != null) {
			str += this.leftExpression.toString();
		}
		str += " " + UniRuleFlatFileConstants.OPERATOR_AND + " ";
		if (this.rightExpression != null) {
			str += this.rightExpression.toString();
		}
		if (this.rightParenthesis != null) {
			str += UniRuleFlatFileConstants.RIGHT_PARENTHESIS;
		}
		return str;
	}

//	@Override
//	public void setExpressionValue(ExpressionValue expressionValue) {
//		this.expressionValue = expressionValue;
//	}
}
