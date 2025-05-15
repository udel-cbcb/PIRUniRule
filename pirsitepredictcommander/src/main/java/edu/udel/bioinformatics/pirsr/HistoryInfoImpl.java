package edu.udel.bioinformatics.pirsr;

import org.proteininformationresource.pirsr.model.HistoryInfo;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br><br>
 * 
 * The information about the version number, date/time and author of the creation/modification.
 */
public class HistoryInfoImpl implements HistoryInfo {
	
	private String versionNumber;
	private String curator;
	private String dateTime;
	private String type;

	public HistoryInfoImpl() {
		super();
	}
	
	public HistoryInfoImpl(String curator, String dateTime, String versionNumber, String type) {
		super();
		this.versionNumber = versionNumber;
		this.curator = curator;
		this.dateTime = dateTime;
		this.type = type;
	}

	
	public String getVersionNumber() {
		return this.versionNumber;
	}

	
	public String getCurator() {
		return this.curator;
	}

	
	public String getDateTime() {
		return this.dateTime;
	}

	
	public String getType() {
		return this.type;
	}

	
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	
	public void setCurator(String curator) {
		this.curator = curator;
	}

	
	public void setDataTime(String dateTime) {
		this.dateTime = dateTime;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		String str = "";
		String curator = this.getCurator();
		String dateTime = this.getDateTime();
		String versionNumber = this.getVersionNumber();
		if (this.getType().equals("creation")) {
			str += "**ZA "
					+ ((curator != null) ? curator : "");
		}
		if (this.getType().equals("lastModification")) {
			str += "**ZB "
					+ ((curator != null) ? curator : "");
		}
		str += " "+((dateTime != null) ? dateTime : "");
		if(versionNumber != null) {
			str += " "+versionNumber;
		}

		str += "\n";
		return str;
	}
}
