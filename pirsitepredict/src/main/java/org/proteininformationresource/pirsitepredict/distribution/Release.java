package org.proteininformationresource.pirsitepredict.distribution;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;


public class Release {
	private String id;
	private String releaseNumber;
	
	private int numberOfRules;
	
	private String tarFileName;
	
	private String tarFileSize;
	
	private String releaseDate;
	
	private String md5;

	public String getReleaseNumber() {
		return releaseNumber;
	}

	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	public String getTarFileName() {
		return tarFileName;
	}

	public void setTarFileName(String tarFileName) {
		this.tarFileName = tarFileName;
	}

	public String getTarFileSize() {
		return tarFileSize;
	}

	public void setTarFileSize(String tarFileSize) {
		this.tarFileSize = tarFileSize;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNumberOfRules() {
		return numberOfRules;
	}

	public void setNumberOfRules(int numberOfRules) {
		this.numberOfRules = numberOfRules;
	}
   
	
}
