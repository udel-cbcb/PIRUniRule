package edu.udel.bioinformatics.pirsr.uniprot;

import org.proteininformationresource.pirsr.uniprot.model.EntrySequence;

import edu.udel.bioinformatics.pirsr.uniprot.io.UniProtFlatFileConstants;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * Information related to the sequence of UniProt protein entry.
 */
public class EntrySequenceImpl implements EntrySequence{
	
	private String crc64;
	private int length;
	private int molecularWeight;
	private String sequenceData;
	
	
	public EntrySequenceImpl() {
		super();
	}

	public EntrySequenceImpl(String crc64, int length, int molecularWeight) {
		super();
		this.crc64 = crc64;
		this.length = length;
		this.molecularWeight = molecularWeight;
	}

	
	public String getCRC64() {
		return this.crc64;
	}

	
	public int getLength() {
		return this.length;
	}

	
	public int getMolecularWeight() {
		return this.molecularWeight;
	}

	
	public String getSequenceData() {
		return this.sequenceData;
	}

	
	public void setSequenceData(String sequenceData) {
		this.sequenceData = sequenceData;
	}
	
	public String toString() {
		String record = UniProtFlatFileConstants.SQ_LINE_START;
		record += this.length +" AA; "+this.molecularWeight + " MW; " + this.crc64 + " CRC64; ";
		record += this.sequenceData;
		//record += "\n "+UniProtFlatFileConstants.SD_LINE_START+this.sequenceData.length();
		return record;
	}
}
