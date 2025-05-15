package org.proteininformationresource.pirsr.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * The FT From line is mandatory above the FT feature line. It defines the
 * "template" / "frame of reference" to allow the rule specified coordinates to
 * be mapped into the target sequence coordinates, for all subsequent FT feature
 * lines. The template must be a protein identifier.
 */

public interface FTFromLine extends FTLine {

	/**
	 * Gets the accession number of template protein, the subsequent positions
	 * will be mapped to the annotated target sequence.
	 * 
	 * @return the accession number of template protein.
	 */
	String getFTTemplateAccessionNumber();

	/**
	 * Sets the accession number of template protein, the subsequent positions
	 * will be mapped to the annotated target sequence.
	 * 
	 * @param ftTemplateAccessionNumber
	 */
	void setFTTemplateAccessionNumber(String ftTemplateAccessionNumber);

}
