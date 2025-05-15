package edu.udel.bioinformatics.pirrule;

import org.proteininformationresource.pirrule.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirrule.model.FTFromLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * The FT From line is mandatory above the FT feature line. It defines the
 * "template" / "frame of reference" to allow the rule specified coordinates to
 * be mapped into the target sequence coordinates, for all subsequent FT feature
 * lines. The template must be a protein identifier.
 */
public class FTFromLineImpl implements FTFromLine {

	private String ftTemplateAccessionNumber;

	public FTFromLineImpl() {
		super();
	}

	public FTFromLineImpl(String ftTemplateAccessionNumber) {
		super();
		this.ftTemplateAccessionNumber = ftTemplateAccessionNumber;
	}

	@Override
	public String getFTTemplateAccessionNumber() {
		return this.ftTemplateAccessionNumber;
	}

	@Override
	public void setFTTemplateAccessionNumber(String ftTemplateAccessionNumber) {
		this.ftTemplateAccessionNumber = ftTemplateAccessionNumber;
	}

	public String toString() {
		return UniRuleFlatFileConstants.FT_FROM_LINE_START + this.ftTemplateAccessionNumber + "\n";
	}

}
