package org.proteininformationresource.pirrule.model;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * A <code>PIRRuleManager</code> manages a set of PIR Rules. It is the main
 * point for creating, loading and accessing PIR Rules.
 */
public interface PIRRuleManager {

	public enum Format {
		UNIRULE("UNIRULE"), XML("XML"), HTML("HTML");
		
		private String format;
		
		Format (String format) {
			this.format = format;
		}
		String getFormat() {
			return this.format;
		}
	}

	/*
	 * Gets a data factory which can be used to create PIR Rule objects such as
	 * entities, lines and conditions etc.
	 * 
	 * @return A reference to a data factory for creating PIR Rule objects.
	 */
	PIRRuleDataFactory getDataFactory();

	/**
	 * Gets PIR Rules managed by this manager.
	 * 
	 * @return a list of <code>PIRRule</code>s.
	 */
	List<PIRRule> getPIRRules();

	/**
	 * Gets PIR Rules managed by this manager ordered by the rule accession
	 * number.
	 * 
	 * @param sortingOrder
	 *            The sorting order either asc or desc alphabetically
	 * @return a list of <code>PIRRule</code>s ordered by their accession
	 *         numbers.
	 */
	List<PIRRule> getPIRRulesOrderedByAC(String sortingOrder);

	/**
	 * Add a PIR Rule if it is not managed by this manager. accession number.
	 * 
	 * @param pirrule
	 *            A <code>PIRRule</code> to be added.
	 * @return true if success, false otherwise.
	 */
	boolean addPIRRule(PIRRule pirrule);

	/**
	 * Replace a PIR Rule with the same rule accession number as the given PIR
	 * Rule.
	 * 
	 * @param pirrule
	 *            A <code>PIRRule</code> replaces the existing PIR Rule with the
	 *            same rule accession number.
	 * @return true if a PIR Rule with the same rule accession number of the
	 *         given PIR Rule is managed by the manager and have been
	 *         successfully replaced, false otherwise.
	 */
	boolean replacePIRRule(PIRRule pirrule);

	/**
	 * Remove a PIR Rule with the specified accession number
	 * 
	 * @param pirruleAC
	 *            A PIR Rule accession number
	 * @return true if the PIR Rule with specified accession number is managed
	 *         by the manager and has been removed. false otherwise.
	 */
	boolean removePIRRule(String pirruleAC);

	/**
	 * Remove a PIR Rule from manager using the rule accession number of the
	 * given PIR Rule.
	 * 
	 * @param pirrule
	 *            A <code>PIRRule</code>
	 * @return true if the PIR Rule with specified accession number is managed
	 *         by the manager and has been removed. false otherwise.
	 */
	boolean removePIRRule(PIRRule pirrule);

	/**
	 * Parse a list of <code>PIRRule</code>s specified by an URL.
	 * 
	 * @param url
	 *            url can be used to obtain the representation of a list of
	 *            <code>PIRRule</code>s.
	 * @param format
	 *            The format of a PIR Rule, can be either "UniRule" flat file or
	 *            XML format.
	 * @param isStrict
	 *            if set to true, program will exit on error.
	 * @return a list of <code>PIRRule</code>.
	 */
	List<PIRRule> parsePIRRules(URL url, Format format, Boolean isStrict, Boolean debug, String lineNumberType);

	/**
	 * Parse a list of <code>PIRRule</code>s contained in a local file.
	 * 
	 * @param file
	 *            A local file containing a list of <code>PIRRule</code>s.
	 * @param format
	 *            The format of a PIR Rule, can be either "UniRule" flat file or
	 *            XML format.
	 * @param isStrict
	 *            if set to true, program will exit on error.
	 * @param string 
	 * @return a list of <code>PIRRule</code>
	 */
	List<PIRRule> parsePIRRules(File file, Format format, Boolean isStrict, Boolean debug, String lineNumberType);

	/**
	 * Parse a list of <code>PIRRule</code> from an input stream.
	 * 
	 * @param inputStream
	 *            InputStream that can be used to obtain the representation of a
	 *            list of <code>PIRRule</code>.
	 * @param format
	 *            The format of a PIR Rule, can be either "UniRule" flat file or
	 *            XML format.
	 * @param isStrict
	 *            if set to true, program will exit on error.
	 * @return a list of <code>PIRRule</code>
	 */
	List<PIRRule> parsePIRRules(InputStream inputStream, Format format, Boolean isStrct, Boolean debug, String lineNumberType);

	/**
	 * Save the list of <code>PIRRule</code>s as a local file.
	 * 
	 * @param pirrules
	 *            A list of <code>PIRRule</code> to be saved.
	 * @param file
	 *            A local file.
	 * @param format
	 *            The format of a PIR Rule, can be either "UniRule" flat file or
	 *            XML format.
	 */
	void savePIRRules(List<PIRRule> pirrules, File file, Format format);

	/**
	 * Print out a list of <code>PIRRule</code>s.
	 * 
	 * @param pirrules
	 *            A list of <code>PIRRule</code> to be printed.
	 * @param format
	 *            The format of a PIR Rule, can be either "UniRule" flat file,
	 *            XML or HTML format.
	 * @param writer
	 *            PrintWriter
	 */
	void printPIRRules(List<PIRRule> pirrules, Format format, PrintWriter writer);

	/**
	 * Print out a <code>PIRRule</code>.
	 * 
	 * @param pirrule
	 *            a <code>PIRRule</code>.
	 * @param format
	 *            The format of a PIR Rule, can be either "UniRule" flat file,
	 *            XML or HTML format.
	 * @param writer
	 *            PrintWriter
	 * 
	 */
	void printPIRRule(PIRRule pirrule, Format format, PrintWriter writer);

	String getParserLogs();
	
	/**
	 * Gets a <code>PIRRule</code>
	 * @param ruleAC the rule accession number
	 * @return a <code>PIRRule</code>.
	 */
	PIRRule getRuleByAC(String ruleAC);
}
