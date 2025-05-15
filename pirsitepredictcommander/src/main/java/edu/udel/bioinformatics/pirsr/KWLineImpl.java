package edu.udel.bioinformatics.pirsr;

import org.proteininformationresource.pirsr.model.KWLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br><br>
 * 
 * The KW line contains all applicable keywords for a UniProtKB/Swiss-Prot entry, one per line.
 */
public class KWLineImpl implements KWLine {
	
	private String keyword;
	
	
    public KWLineImpl() {
    	super();
    }
    
	public KWLineImpl(String keyword) {
		super();
		this.keyword = keyword;
	}

	
	public String getKeyword() {
		return this.keyword;
	}

	public String toString() {
		return "KW   "+this.keyword+"\n";
	}

	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
