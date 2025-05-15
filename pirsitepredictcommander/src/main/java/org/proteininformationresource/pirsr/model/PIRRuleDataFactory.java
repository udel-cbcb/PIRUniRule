package org.proteininformationresource.pirsr.model;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.model.expression.AndOperatorExpression;
import org.proteininformationresource.pirsr.model.expression.DefinedOperatorExpression;
import org.proteininformationresource.pirsr.model.expression.Expression;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;
import org.proteininformationresource.pirsr.model.expression.FTGroupConditionExpression;
import org.proteininformationresource.pirsr.model.expression.FeatureConditionExpression;
import org.proteininformationresource.pirsr.model.expression.NotOperatorExpression;
import org.proteininformationresource.pirsr.model.expression.OrOperatorExpression;
import org.proteininformationresource.pirsr.model.expression.OrganismConditionExpression;
import org.proteininformationresource.pirsr.model.expression.OrganismConditionType;

import edu.udel.bioinformatics.pirsr.PIRRuleUtil;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 15, 2014<br>
 * <br>
 * 
 * An interface for creating entities, lines and conditions.
 */

public interface PIRRuleDataFactory {

	/**
	 * Gets an <code>ACLine</code> which is the identifier of the rule.
	 * 
	 * @param accessionNumber
	 *            The accession number of the Rule.
	 * @return an <code>ACLine</code>.
	 */
	ACLine buildACLine(String accessionNumber);

	/**
	 * Gets a <code>DCLine</code> which specifies which type of data the rule
	 * annotates.
	 * 
	 * @param dataClass
	 *            Specifies which type of data the rule annotates.
	 * @return a <code>DCLine</code>.
	 */
	DCLine buildDCLine(String dataClass);

	/**
	 * Gets a <code>TRLine</code> which describes which (selected) sequence
	 * analysis features trigger the application of the rule.
	 * 
	 * @param dbName
	 *            Generally is the name of the sequence analysis feature
	 *            database.
	 * @param identifier1
	 *            The feature name e.g. the unique identifier of the motif in
	 *            the given database, which is generally an accession number.
	 * @param identifier2
	 *            A secondary identifier for the motif, which is generally an
	 *            entry name, and which is empty ("-") in the rule.
	 * @param nbHits
	 *            The expected number of hits to the motif. Obviously, the rule
	 *            is only triggered if at least one hit is found. The default
	 *            value is '1' for the rule.
	 * @param level
	 *            The minimum level that a hit must have to trigger the rule.
	 *            The default value is '0' for the rule.
	 * @return a <code>TRLine</code> which describes which (selected) sequ3ence
	 *         analysis features trigger the application of the rule.
	 */
	TRLine buildTRLine(String dbName, String identifier1, String identifier2, String nbHits, String level);

	/**
	 * Gets a comment line consists of comment description text
	 * 
	 * @param commentText
	 *            comment description text
	 * @return a comment line
	 */
	CommentsLine buildCommentLine(String commentText);

	/**
	 * Gets an empty <code>HeaderSection</code> object which contains technical
	 * information about the rule.
	 * 
	 * @return an empty <code>HeaderSection</code> object which contains
	 *         technical information about this rule.
	 */
	HeaderSection buildHeaderSection();

	/**
	 * Gets an applicable comment line of a UniProtKB/Swiss-Prot entry.
	 * 
	 * @param topic
	 *            The topic of a <code>CCLine</code>.
	 * @param description
	 *            The detailed description of the topic.
	 * @return a comment line
	 */
	CCLine buildCCLine(RuleCommentType topic, List<String> description);

	/**
	 * Gets an applicable keyword for a UniProtKB/Swiss-Prot entry, one per
	 * line.
	 * 
	 * @param keyword
	 *            An applicable keywords for a UniProtKB/Swiss-Prot entry.
	 * @return an applicable keywords for a UniProtKB/Swiss-Prot entry
	 */
	KWLine buildKWLine(String keyword);

