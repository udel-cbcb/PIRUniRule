package org.proteininformationresource.pirrule.uniprot.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * An UniProt entry parsed from .dat file, only contains information useful for 
 * PIR site rule propagation.
 */

public interface UniProtEntry {
	/**
	 * Gets a list of <code>EntryLine</code>s.
	 * @return a list of <code>EntryLine</code>s.
	 */
	List<EntryLine> getEntryLines();
	
	/**
	 * Gets a list of <code>EntryCommet</code>s.
	 * @return a list of <code>EntryComment</code>s.
	 */
	List<EntryComment> getComments();
	
	/**
	 * Sets a list of <code>EntryCommet</code>s.
	 * @param comments a list of <code>EntryCommet</code>s.
	 */
	void setComments(List<EntryComment> comments);
	
	/**
	 * Gets a list of <code>EntryDatabaseCrossReference</code>s.
	 * @return a list of <code>EntryDatabaseCrossReference</code>s.
	 */
	List<EntryDatabaseCrossReference> getDatabaseCrossReferences();
	
	/**
	 * Gets a list of <code>EntryDatabaseCrossReference</code>s of certain type.
	 * @param databaseType the database type.
	 * @return a list of <code>EntryDatabaseCrossReference</code>s of certain type.
	 */
	List<EntryDatabaseCrossReference> getDatabaseCrossReferences(EntryDatabaseType databaseType);
	
	/**
	 * Sets a list of <code>EntryDatabaseCrossReference</code>s.
	 * @param databaseCrossReferences a list of <code>EntryDatabaseCrossReference</code>s.
	 */
	void setDatabaseCrossReference(List<EntryDatabaseCrossReference> databaseCrossReferences);
	
	/**
	 * Gets a list of <code>EntryFeatureTable</code>s.
	 * @return a list of <code>EntryFeatureTable</code>s.
	 */
	List<EntryFeatureTable> getFeatures();
	
	/**
	 * Sets a list of <code>EntryFeatureTable</code>s.
	 * @param features a list of <code>EntryFeatureTable</code>s.
	 */
	void setFeatures(List<EntryFeatureTable> features);
	
	/**
	 * Gets a list of <code>EntryKeyword</code>s.
	 * @return a list of <code>EntryKeyword</code>s.
	 */
	List<EntryKeyword> getKeywords();
	
	/**
	 * Sets a list of <code>EntryKeyword</code>s.
	 * @param keywords a list of <code>EntryKeyword</code>s.
	 */
	void setKeywords(List<EntryKeyword> keywords);
	
	/**
	 * Gets a list of NCBI taxonomy name for the organism classification (Lineage).
	 * @return a list of NCBI taxonomy name for the organism classification (Lineage).
	 */
	EntryOrganismClassification getOrganismClassification();
	
	/**
	 * Sets a list of NCBI taxonomy name for the organism classification (Lineage).
	 * @param organismClassification a list of NCBI taxonomy name for the organism classification (Lineage).
	 */
	void setOrganismClassifcation(EntryOrganismClassification organismClassification);
	
	/**
	 * Gets the organism species of the protein entry.
	 * @return the organism species of the protein entry.
	 */
	EntryOrganismSpecies getOrganismSpecies();
	
	/**
	 * Sets the organism species of the protein entry.
	 * @param organismSpecies the organism species of the protein entry.
	 */
	void setOrganismSpecies(EntryOrganismSpecies organismSpecies);
	
	/**
	 * Gets the organelle of the protein entry.
	 * @return the organelle of the protein entry.
	 */
	EntryOrganelle getOrganelle();
	
	/**
	 * Sets the organelle of the protein entry.
	 * @param organelle the organelle of the protein entry.
	 */
	void setOrganelle(EntryOrganelle organelle);
	
	/**
	 * Gets the primary accession number of an UniProt entry.
	 * @return the primary accession number of an UniProt entry.
	 */
	String getPrimiaryAccessionNumber();
	
	/**
	 * Gets a list of secondary accession numbers of an UniProt entry.
	 * @return a list of secondary accession numbers of an UniProt entry.
	 */
	List<String> getSecondaryAccessionNumbers();
	
	/**
	 * Sets the entry accession number.
	 * @param entryAccessionNumber the entry accession number.
	 */
	void setAccessionNumber(EntryAccessionNumber entryAccessionNumber);
	
	/**
	 * Gets the UniProt entry name.
	 * @return the UniProt entry name.
	 */
	String getEntryId();
	
	/**
	 * Sets the UniProt entry identifier.
	 * @param entryIdentifier UniProt entry identifier
	 */
	void setIdentifier(EntryIdentifier entryIdentifier);
	
	/**
	 * Gets the sequence info of a protein entry.
	 * @return the sequence info of a protein entry.
	 */
	EntrySequence getSequence();
	
	/**
	 * Sets the sequence info of a protein entry.
	 * @param entrySequence the sequence info of a protein entry.
	 */
	void setSequece(EntrySequence entrySequence);
	
	/**
	 * Add an <code>EntryLine</code>.
	 * @param entryLine an <code>EntryLine</code>.
	 */
	void addEntryLine(EntryLine entryLine);
	
	EntryType getEntryType();
	
}
