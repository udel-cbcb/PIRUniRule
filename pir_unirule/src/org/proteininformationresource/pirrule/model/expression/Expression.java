package org.proteininformationresource.pirrule.model.expression;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * case <condition>[ and|or [not] [defined] <condition>]... The elements of
 * logic expressions are: conditions, which may have the values true or false
 * unary and binary logic operators parentheses which may affect the order in
 * which the operations are carried out
 * 
 * <expression> = <condition> | ( <expression> <operator> <expression> ) | (
 * <operator> <expression>)
 * 
 * * Binary operators: 'and' and 'or' i j i and j i or j true true true true
 * true false false true true undef undef true false true false true false false
 * false false false undef false undef undef true undef true undef false undef
 * false undef undef undef undef
 * 
 * Unary operators: 'not' and 'defined' i not i defined i true false true false
 * true true undef undef false
 * 
 * Operator associativity and precedence
 *
 * The precedence order from highest to lowest and associativity are as follows.
 *
 * associativity operator right defined right not left and left or
 *
 */
public interface Expression {

	/**
	 * Gets the value of expression.
	 * 
	 * @return the value of expression.
	 */
	//ExpressionValue getExpressionValue();
	
	/**
	 * Sets the value of expression
	 * @param expressionValue the value of expression
	 */
	//void setExpressionValue(ExpressionValue expressionValue);

	/**
	 * Gets the left parenthesis, optional if there is no right parenthesis.
	 * 
	 * @return the left parenthesis.
	 */
	String getLeftParenthesis();

	/**
	 * Sets the left parenthesis.
	 * 
	 * @param leftParenthesis
	 *            the left parenthesis.
	 */
	void setLeftParenthesis(String leftParenthesis);

	/**
	 * Gets the right parenthesis, required if there is a left parenthesis.
	 * 
	 * @return the right parenthesis.
	 */
	String getRightParenthesis();

	/**
	 * Sets the right parenthesis.
	 * 
	 * @param rightParenthese
	 *            the right parenthesis.
	 */
	void setRightParenthesis(String rightParenthesis);
}
