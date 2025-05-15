package edu.udel.bioinformatics.pirrule.uniprot;

import java.util.List;

import org.proteininformationresource.pirrule.uniprot.model.EntryOrganismClassification;

import edu.udel.bioinformatics.pirrule.uniprot.io.UniProtFlatFileConstants;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * Contains the taxonomic classification of the source organism. The taxonomic
 * classification used is that maintained at the NCBI (see
 * http://www.ncbi.nlm.nih.gov/Taxonomy/) and used by the nucleotide sequence
 * databases (EMBL/GenBank/DDBJ). The NCBI's taxonomy reflects current
 * phylogenetic knowledge. It is a sequence-based taxonomy as much as possible
 * and based on published authorities wherever possible.
 */
public class EntryOrganismClassificationImpl implements EntryOrganismClassification {
	private List<String> organismClassification;
	
	
	public EntryOrganismClassificationImpl(List<String> organismClassification) {
		super();
		this.organismClassification = organismClassification;
	}

	@Override
	public List<String> getOrganismClassification() {
		return this.organismClassification;
	}

	public String toString() {
		String record = UniProtFlatFileConstants.OC_LINE_START;
		for(int i = 0; i < this.organismClassification.size(); i++) {
			if(i == 0) {
				record +=this.organismClassification.get(i);
			}
			else {
				record += ", "+ this.organismClassification.get(i);
			}
		}
		return record.trim();
	}
}
