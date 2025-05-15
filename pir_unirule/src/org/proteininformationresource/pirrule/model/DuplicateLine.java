package org.proteininformationresource.pirrule.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 16, 2014<br>
 * <br>
 * 
 * The Duplicate line Lists the organisms for which the motif which triggers the
 * rule is found in multiple copies.
 */
public interface DuplicateLine extends Line {
	/**
	 * Gets a list of organisms.
	 * 
	 * @return a list of organisms.
	 */
	List<String> getOrganisms();

	/**
	 * Sets a list of organisms.
	 * 
	 * @param organisms
	 *            a list of organisms.
	 */
	void setOrganisms(List<String> organisms);

}
