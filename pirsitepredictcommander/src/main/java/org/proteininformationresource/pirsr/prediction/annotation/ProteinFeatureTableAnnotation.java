package org.proteininformationresource.pirsr.prediction.annotation;

import org.proteininformationresource.pirsr.model.FeatureDescriptionLine;
import org.proteininformationresource.pirsr.model.FeatureType;
import org.proteininformationresource.pirsr.prediction.Prediction;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureTable;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 27, 2015<br>
 * <br>
 * 
 * Protein feature table annotation information.
 */
public interface ProteinFeatureTableAnnotation extends ProteinAnnotation {
	/**
	 * Gets the feature type.
	 * @return the feature type.
	 */
	FeatureType getFeatureType();
	
	/**
	 * Sets the feature type.
	 * @param featureType the feature type.
	 */
	void setFeatureType(FeatureType featureType);
	
	/**
	 * Gets the feature description line.
	 * @return the feature description line.
	 */
	FeatureDescriptionLine getFeatureDescriptionLine() ;
	
	/**
	 * Sets the feature description line.
	 * @param featureDescriptionLine the feature description line.
	 */
	void setFeatureDescriptionLine(FeatureDescriptionLine featureDescriptionLine);
	/**
	 * Gets the miss match reason.
	 * @return the miss match reason.
	 */
	String getMissMatchReason();
	
	void setMissMatchReason(String missMatchReason);
	
	/**
	 * Gets the prediction info.
	 * @return the prediction info.
	 */
	Prediction getPrediction();
	
//	/**
//	 * Gets the XML representation of prediction.
//	 * @return the XML representation of prediction.
//	 */
//	String toXML();
}
