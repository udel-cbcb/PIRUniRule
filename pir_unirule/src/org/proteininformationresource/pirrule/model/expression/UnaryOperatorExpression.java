package org.proteininformationresource.pirrule.model.expression;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * ( [not] [defined] <expression> )
 * 
 * Unary operators: 'not' and 'defined' i not i defined i true false true false
 * true true undef undef false
 */
public interface UnaryOperatorExpression extends Expression {

	/**
	 * Gets the expression.
	 * 
	 * @return the expression.
	 */
	Expression getExpression();

	/**
	 * Sets the expression.
	 * 
	 * @param expression
	 *            .
	 */
	void setExpression(Expression expression);

}
