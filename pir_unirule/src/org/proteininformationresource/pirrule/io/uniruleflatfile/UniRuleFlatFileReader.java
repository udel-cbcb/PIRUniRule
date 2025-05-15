package org.proteininformationresource.pirrule.io.uniruleflatfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.proteininformationresource.pirrule.model.AnnotationSection;
import org.proteininformationresource.pirrule.model.Block;
import org.proteininformationresource.pirrule.model.ComputingSection;
import org.proteininformationresource.pirrule.model.PIRRuleDataFactory;
import org.proteininformationresource.pirrule.model.HeaderSection;
import org.proteininformationresource.pirrule.model.HistorySection;
import org.proteininformationresource.pirrule.model.PIRRule;
import org.proteininformationresource.pirrule.model.PIRRuleManager;
import org.proteininformationresource.pirrule.model.RuleSection;

public class UniRuleFlatFileReader {

	private PIRRuleManager siteRuleManager;
	private PIRRuleDataFactory dataFactory;
	private boolean isStrict = false;
	private boolean debug = false;
	private List<PIRRule> siteRules = new ArrayList<PIRRule>();
	private Logger logger;
	private UniRuleFlatFileInputStream pirsrInputStream;
	ArrayList<UniRuleFlatFileParserLog> readerLogs = new ArrayList<UniRuleFlatFileParserLog>();

	String currentRuleAC = null;
	UniRuleFlatFileParserLog currentReaderLog = null;

	/**
	 * 
	 * @param siteRuleManager
	 * @param isStrict
	 */
	public UniRuleFlatFileReader(PIRRuleManager siteRuleManager, boolean isStrict, boolean debug) {
		this.siteRuleManager = siteRuleManager;
		this.isStrict = isStrict;
		this.debug = debug;
		this.dataFactory = this.siteRuleManager.getDataFactory();
		this.pirsrInputStream = new UniRuleFlatFileInputStream();
	}

	/**
	 * 
	 * @param path
	 */
	public void parse(String path) {
		if (path.startsWith("http:") || path.startsWith("https:") || path.startsWith("ftp:")) {
			try {
				parse(new URL(path));
			} catch (MalformedURLException e) {
				logger.severe("MalformedURL: " + path);
				logger.severe(e.toString());
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			parse(new File(path));
		}
	}

	private void parse(File file) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			parse(reader);
		} catch (FileNotFoundException e) {
			logger.severe("FileNotFound: " + file);
			logger.severe(e.toString());
			e.printStackTrace();
			System.exit(1);
		}

	}

	private void parse(URL url) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			parse(reader);
		} catch (IOException e) {
			logger.severe("IOException: " + url);
			logger.severe(e.toString());
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void parse(BufferedReader reader) {
		this.pirsrInputStream.setReader(reader);
		this.pirsrInputStream.setLogger(logger);
		this.pirsrInputStream.advanceLine();
		String currentLine = null;
		while (this.pirsrInputStream.line != null) {
			if (debug) {
				System.out.println(this.pirsrInputStream.getLineNo() + ": " + this.pirsrInputStream.line);
			}
			this.pirsrInputStream.advanceLine();
			currentLine = this.pirsrInputStream.line;
			if(currentLine.startsWith(UniRuleFlatFileConstants.AC_LINE_START)) {
				
			}
		}
	}

	
	private PIRRule parseSingleSiteRule() {
		HeaderSection headerSection = null;
		AnnotationSection annotationSection = null;
		ComputingSection computingSection = null;
		HistorySection historySection = null;
		// parseRuleSeparator();

		PIRRule parsedSiteRule = this.dataFactory.buildPIRRule(siteRuleManager, headerSection, annotationSection, computingSection, historySection);
		return parsedSiteRule;
	}

	private void parseRuleSeparator() {

		if (!this.pirsrInputStream.line.equals(UniRuleFlatFileConstants.RULE_SEPARATOR)) {
			String message = this.pirsrInputStream.toString() + " " + "Missing rule separater \"//\".";
			logger.severe(message);
			currentReaderLog.addMessage(message);
			if (this.isStrict) {
				System.exit(1);
			}
		}
	}

	private HistorySection parseHistorySection() {
		// TODO Auto-generated method stub
		return null;
	}

	private ComputingSection parseComputingSection() {
		// TODO Auto-generated method stub
		return null;
	}

	private AnnotationSection parseAnnotationSection() {
		// TODO Auto-generated method stub
		return null;
	}

	private HeaderSection parseHeaderSection() {
		// TODO Auto-generated method stub
		return null;
	}

}
