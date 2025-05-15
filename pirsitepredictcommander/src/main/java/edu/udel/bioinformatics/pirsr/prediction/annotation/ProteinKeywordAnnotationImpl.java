package edu.udel.bioinformatics.pirsr.prediction.annotation;

import org.proteininformationresource.pirsr.model.KWLine;
import org.proteininformationresource.pirsr.prediction.Prediction;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinKeywordAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleKeywordAnnotation;
import org.proteininformationresource.pirsr.uniprot.model.EntryKeyword;

import edu.udel.bioinformatics.pirsr.prediction.PredictionImpl;
import edu.udel.bioinformatics.pirsr.propagation.annotation.AnnotationType;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 28, 2015<br>
 * <br>
 * 
 * The implementation of <code>ProteinKeywordAnnotation</code>.
 */
public class ProteinKeywordAnnotationImpl extends ProteinAnnotationImpl implements ProteinKeywordAnnotation {

	private KWLine keyword;
	
	public ProteinKeywordAnnotationImpl(String proteinId, KWLine keyword) {
		super(proteinId);
		this.keyword = keyword;
	}
	public ProteinKeywordAnnotationImpl(String proteinId) {
		super(proteinId);
	}

	public KWLine getKeyword() {
		return this.keyword;
	}

	public void setKeyword(KWLine keyword) {
		this.keyword = keyword;
	}

	public String toString() {
		String record = "";
		//Query
		record += super.getProteinId()+ "\t";
		//From
		record += "?\t";
		//To
		record += "?\t";
		Prediction prediction = this.getPrediction();
		if(prediction.getType() != null) {
			record += prediction.getType()+"\t";
		}
		else {
			record += ".\t";
		}
		
		if(prediction.getCategory() != null) {
			record += prediction.getCategory()+"\t";
		}
		else {
			record += ".\t";
		}
		if(prediction.getDescription() != null) {
			record += prediction.getDescription()+"\t";
		}
		else {
			record += ".\t";
		}
//		record += AnnotationType.KEYWORD +"\t";
//		if(this.keyword != null) {
//			record += this.keyword.getKeyword()+"\t";
//		}
//		else {
//			record += "\t";
//		}
		if(getNucleotideSequenceId() != null && getNucleotideSequenceId().length() > 0) {
			record += getNucleotideSequenceId()+"\t";
		}
		else {
			record += ".\t";
		}
		if(getNucleotideORFStart() > 0) {
			record += getNucleotideORFStart() + "\t";
		}
		else {
			record += ".\t";
		}
		if(getNucleotideORFEnd() > 0) {
			record += getNucleotideORFEnd() + "\t";
		}
		else {
			record += ".\t";
		}
		if(getNucleotideStrand() != null && getNucleotideStrand().length() > 0) {
			record += getNucleotideStrand() +"\t";
		}
		else {
			record += ".\t";
		}
		return record;
	}
	

	public String toReport() {
//		String report = "";
//		report += AnnotationType.KEYWORD +"\t";
//		if(this.keyword != null) {
//			report += this.keyword.getKeyword()+"\t";
//		}
//		else {
//			report += "\t";
//		}
//		report += "?\t";
//		report += "?";
//		
//		return report;
		return toString();
	}
	
	public String toKraken() {
		String kraken = "";
		
		kraken += AnnotationType.KEYWORD +"\t";
		kraken += this.keyword.getKeyword().replaceAll("\\.", "") +"\t";
		
		kraken += this.getProteinId() +"\t";
		kraken += "?\t";
		kraken += "?";
		//System.out.println(kraken);
		return kraken;
	}
	public Prediction getPrediction() {
		return new PredictionImpl("Keyword", null, this.keyword.getKeyword());
	}
	
	public String toXML() {
		StringBuffer xml = new StringBuffer();
		//xml.append("\t\t <prediction>\n");
		xml.append("\t\t   <keyword>"+this.keyword.getKeyword()+"</keyword>\n");
		//xml.append("\t\t </prediction>\n");
		return xml.toString();
	}
}
