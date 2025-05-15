package edu.udel.bioinformatics.pirsr;

import java.util.List;

import org.proteininformationresource.pirsr.model.TemplateLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 */
public class TemplateLineImpl implements TemplateLine {
	List<String> templateAccessionNumbers;
	
	public TemplateLineImpl() {
		super();
	}
	
	public TemplateLineImpl(List<String> templateAccessionNumbers) {
		this.templateAccessionNumbers = templateAccessionNumbers;
	}

	
	public List<String> getTemplateAccessionNumbers() {
		return this.templateAccessionNumbers;
	}

	
	public void setTemplateAccessionNumbers(List<String> templateAccessionNumbers) {
		this.templateAccessionNumbers = templateAccessionNumbers;
	}

	public String toString() {
		String str = "Template: ";
		for(String ac : this.templateAccessionNumbers) {
			str += ac +"; ";
		}
		return str.trim()+"\n";
	}
}
