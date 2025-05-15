package edu.udel.bioinformatics.pirsr.prediction.annotation;

import java.util.List;

import org.proteininformationresource.pirsr.prediction.annotation.ProteinAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleAnnotation;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 28, 2015<br>
 * <br>
 * 
 * The implementation of <code>ProteinAnnotation</code>.
 */
public class ProteinAnnotationImpl implements ProteinAnnotation {
	private String proteinId;
	private int nucleotideStart;
	private int nucleotideEnd;
	private String strand;
	private String nucleotideSequeneId;
	private String trigger;
	private String ruleAC;
	private String templateAC;
	private String organismSpecies;
	private String organelle;
	private List<String> organismClassification;
	private String proteinSequence;
	private String nucleotideSequence;
	
	
	public ProteinAnnotationImpl(String proteinId) {
		super();
		this.proteinId = proteinId;
	}

	public String getProteinId() {
		return this.proteinId;
	}

	public void setProteinid(String proteinId) {
		this.proteinId = proteinId;
	}
	
	public String toString() {
		String record = "";
//		record += this.proteinId +"\t";
//		record += this.nucleotideSequeneId+ "\t";
//		record += this.strand+"\t";
//		record += this.nucleotideStart+"\t";
//		record += this.nucleotideEnd + "\t";
		
		return record;
	}

	public String toReport() {
		String report = "";
//		report += "\t";
//		report += "\t";
//		report += "?\t";
//		report += "?";
		return report;
	}

	public String toKraken() {
		String kraken = "";
		kraken += "\t";
		kraken += "\t";
		kraken += proteinId+"\t";
		kraken += "?\t";
		kraken += "?";
		
		return kraken;
	}

	public int getNucleotideORFStart() {
		return this.nucleotideStart;
	}

	public void setNucleotideORFStart(int nucleotideStart) {
		this.nucleotideStart = nucleotideStart;
	}

	public int getNucleotideORFEnd() {
		return this.nucleotideEnd;
	}

	public void setNucleotideORFEnd(int nucleotideEnd) {
		this.nucleotideEnd = nucleotideEnd;
	}

	public String getNucleotideStrand() {
		return this.strand;
	}

	public void setNucleotideStrand(String strand) {
		this.strand = strand;
	}

	public String getNucleotideSequenceId() {
		return this.nucleotideSequeneId;
	}

	public void setNucleotideSequenceId(String nucleotideSequeneId) {
		this.nucleotideSequeneId = nucleotideSequeneId;
	}

	public String getTrigger() {
		return this.trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getRuleAC() {
		return this.ruleAC;
	}

	public void setRuleAC(String ruleAC) {
		this.ruleAC = ruleAC;
	}

	public String getTemplateAC() {
		return this.templateAC;
	}

	public void setTemplateAC(String templateAC) {
		this.templateAC = templateAC;
	}

	public String getOrganelle() {
		return this.organelle;
	}

	public void setOrganelle(String organelle) {
		this.organelle = organelle;
	}

	public String getOrganismSpecies() {
		return this.organismSpecies;
	}

	public void setOrganismSpecies(String organismSpecies) {
		this.organismSpecies  = organismSpecies;
	}

	public List<String> getOrganismClassification() {
		return this.organismClassification;
	}

	public void setOrganismClassification(List<String> organismClassification) {
		this.organismClassification = organismClassification;
	}

	public String getNucleotideSequence() {
		return this.nucleotideSequence;
	}

	public void setNucleotideSequence(String nucleotideSequence) {
		this.nucleotideSequence = nucleotideSequence;
	}

	public String getProteinSequence() {
		return this.proteinSequence;
	}

	public void setProteinSequence(String proteinSequence) {
		this.proteinSequence = proteinSequence;
	}

	public String toXML() {
		// TODO Auto-generated method stub
		return "";
	}

	
}
