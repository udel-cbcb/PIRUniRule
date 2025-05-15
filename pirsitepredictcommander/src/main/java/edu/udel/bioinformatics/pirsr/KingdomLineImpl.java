package edu.udel.bioinformatics.pirsr;

import java.util.List;

import org.proteininformationresource.pirsr.model.KingdomLine;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2013<br><br>
 * 
 * The taxonomic classification is composed of the kingdom, optionally followed by the name of a sub-taxon, 
 * to further limit the application of the Site Rule to any taxonomic level. Valid values for kingdom are: 
 * "Eukaryota", "Bacteria", "Archaea", "Viruses", "Bacteriophage", "Plastid" and "Mitochondrion". 
 * The two latter values designate proteins encoded in the organelle genome but not proteins encoded 
 * in the nucleus and targeted to the organelle. 
 * If it has been assessed with certainty that a Site Rule is not represented in:
 *		A taxonomic group, its name may be indicated in the "except" field.
 * 		A complete proteome, its taxcode may be indicated in the "not in" field.
 */
public class KingdomLineImpl implements KingdomLine {
	
	private String kingdom;
	private String kingdomSubTaxon;
	private List<String> exceptTaxonomicGroups;
	private List<String> completeProteomeNotInTaxCodes;
	
	public KingdomLineImpl() {
		super();
	}

	public KingdomLineImpl(String kingdom, String kingdomSubTaxon,
			List<String> exceptTaxonomicGroups, List<String> completeProteomeNotInTaxCodes) {
		super();
		this.kingdom = kingdom;
		this.kingdomSubTaxon = kingdomSubTaxon;
		this.exceptTaxonomicGroups = exceptTaxonomicGroups;
		this.completeProteomeNotInTaxCodes = completeProteomeNotInTaxCodes;
	}

	
	public String getKingdom() {
		return this.kingdom;
	}

	
	public String getKingdomSubTaxon() {
		return this.kingdomSubTaxon;
	}

	
	public List<String> getExceptTaxonomicGroups() {
		return this.exceptTaxonomicGroups;
	}

	
	public List<String> getCompleteProteomeNotInTaxCodes() {
		return this.completeProteomeNotInTaxCodes;
	}

	
	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}

	
	public void setKingdomSubTaxon(String kingdomSubTaxon) {
		this.kingdomSubTaxon = kingdomSubTaxon;
	}

	
	public void setExceptTaxonomicGroups(List<String> exceptTaxonomicGroups) {
		this.exceptTaxonomicGroups = exceptTaxonomicGroups;
	}

	
	public void setCompleteProteomeNotInTaxCodes(List<String> completeProteomeNotInTaxCodes) {
		this.completeProteomeNotInTaxCodes = completeProteomeNotInTaxCodes;
	}

}
