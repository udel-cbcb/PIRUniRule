package edu.udel.bioinformatics.pirsr;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.model.ACLine;
import org.proteininformationresource.pirsr.model.AnnotationSection;
import org.proteininformationresource.pirsr.model.Block;
import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.CCNoneLine;
import org.proteininformationresource.pirsr.model.CaseStatement;
import org.proteininformationresource.pirsr.model.CommentsLine;
import org.proteininformationresource.pirsr.model.ComputingSection;
import org.proteininformationresource.pirsr.model.ControlBlock;
import org.proteininformationresource.pirsr.model.ControlStatement;
import org.proteininformationresource.pirsr.model.DCLine;
import org.proteininformationresource.pirsr.model.DELine;
import org.proteininformationresource.pirsr.model.DRLine;
import org.proteininformationresource.pirsr.model.DuplicateLine;
import org.proteininformationresource.pirsr.model.ElseCaseStatement;
import org.proteininformationresource.pirsr.model.ElseStatement;
import org.proteininformationresource.pirsr.model.FTFromLine;
import org.proteininformationresource.pirsr.model.FTNoneLine;
import org.proteininformationresource.pirsr.model.FeatureConstraintLine;
import org.proteininformationresource.pirsr.model.FeatureDescriptionLine;
import org.proteininformationresource.pirsr.model.FeatureType;
import org.proteininformationresource.pirsr.model.FusionBlock;
import org.proteininformationresource.pirsr.model.GOLine;
import org.proteininformationresource.pirsr.model.HeaderSection;
import org.proteininformationresource.pirsr.model.HistoryInfo;
import org.proteininformationresource.pirsr.model.HistorySection;
import org.proteininformationresource.pirsr.model.InternalCommentsLine;
import org.proteininformationresource.pirsr.model.KWLine;
import org.proteininformationresource.pirsr.model.KingdomLine;
import org.proteininformationresource.pirsr.model.Line;
import org.proteininformationresource.pirsr.model.PIRRule;
import org.proteininformationresource.pirsr.model.PIRRuleDataFactory;
import org.proteininformationresource.pirsr.model.PIRRuleManager;
import org.proteininformationresource.pirsr.model.PlasmidLine;
import org.proteininformationresource.pirsr.model.RelatedLine;
import org.proteininformationresource.pirsr.model.RuleCommentType;
import org.proteininformationresource.pirsr.model.ScopeBlock;
import org.proteininformationresource.pirsr.model.SizeLine;
import org.proteininformationresource.pirsr.model.TRLine;
import org.proteininformationresource.pirsr.model.TemplateLine;
import org.proteininformationresource.pirsr.model.XXLine;
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

import edu.udel.bioinformatics.pirsr.expression.AndOperatorExpressionImpl;
import edu.udel.bioinformatics.pirsr.expression.DefinedOperatorExpressionImpl;
import edu.udel.bioinformatics.pirsr.expression.FTGroupConditionExpressionImpl;
import edu.udel.bioinformatics.pirsr.expression.FeatureConditionExpressionImpl;
import edu.udel.bioinformatics.pirsr.expression.NotOperatorExpressionImpl;
import edu.udel.bioinformatics.pirsr.expression.OrOperatorExpressionImpl;
import edu.udel.bioinformatics.pirsr.expression.OrganismConditionExpressionImpl;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br><br>
 * 
 * An interface for creating entities, lines and conditions. 
 */
public class PIRRuleDataFactoryImpl implements PIRRuleDataFactory{

	
	public ACLine buildACLine(String accessionNumber) {
		ACLine acLine = new ACLineImpl(accessionNumber);
		return acLine;
	}
	
	

	
	public DCLine buildDCLine(String dataClass) {
		DCLine dcLine = new DCLineImpl(dataClass);
		return dcLine;
	}

	
	public TRLine buildTRLine(String dbName, String identifier1,
			String identifier2, String nbHits, String level) {
		TRLine trLine = new TRLineImpl(dbName, identifier1, identifier2, nbHits, level);
		return trLine;
	}

	


	
	
