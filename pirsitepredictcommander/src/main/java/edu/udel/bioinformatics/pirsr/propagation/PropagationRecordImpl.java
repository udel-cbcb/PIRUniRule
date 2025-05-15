package edu.udel.bioinformatics.pirsr.propagation;

import org.proteininformationresource.pirsr.model.ControlStatement;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;
import org.proteininformationresource.pirsr.propagation.PropagationRecord;
import org.proteininformationresource.pirsr.propagation.PropagationStats;
import org.proteininformationresource.pirsr.propagation.annotation.EntryAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.EntryKeywordAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleKeywordAnnotation;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 */
public class PropagationRecordImpl implements PropagationRecord{

	private EntryAnnotation entryAnnotation;
	private RuleAnnotation ruleAnnotation;
	private ControlStatement controlStatement;
	private ExpressionValue controlStatementEvaluation;
	private PropagationStats propagationStatistics;
	private boolean propagatedStatus;
	private boolean matchedStatus;
	private String note;
	private double precision;
	private double confidence;
	
	public PropagationRecordImpl() {
		super();
		
	}
	public EntryAnnotation getEntryAnnotation() {
		return entryAnnotation;
	}
	public void setEntryAnnotation(EntryAnnotation entryAnnotation) {
		this.entryAnnotation = entryAnnotation;
	}
	public RuleAnnotation getRuleAnnotation() {
		return ruleAnnotation;
	}
	public void setRuleAnnotation(RuleAnnotation ruleAnnotation) {
		this.ruleAnnotation = ruleAnnotation;
	}
	public ControlStatement getControlStatement() {
		return controlStatement;
	}
	public void setControlStatement(ControlStatement controlStatement) {
		this.controlStatement = controlStatement;
	}
	public ExpressionValue getControlStatementEvaluation() {
		return controlStatementEvaluation;
	}
	public void setControlStatementEvaluation(ExpressionValue controlStatementEvaluation) {
		this.controlStatementEvaluation = controlStatementEvaluation;
	}
	public PropagationStats getPropagationStats() {
		return propagationStatistics;
	}
	public void setPropagationStatistics(PropagationStats propagationStatistics) {
		this.propagationStatistics = propagationStatistics;
	}
	public boolean isPropagated() {
		return propagatedStatus;
	}
	public void setPropagated(boolean propagatedStatus) {
		this.propagatedStatus = propagatedStatus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public void setControlStatementEvalution(ExpressionValue evaluation) {
		this.controlStatementEvaluation = evaluation;
	}
	
	public boolean isMatched() {
		return this.matchedStatus;
	}
	
	public void setMatched(boolean matched) {
		this.matchedStatus = matched;
	}
	
	
	public String toString() {
		String record = this.getPropagationStats()+"\t";
		if(this.entryAnnotation != null) {
			record += this.entryAnnotation.toString()+ "\t";
		}
		else {
			record += "\t\t\t\t\t\t\t";
		}
		if(this.ruleAnnotation != null) {
			record += this.ruleAnnotation.toString() + "\t";
		}
		else {
			record += "\t\t\t\t\t\t";
		}
		if(this.getControlStatement() != null) {
			record += this.getControlStatement().toString().trim() + "\t";
		}
		else {
			record += "\t";
		}
		
		if(this.getControlStatementEvaluation() != null) {
			record += this.getControlStatementEvaluation() + "\t";
		}
		else {
			record += "\t";
		}
		
		record += this.isMatched() + "\t";
		//record += this.getPropagationStats() + "\t";
		record += this.isPropagated() + "\t";
		if(this.getNote() != null) {
			record += this.getNote()+"";
		}
		else {
			record += "";
		}
		
		return record;
	}

	public String toReport() {
		String report = this.getPropagationStats()+"\t";
		if(this.entryAnnotation != null) {
			report += this.entryAnnotation.toReport()+ "\t";
		}
		else {
			report += "\t\t\t\t";
		}
		if(this.ruleAnnotation != null) {
			report += this.ruleAnnotation.toReport() + "\t";
		}
		else {
			report += "\t\t\t";
		}
		if(this.getControlStatement() != null) {
			report += this.getControlStatement().toString().trim() + "\t";
		}
		else {
			report += "\t";
		}
		
		if(this.getControlStatementEvaluation() != null) {
			report += this.getControlStatementEvaluation() + "\t";
		}
		else {
			report += "\t";
		}
		
		report += this.isMatched() + "\t";
		//report += this.getPropagationStats() + "\t";
		report += this.isPropagated() + "\t";
		if(this.getNote() != null) {
			report += this.getNote()+"";
		}
		else {
			report += "";
		}
		
		return report;
	}
	
	public String toKraken() {
		String kraken = "";
		kraken += this.ruleAnnotation.toKraken()+"\t";
		kraken += this.entryAnnotation.toKraken(this.ruleAnnotation)+"\t";
		kraken += String.format("%.2f", this.precision)+"\t";
		kraken += String.format("%.2f", this.confidence)+"\t";
		kraken += "PIRSR" + "\t";
		kraken += "PREDICTED" + "\t";
		kraken += "MATCH:+:" + this.ruleAnnotation.toKraken()+";";
		return kraken;
	}
	
	
	public double getPrecision() {
		return this.precision;
	}
	
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	
	public double getConfidence() {
		return this.confidence;
	}
	
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	
}
