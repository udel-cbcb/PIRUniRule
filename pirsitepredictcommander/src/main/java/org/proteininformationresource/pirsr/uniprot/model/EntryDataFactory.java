package org.proteininformationresource.pirsr.uniprot.model;

import java.util.ArrayList;
import java.util.List;

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
public interface EntryDataFactory {
	/**
	 * Build an <code>UniProtEntry</code> object.
	 * @return an <code>UniProtEntry</code> object.
	 */
	UniProtEntry buildUniProtEntry();
	
	/**
	 * Build an <code>EntrySequence</code> object.
	 * @param crc64 the CRC 64 checksum
	 * @param length the length of amino acid sequence.
	 * @param molecularWeight the molecular weight of the protein.
	 * @return an <code>EntrySequence</code> object.
	 */
	EntrySequence buildEntrySequence(String crc64, int length, int molecularWeight);
	
	/**
	 * Build an <code>EntryOrganismClassification</code> object.
	 * @param organismClassification a list of NCBI taxomony for the source organism.
	 * @return @return an <code>EntryOrganismClassification</code> object.
	 */
	EntryOrganismClassification buildEntryOrganismClassification(List<String> organismClassification);
	
	
	/**
	 * Build an <code>EntryKeyword</code> object.
	 * @param keyword the keyword.
	 * @return an <code>EntryKeyword</code> object.
	 */
	EntryKeyword buildEntryKeyword(String keyword);

	
	/**
	 * Build an <code>EntryIdentifier</code> object.
	 * @param entryName the name of the protein entry.
	 * @param entryType the type of the protein entry.
	 * @param sequenceLength the amino acid length the protein sequence.
	 * @return an <code>EntryIdentifier</code> object.
	 */
	EntryIdentifier buildEntryIdentifier(String entryName, EntryType entryType, int sequenceLength);
	
	/**
	 * 
	 * 
	 */
	
	/**
	 * Build an <code>EntryFeatureTable</code> object.
	 * @param featureType the feature type.
	 * @param fromEndPoint the 'from' end point.
	 * @param toEndPoint the 'to' end point.
	 * @return an <code>EntryFeatureTable</code> object.
	 */
	EntryFeatureTable buildEntryFeatureTable(EntryFeatureType featureType, String fromEndPoint, String toEndPoint);
	
	/**
	 * 
	 * 
	 */
	
	/**
	 * Build an <code>EntryDatabaseCrossReference</code> object.
	 * @param databaseType the database cross reference type.
	 * @param identifier the database identifier.
	 * @param optionalInfo1 the optional information 1.
	 * @param optionalInfo2 the optional information 2.
	 * @param optionalInfo3 the optional information 3.
	 * @return an <code>EntryDatabaseCrossReference</code> object.
	 */
	EntryDatabaseCrossReference buildEntryDatabaseCrossReference(EntryDatabaseType databaseType, String identifier, String optionalInfo1, String optionalInfo2,
			String optionalInfo3);
	
	/**
	 * 
	 * 
	 */
	/**
	 * Build an <code>EntryComment</code> object.
	 * @param commentType comment type
	 //* @param commentDescription comment description 
	*  @param commentDescriptionList comment description as list
	 * @return an <code>EntryComment</code> object.
	 */
	EntryComment buildEntryComment(EntryCommentType commentType, List<String> commentDescriptionList);
	
	
	/**
	 * Build an <code>EntryAccessionNumber</code> object.
	 * @param accessionNumberList a list of entry accession numbers.
	 * @return an <code>EntryAccessionNumber</code> object.
	 */
	EntryAccessionNumber buildEntryAccessionNumber(List<String> accessionNumberList);
	
	/**
	 * Build an <code>UniProtFlatFileParserLog</code> object.
	 * @param entryId then entry identifier.
	 * @param messages a list of messages.
	 * @return an <code>UniProtFlatFileParserLog</code> object.
	 */
	UniProtFlatFileParserLog buildUniProtFlatFileParserLog(String entryId, ArrayList<String> messages);
	
	/**
	 * Build an <code>EntryOrganelle</code> object.
	 * @param organelle the organelle of the protein.
	 * @return an <code>EntryOrganelle</code> object.
	 */
	EntryOrganelle buildEntryOrganelle(String organelle);
	
	/**
	 * Build an <code>EntryOrganismSpecies</code> object.
	 * @param organismSpecies the organism species of the protein.
	 * @return an <code>EntryOrganismSpecies</code> object.
	 */
	EntryOrganismSpecies buildEntryOrganismSpecies(String organismSpecies);
	
	
}
