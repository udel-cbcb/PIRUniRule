package org.proteininformationresource.pirsr.iprscanmatch;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 28, 2015<br><br>
 * 
 * The matched protein sequence from InterProScan XML file
 */
public interface IPRScanMatchedProtein {

	/**
	 * Gets the protein identifier.
	 * @return the protein identifier.
	 */
	String getId();
	
	/**
	 * Sets the protein identifier.
	 * @param id the protein identifier.
	 */
	void setId(String id);
	
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
	 * Gets the parent nucleotide sequence identifier.
	 * @return the parent nucleotide sequence identifier.
	 */
	String getParentNucleotideSeqId();
	
	/**
	 * Sets the parent nucleotide sequence identifier.
	 * @param id the parent nucleotide sequence identifier.
	 */
	void setParaentNucleotideSeqId(String id);
}
