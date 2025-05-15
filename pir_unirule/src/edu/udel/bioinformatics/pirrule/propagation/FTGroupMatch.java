package edu.udel.bioinformatics.pirrule.propagation;

import java.util.Map;

public class FTGroupMatch {

	Map<Integer, Boolean> ftGroups;
	String note;
	
	public FTGroupMatch() {
		
	}

	public Map<Integer, Boolean> getFtGroups() {
		return ftGroups;
	}

	public void setFtGroups(Map<Integer, Boolean> ftGroups) {
		this.ftGroups = ftGroups;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