	/**
	 * The accession number of template protein, the subsequent positions will
	 * be mapped to the annotated target sequence.
	 * 
	 * @param templateAccessionNumber
	 *            The template protein accession number.
	 * @return the accession number of template protein.
	 */
	FTFromLine buildFTFromLine(String templateAccessionNumber);

	/**
	 * Gets the description of actual features that are to be propagated in
	 * member entries.
	 * 
	 * @param ftFeature
	 *            A UniProtKB/Swiss-Prot feature key.
	 * @param featureFromPosition
	 *            A position relative to the beginning of the template protein
	 *            sequence.
	 * @param featureToPosition
	 *            A position relative to the end of the template protein
	 *            sequence.
	 * @param description
	 *            a list of description of a feature
	 * @return the description of actual features that are to be propagated in
	 *         member entries.
	 */
	FeatureDescriptionLine buildFeatureDescription(FeatureType featureType, String featureFromPosition, String featureToPosition, List<String> description);

	/**
	 * Gets the condition gives constraints on the FT features.
	 * 
	 * @param featureGroupNumber
	 *            The feature group number.For the consistency of annotation,
	 *            multiple features that should be applied either all together
	 *            or not at all should be grouped within a "Group", to constrain
	 *            the common presence of all Names. This group can the be
	 *            referenced by case statements, for instance in the relevant KW
	 *            and CC lines that depend on the presence of the feature.
	 * @param conditionPattern
	 *            The feature condition pattern. The "pattern" is specified in
	 *            the PROName pattern format with the addition that the
	 *            character "*" may be used to specify an unconstrained range,
	 *            e.g. "C-x*-C". The region of the sequence corresponding to the
	 *            feature must exactly match this pattern.
	 * @return the condition gives constraints on the FT features.
	 */
	FeatureConstraintLine buildFeatureConstraintLine(int featureGroupNumber, String conditionPattern);

	/**
	 * Gets a <code>AnnotationSection</code>, which includes all UniProtKB
	 * annotation lines that can be applied to a rule match. Additionally, there
	 * can be lines which indicate condition statements ('case', 'else case',
	 * 'else') and a line which indicates the motif or alignments, according to
	 * which the feature positions are calculated. The line order is the same as
	 * in UniProtKB/Swiss-Prot.
	 * 
	 * @return a <code>AnnotationSection</code>.
	 */
	AnnotationSection buildAnnotationSection();

	/**
	 * Gets the Size line that indicates the size relevant to a protein family
	 * or motif. A size may be specified as 'unlimited'.
	 * 
	 * @param sizeLimit
	 *            The size limit relevant to a protein family or motif. A size
	 *            may be specified as 'unlimited'.
	 * @return the Size line that indicates the size relevant to a protein
	 *         family or motif.
	 */
	SizeLine buildSizeLine(String sizeLimit);

	/**
	 * Gets the list of related rule accession numbers. Default is "none";
	 * 
	 * @param relatedNameRuleAccessionNumber
	 *            Related rule accession number
	 * @return the list of related rule accession numbers. Default is "none";
	 */
	RelatedLine buildRelatedLine(List<String> relatedNameRuleAccessionNumbers);

	/**
	 * Gets the taxonomic classification composed of the kingdom, optionally
	 * followed by the name of a sub-taxon, to further limit the application of
	 * the rule to any taxonomic level. Valid values for kingdom are:
	 * "Eukaryota", "Bacteria", "Archaea", "Viruses", "Bacteriophage", "Plastid"
	 * and "Mitochondrion". The two latter values designate proteins encoded in
	 * the organelle genome but not proteins encoded in the nucleus and targeted
	 * to the organelle. If it has been assessed with certainty that a rule is
	 * not represented in: A taxonomic group, its name may be indicated in the
	 * "except" field. A complete proteome, its taxcode may be indicated in the
	 * "not in" field.
	 * 
	 * @param kingdom
	 *            The kingdom name.
	 * @param kingdomSubTaxon
	 *            The kindom sub-taxon.
	 * @param exceptTaxonomicGroups
	 *            The list of taxonomic group names not belong to this Kingdom.
	 * @param completeProteomeNotInTaxCodes
	 *            The list of taxcode for complete proteomes not belong to this
	 *            Kingdom.
	 * @return the taxonomic classification.
	 */
	KingdomLine buildKingdomLine(String kingdom, String kingdomSubTaxon, List<String> exceptTaxonomicGroups, List<String> completeProteomeNotInTaxCodes);

