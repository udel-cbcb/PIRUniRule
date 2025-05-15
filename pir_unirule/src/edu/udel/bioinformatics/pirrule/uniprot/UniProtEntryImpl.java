package edu.udel.bioinformatics.pirrule.uniprot;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirrule.uniprot.model.EntryAccessionNumber;
import org.proteininformationresource.pirrule.uniprot.model.EntryComment;
import org.proteininformationresource.pirrule.uniprot.model.EntryDatabaseCrossReference;
import org.proteininformationresource.pirrule.uniprot.model.EntryDatabaseType;
import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirrule.uniprot.model.EntryIdentifier;
import org.proteininformationresource.pirrule.uniprot.model.EntryKeyword;
import org.proteininformationresource.pirrule.uniprot.model.EntryLine;
import org.proteininformationresource.pirrule.uniprot.model.EntryOrganelle;
import org.proteininformationresource.pirrule.uniprot.model.EntryOrganismClassification;
import org.proteininformationresource.pirrule.uniprot.model.EntryOrganismSpecies;
import org.proteininformationresource.pirrule.uniprot.model.EntrySequence;
import org.proteininformationresource.pirrule.uniprot.model.EntryType;
import org.proteininformationresource.pirrule.uniprot.model.UniProtEntry;

import edu.udel.bioinformatics.pirrule.uniprot.io.UniProtFlatFileConstants;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 29, 2014<br>
 * <br>
 * 
 * An UniProt entry parsed from .dat file, only contains information useful for 
 * PIR site rule propagation.
 */
public class UniProtEntryImpl implements UniProtEntry {

	List<EntryLine> entryLines;

	
	public UniProtEntryImpl() {
		super();
		entryLines = new ArrayList<EntryLine>();
	}

	@Override
	public List<EntryLine> getEntryLines() {
		return entryLines;
	}

	@Override
	public List<EntryComment> getComments() {
		List<EntryComment> commentList = new ArrayList<EntryComment>();
		for(EntryLine entryLine: entryLines) {
			if(entryLine instanceof EntryComment) {
				commentList.add((EntryComment)entryLine);
			}
		}
		return commentList;
	}

	@Override
	public List<EntryDatabaseCrossReference> getDatabaseCrossReferences() {
		List<EntryDatabaseCrossReference> databaseCrossReferenceList = new ArrayList<EntryDatabaseCrossReference>();
		for(EntryLine entryLine: entryLines) {
			if(entryLine instanceof EntryDatabaseCrossReference) {
				databaseCrossReferenceList.add((EntryDatabaseCrossReference)entryLine);
			}
		}
		return databaseCrossReferenceList;
	}

	@Override
	public List<EntryFeatureTable> getFeatures() {
		List<EntryFeatureTable> featureTableList = new ArrayList<EntryFeatureTable>();
		for(EntryLine entryLine: entryLines) {
			if(entryLine instanceof EntryFeatureTable) {
				featureTableList.add((EntryFeatureTable)entryLine);
			}
		}
		return featureTableList;
	}

	@Override
	public List<EntryKeyword> getKeywords() {
		List<EntryKeyword> keywordList = new ArrayList<EntryKeyword>();
		for(EntryLine entryLine: entryLines) {
			if(entryLine instanceof EntryKeyword) {
				keywordList.add((EntryKeyword)entryLine);
			}
		}
		return keywordList;
	}

	@Override
	public EntryOrganismClassification getOrganismClassification() {
		EntryOrganismClassification organismClassification = null;
		for(EntryLine entryLine: entryLines) {
			if(entryLine instanceof EntryOrganismClassification) {
				organismClassification = (EntryOrganismClassification)entryLine;
			}
		}
			
		return organismClassification;
	}

	@Override
	public String getPrimiaryAccessionNumber() {
		String primiaryAccessionNumber = null;
		for(EntryLine entryLine: entryLines) {
			if(entryLine instanceof EntryAccessionNumber) {
				primiaryAccessionNumber = ((EntryAccessionNumber)entryLine).getPrimaryAccessionNumber();
			}
		}
		return primiaryAccessionNumber;
	}

