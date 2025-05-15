package edu.udel.bioinformatics.pirrule;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirrule.model.ControlStatement;
import org.proteininformationresource.pirrule.model.Line;
import org.proteininformationresource.pirrule.model.expression.Expression;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 *
 */
public class ControlStatementImpl implements ControlStatement {

	private Expression expression;
	private List<Line> applicableLines;

	public ControlStatementImpl() {
		super();
		this.applicableLines = new ArrayList<Line>();
	}

	@Override
	public Expression getExpression() {
		return this.expression;
	}

	@Override
	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public List<Line> getApplicableLines() {
		return this.applicableLines;
	}

	@Override
	public void addLine(Line line) {
		if (this.applicableLines == null) {
			this.applicableLines = new ArrayList<Line>();
		}
		this.applicableLines.add(line);
	}

	@Override
	public Line getLastApplicableLine() {
		if (this.applicableLines != null /*&& this.applicableLines.size() > 1*/) {
			return this.applicableLines.get(this.applicableLines.size() - 1);
		}
		return null;
	}

	@Override
	public void setLastApplicableLine(Line lastApplicableLine) {
		if (this.applicableLines.size() > 0) {
			this.applicableLines.set(this.applicableLines.size() - 1, lastApplicableLine);
		} else {
			this.applicableLines.add(lastApplicableLine);
		}

	}
	
//	public String toString() {
//		if(this instanceof CaseStatement) {
//			return ((CaseStatement)this).toString();
//		}
//		return "";
//		
//	}
}
