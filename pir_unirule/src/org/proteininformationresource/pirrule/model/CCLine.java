package org.proteininformationresource.pirrule.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * The <code>CCLine</code> is applicable comment line of a UniProtKB/Swiss-Prot
 * entry.
 */
public interface CCLine extends Line {

	/**
	 * Gets the topic of a <code>CCLine</code>.
	 * 
	 * @return the topic of a <code>CCLine</code>.
	 */
	RuleCommentType getTopic();

	/**
	 * Sets the topic of a <code>CCLine</code>.
	 * 
	 * @param topic
	 *            the topic of a <code>CCLine</code>.
	 */
	void setTopic(RuleCommentType topic);

	/**
	 * Gets a list of detailed description of the topic.
	 * 
	 * @return a list of detailed description of the topic.
	 */
	List<String> getDescription();

	/**
	 * Sets a list of detailed description of the topic
	 * 
	 * @param description
	 *            a list of detailed description of the topic.
	 */
	void setDescription(List<String> description);
	
	/**
	 * Gets the comment description.
	 * @return the comment description.
	 */
	String getCommentDescription();
	
	String toReport();
}
