package org.proteininformationresource.pirrule.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * A list of <code>Line</code>s with optional condition statements.
 */

public interface Block {
	/**
	 * Gets a list of <code>Line</code>s within this <code>Block</code>.
	 * 
	 * @return a list of <code>Line</code>s within this <code>Block</code>.
	 */
	List<Line> getLines();

	/**
	 * Sets a list of <code>Line</code>s within this <code>Block</code>.
	 * 
	 * @param lines
	 *            a list of <code>Line</code>s.
	 */
	void setLines(List<Line> lines);

	/**
	 * Adds a line to the block
	 * 
	 * @param line
	 *            a <code>Line</code>.
	 */
	void addLine(Line line);
}