	public CCLine buildCCLine(RuleCommentType topic, List<String> description) {
		CCLine ccLine = new CCLineImpl(topic, description);
		return ccLine;
	}

	
	public KWLine buildKWLine(String keyword) {
		KWLine kwLine = new KWLineImpl(keyword);
		return kwLine;
	}

	
	public FTFromLine buildFTFromLine(String templateAccessionNumber) {
		FTFromLine ftFromLine = new FTFromLineImpl(templateAccessionNumber);
		return ftFromLine;
	}

	

	


	
	public SizeLine buildSizeLine(String sizeLimit) {
		SizeLine sizeLine = new SizeLineImpl(sizeLimit);
		return sizeLine;
	}

	

	
	public KingdomLine buildKingdomLine(String kingdom, String kingdomSubTaxon,
			List<String> exceptTaxonomicGroups, List<String> completeProteomeNotInTaxCodes) {
		KingdomLine kingdomLine = new KingdomLineImpl(kingdom, kingdomSubTaxon, exceptTaxonomicGroups, completeProteomeNotInTaxCodes);
		return kingdomLine;
	}

	
	public ScopeBlock buildScopeBlock(List<KingdomLine> kingdomLines) {
		ScopeBlock scopeBlock = new ScopeBlockImpl(kingdomLines);
		return scopeBlock;
	}


	
	public HistoryInfo buildHistoryInfo(String curator, String dateTime,
			String versionNumber, String type) {
		HistoryInfo historyInfo = new HistoryInfoImpl(curator, dateTime, versionNumber, type);
		return historyInfo;
	}

	
	public HistorySection buildHistorySection(List<HistoryInfo> historyInfo) {
		HistorySection historySection = new HistorySectionImpl(historyInfo);
		return historySection;
	}

	
	public PIRRule buildPIRRule(PIRRuleManager NameRuleManager,
			HeaderSection headerSection, AnnotationSection annotationSection,
			ComputingSection computingSection, HistorySection historySection) {
		PIRRule NameRule = new PIRRuleImpl(NameRuleManager, headerSection, annotationSection, computingSection, historySection);
		return NameRule;
	}

//	
//	public AnnotationSection getAnnotationSection(List<Block> blocks,
//			InternalCommentsBlock internalCommentsBlock) {
//		AnnotationSection annotationSection = new AnnotationSectionImpl(blocks, internalCommentsBlock);
//		return annotationSection;
//	}

	

	
	
	
	

	
	public FeatureConstraintLine buildFeatureConstraintLine(int featureGroupNumber,
			String conditionPattern) {
		FeatureConstraintLine featureCondition = new FeatureConstraintLineImpl(featureGroupNumber, conditionPattern);
		return featureCondition;
	}

	

	
//	public FeatureDescriptionLine buildFeatureDescription(String featureKey,
//			String featureFromPosition, String featureToPosition, List<String> descriptions) {
//		FeatureDescriptionLine featureDescription = new FeatureDescriptionLineImpl(featureKey, featureFromPosition, featureToPosition, descriptions);
//		return featureDescription;
//	}

