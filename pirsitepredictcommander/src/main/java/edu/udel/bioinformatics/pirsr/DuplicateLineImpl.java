package edu.udel.bioinformatics.pirsr;

import java.util.List;

import org.proteininformationresource.pirsr.model.DuplicateLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 12, 2014<br>
 * <br>
 * 
 */
public class DuplicateLineImpl implements DuplicateLine {

	List<String> organisms = null;

	public DuplicateLineImpl() {
		super();
	}

	public DuplicateLineImpl(List<String> organisms) {
		this.organisms = organisms;
	}

	
	public List<String> getOrganisms() {
		return this.organisms;
	}

	
	public void setOrganisms(List<String> organisms) {
		this.organisms = organisms;
	}

	public String toString() {
		String str = "Duplicate: ";
		if (this.organisms != null && this.organisms.size() > 0) {
			str += "in ";
			for (int i = 0; i < this.organisms.size(); i++) {
				if (i == 0) {
					str += this.organisms.get(i);
				} else {
					str += ", " + this.organisms.get(i);
				}
			}
		}
		if (str.equals("Duplicate: ")) {
			str += "None";
		}
		return str + "\n";
	}
}
