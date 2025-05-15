package edu.udel.bioinformatics.pirrule.uniprot;

import org.proteininformationresource.pirrule.uniprot.model.EntryOrganelle;

import edu.udel.bioinformatics.pirrule.uniprot.io.UniProtFlatFileConstants;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 30, 2014<br>
 * <br>
 * 
 * Indicates if the gene coding for a protein originates from mitochondria, a
 * plastid, a nucleomorph or a plasmid.
 */
public class EntryOrganelleImpl implements EntryOrganelle {
	private String organelle;
	
	

	public EntryOrganelleImpl(String organelle) {
		super();
		this.organelle = organelle;
	}
	

	@Override
	public String getOrganelle() {
		return this.organelle;
	}
	
	public String toString() {
		return UniProtFlatFileConstants.OG_LINE_START+this.organelle;
	}

}
