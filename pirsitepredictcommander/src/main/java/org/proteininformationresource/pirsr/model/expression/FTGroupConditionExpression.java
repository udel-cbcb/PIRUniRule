package org.proteininformationresource.pirsr.model.expression;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * Condition on the fact that a feature group was propagated: (<FTGroup:n>)
 */
public interface FTGroupConditionExpression extends ConditionExpression {

	/**
	 * Gets the FTGroup number.
	 * 
	 * @return the FTGroup number.
	 */
	int getFTGroupNumber();

	/**
	 * Sets the FTGroup number.
	 * 
	 * @param ftGroupNumber
	 *            the FTGroup number.
	 */
	void setFTGroupNumnber(int ftGroupNumber);
}
