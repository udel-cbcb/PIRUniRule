package edu.udel.bioinformatics.pirsr.propagation;

import java.util.List;
import java.util.Map;

import org.proteininformationresource.pirsr.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirsr.model.PIRRule;
import org.proteininformationresource.pirsr.model.expression.AndOperatorExpression;
import org.proteininformationresource.pirsr.model.expression.ConditionExpression;
import org.proteininformationresource.pirsr.model.expression.DefinedOperatorExpression;
import org.proteininformationresource.pirsr.model.expression.Expression;
import org.proteininformationresource.pirsr.model.expression.ExpressionValue;
import org.proteininformationresource.pirsr.model.expression.FTGroupConditionExpression;
import org.proteininformationresource.pirsr.model.expression.FeatureConditionExpression;
import org.proteininformationresource.pirsr.model.expression.NotOperatorExpression;
import org.proteininformationresource.pirsr.model.expression.OrOperatorExpression;
import org.proteininformationresource.pirsr.model.expression.OrganismConditionExpression;
import org.proteininformationresource.pirsr.model.expression.OrganismConditionType;
import org.proteininformationresource.pirsr.uniprot.model.UniProtEntry;



/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 15, 2014<br>
 * <br>
 * 
 * Rule expression evaluator.
 */
public class ExpressionEvaluator {
	private UniProtEntry entry;
	private PIRRule rule;
	private Map<Integer, Boolean> ftGroupMatches;

	public ExpressionEvaluator(UniProtEntry entry, PIRRule rule, Map<Integer, Boolean> ftGroupMatches) {
		super();
		this.entry = entry;
		this.rule = rule;
		this.ftGroupMatches = ftGroupMatches;
	}

	public ExpressionValue evaluateExpression(Expression expression) {
		ExpressionValue eVal = ExpressionValue.FALSE;
		if (expression instanceof DefinedOperatorExpression) {
			DefinedOperatorExpression definedOperatorExpression = (DefinedOperatorExpression) expression;
			eVal = evaluateDefinedOperatorExpression(definedOperatorExpression);
		} else if (expression instanceof NotOperatorExpression) {
			NotOperatorExpression notOperatorExpression = (NotOperatorExpression) expression;
			eVal = evaluateNotOperatorExpression(notOperatorExpression);
		} else if (expression instanceof OrOperatorExpression) {
			OrOperatorExpression orOperatorExpression = (OrOperatorExpression) expression;
			eVal =  evaluateOrOperatorExpression(orOperatorExpression);
		} else if (expression instanceof AndOperatorExpression) {
			AndOperatorExpression andOperatorExpression = (AndOperatorExpression) expression;
			eVal = evaluateAndOperatorExpression(andOperatorExpression);
		} else if (expression instanceof ConditionExpression) {
			ConditionExpression conditionExpression = (ConditionExpression) expression;
			eVal = evaluateConditionExpression(conditionExpression);
		}
		return eVal;
	}

	private ExpressionValue evaluateConditionExpression(ConditionExpression conditionExpression) {
		ExpressionValue eVal = ExpressionValue.FALSE;
		if (conditionExpression instanceof FTGroupConditionExpression) {
			FTGroupConditionExpression ftGroupConditionExpression = (FTGroupConditionExpression) conditionExpression;
			eVal = evaluateFTGroupConditionExpression(ftGroupConditionExpression);
		} else if (conditionExpression instanceof FeatureConditionExpression) {
			FeatureConditionExpression featureConditionExpression = (FeatureConditionExpression) conditionExpression;
			eVal = evaluateFeatureConditionExpression(featureConditionExpression);
		} else if (conditionExpression instanceof OrganismConditionExpression) {
			OrganismConditionExpression organismConditionExpression = (OrganismConditionExpression) conditionExpression;
			eVal = evaluateOrganismConditionExpression(organismConditionExpression);
		}
		return eVal;
	}

