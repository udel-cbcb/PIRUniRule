package edu.udel.bioinformatics.pirrule.propagation;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 8, 2014<br><br>
 * 
 * 
 * Residue of alignment for template and aligned sequences.
*/
public class SeqResidue {
	private char residue;
	private int positionInAlignment;
	private int positionInSequence;
	
	
	public SeqResidue(char residue, int positionInAlignment, int positionInSequence) {
		super();
		this.residue = residue;
		this.positionInAlignment = positionInAlignment;
		this.positionInSequence = positionInSequence;
	}
	public char getResidue() {
		return residue;
	}
	public void setResidue(char residue) {
		this.residue = residue;
	}
	public int getPositionInAlignment() {
		return positionInAlignment;
	}
	public void setPositionInAlignment(int positionInAlignment) {
		this.positionInAlignment = positionInAlignment;
	}
	public int getPositionInSequence() {
		return positionInSequence;
	}
	public void setPositionInSequence(int positionInSequence) {
		this.positionInSequence = positionInSequence;
	}
	
	
	
}
