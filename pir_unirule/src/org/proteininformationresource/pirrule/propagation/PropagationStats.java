package org.proteininformationresource.pirrule.propagation;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 16, 2014<br>
 * <br>
 * 
 */
public enum PropagationStats {
    /**
     * True Positives (TPs): annotation exists in the SwissProt entry and is predicted by the Rule.
     * True Negatives (TNs): annotation does not exist in the SwissProt entry and is not predicated by the Rule.
     * False Positives (FPs): annotation does not exist in the SwissProt entry, but is predicted by the Rule.
     * False Negatives (FNS): annotation exists in the SwissProt entry, but is not predicated by the Rule
     * 
     */
	TP("TP"),
	TN("TN"),
	FP("FP"),
	FN("FN"),
	NA("NA");
	
	String propagationStats = null;
	PropagationStats(String propagationStats) {
		this.propagationStats = propagationStats;
	}
	
	String getPropagationStats() {
		return this.propagationStats;
	}
}
