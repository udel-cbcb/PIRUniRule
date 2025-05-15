package org.proteininformationresource.pirrule.propagation.annotation;

import org.proteininformationresource.pirrule.model.CCLine;
import org.proteininformationresource.pirrule.model.RuleCommentType;
import org.proteininformationresource.pirrule.model.Line;

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
