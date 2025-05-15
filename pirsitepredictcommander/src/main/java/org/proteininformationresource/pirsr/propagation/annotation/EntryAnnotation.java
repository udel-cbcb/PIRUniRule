package org.proteininformationresource.pirsr.propagation.annotation;

import org.proteininformationresource.pirsr.uniprot.model.EntryType;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 * 
 */
public interface EntryAnnotation {
	
	/**
	 * Gets the UniProt entry accession number.
	 * @return the UniProt entry accession number.
	 */
	String getEntryAC();
	
	
	/**
	 * Sets the UniProt entry accession number.
	 * @param entryAC the UniProt entry accession number.
	 */
	void setEntryAC(String entryAC);
	
	/**
	 * Gets the UniProt entry identifier.
	 * @return the UniProt entry identifier.
	 */
	String getEntryID();
	
	
	/**
	 * Sets the UniProt entry identifier.
	 * @param entryID the UniProt entry identifier.
	 */
	void setEntryID(String entryID);
	
	/**
	 * Gets the UniProt entry type.
	 * @return the UniProt entry type.
	 */
	EntryType getEntryType();
	
	/**
	 * Sets the UniProt entry type.
	 * @param entryType the UniProt entry type.
	 */
	void setEntryType(EntryType entryType);
	
	/**
	 * Gets the entry annotation in report format.
	 * @return the entry annotation in report format.
	 */
	String toReport();
	
	/**
	 *
	 * 
	 */
	
	/**
	 * Gets the entry annotation in Kraken format.
	 * @param ruleAnnotation rule annotation
	 * @return the entry annotation in Kraken format.
	 */
	String toKraken(RuleAnnotation ruleAnnotation);
	
	
}
