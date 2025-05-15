package edu.udel.bioinformatics.pirsr.profilehmm;

import java.util.LinkedHashMap;
import java.util.List;

import org.proteininformationresource.pirsr.profilehmm.Template;
import org.proteininformationresource.pirsr.profilehmm.TemplateResidue;

public class TemplateImpl implements Template {

	private String accession;
	
	private LinkedHashMap<String, List<TemplateResidue>> domainAlignments;
	
	private int length;
	
	public TemplateImpl(String accession, int length, LinkedHashMap<String, List<TemplateResidue>> domainAlignments) {
		super();
		this.accession = accession;
		this.domainAlignments = domainAlignments;
		this.length = length;
	}

	
	public String getAccession() {
		return this.accession;
	}

	
	public LinkedHashMap<String, List<TemplateResidue>> getDomainAlignments() {
		return this.domainAlignments;
	}

	
	public int getLength() {
		return this.length;
	}

	

}
