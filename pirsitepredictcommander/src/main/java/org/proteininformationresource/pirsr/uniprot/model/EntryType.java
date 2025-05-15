package org.proteininformationresource.pirsr.uniprot.model;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * The types of UniProt entry.
 */
public enum EntryType {
	SWISSPROT("SWISSPROT"),
	TREMBL("TREMBL"),
	UNKNOWN("UNKNOWN");
	
	private String value;
	
	EntryType(String value) {
		this.value = value;
	}
	
	String getValue() {
		return this.value;
	}
	
}
