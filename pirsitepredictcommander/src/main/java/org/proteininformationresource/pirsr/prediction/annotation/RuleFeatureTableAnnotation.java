package org.proteininformationresource.pirsr.prediction.annotation;

import org.proteininformationresource.pirsr.model.FeatureDescriptionLine;
import org.proteininformationresource.pirsr.model.FeatureType;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 * 
 */
public interface RuleFeatureTableAnnotation extends RuleAnnotation {

	/**
	 * Gets the feature type.
	 * @return the feature type.
	 */
	FeatureType getFeatureType();
	
	/**
	 * Gets the feature description line.
	 * @return the feature description line.
	 */
	FeatureDescriptionLine getFeatureDescriptionLine() ;
	
	/**
	 * Gets the description.
	 * @return the description.
	 */
	String getDescription();
}
