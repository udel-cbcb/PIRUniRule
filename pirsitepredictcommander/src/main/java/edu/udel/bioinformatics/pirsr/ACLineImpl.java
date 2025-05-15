package edu.udel.bioinformatics.pirsr;


import org.proteininformationresource.pirsr.model.ACLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2013<br><br>
 * 
 * The AC (ACcession number) line of the rule, which is the identifier for the rule.
 */
public class ACLineImpl implements ACLine{
	private String accessionNumber;
	
	public ACLineImpl() {
		super();
	}
	
	public ACLineImpl(String accessionNumber) {
		super();
		this.accessionNumber = accessionNumber;
	}


	
	public String getAccessionNumber() {
		return this.accessionNumber;
	}
	
	
	public String toString() {
		return "AC   "+this.accessionNumber+";\n";
	}
	

	
	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

}
