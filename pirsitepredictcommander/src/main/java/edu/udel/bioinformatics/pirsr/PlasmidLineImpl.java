package edu.udel.bioinformatics.pirsr;

import java.util.List;

import org.proteininformationresource.pirsr.model.PlasmidLine;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * 
 */
public class PlasmidLineImpl implements PlasmidLine{
	List<String> organisms = null;
	
	public PlasmidLineImpl() {
		super();
	}

	public PlasmidLineImpl(List<String> organisms) {
		this.organisms = organisms;
	}
	
	
	public List<String> getOrganisms() {
		return this.organisms;
	}

	
	public void setOrganisms(List<String> organisms) {
		this.organisms = organisms;
	}

	public String toString() {
		String str = "Plasmid: ";
		if(this.organisms != null && this.organisms.size() > 0) {
			str += "in ";
			for(int i = 0; i < this.organisms.size(); i++) {
				if(i == 0) {
					str += this.organisms.get(i);
				}
				else {
					str += ", " + this.organisms.get(i);
				}
			}
		}
		if(str.equals("Plasmid: ")) {
			str += "None";
		}
		return str+"\n";
	}
}
