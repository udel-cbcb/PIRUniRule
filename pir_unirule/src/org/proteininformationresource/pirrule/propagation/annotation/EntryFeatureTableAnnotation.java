package org.proteininformationresource.pirrule.propagation.annotation;

import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureTable;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 * 
 */
public interface EntryFeatureTableAnnotation extends EntryAnnotation {
	
	/**
	 * Gets UniProt entry feature table record.
	 * @return UniProt entry feature table record.
	 */
	EntryFeatureTable getFeature();

	/**
	 * Sets UniProt entry feature table record.
	 * @param feature UniProt entry feature table record.
	 */
	void setFeature(EntryFeatureTable feature);
	
	/**
	 * Gets the feature description;
	 * @return the feature description;
	 */
	String getFeatureDescription();
	
	/**
	 * Gets the miss match reason.
	 * @return the miss match reason.
	 */
	String getMissmatchReason();
}
