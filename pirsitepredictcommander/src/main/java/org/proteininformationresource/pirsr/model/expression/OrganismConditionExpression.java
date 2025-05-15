package org.proteininformationresource.pirsr.model.expression;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: August 24, 2014<br>
 * <br>
 * 
 * OS/OC/OG: on taxonomy and organelle (OS, OC and OG lines):
 *
 * case <OG:Chloroplast> or <OC:Cyanobacteria>
 *
 * case not <OG:Chloroplast> and not <OG:Cyanelle>
 *
 * case <OC:Archaea>
 *
 * case <OC:Bacteria>
 * 
 * case <OS:Staphylococcus aureus>
 *
 * Note for conditions on organism names ('case <OS:taxon>'): the organism name
 * matches also subspecies, i.e. organisms with the same name followed by a
 * space and then any text. For example, 'Staphylococcus aureus' matches
 * 'Staphylococcus aureus RF122', but 'Salmonella typhi' does not match
 * 'Salmonella typhimurium'.
 *
 */
public interface OrganismConditionExpression extends ConditionExpression {
	/**
	 * Gets the organism condition type.
	 * 
	 * @return the organism condition type.
	 */
	OrganismConditionType getOrganimsConditionType();

	/**
	 * Sets the organism condition type.
	 * 
	 * @param organismType
	 *            the organism condition type.
	 */
	void setOrganismConditionType(OrganismConditionType organismConditionType);

	/**
	 * Gets the organism name.
	 * 
	 * @return the organism name.
	 */
	String getOrganismName();

	/**
	 * Sets the organism name.
	 * 
	 * @param organismName
	 *            .
	 */
	void setOrganismName(String organismName);
}
