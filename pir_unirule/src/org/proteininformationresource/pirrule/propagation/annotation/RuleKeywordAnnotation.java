package org.proteininformationresource.pirrule.propagation.annotation;

import org.proteininformationresource.pirrule.model.KWLine;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 * 
 */
public interface RuleKeywordAnnotation extends RuleAnnotation {

	/**
	 * Gets the KWLine.
	 * @return the KWLine.
	 */
	KWLine getKeyword();
	
	
}
