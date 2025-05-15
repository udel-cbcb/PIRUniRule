package org.proteininformationresource.pirsr.uniprot.model;

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
public interface EntryOrganismSpecies extends EntryLine {
	
	/**
	 * Gets the organism of the protein sequence.
	 * @return the organism of the protein sequence.
	 */
	String getOrganismSpecies();
}
