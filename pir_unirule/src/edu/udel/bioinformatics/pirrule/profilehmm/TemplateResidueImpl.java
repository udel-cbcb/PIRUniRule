package edu.udel.bioinformatics.pirrule.profilehmm;

import org.proteininformationresource.pirrule.profilehmm.TemplateResidue;

public class TemplateResidueImpl implements TemplateResidue {

	private char residue;
	private int position;
	private char match;
	private boolean matchStatus;
	private char profileResidue;
	private char posteriorProability;
	private String domain;
	
	public TemplateResidueImpl(char residue, int position, char match, boolean matchStatus, char profileResidue, char posteriorProability, String domain) {
		super();
		this.residue = residue;
		this.position = position;
		this.match = match;
		this.matchStatus = matchStatus;
		this.profileResidue = profileResidue;
		this.posteriorProability = posteriorProability;
		this.domain = domain;
	}

	@Override
	public char getResidue() {
		return this.residue;
	}

	@Override
	public int getPosition() {
		return this.position;
	}

	@Override
	public char getMatch() {
		return this.match;
	}

	@Override
	public boolean getMatchStatus() {
		return this.matchStatus;
	}
	
	public String toString() {
		return this.residue + " | "+ 
	           this.position + " | " + 
			   this.match + " | " + 
	           this.matchStatus + " | " +
			   this.profileResidue + " | " +
	           this.posteriorProability + " | " + 
			   this.domain;
	}
	
	public String toReport() {
		//String report = "Template to Profile HMM Alignment:\n";
		String report = "Domain: "+ this.domain+"\n";
		report += "Profile Residue: " + this.profileResidue+"\n";
		report += "Template Residue: " + this.residue + "\n";
		report += "Template Position: " + this.position + "\n";
		if(this.match !=' ') {
			report += "Match: " + this.match +"\n";
		}
		if(this.matchStatus == true) {
			report += "Match State: match\n";
		}
		else {
			report += "Match State: non-match\n";
		}
		report += "Posterior Prob.: "+this.posteriorProability+"\n";
		return report;
	}

	@Override
	public char getProfileResidue() {
		return this.profileResidue;
	}

	@Override
	public char getPosteriorProbablilty() {
		return this.posteriorProability;
	}

	@Override
	public String getDomain() {
		return this.domain;
	}

}
