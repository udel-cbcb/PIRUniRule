package org.proteininformationresource.pirrule.propagation;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.proteininformationresource.pirrule.model.PIRRuleManager;
import org.proteininformationresource.pirrule.uniprot.model.UniProtEntry;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 8, 2014<br><br>
 * 
 * 
 * Manage annotation related tasks.
*/
public interface PropagationManager {
	
	/**
	 * Gets the <code>PropagationDataFactory</code>.
	 * @return the <code>PropagationDataFactory</code>.
	 */
	PropagationDataFactory getPropagationDataFactory();
	
	/**
	 * Load a list of <code>Alignment</code>s specified by an URL.
	 * @param url can be used to obtain a list of <code>Alignment</code>s.
	 * @return a list of <code>Alignment</code>.
	 */
	List<Alignment> loadAlignment(URL url);

	
	
	/**
	 * Load a list of <code>Alignment</code>s contained in a local file.
	 * @param file A local file containing a list of <code>Alignment</code>s.
	 * @return a list of <code>Alignment</code>s.
	 */
	List<Alignment> loadAlignment(File file);
	
	
	
	
	/**
	 * Load a list of <code>Alignment</code>s from an input stream.
	 * @param inputStream that can be used to obtain the list of <code>Alignment</code>.
	 * @return a list of <code>Alignment</code>.
	 */
	List<Alignment> loadAlignment(InputStream inputStream);
	
	/**
	 * Gets an alignment
	 * @param ruleAC rule accession number
	 * @param entryId entry ID
	 * @param templateAC template accession number
	 * @return an <code>Alignment</code>.
	 */
	Alignment getAlignment(String ruleAC, String entryId, String templateAC);
	
	/**
	 * Load a list of UniProt flat file entries from URL
	 * @param url can be used to load a list of UniProt flat file entries.
	 * @return a list of <code>UniPrtoEntry</code>s.
	 */
	List<UniProtEntry> loadEntry(URL url);
	
	/**
	 * Load a list of UniProt flat file entries from a local file
	 * @param file a local UniProt flat file.
	 * @return a list of <code>UniPrtoEntry</code>s.
	 */
	List<UniProtEntry> loadEntry(File file);
	
	/**
	 * Load a list of UniProt flat file entries from an InputStream.
	 * @param inputStream hat can be used to obtain the list of UniProt flat file entries.
	 * @return a list of <code>UniPrtoEntry</code>s.
	 */
	List<UniProtEntry> loadEntry(InputStream inputStream);
	
	/**
	 * Gets an <code>Alignment</code>.
	 * @param entryId UniProt entry identifier.
	 * @param ruleAC rule accession number.
	 * @return an <code>Alignment</code>.
	 */
	Alignment getAlignmentByEntryAndRule(String entryId, String ruleAC);
	
	/**
	 * Gets an <code>UniProtEntry</code>.
	 * @param entryPrimaryAC UniProt entry primary accession number.
	 * @return an <code>UniProtEntry</code>.
	 */
	UniProtEntry getEntryByPrimaryAC(String entryPrimaryAC);
	
	
	/**
	 * Gets an <code>UniProtEntry</code>.
	 * @param entryId UniProt entry identifier.
	 * @return an <code>UniProtEntry</code>.
	 */
	UniProtEntry getEntryById(String entryId);
	
	PIRRuleManager getPIRRuleManager();

	void propagate(String propagationDirectory);

}
