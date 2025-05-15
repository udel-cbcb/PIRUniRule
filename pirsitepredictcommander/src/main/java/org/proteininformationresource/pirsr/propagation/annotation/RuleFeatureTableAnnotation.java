package org.proteininformationresource.pirsr.propagation.annotation;

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

	FeatureType getFeatureType();
	
	FeatureDescriptionLine getFeatureDescriptionLine() ;
	
	String getDescription();
}
