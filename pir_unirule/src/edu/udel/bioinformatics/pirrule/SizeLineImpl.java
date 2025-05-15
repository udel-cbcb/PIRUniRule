package edu.udel.bioinformatics.pirrule;

import org.proteininformationresource.pirrule.model.SizeLine;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br><br>
 * 
 * 
 * The Size line that indicates the size relevant to a protein family or motif.
 * A size may be specified as 'unlimited'.
*/
public class SizeLineImpl implements SizeLine{

	public SizeLineImpl() {
		super();
		// TODO Auto-generated constructor stub
	}


	private String sizeLimit;
	
	
	public SizeLineImpl(String sizeLimit) {
		super();
		this.sizeLimit = sizeLimit;
	}


	@Override
	public String getSizeLimit() {
		return this.sizeLimit;
	}


	@Override
	public void setSizeLimit(String sizeLimit) {
		this.sizeLimit = sizeLimit;
	}

	public String toString() {
		return "Size: " + this.sizeLimit + ";\n";
	}
}
