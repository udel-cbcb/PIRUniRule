package edu.udel.bioinformatics.pirsr.prediction;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 15, 2014<br>
 * <br>
 * 
 * Matched feature description.
 */
public class FeatureDescriptionMatch {

	boolean featureDescriptionMatch;
	
	String note;

	public FeatureDescriptionMatch() {
		
	}

	public boolean isFeatureDescriptionMatch() {
		return featureDescriptionMatch;
	}

	public void setFeatureDescriptionMatch(boolean featureDescriptionMatch) {
		this.featureDescriptionMatch = featureDescriptionMatch;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
}
