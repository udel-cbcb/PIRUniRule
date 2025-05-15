package edu.udel.bioinformatics.pirrule;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirrule.model.Block;
import org.proteininformationresource.pirrule.model.Line;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * 
 */
public class BlockImpl implements Block {

	private List<Line> lines;

	@Override
	public List<Line> getLines() {
		return this.lines;

	}

	public BlockImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BlockImpl(List<Line> lines) {
		super();
		this.lines = lines;
	}

	@Override
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	@Override
	public void addLine(Line line) {
		if (this.lines == null) {
			lines = new ArrayList<Line>();
		}
		lines.add(line);
	}

}
