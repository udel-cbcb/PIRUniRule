package edu.udel.bioinformatics.pirrule.propagation.annotation;

import java.util.List;

import org.proteininformationresource.pirrule.model.FeatureType;
import org.proteininformationresource.pirrule.model.FTLine;
import org.proteininformationresource.pirrule.model.FeatureDescriptionLine;
import org.proteininformationresource.pirrule.propagation.annotation.RuleFeatureTableAnnotation;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 */
public class RuleFeatureTableAnnotationImpl extends RuleAnnotationImpl implements RuleFeatureTableAnnotation {

	private FeatureType featureType;
	private FeatureDescriptionLine featureDescriptionLine;

	public RuleFeatureTableAnnotationImpl(String ruleAC, String templateAC, FeatureDescriptionLine featureDescriptionLine) {
		super(ruleAC, templateAC);
		this.featureDescriptionLine = featureDescriptionLine;
		if (this.featureDescriptionLine != null) {
			this.featureType = this.featureDescriptionLine.getFeatureKey();
		}
	}

	public FeatureType getFeatureType() {
		return featureType;
	}

	public FeatureDescriptionLine getFeatureDescriptionLine() {
		return featureDescriptionLine;
	}

	public void setFeatureDescriptionLine(FeatureDescriptionLine featureDescriptionLine) {
		this.featureDescriptionLine = featureDescriptionLine;
	}

	public String toString() {
		String record = "";
		record += super.getRuleAC() + "\t";
		record += super.getTemplateAC() + "\t";
		if (this.featureDescriptionLine != null) {
			record += featureType + "\t";
			String description = "";

			if (this.getDescription() != null && this.getDescription().length() > 0) {
				description += "Description: " + this.getDescription() + " | ";
			}
			//description = description.trim();
			description += "Condition Pattern: " + this.featureDescriptionLine.getFeatureConditionPattern() + " | Group: "
					+ this.featureDescriptionLine.getFeatureGroupNumber();
			record += description + "\t";
			record += this.featureDescriptionLine.getFeatureFromPosition() + "\t";
			record += this.featureDescriptionLine.getFeatureToPosition();
		} else {
			record += "\t\t?\t?";
		}
		return record;
	}

	@Override
	public String toReport() {
		String report = "";
		if (this.featureDescriptionLine != null) {
			report += featureType + "\t";
			String description = "";
			List<String> descriptionList = this.featureDescriptionLine.getDescriptions();
			if (descriptionList != null && descriptionList.size() > 0) {

				description += "Description: ";
				for (int i = 0; i < descriptionList.size(); i++) {
					if (i == 0) {
						description += descriptionList.get(i);
					} else {
						description += " " + descriptionList.get(i);
					}
				}
			}
			description = description.trim();
			description += " | Condition Pattern: " + this.featureDescriptionLine.getFeatureConditionPattern() + " | Group: "
					+ this.featureDescriptionLine.getFeatureGroupNumber();
			report += description + "\t";
			report += this.featureDescriptionLine.getFeatureFromPosition() + "\t";
			report += this.featureDescriptionLine.getFeatureToPosition();
		} else {
			report += "\t\t?\t?";
		}
		return report;
	}

	@Override
	public String toKraken() {
		String kraken = super.getRuleAC();
		return kraken;
	}

	public String getDescription() {
		String description = "";
		if (this.featureDescriptionLine != null) {
			List<String> descriptionList = this.featureDescriptionLine.getDescriptions();
			//System.out.println(descriptionList);
			if (descriptionList != null && descriptionList.size() > 0) {

				for (int i = 0; i < descriptionList.size(); i++) {
					if (i == 0) {
						description += descriptionList.get(i).trim();
					} else {
						if(description.endsWith("-")) {
							description += descriptionList.get(i).trim();
						}
						else {
							description += " " + descriptionList.get(i).trim();
						}
					}
				}
			}
		}
		description = description.trim();
		return description;
	}
}
