package org.proteininformationresource.pirsr.model.expression;

public enum OrganismConditionType {
	OS("OS"), OC("OC"), OG("OG");
	private String type;

	//
	// The constructor of ConditionType enum.
	//
	OrganismConditionType(String type) {
		this.type = type;
	}

	/**
	 * Get condition type
	 * 
	 * @return condition type
	 */
	public String getType() {
		return this.type;
	}
}
