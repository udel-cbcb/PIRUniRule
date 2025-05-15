package edu.udel.bioinformatics.pirrule.propagation;

import org.proteininformationresource.pirrule.propagation.Alignment;

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
	
	@Override
	public String getEntryId() {
		return this.entryId;
	}

	@Override
	public String getFeatureGenerator() {
		return this.featureGenerator;
	}

	@Override
	public String getTrigger() {
		return this.trigger;
	}

	@Override
	public String getRuleAC() {
		return this.ruleAC;
	}

	@Override
	public String getPIRSFID() {
		return this.pirsfID;
	}

	
	@Override
	public String getSelected() {
		return this.selected;
	}

	@Override
	public String getAlignedSeq() {
		return this.seqAln;
	}

	@Override
	public String getTemplateEntryAC() {
		return this.templateEntryAC;
	}

	@Override
	public String getAlignedTemplateSeq() {
		return this.templateAln;
	}

	public AlignmentImpl() {
		super();
		
	}

	@Override
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	@Override
	public void setFeatureGenerator(String featureGenerator) {
		this.featureGenerator = featureGenerator;
	}

	@Override
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	@Override
	public void setRuleAC(String ruleAC) {
		this.ruleAC = ruleAC;
	}

	@Override
	public void setPIRSFID(String pirsfID) {
		this.pirsfID = pirsfID;
	}

	@Override
	public void setSeqStart(int seqStart) {
		this.seqStart = seqStart;
	}

	@Override
	public int getSeqStart() {
		return this.seqStart;
	}

	@Override
	public void setSeqEnd(int seqEnd) {
		this.seqEnd = seqEnd;
	}

	@Override
	public int getSeqEnd() {
		return this.seqEnd;
	}

	@Override
	public void setSelected(String selected) {
		this.selected = selected;
	}

	@Override
	public void setAlignedSeq(String seqAln) {
		this.seqAln = seqAln;
	}

	@Override
	public void setTemplateEntryAC(String templateEntryAC) {
		this.templateEntryAC = templateEntryAC;
	}

	@Override
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
