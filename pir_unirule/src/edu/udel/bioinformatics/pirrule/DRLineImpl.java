package edu.udel.bioinformatics.pirrule;

import org.proteininformationresource.pirrule.model.DRLine;

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

	@Override
	public String getDBName() {
		return this.dbName;
	}

	@Override
	public void setDBName(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public String getFeatureName() {
		return this.featureName;
	}

	@Override
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	@Override
	public String getIdentifier() {
		return this.identifier;
	}

	@Override
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String getNBHits() {
		return this.nbHits;
	}

	@Override
	public void setNBHits(String nbHits) {
		this.nbHits = nbHits;
	}

	@Override
	public String getTriggerLevel() {
		return this.triggerLevel;
	}

	@Override
	public void setTriggerLevel(String triggerLevel) {
		this.triggerLevel = triggerLevel;

	}

	public String toString() {
		return "DR   " + this.dbName + "; " + this.featureName + "; " + this.identifier + "; " + this.nbHits + "; " + this.triggerLevel + "\n";
	}

}
