package edu.udel.bioinformatics.pirsr;

import java.util.List;

import org.proteininformationresource.pirsr.model.RelatedLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * Related line that lists other Site Rules known to be similar in sequence, and
 * risk to produce cross-matches.
 */
public class RelatedLineImpl implements RelatedLine {
	List<String> relatedPIRRuleAccessionNumbers;

	public RelatedLineImpl(List<String> relatedPIRRuleAccessionNumbers) {
		super();
		this.relatedPIRRuleAccessionNumbers = relatedPIRRuleAccessionNumbers;
	}

	public RelatedLineImpl() {
		super();
	}

	
	public List<String> getRelatedPIRRuleAccessionNumbers() {
		return this.relatedPIRRuleAccessionNumbers;
	}

	
	public void setRelatedPIRRuleAccesionNumbers(List<String> relatedPIRRuleAccessionNumbers) {
		this.relatedPIRRuleAccessionNumbers = relatedPIRRuleAccessionNumbers;
	}

	public String toString() {
		String str = "Related: ";
		for (int i = 0; i < this.relatedPIRRuleAccessionNumbers.size(); i++) {
			str += this.relatedPIRRuleAccessionNumbers.get(i) + "; ";
		}
		return str + "\n";
	}

}
