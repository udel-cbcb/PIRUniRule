package org.proteininformationresource.pirrule.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 *
 */
public enum FeatureType {

	ACT_SITE("ACT_SITE"),
	BINDING("BINDING"),
	CARBOHYD("CARBOHYD"),
    CHAIN("CHAIN"),
	CROSSLNK("CROSSLNK"),
	DISULFID("DISULFID"),
	DNA_BIND("DNA_BIND"),
	LIPID("LIPID"),
	METAL("METAL"),
	MOD_RES("MOD_RES"),
	MOTIF("MOTIF"),
	NON_STD("NON_STD"),
	NP_BIND("NP_BIND"),
	PROPEP("PROPEP"),
	REGION("REGION"),
	SITE("SITE"),
	ZN_FING("ZN_FING");
	
	private String feature;
	
	FeatureType(String feature) {
		this.feature = feature;
		
	}
	
	public String getFeature() {
		return this.feature;
		
	}
}
