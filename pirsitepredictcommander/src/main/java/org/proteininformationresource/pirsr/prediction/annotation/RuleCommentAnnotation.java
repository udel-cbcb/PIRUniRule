package org.proteininformationresource.pirsr.prediction.annotation;

import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.Line;
import org.proteininformationresource.pirsr.model.RuleCommentType;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 * 
 */
public interface RuleCommentAnnotation extends RuleAnnotation {

	/**
	 * Gets the comment.
	 * @return the comment.
	 */
	CCLine getComment();
	
	/**
	 * Gets the comment topic.
	 * @return the comment topic.
	 */
	RuleCommentType getCommentType();
	
	/**
	 * Gets the comment description.
	 * @return the comment description.
	 */
	String getCommentDescription();
}
