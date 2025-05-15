package edu.udel.bioinformatics.pirsr;

import org.proteininformationresource.pirsr.model.FTNoneLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 *
 */
public class FTNoneLineImpl implements FTNoneLine {
	
	public FTNoneLineImpl() {
		super();
	}
	
	private String description = "None";
	
	public String getDescription() {
		return this.description;
	}

	public String toString() {
		return "FT   "+this.description+"\n";
	}
}
