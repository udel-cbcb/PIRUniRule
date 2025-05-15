package org.proteininformationresource.pirsr.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 17, 2014<br>
 * <br>
 * 
 * The Annotation section of the rule, which includes all UniProtKB
 * annotation lines that can be applied to a rule match. Additionally, there can
 * be lines which indicate condition statements ('case', 'else case', 'else')
 * and a line which indicates the motif or alignments, according to which the
 * feature positions are calculated. The line order is the same as in
 * UniProtKB/Swiss-Prot.
 * 
 * A visitor which visits the different lines of
 * <code>AnnotationSection</code>
 */

public interface AnnotationSectionVisitor {

	/**
	 * Visit each of the lines in the <code>AnnotationSection</code>.
	 * @param lines a list of <code>Line</code>s in the <code>AnnotationSection</code>.
	 */
	void visit(List<Line> lines);

}
