package org.proteininformationresource.pirsr.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * 
 * The Size line that indicates the size relevant to a protein family or motif.
 * A size may be specified as 'unlimited'.
 */
public interface SizeLine extends Line {

	/**
	 * Gets the size limit relevant to a protein family or motif. A size may be
	 * specified as 'unlimited'.
	 * 
	 * @return the size limit relevant to a protein family or motif.
	 */
	String getSizeLimit();

	/**
	 * Sets the size limit relevant to a protein family or motif. A size may be
	 * specified as 'unlimited'.
	 * 
	 * @param sizeLimit
	 *            the size limit relevant to a protein family or motif.
	 */
	void setSizeLimit(String sizeLimit);

}
