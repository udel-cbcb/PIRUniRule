package org.proteininformationresource.pirrule.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br><br>
 * 
 * The information about the version number, date/time and author of the creation/modification.
 */

public interface HistoryInfo extends Line {
	
		/**
		 * Gets the version number of this history information.
		 * @return the version number of this history information.
		 */
		String getVersionNumber();
		
		/**
		 * Sets the version number of this history information.
		 * @param versionNumber the version number of this history information.
		 */
		void setVersionNumber(String versionNumber);
		
		/**
		 * Gets the curator of this history information.
		 * @return the curator of this history information.
		 */
		String getCurator();
		
		/**
		 * Sets the curator or this history information.
		 * @param curator the curator of this history information.
		 */
		void setCurator(String curator);
		
		/**
		 * Gets the date/time of this history information.
		 * @return the date/time of this history information.
		 */
		String getDateTime();
		
		/**
		 * Sets the date/time of this history information.
		 * @param dateTime the date/time of this history information.
		 */
		void setDataTime(String dateTime);
		
		/**
		 * Gets the type of history info. Either creation or lastModification.
		 * @return the type of history information.
		 */
		String getType();
		
		/**
		 * Sets the type of history info. Either creation or lastModification.
		 * @param type the type of history information.
		 */
		void setType(String type);
		
		
		String toString();

}
