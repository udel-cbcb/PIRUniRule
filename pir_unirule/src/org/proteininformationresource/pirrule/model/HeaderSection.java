package org.proteininformationresource.pirrule.model;

import java.util.List;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br><br>
 * 
 * 
 * The Header section of the Rule, which contains technical information.
 */
public interface HeaderSection extends RuleSection {
	/**
	 * Gets a list of <code>Line</code>s. They can be <code>ACLine</code>, <code>DCLine</code>, 
	 * <code>TRLine</code>, <code>XXLine</code> or <code>InternalComemntLine</code>.
	 * @return a list of <code>Line</code>s.
	 */
	List<Line> getLines();
	
	/**
	 * Sets a list of <code>Line</code>s.
	 * @param lines
	 */
	void setLines(List<Line> lines);
	
	/**
	 * Gets the AC (ACcession number) line of the rule, which is the identifier for the rule.
	 * @return the AC line of the rule.
	 */
	ACLine getACLine();
	
	/**
	 * Sets the AC (ACcession number) line of the rule, which is the identifier for the rule.
	 * @param acLine the AC line of the rule.
	 */
	void setACLine(ACLine acLine);
	
	/**
	 * Gets the DC (Data Class) line of the rule, which specifies which type of data the rule annotates.
	 * @return the DC line of the rule.
	 */
	DCLine getDCLine();
	
	/**
	 * Sets the DC (Data Class) line of the rule, which specifies which type of data the rule annotates.
	 * @param dcLine the DC line of the rule.
	 */
	void setDCLine(DCLine dcLine);
	
	/**
	 * Gets the TR (Trigger) line of the rule, which describes which (selected) sequence analysis features trigger 
	 * the application of the rule.
	 * @return the TR (Trigger) line of the rule.
	 */
	TRLine getTRLine();
	
	/**
	 * Sets the TR (Trigger) line of the rule, which describes which (selected) sequence analysis features trigger 
	 * the application of the rule.
	 * @param trLine the TR (Trigger) line of the rule.
	 */
	void setTRLine(TRLine trLine);
	
	
	
	/**
	 * A visitor which visits the different components of the <code>HeaderSection</code>.
	 * @param visitor which visits the different components of the <code>HeaderSection</code>.
	 */
	void accept(HeaderSectionVisitor visitor);

}
