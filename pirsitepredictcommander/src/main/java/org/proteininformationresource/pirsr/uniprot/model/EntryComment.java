package org.proteininformationresource.pirsr.uniprot.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * The free text comments on the entry, and are used to convey any useful
 * information. The comments always appear below the last reference line and are
 * grouped together in comment blocks; a block is made up of 1 or more comment
 * lines. The first line of a block starts with the characters '-!-'.
 * 
 * The format of a comment block is:
 * 
 * 
 * CC -!- TOPIC: First line of a comment block;
 * 
 * CC            second and subsequent lines of a comment block.
 * 
 * The comment blocks are arranged according to what we designate as 'topics'.
 */
public interface EntryComment extends EntryLine {

	/**
	 * Gets the type of comment ("topic").
	 * @return the type of comment ("topic").
	 */
	EntryCommentType getCommentType();

	/**
	 * Gets the comment description.
	 * @return the comment description.
	 */
	String getCommentDescription();
	
	/**
	 * Sets the comment description.
	 * @param commentDescription the comment description.
	 */
	void setCommentDescription(String commentDescription);
	
	/**
	 * Gets the comment description list.
	 * @return the comment description list.
	 */
	List<String> getDescripitonList();
	
	String toReport();
}
