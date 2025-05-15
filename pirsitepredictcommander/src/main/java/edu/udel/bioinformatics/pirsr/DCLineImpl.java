package edu.udel.bioinformatics.pirsr;

import org.proteininformationresource.pirsr.model.DCLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * DC (Data Class) of this Site Rule, which specifies which type of data the
 * rule annotates.
 */
public class DCLineImpl implements DCLine {

	private String dataClass;

	public DCLineImpl() {
		super();
	}

	public DCLineImpl(String dataClass) {
		super();
		this.dataClass = dataClass;
	}

	
	public String getDataClass() {
		return this.dataClass;
	}

	public String toString() {
		return "DC   " + this.dataClass + ";\n";
	}

	
	public void setDataClass(String dataClass) {
		this.dataClass = dataClass;
	}
}
