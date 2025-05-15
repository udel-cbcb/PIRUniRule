package org.proteininformationresource.pirsr.uniprot.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * The first line of an entry. The general form of the ID line is:
 *
 * ID EntryName Status; SequenceLength.
 */
public interface EntryIdentifier extends EntryLine {

	/**
	 * Gets the entry name of the sequence. This name is a useful means of
	 * identifying a sequence, but it is not a stable identifier as is the
	 * accession number
	 * 
	 * @return the entry name of the sequence.
	 */
	String getEntryName();

	/**
	 * Gets the <code>EntryType</code> of the UniProt entry.
	 * 
	 * @return the <code>EntryType</code> of the UniProt entry.
	 */
	EntryType getEntryType();

	/**
	 * Gets the length of the molecule, which is the total number of amino acids
	 * in the sequence. This number includes the positions reported to be
	 * present but which have not been determined (coded as 'X'). The length is
	 * followed by the letter code 'AA' (Amino Acids).
	 * 
	 * @return the length of the molecule.
	 */
	int getSequenceLength();
}
