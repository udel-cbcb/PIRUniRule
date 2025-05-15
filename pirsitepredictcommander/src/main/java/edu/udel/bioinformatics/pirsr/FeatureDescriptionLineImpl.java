package edu.udel.bioinformatics.pirsr;

import java.util.List;

import org.proteininformationresource.pirsr.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirsr.model.FeatureDescriptionLine;
import org.proteininformationresource.pirsr.model.FeatureType;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * The description of actual features that are to be propagated in member
 * entries.
 */
public class FeatureDescriptionLineImpl extends FTLineImpl implements FeatureDescriptionLine {

	// private String featureKey;
	private FeatureType featureKey;
	private String featureFromPosition;
	private String featureToPosition;
	private List<String> descriptionList;
	private int featureGroupNumber;
	private String featureConditionPattern;

	public FeatureDescriptionLineImpl() {
		super();
	}

	public FeatureDescriptionLineImpl(FeatureType featureKey, String featureFromPosition, String featureToPosition, List<String> descriptionList) {
		super();
		this.featureKey = featureKey;
		this.featureFromPosition = featureFromPosition;
		this.featureToPosition = featureToPosition;
		this.descriptionList = descriptionList;
		this.featureGroupNumber = 0;
		this.featureConditionPattern = null;
	}

	
	public FeatureType getFeatureKey() {
		return this.featureKey;
	}

	
	public String getFeatureFromPosition() {
		return this.featureFromPosition;
	}

	
	public String getFeatureToPosition() {
		return this.featureToPosition;
	}

	
	public List<String> getDescriptions() {
		return this.descriptionList;
	}

	
	public void setFeatureKey(FeatureType featureKey) {
		this.featureKey = featureKey;
	}

	
	public void setFeatureFromPosition(String featureFromPosition) {
		this.featureFromPosition = featureFromPosition;
	}

	
	public void setFeatureToPosition(String featureToPosition) {
		this.featureToPosition = featureToPosition;
	}

	
	public void setDescription(List<String> descriptionList) {
		this.descriptionList = descriptionList;
	}

	public String toString() {
		String str = "";
		int featureKeyLength = this.featureKey.getFeature().length();
		int featureFromPositionLength = featureFromPosition.length();
		int featureToPositionLength = featureToPosition.length();
		int featureKeyRightPadding = 15 - featureKeyLength - featureFromPositionLength;
		// System.out.println(featureKeyRightPadding + " | " + featureKeyLength
		// + " | " + featureFromPositionLength);
		int featureFromPositionRightPadding = 7 - featureToPositionLength;

		int featureToPositionRightPadding = 8;

		str += UniRuleFlatFileConstants.FT_LINE_START + this.featureKey + UniRuleFlatFileConstants.getBlankSpaces(featureKeyRightPadding);
		str += this.featureFromPosition + UniRuleFlatFileConstants.getBlankSpaces(featureFromPositionRightPadding);
		str += this.featureToPosition + UniRuleFlatFileConstants.getBlankSpaces(featureToPositionRightPadding);
		if (this.descriptionList.size() == 1) {
			str += this.descriptionList.get(0);
			if(!this.descriptionList.get(0).equals("")) {
				if(!(str.endsWith(".") || str.endsWith(";"))) {
					str += ".";
				}
			}
		} else {
			str += this.descriptionList.get(0) + "\n";
			for (int i = 1; i < this.descriptionList.size(); i++) {
				if (i == 1) {
					str += UniRuleFlatFileConstants.FT_LINE_START + UniRuleFlatFileConstants.getBlankSpaces(30) + this.descriptionList.get(i);
				} else {
					str += "\n" + UniRuleFlatFileConstants.FT_LINE_START + UniRuleFlatFileConstants.getBlankSpaces(30) + this.descriptionList.get(i);
				}
			}
			if(!(str.endsWith(".") || str.endsWith(";"))) {
				str += ".";
			}
		}
		
		if (this.getFeatureGroupNumber() != 0 && this.getFeatureConditionPattern() != null) {
			str += "\n" + UniRuleFlatFileConstants.FT_LINE_START + UniRuleFlatFileConstants.FEATURE_CONSTRAIN_GROUP + this.getFeatureGroupNumber()
					+ "; " + UniRuleFlatFileConstants.FEATURE_CONSTRAINT_CONDITION + this.featureConditionPattern + "\n";
		}
		return str;
	}
	
	public String toReport() {
		String str = "";
		int featureKeyLength = this.featureKey.getFeature().length();
		int featureFromPositionLength = featureFromPosition.length();
		int featureToPositionLength = featureToPosition.length();
		int featureKeyRightPadding = 15 - featureKeyLength - featureFromPositionLength;
		// System.out.println(featureKeyRightPadding + " | " + featureKeyLength
		// + " | " + featureFromPositionLength);
		int featureFromPositionRightPadding = 7 - featureToPositionLength;

		int featureToPositionRightPadding = 8;

		str += UniRuleFlatFileConstants.FT_LINE_START + this.featureKey + UniRuleFlatFileConstants.getBlankSpaces(featureKeyRightPadding);
		str += this.featureFromPosition + UniRuleFlatFileConstants.getBlankSpaces(featureFromPositionRightPadding);
		str += this.featureToPosition + UniRuleFlatFileConstants.getBlankSpaces(featureToPositionRightPadding);
		if (this.descriptionList.size() == 1) {
			str += this.descriptionList.get(0);
			
		} else {
			str += this.descriptionList.get(0) + "\n";
			for (int i = 1; i < this.descriptionList.size(); i++) {
				if (i == 1) {
					str += UniRuleFlatFileConstants.FT_LINE_START + UniRuleFlatFileConstants.getBlankSpaces(30) + this.descriptionList.get(i);
				} else {
					str += "\n" + UniRuleFlatFileConstants.FT_LINE_START + UniRuleFlatFileConstants.getBlankSpaces(30) + this.descriptionList.get(i);
				}
			}
			
		}
		
		if (this.getFeatureGroupNumber() != 0 && this.getFeatureConditionPattern() != null) {
			str += "\n" + UniRuleFlatFileConstants.FT_LINE_START + UniRuleFlatFileConstants.FEATURE_CONSTRAIN_GROUP + this.getFeatureGroupNumber()
					+ "; " + UniRuleFlatFileConstants.FEATURE_CONSTRAINT_CONDITION + this.featureConditionPattern + "\n";
		}
		return str;
	}

	
	public int getFeatureGroupNumber() {
		return this.featureGroupNumber;
	}

	
	public void setFeatureGroupNumber(int featureGroupNumber) {
		this.featureGroupNumber = featureGroupNumber;
	}

	
	public String getFeatureConditionPattern() {
		return this.featureConditionPattern;
	}

	
	public void setFeatureConditionPattern(String featureConditionPattern) {
		this.featureConditionPattern = featureConditionPattern;
	}

	
	public String getFeatureDescription() {
		String record = "";
		// for(String desc: this.descriptionList) {
		for (int i = 0; i < this.descriptionList.size(); i++) {
			if (i == 0) {
				record += this.descriptionList.get(i).trim();
			} else {
				if(record.endsWith("-")) {
					record += this.descriptionList.get(i).trim();
				}
				else {
					record += " " + this.descriptionList.get(i).trim();
				}
			}
		}
		// }
		return record;
	}

}
