package org.proteininformationresource.pirsr.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * The condition gives constraints on the FT features.
 */

public interface FeatureConstraintLine extends FTLine {

	/**
	 * Gets the feature group number.For the consistency of annotation, multiple
	 * features that should be applied either all together or not at all should
	 * be grouped within a "Group", to constrain the common presence of all
	 * sites. This group can the be referenced by case statements, for instance
	 * in the relevant KW and CC lines that depend on the presence of the
	 * feature.
	 * 
	 * @return the feature group number.
	 */
	int getFeatureGroupNumber();

	/**
	 * Sets the feature group number.For the consistency of annotation, multiple
	 * features that should be applied either all together or not at all should
	 * be grouped within a "Group", to constrain the common presence of all
	 * sites. This group can the be referenced by case statements, for instance
	 * in the relevant KW and CC lines that depend on the presence of the
	 * feature.
	 * 
	 * @param featureGroupNumber
	 *            the feature group number.
	 */
	void setFeatureGroupNumber(int featureGroupNumber);

	/**
	 * Gets the feature condition pattern. The "pattern" is specified in the
	 * PROSITE pattern format with the addition that the character "*" may be
	 * used to specify an unconstrained range, e.g. "C-x*-C". The region of the
	 * sequence corresponding to the feature must exactly match this pattern.
	 * 
	 * @return the feature condition pattern.
	 */
	String getConditionPattern();

	/**
	 * Sets the feature condition pattern. The "pattern" is specified in the
	 * PROSITE pattern format with the addition that the character "*" may be
	 * used to specify an unconstrained range, e.g. "C-x*-C". The region of the
	 * sequence corresponding to the feature must exactly match this pattern.
	 * 
	 * @param conditionPattern
	 *            the feature condition pattern.
	 */
	void setConditionPattern(String conditionPattern);

}
