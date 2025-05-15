package org.proteininformationresource.pirsr.prediction.annotation;

import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.RuleCommentType;
import org.proteininformationresource.pirsr.prediction.Prediction;
import org.proteininformationresource.pirsr.uniprot.model.EntryComment;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 27, 2015<br>
 * <br>
 * 
 * Protein comment annotation information.
 */
public interface ProteinCommentAnnotation extends ProteinAnnotation {
	/**
	 * Gets the predicted comment for protein.
	 * @return the predicted comment. 
	 */
	CCLine getComment();
	
	/**
	 * Sets the predicted comment for protein.
	 * @param the predicted comment.
	 */
	void setComment(CCLine comment);
	
	/**
	 * Gets the predicted comment description.
	 * @return the predicted comment description.
	 */
	String getCommentDescription();
	
	/**
	 * Gets the predicted comment type.
	 * @return the predicted comment type.
	 */
	RuleCommentType getCommentType();
	
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
