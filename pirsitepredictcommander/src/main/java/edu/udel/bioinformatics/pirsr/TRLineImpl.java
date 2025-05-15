package edu.udel.bioinformatics.pirsr;

import org.proteininformationresource.pirsr.model.TRLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * The TR (Trigger) line of this PIR Rule, which describes which (selected)
 * sequence analysis features trigger the application of this PIR Rule.Each
 * feature name should appear only once in the TR line in all the PIR Rules, as
 * one type of feature is expected to trigger only one rule.
 */

public class TRLineImpl implements TRLine {

	public TRLineImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String dbName;
	private String identifier1;
	private String identifier2;
	private String nbHits;
	private String level;

	public TRLineImpl(String dbName, String identifier1, String identifier2, String nbHits, String level) {
		super();
		this.dbName = dbName;
		this.identifier1 = identifier1;
		this.identifier2 = identifier2;
		this.nbHits = nbHits;
		this.level = level;
	}

	
	public String getDBName() {
		return this.dbName;
	}

	
	public String getIdentifier1() {
		return this.identifier1;
	}

	
	public String getIdentifier2() {
		return this.identifier2;
	}

	
	public String getNBHits() {
		return this.nbHits;
	}

	
	public String getLevel() {
		return this.level;
	}

	public String toString() {
		return "TR   " + this.dbName + "; " + this.identifier1 + "; " + this.identifier2 + "; " + this.nbHits + "; level=" + this.level + "\n";
	}

	
	public void setDBName(String dbName) {
		this.dbName = dbName;
	}

	
	public void setIdentifier1(String identifier1) {
		this.identifier1 = identifier1;
	}

	
	public void setIdentifier2(String identifier2) {
		this.identifier2 = identifier2;
	}

	
	public void setNBHits(String nbHits) {
		this.nbHits = nbHits;
	}

	
	public void setLevel(String level) {
		this.level = level;
	}
}