	private ExpressionValue evaluateOrganismConditionExpression(OrganismConditionExpression organismConditionExpression) {
		ExpressionValue eVal = ExpressionValue.FALSE;
		OrganismConditionType organismConditionType = organismConditionExpression.getOrganimsConditionType();
		if (organismConditionType.equals(OrganismConditionType.OC)) {
			eVal = evaluateOrganismClassificationExpression(organismConditionExpression.getOrganismName());
		} else if (organismConditionType.equals(OrganismConditionType.OG)) {
			eVal = evaluateOrganelleExpression(organismConditionExpression.getOrganismName());
		} else if (organismConditionType.equals(OrganismConditionType.OS)) {
			eVal = evaluateOrganismSpeciesExpression(organismConditionExpression.getOrganismName());
		}
		return eVal;
	}

	private ExpressionValue evaluateOrganismSpeciesExpression(String organismSpeciesName) {
		ExpressionValue eVal = ExpressionValue.FALSE;
		if(this.entry.getOrganismSpecies().equals(organismSpeciesName)) {
			eVal = ExpressionValue.TRUE;
		}
		return eVal;
	}

	private ExpressionValue evaluateOrganelleExpression(String organelleName) {
		ExpressionValue eVal = ExpressionValue.FALSE;
//		List<Organelle> organelles = this.entry.getOrganelles();
//		for(Organelle organelle: organelles) {
//			if(organelle.getValue().equals(organelleName)) {
//				eVal = ExpressionValue.TRUE;
//			}
//		}
		String organelles = this.entry.getOrganelle().getOrganelle();
		if(organelles.contains(organelleName)) {
			eVal = ExpressionValue.TRUE;
		}
		return eVal;
	}

	private ExpressionValue evaluateOrganismClassificationExpression(String organismName) {
		ExpressionValue eVal = ExpressionValue.FALSE;
		List<String> lineage = this.entry.getOrganismClassification().getOrganismClassification();
		for(String ncbiTaxon: lineage) {
			if(ncbiTaxon.equals(organismName)) {
				eVal = ExpressionValue.TRUE;
			}
		}
		return eVal;
	}

	private ExpressionValue evaluateFeatureConditionExpression(FeatureConditionExpression featureConditionExpression) {
		ExpressionValue eVal = ExpressionValue.FALSE;
		String featureConditionDescription = featureConditionExpression.getFeatureConditionDescription();
		if (featureConditionDescription.startsWith(UniRuleFlatFileConstants.SRHMM)) {
			String ruleAC = this.rule.getHeaderSection().getACLine().getAccessionNumber();
			if (featureConditionDescription.replaceAll(UniRuleFlatFileConstants.SRHMM, UniRuleFlatFileConstants.PIRSR).equals(ruleAC)) {
				eVal = ExpressionValue.TRUE;
			} else {
				eVal = ExpressionValue.FALSE;
			}
//			System.out.println(featureDescription+ "|"+ ruleAC);
//			System.out.println("ConditionExpression: " + eVal);
		}
		
		return eVal;
	}

	private ExpressionValue evaluateFTGroupConditionExpression(FTGroupConditionExpression ftGroupConditionExpression) {
		int ftGroupNumber = ftGroupConditionExpression.getFTGroupNumber();
		ExpressionValue eVal = ExpressionValue.FALSE;
		Boolean match = this.ftGroupMatches.get(new Integer(ftGroupNumber));
		if(match == null) {
			System.err.println("There is no "+ftGroupConditionExpression+", program quit.\n");
			System.exit(1);
		}
		if (match) {
			eVal = ExpressionValue.TRUE;
		} else {
			eVal = ExpressionValue.FALSE;
		}
		//System.out.println("FTGroup " +ftGroupNumber+": " + eVal);
		return eVal;
	}

