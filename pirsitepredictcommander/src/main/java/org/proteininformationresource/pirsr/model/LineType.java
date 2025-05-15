package org.proteininformationresource.pirsr.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 24, 2014<br>
 * <br>
 */
public enum LineType {

	AC("AC"),
	DC("DC"),
	TR("TR"),
	XX("XX"),
	INTERNALCOMMENTS("INTERNALCOMMENTS"),
	DE("DE"),
	CC("CC"),
	DR("DR"),
	KW("KW"),
	GO("GO"),
	FT("FT"),
	SIZE("SIZE"),
	RELATED("RELATED"),
	TEMPLATE("TEMPLATE"),
	SCOPEBLOCK("SCOPEBLOCK"),
	FUSIONBLOCK("FUSIONBLOCK"),
	DUPLICATE("DUPLICATE"),
	PLASMID("PLASMID"),
	COMMENTS("COMMENTS");
	

	private String type;

	//
	// The constructor of LineType enum.
	//
	LineType(String type) {
		this.type = type;
	}

	/**
	 * Get line type
	 * 
	 * @return line type
	 */
	public String getType() {
		return this.type;
	}
}
