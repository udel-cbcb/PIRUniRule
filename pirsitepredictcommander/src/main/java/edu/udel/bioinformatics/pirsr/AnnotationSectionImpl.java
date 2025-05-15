package edu.udel.bioinformatics.pirsr;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.model.AnnotationSection;
import org.proteininformationresource.pirsr.model.AnnotationSectionVisitor;
import org.proteininformationresource.pirsr.model.ControlBlock;
import org.proteininformationresource.pirsr.model.Line;

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

	
	public void accept(AnnotationSectionVisitor visitor) {
		visitor.visit(this.lines);
	}

	public boolean isComplete() {
		return this.isComplete;
	}

	
	public List<ControlBlock> getControlBlocks() {
		return this.controlBlocks;
	}

	
	public void setControlBlocks(List<ControlBlock> controlBlocks) {
		this.controlBlocks = controlBlocks;
	}

	
	public List<Line> getLines() {
		return this.lines;
	}

	
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	
	public Line getLastLine() {
		if (this.lines != null && this.lines.size() > 0) {
			return this.lines.get(this.lines.size() - 1);
		}
		return null;
	}

	
	public void setLastLine(Line lastLine) {
		this.lines.set(this.lines.size() - 1, lastLine);
	}

	
	public List<Line> getNonControlBlockLines() {
		return this.nonControlBlockLines;
	}

	
	public void setNonControlBlockLines(List<Line> nonControlBlockLines) {
		this.nonControlBlockLines = nonControlBlockLines;
	}

	
	public Line getLastNonControlBlockLine() {
		if (this.nonControlBlockLines != null && this.nonControlBlockLines.size() > 0) {
			return this.nonControlBlockLines.get(this.nonControlBlockLines.size() - 1);
		}
		return null;
	}

	
	public void setLastNonControlBlockLine(Line lastNonControlBlockLine) {
		if (this.nonControlBlockLines.size() == 0) {
			this.nonControlBlockLines.add(lastNonControlBlockLine);
		} else {
			this.nonControlBlockLines.set(this.nonControlBlockLines.size() - 1, lastNonControlBlockLine);
		}
	}

}
