package org.proteininformationresource.pirrule.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 16, 2014<br><br>
 * 
 * Indicates there is no <code>CCLine</code>.
 */
public interface CCNoneLine extends Line {
	
	/**
	 * Gets the description of this special <code>CCLine</code>, should be "None".
	 * @return the description of this special <code>CCLine</code>.
	 */
	String getDescription();

}
