package org.proteininformationresource.pirsr.prediction;

import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.FeatureDescriptionLine;
import org.proteininformationresource.pirsr.model.KWLine;
import org.proteininformationresource.pirsr.model.PIRRuleDataFactory;
import org.proteininformationresource.pirsr.model.PIRRuleManager;
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
import org.proteininformationresource.pirsr.uniprot.model.EntryType;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 27, 2015<br>
 * <br>
 * 
 * An interface for creating prediction related objects.
 */
public interface PredictionDataFactory {
	/**
	 * Gets a <code>Alignment</code> object.
	 * @return a <code>Alignment</code> object.
	 */
	Alignment buildAlignment();
	
	/**
	 * Gets a <code>PredictionRecord</code> object.
	 * @return a <code>PredictionRecord</code> object.
	 */
	PredictionRecord buildPredictionRecord();
	
	/**
	 * Gets a <code>ProteinCommentAnnotation</code> object.
	 * @param proteinId protein identifier.
	 * @param comment predicted comment.
	 * @return a <code>ProteinCommentAnnotation</code> object.
	 */
	ProteinCommentAnnotation buildProteinCommentAnnotation(String proteinId, CCLine comment);

	/**
	 * Gets a <code>ProteinKeywordAnnotation</code> object.
	 * @param proteinId protein identifier.
	 * @param keyword predicted keyword.
	 * @return a <code>ProteinKeywordAnnotation</code> object.
	 */
	ProteinKeywordAnnotation buildProteinKeywordAnnotation(String proteinId, KWLine keyword);

	/**
	 * Gets a <code>ProteinFeatureTableAnnotation</code> object.
	 * @param proteinId proteinId.
	 * @param feature predicted feature
	 * @return a <code>ProteinFeatureTableAnnotation</code> object.
	 */
	ProteinFeatureTableAnnotation buildProteinFeatureTableAnnotation(String proteinId, FeatureDescriptionLine feature);

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
	 * Gets a <code>PredictionManager</code> object.
	 * @param pirRuleDataFactory 
	 * @param predictionDataFactory prediction data factory.
	 * @param pirRuleManager PIR rule manager.
	 * @return a <code>PredictionManager</code> object.
	 */
	PredictionManager buildPredictionManager(PIRRuleDataFactory pirRuleDataFactory, PredictionDataFactory predictionDataFactory, PIRRuleManager pirRuleManager, String organism);
	
	/**
	 * Gets a <code>ProteinAnntation</code> object.
	 * @param proteinId protein identifier.
	 * @return a <code>ProteinAnntation</code> object.
	 */
	ProteinAnnotation buildProteinAnnotation(String proteinId);
	
	/**
	 * Gets a <code>RuleAnnotation</code> object.
	 * @param ruleAC rule accession number.
	 * @param templateAC rule template sequence accession number.
	 * @return a <code>RuleAnnotation</code> object.
	 */
	RuleAnnotation buildRuleAnnotation(String ruleAC, String templateAC);
	
	Prediction buildPrediction(String type, String category, String description);
	
}
