package edu.udel.bioinformatics.pirsr;

import java.util.List;

import org.proteininformationresource.pirsr.model.HeaderSectionVisitor;
import org.proteininformationresource.pirsr.model.Line;

public class HeaderSectionUniRuleWriterVisitor implements HeaderSectionVisitor {

	private StringBuffer headerSection = new StringBuffer();

	public String getHeaderSection() {
		return this.headerSection.toString();
	}

	
	public void visit(List<Line> lines) {
		if (lines != null) {
			for (Line line : lines) {
				this.headerSection.append(line.toString());
				
			}
		}

	}

	
}
