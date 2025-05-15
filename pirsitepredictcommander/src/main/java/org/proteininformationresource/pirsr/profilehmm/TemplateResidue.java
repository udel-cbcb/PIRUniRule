package org.proteininformationresource.pirsr.profilehmm;

public interface TemplateResidue {
	char getProfileResidue();
	char getResidue();
	int getPosition();
	char getMatch();
	char getPosteriorProbablilty();
	boolean getMatchStatus();
	String getDomain();
	String toReport();
}
