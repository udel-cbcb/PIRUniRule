package edu.udel.bioinformatics.pirrule.uniprot;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirrule.uniprot.model.EntryAccessionNumber;

import edu.udel.bioinformatics.pirrule.uniprot.io.UniProtFlatFileConstants;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * lists the accession number(s) associated with an entry. The format of the AC
 * line is:
 * 
 * AC AC_number_1;[ AC_number_2;]...[ AC_number_N;]
 * 
 * 
 */
public class EntryAccessionNumberImpl implements EntryAccessionNumber {
	List<String> accessionNumberList;
	
	public EntryAccessionNumberImpl(List<String> accessionNumberList) {
		super();
		this.accessionNumberList = accessionNumberList;
	}

	@Override
	public String getPrimaryAccessionNumber() {
		if(this.accessionNumberList!=null && this.accessionNumberList.size() > 0) {
			return this.accessionNumberList.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public List<String> getSecondaryAccessionNumbers() {
		List<String> secondaryAccessionNumbers = new ArrayList<String>();
		if(this.accessionNumberList.size() <=1) {
			return null;
		}
		else {
			for(int i = 1; i < this.accessionNumberList.size(); i++) {
				secondaryAccessionNumbers.add(this.accessionNumberList.get(i));
			}
		}
		return secondaryAccessionNumbers;
	}
	
	public String toString() {
		String record = UniProtFlatFileConstants.AC_LINE_START;
		for(int i = 0; i < accessionNumberList.size(); i++) {
			if(i == 0) {
				record += accessionNumberList.get(i);
			}
			else {
				record += ", "+ accessionNumberList.get(i);
			}
		}
		return record.trim();
	}
}
