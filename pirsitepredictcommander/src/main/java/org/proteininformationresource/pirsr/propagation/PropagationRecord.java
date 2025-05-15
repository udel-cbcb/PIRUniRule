package org.proteininformationresource.pirsr.propagation;

import org.proteininformationresource.pirsr.model.ControlStatement;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;
import org.proteininformationresource.pirsr.propagation.annotation.EntryAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleAnnotation;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 */
public interface PropagationRecord {
	
	EntryAnnotation getEntryAnnotation();
	
	void setEntryAnnotation(EntryAnnotation entryAnnotation);
	
	RuleAnnotation getRuleAnnotation();
	
	void setRuleAnnotation(RuleAnnotation ruleAnnotation);
	
	ControlStatement getControlStatement();
	
	void setControlStatement(ControlStatement controlStatement);
	
	ExpressionValue getControlStatementEvaluation();
	
	void setControlStatementEvalution(ExpressionValue evaluation);
	
	PropagationStats getPropagationStats();
	
	void setPropagationStatistics(PropagationStats propagationStatistics);
	
	boolean isPropagated();
	
	void setPropagated(boolean propagated);
	
	String getNote();
	
	void setNote(String note);
	
	boolean isMatched();
	
	void setMatched(boolean matched);
	
	double getPrecision();
	
	void setPrecision(double precision);
	
	double getConfidence();
	
	void setConfidence(double confidence);
	
	String toReport();
	
	String toKraken();
	
}
