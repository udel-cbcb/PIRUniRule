package edu.udel.bioinformatics.pirrule.expression;

import org.proteininformationresource.pirrule.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirrule.model.expression.Expression;
import org.proteininformationresource.pirrule.model.expression.ExpressionValue;
import org.proteininformationresource.pirrule.model.expression.OrOperatorExpression;

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

	@Override
	public String getLeftParenthesis() {
		return this.leftParenthesis;
	}

	@Override
	public void setLeftParenthesis(String leftParenthesis) {
		this.leftParenthesis = leftParenthesis;
	}

	@Override
	public Expression getLeftExpression() {
		return this.leftExpression;
	}

	@Override
	public void setLeftExpression(Expression leftExpression) {
		this.leftExpression = leftExpression;
	}

	@Override
	public Expression getRightExpression() {
		return this.rightExpression;
	}

	@Override
	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}

	@Override
	public String getRightParenthesis() {
		return this.rightParenthesis;
	}

	@Override
	public void setRightParenthesis(String rightParenthesis) {
		this.rightParenthesis = rightParenthesis;
	}

//	@Override
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

//	@Override
//	public void setExpressionValue(ExpressionValue expressionValue) {
//		this.expressionValue = expressionValue;
//	}
}
