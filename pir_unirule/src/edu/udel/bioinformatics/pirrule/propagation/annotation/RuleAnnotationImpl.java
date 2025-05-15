package edu.udel.bioinformatics.pirrule.propagation.annotation;

import org.proteininformationresource.pirrule.propagation.annotation.RuleAnnotation;

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

	@Override
	public String getRuleAC() {
		return this.ruleAC;
	}

	@Override
	public void setRuleAC(String ruleAC) {
		this.ruleAC = ruleAC;
	}

	@Override
	public String getTemplateAC() {
		return this.templateAC;
	}

	@Override
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

	@Override
	public String toReport() {
		String report = "";
		report += "\t";
		report += "\t";
		report += "?\t";
		report += "?";
		return report;
	}

	@Override
	public String toKraken() {
		String kraken = this.ruleAC;
		return kraken;
	}
}
