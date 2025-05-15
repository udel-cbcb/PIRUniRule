package org.proteininformationresource.pirsr.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 16, 2014<br>
 * <br>
 * 
 * The GO line contains all applicable Gene Ontology terms, one per line.
 * 
 * Format: GO accession-number; aspect:term
 */
public interface GOLine extends Line {

	/**
	 * Gets the Gene Ontology accession number.
	 * 
	 * @return Gene Ontology accession number.
	 */
	String getGeneOntologyAccessionNumber();

	/**
	 * Sets the Gene Ontology accession number.
	 * 
	 * @param geneOntologyAccessionNumber
	 *            Gene Ontology accession number.
	 */
	void setGeneOntologyAccessionNumber(String geneOntologyAccessionNumber);

	/**
	 * Gets the Gene Ontology Aspect (C: cellular component; F: molecular
	 * function; P: biological process).
	 * 
	 * @return Gene Ontology Aspect (C: cellular component; F: molecular
	 *         function; P: biological process).
	 */
	String getGeneOntologyAspect();

	/**
	 * Sets the Gene Ontology Aspect (C: cellular component; F: molecular
	 * function; P: biological process).
	 * 
	 * @param geneOntologyAspect
	 *            Gene Ontology Aspect (C: cellular component; F: molecular
	 *            function; P: biological process).
	 */
	void setGeneOntologyAspect(String geneOntologyAspect);

	/**
	 * Gets the Gene Ontology term.
	 * 
	 * @return Gene Ontology term.
	 */
	String getGeneOntologyTerm();

	/**
	 * Sets the Gene Ontology term.
	 * 
	 * @param geneOntologyTerm
	 *            Gene Ontology term.
	 */
	void setGeneOntologyTerm(String geneOntologyTerm);
}
