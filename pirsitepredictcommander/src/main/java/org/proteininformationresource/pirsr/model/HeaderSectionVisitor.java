package org.proteininformationresource.pirsr.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * 
 * The Header section of the rule, which contains technical information.
 */
public interface HeaderSectionVisitor {

	/**
	 * Visits a list of <code>Line</code>s.
	 * 
	 * @param lines
	 *            a list of <code>Line</code>s.
	 */
	void visit(List<Line> lines);

}
