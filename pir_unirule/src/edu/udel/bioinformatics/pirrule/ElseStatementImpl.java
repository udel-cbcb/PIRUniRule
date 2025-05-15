package edu.udel.bioinformatics.pirrule;

import org.proteininformationresource.pirrule.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirrule.model.ElseStatement;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 */
public class ElseStatementImpl extends ControlStatementImpl implements ElseStatement {

	public String toString() {
		if (this.getExpression() != null) {
			return UniRuleFlatFileConstants.ELSE_START + " " + this.getExpression().toString() + "\n";
		} else {
			return UniRuleFlatFileConstants.ELSE_START + "\n";
		}
	}

}
