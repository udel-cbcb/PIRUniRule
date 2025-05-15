package edu.udel.bioinformatics.pirsr.prediction.annotation;

import org.proteininformationresource.pirsr.prediction.annotation.RuleAnnotation;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 17, 2014<br>
 * <br>
 * 
 */
public class RuleAnnotationImpl implements RuleAnnotation {
	private String ruleAC;
	private String templateAC;
	
	

	public RuleAnnotationImpl(String ruleAC, String templateAC) {
		super();
		this.ruleAC = ruleAC;
		this.templateAC = templateAC;
	}

	
	public String getRuleAC() {
		return this.ruleAC;
	}

	
	public void setRuleAC(String ruleAC) {
		this.ruleAC = ruleAC;
	}

	
	public String getTemplateAC() {
		return this.templateAC;
	}

	
	public void setTemplateAC(String templateAC) {
		this.templateAC = templateAC;
	}

	public String toString() {
		String record = "";
		record += this.ruleAC +"\t";
		record += this.templateAC + "\t";
		record += "\t";
		record += "\t";
		record += "?\t";
		record += "?";
		return record;
	}

	
	public String toReport() {
		String report = "";
		report += "\t";
		report += "\t";
		report += "?\t";
		report += "?";
		return report;
	}

	
	public String toKraken() {
		String kraken = this.ruleAC;
		return kraken;
	}
}
