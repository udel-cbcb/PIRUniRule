package org.proteininformationresource.pirsr.model;

import java.util.List;

import org.proteininformationresource.pirsr.model.expression.Expression;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * Control statements, can be <code>CaseStatement</code>,
 * <code>ElseCaseStatement</code>, <code>ElseStatement</code> and
 * <code>EndCaseStatement</code>.
 */
public interface ControlStatement extends Line {
	/**
	 * Gets the condition <code>Expression</code>.
	 * 
	 * @return the condition <code>Expression</code>.
	 */
	Expression getExpression();

	/**
	 * Sets the condition <code>Expression</code>.
	 * 
	 * @param expression
	 *            the condition <code>Expression</code>.
	 */
	void setExpression(Expression expression);

	/**
	 * Gets a list of applicable <code>Line</code>s.
	 * 
	 * @return the list of applicable <code>Line</code>s.
	 */
	List<Line> getApplicableLines();

	/**
	 * Add an applicable <code>Line</code> to the <code>ControlStatement</code>.
	 * 
	 * @param line
	 *            an applicable <code>Line</code>.
	 */
	void addLine(Line line);

	/**
	 * Gets the last applicable <code>Line</code>.
	 * 
	 * @return the last applicable <code>Line</code>.
	 */
	Line getLastApplicableLine();

	/**
	 * Sets the last applicable <code>Line</code>.
	 * 
	 * @param lastApplicableLine
	 *            the last applicable <code>Line</code>.
	 */
	void setLastApplicableLine(Line lastApplicableLine);
	
	
}
