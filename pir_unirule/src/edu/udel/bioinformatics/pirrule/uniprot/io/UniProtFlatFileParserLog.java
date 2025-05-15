package edu.udel.bioinformatics.pirrule.uniprot.io;

import java.util.ArrayList;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: Oct 29, 2014<br>
 * <br>
 */
public class UniProtFlatFileParserLog {
	private String entryId;
	
	private ArrayList<String> messages;

	public UniProtFlatFileParserLog(String entryId) {
		this.entryId = entryId;
		this.messages = new ArrayList<String>();
	}
	public UniProtFlatFileParserLog(String entryId, ArrayList<String> messages) {
		super();
		this.entryId = entryId;
		this.messages = messages;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
	
	public void addMessage(String message) {
		this.messages.add(message);
	}
	
	public String toReport() {
		String report = "";
		
		report += this.entryId +"\n";
		for(String message : messages) {
			report += message+"\n";
		}
		return report;
	}
}
