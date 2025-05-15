package edu.udel.bioinformatics.pirrule.uniprot;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirrule.uniprot.model.EntryAccessionNumber;
import org.proteininformationresource.pirrule.uniprot.model.EntryComment;
import org.proteininformationresource.pirrule.uniprot.model.EntryCommentType;
import org.proteininformationresource.pirrule.uniprot.model.EntryDataFactory;
import org.proteininformationresource.pirrule.uniprot.model.EntryDatabaseCrossReference;
import org.proteininformationresource.pirrule.uniprot.model.EntryDatabaseType;
import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureType;
import org.proteininformationresource.pirrule.uniprot.model.EntryIdentifier;
import org.proteininformationresource.pirrule.uniprot.model.EntryKeyword;
import org.proteininformationresource.pirrule.uniprot.model.EntryOrganelle;
import org.proteininformationresource.pirrule.uniprot.model.EntryOrganismClassification;
import org.proteininformationresource.pirrule.uniprot.model.EntryOrganismSpecies;
import org.proteininformationresource.pirrule.uniprot.model.EntrySequence;
import org.proteininformationresource.pirrule.uniprot.model.EntryType;
import org.proteininformationresource.pirrule.uniprot.model.UniProtEntry;

import edu.udel.bioinformatics.pirrule.uniprot.io.UniProtFlatFileParserLog;

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

	@Override
	public UniProtEntry buildUniProtEntry() {
		return new UniProtEntryImpl();
	}

	@Override
	public EntrySequence buildEntrySequence(String crc64, int length, int molecularWeight) {
		return new EntrySequenceImpl(crc64, length, molecularWeight);
	}

	@Override
	public EntryOrganismClassification buildEntryOrganismClassification(List<String> organismClassification) {
		return new EntryOrganismClassificationImpl(organismClassification);
	}

	@Override
	public EntryKeyword buildEntryKeyword(String keyword) {
		return new EntryKeywordImpl(keyword);
	}

	@Override
	public EntryIdentifier buildEntryIdentifier(String entryName, EntryType entryType, int sequenceLength) {
		return new EntryIdentifierImpl(entryName, entryType, sequenceLength);
	}

	@Override
	public EntryFeatureTable buildEntryFeatureTable(EntryFeatureType featureType, String fromEndPoint, String toEndPoint) {
		return new EntryFeatureTableImpl(featureType, fromEndPoint, toEndPoint);
	}

	@Override
	public EntryDatabaseCrossReference buildEntryDatabaseCrossReference(EntryDatabaseType databaseType, String identifier, String optionalInfo1,
			String optionalInfo2, String optionalInfo3) {
		return new EntryDatabaseCrossReferenceImpl(databaseType, identifier, optionalInfo1, optionalInfo2, optionalInfo3);
	}

	@Override
	public EntryComment buildEntryComment(EntryCommentType commentType, List<String> commentDescriptionList) {
		return new EntryCommentImpl(commentType, commentDescriptionList);
	}

	@Override
	public EntryAccessionNumber buildEntryAccessionNumber(List<String> accessionNumberList) {
		return new EntryAccessionNumberImpl(accessionNumberList);
	}

	@Override
	public UniProtFlatFileParserLog buildUniProtFlatFileParserLog(String entryId, ArrayList<String> messages) {
		return this.buildUniProtFlatFileParserLog(entryId, messages);
	}

	@Override
	public EntryOrganelle buildEntryOrganelle(String organelle) {
		return new EntryOrganelleImpl(organelle);
	}

	@Override
	public EntryOrganismSpecies buildEntryOrganismSpecies(String organismSpecies) {
		return new EntryOrganismSpeciesImpl(organismSpecies);
	}

	public static EntryDataFactory getInstance() {
		return new EntryDataFactoryImpl();
	}
}
