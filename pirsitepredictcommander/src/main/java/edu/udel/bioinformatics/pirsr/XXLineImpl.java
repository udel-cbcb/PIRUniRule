package edu.udel.bioinformatics.pirsr;

import org.proteininformationresource.pirsr.model.XXLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 16, 2014<br><br>
 * 
 * This separator line conveys no meaning. It is used to separate blocks of lines.
 *
 */
public class XXLineImpl implements XXLine {
	public XXLineImpl() {
		super();
	}
	
	public String toString() {
		return "XX\n";
	}
}
