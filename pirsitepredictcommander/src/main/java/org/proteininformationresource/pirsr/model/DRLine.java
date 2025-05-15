package org.proteininformationresource.pirsr.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 16, 2014<br>
 * <br>
 * 
 * The DR line is to trigger 'child' rules in order to avoid duplication of rule
 * content
 * 
 * Format: DR type/dbname; feature name; identifier; nbhits;
 * trigger=[yes|strict|no]
 */
public interface DRLine extends Line {
	/**
	 * Gets the type/dbname.
	 * 
	 * @return the type/dbname.
	 */
	String getDBName();

	/**
	 * Sets the type/dbname.
	 * 
	 * @param dbName
	 *            type/dbname.
	 */
	void setDBName(String dbName);

	/**
	 * Gets the name of the feature that should be used (e.g. Transmembrane,
	 * motif accession number, etc...) to propagate annotation through the
	 * triggering of its associated rule.
	 * 
	 * @return the name of the feature that should be used.
	 */
	String getFeatureName();

	/**
	 * Sets the name of the feature that should be used (e.g. Transmembrane,
	 * motif accession number, etc...) to propagate annotation through the
	 * triggering of its associated rule.
	 * 
	 * @param featureName
	 *            the name of the feature that should be used.
	 */
	void setFeatureName(String featureName);

	/**
	 * Gets the secondary identifier of the motif.
	 * 
	 * @return the secondary identifier of the motif.
	 */
	String getIdentifier();

	/**
	 * Sets the secondary identifier of the motif.
	 * 
	 * @param identifier
	 *            the secondary identifier of the motif.
	 */
	void setIdentifier(String identifier);

	/**
	 * Gets the number of hits. The value of this field may be a number ('1'),
	 * or a range ('0-1'). A range in the form 'number-unlimited' indicates that
	 * the number of matches is unlimited.
	 * 
	 * @return the number of hits.
	 */
	String getNBHits();

	/**
	 * Sets the number of hits. The value of this field may be a number ('1'),
	 * or a range ('0-1'). A range in the form 'number-unlimited' indicates that
	 * the number of matches is unlimited.
	 * 
	 * @param nbHits
	 *            the number of hits.
	 */
	void setNBHits(String nbHits);

	/**
	 * Gets the trigger level. must be set to either of 'yes', 'strict' or 'no'.
	 * The values 'yes' or 'strict' indicates that the rule associated with the
	 * specified features should be triggered to generate annotation. With
	 * 'strict', only selected features (by the analysis result automatic
	 * selection module) will be used. With 'yes', selected features will also
	 * be used preferentially, but if none is found, all (non-selected) features
	 * of the specified type will be used! The value 'no' indicates that
	 * features (overlapping, if parent rule is not a protein type rule, all, if
	 * parent rule is of protein type) with the specified feature name will not
	 * be used to generate annotation.
	 * 
	 * @return Trigger level.
	 */
	String getTriggerLevel();

	/**
	 * Sets the trigger level. must be set to either of 'yes', 'strict' or 'no'.
	 * The values 'yes' or 'strict' indicates that the rule associated with the
	 * specified features should be triggered to generate annotation. With
	 * 'strict', only selected features (by the analysis result automatic
	 * selection module) will be used. With 'yes', selected features will also
	 * be used preferentially, but if none is found, all (non-selected) features
	 * of the specified type will be used! The value 'no' indicates that
	 * features (overlapping, if parent rule is not a protein type rule, all, if
	 * parent rule is of protein type) with the specified feature name will not
	 * be used to generate annotation.
	 * 
	 * @param triggerLevel
	 *            Trigger level
	 */
	void setTriggerLevel(String triggerLevel);
}
