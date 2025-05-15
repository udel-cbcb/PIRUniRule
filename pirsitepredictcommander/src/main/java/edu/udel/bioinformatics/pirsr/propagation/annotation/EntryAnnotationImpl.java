package edu.udel.bioinformatics.pirsr.propagation.annotation;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 17, 2014<br>
 * <br>
 * 
 */
import org.proteininformationresource.pirsr.propagation.annotation.EntryAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleAnnotation;
import org.proteininformationresource.pirsr.uniprot.model.EntryType;



public class EntryAnnotationImpl implements EntryAnnotation {

	private String entryAC;
	private String entryID;
	private EntryType entryType;
	
	
	public EntryAnnotationImpl(String entryAC, String entryID, EntryType entryType) {
		super();
		this.entryAC = entryAC;
		this.entryID = entryID;
		this.entryType = entryType;
	}

	
	public String getEntryAC() {
		return this.entryAC;
	}

	
	public String getEntryID() {
		return this.entryID;
	}

	

	
	public void setEntryAC(String entryAC) {
		this.entryAC = entryAC;
	}

	

	public String toString() {
		String record = "";
		record += entryAC + "\t";
		record += entryID + "\t";
		record += entryType +"\t";
		record += "\t";
		record += "\t";

		record += "?\t";
		record += "?";
		
		return record;
	}

	
	public String toReport() {
		String report = "";
		report += "\t";
		report += "\t";

		report += "?\t";
		report += "?";
		return report;
	}

	
	public String toKraken(RuleAnnotation ruleAnnotation) {
		String kraken = "";
		kraken += "\t";
		kraken += "\t";
		kraken += entryAC+"\t";
		kraken += "?\t";
		kraken += "?";
		
		return kraken;
	}

	
	public void setEntryID(String entryID) {
		this.entryID = entryID;
	}

	
	public EntryType getEntryType() {
		return this.entryType;
	}

	
	public void setEntryType(EntryType entryType) {
		this.entryType = entryType;
	}
	
	
}
