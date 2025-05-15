package org.proteininformationresource.pirsr.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * The Computing section contrasts with the Annotation section in that the line
 * identifiers are no longer restricted to 2 letters. The information starts in
 * the line of the line identifier, the following lines are indented by 1 space.
 * 
 */
public interface ComputingSectionVisitor {
	
	//<T> void visit(List<T> controlBlocksOrLines);

	void visit(List<Line> lines);

}
