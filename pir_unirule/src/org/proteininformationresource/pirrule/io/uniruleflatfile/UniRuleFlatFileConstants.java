package org.proteininformationresource.pirrule.io.uniruleflatfile;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: Jan 31, 2014<br>
 * <br>
 * 
 */
public final class UniRuleFlatFileConstants {

	public static final String XX_LINE_START = "XX";
	
	public static final String INTERNAL_COMMENTS_START = "**";
	
	public static final String COMMENTS_START ="Comments: ";
	
	public static final String RULE_SEPARATOR  = "//";
	
	/* Header section */
	public static final String AC_LINE_START = "AC   ";
	
	public static final String AC_LINE_END = ";";
	
	public static final String AC_ACCESSION_NUMBER_SEPARATOR = "; ";
	
	
	public static final String DC_LINE_START = "DC   ";
	
	public static final String DC_LINE_END = ";";
		
	public static final String DC_DATA_CLASS_SEPARATOR = ";";
	
	public static final String TR_LINE_START = "TR   ";
	
	public static final String TR_LINE_SEPARATOR = "; ";
	
	public static final String TR_LINE_LEVEL = "level=";
	
	
	/* Annotation section */
	public static final String CC_LINE_START = "CC   ";
	
	public static final String CC_LINE_TOPIC_START = "CC   -!- ";
	
	public static final String CC_LINE_DESCRIPTION_START = "CC       ";
	
	public static final String CC_TOPIC_COFACTOR = "COFACTOR";
	
	
    public static final String CC_TOPIC_PTM = "PTM";
	
	public static final String CC_TOPIC_SIMILARITY = "SIMILARITY";
	
	public static final String CC_TOPIC_SUBCELLULAR = "SUBCELLULAR LOCATION";
	
	public static final String CC_TOPIC_SUBUNIT = "SUBUNIT";
	
	
	public static final String DE_LINE_START = "DE   ";
	
	public static final String DE_RECNAME_START = "DE   RecName: ";
	
	public static final String DE_ALTNAME_START = "DE   AltName: ";
	
	public static final String DE_FOLLOWUP_START = "DE            ";
	
	public static final String DR_LINE_START = "DR   ";
	
	public static final String KW_LINE_START = "KW   ";
	
	public static final String GO_LINE_START = "GO   ";
	
	public static final String FT_LINE_START = "FT   ";
	
	public static final String FT_FEATURE_ACT_SITE = "ACT_SITE";
	
	public static final String FT_FEATURE_BINDING = "BINDING";
	
	public static final String FT_FEATURE_CHAIN = "CHAIN";
	
	public static final String FT_FEATURE_CROSSLNK = "CROSSLINK";
	
	public static final String FT_FEATURE_DISULFID = "DISULFID";
	
	public static final String FT_FEATURE_LIPID = "LIPID";
	
	public static final String FT_FEATURE_METAL = "METAL";
	
	public static final String FT_FEATURE_MOD_RES = "MOD_RES";
	
	public static final String FT_FEATURE_MOTIF = "MOTIF";
	
	public static final String FT_FEATURE_NP_BIND = "NP_BIND";
	
	public static final String FT_FEATURE_REGION = "REGION";
	
	public static final String FT_FEATURE_SITE = "SITE";
	
	public static final String FT_FEATURE_ZN_FING = "ZN_FING";
	
	public static final String FT_FROM_LINE_START = "FT   From: ";
	
	public static final String FT_CONSTRAINTS_LINE_START = "FT   Group: ";
	
	public static final String SRHMM = "SRHMM";
	
	public static final String PIRSR = "PIRSR";
	
	/* Computing section */
	public static final String SIZE_LINE_START = "Size: ";
	
	public static final String SIZE_LINE_END = ";";
	
	public static final String PLASMID_LINE_START = "Plasmid: ";
	
	public static final String PLASMID_LINE_IN = "Plasmid: in ";
	
