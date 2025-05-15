package org.proteininformationresource.pirsr.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 16, 2014<br>
 * <br>
 * 
 * The DE line corresponds to the description line of a UniProtKB/Swiss-Prot
 * entry
 */

public interface DELine extends Line {
	/**
	 * Gets the name type.
	 * 
	 * @return the name type.
	 */
	String getNameType();

	/**
	 * Sets the name type.
	 * 
	 * @param nameType
	 *            the name type.
	 */
	void setNameType(String nameType);

	/**
	 * Gets a list of full names.
	 * 
	 * @return a list of full names.
	 */
	List<String> getFullNames();

	/**
	 * Sets a list of full names.
	 * 
	 * @param fullNames
	 *            a list of full names.
	 */
	void setFullNames(List<String> fullNames);

	/**
	 * Gets a list of short names.
	 * 
	 * @return a list of short names.
	 */
	List<String> getShortNames();

	/**
	 * Sets a list of short names.
	 * 
	 * @param shortNames
	 *            a list of short names.
	 */
	void setShortNames(List<String> shortNames);

	/**
	 * Gets a list of EC numbers.
	 * 
	 * @return a list of EC numbers.
	 */
	List<String> getECs();

	/**
	 * Sets a list of EC numbers.
	 * 
	 * @param ecNumbers
	 *            a list of EC numbers.
	 */
	void setECs(List<String> ecNumbers);
}
