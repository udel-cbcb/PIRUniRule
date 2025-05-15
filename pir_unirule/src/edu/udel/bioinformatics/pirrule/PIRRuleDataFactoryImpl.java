package edu.udel.bioinformatics.pirrule;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirrule.model.ACLine;
import org.proteininformationresource.pirrule.model.AnnotationSection;
import org.proteininformationresource.pirrule.model.Block;
import org.proteininformationresource.pirrule.model.CCLine;
import org.proteininformationresource.pirrule.model.CCNoneLine;
import org.proteininformationresource.pirrule.model.RuleCommentType;
import org.proteininformationresource.pirrule.model.CaseStatement;
import org.proteininformationresource.pirrule.model.CommentsLine;
import org.proteininformationresource.pirrule.model.ComputingSection;
import org.proteininformationresource.pirrule.model.ControlBlock;
import org.proteininformationresource.pirrule.model.ControlStatement;
import org.proteininformationresource.pirrule.model.DCLine;
import org.proteininformationresource.pirrule.model.DELine;
import org.proteininformationresource.pirrule.model.DRLine;
import org.proteininformationresource.pirrule.model.FeatureType;
import org.proteininformationresource.pirrule.model.PIRRuleDataFactory;
import org.proteininformationresource.pirrule.model.DuplicateLine;
import org.proteininformationresource.pirrule.model.ElseCaseStatement;
import org.proteininformationresource.pirrule.model.ElseStatement;
import org.proteininformationresource.pirrule.model.FTFromLine;
import org.proteininformationresource.pirrule.model.FTNoneLine;
import org.proteininformationresource.pirrule.model.FeatureConstraintLine;
import org.proteininformationresource.pirrule.model.FeatureDescriptionLine;
import org.proteininformationresource.pirrule.model.FusionBlock;
import org.proteininformationresource.pirrule.model.GOLine;
import org.proteininformationresource.pirrule.model.HeaderSection;
import org.proteininformationresource.pirrule.model.HistoryInfo;
import org.proteininformationresource.pirrule.model.HistorySection;
import org.proteininformationresource.pirrule.model.InternalCommentsLine;
import org.proteininformationresource.pirrule.model.KWLine;
import org.proteininformationresource.pirrule.model.KingdomLine;
import org.proteininformationresource.pirrule.model.Line;
import org.proteininformationresource.pirrule.model.PIRRule;
import org.proteininformationresource.pirrule.model.PIRRuleManager;
import org.proteininformationresource.pirrule.model.PlasmidLine;
import org.proteininformationresource.pirrule.model.RelatedLine;
import org.proteininformationresource.pirrule.model.ScopeBlock;
import org.proteininformationresource.pirrule.model.SizeLine;
import org.proteininformationresource.pirrule.model.TRLine;
import org.proteininformationresource.pirrule.model.TemplateLine;
import org.proteininformationresource.pirrule.model.XXLine;
import org.proteininformationresource.pirrule.model.expression.AndOperatorExpression;
import org.proteininformationresource.pirrule.model.expression.DefinedOperatorExpression;
import org.proteininformationresource.pirrule.model.expression.Expression;
import org.proteininformationresource.pirrule.model.expression.ExpressionValue;
import org.proteininformationresource.pirrule.model.expression.FTGroupConditionExpression;
import org.proteininformationresource.pirrule.model.expression.FeatureConditionExpression;
import org.proteininformationresource.pirrule.model.expression.NotOperatorExpression;
import org.proteininformationresource.pirrule.model.expression.OrOperatorExpression;
import org.proteininformationresource.pirrule.model.expression.OrganismConditionExpression;
import org.proteininformationresource.pirrule.model.expression.OrganismConditionType;

import edu.udel.bioinformatics.pirrule.expression.AndOperatorExpressionImpl;
import edu.udel.bioinformatics.pirrule.expression.DefinedOperatorExpressionImpl;
import edu.udel.bioinformatics.pirrule.expression.FTGroupConditionExpressionImpl;
import edu.udel.bioinformatics.pirrule.expression.FeatureConditionExpressionImpl;
import edu.udel.bioinformatics.pirrule.expression.NotOperatorExpressionImpl;
import edu.udel.bioinformatics.pirrule.expression.OrOperatorExpressionImpl;
import edu.udel.bioinformatics.pirrule.expression.OrganismConditionExpressionImpl;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br><br>
 * 
 * An interface for creating entities, lines and conditions. 
 */
public class PIRRuleDataFactoryImpl implements PIRRuleDataFactory{

	@Override
	public ACLine buildACLine(String accessionNumber) {
		ACLine acLine = new ACLineImpl(accessionNumber);
		return acLine;
	}
	
	