	public FeatureDescriptionLine buildFeatureDescription(FeatureType featureKey,
			String featureFromPosition, String featureToPosition, List<String> descriptions) {
		FeatureDescriptionLine featureDescription = new FeatureDescriptionLineImpl(featureKey, featureFromPosition, featureToPosition, descriptions);
		return featureDescription;
	}


	

	
	public RelatedLine buildRelatedLine(List<String> relatedNameRuleAccessionNumbers) {
		RelatedLine relatedLine = new RelatedLineImpl(relatedNameRuleAccessionNumbers);
		return relatedLine;
	}



	
	public HeaderSection buildHeaderSection() {
		return new HeaderSectionImpl();
	}



	
	public PIRRule buildPIRRule(PIRRuleManager NameRuleManager) {
		return new PIRRuleImpl(NameRuleManager);
	}



	
	public ControlBlock buildControlBlock(ArrayList<ControlStatement> controlStatements) {
		return new ControlBlockImpl(controlStatements);
	}



	
	public CaseStatement buildCaseStatement() {
		return new CaseStatementImpl();
	}



	
	public AndOperatorExpression buildAndOperatorExpression(String leftParenthesis, Expression leftExpression, Expression rightExpression, String rightParenthesis) {
		return new AndOperatorExpressionImpl(leftParenthesis, leftExpression, rightExpression, rightParenthesis);
	}



	
	public OrOperatorExpression buildOrOperatorExpression(String leftParenthesis, Expression leftExpression, Expression rightExpression, String rightParenthesis) {
		return new OrOperatorExpressionImpl(leftParenthesis, leftExpression, rightExpression, rightParenthesis);
	}



	
	public FeatureConditionExpression buildFeatureConditionExpression(String featureConditionDescription) {
		return new FeatureConditionExpressionImpl(featureConditionDescription);
	}



	
	public FTGroupConditionExpression buildFeatureConditionExpression(int ftGroupNumber, ExpressionValue expressionValue) {
		return new FTGroupConditionExpressionImpl(ftGroupNumber, expressionValue);
	}



	
	public DefinedOperatorExpression buildDefinedOperatorExpression(String leftParenthesis, Expression definedExpression, String rightParenthesis) {
		return new DefinedOperatorExpressionImpl(leftParenthesis, definedExpression, rightParenthesis);
	}



	
	public NotOperatorExpression buildNotOperatorExpression(String leftParenthesis, Expression notExpression, String rightParenthesis) {
		return new NotOperatorExpressionImpl(leftParenthesis, notExpression, rightParenthesis);
	}



	
	public Block buildBlock() {
		return new BlockImpl();
	}



	
	public Block buildBlock(List<Line> lines) {
		return new BlockImpl(lines);
	}



	
	public ControlStatement buildEndCaseStatement() {
		return new EndCaseStatementImpl();
	}



	
	public CommentsLine buildCommentLine(String commentText) {
		return new CommentsLineImpl(commentText);

	}



	
	public XXLine buildXXLine() {
		return new XXLineImpl();
	}



	
	public FTNoneLine buildFTNoneLine() {
		return new FTNoneLineImpl();
	}



	
	public DELine buildDELine() {
		return new DELineImpl();
	}



	
	public ComputingSection buildComputingSection() {
		return new ComputingSectionImpl();
	}



	
	public InternalCommentsLine buildInternalCommentsLine(String comments) {
		InternalCommentsLine internalCommentsLine = new InternalCommentsLineImpl();
		internalCommentsLine.setComments(comments);
		return internalCommentsLine;
	}



	
	public AnnotationSection buildAnnotationSection() {
		return new AnnotationSectionImpl();
	}

	
	public GOLine buildGOLine(String geneOntologyAccessionNumber, String geneOntologyAspect, String geneOntologyTerm) {
		return new GOLineImpl(geneOntologyAccessionNumber, geneOntologyAspect, geneOntologyTerm);
		
	}



	
	public ScopeBlock buildScopeBlock() {
		return new ScopeBlockImpl();
	}



	
	public FusionBlock buildFusionBlock() {
		return new FusionBlockImpl();
	}



	
	public TemplateLine buildTemplateLine(List<String> templateAccessionNumbers) {
		return new TemplateLineImpl(templateAccessionNumbers);
	}



	
	public PlasmidLine buildPlasmidLine(List<String> organisms) {
		return new PlasmidLineImpl(organisms);
	}



	
	public DuplicateLine buildDuplicateLine(List<String> organisms) {
		return new DuplicateLineImpl(organisms);
	}



	
	public DRLine buildDRLine(String dbName, String featureName, String identifier, String nbHits, String triggerLevel) {
		DRLineImpl drLine = new DRLineImpl();
		drLine.setDBName(dbName);
		drLine.setFeatureName(featureName);
		drLine.setIdentifier(identifier);
		drLine.setNBHits(nbHits);
		drLine.setTriggerLevel(triggerLevel);
		return drLine;
	}



	
	public OrganismConditionExpression buildOrganismConditionExpression(OrganismConditionType organismConditionType, String organismName) {
		OrganismConditionExpression organismConditionExpression = new OrganismConditionExpressionImpl();
		organismConditionExpression.setOrganismConditionType(organismConditionType);
		organismConditionExpression.setOrganismName(organismName);
		return organismConditionExpression;
	}



	
	public ElseStatement buildElseStatement() {
		return new ElseStatementImpl();
	}



	
	public ElseCaseStatement buildElseCaseStatement() {
		return new ElseCaseStatementImpl();
	}



	
	public CCNoneLine buildCCNoneLine() {
		return new CCNoneLineImpl();
	}



	
	public List<ControlBlock> buildControlBlocks() {
		return new ArrayList<ControlBlock>();
	}



	public static PIRRuleDataFactory getInstance() {
		return new PIRRuleDataFactoryImpl();
	}



	
	public PIRRuleManager buildPIRRuleManager(PIRRuleDataFactory pirRuleDataFactory) {
		return new PIRRuleManagerImpl(pirRuleDataFactory);
	}



	
	public PIRRuleUtil buidPIRRuleUtil(PIRRule rule) {
		return new PIRRuleUtil(rule);
	}



}
