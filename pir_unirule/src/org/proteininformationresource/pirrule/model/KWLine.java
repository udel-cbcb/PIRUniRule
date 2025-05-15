package org.proteininformationresource.pirrule.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * The KW line contains a applicable keyword for a UniProtKB/Swiss-Prot entry,
 * one per line.
 */
public interface KWLine extends Line {

	/**
	 * Gets the applicable keyword for a UniProtKB/Swiss-Prot entry.
	 * 
	 * @return applicable keyword for a UniProtKB/Swiss-Prot entry.
	 */
	String getKeyword();

	/**
	 * Sets the applicable keyword for a UniProtKB/Swiss-Prot entry.
	 * 
	 * @param keyword
	 *            applicable keyword for a UniProtKB/Swiss-Prot entry.
	 */
	void setKeyword(String keyword);

}
