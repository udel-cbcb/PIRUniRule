package org.proteininformationresource.pirrule.uniprot.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * Information related to the sequence of UniProt protein entry.
 */
public interface EntrySequence extends EntryLine {

	/**
	 * Gets the CRC64 checksum of protein sequence.
	 * 
	 * The algorithm to compute the CRC64 is described in the ISO 3309 standard.
	 * The generator polynomial is x64 + x4 + x3 + x + 1.
	 * 
	 * @return the CRC64 checksum of protein sequence.
	 */
	String getCRC64();

	/**
	 * Gets the length of the sequence in amino acids ('AA') 
	 * @return the length of the sequence in amino acids ('AA') 
	 */
	int getLength();

	/**
	 * Gets the molecular weight of the mature processed and posttranslationally modified protein.
	 * @return the molecular weight of the mature processed and posttranslationally modified protein.
	 */
	int getMolecularWeight();

	/**
	 * Gets the amino acid sequences of protein.
	 * @return the amino acid sequences of protein.
	 */
	String getSequenceData();
	
	/**
	 * Sets the amino acid sequences of protein.
	 * @param sequenceData the amino acid sequences of protein.
	 */
	void setSequenceData(String sequenceData);

}
