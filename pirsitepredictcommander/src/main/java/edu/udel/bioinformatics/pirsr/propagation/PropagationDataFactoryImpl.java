package edu.udel.bioinformatics.pirsr.propagation;

import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.FeatureDescriptionLine;
import org.proteininformationresource.pirsr.model.KWLine;
import org.proteininformationresource.pirsr.model.PIRRuleManager;
import org.proteininformationresource.pirsr.propagation.Alignment;
import org.proteininformationresource.pirsr.propagation.PropagationDataFactory;
import org.proteininformationresource.pirsr.propagation.PropagationManager;
import org.proteininformationresource.pirsr.propagation.PropagationRecord;
import org.proteininformationresource.pirsr.propagation.annotation.EntryAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.EntryCommentAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.EntryFeatureTableAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.EntryKeywordAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleCommentAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleFeatureTableAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleKeywordAnnotation;
import org.proteininformationresource.pirsr.uniprot.model.EntryComment;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirsr.uniprot.model.EntryKeyword;
import org.proteininformationresource.pirsr.uniprot.model.EntryType;

import edu.udel.bioinformatics.pirsr.propagation.annotation.EntryAnnotationImpl;
import edu.udel.bioinformatics.pirsr.propagation.annotation.EntryCommentAnnotationImpl;
import edu.udel.bioinformatics.pirsr.propagation.annotation.EntryFeatureTableAnnotationImpl;
import edu.udel.bioinformatics.pirsr.propagation.annotation.EntryKeywordAnnotationImpl;
import edu.udel.bioinformatics.pirsr.propagation.annotation.RuleAnnotationImpl;
import edu.udel.bioinformatics.pirsr.propagation.annotation.RuleCommentAnnotationImpl;
import edu.udel.bioinformatics.pirsr.propagation.annotation.RuleFeatureTableAnnotationImpl;
import edu.udel.bioinformatics.pirsr.propagation.annotation.RuleKeywordAnnotationImpl;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 8, 2014<br><br>
 * 
 * The implementation of <code>AnnotationDataFactory</code>. 
 */
public class PropagationDataFactoryImpl implements PropagationDataFactory {

	
	public Alignment buildAlignment() {
		return new AlignmentImpl();
	}

	
	public PropagationRecord buildPropagationRecord() {
		return new PropagationRecordImpl();
	}

	
	public EntryCommentAnnotation buildEntryCommentAnnotation(String entryAC, String entryID, EntryType entryType, EntryComment comment) {
		return new EntryCommentAnnotationImpl(entryAC, entryID, entryType, comment);
	}

	
	public EntryKeywordAnnotation buildEntryKeywordAnnotation(String entryAC, String entryID, EntryType entryType, EntryKeyword keyword) {
		return new EntryKeywordAnnotationImpl(entryAC, entryID, entryType, keyword);
	}

	
	public EntryFeatureTableAnnotation buildEntryFeatureTableAnnotation(String entryAC, String entryID, EntryType entryType, EntryFeatureTable feature, String missMatchReason) {
		return new EntryFeatureTableAnnotationImpl(entryAC, entryID, entryType, feature, missMatchReason);
	}

	
	public RuleCommentAnnotation buildRuleCommentAnnotation(String ruleAC, String templateAC, CCLine comment) {
		return new RuleCommentAnnotationImpl(ruleAC, templateAC, comment);
	}

	
	public RuleKeywordAnnotation buildRuleKeywordAnnotation(String ruleAC, String templateAC, KWLine keyword) {
		return new RuleKeywordAnnotationImpl(ruleAC, templateAC, keyword);
	}

	
	public RuleFeatureTableAnnotation buildRuleFeatureTableAnnotation(String ruleAC, String templateAC, FeatureDescriptionLine featureDescriptionLine) {
		return new RuleFeatureTableAnnotationImpl(ruleAC, templateAC, featureDescriptionLine);
	}

	public static PropagationDataFactory getInstance() {
		return new PropagationDataFactoryImpl();
	}

	
	public PropagationManager buildPropagationManager(PropagationDataFactory propagationDataFactory, PIRRuleManager pirRuleManager) {
		return new PropagationManagerImpl(propagationDataFactory, pirRuleManager);
	}

	
	public EntryAnnotation buildEntryAnnotation(String entryAC, String entryID, EntryType entryType) {
		return new EntryAnnotationImpl(entryAC, entryID, entryType);
	}

	
	public RuleAnnotation buildRuleAnnotation(String ruleAC, String templateAC) {
		return new RuleAnnotationImpl(ruleAC, templateAC);
	}
}
