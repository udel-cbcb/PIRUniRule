package org.proteininformationresource.pirsr.model;

import java.util.List;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br><br>
 * 
 * The History section indicates the version number, date/time and author of the creation/modification.
 * 
 */
public interface HistorySectionVisitor {
	
		/**
		 * Visits a list of <code>HistoryInfo</code>s.
		 * @param historyInfos a list of <code>HistoryInfo</code>s.
		 */
		void visit(List<HistoryInfo> historyInfos);
		
}
