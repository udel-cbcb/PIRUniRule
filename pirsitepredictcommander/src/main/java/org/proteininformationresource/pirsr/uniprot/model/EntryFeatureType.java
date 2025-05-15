package org.proteininformationresource.pirsr.uniprot.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * The type of sequence features.
 */
public enum EntryFeatureType {
	ACT_SITE("ACT_SITE"),
	BINDING("BINDING"),
	CA_BIND("CA_BIND"),
	CARBOHYD("CARBOHYD"),
	CHAIN("CHAIN"),
	COILED("COILED"),
	COMPBIAS("COMPBIAS"),
	CONFLICT("CONFLICT"),
	CROSSLNK("CROSSLINK"),
	DISULFID("DISULFID"),
	DNA_BIND("DNA_BIND"),
	DOMAIN("DOMAIN"),
	HELEX("HELEX"),
	HELIX("HELIX"),
	INT_MET("INT_MET"),
	INIT_MET("INIT_MET"),
	INTRAMEM("INTRAMEME"),
	LIPID("LIPID"),
	METAL("METAL"),
	MOD_RES("MOD_RES"),
	MOTIF("MOTIF"),
	MUTAGEN("MUTAGEN"),
	NON_CONS("NON_CONS"),
	NON_STD("NON_STD"),
	NON_TER("NON_TER"),
	NP_BIND("NP_BIND"),
	PEPTIDE("PEPTIDE"),
	PROPEP("PROPEP"),
	REGION("REGION"),
	REPEAT("REPEAT"),
	SIGNAL("SIGNAL"),
	SITE("SITE"),
	STRAND("STRAND"),
	TOPO_DOM("TOPO_DOM"),
	TRANSIT("TRANSIT"),
	TRANSMEM("TRANSMEM"),
	TURN("TURN"),
	UNSURE("UNSURE"),
	VAR_SEQ("VAR_SEQ"),
	VARIANT("VARIANT"),
	ZN_FING("ZN_FING");
	
	private String value;
	
	EntryFeatureType(String value) {
		this.value = value;
	}
	
	String getValue() {
		return this.value;
	}
}
