package edu.udel.bioinformatics.pirsr;

import org.proteininformationresource.pirsr.model.DRLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * 
 */
public class DRLineImpl implements DRLine {

	private String dbName;
	private String featureName;
	private String identifier;
	private String nbHits;
	private String triggerLevel;

	public DRLineImpl() {
		super();
	}

	
	public String getDBName() {
		return this.dbName;
	}

	
	public void setDBName(String dbName) {
		this.dbName = dbName;
	}

	
	public String getFeatureName() {
		return this.featureName;
	}

	
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	
	public String getIdentifier() {
		return this.identifier;
	}

	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	
	public String getNBHits() {
		return this.nbHits;
	}

	
	public void setNBHits(String nbHits) {
		this.nbHits = nbHits;
	}

	
	public String getTriggerLevel() {
		return this.triggerLevel;
	}

	
	public void setTriggerLevel(String triggerLevel) {
		this.triggerLevel = triggerLevel;

	}

	public String toString() {
		return "DR   " + this.dbName + "; " + this.featureName + "; " + this.identifier + "; " + this.nbHits + "; " + this.triggerLevel + "\n";
	}

}