	@Override
	public DCLine buildDCLine(String dataClass) {
		DCLine dcLine = new DCLineImpl(dataClass);
		return dcLine;
	}

	@Override
	public TRLine buildTRLine(String dbName, String identifier1,
			String identifier2, String nbHits, String level) {
		TRLine trLine = new TRLineImpl(dbName, identifier1, identifier2, nbHits, level);
		return trLine;
	}

	


	
	@Override
	public CCLine buildCCLine(RuleCommentType topic, List<String> description) {
		CCLine ccLine = new CCLineImpl(topic, description);
		return ccLine;
	}

	@Override
	public KWLine buildKWLine(String keyword) {
		KWLine kwLine = new KWLineImpl(keyword);
		return kwLine;
	}

	@Override
	public FTFromLine buildFTFromLine(String templateAccessionNumber) {
		FTFromLine ftFromLine = new FTFromLineImpl(templateAccessionNumber);
		return ftFromLine;
	}

	

	


	@Override
	public SizeLine buildSizeLine(String sizeLimit) {
		SizeLine sizeLine = new SizeLineImpl(sizeLimit);
		return sizeLine;
	}

	

	@Override
	public KingdomLine buildKingdomLine(String kingdom, String kingdomSubTaxon,
			List<String> exceptTaxonomicGroups, List<String> completeProteomeNotInTaxCodes) {
		KingdomLine kingdomLine = new KingdomLineImpl(kingdom, kingdomSubTaxon, exceptTaxonomicGroups, completeProteomeNotInTaxCodes);
		return kingdomLine;
	}

	@Override
	public ScopeBlock buildScopeBlock(List<KingdomLine> kingdomLines) {
		ScopeBlock scopeBlock = new ScopeBlockImpl(kingdomLines);
		return scopeBlock;
	}


	@Override
	public HistoryInfo buildHistoryInfo(String curator, String dateTime,
			String versionNumber, String type) {
		HistoryInfo historyInfo = new HistoryInfoImpl(curator, dateTime, versionNumber, type);
		return historyInfo;
	}

	@Override
	public HistorySection buildHistorySection(List<HistoryInfo> historyInfo) {
		HistorySection historySection = new HistorySectionImpl(historyInfo);
		return historySection;
	}

	@Override
	public PIRRule buildPIRRule(PIRRuleManager NameRuleManager,
			HeaderSection headerSection, AnnotationSection annotationSection,
			ComputingSection computingSection, HistorySection historySection) {
		PIRRule NameRule = new PIRRuleImpl(NameRuleManager, headerSection, annotationSection, computingSection, historySection);
		return NameRule;
	}

//	@Override
//	public AnnotationSection getAnnotationSection(List<Block> blocks,
//			InternalCommentsBlock internalCommentsBlock) {
//		AnnotationSection annotationSection = new AnnotationSectionImpl(blocks, internalCommentsBlock);
//		return annotationSection;
//	}

	

	
	
	
	

	@Override
	public FeatureConstraintLine buildFeatureConstraintLine(int featureGroupNumber,
			String conditionPattern) {
		FeatureConstraintLine featureCondition = new FeatureConstraintLineImpl(featureGroupNumber, conditionPattern);
		return featureCondition;
	}

	

	@Override
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


	

	@Override
	public RelatedLine buildRelatedLine(List<String> relatedNameRuleAccessionNumbers) {
		RelatedLine relatedLine = new RelatedLineImpl(relatedNameRuleAccessionNumbers);
		return relatedLine;
	}



	@Override
	public HeaderSection buildHeaderSection() {
		return new HeaderSectionImpl();
	}



	@Override
	public PIRRule buildPIRRule(PIRRuleManager NameRuleManager) {
		return new PIRRuleImpl(NameRuleManager);
	}



	@Override
	public ControlBlock buildControlBlock(ArrayList<ControlStatement> controlStatements) {
		return new ControlBlockImpl(controlStatements);
	}



	@Override
	public CaseStatement buildCaseStatement() {
		return new CaseStatementImpl();
	}



	@Override
	public AndOperatorExpression buildAndOperatorExpression(String leftParenthesis, Expression leftExpression, Expression rightExpression, String rightParenthesis) {
		return new AndOperatorExpressionImpl(leftParenthesis, leftExpression, rightExpression, rightParenthesis);
	}



	@Override
	public OrOperatorExpression buildOrOperatorExpression(String leftParenthesis, Expression leftExpression, Expression rightExpression, String rightParenthesis) {
		return new OrOperatorExpressionImpl(leftParenthesis, leftExpression, rightExpression, rightParenthesis);
	}



