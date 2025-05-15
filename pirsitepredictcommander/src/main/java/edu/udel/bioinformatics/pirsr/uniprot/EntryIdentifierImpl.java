package edu.udel.bioinformatics.pirsr.uniprot;

import org.proteininformationresource.pirsr.uniprot.model.EntryIdentifier;
import org.proteininformationresource.pirsr.uniprot.model.EntryType;

import edu.udel.bioinformatics.pirsr.uniprot.io.UniProtFlatFileConstants;

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
public class EntryIdentifierImpl implements EntryIdentifier {
	private String entryName;
	private EntryType entryType;
	private int sequenceLength;

	
	public EntryIdentifierImpl(String entryName, EntryType entryType, int sequenceLength) {
		super();
		this.entryName = entryName;
		this.entryType = entryType;
		this.sequenceLength = sequenceLength;
	}

	
	public String getEntryName() {
		return this.entryName;
	}

	
	public EntryType getEntryType() {
		return this.entryType;
	}

	
	public int getSequenceLength() {
		return this.sequenceLength;
	}

	public String toString() {
		String record = "";
		
		record += UniProtFlatFileConstants.ID_LINE_START+ entryName + " " + entryType+ " " + sequenceLength;
		return record;
	}
}
