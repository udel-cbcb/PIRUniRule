package org.proteininformationresource.pirrule.model;

import java.util.List;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br><br>
 * 
 * The Scope Block that lists the taxonomic classes in which a rule match may be found.
 * The taxonomic classification is composed of the kingdom, optionally followed by the name of a sub-taxon, 
 * to further limit the application of the PIR Rule to any taxonomic level. Valid values for kingdom are: 
 * "Eukaryota", "Bacteria", "Archaea", "Viruses", "Bacteriophage", "Plastid" and "Mitochondrion". 
 * The two latter values designate proteins encoded in the organelle genome but not proteins encoded 
 * in the nucleus and targeted to the organelle. 
 * If it has been assessed with certainty that a PIR Rule is not represented in:
 *		A taxonomic group, its name may be indicated in the "except" field.
 * 		A complete proteome, its taxcode may be indicated in the "not in" field.
 * 
 */
public interface ScopeBlock extends Line{
	
		/**
		 * Gets a list of <code>KingdomLine</code>s.
		 * @return a list of <code>KingdomLine</code>s.
		 */
		List<KingdomLine> getKingdomLines();
		
		/**
		 * Sets a list of <code>KingdomLine</code>s.
		 * @param kingdomLine a list of <code>KingdomLine</code>s.
		 */
		void setKingdomLines(List<KingdomLine> kingdomLine);
		
	

}
