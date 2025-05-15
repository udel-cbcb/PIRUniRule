package edu.udel.bioinformatics.pirsr;

import java.util.List;

import org.proteininformationresource.pirsr.model.CCLine;
import org.proteininformationresource.pirsr.model.RuleCommentType;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * The CC line(s) contains all applicable comment lines of a
 * UniProtKB/Swiss-Prot entry.
 */
public class CCLineImpl implements CCLine {

	private RuleCommentType topic;
	private List<String> descriptionList;

	public CCLineImpl() {
		super();
	}

	public CCLineImpl(RuleCommentType topic, List<String> descriptionList) {
		super();
		this.topic = topic;
		this.descriptionList = descriptionList;
	}

	
	public RuleCommentType getTopic() {
		return this.topic;
	}

	
	public List<String> getDescription() {
		return this.descriptionList;
	}

	
	public void setDescription(List<String> descriptionList) {
		this.descriptionList = descriptionList;
	}

	public String toString() {
		String str = "CC   -!- " + this.topic.getTopic() + ": " + this.descriptionList.get(0);
		for (int i = 1; i < this.descriptionList.size(); i++) {
			str += "\nCC       " + this.descriptionList.get(i);
		}
		if(!(str.endsWith(".") || str.endsWith(";"))) {
			//System.out.println(str+"|");
			str += ".";
		}
		return str + "\n";
	}
	
	public String toReport() {
		String str = "CC   -!- " + this.topic.getTopic() + ": " + this.descriptionList.get(0);
		for (int i = 1; i < this.descriptionList.size(); i++) {
			str += "\nCC       " + this.descriptionList.get(i);
		}
//		if(!(str.endsWith(".") || str.endsWith(";"))) {
//			//System.out.println(str+"|");
//			str += ".";
//		}
		return str + "\n";
	}

	
	public void setTopic(RuleCommentType topic) {
		this.topic = topic;
	}

	public String getCommentDescription() {
		String record = "";
//		System.out.println("??");
//		System.out.println(this.descriptionList);
		// for(String desc: this.descriptionList) {
		for (int i = 0; i < this.descriptionList.size(); i++) {
			if (i == 0) {
				record += this.descriptionList.get(i).trim();
			} else {
				if(record.endsWith("-")) {
					record += this.descriptionList.get(i).trim();
				}
				else {
					record += " " + this.descriptionList.get(i).trim();
				}
			}
		}
		// }
		//System.out.println(record);

		return record.trim();
	}
}
