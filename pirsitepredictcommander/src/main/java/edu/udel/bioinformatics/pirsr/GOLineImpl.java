package edu.udel.bioinformatics.pirsr;

import org.proteininformationresource.pirsr.model.GOLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * Gene ontology information line.
 */
public class GOLineImpl implements GOLine {
	private String geneOntologyAccessionNumber;
	private String geneOntologyAspect;
	private String geneOntologyTerm;

	public GOLineImpl() {
		super();
	}

	public GOLineImpl(String geneOntologyAccessionNumber, String geneOntologyAspect, String geneOntologyTerm) {
		this.geneOntologyAccessionNumber = geneOntologyAccessionNumber;
		this.geneOntologyAspect = geneOntologyAspect;
		this.geneOntologyTerm = geneOntologyTerm;
	}

	
	public String getGeneOntologyAccessionNumber() {
		return this.geneOntologyAccessionNumber;
	}

	
	public void setGeneOntologyAccessionNumber(String geneOntologyAccessionNumber) {
		this.geneOntologyAccessionNumber = geneOntologyAccessionNumber;
	}

	
	public String getGeneOntologyAspect() {
		return this.geneOntologyAspect;
	}

	
	public void setGeneOntologyAspect(String geneOntologyAspect) {
		this.geneOntologyAspect = geneOntologyAspect;
	}

	
	public String getGeneOntologyTerm() {
		return this.geneOntologyTerm;
	}

	
	public void setGeneOntologyTerm(String geneOntologyTerm) {
		this.geneOntologyTerm = geneOntologyTerm;
	}

	public String toString() {
		String str = "";
		str += "GO   " + this.geneOntologyAccessionNumber+"; " + this.geneOntologyAspect+":"+ this.geneOntologyTerm+"\n";
		return str;
	}
}
