package edu.udel.bioinformatics.pirsr.expression;

import org.proteininformationresource.pirsr.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirsr.model.expression.AndOperatorExpression;
import org.proteininformationresource.pirsr.model.expression.Expression;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;

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

	
	public String getLeftParenthesis() {
		return this.leftParenthesis;
	}

	
	public Expression getLeftExpression() {
		return this.leftExpression;
	}

	
	public Expression getRightExpression() {
		return this.rightExpression;
	}

	
	public String getRightParenthesis() {
		return this.rightParenthesis;
	}

	//
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

	
	public void setLeftParenthesis(String leftParenthesis) {
		this.leftParenthesis = leftParenthesis;
	}

	
	public void setLeftExpression(Expression leftExpression) {
		this.leftExpression = leftExpression;
	}

	
	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}

	
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

//	
//	public void setExpressionValue(ExpressionValue expressionValue) {
//		this.expressionValue = expressionValue;
//	}
}
