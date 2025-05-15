package org.proteininformationresource.pirsr.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: June 2, 2014<br><br>
 * 
 * Comments concerning the rule, which should be made visible to the public.
 */
public interface CommentsLine extends Line{
	
	/**
	 * Gets the comment text
	 * @return comment text
	 */
	public String getCommentText();
	
	/**
	 * Sets the comment text
	 * @param commentText
	 */
	void setCommentText(String commentText);
}
