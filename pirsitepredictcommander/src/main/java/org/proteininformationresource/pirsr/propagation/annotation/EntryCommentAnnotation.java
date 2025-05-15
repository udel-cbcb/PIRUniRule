package org.proteininformationresource.pirsr.propagation.annotation;

import org.proteininformationresource.pirsr.uniprot.model.EntryComment;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 * 
 */
public interface EntryCommentAnnotation extends EntryAnnotation {
	
	/**
	 * Gets UniProt entry comment.
	 * @return the UniProt entry comment.
	 */
	EntryComment getComment();
	
	/**
	 * Sets UniProt entry comment.
	 * @param comment the UniProt entry comment.
	 */
	void setComment(EntryComment comment);
	
	/**
	 * Gets the comment description.
	 * @return the comment description.
	 */
	String getCommentDescription();

}
