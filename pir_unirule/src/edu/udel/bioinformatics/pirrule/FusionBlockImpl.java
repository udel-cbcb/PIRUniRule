package edu.udel.bioinformatics.pirrule;

import java.util.List;

import org.proteininformationresource.pirrule.model.FusionBlock;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 *
 */
public class FusionBlockImpl implements FusionBlock{

	private List<String> ntList;
	private List<String> ctList;
	
	public FusionBlockImpl() {
		super();
	}

	@Override
	public List<String> getNTList() {
		return this.ntList;
	}

	@Override
	public List<String> getCTList() {
		return this.ctList;
	}

	@Override
	public void setNTList(List<String> ntList) {
		this.ntList = ntList;
	}

	@Override
	public void setCTList(List<String> ctList) {
		this.ctList = ctList;
	}
	
	public String toString() {
		String str = "Fusion:\n";
		if(this.ntList != null && this.ntList.size() > 0) {
			str += " Nter: ";
			for(int i = 0; i < this.ntList.size(); i++) {
				if(i == 0) {
					str += this.ntList.get(i);
				}
				else {
					str += "; "+this.ntList.get(i);
				}
			}
			str +="\n";
		}
		else {
			str += " Nter: None\n";
		}
		if(this.ctList != null && this.ctList.size() > 0) {
			str += " Cter: ";
			for(int i = 0; i < this.ctList.size(); i++) {
				if(i == 0) {
					str += this.ctList.get(i);
				}
				else {
					str += "; "+this.ctList.get(i);
				}
			}
		}
		else {
			str += " Cter: None\n";
		}
		return str;
	}

}
