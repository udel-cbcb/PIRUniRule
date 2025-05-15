package edu.udel.bioinformatics.pirrule.expression;

import org.proteininformationresource.pirrule.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirrule.model.expression.Expression;
import org.proteininformationresource.pirrule.model.expression.ExpressionValue;
import org.proteininformationresource.pirrule.model.expression.NotOperatorExpression;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br><br>
 * 
 * 
 */
public class NotOperatorExpressionImpl implements NotOperatorExpression {
	private String leftParenthesis;
	private String rightParenthesis;
	private Expression expression;
	private ExpressionValue expressionValue;
	
	public NotOperatorExpressionImpl(String leftParenthese, Expression expression, String rightParenthese) {
		super();
		this.leftParenthesis = leftParenthese;
		this.rightParenthesis = rightParenthese;
		this.expression = expression;
	}

	public NotOperatorExpressionImpl() {
		super();
		
	}

	@Override
	public String getLeftParenthesis() {
		return this.leftParenthesis;
	}

	@Override
	public Expression getExpression() {
		return this.expression;
	}

	@Override
	public String getRightParenthesis() {
		return this.rightParenthesis;
	}

//	@Override
//	public ExpressionValue getExpressionValue() {
//		ExpressionValue eVal = ExpressionValue.TRUE; 
//		if(this.expression.getExpressionValue().equals(ExpressionValue.TRUE)) {
//			eVal =  ExpressionValue.FALSE;
//		}
//		else if(this.expression.getExpressionValue().equals(ExpressionValue.FALSE)) {
//			eVal =  ExpressionValue.TRUE;
//		}
//		else if(this.expression.getExpressionValue().equals(ExpressionValue.UNDEF)) {
//			eVal =  ExpressionValue.UNDEF;
//		}
//		this.expressionValue  = eVal;
//		return eVal;
//	}

	@Override
	public void setLeftParenthesis(String leftParenthesis) {
		this.leftParenthesis = leftParenthesis;
	}

	@Override
	public void setExpression(Expression expression) {
		this.expression = expression;
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
		
		str += this.expression.toString();
		if(this.rightParenthesis != null) {
			str += UniRuleFlatFileConstants.RIGHT_PARENTHESIS;
		}
		return "not "+str;
	}

//	@Override
//	public void setExpressionValue(ExpressionValue expressionValue) {
//		this.expressionValue = expressionValue;
//	}
}
