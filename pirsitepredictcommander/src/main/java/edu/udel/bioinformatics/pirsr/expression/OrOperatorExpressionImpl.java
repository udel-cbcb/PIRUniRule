package edu.udel.bioinformatics.pirsr.expression;

import org.proteininformationresource.pirsr.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirsr.model.expression.Expression;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;
import org.proteininformationresource.pirsr.model.expression.OrOperatorExpression;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br><br>
 * 
 * 
 */
public class OrOperatorExpressionImpl implements OrOperatorExpression {

	private String leftParenthesis;
	private String rightParenthesis;
	private Expression leftExpression;
	private Expression rightExpression;
	private ExpressionValue expressionValue;
	
	public OrOperatorExpressionImpl(String leftParenthesis, Expression leftExpression, Expression rightExpression, String rightParenthesis) {
		super();
		this.leftParenthesis = leftParenthesis;
		this.rightParenthesis = rightParenthesis;
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
	}

	public OrOperatorExpressionImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String getLeftParenthesis() {
		return this.leftParenthesis;
	}

	
	public void setLeftParenthesis(String leftParenthesis) {
		this.leftParenthesis = leftParenthesis;
	}

	
	public Expression getLeftExpression() {
		return this.leftExpression;
	}

	
	public void setLeftExpression(Expression leftExpression) {
		this.leftExpression = leftExpression;
	}

	
	public Expression getRightExpression() {
		return this.rightExpression;
	}

	
	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}

	
	public String getRightParenthesis() {
		return this.rightParenthesis;
	}

	
	public void setRightParenthesis(String rightParenthesis) {
		this.rightParenthesis = rightParenthesis;
	}

//	
//	public ExpressionValue getExpressionValue() {
//		ExpressionValue eVal = ExpressionValue.TRUE;
//		if(this.leftExpression.getExpressionValue().equals(ExpressionValue.TRUE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.TRUE)) {
//			eVal = ExpressionValue.TRUE;
//		}
//		else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.TRUE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.FALSE)) {
//			eVal = ExpressionValue.TRUE;
//		}
//		else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.TRUE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.UNDEF)) {
//			eVal = ExpressionValue.TRUE;
//		}
//		else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.FALSE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.TRUE)) {
//			eVal = ExpressionValue.TRUE;
//		}
//		else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.FALSE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.FALSE)) {
//			eVal = ExpressionValue.FALSE;
//		}
//		else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.FALSE) && this.rightExpression.getExpressionValue().equals(ExpressionValue.UNDEF)) {
//			eVal = ExpressionValue.UNDEF;
//		}
//		else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.UNDEF) && this.rightExpression.getExpressionValue().equals(ExpressionValue.TRUE)) {
//			eVal = ExpressionValue.TRUE;
//		}
//		else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.UNDEF) && this.rightExpression.getExpressionValue().equals(ExpressionValue.FALSE)) {
//			eVal = ExpressionValue.FALSE;
//		}
//		else if (this.leftExpression.getExpressionValue().equals(ExpressionValue.UNDEF) && this.rightExpression.getExpressionValue().equals(ExpressionValue.UNDEF)) {
//			eVal = ExpressionValue.UNDEF;
//		}
//		this.expressionValue = eVal;
//		return eVal;
//	}

	public String toString() {
		String str = "";
		if(this.leftParenthesis != null) {
			str += UniRuleFlatFileConstants.LEFT_PARENTHESIS;
		}
		
		str += this.leftExpression.toString();
		str += " " + UniRuleFlatFileConstants.OPERATOR_OR + " ";
		str += this.rightExpression.toString();
		if(this.rightParenthesis != null) {
			str += UniRuleFlatFileConstants.RIGHT_PARENTHESIS;
		}
		return str;
	}

//	
//	public void setExpressionValue(ExpressionValue expressionValue) {
//		this.expressionValue = expressionValue;
//	}
}
