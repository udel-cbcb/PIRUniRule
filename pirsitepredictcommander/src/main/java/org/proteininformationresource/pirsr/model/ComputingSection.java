package org.proteininformationresource.pirsr.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * The Computing section contrasts with the Annotation section in that the line
 * identifiers are no longer restricted to 2 letters. The information starts in
 * the line of the line identifier, the following lines are indented by 1 space.
 * 
 */
public interface ComputingSection extends RuleSection {

	/**
	 * Gets a list of <code>ControlBlock</code>s.
	 * 
	 * @return a list of <code>ControlBlock</code>s.
	 */
	List<ControlBlock> getControlBlocks();

	/**
	 * Sets a list of <code>ControlBlock</code>s.
	 * 
	 * @param controlBlocks
	 *            a list of <code>ControlBlock</code>s.
	 */
	void setControlBlocks(List<ControlBlock> controlBlocks);

	/**
	 * Gets a list of <code>Line</code>s.
	 * 
	 * @return a list of <code>Line</code>s.
	 */
	List<Line> getLines();

	/**
	 * Sets a list of <code>Line</code>s.
	 * 
	 * @param a
	 *            list of <code>Line</code>s.
	 */
	void setLines(List<Line> lines);

	/**
	 * Gets the Size line that indicates the size relevant to a protein family
	 * or motif. A size may be specified as 'unlimited'.
	 * 
	 * @return the Size line of this Rule.
	 */
	SizeLine getSizeLine();

	/**
	 * Sets the Size line that indicates the size relevant to a protein family
	 * or motif. A size may be specified as 'unlimited'.
	 * 
	 * @param sizeLine
	 *            the size relevant to a protein family or motif.
	 */
	void setSizeLine(SizeLine sizeLine);

	/**
	 * Gets the Related line that lists other Rules known to be similar in
	 * sequence, and risk to produce cross-matches.
	 * 
	 * @return the Related line of this Rule.
	 */
	RelatedLine getRelatedLine();

	/**
	 * Sets the Related line that lists other Rules known to be similar in
	 * sequence, and risk to produce cross-matches.
	 * 
	 * @param relatedLine
	 *            lists of other Rules known to be similar in sequence.
	 */
	void setRelatedLine(RelatedLine relatedLine);

	/**
	 * Gets a list of template accession numbers.
	 * 
	 * @return a list of template accession numbers.
	 */
	TemplateLine getTemplateLine();

	/**
	 * Sets a list of template accession numbers.
	 * 
	 * @param templateLine
	 *            a list of template accession numbers.
	 */
	void setTemplateLine(TemplateLine templateLine);

	/**
	 * Gets the list of the organisms for which the motif which triggers the
	 * rule is found in multiple copies.
	 * 
	 * @return a list of the organisms.
	 */
	DuplicateLine getDuplicateLine();

	/**
	 * Sets the list of the organisms for which the motif which triggers the
	 * rule is found in multiple copies.
	 * 
	 * @param duplicateLine
	 *            a list of the organisms.
	 */
	void setDuplicateLine(DuplicateLine duplicateLine);

	/**
	 * Gets the list of the organisms for which the motif which triggers the
	 * rule is found encoded on a plasmid.
	 * 
	 * @return a list of the organisms.
	 */
	PlasmidLine getPlasmidLine();

	/**
	 * Sets the list of the organisms for which the motif which triggers the
	 * rule is found encoded on a plasmid.
	 * 
	 * @param plasmidLine
	 *            a list of the organisms.
	 */
	void setPlasmidLine(PlasmidLine plasmidLine);

	/**
	 * Gets the Scope Block that lists the taxonomic classes in which a rule
	 * match may be found.
	 * 
	 * @return the <code>ScopeBlock</code> of this Rule.
	 */
	ScopeBlock getScopeBlock();

	/**
	 * Sets the <code>ScopeBlock</code> that lists the taxonomic classes in
	 * which a rule match may be found.
	 * 
	 * @param scopeBlock
	 *            the list of taxonomic classes in which a rule match may be
	 *            found.
	 */
	void setScopeBlock(ScopeBlock scopeBlock);

	/**
	 * Gets the <code>FusionBlock</code> that lists UniRules to which a given
	 * UniRule may be fused in some instances.
	 * 
	 * @return the <code>FusionBlock</code> of this rule.
	 */
	FusionBlock getFusionBlock();

	/**
	 * Sets the <code>FusionBlock</code> that lists UniRules to which a given
	 * UniRule may be fused in some instances.
	 * 
	 * @param fusionBlock
	 *            the <code>FusionBlock</code> of this rule.
	 */
	void setFusionBlock(FusionBlock fusionBlock);

	/**
	 * A visitor which visits the different components of
	 * <code>ComputingSection</code>.
	 * 
	 * @param visitor
	 *            which visits the different components of
	 *            <code>ComputingSection</code>.
	 */
	void accept(ComputingSectionVisitor visitor);

	/**
	 * Gets a list of <code>Line</code>s in non control blocks of the rule.
	 * 
	 * @return a list of <code>Line</code>s in non control blocks of the rule.
	 */
	List<Line> getNonControlBlockLines();

	/**
	 * Sets a list of <code>Line</code>s in non control blocks of the rule.
	 * 
	 * @param nonControlBlockLines
	 *            a list of <code>Line</code>s in non control blocks of the
	 *            rule.
	 */
	void setNonControlBlockLines(List<Line> nonControlBlockLines);
}
