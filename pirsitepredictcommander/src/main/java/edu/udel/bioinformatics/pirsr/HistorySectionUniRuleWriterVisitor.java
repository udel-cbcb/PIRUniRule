package edu.udel.bioinformatics.pirsr;

import java.util.List;

import org.proteininformationresource.pirsr.model.HistoryInfo;
import org.proteininformationresource.pirsr.model.HistorySectionVisitor;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 */
public class HistorySectionUniRuleWriterVisitor implements
		HistorySectionVisitor {

	private String historySection = "";

	public String getHistorySection() {
		return this.historySection;
	}

	
	public void visit(List<HistoryInfo> historyInfos) {
		if (historyInfos != null) {
			for (HistoryInfo historyInfo : historyInfos) {
				String curator = historyInfo.getCurator();
				String dateTime = historyInfo.getDateTime();
				String versionNumber = historyInfo.getVersionNumber();
				if (historyInfo.getType().equals("creation")) {
					this.historySection += "**ZA "
							+ ((curator != null) ? curator : "");
				}
				if (historyInfo.getType().equals("lastModification")) {
					this.historySection += "**ZB "
							+ ((curator != null) ? curator : "");
				}
				this.historySection += " "+((dateTime != null) ? dateTime : "");
				if(versionNumber != null) {
					this.historySection += " "+versionNumber;
				}
//				this.historySection += " "+((versionNumber != null) ? versionNumber
//						: "");
				this.historySection += "\n";
			}
		}
	}

}
