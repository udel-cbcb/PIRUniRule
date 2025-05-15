package edu.udel.bioinformatics.pirsr;

import java.util.List;

import org.proteininformationresource.pirsr.model.ComputingSectionVisitor;
import org.proteininformationresource.pirsr.model.Line;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 *  
 * A visitor to visit the different lines in <code>ComputingSection</code>.
 */
public class ComputingSectionUniRuleWriterVisitor implements ComputingSectionVisitor {

	private StringBuffer computingSection = new StringBuffer();

	public String getComputingSection() {
		return this.computingSection.toString();
	}

	private void visit(Line line) {

		this.computingSection.append(line.toString());

	}

	
	public void visit(List<Line> lines) {
		for (Line line : lines) {
			visit(line);
		}

	}
}
