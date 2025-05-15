package org.proteininformationresource.pirsr.uniprot.model;

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

public interface EntryOrganelle extends EntryLine {
	
	/**
	 * Gets the organelle of the protein entry.
	 * @return the organelle of the protein entry.
	 */
	String getOrganelle();
}
