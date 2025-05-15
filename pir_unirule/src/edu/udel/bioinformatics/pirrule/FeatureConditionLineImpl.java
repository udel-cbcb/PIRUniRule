package edu.udel.bioinformatics.pirrule;

import org.proteininformationresource.pirrule.model.FeatureConstraintLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * The condition gives constraints on the FT features.
 */
public class FeatureConditionLineImpl extends FTLineImpl implements FeatureConstraintLine {

	private int featureGroupNumber;
	private String conditionPattern;

	public FeatureConditionLineImpl() {
		super();
	}

	public FeatureConditionLineImpl(int featureGroupNumber, String conditionPattern) {
		super();
		this.featureGroupNumber = featureGroupNumber;
		this.conditionPattern = conditionPattern;
	}

	@Override
	public int getFeatureGroupNumber() {
		return this.featureGroupNumber;
	}

	@Override
	public String getConditionPattern() {
		return this.conditionPattern;
	}

	@Override
	public void setFeatureGroupNumber(int featureGroupNumber) {
		this.featureGroupNumber = featureGroupNumber;
	}

	@Override
	public void setConditionPattern(String conditionPattern) {
		this.conditionPattern = conditionPattern;
	}

	// public String toString() {
	// return
	// UniRuleFlatFileConstants.FT_LINE_START+UniRuleFlatFileConstants.FEATURE_CONDITION
	// + this.featureGroupNumber+ "; " +
	// UniRuleFlatFileConstants.FEATURE_CONSTRAINT_CONDITION+this.conditionPattern;
	// }
	//

}
