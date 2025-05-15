package org.proteininformationresource.pirsr.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2013<br>
 * <br>
 * 
 * Related line that lists other rules known to be similar in sequence, and risk
 * to produce cross-matches.
 */

public interface RelatedLine extends Line {

	/**
	 * Gets a list of related PIR Rule accession numbers. Default is "none".
	 * 
	 * @return a list of related PIR Rule accession numbers. Default is "none".
	 */
	List<String> getRelatedPIRRuleAccessionNumbers();

	/**
	 * Sets a list of realted PIR Rule accession numbers. Default is "none".
	 * 
	 * @param relatedPIRRuleAccesionNumbers
	 *            a list of related PIR Rule accession numbers. Default is
	 *            "none".
	 */
	void setRelatedPIRRuleAccesionNumbers(List<String> relatedPIRRuleAccesionNumbers);

}
