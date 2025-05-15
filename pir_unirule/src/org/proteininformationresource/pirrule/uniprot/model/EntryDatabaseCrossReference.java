package org.proteininformationresource.pirrule.uniprot.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * Used as pointers to information in external data resources that is related to
 * UniProtKB entries. The full list of all databases to which UniProtKB is
 * cross-referenced can be found in the document dbxref.txt. It also contains
 * references describing these resources and provides links to their web sites.
 */
public interface EntryDatabaseCrossReference extends EntryLine {

	// DR RESOURCE_ABBREVIATION; RESOURCE_IDENTIFIER; OPTIONAL_INFORMATION_1[;
	// OPTIONAL_INFORMATION_2][; OPTIONAL_INFORMATION_3].
	/**
	 * Gets the type of database cross reference.
	 * @return the type of database cross reference.
	 */
	EntryDatabaseType getDatabseType();

	/**
	 * Gets the unambiguous pointer to a record in the referenced database.
	 * @return the unambiguous pointer to a record in the referenced database.
	 */
	String getIdentifier();

	/**
	 * Gets the first optional information.
	 * @return the first optional information.
	 */
	String getOptionalInfo1();

	/**
	 * Gets the second optional information.
	 * @return the second optional information.
	 */
	String getOptionalInfo2();

	/**
	 * Gets the third optional information.
	 * @return the third optional information.
	 */
	String getOptionalInfo3();

	/**
	 * Check if there is second optional information.
	 * @return true if there is second optional information.
	 */
	boolean hasOptionalInfo2();

	/**
	 * Check if there is third optional information.
	 * @return true if there is third optional information.
	 */
	boolean hasOptionalInfo3();

}
