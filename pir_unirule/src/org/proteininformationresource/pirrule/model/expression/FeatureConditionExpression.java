package org.proteininformationresource.pirrule.model.expression;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * on the presence of a feature in the GFF file. 'Feature' only refers to the
 * (rule) triggering feature + features that overlap (by at least 50%) with it.
 */
public interface FeatureConditionExpression extends ConditionExpression {

	/**
	 * Gets the feature condition description.
	 * 
	 * @return the feature condition description.
	 */
	String getFeatureConditionDescription();

	/**
	 * Sets the feature condition description.
	 * 
	 * @param featureConditionDescription
	 *            the feature condition description.
	 */
	void setFeatureConditionDescription(String featureConditionDescription);

}
