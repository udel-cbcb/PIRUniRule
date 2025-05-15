package edu.udel.bioinformatics.pirsr;

import org.proteininformationresource.pirsr.model.InternalCommentsLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 */
public class InternalCommentsLineImpl implements InternalCommentsLine{
	String comments = "";
	
	public InternalCommentsLineImpl() {
		super();
	}
	
	
	public String getComments() {
		return this.comments;
	}

	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String toString() {
		return "** "+this.comments+"\n";
	}
}
