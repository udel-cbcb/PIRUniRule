package edu.udel.bioinformatics.pirsr.prediction;

import org.proteininformationresource.pirsr.model.ControlStatement;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;
import org.proteininformationresource.pirsr.prediction.PredictionRecord;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleAnnotation;
/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 28, 2015<br>
 * <br>
 * 
 * The implementation of <code>PredictionRecord</code>.
 */
public class PredictionRecordImpl implements PredictionRecord {

	private ProteinAnnotation proteinAnnotation;
	private RuleAnnotation ruleAnnotation;
	private ControlStatement controlStatement;
	private ExpressionValue controlStatementEvaluation;
	private boolean predicatedStatus;
	private boolean matchedStatus;
	private String note;
	
	

	public ProteinAnnotation getProteinAnnotation() {
		return this.proteinAnnotation;
	}

	public void setProteinAnnotation(ProteinAnnotation proteinAnnotation) {
		this.proteinAnnotation = proteinAnnotation;
	}

	
	public ControlStatement getControlStatement() {
		return this.controlStatement;
	}

	public void setControlStatement(ControlStatement controlStatement) {
		this.controlStatement = controlStatement;
	}

	public ExpressionValue getControlStatementEvaluation() {
		return this.controlStatementEvaluation;
	}

	public void setControlStatementEvalution(ExpressionValue evaluation) {
		this.controlStatementEvaluation = evaluation;
	}

	public boolean isPredicted() {
		 return this.predicatedStatus;
	}

	public void setPredicted(boolean predicted) {
		this.predicatedStatus = predicted;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isMatched() {
		return this.matchedStatus;
	}

	public void setMatched(boolean matched) {
		this.matchedStatus = matched;
	}

	public String toReport() {
		String report = "";
		if(this.ruleAnnotation != null) {
			report += this.ruleAnnotation.getRuleAC() + "\t";
		}
		else {
			report += "\t";
		}
		if(this.proteinAnnotation != null) {
			report += this.proteinAnnotation.toString()+ "\t";
		}
		else {
			report += "\t\t\t\t\t\t\t";
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
		report += this.isPredicted()+ "\t";
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
		if(this.proteinAnnotation == null) {
			kraken += "\t";
		}
		else {
			kraken += this.proteinAnnotation.toKraken()+"\t";
		}
//		kraken += String.format("%.2f", this.precision)+"\t";
//		kraken += String.format("%.2f", this.confidence)+"\t";
		kraken += "PIRSR" + "\t";
		kraken += "PREDICTED" + "\t";
		kraken += "MATCH:+:" + this.ruleAnnotation.toKraken()+";";
		return kraken;
	}

	public RuleAnnotation getRuleAnnotation() {
		return this.ruleAnnotation;
	}

	public void setRuleAnnotation(RuleAnnotation ruleAnnotation) {
		this.ruleAnnotation = ruleAnnotation;
	}

	public String toString() {
		String record = "";
		if(this.ruleAnnotation != null) {
			record += this.ruleAnnotation.getRuleAC() + "\t";
		}
		else {
			record += "\t";
		}
		if(this.proteinAnnotation != null) {
			record += this.proteinAnnotation.toString()+ "\t";
		}
		else {
			record += "\t\t\t\t\t\t\t";
		}
		
//		if(this.getControlStatement() != null) {
//			record += this.getControlStatement().toString().trim() + "\t";
//		}
//		else {
//			record += "\t";
//		}
//		
//		if(this.getControlStatementEvaluation() != null) {
//			record += this.getControlStatementEvaluation() + "\t";
//		}
//		else {
//			record += "\t";
//		}
//		
//		record += this.isMatched() + "\t";
//		//record += this.getPropagationStats() + "\t";
//		record += this.isPredicted() + "\t";
//		if(this.getNote() != null) {
//			record += this.getNote()+"";
//		}
//		else {
//			record += "";
//		}
		
		return record;
	}
}