	public static final String TEMPLATE_LINE_START = "Template: ";
	
	public static final String RELATED_LINE_START = "Related: ";
	
	public static final String RELATED_LINE_END = ";";
	
	public static final String RELATED_LINE_SEPARATOR = "; ";
	
	public static final String DUPLICATE_LINE_START = "Duplicate: ";
	
	public static final String DUPLICATE_LINE_IN = "Duplicate: in ";
	
	public static final String FUSION_BLOCK_START = "Fusion:";
	
	public static final String FUSION_NTER_START = " Nter: ";
	
	public static final String FUSION_CTER_START = " Cter: ";
	
	public static final String SCOPE_BLOCK_START = "Scope:";
	
	public static final String SCOPE_KINGDOM_LINE_SEPARATOR = "; ";
		
	public static final String SCOPE_KINGDOM_BACTERIA = " Bacteria";
	
	public static final String SCOPE_KINGDOM_ARCHAEA = " Archaea";
	
	public static final String SCOPE_KINGDOM_EUKARYOTA = " Eukaryota";
	
	public static final String SCOPE_KINGDOM_VIRUSES = " Viruses";
	
	public static final String SCOPE_KINGDOM_BACTERIAOPHAGE = " Bacteriophage";
	
	public static final String SCOPE_KINGDOM_PLASTID = " Plastid";
	
	public static final String SCOPE_KINGDOM_MITOCHONDRION = " Mitochondrion";
	
	public static final String SCOPE_EXCEPT_START = "  except ";
	
	public static final String SCOPE_NOT_IN_START = "  not in ";
	
	public static final String SCOPE_NOT_IN_COMPLETE_PROTEOME_SEPARATOR = ", ";
	
	/* History section */
	public static final String HISTORY_FIRST_CREATE = "**ZA ";
	
	public static final String HISTORY_LAST_MODIFIED = "**ZB ";

	
	/* Case statement */
	public static final String CASE_START = "case";
	
	public static final String ELSE_CASE_START = "else case";
	
	public static final String ELSE_START = "else";
	
	public static final String END_CASE= "end case";
	
	public static final String CASE_CONDITION_FEATURE = "Feature:";
	
	public static final String CASE_CONDITION_FTGROUP = "FTGroup:";
	
	public static final String CASE_CONDITION_OC = "OC:";
	
	public static final String CASE_CONDITION_OS = "OS:";
	
	public static final String CASE_CONDITION_OG = "OG:";
	
	public static final String OPERATOR_NOT = "not";
	
	public static final String OPERATOR_AND = "and";
	
	public static final String OPERATOR_OR = "or";
	
	public static final String OPERATOR_DEFINED = "defined";
	
	public static final String LEFT_PARENTHESIS = "(";
	
	public static final String RIGHT_PARENTHESIS = ")";
	
	public static final String LEFT_ANGLE_BRAKET = "<";
	
	public static final String RIGHT_ANGLE_BRAKET = ">";
	
	public static final String FEATURE_CONDITION = "Feature:";
	
	public static final String FTGROUP_CONDITION = "FTGroup:";
	
	public static final String FEATURE_CONSTRAINT_CONDITION = "Condition: ";
	
	public static final String FEATURE_CONSTRAIN_GROUP = "Group: ";

	
	/* Sections */
	public static final String HEADER_SECTION = "HeaderSection";
	
	public static final String ANNOTATION_SECTION = "AnnotationSection";
	
	public static final String COMPUTING_SECTION = "ComputingSection";
	
	public static final String HISTORY_SECTION = "HistorySection";

	public static final String DR_LINE_SEPARATOR = "; ";

	public static final String DR_LINE_LEVEL = "level=";
	
	
	public static final String getBlankSpaces(int n) {
		String spaces = "";
		for(int i = 0; i < n; i++) {
			spaces += " ";
		}
		return spaces;
	}
}  
