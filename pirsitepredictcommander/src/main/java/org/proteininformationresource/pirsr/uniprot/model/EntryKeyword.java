package org.proteininformationresource.pirsr.uniprot.model;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * Provides information that can be used to generate indexes of the sequence
 * entries based on functional, structural, or other categories. The keywords
 * chosen for each entry serve as a subject reference for the sequence. The
 * document keywlist.txt lists all the keywords and a definition of their usage
 * in the database. Often several KW lines are necessary for a single entry. The
 * list of keywords associated to one entry can be downloaded using the
 * following URL:
 * http://www.uniprot.org/uniprot/?query=reviewed%3ayes+keyword%3a
 * *&force=yes&format=tab&columns=id,keywords.
 * 
 * The format of the KW line is:
 * 
 * KW Keyword[; Keyword...]. 
 * 
 * More than one keyword may be listed on each KW
 * line; semicolons separate the keywords, and the last keyword is followed by a
 * period. An entry often contains several KW lines. Keywords may consist of
 * more than one word (they may contain blanks), but are never split between
 * lines. The keywords are stored by alphabetical order.
 */
public interface EntryKeyword extends EntryLine {

	/**
	 * Gets the keyword
	 * @return the keyword
	 */
	String getKeyword();
	
	String toReport();
}
