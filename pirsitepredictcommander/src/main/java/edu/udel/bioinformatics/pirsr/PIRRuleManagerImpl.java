package edu.udel.bioinformatics.pirsr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.proteininformationresource.pirsr.io.html.PIRRuleHTMLWriter;
import org.proteininformationresource.pirsr.io.uniruleflatfile.UniRuleFlatFileParser;
import org.proteininformationresource.pirsr.io.uniruleflatfile.UniRuleFlatFileParserException;
import org.proteininformationresource.pirsr.model.Line;
import org.proteininformationresource.pirsr.model.PIRRule;
import org.proteininformationresource.pirsr.model.PIRRuleDataFactory;
import org.proteininformationresource.pirsr.model.PIRRuleManager;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * A <code>PIRRuleManager</code> manages a set of Site Rules. It is the main
 * point for creating, loading and accessing Site Rules.
 */
public class PIRRuleManagerImpl implements PIRRuleManager {

	private PIRRuleDataFactory dataFactory = null;
	private List<PIRRule> pirrules = null;
	private String parserLogs = null;

	public PIRRuleManagerImpl(PIRRuleDataFactory dataFactory) {
		super();
		this.dataFactory = dataFactory;
	}

	
	public PIRRuleDataFactory getDataFactory() {
		return this.dataFactory;
	}

	
	public List<PIRRule> parsePIRRules(URL url, Format format, Boolean isStrict, String lineNumberType) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			if (format == PIRRuleManager.Format.UNIRULE) {
				UniRuleFlatFileParser parser = new UniRuleFlatFileParser(this, isStrict, br);
				try {
					parser.parse();
				} catch (UniRuleFlatFileParserException e) {
					
					Logger.getLogger(PIRRuleManager.class.getName()).log(Level.SEVERE, null, e);
				}
				this.pirrules = parser.getPIRRules();
				this.parserLogs = parser.getParserLogs(lineNumberType);
				
			}
			br.close();
		} catch (IOException e) {
			Logger.getLogger(PIRRuleManager.class.getName()).log(Level.SEVERE, null, e);
		}
		return this.pirrules;
	}

	
	public List<PIRRule> parsePIRRules(File file, Format format, Boolean isStrict, String lineNumberType) {

		try {

			BufferedReader br = new BufferedReader(new FileReader(file));
			if (format == PIRRuleManager.Format.UNIRULE) {
				UniRuleFlatFileParser parser = new UniRuleFlatFileParser(this, isStrict, br);

				try {
					
					parser.parse();
					
					this.pirrules = parser.getPIRRules();
					this.parserLogs = parser.getParserLogs(lineNumberType);
					
				} catch (UniRuleFlatFileParserException e) {
					
					Logger.getLogger(PIRRuleManager.class.getName()).log(Level.SEVERE, null, e);
				}
				
				
			}
			br.close();

		} catch (FileNotFoundException e) {
			Logger.getLogger(PIRRuleManager.class.getName()).log(Level.SEVERE, null, e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.pirrules;
	}

	
	public List<PIRRule> parsePIRRules(InputStream inputStream, Format format, Boolean isStrict, String lineNumberType) {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			if (format == PIRRuleManager.Format.UNIRULE) {
				UniRuleFlatFileParser uniruleParser = new UniRuleFlatFileParser(this, isStrict, br);
				try {
					uniruleParser.parse();
					this.pirrules = uniruleParser.getPIRRules();
					this.parserLogs = uniruleParser.getParserLogs(lineNumberType);
				} catch (UniRuleFlatFileParserException e) {
					Logger.getLogger(PIRRuleManager.class.getName()).log(Level.SEVERE, null, e);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.pirrules;
	}

	
	public void savePIRRules(List<PIRRule> pirrules, File file, Format format) {
		String pirruleContents = "";
		if (format == PIRRuleManager.Format.UNIRULE) {
			for (int i = 0; i < pirrules.size(); i++) {
				PIRRule pirrule = pirrules.get(i);

				HeaderSectionUniRuleWriterVisitor headerSectionUniRuleWriterVisitor = new HeaderSectionUniRuleWriterVisitor();
				pirrule.getHeaderSection().accept(headerSectionUniRuleWriterVisitor);
				pirruleContents += headerSectionUniRuleWriterVisitor.getHeaderSection() + "\n";
				AnnotationSectionUniRuleWriterVisitor annotationSectionUniRuleWriterVisitor = new AnnotationSectionUniRuleWriterVisitor();
				pirrule.getAnnotationSection().accept(annotationSectionUniRuleWriterVisitor);
				pirruleContents += annotationSectionUniRuleWriterVisitor.getAnnotationSection() + "\n";
				ComputingSectionUniRuleWriterVisitor computingSectionUniRuleWriterVisitor = new ComputingSectionUniRuleWriterVisitor();
				pirrule.getComputingSection().accept(computingSectionUniRuleWriterVisitor);
				pirruleContents += computingSectionUniRuleWriterVisitor.getComputingSection() + "\n";
				HistorySectionUniRuleWriterVisitor historySectionUniRuleWriterVisitor = new HistorySectionUniRuleWriterVisitor();
				pirrule.getHistorySection().accept(historySectionUniRuleWriterVisitor);
				pirruleContents += historySectionUniRuleWriterVisitor.getHistorySection() + "\n";
				pirruleContents += "//";
			}
		}

		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			fw.write(pirruleContents);
		} catch (IOException ioe) {
			Logger.getLogger(PIRRuleManager.class.getName()).log(Level.SEVERE, null, ioe);
		} finally {
			try {
				fw.close();
			} catch (IOException ioe) {
				Logger.getLogger(PIRRuleManager.class.getName()).log(Level.SEVERE, null, ioe);
			}
		}

	}

	
	public List<PIRRule> getPIRRules() {
		return this.pirrules;
	}

	// @SuppressWarnings("unchecked")
	
	public List<PIRRule> getPIRRulesOrderedByAC(final String sortingOrder) {

		Collections.sort(this.pirrules, new Comparator<Object>() {
			// 
			// 
			public int compare(Object o1, Object o2) {
				String ruleAC1 = ((PIRRule) o1).getHeaderSection().getACLine().getAccessionNumber();
				String ruleAC2 = ((PIRRule) o2).getHeaderSection().getACLine().getAccessionNumber();
				if (sortingOrder.equals("ASC")) {
					return ruleAC1.compareTo(ruleAC2);
				} else {
					return ruleAC2.compareTo(ruleAC1);
				}
			}
		});
		List<PIRRule> sortedPIRRules = new ArrayList<PIRRule>();
		for (int i = 0; i < this.pirrules.size(); i++) {
			sortedPIRRules.add(this.pirrules.get(i));
		}
		return sortedPIRRules;
	}

	
	public boolean addPIRRule(PIRRule pirrule) {

		if (pirrule != null) {
			this.pirrules.add(pirrule);
			return true;
		} else {
			return false;
		}

	}

	
	public boolean removePIRRule(String pirruleAC) {
		Iterator<PIRRule> it = this.pirrules.iterator();
		while (it.hasNext()) {
			PIRRule pirrule = it.next();
			if (pirrule.getHeaderSection().getACLine().getAccessionNumber().contains(pirruleAC)) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	
	public void printPIRRules(List<PIRRule> pirrules, Format format, PrintWriter writer) {
		//if (format == PIRRuleManager.Format.UNIRULE) {
			for (int i = 0; i < pirrules.size(); i++) {
				PIRRule pirrule = pirrules.get(i);
				printPIRRule(pirrule, format, writer);

			}
		//}

	}

	
	public boolean removePIRRule(PIRRule pirrule) {
		String pirruleAC = pirrule.getHeaderSection().getACLine().getAccessionNumber();
		Iterator<PIRRule> it = this.pirrules.iterator();

		while (it.hasNext()) {
			PIRRule sr = it.next();
			if (sr.getHeaderSection().getACLine().getAccessionNumber().equals(pirruleAC)) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	
	public boolean replacePIRRule(PIRRule pirrule) {
		String pirruleAC = pirrule.getHeaderSection().getACLine().getAccessionNumber();
		if (this.pirrules != null && this.pirrules.size() > 0) {
			for (int i = 0; i < this.pirrules.size(); i++) {
				PIRRule sr = this.pirrules.get(i);
				if (sr.getHeaderSection().getACLine().getAccessionNumber().equals(pirruleAC)) {
					this.pirrules.set(i, pirrule);
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}

	
	public void printPIRRule(PIRRule pirrule, Format format, PrintWriter writer) {
		
		if (format.equals(Format.UNIRULE)) {
			HeaderSectionUniRuleWriterVisitor headerSectionUniRuleWriterVisitor = new HeaderSectionUniRuleWriterVisitor();
			if (pirrule.getHeaderSection() != null) {
				pirrule.getHeaderSection().accept(headerSectionUniRuleWriterVisitor);
				writer.print(headerSectionUniRuleWriterVisitor.getHeaderSection());
			}
			AnnotationSectionUniRuleWriterVisitor annotationSectionUniRuleWriterVisitor = new AnnotationSectionUniRuleWriterVisitor();
			if (pirrule.getAnnotationSection() != null) {
				pirrule.getAnnotationSection().accept(annotationSectionUniRuleWriterVisitor);
				writer.print(annotationSectionUniRuleWriterVisitor.getAnnotationSection());
			}
			ComputingSectionUniRuleWriterVisitor computingSectionUniRuleWriterVisitor = new ComputingSectionUniRuleWriterVisitor();
			if (pirrule.getComputingSection() != null) {
				pirrule.getComputingSection().accept(computingSectionUniRuleWriterVisitor);
				writer.print(computingSectionUniRuleWriterVisitor.getComputingSection());
			}
			HistorySectionUniRuleWriterVisitor historySectionUniRuleWriterVisitor = new HistorySectionUniRuleWriterVisitor();
			if (pirrule.getHistorySection() != null) {
				pirrule.getHistorySection().accept(historySectionUniRuleWriterVisitor);
				writer.print(historySectionUniRuleWriterVisitor.getHistorySection());
			}
//			for(Line line : pirrule.getRuleLines()) {
//				writer.print(line.toString());
//			}
			writer.println("//");
		}
		else if(format.equals(Format.HTML)) {
			PIRRuleHTMLWriter htmlWriter = new PIRRuleHTMLWriter(pirrule);
			//System.out.println(pirrule.getHeaderSection().getACLine());
			//System.out.print("???"+htmlWriter.getHTML());
			if(writer == null) {
				System.out.print(htmlWriter.getHTML());
			}
			else {
				writer.print(htmlWriter.getHTML());
			}
//			for(Line line : pirrule.getRuleLines()) {
//				System.out.print(line.toString());
//			}
		}
	}
	
	public String getParserLogs() {
		return this.parserLogs;
	}

	
	public PIRRule getRuleByAC(String ruleAC) {
		for(PIRRule rule: pirrules) {
			if(rule.getHeaderSection().getACLine().getAccessionNumber().equals(ruleAC)) {
				return rule;
			}
		}
		return null;
	}

	

}