	private ExpressionValue evaluateAndOperatorExpression(AndOperatorExpression andOperatorExpression) {
		ExpressionValue eVal = ExpressionValue.FALSE;
		Expression leftExpression = andOperatorExpression.getLeftExpression();
		Expression rightExpression = andOperatorExpression.getRightExpression();
		if (evaluateExpression(leftExpression).equals(ExpressionValue.TRUE) && evaluateExpression(rightExpression).equals(ExpressionValue.TRUE)) {
			eVal = ExpressionValue.TRUE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.TRUE)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.FALSE)) {
			eVal = ExpressionValue.FALSE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.TRUE)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.UNDEF)) {
			eVal = ExpressionValue.UNDEF;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.FALSE)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.TRUE)) {
			eVal = ExpressionValue.FALSE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.FALSE)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.FALSE)) {
			eVal = ExpressionValue.FALSE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.FALSE)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.UNDEF)) {
			eVal = ExpressionValue.FALSE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.UNDEF)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.TRUE)) {
			eVal = ExpressionValue.UNDEF;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.UNDEF)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.FALSE)) {
			eVal = ExpressionValue.UNDEF;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.UNDEF)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.UNDEF)) {
			eVal = ExpressionValue.UNDEF;
		}
		return eVal;
	}

	private ExpressionValue evaluateOrOperatorExpression(OrOperatorExpression orOperatorExpression) {
		ExpressionValue eVal = ExpressionValue.FALSE;
		Expression leftExpression = orOperatorExpression.getLeftExpression();
		Expression rightExpression = orOperatorExpression.getRightExpression();
		if (evaluateExpression(leftExpression).equals(ExpressionValue.TRUE) && evaluateExpression(rightExpression).equals(ExpressionValue.TRUE)) {
			eVal = ExpressionValue.TRUE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.TRUE)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.FALSE)) {
			eVal = ExpressionValue.TRUE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.TRUE)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.UNDEF)) {
			eVal = ExpressionValue.TRUE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.FALSE)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.TRUE)) {
			eVal = ExpressionValue.TRUE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.FALSE)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.FALSE)) {
			eVal = ExpressionValue.FALSE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.FALSE)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.UNDEF)) {
			eVal = ExpressionValue.UNDEF;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.UNDEF)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.TRUE)) {
			eVal = ExpressionValue.TRUE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.UNDEF)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.FALSE)) {
			eVal = ExpressionValue.FALSE;
		} else if (evaluateExpression(leftExpression).equals(ExpressionValue.UNDEF)
				&& evaluateExpression(rightExpression).equals(ExpressionValue.UNDEF)) {
			eVal = ExpressionValue.UNDEF;
		}
		return eVal;
	}

	private ExpressionValue evaluateNotOperatorExpression(NotOperatorExpression notOperatorExpression) {
		ExpressionValue eVal = ExpressionValue.FALSE;
		Expression expression = notOperatorExpression.getExpression();
		if (evaluateExpression(expression).equals(ExpressionValue.TRUE)) {
			eVal = ExpressionValue.FALSE;
		} else if (evaluateExpression(expression).equals(ExpressionValue.FALSE)) {
			eVal = ExpressionValue.TRUE;
		} else if (evaluateExpression(expression).equals(ExpressionValue.UNDEF)) {
			eVal = ExpressionValue.UNDEF;
		}
		return eVal;
	}

	private ExpressionValue evaluateDefinedOperatorExpression(DefinedOperatorExpression definedOperatorExpression) {
		ExpressionValue eVal = ExpressionValue.FALSE;
		Expression expression = definedOperatorExpression.getExpression();
		if (evaluateExpression(expression).equals(ExpressionValue.TRUE)) {
			eVal = ExpressionValue.TRUE;
		} else if (evaluateExpression(expression).equals(ExpressionValue.FALSE)) {
			eVal = ExpressionValue.TRUE;
		} else if (evaluateExpression(expression).equals(ExpressionValue.UNDEF)) {
			eVal = ExpressionValue.FALSE;
		}
		return eVal;

	}

}
