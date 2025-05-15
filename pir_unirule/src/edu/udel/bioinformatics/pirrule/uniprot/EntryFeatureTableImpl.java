package edu.udel.bioinformatics.pirrule.uniprot;


import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureType;

import edu.udel.bioinformatics.pirrule.uniprot.io.UniProtFlatFileConstants;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * The FT (Feature Table) lines provide a precise but simple means for the
 * annotation of the sequence data. The table describes regions or sites of
 * interest in the sequence. In general the feature table lists
 * posttranslational modifications, binding sites, enzyme active sites, local
 * secondary structure or other characteristics reported in the cited
 * references. Sequence conflicts between references are also included in the
 * feature table.
 */
public class EntryFeatureTableImpl implements EntryFeatureTable {

	private EntryFeatureType featureType;
	private String fromEndPoint;
	private String toEndPoint;
	private String description;
	private String featureIdentifier;
	private List<String> featureDescriptionList;
	
	
	public EntryFeatureTableImpl(EntryFeatureType featureType, String fromEndPoint, String toEndPoint) {
		super();
		this.featureType = featureType;
		this.fromEndPoint = fromEndPoint;
		this.toEndPoint = toEndPoint;
	}

	@Override
	public EntryFeatureType getFeatureType() {
		return this.featureType;
	}

	@Override
	public String getFromEndPoint() {
//		if(this.fromEndPoint.equals("Nter")) {
//			return this.toEndPoint;
//		}
		return this.fromEndPoint;
	}

	@Override
	public String getToEndPoint() {
//		if(this.toEndPoint.equals("Cter")) {
//			return this.fromEndPoint;
//		}
		return this.toEndPoint;
	}

	@Override
	public String getFeatureDescription() {
		return this.description;
	}

	@Override
	public String getFeatureIdentifier() {
		return this.featureIdentifier;
	}

	@Override
	public void setFeatureIdentifier(String featureIdentifier) {
		this.featureIdentifier = featureIdentifier;
	}

	@Override
	public void setFeatureDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		String str = "";
		int featureKeyLength = this.featureType.name().length();
		int featureFromPositionLength = this.fromEndPoint.length();
		int featureToPositionLength = this.toEndPoint.length();
		int featureKeyRightPadding = 15 - featureKeyLength - featureFromPositionLength;
		
		int featureFromPositionRightPadding = 7 - featureToPositionLength;

		int featureToPositionRightPadding = 8;

		str += UniProtFlatFileConstants.FT_LINE_START + this.featureType + UniProtFlatFileConstants.getBlankSpaces(featureKeyRightPadding);
		str += this.fromEndPoint + UniProtFlatFileConstants.getBlankSpaces(featureFromPositionRightPadding);
		str += this.toEndPoint + UniProtFlatFileConstants.getBlankSpaces(featureToPositionRightPadding);
		//System.out.println(this.description+"??");
		str += EntryUtil.cleanup(this.description);
		//System.out.println(EntryUtil.cleanup(this.description)+"!!!");
		return str;
	}
	
	public String toReport() {
		String str = "";
		int featureKeyLength = this.featureType.name().length();
		int featureFromPositionLength = this.fromEndPoint.length();
		int featureToPositionLength = this.toEndPoint.length();
		int featureKeyRightPadding = 15 - featureKeyLength - featureFromPositionLength;
		
		int featureFromPositionRightPadding = 7 - featureToPositionLength;

		int featureToPositionRightPadding = 8;

		str += UniProtFlatFileConstants.FT_LINE_START + this.featureType + UniProtFlatFileConstants.getBlankSpaces(featureKeyRightPadding);
		str += this.fromEndPoint + UniProtFlatFileConstants.getBlankSpaces(featureFromPositionRightPadding);
		str += this.toEndPoint + UniProtFlatFileConstants.getBlankSpaces(featureToPositionRightPadding);
		
		//str += EntryUtil.cleanup(this.description);
		str += WordUtils.wrap(EntryUtil.cleanup(this.description.trim()), 55, "\n"+UniProtFlatFileConstants.FT_DESCRIPTION_START, false);
		
		return str;
	}

	@Override
	public void setFromEndPoint(String fromEndPoint) {
		this.fromEndPoint = fromEndPoint;
	}

	@Override
	public void setToEndPoint(String toEndPoint) {
		this.toEndPoint = toEndPoint;
	}

//	@Override
//	public List<String> getFeatureDescriptionList() {
//		return this.featureDescriptionList;
//	}

//	private String cleanup(String description) {
//		//System.out.println(description);
//		String cleanDescription = description;
//		cleanDescription = cleanDescription.replaceAll(" Evidence=\\{ECO:.*?\\};", "");
//		cleanDescription = cleanDescription.replaceAll(" \\{ECO:.*?\\}\\.", "");
//		cleanDescription = cleanDescription.replaceAll("\\{ECO:.*?\\}", "");
//		cleanDescription = cleanDescription.replaceAll("\\.$", "");
//		
//		cleanDescription = cleanDescription.replaceAll(" \\(By similarity\\)", "");
//		cleanDescription = cleanDescription.replaceAll(" \\(Potential\\)", "");
//		cleanDescription = cleanDescription.replaceAll(" \\(Experimental\\)", "");
//		//System.out.println(cleanDescription);
//		return cleanDescription.trim();
//	}
}
