package org.proteininformationresource.pirsr.io.uniruleflatfile;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br><br>
 * 
 * The error message during parsing
 */
public class ErrorMessage {
	int lineNumberInRule;
	int lineNumberInSource;
	String message;
	public ErrorMessage(int lineNumberInRule, int lineNumberInSource, String message) {
		super();
		this.lineNumberInRule = lineNumberInRule;
		this.lineNumberInSource = lineNumberInSource;
		this.message = message;
	}
	public int getLineNumberInRule() {
		return lineNumberInRule;
	}
	public void setLineNumberInRule(int lineNumberInRule) {
		this.lineNumberInRule = lineNumberInRule;
	}
	public int getLineNumberInSource() {
		return lineNumberInSource;
	}
	public void setLineNumberInSource(int lineNumberInSource) {
		this.lineNumberInSource = lineNumberInSource;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