	/**
	 * Gets a <code>ScopeBlock</code> that lists the taxonomic classes in which
	 * a rule match may be found.
	 * 
	 * @param kingdomLines
	 *            The taxonomic classes in which a rule match may be found.
	 * @return the Scope Block that lists the taxonomic classes in which a rule
	 *         match may be found.
	 */
	ScopeBlock buildScopeBlock(List<KingdomLine> kingdomLines);

	/**
	 * Gets a <code>ScopeBlock</code>.
	 * 
	 * @return a <code>ScopeBlock</code>.
	 */
	ScopeBlock buildScopeBlock();

	/**
	 * The information about the version number, date/time and author of the
	 * creation/modification.
	 * 
	 * @param curator
	 *            The curator of this history information.
	 * @param dateTime
	 *            The date/time of this history information.
	 * @param versionNumber
	 *            The version number of this history information.
	 * @param type
	 *            The type of history info, either creation or lastModification.
	 * @return the information about the version number, date/time and author of
	 *         the creation/modification.
	 */
	HistoryInfo buildHistoryInfo(String curator, String dateTime, String versionNumber, String type);

	/**
	 * Gets the History section indicates the version number, date/time and
	 * author of the creation/modification.
	 * 
	 * @param historyInfo
	 *            A list of <code>HistoryInfo</code> about the creation/last
	 *            modification info of this rule.
	 * @return the History section indicates the version number, date/time and
	 *         author of the creation/modification.
	 */
	HistorySection buildHistorySection(List<HistoryInfo> historyInfo);

	/**
	 * Rule is a format describing conditional annotation templates (rules) used
	 * by UniProtKB automated annotation projects.
	 * 
	 * @param pirruleManager
	 *            The rule manager.
	 * @param headerSection
	 *            The Header Section of this rule.
	 * @param annotationSection
	 *            The Annotation Section of this rule.
	 * @param computingSection
	 *            The Computing Section of this rule.
	 * @param historySection
	 *            The History Section of this rule.
	 * @return The rule describing conditional annotation templates (rules) used
	 *         by UniProtKB automated annotation projects.
	 */
	PIRRule buildPIRRule(PIRRuleManager pirruleManager, HeaderSection headerSection, AnnotationSection annotationSection,
			ComputingSection computingSection, HistorySection historySection);

	/**
	 * Rule is a format describing conditional annotation templates (rules) used
	 * by UniProtKB automated annotation projects.
	 * 
	 * @param pirruleManager
	 *            The rule manager.
	 * @return empty <code>PIRRule</code> object.
	 */
	PIRRule buildPIRRule(PIRRuleManager pirruleManager);

	/**
	 * Gets a <code>ControlBlock</code>.
	 * 
	 * @param controlStatements
	 *            a list of <code>ControlStatement</code>s.
	 * @return a <code>ControlBlock</code>.
	 */
	ControlBlock buildControlBlock(ArrayList<ControlStatement> controlStatements);

	/**
	 * Gets a <code>CaseStatement</code> object.
	 * 
	 * @return a <code>CaseStatement</code> object.
	 */
	CaseStatement buildCaseStatement();

	/**
	 * Gets a <code>AndOperatorExpression</code> object.
	 * 
	 * @param leftParenthesis
	 *            the left parenthesis
	 * @param leftExpression
	 *            the left expression
	 * @param rightExpression
	 *            the right expression
	 * @param rightParenthesis
	 *            the right parenthesis
	 * @return a <code>AndOperatorExpression</code> object.
	 */

	AndOperatorExpression buildAndOperatorExpression(String leftParenthesis, Expression leftExpression, Expression rightExpression,
			String rightParenthesis);

