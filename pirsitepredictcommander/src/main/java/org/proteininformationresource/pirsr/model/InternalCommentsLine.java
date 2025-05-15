package org.proteininformationresource.pirsr.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 16, 2014<br>
 * <br>
 * 
 * Comments concerning the rule, which are for internal use, and do not appear
 * in published versions. It should appear on lines prefixed with two asterisks
 * and a space. They may appear at the very end of the Header section, and
 * throughout the Annotation section and the Computing section. However, it is
 * good practice to put such comments at the end of the Header section where
 * they are immediately visible, and only put them exceptionally in the
 * Annotation and Computing sections.
 */
public interface InternalCommentsLine extends Line {

	/**
	 * Gets the internal comments.
	 * 
	 * @return the internal comments.
	 */
	String getComments();

	/**
	 * Sets the internal comments.
	 * 
	 * @param comments
	 *            the internal comments.
	 */
	void setComments(String comments);

}