	@Override
	public FeatureConditionExpression buildFeatureConditionExpression(String featureConditionDescription) {
		return new FeatureConditionExpressionImpl(featureConditionDescription);
	}



	@Override
	public FTGroupConditionExpression buildFeatureConditionExpression(int ftGroupNumber, ExpressionValue expressionValue) {
		return new FTGroupConditionExpressionImpl(ftGroupNumber, expressionValue);
	}



	@Override
	public DefinedOperatorExpression buildDefinedOperatorExpression(String leftParenthesis, Expression definedExpression, String rightParenthesis) {
		return new DefinedOperatorExpressionImpl(leftParenthesis, definedExpression, rightParenthesis);
	}



	@Override
	public NotOperatorExpression buildNotOperatorExpression(String leftParenthesis, Expression notExpression, String rightParenthesis) {
		return new NotOperatorExpressionImpl(leftParenthesis, notExpression, rightParenthesis);
	}



	@Override
	public Block buildBlock() {
		return new BlockImpl();
	}



	@Override
	public Block buildBlock(List<Line> lines) {
		return new BlockImpl(lines);
	}



	@Override
	public ControlStatement buildEndCaseStatement() {
		return new EndCaseStatementImpl();
	}



	@Override
	public CommentsLine buildCommentLine(String commentText) {
		return new CommentsLineImpl(commentText);

	}



	@Override
	public XXLine buildXXLine() {
		return new XXLineImpl();
	}



	@Override
	public FTNoneLine buildFTNoneLine() {
		return new FTNoneLineImpl();
	}



	@Override
	public DELine buildDELine() {
		return new DELineImpl();
	}



	@Override
	public ComputingSection buildComputingSection() {
		return new ComputingSectionImpl();
	}



	@Override
	public InternalCommentsLine buildInternalCommentsLine(String comments) {
		InternalCommentsLine internalCommentsLine = new InternalCommentsLineImpl();
		internalCommentsLine.setComments(comments);
		return internalCommentsLine;
	}



	@Override
	public AnnotationSection buildAnnotationSection() {
		return new AnnotationSectionImpl();
	}

	@Override
	public GOLine buildGOLine(String geneOntologyAccessionNumber, String geneOntologyAspect, String geneOntologyTerm) {
		return new GOLineImpl(geneOntologyAccessionNumber, geneOntologyAspect, geneOntologyTerm);
		
	}



	@Override
	public ScopeBlock buildScopeBlock() {
		return new ScopeBlockImpl();
	}



	@Override
	public FusionBlock buildFusionBlock() {
		return new FusionBlockImpl();
	}



	@Override
	public TemplateLine buildTemplateLine(List<String> templateAccessionNumbers) {
		return new TemplateLineImpl(templateAccessionNumbers);
	}



	@Override
	public PlasmidLine buildPlasmidLine(List<String> organisms) {
		return new PlasmidLineImpl(organisms);
	}



	@Override
	public DuplicateLine buildDuplicateLine(List<String> organisms) {
		return new DuplicateLineImpl(organisms);
	}



	@Override
	public DRLine buildDRLine(String dbName, String featureName, String identifier, String nbHits, String triggerLevel) {
		DRLineImpl drLine = new DRLineImpl();
		drLine.setDBName(dbName);
		drLine.setFeatureName(featureName);
		drLine.setIdentifier(identifier);
		drLine.setNBHits(nbHits);
		drLine.setTriggerLevel(triggerLevel);
		return drLine;
	}



	@Override
	public OrganismConditionExpression buildOrganismConditionExpression(OrganismConditionType organismConditionType, String organismName) {
		OrganismConditionExpression organismConditionExpression = new OrganismConditionExpressionImpl();
		organismConditionExpression.setOrganismConditionType(organismConditionType);
		organismConditionExpression.setOrganismName(organismName);
		return organismConditionExpression;
	}



	@Override
	public ElseStatement buildElseStatement() {
		return new ElseStatementImpl();
	}



	@Override
	public ElseCaseStatement buildElseCaseStatement() {
		return new ElseCaseStatementImpl();
	}



	@Override
	public CCNoneLine buildCCNoneLine() {
		return new CCNoneLineImpl();
	}



	@Override
	public List<ControlBlock> buildControlBlocks() {
		return new ArrayList<ControlBlock>();
	}



	public static PIRRuleDataFactory getInstance() {
		return new PIRRuleDataFactoryImpl();
	}



	@Override
	public PIRRuleManager buildPIRRuleManager(PIRRuleDataFactory pirRuleDataFactory) {
		return new PIRRuleManagerImpl(pirRuleDataFactory);
	}



	
	public PIRRuleUtil buidPIRRuleUtil(PIRRule rule) {
		return new PIRRuleUtil(rule);
	}



}
