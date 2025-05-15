package org.proteininformationresource.pirsr.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 *
 * PIR Rule is a format describing conditional annotation templates (rules) used
 * by UniProtKB automated annotation projects. It defines annotation which will
 * be generated for (selected) predicted features. Each rule entry consists of
 * the following parts. 1. The <code>HeaderSection</code> contains the rule
 * accession number, the data class, identifier(s) of the feature(s) that
 * trigger(s) the rule, as well as some basic information on the rule. 2. The
 * <code>AnnotationSection</code>. All UniProtKB annotation relevant for a PIR
 * Rule is indicated. Not all annotation lines apply to all true hits.
 * Therefore, case statements restrict all or part of the annotation to further
 * conditions like the taxonomic group or the size of a protein. 3. The
 * <code>ComputingSection</code>covers information on a protein, domain or site
 * relevant to programs. 4. The History section indicates the version number,
 * date and author of the modification.
 * 
 * A PIR Rule cannot be modified directly. Changes must be applied via its
 * <code>SiteRuleManager</code>.
 */
public interface PIRRule {

	/**
	 * Gets the manager that created this PIR Rule.
	 * 
	 * @return the manager that created this PIR Rule.
	 */
	PIRRuleManager getPIRRuleManager();

	/**
	 * Sets the manager that created this PIR Rule.
	 * 
	 * @param pirruleManager
	 *            the manager that created this PIR Rule.
	 */
	void setPIRRuleMananger(PIRRuleManager pirruleManager);

	/**
	 * Gets the <code>HeaderSection</code> of this PIR Rule, which contains
	 * technical information.
	 * 
	 * @return the <code>HeaderSection</code> of this PIR Rule.
	 */
	HeaderSection getHeaderSection();

	/**
	 * Sets the <code>HeaderSection</code> of this PIR Rule, which contains
	 * technical information.
	 * 
	 * @param headerSection
	 *            the <code>HeaderSection</code> of this PIR Rule.
	 */
	void setHeaderSection(HeaderSection headerSection);

	/**
	 * Gets the <code>AnnotationSection</code> of this PIR Rule, which includes
	 * all UniProtKB/Swiss-Prot annotation lines that can be applied to a rule
	 * match. Additionally, there can be lines which indicate condition
	 * statements ('case', 'else case', 'else') and a line which indicates the
	 * motif or alignments, according to which the feature positions are
	 * calculated. The line order is the same as in UniProtKB.
	 * 
	 * @return the <code>AnnotationSection</code> of this PIR Rule.
	 */
	AnnotationSection getAnnotationSection();

	/**
	 * Sets the <code>AnnotationSection</code> of this PIR Rule, which includes
	 * all UniProtKB/Swiss-Prot annotation lines that can be applied to a rule
	 * match. Additionally, there can be lines which indicate condition
	 * statements ('case', 'else case', 'else') and a line which indicates the
	 * motif or alignments, according to which the feature positions are
	 * calculated. The line order is the same as in UniProtKB.
	 * 
	 * @param annotationSection
	 *            the <code>AnnotationSection</code> of this PIR Rule.
	 */
	void setAnnotationSection(AnnotationSection annotationSection);

	/**
	 * Gets the <code>ComputingSection</code> of this PIR Rule.
	 * 
	 * @return the <code>ComputingSection</code> of this PIR Rule.
	 */
	ComputingSection getComputingSection();

	/**
	 * Sets the <code>ComputingSection</code> of this PIR Rule.
	 * 
	 * @param computingSection
	 *            the <code>ComputingSection</code> of this PIR Rule.
	 */
	void setComputingSection(ComputingSection computingSection);

	/**
	 * Gets the <code>HistorySection</code> indicates the version number, date
	 * and author of the modification.
	 * 
	 * @return the <code>HistorySection</code> of this PIR Rule.
	 */
	HistorySection getHistorySection();

	/**
	 * Sets the <code>HistorySection</code> indicates the version number, date
	 * and author of the modification.
	 * 
	 * @param historySection
	 *            the <code>HistorySection</code> of this PIR Rule.
	 */
	void setHistorySection(HistorySection historySection);

	/**
	 * Gets a list of <code>ControlBlock</code>s associated with the rule.
	 * 
	 * @return a list of <code>ControlBlock</code>s associated with the rule.
	 */
	List<ControlBlock> getControlBlocks();

	/**
	 * Sets a list of <code>ControlBlock</code>s associated with the rule.
	 * 
	 * @param controlBlocks
	 *            a list of <code>ControlBlock</code>s associated with the rule.
	 */
	void setControlBlocks(List<ControlBlock> controlBlocks);
	
