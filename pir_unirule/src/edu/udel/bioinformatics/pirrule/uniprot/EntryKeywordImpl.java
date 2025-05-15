package edu.udel.bioinformatics.pirrule.uniprot;

import org.proteininformationresource.pirrule.uniprot.model.EntryKeyword;

import edu.udel.bioinformatics.pirrule.uniprot.io.UniProtFlatFileConstants;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * Provides information that can be used to generate indexes of the sequence
 * entries based on functional, structural, or other categories. 
 */
public class EntryKeywordImpl implements EntryKeyword {
	private String keyword;

	
	public EntryKeywordImpl(String keyword) {
		super();
		this.keyword = keyword;
	}


	@Override
	public String getKeyword() {
		return this.keyword;
	}
	
	public String toString() {
		String record = UniProtFlatFileConstants.KW_LINE_START;
		record += this.keyword;
		
		return record;
	}
	
	public String toReport() {
		return this.keyword;
	}
}
