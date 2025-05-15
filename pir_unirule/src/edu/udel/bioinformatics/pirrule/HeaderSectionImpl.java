package edu.udel.bioinformatics.pirrule;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirrule.model.ACLine;
import org.proteininformationresource.pirrule.model.DCLine;
import org.proteininformationresource.pirrule.model.HeaderSection;
import org.proteininformationresource.pirrule.model.HeaderSectionVisitor;
import org.proteininformationresource.pirrule.model.Line;
import org.proteininformationresource.pirrule.model.TRLine;

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



	@Override
	public List<Line> getLines() {
		return this.lines;
	}

	
	@Override
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}



	
	
	

	@Override
	public ACLine getACLine() {
		for(Line line: this.lines) {
			if(line instanceof ACLine) {
				return (ACLine)line;
			}
		}
		return this.acLine;
	}
	
	@Override
	public void setACLine(ACLine acLine) {
		this.acLine = acLine;
	}

	@Override
	public DCLine getDCLine() {
		for(Line line: this.lines) {
			if(line instanceof DCLine) {
				return (DCLine)line;
			}
		}
		return this.dcLine;
	}

	@Override
	public void setDCLine(DCLine dcLine) {
		this.dcLine = dcLine;
	}
	
	@Override
	public TRLine getTRLine() {
		for(Line line: this.lines) {
			if(line instanceof TRLine) {
				return (TRLine)line;
			}
		}
		return this.trLine;
	}

	@Override
	public void setTRLine(TRLine trLine) {
		this.trLine = trLine;
	}
	
	

	
	@Override
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



	@Override
	public Line getLastLine() {
		if(this.lines != null && this.lines.size() > 0) {
			return this.lines.get(this.lines.size() - 1);
		}
		return null;
	}



	@Override
	public void setLastLine(Line lastLine) {
		this.lines.set(this.lines.size()-1, lastLine);
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
		if(this.nonControlBlockLines != null && this.nonControlBlockLines.size() > 0) {
			return this.nonControlBlockLines.get(this.lines.size() - 1);
		}
		return null;
	}



	@Override
	public void setLastNonControlBlockLine(Line lastNonControlBlockLine) {
		if(this.nonControlBlockLines.size() == 0) {
			this.nonControlBlockLines.add(lastNonControlBlockLine);
		}
		else {
			this.nonControlBlockLines.set(this.nonControlBlockLines.size()-1, lastNonControlBlockLine);
		}
	}

}
