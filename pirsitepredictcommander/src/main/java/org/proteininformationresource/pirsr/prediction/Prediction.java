package org.proteininformationresource.pirsr.prediction;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: May 20, 2015<br><br>
 * 
 * 
 * Holds prediction information
*/
public interface Prediction {
	
	/**
	 * Sets the type of prediction. 
	 * PIR site rule currently includes "Feature", "Comment" and "Keyword".
	 * @return the type of prediction.
	 */
	String getType();
	
	/**
	 * Gets the type of prediction. 
	 * PIR site rule currently includes "Feature", "Comment" and "Keyword".
	 * @param the type of prediction.
	 */
	void setType(String type);
	
	/**
	 * Gets the category of prediction.
	 * PIR site rule currently includes:
	 * 		Comment: COFACTOR, PTM, SIMILARITY, SUBCELLULAR LOCATION, SUBUNIT
	 * 		Feature: ACT_SITE, BINDING, CHAIN, CROSSLNK, DISULFID, LIPID, METAL, MOD_RES, MOTIF, NP_BIND, REGION, SITE, ZN_FING
	 * @return the category of prediction.
	 */
	String getCategory();
	
	/**
	 * Gets the category of prediction.
	 * PIR site rule currently includes:
	 * 		Comment: COFACTOR, PTM, SIMILARITY, SUBCELLULAR LOCATION, SUBUNIT
	 * 		Feature: ACT_SITE, BINDING, CHAIN, CROSSLNK, DISULFID, LIPID, METAL, MOD_RES, MOTIF, NP_BIND, REGION, SITE, ZN_FING
	 * @param category the category of prediction.
	 */
	void setCategory(String category);
	
	/**
	 * Gets the prediction description.
	 * @return the prediction description.
	 */
	String getDescription();
	
	/**
	 * Sets the prediction description.
	 * @param description the prediction description.
	 */
	void setDescription(String description);
}
