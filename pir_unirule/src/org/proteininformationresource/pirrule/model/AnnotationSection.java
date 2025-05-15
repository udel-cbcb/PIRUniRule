package org.proteininformationresource.pirrule.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 17, 2014<br>
 * <br>
 * 
 * The Annotation section of the rule, which includes all UniProtKB annotation
 * lines that can be applied to a rule match. Additionally, there can be lines
 * which indicate condition statements ('case', 'else case', 'else') and a line
 * which indicates the motif or alignments, according to which the feature
 * positions are calculated. The line order is the same as in
 * UniProtKB/Swiss-Prot.
 */

public interface AnnotationSection extends RuleSection {

	/**
	 * Gets a list of <code>ControlBlock</code> that are annotation lines
	 * enclosed by optional condition statements.
	 * 
	 * @return a list of <code>ControlBlock</code>.
	 */
	List<ControlBlock> getControlBlocks();

	/**
	 * Sets a list of <code>ControlBlock</code> that are annotation lines
	 * enclosed by optional condition statements.
	 * 
	 * @param caseBlocks
	 *            a list of <code>ControlBlock</code>.
	 */
	void setControlBlocks(List<ControlBlock> blocks);

	/**
	 * Gets a list of <code>Line</code> in the annotation section.
	 * 
	 * @return a list of <code>Line</code> in the annotation section.
	 */
	List<Line> getLines();

	/**
	 * Sets a list of <code>Line</code> in the annotation section.
	 * 
	 * @param lines
	 *            a list of <code>Line</code> in the annotation section.
	 */
	void setLines(List<Line> lines);

	/**
	 * A visitor which visits the different lines of
	 * <code>AnnotationSection</code>.
	 * 
	 * @param visitor
	 *            which visits the different lines of
	 *            <code>AnnotationSection</code>.
	 */
	void accept(AnnotationSectionVisitor visitor);

	/**
	 * Gets a list of <code>Line</code>, which are in the non control blocks.
	 * 
	 * @return a list of <code>Line</code>, which are in the non control blocks.
	 */
	List<Line> getNonControlBlockLines();

	/**
	 * Sets a list of <code>Line</code>, which are in the non control blocks.
	 * 
	 * @param nonControlBlockLiens
	 *            a list of <code>Line</code>, which are in the non control
	 *            blocks.
	 */
	void setNonControlBlockLines(List<Line> nonControlBlockLines);


}
