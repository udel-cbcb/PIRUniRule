package edu.udel.bioinformatics.pirrule;

import java.util.List;

import org.proteininformationresource.pirrule.model.HeaderSectionVisitor;
import org.proteininformationresource.pirrule.model.Line;

public class HeaderSectionUniRuleWriterVisitor implements HeaderSectionVisitor {

	private StringBuffer headerSection = new StringBuffer();

	public String getHeaderSection() {
		return this.headerSection.toString();
	}

	@Override
	public void visit(List<Line> lines) {
		if (lines != null) {
			for (Line line : lines) {
				this.headerSection.append(line.toString());
				
			}
		}

	}

	
}
