package org.proteininformationresource.pirrule.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * The description of actual features that are to be propagated in member
 * entries.
 */

public interface FeatureDescriptionLine extends FTLine {

	/**
	 * Gets a UniProtKB/Swiss-Prot feature key.
	 * 
	 * @return the feature key.
	 */
	FeatureType getFeatureKey();

	/**
	 * Sets a UniProtKB/Swiss-Prot feature key.
	 * 
	 * @param featureKey
	 *            the feature key.
	 */
	void setFeatureKey(FeatureType featureKey);

	/**
	 * Gets a position relative to the beginning of the template protein
	 * sequence.
	 * 
	 * @return the position relative to the beginning of the template protein
	 *         sequence.
	 */
	String getFeatureFromPosition();

	/**
	 * Sets a position relative to the beginning of the template protein
	 * sequence.
	 * 
	 * @param featureFromPosition
	 *            the position relative to the beginning of the template protein
	 *            sequence.
	 */
	void setFeatureFromPosition(String featureFromPosition);

	/**
	 * Gets a position relative to the end of the template protein sequence.
	 * 
	 * @return the position relative to the end of the template protein
	 *         sequence.
	 */
	String getFeatureToPosition();

	/**
	 * Sets a position relative to the end of the template protein sequence.
	 * 
	 * @param featureToPosition
	 *            the position related to the end of the template protein
	 *            sequence.
	 */
	void setFeatureToPosition(String featureToPosition);

	/**
	 * Gets a list of the feature descriptions.
	 * 
	 * @return the feature descriptions.
	 */
	List<String> getDescriptions();

	/**
	 * Sets a list of feature descriptions
	 * 
	 * @param description
	 *            the feature descriptions
	 */
	void setDescription(List<String> descriptions);

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
	String getFeatureConditionPattern();

	/**
	 * Sets the feature condition pattern. The "pattern" is specified in the
	 * PROSITE pattern format with the addition that the character "*" may be
	 * used to specify an unconstrained range, e.g. "C-x*-C". The region of the
	 * sequence corresponding to the feature must exactly match this pattern.
	 * 
	 * @param featureConditionPattern
	 *            the feature condition pattern.
	 */
	void setFeatureConditionPattern(String featureConditionPattern);
	
	/**
	 * Gets the feature description.
	 * @return the feature description.
	 */
	String getFeatureDescription();
	
	String toReport();
}
