package org.proteininformationresource.pirsitepredict.prediction;

import java.util.Arrays;
import java.util.List;

import org.proteininformationresource.pirsitepredict.distribution.Release;


public class PredictionForm {
	
	private String release;
	
	private List<Release> allReleases;
	
	
	private List<String> organisms = Arrays.asList("Eukaryota", "Bacteria", "Archaea", "Viruses",
            "Bacteriophage", "Plastid", "Mitochondrion");
	private String organism;
	
	private String interProScanXMLFile;
	
	//private List<String> outputFormats;
	
	//private List<String> checkedOutputFormats;
	
	private String EValue;
	
	private String email;

	private String jobTitle;
	
	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getInterProScanXMLFile() {
		return interProScanXMLFile;
	}

	public void setInterProScanXMLFile(String interProScanXMLFile) {
		this.interProScanXMLFile = interProScanXMLFile;
	}

	


	public String getEValue() {
		return EValue;
	}

	public void setEValue(String EValue) {
		this.EValue = EValue;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public List<String> getOutputFormats() {
//		return outputFormats;
//	}
//
//	public void setOutputFormats(List<String> outputFormats) {
//		this.outputFormats = outputFormats;
//	}
//
//	public List<String> getCheckedOutputFormats() {
//		return checkedOutputFormats;
//	}
//
//	public void setCheckedOutputFormats(List<String> checkedOutputFormats) {
//		this.checkedOutputFormats = checkedOutputFormats;
//	}

	public List<Release> getAllReleases() {
		return allReleases;
	}

	public void setAllReleases(List<Release> allReleases) {
		this.allReleases = allReleases;
	}

	
	public String toString() {
		String sb = "";
		sb += "Release: " + this.release+"\n";
		sb += "InterProScanXMLFile: " + this.interProScanXMLFile+"\n";
		//sb += "OutputFormats: " + this.outputFormats+"\n";
		sb += "EValue: " + this.EValue+"\n";
		sb += "Email: " + this.email+"\n";
		sb += "Job Title: " + this.jobTitle+"\n";
		return sb;
	}

	public List<String> getOrganisms() {
		return organisms;
	}

	public void setOrganisms(List<String> organisms) {
		this.organisms = organisms;
	}

	public String getOrganism() {
		return organism;
	}

	public void setOrganism(String organism) {
		this.organism = organism;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

}
