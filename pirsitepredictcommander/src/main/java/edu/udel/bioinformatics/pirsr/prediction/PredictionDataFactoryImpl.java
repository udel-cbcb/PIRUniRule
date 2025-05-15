package edu.udel.bioinformatics.pirsr.prediction;

import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.FeatureDescriptionLine;
import org.proteininformationresource.pirsr.model.KWLine;
import org.proteininformationresource.pirsr.model.PIRRuleDataFactory;
import org.proteininformationresource.pirsr.model.PIRRuleManager;
import org.proteininformationresource.pirsr.prediction.Alignment;
import org.proteininformationresource.pirsr.prediction.Prediction;
import org.proteininformationresource.pirsr.prediction.PredictionDataFactory;
import org.proteininformationresource.pirsr.prediction.PredictionManager;
import org.proteininformationresource.pirsr.prediction.PredictionRecord;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinCommentAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinFeatureTableAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinKeywordAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleCommentAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleFeatureTableAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleKeywordAnnotation;
import org.proteininformationresource.pirsr.uniprot.model.EntryComment;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirsr.uniprot.model.EntryKeyword;

import edu.udel.bioinformatics.pirsr.prediction.annotation.ProteinAnnotationImpl;
import edu.udel.bioinformatics.pirsr.prediction.annotation.ProteinCommentAnnotationImpl;
import edu.udel.bioinformatics.pirsr.prediction.annotation.ProteinFeatureTableAnnotationImpl;
import edu.udel.bioinformatics.pirsr.prediction.annotation.ProteinKeywordAnnotationImpl;
import edu.udel.bioinformatics.pirsr.prediction.annotation.RuleAnnotationImpl;
import edu.udel.bioinformatics.pirsr.prediction.annotation.RuleCommentAnnotationImpl;
import edu.udel.bioinformatics.pirsr.prediction.annotation.RuleFeatureTableAnnotationImpl;
import edu.udel.bioinformatics.pirsr.prediction.annotation.RuleKeywordAnnotationImpl;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 28, 2015<br>
 * <br>
 * 
 * The implementation of <code>PredictionDataFactory</code>.
 */
public class PredictionDataFactoryImpl implements PredictionDataFactory {

	public Alignment buildAlignment() {
		return new AlignmentImpl();
	}

	public PredictionRecord buildPredictionRecord() {
		return new PredictionRecordImpl();
	}

	public ProteinCommentAnnotation buildProteinCommentAnnotation(String proteinId, CCLine comment) {
		return new ProteinCommentAnnotationImpl(proteinId, comment);
	}

	public ProteinKeywordAnnotation buildProteinKeywordAnnotation(String proteinId, KWLine keyword) {
		return new ProteinKeywordAnnotationImpl(proteinId, keyword);
	}

	public ProteinFeatureTableAnnotation buildProteinFeatureTableAnnotation(String proteinId, FeatureDescriptionLine feature) {
		return new ProteinFeatureTableAnnotationImpl(proteinId, feature);
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

	public PredictionManager buildPredictionManager(PIRRuleDataFactory pirRuleDataFactory, PredictionDataFactory predictionDataFactory, PIRRuleManager pirRuleManager, String organism) {
		return new PredictionManagerImpl(pirRuleDataFactory, predictionDataFactory, pirRuleManager, organism);
	}

	public ProteinAnnotation buildProteinAnnotation(String proteinId) {
		return new ProteinAnnotationImpl(proteinId);
	}

	public RuleAnnotation buildRuleAnnotation(String ruleAC, String templateAC) {
		return new RuleAnnotationImpl(ruleAC, templateAC);
	}

	public Prediction buildPrediction(String type, String category, String description) {
		return new PredictionImpl(type, category, description);
	}

	

	

}
