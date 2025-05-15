package edu.udel.bioinformatics.pirsr.prediction;

import java.util.Map;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 15, 2014<br>
 * <br>
 * 
 * Matched FT group.
 */
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
