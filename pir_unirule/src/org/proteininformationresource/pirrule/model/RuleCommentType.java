package org.proteininformationresource.pirrule.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 *
 */
public enum RuleCommentType {

   
	CATALYTIC_ACTIVITY("CATALYTIC_ACTIVITY"),
	COFACTOR("COFACTOR"),
	DOMAIN("DOMAIN"),
	ENZYME_REGULATION("ENZYME_REGULATION"),
	FUNCTION("FUNCTION"),
	INDUCTION("INDUCTION"),
	MISCELLANEOUS("MISCELLANEOUS"),
	PATHWAY("PATHWAY"),
	PTM("PTM"),
	SIMILARITY("SIMILARITY"),
	SUBCELLULAR_LOCATION("SUBCELLULAR_LOCATION"),
	SUBUNIT("SUBUNIT");
 
    private String ccTopic;
 
    //
    // The constructor of CCTopic enum.
    //
    RuleCommentType(String ccTopic) {
        this.ccTopic = ccTopic;
    }
 
    /**
     * Get CC line topic.
     * @return CC line topic
     */
    public String getTopic() {
        return this.ccTopic.replaceAll("_", " ");
    }
}
