package edu.udel.bioinformatics.pirsr.uniprot;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.uniprot.model.EntryAccessionNumber;
import org.proteininformationresource.pirsr.uniprot.model.EntryComment;
import org.proteininformationresource.pirsr.uniprot.model.EntryCommentType;
import org.proteininformationresource.pirsr.uniprot.model.EntryDataFactory;
import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseCrossReference;
import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseType;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureType;
import org.proteininformationresource.pirsr.uniprot.model.EntryIdentifier;
import org.proteininformationresource.pirsr.uniprot.model.EntryKeyword;
import org.proteininformationresource.pirsr.uniprot.model.EntryOrganelle;
import org.proteininformationresource.pirsr.uniprot.model.EntryOrganismClassification;
import org.proteininformationresource.pirsr.uniprot.model.EntryOrganismSpecies;
import org.proteininformationresource.pirsr.uniprot.model.EntrySequence;
import org.proteininformationresource.pirsr.uniprot.model.EntryType;
import org.proteininformationresource.pirsr.uniprot.model.UniProtEntry;

import edu.udel.bioinformatics.pirsr.uniprot.io.UniProtFlatFileParserLog;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * The data factory pattern used to build different kinds of objects related to UniProt
 * entry.
 */
public class EntryDataFactoryImpl implements EntryDataFactory {

	
	public UniProtEntry buildUniProtEntry() {
		return new UniProtEntryImpl();
	}

	
	public EntrySequence buildEntrySequence(String crc64, int length, int molecularWeight) {
		return new EntrySequenceImpl(crc64, length, molecularWeight);
	}

	
	public EntryOrganismClassification buildEntryOrganismClassification(List<String> organismClassification) {
		return new EntryOrganismClassificationImpl(organismClassification);
	}

	
	public EntryKeyword buildEntryKeyword(String keyword) {
		return new EntryKeywordImpl(keyword);
	}

	
	public EntryIdentifier buildEntryIdentifier(String entryName, EntryType entryType, int sequenceLength) {
		return new EntryIdentifierImpl(entryName, entryType, sequenceLength);
	}

	
	public EntryFeatureTable buildEntryFeatureTable(EntryFeatureType featureType, String fromEndPoint, String toEndPoint) {
		return new EntryFeatureTableImpl(featureType, fromEndPoint, toEndPoint);
	}

	
	public EntryDatabaseCrossReference buildEntryDatabaseCrossReference(EntryDatabaseType databaseType, String identifier, String optionalInfo1,
			String optionalInfo2, String optionalInfo3) {
		return new EntryDatabaseCrossReferenceImpl(databaseType, identifier, optionalInfo1, optionalInfo2, optionalInfo3);
	}

	
	public EntryComment buildEntryComment(EntryCommentType commentType, List<String> commentDescriptionList) {
		return new EntryCommentImpl(commentType, commentDescriptionList);
	}

	
	public EntryAccessionNumber buildEntryAccessionNumber(List<String> accessionNumberList) {
		return new EntryAccessionNumberImpl(accessionNumberList);
	}

	
	public UniProtFlatFileParserLog buildUniProtFlatFileParserLog(String entryId, ArrayList<String> messages) {
		return this.buildUniProtFlatFileParserLog(entryId, messages);
	}

	
	public EntryOrganelle buildEntryOrganelle(String organelle) {
		return new EntryOrganelleImpl(organelle);
	}

	
	public EntryOrganismSpecies buildEntryOrganismSpecies(String organismSpecies) {
		return new EntryOrganismSpeciesImpl(organismSpecies);
	}

	public static EntryDataFactory getInstance() {
		return new EntryDataFactoryImpl();
	}
}
