package edu.udel.bioinformatics.pirrule.propagation;

import org.proteininformationresource.pirrule.model.CCLine;
import org.proteininformationresource.pirrule.model.FeatureDescriptionLine;
import org.proteininformationresource.pirrule.model.KWLine;
import org.proteininformationresource.pirrule.model.PIRRuleManager;
import org.proteininformationresource.pirrule.propagation.Alignment;
import org.proteininformationresource.pirrule.propagation.PropagationDataFactory;
import org.proteininformationresource.pirrule.propagation.PropagationManager;
import org.proteininformationresource.pirrule.propagation.PropagationRecord;
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

import edu.udel.bioinformatics.pirrule.propagation.annotation.EntryAnnotationImpl;
import edu.udel.bioinformatics.pirrule.propagation.annotation.EntryCommentAnnotationImpl;
import edu.udel.bioinformatics.pirrule.propagation.annotation.EntryFeatureTableAnnotationImpl;
import edu.udel.bioinformatics.pirrule.propagation.annotation.EntryKeywordAnnotationImpl;
import edu.udel.bioinformatics.pirrule.propagation.annotation.RuleAnnotationImpl;
import edu.udel.bioinformatics.pirrule.propagation.annotation.RuleCommentAnnotationImpl;
import edu.udel.bioinformatics.pirrule.propagation.annotation.RuleFeatureTableAnnotationImpl;
import edu.udel.bioinformatics.pirrule.propagation.annotation.RuleKeywordAnnotationImpl;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 8, 2014<br><br>
 * 
 * The implementation of <code>AnnotationDataFactory</code>. 
 */
public class PropagationDataFactoryImpl implements PropagationDataFactory {

	@Override
	public Alignment buildAlignment() {
		return new AlignmentImpl();
	}

	@Override
	public PropagationRecord buildPropagationRecord() {
		return new PropagationRecordImpl();
	}

	@Override
	public EntryCommentAnnotation buildEntryCommentAnnotation(String entryAC, String entryID, EntryType entryType, EntryComment comment) {
		return new EntryCommentAnnotationImpl(entryAC, entryID, entryType, comment);
	}

	@Override
	public EntryKeywordAnnotation buildEntryKeywordAnnotation(String entryAC, String entryID, EntryType entryType, EntryKeyword keyword) {
		return new EntryKeywordAnnotationImpl(entryAC, entryID, entryType, keyword);
	}

	@Override
	public EntryFeatureTableAnnotation buildEntryFeatureTableAnnotation(String entryAC, String entryID, EntryType entryType, EntryFeatureTable feature, String missMatchReason) {
		return new EntryFeatureTableAnnotationImpl(entryAC, entryID, entryType, feature, missMatchReason);
	}

	@Override
	public RuleCommentAnnotation buildRuleCommentAnnotation(String ruleAC, String templateAC, CCLine comment) {
		return new RuleCommentAnnotationImpl(ruleAC, templateAC, comment);
	}

	@Override
	public RuleKeywordAnnotation buildRuleKeywordAnnotation(String ruleAC, String templateAC, KWLine keyword) {
		return new RuleKeywordAnnotationImpl(ruleAC, templateAC, keyword);
	}

	@Override
	public RuleFeatureTableAnnotation buildRuleFeatureTableAnnotation(String ruleAC, String templateAC, FeatureDescriptionLine featureDescriptionLine) {
		return new RuleFeatureTableAnnotationImpl(ruleAC, templateAC, featureDescriptionLine);
	}

	public static PropagationDataFactory getInstance() {
		return new PropagationDataFactoryImpl();
	}

	@Override
	public PropagationManager buildPropagationManager(PropagationDataFactory propagationDataFactory, PIRRuleManager pirRuleManager) {
		return new PropagationManagerImpl(propagationDataFactory, pirRuleManager);
	}

	@Override
	public EntryAnnotation buildEntryAnnotation(String entryAC, String entryID, EntryType entryType) {
		return new EntryAnnotationImpl(entryAC, entryID, entryType);
	}

	@Override
	public RuleAnnotation buildRuleAnnotation(String ruleAC, String templateAC) {
		return new RuleAnnotationImpl(ruleAC, templateAC);
	}
}
