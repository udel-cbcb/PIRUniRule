package edu.udel.bioinformatics.pirsr.propagation;

import org.proteininformationresource.pirsr.propagation.Alignment;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 8, 2014<br><br>
 * 
 * 
 * Holds the HMMER 3 alignment information by a given UniProt entry and corresponding template entry.
*/
public class AlignmentImpl implements Alignment{

	String entryId;
	String featureGenerator;
	String trigger;
	String ruleAC ;
	String pirsfID;
	int seqStart;
	int seqEnd;
	String selected;
	String seqAln ;
	String templateEntryAC;
	String templateAln;
	
	
	public String getEntryId() {
		return this.entryId;
	}

	
	public String getFeatureGenerator() {
		return this.featureGenerator;
	}

	
	public String getTrigger() {
		return this.trigger;
	}

	
	public String getRuleAC() {
		return this.ruleAC;
	}

	
	public String getPIRSFID() {
		return this.pirsfID;
	}

	
	
	public String getSelected() {
		return this.selected;
	}

	
	public String getAlignedSeq() {
		return this.seqAln;
	}

	
	public String getTemplateEntryAC() {
		return this.templateEntryAC;
	}

	
	public String getAlignedTemplateSeq() {
		return this.templateAln;
	}

	public AlignmentImpl() {
		super();
		
	}

	
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	
	public void setFeatureGenerator(String featureGenerator) {
		this.featureGenerator = featureGenerator;
	}

	
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	
	public void setRuleAC(String ruleAC) {
		this.ruleAC = ruleAC;
	}

	
	public void setPIRSFID(String pirsfID) {
		this.pirsfID = pirsfID;
	}

	
	public void setSeqStart(int seqStart) {
		this.seqStart = seqStart;
	}

	
	public int getSeqStart() {
		return this.seqStart;
	}

	
	public void setSeqEnd(int seqEnd) {
		this.seqEnd = seqEnd;
	}

	
	public int getSeqEnd() {
		return this.seqEnd;
	}

	
	public void setSelected(String selected) {
		this.selected = selected;
	}

	
	public void setAlignedSeq(String seqAln) {
		this.seqAln = seqAln;
	}

	
	public void setTemplateEntryAC(String templateEntryAC) {
		this.templateEntryAC = templateEntryAC;
	}

	
	public void setAlignedTemplateSeq(String templateAln) {
		this.templateAln = templateAln;
	}

	public SeqResidue[] getSeqResidues(String alnSeq) {
		char[] alnResidues = alnSeq.toCharArray();
		int alnLength = alnResidues.length;
		SeqResidue[] seqResidues = new SeqResidue[alnLength];
		int seqIndex = 0;
		for(int i = 0; i < alnLength; i++) {
			if(alnResidues[i] != '-') {
				seqIndex++;
				seqResidues[i] = new SeqResidue(alnResidues[i], i, seqIndex);
			}
			else {
				seqResidues[i] = new SeqResidue(alnResidues[i], i, -1);
			}
		}
		return seqResidues;
	}
	
}
