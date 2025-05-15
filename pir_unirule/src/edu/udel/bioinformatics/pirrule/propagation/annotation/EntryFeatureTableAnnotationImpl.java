package edu.udel.bioinformatics.pirrule.propagation.annotation;

import org.proteininformationresource.pirrule.propagation.annotation.EntryFeatureTableAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.RuleAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.RuleFeatureTableAnnotation;
import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureType;
import org.proteininformationresource.pirrule.uniprot.model.EntryType;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 */
public class EntryFeatureTableAnnotationImpl extends EntryAnnotationImpl implements EntryFeatureTableAnnotation {

	private EntryFeatureTable feature;
	private EntryFeatureType featureType;
	private String missMatchReason;
	
	public EntryFeatureTableAnnotationImpl(String entryAC, String entryID, EntryType entryType, EntryFeatureTable feature, String missMatchReason) {
		super(entryAC, entryID, entryType);
		this.feature = feature;
		if (this.feature != null) {
			this.featureType = feature.getFeatureType();
		}
		this.missMatchReason = missMatchReason;
	}

	public EntryFeatureTable getFeature() {
		return feature;
	}

	public void setFeature(EntryFeatureTable feature) {
		this.feature = feature;
	}

	public EntryFeatureType getFeatureType() {
		return featureType;
	}

	@Override
	public String getFeatureDescription() {
		return this.feature.getFeatureDescription();
	}

	public String toString() {
		String record = "";
		record += super.getEntryAC() + "\t";
		record += super.getEntryID() + "\t";
		record += super.getEntryType() + "\t";
		if (feature != null) {
			record += feature.getFeatureType() + "\t";
			record += this.getFeatureDescription() + "\t";

			record += feature.getFromEndPoint() + "\t";
			record += feature.getToEndPoint();
		} else {
			record += "Feature\t\t?\t?";
		}

		return record;
	}

	public String toReport() {
		String report = "";
		if (this.feature != null) {
			report += feature.getFeatureType() + "\t";
			report += this.getFeatureDescription() + "\t";
			if(!feature.getFromEndPoint().equals("")) {
				report += feature.getFromEndPoint() + "\t";
			}
			else {
				report += "?\t";
			}
			if(!feature.getToEndPoint().equals("")) {
				report += feature.getToEndPoint() + "";
			}
			else {
				report += "?";
			}
			
		} else {
			report += "Feature\t\t?\t?";
		}
		return report;
	}

	public String toKraken(RuleAnnotation ruleAnnotation) {
		String kraken = "";
		if (feature != null) {
			kraken += "FEATURE_"+feature.getFeatureType() + "\t";
			//kraken += this.getFeatureDescription() + "\t";
			String description = ((RuleFeatureTableAnnotation)ruleAnnotation).getDescription().replaceAll("\\.$", "");
			description = description.replace("\\;$", "");
			kraken += description+"\t";
			kraken += super.getEntryAC() + "\t";
			kraken += feature.getFromEndPoint() + "\t";
			kraken += feature.getToEndPoint();
		}
		return kraken;
	}

	@Override
	public String getMissmatchReason() {
		return this.missMatchReason;
	}

}
