package edu.udel.bioinformatics.pirsr;

import java.util.List;

import org.proteininformationresource.pirsr.model.DELine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 * 
 */
public class DELineImpl implements DELine {
	private String nameType;
	private List<String> fullNames;
	private List<String> shortNames;
	private List<String> ecNumbers;

	public DELineImpl() {
		super();
	}

	
	public String getNameType() {
		return this.nameType;
	}

	
	public void setNameType(String nameType) {
		this.nameType = nameType;
	}

	
	public List<String> getFullNames() {
		return this.fullNames;
	}

	
	public void setFullNames(List<String> fullNames) {
		this.fullNames = fullNames;
	}

	
	public List<String> getShortNames() {
		return this.shortNames;
	}

	
	public void setShortNames(List<String> shortNames) {
		this.shortNames = shortNames;
	}

	
	public List<String> getECs() {
		return this.ecNumbers;
	}

	
	public void setECs(List<String> ecNumbers) {
		this.ecNumbers = ecNumbers;
	}

	public String toString() {
		String str = "";
		boolean hasFullName = false;
		boolean hasShortName = false;
		if (this.fullNames != null && this.fullNames.size() > 0) {
			for (int i = 0; i < this.fullNames.size(); i++) {
				if (i == 0) {
					str += "DE   " + this.nameType + ": Full=" + this.fullNames.get(i) + ";\n";
				} else {
					str += "DE            Full=" + this.fullNames.get(i) + ";\n";
				}
				hasFullName = true;
			}
		}
		if (this.shortNames != null && this.shortNames.size() > 0) {
			for (int i = 0; i < this.shortNames.size(); i++) {
				if (i == 0) {
					if(!hasFullName) {
						str += "DE   " + this.nameType + ": Short=" + this.shortNames.get(i) + ";\n";
					}
					else {
						str += "DE            Short=" + this.shortNames.get(i) + ";\n";
					}
				} else {
					str += "DE            Short=" + this.shortNames.get(i) + ";\n";
				}
				hasShortName = true;
			}
		}

		if (this.ecNumbers != null && this.ecNumbers.size() > 0) {
			for (int i = 0; i < this.ecNumbers.size(); i++) {
				if (i == 0) {
					if(!hasFullName && !hasShortName) {
						str += "DE   " + this.nameType + ": EC=" + this.ecNumbers.get(i) + ";\n";
					}
					else {
						str += "DE            EC=" + this.ecNumbers.get(i) + ";\n";
					}
				} else {
					str += "DE            EC=" + this.ecNumbers.get(i) + ";\n";
				}
			}
		}
		return str;
	}

}
