package edu.udel.bioinformatics.pirrule.uniprot.io;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: Oct 29, 2014<br>
 * <br>
 * 
 */
public class UniProtFlatFileConstants {
	public static final String ID_LINE_START = "ID   ";
	
	public static final String AC_LINE_START = "AC   ";
	
	public static final String OC_LINE_START = "OC   ";
	
	public static final String KW_LINE_START = "KW   ";
	
	public static final String CC_LINE_START = "CC   ";
	
	public static final String CC_LINE_TOPIC_START = "CC   -!- ";
	
	public static final String CC_LINE_DESCRIPTION_START = "CC       ";
	
	public static final String FT_LINE_START = "FT   ";
	
	public static final String FT_ID_START = "FT                                /FTId=";
	
	public static final String FT_DESCRIPTION_START = "FT                                ";
	
	public static final String DR_LINE_START = "DR   ";
	
	public static final String SQ_LINE_START = "SQ   SEQUENCE   ";
	
	public static final String SD_LINE_START = "     ";
	
	public static final String SEPARATOR = "//";
	
	public static final String SP_STATUS = "Reviewed";
	
	public static final String TR_STATUS = "Unreviewed";
	
	public static final String OG_LINE_START = "OG   ";
	
	public static final String OS_LINE_START = "OS   ";
	
	public static final String getBlankSpaces(int n) {
		String spaces = "";
		for(int i = 0; i < n; i++) {
			spaces += " ";
		}
		return spaces;
	}
}
