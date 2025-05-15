package org.proteininformationresource.pirsr.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 22, 2014<br>
 * <br>
 * 
 * Lists accession number(s) of characterized proteins which were used to build
 * the UniRule (Note: indicative only). Uncharacterized protein families do not
 * necessarily have a template, this is noted as 'Template: None;'.
 */
public interface TemplateLine extends Line {
	/**
	 * Gets the list of template accession numbers.
	 * 
	 * @return the list of template accession numbers.
	 */
	List<String> getTemplateAccessionNumbers();

	/**
	 * Sets the list of template accession numbers.
	 * 
	 * @param templateAccessionNumbers
	 *            the list of template accession numbers.
	 */
	void setTemplateAccessionNumbers(List<String> templateAccessionNumbers);
}
