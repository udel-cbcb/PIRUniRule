package edu.udel.bioinformatics.pirrule;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirrule.model.AnnotationSection;
import org.proteininformationresource.pirrule.model.AnnotationSectionVisitor;
import org.proteininformationresource.pirrule.model.ControlBlock;
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

public class AnnotationSectionImpl implements AnnotationSection {

	private List<ControlBlock> controlBlocks = null;
	private List<Line> lines = null;
	private boolean isComplete = false;
	private List<Line> nonControlBlockLines = null;

	public AnnotationSectionImpl() {
		super();
		controlBlocks = new ArrayList<ControlBlock>();
		lines = new ArrayList<Line>();
		nonControlBlockLines = new ArrayList<Line>();
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	@Override
	public void accept(AnnotationSectionVisitor visitor) {
		visitor.visit(this.lines);
	}

	public boolean isComplete() {
		return this.isComplete;
	}

	@Override
	public List<ControlBlock> getControlBlocks() {
		return this.controlBlocks;
	}

	@Override
	public void setControlBlocks(List<ControlBlock> controlBlocks) {
		this.controlBlocks = controlBlocks;
	}

	@Override
	public List<Line> getLines() {
		return this.lines;
	}

	@Override
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	@Override
	public Line getLastLine() {
		if (this.lines != null && this.lines.size() > 0) {
			return this.lines.get(this.lines.size() - 1);
		}
		return null;
	}

	@Override
	public void setLastLine(Line lastLine) {
		this.lines.set(this.lines.size() - 1, lastLine);
	}

	@Override
	public List<Line> getNonControlBlockLines() {
		return this.nonControlBlockLines;
	}

	@Override
	public void setNonControlBlockLines(List<Line> nonControlBlockLines) {
		this.nonControlBlockLines = nonControlBlockLines;
	}

	@Override
	public Line getLastNonControlBlockLine() {
		if (this.nonControlBlockLines != null && this.nonControlBlockLines.size() > 0) {
			return this.nonControlBlockLines.get(this.nonControlBlockLines.size() - 1);
		}
		return null;
	}

	@Override
	public void setLastNonControlBlockLine(Line lastNonControlBlockLine) {
		if (this.nonControlBlockLines.size() == 0) {
			this.nonControlBlockLines.add(lastNonControlBlockLine);
		} else {
			this.nonControlBlockLines.set(this.nonControlBlockLines.size() - 1, lastNonControlBlockLine);
		}
	}

}
