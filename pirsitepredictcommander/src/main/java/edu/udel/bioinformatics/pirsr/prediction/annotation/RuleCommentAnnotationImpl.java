package edu.udel.bioinformatics.pirsr.prediction.annotation;

import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.Line;
import org.proteininformationresource.pirsr.model.RuleCommentType;
import org.proteininformationresource.pirsr.prediction.annotation.RuleCommentAnnotation;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 */
public class RuleCommentAnnotationImpl extends RuleAnnotationImpl implements RuleCommentAnnotation {

	private CCLine comment;
	private RuleCommentType commentType;

	public RuleCommentAnnotationImpl(String ruleAC, String templateAC, CCLine comment) {
		super(ruleAC, templateAC);
		this.comment = comment;
		if (comment != null) {
			this.commentType = comment.getTopic();
		}

	}

	public CCLine getComment() {
		return comment;
	}

	public void setComment(CCLine comment) {
		this.comment = comment;
	}

	

	
	public String getCommentDescription() {
		String record = "";
		if (this.comment != null) {
//			for (String description : this.comment.getDescription()) {
//				record += description + " ";
//			}
			for(int i= 0; i < this.comment.getDescription().size(); i++) {
				if(i == 0) {
					record += this.comment.getDescription().get(i).trim();
				}
				else {
					if(record.endsWith("-")) {
						record += this.comment.getDescription().get(i).trim();
					}
					else {
						record += " "+this.comment.getDescription().get(i).trim();
					}
				}
			}
		}
		return record.trim();
	}

	public String toString() {
		String record = "";
		record += this.getRuleAC() + "\t";
		record += this.getTemplateAC() + "\t";
		if (this.commentType != null) {
			record += this.commentType + "\t";
		} else {
			record += "\t";
		}
		record += this.getCommentDescription() + "\t";
		record += "?\t?";
		return record;
	}

	public String toReport() {
		String report = "";
		if (this.commentType != null) {
			report += this.commentType + "\t";
		} else {
			report += "\t";
		}
		report += this.getCommentDescription()+"\t";
		report += "?\t?";
		return report;
	}

	
	public RuleCommentType getCommentType() {
		return this.commentType;
	}
}
