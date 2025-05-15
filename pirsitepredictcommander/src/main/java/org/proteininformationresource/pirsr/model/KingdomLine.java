package org.proteininformationresource.pirsr.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2013<br>
 * <br>
 * 
 * The taxonomic classification is composed of the kingdom, optionally followed
 * by the name of a sub-taxon, to further limit the application of the Site Rule
 * to any taxonomic level. Valid values for kingdom are: "Eukaryota",
 * "Bacteria", "Archaea", "Viruses", "Bacteriophage", "Plastid" and
 * "Mitochondrion". The two latter values designate proteins encoded in the
 * organelle genome but not proteins encoded in the nucleus and targeted to the
 * organelle. If it has been assessed with certainty that a Site Rule is not
 * represented in: A taxonomic group, its name may be indicated in the "except"
 * field. A complete proteome, its taxcode may be indicated in the "not in"
 * field.
 */
public interface KingdomLine extends Line {

	/**
	 * Gets the Kingdom name.
	 * 
	 * @return the Kingdom name.
	 */
	String getKingdom();

	/**
	 * Sets the Kingdom name
	 * 
	 * @param kingdom
	 *            the Kingdom name.
	 */
	void setKingdom(String kingdom);

	/**
	 * Gets the Kingdom sub-taxon name.
	 * 
	 * @return the Kingdom sub-taxon name.
	 */
	String getKingdomSubTaxon();

	/**
	 * Sets the Kingdom sub-taxon name.
	 * 
	 * @param kingdomSubTaxon
	 *            the Kingdom sub-taxon name.
	 */
	void setKingdomSubTaxon(String kingdomSubTaxon);

	/**
	 * Gets the list of taxonomic group names not belong to this Kingdom.
	 * 
	 * @return the list of taxonomic group names not belong to this Kingdom.
	 */
	List<String> getExceptTaxonomicGroups();

	/**
	 * Sets the list of taxonomic group names not belong to this Kingdom.
	 * 
	 * @param exceptTaxonomicGroups
	 *            the list of taxonomic group names not belong to this Kingdom.
	 */
	void setExceptTaxonomicGroups(List<String> exceptTaxonomicGroups);

	/**
	 * Gets the list of taxcode for complete proteomes not belong to this
	 * Kingdom.
	 * 
	 * @return the list of taxcode for complete proteomes not belong to this
	 *         Kingdom.
	 */
	List<String> getCompleteProteomeNotInTaxCodes();

	/**
	 * Sets the list of taxcode for complete proteomes not belong to this
	 * Kingdom.
	 * 
	 * @param completeProteomeNotInTaxCodes
	 *            the list of taxcode for complete proteomes not belong to this
	 *            Kingdom.
	 */
	void setCompleteProteomeNotInTaxCodes(List<String> completeProteomeNotInTaxCodes);

}
