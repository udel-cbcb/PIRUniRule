package org.proteininformationresource.pirsr.iprscanmatch;

import java.util.List;

import org.proteininformationresource.pirsr.interproscan5.model.OrfType;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 27, 2015<br><br>
 * 
 * The matched nucleotide sequence in InterProScan XML file
 */
public interface NucleotideMatch {
	String getXrefId();
	String getSequence();
	List<OrfType> getOrfList();
}
