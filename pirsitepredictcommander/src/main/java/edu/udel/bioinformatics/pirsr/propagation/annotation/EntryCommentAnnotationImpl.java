package edu.udel.bioinformatics.pirsr.propagation.annotation;

import java.util.List;

import org.proteininformationresource.pirsr.propagation.annotation.EntryCommentAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleCommentAnnotation;
import org.proteininformationresource.pirsr.uniprot.model.EntryComment;
import org.proteininformationresource.pirsr.uniprot.model.EntryCommentType;
import org.proteininformationresource.pirsr.uniprot.model.EntryType;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 */
public class EntryCommentAnnotationImpl extends EntryAnnotationImpl implements EntryCommentAnnotation {

	private EntryComment comment;
	private EntryCommentType commentType;

	public EntryCommentAnnotationImpl(String entryAC, String entryID, EntryType entryType, EntryComment comment) {
		super(entryAC, entryID, entryType);
		this.comment = comment;
		if (comment != null) {
			this.commentType = comment.getCommentType();
		}
		//System.out.println(comment);
	}

	public EntryComment getComment() {
		return comment;
	}

	public void setComment(EntryComment comment) {
		this.comment = comment;
	}

	public EntryCommentType getCommentType() {
		return commentType;
	}

	public void setCommentType(EntryCommentType commentType) {
		this.commentType = commentType;
	}

	
	public String getCommentDescription() {
		if(this.comment != null) {
			//System.out.println(comment);
			return this.comment.getCommentDescription();
		}
		else {
			return "";
		}
	}

	

	public String toString() {
		String record = "";
		record += super.getEntryAC() + "\t";
		record += super.getEntryID() + "\t";
		record += super.getEntryType() + "\t";
		if (this.commentType != null) {

			record += this.commentType+"\t";
		} else {
			record += "\t";
		}
		record += this.getCommentDescription() + "\t";
		record += "?\t";
		record += "?";
		return record;
	}

	public String toReport() {
		String report = "";
		if (this.commentType != null) {
			report += this.commentType +"\t";
		} else {
			report += "\t";
		}
		report += this.getCommentDescription() + "\t";
		report += "?\t";
		report += "?";
		return report;
	}

	public String toKraken(RuleAnnotation ruleAnnotation) {
		String kraken = "";

		if (this.commentType.equals(EntryCommentType.COFACTOR)) {
			kraken += AnnotationType.COMMENT_COFACTOR + "\t";
		} else if (this.commentType.equals(EntryCommentType.FUNCTION)) {
			kraken += AnnotationType.COMMENT_FUNCTION + "\t";
		} else if (this.commentType.equals(EntryCommentType.PTM)) {
			kraken += AnnotationType.COMMENT_PTM + "\t";
		} else if (this.commentType.equals(EntryCommentType.SIMILARITY)) {
			kraken += AnnotationType.COMMENT_SIMILARITY + "\t";
		} else if (this.commentType.equals(EntryCommentType.SUBCELLULAR_LOCATION)) {
			kraken += AnnotationType.COMMENT_SUBCELLULAR_LOCATION + "\t";
		} else if (this.commentType.equals(EntryCommentType.SUBUNIT)) {
			kraken += AnnotationType.COMMENT_SUBUNIT + "\t";
		}
		//kraken += this.getCommentDescription() + "\t";
		String description = ((RuleCommentAnnotation)ruleAnnotation).getCommentDescription().replaceAll("\\.$", "");
		description = description.replaceAll("\\;$", "");
		kraken += description +"\t";

		kraken += this.getEntryAC() + "\t";
		kraken += "?\t";
		kraken += "?";
		return kraken;
	}

	
}
