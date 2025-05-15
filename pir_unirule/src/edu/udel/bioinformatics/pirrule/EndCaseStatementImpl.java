package edu.udel.bioinformatics.pirrule;

import org.proteininformationresource.pirrule.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirrule.model.EndCaseStatement;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * 
 */
public class EndCaseStatementImpl extends ControlStatementImpl implements EndCaseStatement {
	public String toString() {
		return UniRuleFlatFileConstants.END_CASE + "\n";
	}
}
