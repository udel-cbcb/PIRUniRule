package org.proteininformationresource.pirrule.profilehmm;

import java.util.LinkedHashMap;
import java.util.List;

public interface Template {

	String getAccession();
	LinkedHashMap<String, List<TemplateResidue>> getDomainAlignments();
	int getLength();
}
