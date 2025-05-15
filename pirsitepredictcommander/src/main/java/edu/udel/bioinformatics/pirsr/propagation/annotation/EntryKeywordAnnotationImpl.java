package edu.udel.bioinformatics.pirsr.propagation.annotation;

import org.proteininformationresource.pirsr.propagation.annotation.EntryKeywordAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleKeywordAnnotation;
import org.proteininformationresource.pirsr.uniprot.model.EntryKeyword;
import org.proteininformationresource.pirsr.uniprot.model.EntryType;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 */

public class EntryKeywordAnnotationImpl extends EntryAnnotationImpl implements EntryKeywordAnnotation {
	
	private EntryKeyword keyword;
	
	public EntryKeywordAnnotationImpl(String entryAC, String entryID, EntryType entryType, EntryKeyword keyword) {
		super(entryAC, entryID, entryType);
		this.keyword = keyword;
	}
	
	public EntryKeyword getKeyword() {
		return keyword;
	}
	public void setKeyword(EntryKeyword keyword) {
		this.keyword = keyword;
	}
	
	public String toString() {
		String record = "";
		record += super.getEntryAC() + "\t";
		record += super.getEntryID() + "\t";
		record += super.getEntryType()+"\t";
		
		record += AnnotationType.KEYWORD +"\t";
		if(this.keyword != null) {
			record += this.keyword+"\t";
		}
		else {
			record += "\t";
		}
		record += "?\t";
		record += "?";
		//record += this.keyword.getValue();
		return record;
	}
	
	public String toReport() {
		String report = "";
		report += AnnotationType.KEYWORD +"\t";
		if(this.keyword != null) {
			report += this.keyword.toReport()+"\t";
		}
		else {
			report += "\t";
		}
		report += "?\t";
		report += "?";
		
		return report;
	}
	
	public String toKraken(RuleAnnotation ruleAnnotation) {
		String kraken = "";
		kraken += AnnotationType.KEYWORD +"\t";
		kraken += ((RuleKeywordAnnotation)ruleAnnotation).getKeyword().getKeyword().replaceAll("\\.", "") +"\t";
		
		kraken += this.getEntryAC() +"\t";
		kraken += "?\t";
		kraken += "?";
		return kraken;
	}
}