	@Override
	public List<String> getSecondaryAccessionNumbers() {
		List<String> secondaryAccessionNumberList = null;
		for(EntryLine entryLine: entryLines) {
			if(entryLine instanceof EntryAccessionNumber) {
				secondaryAccessionNumberList = ((EntryAccessionNumber)entryLine).getSecondaryAccessionNumbers();
			}
		}
		return secondaryAccessionNumberList;
	}

	@Override
	public String getEntryId() {
		String entryIdentifier = null;
		for(EntryLine entryLine : entryLines) {
			if(entryLine instanceof EntryIdentifier) {
				entryIdentifier = ((EntryIdentifier)entryLine).getEntryName();
			}
		}
		return entryIdentifier;
	}

	@Override
	public EntrySequence getSequence() {
		EntrySequence entrySequence = null;
		for(EntryLine entryLine: entryLines) {
			if(entryLine instanceof EntrySequence) {
				entrySequence = ((EntrySequence)entryLine);
			}
		}
		return entrySequence;
	}

	@Override
	public void addEntryLine(EntryLine entryLine) {
		this.entryLines.add(entryLine);
	}

	@Override
	public void setComments(List<EntryComment> comments) {
		this.entryLines.addAll(comments);
	}

	@Override
	public void setDatabaseCrossReference(List<EntryDatabaseCrossReference> databaseCrossReferences) {
		this.entryLines.addAll(databaseCrossReferences);
	}

	@Override
	public void setFeatures(List<EntryFeatureTable> features) {
		this.entryLines.addAll(features);
	}

	@Override
	public void setKeywords(List<EntryKeyword> keywords) {
		this.entryLines.addAll(keywords);
	}

	@Override
	public void setOrganismClassifcation(EntryOrganismClassification organismClassification) {
		this.entryLines.add(organismClassification);
	}

	@Override
	public void setAccessionNumber(EntryAccessionNumber entryAccessionNumber) {
		this.entryLines.add(entryAccessionNumber);
	}

	@Override
	public void setIdentifier(EntryIdentifier entryIdentifier) {
		this.entryLines.add(entryIdentifier);
	}

	@Override
	public void setSequece(EntrySequence entrySequence) {
		this.entryLines.add(entrySequence);
	}

	public String toString() {
		String entry = "";
		for(EntryLine line: entryLines) {
			entry += line.toString()+"\n";
		}
		entry += UniProtFlatFileConstants.SEPARATOR;
		return entry;
	}

	@Override
	public EntryOrganismSpecies getOrganismSpecies() {
		EntryOrganismSpecies organismSpecies = null;
		for(EntryLine entryLine: entryLines) {
			if(entryLine instanceof EntryOrganismSpecies) {
				organismSpecies = (EntryOrganismSpecies)entryLine;
			}
		}
		return organismSpecies;
	}

	@Override
	public void setOrganismSpecies(EntryOrganismSpecies organismSpecies) {
		this.entryLines.add(organismSpecies);
	}

	@Override
	public EntryOrganelle getOrganelle() {
		EntryOrganelle organelle = null;
		for(EntryLine entryLine: entryLines) {
			if(entryLine instanceof EntryOrganelle) {
				organelle = (EntryOrganelle)entryLine;
			}
		}
		return organelle;
	}

	@Override
	public void setOrganelle(EntryOrganelle organelle) {
		this.entryLines.add(organelle);
	}

	@Override
	public List<EntryDatabaseCrossReference> getDatabaseCrossReferences(EntryDatabaseType databaseType) {
		List<EntryDatabaseCrossReference> databaseCrossReferences = new ArrayList<EntryDatabaseCrossReference>();
		for(EntryLine entryLine: entryLines) {
			if(entryLine instanceof EntryDatabaseCrossReference) {
				EntryDatabaseCrossReference databaseCrossReference = (EntryDatabaseCrossReference)entryLine;
				if(databaseCrossReference.getDatabseType().equals(databaseType)) {
					databaseCrossReferences.add(databaseCrossReference);
				}
			}
		}
		if(databaseCrossReferences.size() > 0) {
			return databaseCrossReferences;
		}
		else {
			return null;
		}
	}

	@Override
	public EntryType getEntryType() {
		EntryType entryType = null;
		for(EntryLine entryLine : entryLines) {
			if(entryLine instanceof EntryIdentifier) {
				entryType = ((EntryIdentifier)entryLine).getEntryType();
			}
		}
		return entryType;
	}
}