	/**
	 * 
	 * Gets a <code>OrOperatorExpression</code> object.
	 * 
	 * @param leftParenthesis
	 *            the left parenthesis
	 * @param leftExpression
	 *            the left expression
	 * @param rightExpression
	 *            the right expression
	 * @param rightParenthesis
	 *            the right parenthesis
	 * @return a <code>OrOperatorExpression</code> object.
	 */
	OrOperatorExpression buildOrOperatorExpression(String leftParenthesis, Expression leftExpression, Expression rightExpression,
			String rightParenthesis);

	/**
	 * Gets a <code>FeatureConditionExpression</code> object.
	 * 
	 * @param featureConditionDescription
	 *            the feature condition description.
	 * @return a <code>FeatureConditionExpression</code> object.
	 */
	FeatureConditionExpression buildFeatureConditionExpression(String featureConditionDescription);

	/**
	 * Gets a <code>FTGroupConditionExpression</code> object.
	 * 
	 * @param ftGroupNumber
	 *            the feature group number.
	 * @return a <code>FTGroupConditionExpression</code> object.
	 */
	//FTGroupConditionExpression buildFeatureConditionExpression(int ftGroupNumber);

	/**
	 * Gets a <code>DefinedOperatorExpression</code> object.
	 * 
	 * @param leftParenthesis
	 *            the left parenthesis.
	 * @param definedExpression
	 *            the expression.
	 * @param rightParenthesis
	 *            the right parenthesis.
	 * @return a <code>DefinedOperatorExpression</code> object.
	 */
	DefinedOperatorExpression buildDefinedOperatorExpression(String leftParenthesis, Expression definedExpression, String rightParenthesis);

	/**
	 * Gets a <code>NotOperatorExpression</code> object.
	 * 
	 * @param leftParenthesis
	 *            the left parenthesis.
	 * @param notExpression
	 *            the not expression.
	 * @param rightParenthesis
	 *            the right parenthesis.
	 * @return a <code>NotOperatorExpression</code> object.
	 */
	NotOperatorExpression buildNotOperatorExpression(String leftParenthesis, Expression notExpression, String rightParenthesis);

	/**
	 * Gets a <code>Block</code> object.
	 * 
	 * @return a <code>Block</code> object.
	 */
	Block buildBlock();

	/**
	 * Gets a <code>Block</code>.
	 * 
	 * @param lines
	 *            a list of lines.
	 * @return a <code>Block</code>.
	 */

	Block buildBlock(List<Line> lines);

	/**
	 * Gets an <code>EndCaseStatement</code> object.
	 * 
	 * @return an <code>EndCaseStatement</code> object.
	 */
	ControlStatement buildEndCaseStatement();

	/**
	 * Gets a <code>XXLine</code> object.
	 * 
	 * @return a <code>XXLine</code> object.
	 */

	XXLine buildXXLine();

	/**
	 * Gets a <code>FTNoneLine</code> object.
	 * 
	 * @return a <code>FTNoneLine</code> object.
	 */
	FTNoneLine buildFTNoneLine();

	/**
	 * Gets a <code>DELine</code> object.
	 * 
	 * @return a <code>DELine</code> object.
	 */
	DELine buildDELine();

	/**
	 * Gets a <code>ComputingSection</code> object.
	 * 
	 * @return a <code>ComputingSection</code> object.
	 */
	ComputingSection buildComputingSection();

	/**
	 * Gets a <code>InternalCommentsLine</code> object.
	 * 
	 * @param comments
	 *            the comments text
	 * @return a <code>InternalCommentsLine</code> object.
	 */
	InternalCommentsLine buildInternalCommentsLine(String comments);

	/**
	 * Gets a <code>GOLine</code> object.
	 * 
	 * @param geneOntologyAccessionNumber
	 *            GO accession number
	 * @param geneOntologyAspect
	 *            GO category(P: biological process; F: molecule function; C:
	 *            cellular component)
	 * @param geneOntologyTerm
	 *            GO term
	 * @return a <code>GOLine</code> object.
	 */
	GOLine buildGOLine(String geneOntologyAccessionNumber, String geneOntologyAspect, String geneOntologyTerm);

