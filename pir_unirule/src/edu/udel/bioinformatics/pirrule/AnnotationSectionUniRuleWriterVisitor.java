package edu.udel.bioinformatics.pirrule;

import java.util.List;

import org.proteininformationresource.pirrule.model.AnnotationSectionVisitor;
import org.proteininformationresource.pirrule.model.Line;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * The Annotation section of the rule, which includes all UniProtKB annotation
 * lines that can be applied to a rule match. Additionally, there can be lines
 * which indicate condition statements ('case', 'else case', 'else') and a line
 * which indicates the motif or alignments, according to which the feature
 * positions are calculated. The line order is the same as in
 * UniProtKB/Swiss-Prot.
 */
public class AnnotationSectionUniRuleWriterVisitor implements AnnotationSectionVisitor {

	private StringBuffer annotationSection = new StringBuffer();



	public String getAnnotationSection() {
		return this.annotationSection.toString();
	}

	@Override
	public void visit(List<Line> lines) {
		for(Line line : lines) {
			this.annotationSection.append(line.toString());
		}
		
	}

	
}
