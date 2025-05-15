package org.proteininformationresource.pirsr.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * The TR (Trigger) line of this PIR Rule, which describes which (selected)
 * sequence analysis features trigger the application of this PIR Rule.Each
 * feature name should appear only once in the TR line in all the PIR Rules, as
 * one type of feature is expected to trigger only one rule.
 */
public interface TRLine extends Line {

	/**
	 * Gets the name of the sequence analysis feature database.
	 * 
	 * @return the name of the sequence analysis feature database.
	 */
	String getDBName();

	/**
	 * Sets the name of the sequence analysis feature database.
	 * 
	 * @param dbName
	 */
	void setDBName(String dbName);

	/**
	 * Gets the identifier1 that is the feature name e.g. the unique identifier
	 * of the motif in the given database, which is generally an accession
	 * number.
	 * 
	 * @return the identifier1 that is the feature name.
	 */
	String getIdentifier1();

	/**
	 * Sets the identifier1 that is the feature name e.g. the unique identifier
	 * of the motif in the given database, which is generally an accession
	 * number.
	 * 
	 * @param identifier1
	 *            the identifier1 that is the feature name.
	 */
	void setIdentifier1(String identifier1);

	/**
	 * Gets the identifier2 that is a secondary identifier for the motif, which
	 * is generally an entry name, and which is empty ("-") in PIR Rule.
	 * 
	 * @return the identifier2 that is a secondary identifier for the motif.
	 */
	String getIdentifier2();

	/**
	 * Sets the identifier2 that is a secondary identifier for the motif, which
	 * is generally an entry name, and which is empty ("-") in PIR Rule.
	 * 
	 * @param identifier2
	 *            the identifier2 that is a secondary identifier for the motif.
	 */
	void setIdentifier2(String identifier2);

	/**
	 * Gets the nbhits field that indicates the expected number of hits to the
	 * motif. Obviously, the rule is only triggered if at least one hit is
	 * found. The default value is '1' for PIR Rule.
	 * 
	 * @return the nbhits field that indicates the expected number of hits to
	 *         the motif. Obviously, the rule is only triggered if at least one
	 *         hit is found.
	 */
	String getNBHits();

	/**
	 * Sets the nbhits field that indicates the expected number of hits to the
	 * motif. Obviously, the rule is only triggered if at least one hit is
	 * found.
	 * 
	 * @param nbHits
	 *            the nbhits field that indicates the expected number of hits to
	 *            the motif. Obviously, the rule is only triggered if at least
	 *            one hit is found.
	 */
	void setNBHits(String nbHits);

	/**
	 * Gets the level that indicates the minimum level that a hit must have to
	 * trigger the rule. The default value is '0' for PIR Rule.
	 * 
	 * @return the level that indicates the minimum level that a hit must have
	 *         to trigger the rule. The default value is '0' for PIR Rule.
	 */
	String getLevel();

	/**
	 * Sets the level that indicates the minimum level that a hit must have to
	 * trigger the rule. The default value is '0' for PIR Rule.
	 * 
	 * @param level
	 *            the level that indicates the minimum level that a hit must
	 *            have to trigger the rule. The default value is '0' for PIR
	 *            Rule.
	 */
	void setLevel(String level);
}
