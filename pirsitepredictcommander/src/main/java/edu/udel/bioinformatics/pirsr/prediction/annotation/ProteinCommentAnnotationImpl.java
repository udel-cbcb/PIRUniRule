package edu.udel.bioinformatics.pirsr.prediction.annotation;

import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.RuleCommentType;
import org.proteininformationresource.pirsr.prediction.Prediction;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinCommentAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleCommentAnnotation;
import org.proteininformationresource.pirsr.uniprot.model.EntryComment;
import org.proteininformationresource.pirsr.uniprot.model.EntryCommentType;

import edu.udel.bioinformatics.pirsr.prediction.PredictionImpl;
import edu.udel.bioinformatics.pirsr.propagation.annotation.AnnotationType;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 28, 2015<br>
 * <br>
 * 
 * The implementation of <code>ProteinCommentAnnotation</code>.
 */
public class ProteinCommentAnnotationImpl extends ProteinAnnotationImpl implements ProteinCommentAnnotation {

	private CCLine comment;
	private RuleCommentType commentType;
	
	public ProteinCommentAnnotationImpl(String proteinId, CCLine comment) {
		super(proteinId);
		this.comment = comment;
		//if(comment != null) {
			this.commentType = comment.getTopic();
		//}
	}

	public CCLine getComment() {
		return this.comment;
	}

	public void setComment(CCLine comment) {
		this.comment = comment;
	}

	public String getCommentDescription() {
		if(this.comment != null) {
			return this.comment.getCommentDescription();
		}
		else {
			return "";
		}
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
			record += "\t";
		}
//		if (this.commentType != null) {
//
//			record += "COMMENT_" +this.commentType+"\t";
//		} else {
//			record += "\t";
//		}
//		record += this.getCommentDescription() + "\t";
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
//		if (this.commentType != null) {
//			report += this.commentType +"\t";
//		} else {
//			report += "\t";
//		}
//		report += this.getCommentDescription() + "\t";
//		report += "?\t";
//		report += "?";
//		return report;
		return toString();
	}
	
	
	public String toKraken() {
		String kraken = "";
		//RuleCommentAnnotation ruleCommentAnnotation = (RuleCommentAnnotation) ruleAnnotation;
		//this.commentType =((RuleCommentAnnotationImpl)ruleAnnotation).getCommentType();
		
		//this.commentType = this.comment.getTopic();
		if (this.commentType.equals(RuleCommentType.COFACTOR)) {
			//System.out.println(this.commentType+"??" + this.comment);
			kraken += AnnotationType.COMMENT_COFACTOR + "\t";
		} else if (this.commentType.equals(RuleCommentType.FUNCTION)) {
			kraken += AnnotationType.COMMENT_FUNCTION + "\t";
		} else if (this.commentType.equals(RuleCommentType.PTM)) {
			kraken += AnnotationType.COMMENT_PTM + "\t";
		} else if (this.commentType.equals(RuleCommentType.SIMILARITY)) {
			kraken += AnnotationType.COMMENT_SIMILARITY + "\t";
		} else if (this.commentType.equals(RuleCommentType.SUBCELLULAR_LOCATION)) {
			kraken += AnnotationType.COMMENT_SUBCELLULAR_LOCATION + "\t";
		} else if (this.commentType.equals(RuleCommentType.SUBUNIT)) {
			kraken += AnnotationType.COMMENT_SUBUNIT + "\t";
		}
		String description = this.getCommentDescription().replaceAll("\\.$", "");
		description = description.replaceAll("\\;$", "");
		kraken += description +"\t";

		kraken += this.getProteinId() + "\t";
		kraken += "?\t";
		kraken += "?";
		return kraken;
	}

	public RuleCommentType getCommentType() {
		return this.commentType;
	}

	public Prediction getPrediction() {
		return new PredictionImpl("Comment", this.commentType.name(), this.comment.getCommentDescription().replaceAll("\\.$", ""));
	}

	public String toXML() {
		StringBuffer xml = new StringBuffer();
		//xml.append("\t\t <prediction>\n");
		xml.append("\t\t  <comment type=\""+this.commentType.name()+"\">\n");
		xml.append("\t\t   <description>\n");
		xml.append("\t\t     "+this.comment.getCommentDescription().replaceAll("\\.$", "")+"\n");
		xml.append("\t\t   </description>\n");
		xml.append("\t\t  </comment>\n");
		//xml.append("\t\t </prediction>\n");
		return xml.toString();
	}
	
	
}
