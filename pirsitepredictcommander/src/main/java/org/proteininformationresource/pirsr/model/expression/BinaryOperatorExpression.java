package org.proteininformationresource.pirsr.model.expression;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * ( <expression> and/or <expression> )
 * 
 * Binary operators: 'and' and 'or' i j i and j i or j true true true true true
 * false false true true undef undef true false true false true false false
 * false false false undef false undef undef true undef true undef false undef
 * false undef undef undef undef
 */

public interface BinaryOperatorExpression extends Expression {

	/**
	 * Gets the left expression.
	 * 
	 * @return the left expression.
	 */
	Expression getLeftExpression();

	/**
	 * Sets the left expression.
	 * 
	 * @param leftExpression
	 *            the left expression.
	 */
	void setLeftExpression(Expression leftExpression);

	/**
	 * Gets the right expression.
	 * 
	 * @return the right expression.
	 */
	Expression getRightExpression();

	/**
	 * Sets the right expression.
	 * 
	 * @param rightExpression
	 *            the right expression.
	 */
	void setRightExpression(Expression rightExpression);

}
