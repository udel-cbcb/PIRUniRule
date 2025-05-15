package edu.udel.bioinformatics.pirrule;

import org.proteininformationresource.pirrule.model.CommentsLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: June 2, 2014<br>
 * <br>
 * 
 * Comments concerning the rule, which should be made visible to the public.
 */
public class CommentsLineImpl implements CommentsLine {

	private String commentText;

	public CommentsLineImpl() {
		super();
	}

	public CommentsLineImpl(String commentText) {
		super();
		this.commentText = commentText;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public String toString() {
		return "Comments: " + this.commentText + "\n";
	}

}
