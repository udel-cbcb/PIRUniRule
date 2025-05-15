package edu.udel.bioinformatics.pirrule.profilehmm;

import java.util.LinkedHashMap;
import java.util.List;

import org.proteininformationresource.pirrule.profilehmm.TemplateResidue;
import org.proteininformationresource.pirrule.profilehmm.Template;

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

	@Override
	public String getAccession() {
		return this.accession;
	}

	@Override
	public LinkedHashMap<String, List<TemplateResidue>> getDomainAlignments() {
		return this.domainAlignments;
	}

	@Override
	public int getLength() {
		return this.length;
	}

	

}
