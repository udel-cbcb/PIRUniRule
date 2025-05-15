package org.proteininformationresource.pirrule.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: Feb 28, 2014<br>
 * <br>
 * 
 * 
 * The section of a rule, cabe <code>HeaderSection</code>,
 * <code>AnnotationSection</code>, <code>ComputingSection</code>,
 * <code>HistorySection</code>
 */
public interface RuleSection {

	// boolean isComplete();

	/**
	 * Gets a list <code>Line</code> in the section of rule.
	 * 
	 * @return a list <code>Line</code> in the section of rule.
	 */
	List<Line> getLines();

	/**
	 * Sets a list of <code>Line</code> in the section of rule.
	 * 
	 * @param lines
	 *            a list <code>Line</code> in the section of rule.
	 */
	void setLines(List<Line> lines);

	/**
	 * Gets the last line seen so far.
	 * 
	 * @return the last line seen so far.
	 */
	Line getLastLine();

	/**
	 * Sets the last line seen so far.
	 * 
	 * @param lastLine
	 *            the last line seen so far.
	 */
	void setLastLine(Line lastLine);

	/**
	 * Gets a list of <code>Line</code>s in non control blocks of the rule.
	 * 
	 * @return a list of <code>Line</code>s in non control blocks of the rule.
	 */
	List<Line> getNonControlBlockLines();

	/**
	 * Sets a list of <code>Line</code>s in non control blocks of the rule.
	 * 
	 * @param nonControlBlockLines
	 *            a list of <code>Line</code>s in non control blocks of the
	 *            rule.
	 */
	void setNonControlBlockLines(List<Line> nonControlBlockLines);

	/**
	 * Gets the last seen non control block line.
	 * 
	 * @return the last seen non control block line.
	 */
	Line getLastNonControlBlockLine();

	/**
	 * Sets the last seen non control block line.
	 * 
	 * @param lastNonControlBlockLine
	 *            the last seen non control block line.
	 */
	void setLastNonControlBlockLine(Line lastNonControlBlockLine);
}
