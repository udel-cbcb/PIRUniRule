package org.proteininformationresource.pirsr.prediction.annotation;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 27, 2015<br>
 * <br>
 * 
 * Protein annotation information.
 */
public interface ProteinAnnotation {

	/**
	 * Gets the protein identifier.
	 * @return the protein identifier.
	 */
	String getProteinId();
	
	/**
	 * Sets the protein identifier.
	 * @param proteinId the protein identifier.
	 */
	void setProteinid(String proteinId);
	
	/**
	 * Gets the protein annotation in report format.
	 * @return the protein annotation in report format.
	 */
	String toReport();
	
	/**
	 * Gets the protein annotation in Kraken format.
	 * 
	 * @return the protein annotation in Kraken format.
	 */
	String toKraken();
	
	/**
	 * Gets the start position in the nucleotide sequence where the protein is derived from.
	 * @return the start position in the nucleotide sequence where the protein is derived from.
	 */
	int getNucleotideORFStart();
	
	/**
	 * Sets the start position in the nucleotide sequence where the protein is derived from.
	 * @param start the start position in the nucleotide sequence where the protein is derived from.
	 */
	void setNucleotideORFStart(int nucleotideORFStart);
	
	/**
	 * Gets the end position in the nucleotide sequence where the protein is derived from.
	 * @return the end position in the nucleotide sequence where the protein is derived from.
	 */
	int getNucleotideORFEnd();
	
	/**
	 * Sets the end position in the nucleotide sequence where the protein is derived from.
	 * @param end the end position in the nucleotide sequence where the protein is derived from.
	 */
	void setNucleotideORFEnd(int nucleotideORFEnd);
	
	/**
	 * Gets the strand information in the nucleotide sequence where the protein is derived from.
	 * @return the strand information in the nucleotide sequence where the protein is derived from.
	 */
	String getNucleotideStrand();
	
	/**
	 * Sets the strand information in the nucleotide sequence where the protein is derived from.
	 * @param strand the strand information in the nucleotide sequence where the protein is derived from.
	 */
	void setNucleotideStrand(String strand);
	
	/**
	 * Gets the id of the nucleotide sequence where the protein is derived from.
	 * @return the id of the nucleotide sequence where the protein is derived from.
	 */
	String getNucleotideSequenceId();
	
	/**
	 * Sets the id of the nucleotide sequence where the protein is derived from.
	 * @param nucleotideSequeneId the id of the nucleotide sequence where the protein is derived from.
	 */
	void setNucleotideSequenceId(String nucleotideSequeneId);
	
	/**
	 * Gets the nucleotide sequence where the protein is derived from.
	 * @return the nucleotide sequence where the protein is derived from.
	 */
	String getNucleotideSequence();
	
	/**
	 * Sets the nucleotide sequence where the protein is derived from.
	 * @param nucleotideSequence the nucleotide sequence where the protein is derived from.
	 */
	void setNucleotideSequence(String nucleotideSequence);
	
	/**
	 * Gets the protein sequence.
	 * @return the protein sequence.
	 */
	String getProteinSequence();
	
	/**
	 * Sets the protein sequence.
	 * @param proteinSequence the protein sequence.
	 */
	void setProteinSequence(String proteinSequence);
	
	/**
	 * Gets the trigger (PIRSF or IPR accession).
	 * @return the trigger (PIRSF or IPR accession).
	 */
	String getTrigger();
	
	/**
	 * Sets the trigger (PIRSF or IPR accession).
	 * @param trigger the trigger (PIRSF or IPR accession).
	 */
	void setTrigger(String trigger);
	
	/**
	 * Gets the PIRSR rule accession.
	 * @return the PIRSR rule accession.
	 */
	String getRuleAC();
	
	/**
	 * Sets the PIRSR rule accession.
	 * @param ruleAC the PIRSR rule accession.
	 */
	void setRuleAC(String ruleAC);
	
	/**
	 * Gets the PIRSR rule template accession.
	 * @return the PIRSR rule template accession.
	 */
	String getTemplateAC();
	
	/**
	 * Sets the PIRSR rule template accession.
	 * @param templateAC the PIRSR rule template accession.
	 */
	void setTemplateAC(String templateAC);
	
	/**
	 * Gets the organelle of the protein sequence.
	 * @return the organelle of the protein sequence.
	 */
	String getOrganelle();
	
	/**
	 * Sets the organelle of the protein sequence.
	 * @param organelle the organelle of the protein sequence.
	 */
	void setOrganelle(String organelle);
	
	/**
	 * Gets the organism of the protein sequence.
	 * @return the organism of the protein sequence.
	 */
	String getOrganismSpecies();
	
	/**
	 * Sets the organism of the protein sequence
	 * @param organismSpecies the organism of the protein sequence
	 */
	void setOrganismSpecies(String organismSpecies);
	
	/**
	 * Gets a list of NCBI taxonomy of the source organism (Lineage) of protein sequence.
	 * @return a list of NCBI taxonomy of the source organism (Lineage) of protein sequence.
	 */
	List<String> getOrganismClassification();
	
	/**
	 * Sets a list of NCBI taxonomy of the source organism (Lineage) of protein sequence.
	 * @param organismClassification a list of NCBI taxonomy of the source organism (Lineage) of protein sequence.
	 */
	void setOrganismClassification(List<String> organismClassification);
	
	String toXML();
}
