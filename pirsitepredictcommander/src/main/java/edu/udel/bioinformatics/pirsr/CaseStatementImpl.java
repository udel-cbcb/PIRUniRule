package edu.udel.bioinformatics.pirsr;

import org.proteininformationresource.pirsr.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirsr.model.CaseStatement;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * case <condition>[ and|or [not] [defined] <condition>]...
 */
public class CaseStatementImpl extends ControlStatementImpl implements CaseStatement {

	public String toString() {
		return UniRuleFlatFileConstants.CASE_START + " " + this.getExpression().toString() + "\n";
	}

}
