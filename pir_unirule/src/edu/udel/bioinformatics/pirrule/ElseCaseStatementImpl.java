package edu.udel.bioinformatics.pirrule;

import org.proteininformationresource.pirrule.io.uniruleflatfile.UniRuleFlatFileConstants;
import org.proteininformationresource.pirrule.model.ElseCaseStatement;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 3, 2014<br>
 * <br>
 * 
 * else case <condition>[ and|or [not] [defined] <condition>]...
 */
public class ElseCaseStatementImpl extends ControlStatementImpl implements ElseCaseStatement {

	public String toString() {
		if (this.getExpression() != null) {
			return UniRuleFlatFileConstants.ELSE_CASE_START + " " + this.getExpression().toString() + "\n";
		} else {
			return UniRuleFlatFileConstants.ELSE_CASE_START + "\n";
		}
	}
}
