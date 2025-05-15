package edu.udel.bioinformatics.pirsr;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.model.HistoryInfo;
import org.proteininformationresource.pirsr.model.HistorySection;
import org.proteininformationresource.pirsr.model.HistorySectionVisitor;
import org.proteininformationresource.pirsr.model.Line;


/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br><br>
 * 
 * The History section indicates the version number, date/time and author of the creation/modification.
 * 
 */
public class HistorySectionImpl implements HistorySection {
	private List<HistoryInfo> historyInfos = null;
	private boolean isComplete = false;
	
	public HistorySectionImpl() {
		super();
	}
	
	

	public HistorySectionImpl(List<HistoryInfo> historyInfos) {
		super();
		this.historyInfos = historyInfos;
	}

	
	public void setHistoryInfos(List<HistoryInfo> historyInfos) {
		this.historyInfos = historyInfos;
	}



	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}



	
	public List<HistoryInfo> getHistoryInfos() {
		
		return this.historyInfos;
	}

	
	public void accept(HistorySectionVisitor visitor) {
		visitor.visit(historyInfos);
	}

	//
	public boolean isComplete() {
		return this.isComplete;
	}



	
	public List<Line> getLines() {
		List<Line> lines = new ArrayList<Line>();
		for(HistoryInfo historyInfo: this.historyInfos) {
			lines.add(historyInfo);
		}
		return lines;
	}



	
	public void setLines(List<Line> lines) {
		for(Line line: lines) {
			this.historyInfos.add((HistoryInfo)line);
		}
	}



	
	public Line getLastLine() {
		if(this.historyInfos != null && this.historyInfos.size() >0) {
			return this.historyInfos.get(this.historyInfos.size() -1 );
		}
		return null;
	}



	
	public void setLastLine(Line lastLine) {
		this.historyInfos.set(this.historyInfos.size() -1, (HistoryInfo) lastLine);
	}



	
	public List<Line> getNonControlBlockLines() {
		List<Line> lines = new ArrayList<Line>();
		for(HistoryInfo historyInfo: this.historyInfos) {
			lines.add(historyInfo);
		}
		return lines;
	}



	
	public void setNonControlBlockLines(List<Line> nonControlBlockLines) {
		for(Line line: nonControlBlockLines) {
			this.historyInfos.add((HistoryInfo)line);
		}
	}



	
	public Line getLastNonControlBlockLine() {
		if(this.historyInfos != null && this.historyInfos.size() >0) {
			return this.historyInfos.get(this.historyInfos.size() -1 );
		}
		return null;
	}



	
	public void setLastNonControlBlockLine(Line lastNonControlBlockLine) {
		this.historyInfos.set(this.historyInfos.size() -1, (HistoryInfo) lastNonControlBlockLine);
	}



	
	public String toString() {
		String str = "";
		if (historyInfos != null) {
			for (HistoryInfo historyInfo : historyInfos) {
				String curator = historyInfo.getCurator();
				String dateTime = historyInfo.getDateTime();
				String versionNumber = historyInfo.getVersionNumber();
				if (historyInfo.getType().equals("creation")) {
					str += "**ZA "
							+ ((curator != null) ? curator : "");
				}
				if (historyInfo.getType().equals("lastModification")) {
					str += "**ZB "
							+ ((curator != null) ? curator : "");
				}
				str += " "+((dateTime != null) ? dateTime : "");
				if(versionNumber != null) {
					str += " "+versionNumber;
				}
//				this.historySection += " "+((versionNumber != null) ? versionNumber
//						: "");
				str += "\n";
			}
		}
		return str;
	}


	
	

}
