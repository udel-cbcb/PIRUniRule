package org.proteininformationresource.pirsr.propagation.annotation;

import org.proteininformationresource.pirsr.model.Line;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 * 
 */
public interface RuleAnnotation {

	/**
	 * Gets the rule accession number.
	 * @return the rule accession number.
	 */
	String getRuleAC();
	
	/**
	 * Sets the rule accession number.
	 * @param ruleAC the rule accession number.
	 */
	void setRuleAC(String ruleAC);
	
	/**
	 * Gets the template UniProt accession number.
	 * @return the template UniProt accession number.
	 */
	String getTemplateAC();
	
	/**
	 * Sets the template UniProt accession number.
	 * @param templateAC the template UniProt accession number.
	 */
	void setTemplateAC(String templateAC);
	
	/**
	 * Gets the rule annotation in report format.
	 * @return the rule annotation in report format.
	 */
	String toReport();
	
	/**
	 * Gets the rule annotation in Kraken format.
	 * @return the rule annotation in Kraken format.
	 */
	String toKraken();
}
