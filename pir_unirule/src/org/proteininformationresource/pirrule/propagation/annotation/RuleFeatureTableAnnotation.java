package org.proteininformationresource.pirrule.propagation.annotation;

import org.proteininformationresource.pirrule.model.FeatureType;
import org.proteininformationresource.pirrule.model.FeatureDescriptionLine;

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
