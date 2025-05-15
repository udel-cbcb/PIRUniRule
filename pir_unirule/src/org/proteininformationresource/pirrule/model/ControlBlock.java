package org.proteininformationresource.pirrule.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * Control statements and their associated block of lines.
 */

public interface ControlBlock extends Block {

	/**
	 * Gets a list of <code>ControlStatement</code>s.
	 * 
	 * @return a list of <code>ControlStatement</code>s.
	 */
	List<ControlStatement> getControlStatements();

	/**
	 * Sets a list of <code>ControlStatement</code>s.
	 * 
	 * @param controlStatements
	 *            a list of <code>ControlStatement</code>s.
	 */
	void setControlStatements(List<ControlStatement> controlStatements);

	/**
	 * Gets the UniRuleFlatFile representation of a <code>ControlBlock</code>
	 * and its associated <code>Line</code>s.
	 * 
	 * @return the UniRuleFlatFile representation of a <code>ControlBlock</code>
	 *         and its associated <code>Line</code>s.
	 */
	String toString();
}
