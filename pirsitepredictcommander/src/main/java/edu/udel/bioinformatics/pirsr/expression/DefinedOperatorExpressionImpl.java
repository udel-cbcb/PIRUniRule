package edu.udel.bioinformatics.pirsr.expression;

import org.proteininformationresource.pirsr.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirsr.model.expression.DefinedOperatorExpression;
import org.proteininformationresource.pirsr.model.expression.Expression;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br><br>
 * 
 * 
 */
public class DefinedOperatorExpressionImpl implements DefinedOperatorExpression {

	private String leftParenthesis;
	private String rightParenthesis;
	private Expression expression;
	private ExpressionValue expressionValue;
	
	public DefinedOperatorExpressionImpl() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public DefinedOperatorExpressionImpl(String leftParenthesis, Expression expression, String rightParenthesis) {
		super();
		this.leftParenthesis = leftParenthesis;
		this.rightParenthesis = rightParenthesis;
		this.expression = expression;
	}



	
	public String getLeftParenthesis() {
		return this.leftParenthesis;
	}

	
	public Expression getExpression() {
		return this.expression;
	}

	
	public String getRightParenthesis() {
		return this.rightParenthesis;
	}



//	
//	public ExpressionValue getExpressionValue() {
//		ExpressionValue eVal = ExpressionValue.TRUE; 
//		if(this.expression.getExpressionValue().equals(ExpressionValue.TRUE)) {
//			eVal =  ExpressionValue.TRUE;
//		}
//		else if(this.expression.getExpressionValue().equals(ExpressionValue.FALSE)) {
//			eVal =  ExpressionValue.TRUE;
//		}
//		else if(this.expression.getExpressionValue().equals(ExpressionValue.UNDEF)) {
//			eVal =  ExpressionValue.FALSE;
//		}
//		return eVal;
//	}



	
	public void setLeftParenthesis(String leftParenthesis) {
		this.leftParenthesis = leftParenthesis;
	}



	
	public void setExpression(Expression expression) {
		this.expression = expression;
	}



	
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
		return "defined " +str;
	}



//	
//	public void setExpressionValue(ExpressionValue expressionValue) {
//		this.expressionValue = expressionValue;
//	}
}
