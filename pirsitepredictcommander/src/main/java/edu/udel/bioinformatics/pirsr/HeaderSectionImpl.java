package edu.udel.bioinformatics.pirsr;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.model.ACLine;
import org.proteininformationresource.pirsr.model.DCLine;
import org.proteininformationresource.pirsr.model.HeaderSection;
import org.proteininformationresource.pirsr.model.HeaderSectionVisitor;
import org.proteininformationresource.pirsr.model.Line;
import org.proteininformationresource.pirsr.model.TRLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br><br>
 * 
 * 
 * The Header section of this Site Rule, which contains technical information.
 */
public class HeaderSectionImpl implements HeaderSection {

	private ACLine acLine;
	private DCLine dcLine;
	private TRLine trLine;
	
	private List<Line> nonControlBlockLines;
	private List<Line> lines;
	
	public HeaderSectionImpl() {
		super();
		lines = new ArrayList<Line>();
		nonControlBlockLines = new ArrayList<Line>();
	}



	
	public List<Line> getLines() {
		return this.lines;
	}

	
	
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}



	
	
	

	
	public ACLine getACLine() {
		for(Line line: this.lines) {
			if(line instanceof ACLine) {
				return (ACLine)line;
			}
		}
		return this.acLine;
	}
	
	
	public void setACLine(ACLine acLine) {
		this.acLine = acLine;
	}

	
	public DCLine getDCLine() {
		for(Line line: this.lines) {
			if(line instanceof DCLine) {
				return (DCLine)line;
			}
		}
		return this.dcLine;
	}

	
	public void setDCLine(DCLine dcLine) {
		this.dcLine = dcLine;
	}
	
	
	public TRLine getTRLine() {
		for(Line line: this.lines) {
			if(line instanceof TRLine) {
				return (TRLine)line;
			}
		}
		return this.trLine;
	}

	
	public void setTRLine(TRLine trLine) {
		this.trLine = trLine;
	}
	
	

	
	
	public void accept(HeaderSectionVisitor visitor) {
		visitor.visit(lines);
	}

	
	public boolean isComplete() {
		if(this.acLine != null && this.dcLine != null && this.trLine != null) {
			return true;
		}
		else {
			return false;
		}
	}



	
	public Line getLastLine() {
		if(this.lines != null && this.lines.size() > 0) {
			return this.lines.get(this.lines.size() - 1);
		}
		return null;
	}



	
	public void setLastLine(Line lastLine) {
		this.lines.set(this.lines.size()-1, lastLine);
	}



	
	public List<Line> getNonControlBlockLines() {
		return this.nonControlBlockLines;
	}



	
	public void setNonControlBlockLines(List<Line> nonControlBlockLines) {
		this.nonControlBlockLines = nonControlBlockLines;
	}



	
	public Line getLastNonControlBlockLine() {
		if(this.nonControlBlockLines != null && this.nonControlBlockLines.size() > 0) {
			return this.nonControlBlockLines.get(this.lines.size() - 1);
		}
		return null;
	}



	
	public void setLastNonControlBlockLine(Line lastNonControlBlockLine) {
		if(this.nonControlBlockLines.size() == 0) {
			this.nonControlBlockLines.add(lastNonControlBlockLine);
		}
		else {
			this.nonControlBlockLines.set(this.nonControlBlockLines.size()-1, lastNonControlBlockLine);
		}
	}

}
