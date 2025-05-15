package org.proteininformationresource.pirsr.prediction;

import org.proteininformationresource.pirsr.model.ControlStatement;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinAnnotation;
import org.proteininformationresource.pirsr.prediction.annotation.RuleAnnotation;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 27, 2015<br>
 * <br>
 * 
 * Prediction information.
 */
public interface PredictionRecord {
	
ProteinAnnotation getProteinAnnotation();
	
/**
 * Sets the protein annotation information.
 * @param proteinAnnotation the protein annotation information.
 */
	void setProteinAnnotation(ProteinAnnotation proteinAnnotation);
	
	/**
	 * Gets the rule annotation information.
	 * @return the rule annotation information.
	 */
	RuleAnnotation getRuleAnnotation();
	
	/**
	 * Sets the rule annotation information.
	 * @param ruleAnnotation the rule annotation information.
	 */
	void setRuleAnnotation(RuleAnnotation ruleAnnotation);
	
	/**
	 * Gets the control statement.
	 * @return the control statement.
	 */
	ControlStatement getControlStatement();
	
	/**
	 * Sets the control statement.
	 * @param controlStatement the control statement.
	 */
	void setControlStatement(ControlStatement controlStatement);
	
	/**
	 * Gets the control statement evaluation value.
	 * @return the control statement evaluation value.
	 */
	ExpressionValue getControlStatementEvaluation();
	
	/**
	 * Sets the control statement evaluation value.
	 * @param evaluation
	 */
	void setControlStatementEvalution(ExpressionValue evaluation);
	
	/**
	 * Check if it is predicted.
	 * @return true if it is predicted, false otherwise.
	 */
	boolean isPredicted();
	
	/**
	 * Sets if it is predicted.
	 * @param predicted true if it is predicted, false otherwise.
	 */
	void setPredicted(boolean predicted);
	
	/**
	 * Gets the prediction note.
	 * @return the prediction note.
	 */
	String getNote();
	
	/**
	 * Sets the prediction note.
	 * @param note the prediction note.
	 */
	void setNote(String note);
	
	/**
	 * Chekc if it is a match.
	 * @return true if it is a match, false otherwise.
	 */
	boolean isMatched();
	
	/**
	 * Sets if it is a match.
	 * @param matched true if it is a match, false otherwise.
	 */
	void setMatched(boolean matched);
	
	/**
	 * Create prediction record in report format.
	 * @return the prediction record in report format.
	 */
	String toReport();
	
	/**
	 * Create prediction record in Kraken format.
	 * @return the prediction record in Kraken format.
	 */
	String toKraken();

}
