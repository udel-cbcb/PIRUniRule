package edu.udel.bioinformatics.pirrule;

import org.proteininformationresource.pirrule.model.CCNoneLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 */
public class CCNoneLineImpl implements CCNoneLine {

	private String description = "None";

	@Override
	public String getDescription() {
		return this.description;
	}

	public String toString() {
		return "CC   " + this.description + "\n";
	}

}
