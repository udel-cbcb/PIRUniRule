package org.proteininformationresource.pirsr.propagation.annotation;

import org.proteininformationresource.pirsr.uniprot.model.EntryKeyword;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 * 
 */
public interface EntryKeywordAnnotation extends EntryAnnotation {
	
	/**
	 * Gets the UniProt entry keyword.
	 * @return the UniProt entry keyword.
	 */
	EntryKeyword getKeyword();
	
	/**
	 * Sets the UniProt entry keyword.
	 * @param keyword the UniProt entry keyword.
	 */
	void setKeyword(EntryKeyword keyword);
	
	/**
	 * Gets the entry keyword annotation in report format.
	 * @return the entry keyword annotation in report format.
	 */
	String toReport();
	
	/**
	 * Gets the entry keyword annotation in Kraken format.
	 * @return the entry keyword annotation in Kraken format.
	 */
	//String toKraken();
}
