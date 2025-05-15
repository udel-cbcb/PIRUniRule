package edu.udel.bioinformatics.pirrule.uniprot;

import org.proteininformationresource.pirrule.uniprot.model.EntryOrganismSpecies;

import edu.udel.bioinformatics.pirrule.uniprot.io.UniProtFlatFileConstants;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 30, 2014<br>
 * <br>
 * 
 * Specifies the organism which was the source of the stored sequence. In the
 * rare case where all the species information will not fit on a single line,
 * more than one OS line is used. The last OS line is terminated by a period.
 */
public class EntryOrganismSpeciesImpl implements EntryOrganismSpecies {
	private String organismSpecies;

	
	public EntryOrganismSpeciesImpl(String organismSpecies) {
		super();
		this.organismSpecies = organismSpecies;
	}


	@Override
	public String getOrganismSpecies() {
		return this.organismSpecies;
	}
	
	public String toString() {
		return UniProtFlatFileConstants.OS_LINE_START+this.organismSpecies;
	}
	
}
