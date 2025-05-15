package org.proteininformationresource.pirsr.io.uniruleflatfile;

import java.util.ArrayList;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: Jan 31, 2014<br>
 * <br>
 */
public class UniRuleFlatFileParserLog {
	private String ruleAC;
	
	private ArrayList<String> messages;

	public UniRuleFlatFileParserLog(String ruleAC) {
		this.ruleAC = ruleAC;
		this.messages = new ArrayList<String>();
	}
	public UniRuleFlatFileParserLog(String ruleAC, ArrayList<String> messages) {
		super();
		this.ruleAC = ruleAC;
		this.messages = messages;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}

	public String getRuleAC() {
		return ruleAC;
	}

	public void setRuleAC(String ruleAC) {
		this.ruleAC = ruleAC;
	}
	
	public void addMessage(String message) {
		this.messages.add(message);
	}
	
}
