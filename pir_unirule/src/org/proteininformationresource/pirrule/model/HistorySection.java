package org.proteininformationresource.pirrule.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * The History section indicates the version number, date/time and author of the
 * creation/modification.
 * 
 */
public interface HistorySection extends RuleSection {

	/**
	 * Gets a list of <code>HistoryInfo</code>.
	 * 
	 * @return the list of <code>HistoryInfo</code>.
	 */
	List<HistoryInfo> getHistoryInfos();

	/**
	 * Sets a list of <code>HistoryInfo</code>.
	 * 
	 * @param historyInfos
	 *            the list of <code>HistoryInfo</code>.
	 */
	void setHistoryInfos(List<HistoryInfo> historyInfos);

	/**
	 * A visitor which visits the different components of the
	 * <code>HistorySection</code>.
	 * 
	 * @param visitor
	 *            which visits the different components of the
	 *            <code>HistorySection</code>.
	 */
	void accept(HistorySectionVisitor visitor);

}
