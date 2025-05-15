package edu.udel.bioinformatics.pirsr;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.model.ControlBlock;
import org.proteininformationresource.pirsr.model.ControlStatement;
import org.proteininformationresource.pirsr.model.Line;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * Control statements and their associated block of lines.
 */
public class ControlBlockImpl implements ControlBlock {

	private List<ControlStatement> controlStatements;

	public ControlBlockImpl() {
		super();
		controlStatements = new ArrayList<ControlStatement>();
	}

	public ControlBlockImpl(List<ControlStatement> controlStatements) {
		super();
		this.controlStatements = controlStatements;
	}

	
	public List<ControlStatement> getControlStatements() {
		return this.controlStatements;
	}

	
	public void setControlStatements(List<ControlStatement> controlStatements) {
		this.controlStatements = controlStatements;
	}

	
	public List<Line> getLines() {
		List<Line> lines = new ArrayList<Line>();
		//lines.addAll(this.controlStatements);
		for(ControlStatement controlStatement : this.controlStatements) {
			lines.add(controlStatement);
			lines.addAll(controlStatement.getApplicableLines());
		}
		return lines;
	}

	
	public void setLines(List<Line> lines) {
		List<ControlStatement> controlLines = new ArrayList<ControlStatement>();
		for (int i = 0; i < lines.size(); i++) {
			controlLines.add((ControlStatement) lines.get(i));
		}
		this.controlStatements = controlLines;
	}

	public void addLine(Line line) {
		if (this.controlStatements == null) {
			this.controlStatements = new ArrayList<ControlStatement>();
		}
		this.controlStatements.add((ControlStatement) line);
	}

	public String toString() {
		String str = "";
		for (ControlStatement controlStatement : this.controlStatements) {
			// System.out.print(controlStatement);
			str += controlStatement;
			List<Line> applicableLines = controlStatement.getApplicableLines();
			if (applicableLines != null) {
				for (Line line : applicableLines) {
					// System.out.print(line);
					str += line;
				}
			}
		}
		return str;
	}
}
