package org.proteininformationresource.pirrule.uniprot.model;

import java.util.List;

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
public interface EntryOrganismClassification extends EntryLine {
	
	/**
	 * Gets a list of NCBI taxonomy of the source organism (Lineage) of the UniProt protein entry.
	 * @return a list of NCBI taxonomy of the source organism (Lineage) of the UniProt protein entry.
	 */
	List<String> getOrganismClassification();
}