	/**
	 * Gets a <code>FusionBlock</code> object.
	 * 
	 * @return a <code>FusionBlock</code> object.
	 */
	FusionBlock buildFusionBlock();

	/**
	 * Gets a <code>TemplateLine</code> object.
	 * 
	 * @param templateAccessionNumbers
	 *            a list of template accession numbers.
	 * @return a <code>TemplateLine</code> object.
	 */
	TemplateLine buildTemplateLine(List<String> templateAccessionNumbers);

	/**
	 * Gets a <code>PlasmidLine</code> object.
	 * 
	 * @param organisms
	 *            a list of organism taxonomy.
	 * @return a <code>PlasmidLine</code> object.
	 */
	PlasmidLine buildPlasmidLine(List<String> organisms);

	/**
	 * Gets a <code>DuplicateLine</code> object.
	 * 
	 * @param duplicateIdsList
	 *            a list of organisms for which the motif which triggers the
	 *            rule is found in multiple copies.
	 * @return a <code>DuplicateLine</code> object.
	 */
	DuplicateLine buildDuplicateLine(List<String> duplicateIdsList);

	/**
	 * Gets a <code>DRLine</code> object.
	 * 
	 * @param dbName
	 *            Generally is the name of the sequence analysis feature
	 *            database.
	 * @param featureName
	 *            The feature name e.g. the unique identifier of the motif in
	 *            the given database, which is generally an accession number.
	 * @param identifier
	 *            An identifier for the motif, which is generally an entry name,
	 *            and which is empty ("-") in the rule.
	 * @param nbHits
	 *            The expected number of hits to the motif. Obviously, the rule
	 *            is only triggered if at least one hit is found. The default
	 *            value is '1' for the rule.
	 * @param level
	 *            The minimum level that a hit must have to trigger the rule.
	 *            The default value is '0' for the rule.
	 * @return a <code>DRLine</code> object.
	 */
	DRLine buildDRLine(String dbName, String featureName, String identifier, String nbHits, String triggerLevel);

	/**
	 * Gets a <code>OrganismConditionExpression</code> object.
	 * 
	 * @param organismConditionType
	 *            organismConditionType (can be 'OS', 'OC', 'OG').
	 * @param organismName
	 *            organism name
	 * @return a <code>OrganismConditionExpression</code> object.
	 */
	OrganismConditionExpression buildOrganismConditionExpression(OrganismConditionType organismConditionType, String organismName);

	/**
	 * Gets a <code>ElseStatement</code> object.
	 * 
	 * @return a <code>ElseStatement</code> object.
	 */
	ElseStatement buildElseStatement();

	/**
	 * Gets a <code>ElseCaseStatement</code> object.
	 * 
	 * @return a <code>ElseCaseStatement</code> object.
	 */
	ElseCaseStatement buildElseCaseStatement();

	/**
	 * Gets a <code>CCNoneLine</code> object.
	 * 
	 * @return a <code>CCNoneLine</code> object.
	 */
	CCNoneLine buildCCNoneLine();

	/**
	 * Gets a list of <code>ControlBlock</code>s.
	 * 
	 * @return a list of <code>ControlBlock</code>s.
	 */
	List<ControlBlock> buildControlBlocks();

	FTGroupConditionExpression buildFeatureConditionExpression(int ftGroupNumber, ExpressionValue expressionValue);
	
	/**
	 * Gets an instance of <code>PIRRuleDataFactory</code>.
	 * @return an instance of <code>PIRRuleDataFactory</code>.
	 */
	//PIRRuleDataFactory getInstance();
	
	PIRRuleManager buildPIRRuleManager(PIRRuleDataFactory pirRuleDataFactory);
	
	/**
	 * Gets an instance of <code>PIRRuleUtil</code>.
	 * @param rule the PIR rule
	 * @return an instance of <code>PIRRuleUtil</code>.
	 */
	public PIRRuleUtil buidPIRRuleUtil(PIRRule rule);

}
