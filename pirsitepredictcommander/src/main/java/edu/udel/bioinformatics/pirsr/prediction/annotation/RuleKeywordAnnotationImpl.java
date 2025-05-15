package edu.udel.bioinformatics.pirsr.prediction.annotation;

import org.proteininformationresource.pirsr.model.KWLine;
import org.proteininformationresource.pirsr.prediction.annotation.RuleKeywordAnnotation;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 */
public class RuleKeywordAnnotationImpl extends RuleAnnotationImpl implements RuleKeywordAnnotation {

	private KWLine keyword;
	
	public RuleKeywordAnnotationImpl(String ruleAC, String templateAC, KWLine keyword) {
		super(ruleAC, templateAC);
		this.keyword = keyword;
	}

	

	public KWLine getKeyword() {
		return keyword;
	}

	public void setKeyword(KWLine keyword) {
		this.keyword = keyword;
	}
	
	public String toString() {
		String record = "";
		record += this.getRuleAC() + "\t";
		record += this.getTemplateAC() + "\t";
		record += AnnotationType.KEYWORD+"\t";
		if(this.getKeyword() != null) {
			record += this.getKeyword().getKeyword() + "\t";
		}
		else {
			record += "\t";
		}
		record +="?\t?";
		return record;
	}
	
	public String toReport() {
		String report = "";
		report += AnnotationType.KEYWORD+"\t";
		//report += "\t";
		if(this.getKeyword() != null) {
			report += this.getKeyword().getKeyword() + "\t";
		}
		else {
			report += "\t";
		}
		report +="?\t?";
		return report;
	}
	

}
