package edu.udel.bioinformatics.pirrule;

import org.proteininformationresource.pirrule.model.GOLine;

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

	@Override
	public String getGeneOntologyAccessionNumber() {
		return this.geneOntologyAccessionNumber;
	}

	@Override
	public void setGeneOntologyAccessionNumber(String geneOntologyAccessionNumber) {
		this.geneOntologyAccessionNumber = geneOntologyAccessionNumber;
	}

	@Override
	public String getGeneOntologyAspect() {
		return this.geneOntologyAspect;
	}

	@Override
	public void setGeneOntologyAspect(String geneOntologyAspect) {
		this.geneOntologyAspect = geneOntologyAspect;
	}

	@Override
	public String getGeneOntologyTerm() {
		return this.geneOntologyTerm;
	}

	@Override
	public void setGeneOntologyTerm(String geneOntologyTerm) {
		this.geneOntologyTerm = geneOntologyTerm;
	}

	public String toString() {
		String str = "";
		str += "GO   " + this.geneOntologyAccessionNumber+"; " + this.geneOntologyAspect+":"+ this.geneOntologyTerm+"\n";
		return str;
	}
}
