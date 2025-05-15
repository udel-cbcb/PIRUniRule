package org.proteininformationresource.pirsr.iprscanmatch;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 27, 2015<br><br>
 * 
 * The matched protein sequence in InterProScan XML file
 */
public interface ProteinMatch {
	String getXrefId();
	String getSequence();
}
