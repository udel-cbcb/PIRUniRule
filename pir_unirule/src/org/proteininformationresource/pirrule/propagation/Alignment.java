package org.proteininformationresource.pirrule.propagation;

import edu.udel.bioinformatics.pirrule.propagation.SeqResidue;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 8, 2014<br><br>
 * 
 * 
 * Holds the HMMER 3 alignment information by a given UniProt entry and corresponding template entry.
*/
public interface Alignment {
	
	/**
	 * Sets the entry ID
	 * @param entryId the entry ID
	 */
	void setEntryId(String entryId);
	

	/**
	 * Gets the entry ID.
	 * @return the ID of an UniProt entry.
	 */
	String getEntryId();
	
	/**
	 * Sets the name of program used to create this alignment.
	 * @param featureGenerator the name of program used to create this alignment.
	 */
	void setFeatureGenerator(String featureGenerator);
	
	/**
	 * Gets the name of program used to create this alignment.
	 * @return the name of program used to create this alignment.
	 */
	String getFeatureGenerator();
	
	/**
	 * Sets the trigger.
	 * @param trigger the trigger.
	 */
	void setTrigger(String trigger);
	/**
	 * Gets the trigger.
	 * @return the trigger.
	 */
	String getTrigger();
	
	/**
	 * Sets the rule accession number.
	 * @param ruleAC the rule accession number.
	 */
	void setRuleAC(String ruleAC);
	
	/**
	 * Gets the rule accession number.
	 * @return the rule accession number. 
	 */
	String getRuleAC();
	
	/**
	 * Sets the PIRSF ID.
	 * @param pirsfID the PIRSF ID.
	 */
	void setPIRSFID(String pirsfID);
	
	/**
	 * Gets the PIRSF ID.
	 * @return the PIRSF ID.
	 */
	String getPIRSFID();
	
	/**
	 * Sets the entry sequence start position.
	 * @param seqStart the entry sequence start position.
	 */
	void setSeqStart(int seqStart);
	
	/**
	 * Gets the entry sequence start position.
	 * @return the entry sequence start position.
	 */
	int getSeqStart();
	
	
	/**
	 * Sets the entry sequence end position.
	 * @param seqEnd the entry sequence end position.
	 */
	void setSeqEnd(int seqEnd);
	
	/**
	 * Gets the entry sequence end position.
	 * @return the entry sequence end position.
	 */
	int getSeqEnd();
	
	/**
	 * Sets the "selected" string
	 * @param selected the "selected" string
	 */
	void setSelected(String selected);
	
	/**
	 * Gets the "selected" string
	 * @return the "selected" string
	 */
	String getSelected();
	
	/**
	 * Sets the entry sequence alignment.
	 * @param seqAln entry sequence alignment.
	 */
	void setAlignedSeq(String seqAln);
	
	/**
	 * Gets the entry sequence alignment.
	 * @return the entry sequence alignment.
	 */
	String getAlignedSeq();
	
	/**
	 * Sets the template entry accession number.
	 * @param templateEntryAC the template entry accession number.
	 */
	void setTemplateEntryAC(String templateEntryAC);
	
	/**
	 * Gets the template entry accession number.
	 * @return the template entry accession number.
	 */
	String getTemplateEntryAC();
	
	/**
	 * Sets the template sequence alignment.
	 * @param templateAln the template sequence alignment.
	 */
	void setAlignedTemplateSeq(String templateAln);
	
	/**
	 * Gets the template sequence alignment.
	 * @return the template sequence alignment.
	 */
	String getAlignedTemplateSeq();
	
	/**
	 * Gets the alignment residues
	 * @param alnSeq alignment string
	 * @return an array of <code>SeqResidue</code>.
	 */
	SeqResidue[] getSeqResidues(String alnSeq);
	
}
