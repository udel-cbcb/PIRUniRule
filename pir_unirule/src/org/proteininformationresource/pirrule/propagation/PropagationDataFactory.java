package org.proteininformationresource.pirrule.propagation;

import org.proteininformationresource.pirrule.model.CCLine;
import org.proteininformationresource.pirrule.model.FeatureDescriptionLine;
import org.proteininformationresource.pirrule.model.KWLine;
import org.proteininformationresource.pirrule.model.PIRRuleManager;
import org.proteininformationresource.pirrule.propagation.annotation.EntryAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.EntryCommentAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.EntryFeatureTableAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.EntryKeywordAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.RuleAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.RuleCommentAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.RuleFeatureTableAnnotation;
import org.proteininformationresource.pirrule.propagation.annotation.RuleKeywordAnnotation;
import org.proteininformationresource.pirrule.uniprot.model.EntryComment;
import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirrule.uniprot.model.EntryKeyword;
import org.proteininformationresource.pirrule.uniprot.model.EntryType;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 15, 2013<br>
 * <br>
 * 
 * An interface for creating annotation related object.
 */
public interface PropagationDataFactory {

	/**
	 * Gets a <code>Alignment</code> object.
	 * 
	 * @return a <code>Alignment</code> object.
	 */
	Alignment buildAlignment();

	/**
	 * Gets a <code>PropagationRecord</code> object.
	 * 
	 * @return a <code>PropagationRecord</code> object.
	 */
	PropagationRecord buildPropagationRecord();

	/**
	 * Gets an <code>EntryCommentAnnotation</code> object.
	 * 
	 * @param entryAC
	 *            UniProt entry accession number.
	 * @param entryID
	 *            UniProt entry identifier.
	 * @param entryType
	 *            Uniprot entry type.
	 * @param comment
	 *            UniProt comment line
	 * @return an <code>EntryCommentAnnotation</code> object.
	 */
	EntryCommentAnnotation buildEntryCommentAnnotation(String entryAC, String entryID, EntryType entryType, EntryComment comment);

	/**
	 * Gets an <code>EntryKeywordAnnotation</code> object.
	 * 
	 * @param entryAC
	 *            UniProt entry accession number.
	 * @param entryID
	 *            UniProt entry identifier.
	 * @param entryType
	 *            Uniprot entry type.
	 * @param keyword
	 *            UniProt keyword.
	 * @return an <code>EntryKeywordAnnotation</code> object.
	 */
	EntryKeywordAnnotation buildEntryKeywordAnnotation(String entryAC, String entryID, EntryType entryType, EntryKeyword keyword);

	/**
	 * Gets a <code>EntryFeatureTableAnnotation</code> object.
	 * 
	 * @param entryAC
	 *            UniProt entry accession number.
	 * @param entryID
	 *            UniProt entry identifier.
	 * @param entryType
	 *            Uniprot entry type.
	 * @param feature
	 *            Uniprot feature
	 * @return an <code>EntryFeatureTableAnnotation</code> object.
	 */
	EntryFeatureTableAnnotation buildEntryFeatureTableAnnotation(String entryAC, String entryID, EntryType entryType, EntryFeatureTable feature, String missMatchReason);

	/**
	 * Gets a <code>RuleCommentAnnotation</code> object.
	 * 
	 * @param ruleAC
	 *            rule accession number.
	 * @param templateAC
	 *            template sequence accession number.
	 * @param comment
	 *            rule <code>CCLine</code>.
	 * @return a <code>RuleCommentAnnotation</code> object.
	 */
	RuleCommentAnnotation buildRuleCommentAnnotation(String ruleAC, String templateAC, CCLine comment);

	/**
	 * Gets a <code>RuleKeywordAnnotation</code> object.
	 * 
	 * @param ruleAC
	 *            rule accession number.
	 * @param templateAC
	 *            template sequence accession number.
	 * @param keyword
	 *            rule <code>KWLine</code>.
	 * @return a <code>RuleKeywordAnnotation</code> object.
	 */
	RuleKeywordAnnotation buildRuleKeywordAnnotation(String ruleAC, String templateAC, KWLine keyword);

	/**
	 * Gets a <code>RuleFeatureTableAnnotation</code> object.
	 * 
	 * @param ruleAC
	 *            rule accession number.
	 * @param templateAC
	 *            template sequence accession number.
	 * @param featureDescriptionLine
	 *            rule <code>FeatureDescriptionLine</code>.
	 * @return a <code>RuleFeatureTableAnnotation</code> object.
	 */
	RuleFeatureTableAnnotation buildRuleFeatureTableAnnotation(String ruleAC, String templateAC, FeatureDescriptionLine featureDescriptionLine);
	
	/**
	 * Gets a <code>PropagationManager</code> object.
	 * @param propagationDataFactory propagation data factory.
	 * @param pirRuleManager PIR rule manager.
	 * @return a <code>PropagationManager</code> object.
	 */
	PropagationManager buildPropagationManager(PropagationDataFactory propagationDataFactory, PIRRuleManager pirRuleManager);
	
	/**
	 * Gets a <code>EntryAnnotation</code> object.
	 * @param entryAC UniProt entry accession number.
	 * @param entryID UniProt entry identifier.
	 * @param entryType  the UniProt entry type.
	 * @return a <code>EntryAnnotation</code> object.
	 */
	EntryAnnotation buildEntryAnnotation(String entryAC, String entryID, EntryType entryType);
	
	/**
	 * Gets a <code>RuleAnnotation</code> object.
	 * @param ruleAC rule accession number.
	 * @param templateAC rule template sequence accession number.
	 * @return a <code>RuleAnnotation</code> object.
	 */
	RuleAnnotation buildRuleAnnotation(String ruleAC, String templateAC);
}
