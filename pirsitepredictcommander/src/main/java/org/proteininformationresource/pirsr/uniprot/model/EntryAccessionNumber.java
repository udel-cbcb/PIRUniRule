package org.proteininformationresource.pirsr.uniprot.model;

import java.util.List;

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
public interface EntryAccessionNumber extends EntryLine {
	
	/**
	 * Gets the primary accession number (the first one listed).
	 * @return the primary accession number (the first one listed).
	 */
	String getPrimaryAccessionNumber();

	/**
	 * Gets the secondary accession numbers (the ones after the first one).
	 * @return the secondary accession numbers (the ones after the first one).
	 */
	List<String> getSecondaryAccessionNumbers();
}
