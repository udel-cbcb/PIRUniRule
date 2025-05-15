package org.proteininformationresource.pirsr.prediction.annotation;

import org.proteininformationresource.pirsr.model.KWLine;
import org.proteininformationresource.pirsr.prediction.Prediction;
import org.proteininformationresource.pirsr.uniprot.model.EntryKeyword;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 27, 2015<br>
 * <br>
 * 
 * Protein keyword annotation information.
 */
public interface ProteinKeywordAnnotation extends ProteinAnnotation {

	/**
	 * Gets the predicted keyword annotation for protein.
	 * @return the predicted keyword annotation.
	 */
	KWLine getKeyword();
	
	/**
	 * Sets the predicted keyword annotation for protein.
	 * @param keyword the predicted keyword annotation for protein.
	 */
	void setKeyword(KWLine keyword);
	
	/**
	 * Gets the predicated keyword annotation in report format.
	 * @return the predicated keyword annotation in report format.
	 */
	String toReport();
	
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
