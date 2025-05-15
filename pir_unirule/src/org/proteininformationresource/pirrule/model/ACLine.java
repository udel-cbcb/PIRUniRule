package org.proteininformationresource.pirrule.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 17, 2014<br>
 * <br>
 * 
 * The AC (ACcession number) line of the rule, which is the identifier for the
 * Rule.
 */
public interface ACLine extends Line {

	/**
	 * Gets the AC (Accession number) of the rule, which is the identifier for
	 * the rule.
	 * 
	 * @return the AC (Accession number) of the rule, which is the identifier
	 *         for the rule.
	 */
	String getAccessionNumber();

	/**
	 * Sets the AC (Accession number) of the rule, which is the identifier for
	 * the rule.
	 * 
	 * @param accessionNumber
	 *            the AC (Accession number) of the rule, which is the identifier
	 *            for the rule.
	 */
	void setAccessionNumber(String accessionNumber);

}