	/**
	 * Gets a list of <code>DELine</code> and associated <code>ControlBlock</code>s. 
	 * @return a list of <code>DELine</code> and associated <code>ControlBlock</code>s. 
	 */
	//List<ControlBlock> getDELineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>DELine</code>s.
	 * @return a list of non control block <code>DELine</code>s.
	 */
	//List<Line> getNonControlBlockDELines();
	
	/**
	 * Gets a list of <code>CCLine</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>CCLine</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getCCLineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>CCLine</code>s.
	 * @return a list of non control block <code>CCLine</code>s.
	 */
	//List<Line> getNonControlBlockCCLines();
	
	/**
	 * Gets a list of <code>DRLine</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>DRLine</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getDRLineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>DRLine</code>s.
	 * @return a list of non control block <code>DRLine</code>s.
	 */
	//List<Line> getNonControlBlockDRLines();
	
	/**
	 * Gets a list of <code>KWLine</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>KWLine</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getKWLineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>KWLine</code>s.
	 * @return a list of non control block <code>KWLine</code>s.
	 */
	//List<Line> getNonControlBlockKWLines();
	
	/**
	 * Gets a list of <code>GOLine</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>GOLine</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getGOLineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>GOLine</code>s.
	 * @return a list of non control block <code>GOLine</code>s.
	 */
	//List<Line> getNonControlBlockGOLines();
	
	/**
	 * Gets a list of <code>FTLine</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>FTLine</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getFTLineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>FTLine</code>s.
	 * @return a list of non control block <code>FTLine</code>s.
	 */
	//List<Line> getNonControlBlockFTLines();
	
	/**
	 * Gets a list of <code>SizeLine</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>SizeLine</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getSizeLineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>SizeLine</code>s.
	 * @return a list of non control block <code>SizeLine</code>s.
	 */
	//List<Line> getNonControlBlockSizeLines();
	
	/**
	 * Gets a list of <code>RelatedLine</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>RelatedLine</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getRelatedLineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>RelatedLine</code>s.
	 * @return a list of non control block <code>RelatedLine</code>s.
	 */
	//List<Line> getNonControlBlockRelatedLines();
	
	/**
	 * Gets a list of <code>TemplateLine</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>TemplateLine</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getTemplateLineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>TemplateLine</code>s.
	 * @return a list of non control block <code>TempalteLine</code>s.
	 */
	//List<Line> getNonControlBlockTemplateLines();
	
	/**
	 * Gets a list of <code>ScopeBlock</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>ScopeBlock</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getScopeBlockControlBlocks();
	
	/**
	 * Gets a list of non control block <code>ScopeBlock</code>s.
	 * @return a list of non control block <code>ScopeBlock</code>s.
	 */
	//<Line> getNonControlBlockScopeBlockLines();
	
	/**
	 * Gets a list of <code>FusionBlock</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>FusionBlock</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getFusionBlockControlBlocks();
	
	/**
	 * Gets a list of non control block <code>FusionBlock</code>s.
	 * @return a list of non control block <code>FusionBlock</code>s.
	 */
	//List<Line> getNonControlBlockFusionBlockLines();
	
	
	/**
	 * Gets a list of <code>DuplicateLine</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>DuplicateLine</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getDuplicateLineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>DuplicateLine</code>s.
	 * @return a list of non control block <code>DuplicateLine</code>s.
	 */
	//List<Line> getNonControlBlockDuplicateLines();
	
	
	/**
	 * Gets a list of <code>PlasmidLine</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>PlasmidLine</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getPlasmidLineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>PlasmidLine</code>s.
	 * @return a list of non control block <code>DuplicateLine</code>s.
	 */
	//List<Line> getNonControlBlockPlasmidLines();
	
	
	/**
	 * Gets a list of <code>CommentsLine</code> and associated <code>ControlBlock</code>s.
	 * @return a list of <code>CommentsLine</code> and associated <code>ControlBlock</code>s.
	 */
	//List<ControlBlock> getCommentsLineControlBlocks();
	
	/**
	 * Gets a list of non control block <code>CommentsLine</code>s.
	 * @return a list of non control block <code>CommentsLine</code>s.
	 */
	//List<Line> getNonControlBlockCommentsLines();
	
	/**
	 * Gets a list of <code>Line</code>s.
	 * @return a list of <code>Line</code>s.
	 */
	List<Line> getRuleLines();
	
	/**
	 * Sets a list of <code>Line</code>s.
	 * @param lines a list of <code>Line</code>s.
	 */
	void setRuleLines(List<Line> lines);
	
	String getRuleAC();
	
	String getTemplateAC();
	
	String getTrigger();
	
	String getLastModifyDateTime();
	
	String getLastModifyCurator();
}
