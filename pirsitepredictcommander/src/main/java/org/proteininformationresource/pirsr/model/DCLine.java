package org.proteininformationresource.pirsr.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * DC (Data Class) of the rule, which specifies which type of data the rule
 * annotates.
 */
public interface DCLine extends Line {

	/**
	 * Gets the DC (Data Class) of the rule, which specifies which type of data
	 * the rule annotates.
	 * 
	 * @return the DC (Data Class) of the rule, which specifies which type of
	 *         data the rule annotates.
	 */
	String getDataClass();

	/**
	 * Sets the DC (Data Class) of the rule, which specifies which type of data
	 * the rule annotates.
	 * 
	 * @param dataClass
	 *            the DC (Data Class) of the rule, which specifies which type of
	 *            data the rule annotates.
	 */
	void setDataClass(String dataClass);

}
