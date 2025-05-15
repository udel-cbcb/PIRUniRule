package edu.udel.bioinformatics.pirsr.expression;

import org.proteininformationresource.pirsr.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;
import org.proteininformationresource.pirsr.model.expression.FeatureConditionExpression;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br><br>
 * 
 * on the presence of a feature in the GFF file.  'Feature' only refers to the (rule) 
 * triggering feature + features that overlap (by at least 50%) with it. 
 */
public class FeatureConditionExpressionImpl implements FeatureConditionExpression{

	private ExpressionValue expressionValue;
	private String featureConditionDescription;
	private String leftParenthesis;
	private String rightParenthesis;
	
	public FeatureConditionExpressionImpl(String featureConditionDescription) {
		super();
		this.expressionValue = ExpressionValue.FALSE;
		this.featureConditionDescription = featureConditionDescription;
	}

	public FeatureConditionExpressionImpl() {
		super();
		this.expressionValue = ExpressionValue.FALSE;
		this.featureConditionDescription = null;
	}


	public ExpressionValue getExpressionValue(String featureConditionDescription) {
		if(this.featureConditionDescription.equals(featureConditionDescription)) {
			return ExpressionValue.TRUE;
		}
		else {
			return ExpressionValue.FALSE;
		}
	}

	public ExpressionValue getExpressionValue() {
		return this.expressionValue;
	}

	
	public void setFeatureConditionDescription(String featureConditionDescription) {
		this.featureConditionDescription = featureConditionDescription;
	}

	
	public String getLeftParenthesis() {
		return this.leftParenthesis;
	}

	
	public void setLeftParenthesis(String leftParenthesis) {
		this.leftParenthesis = leftParenthesis;
	}

	
	public String getRightParenthesis() {
		return this.rightParenthesis;
	}

	
	public void setRightParenthesis(String rightParenthesis) {
		this.rightParenthesis = rightParenthesis;
	}

	
	public String getFeatureConditionDescription() {
		return this.featureConditionDescription;
	}
	
	public String toString() {
		String str = "";
		if(this.leftParenthesis != null) {
			str += UniRuleFlatFileConstants.LEFT_PARENTHESIS;
		}
		
		str += UniRuleFlatFileConstants.LEFT_ANGLE_BRAKET+UniRuleFlatFileConstants.FEATURE_CONDITION +this.featureConditionDescription+ UniRuleFlatFileConstants.RIGHT_ANGLE_BRAKET;
		if(this.rightParenthesis != null) {
			str += UniRuleFlatFileConstants.RIGHT_PARENTHESIS;
		}
		return str;
	}

//	
//	public void setExpressionValue(ExpressionValue expressionValue) {
//		this.expressionValue = expressionValue;
//	}

}
