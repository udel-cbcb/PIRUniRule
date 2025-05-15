package org.proteininformationresource.pirsr.uniprot.model;

import java.util.List;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * The FT (Feature Table) lines provide a precise but simple means for the
 * annotation of the sequence data. The table describes regions or sites of
 * interest in the sequence. In general the feature table lists
 * posttranslational modifications, binding sites, enzyme active sites, local
 * secondary structure or other characteristics reported in the cited
 * references. Sequence conflicts between references are also included in the
 * feature table.
 * 
 * The FT lines have a fixed format. The column numbers allocated to each of the
 * data items within each FT line are shown in the following table (column
 * numbers not referred to in the table are always occupied by blanks).
 * 
 * Columns Data item 1-2 FT 6-13 Key name 15-20 'From' endpoint 22-27 'To'
 * endpoint 35-75 Description
 * 
 * The key name and the endpoints are always on a single line, but the
 * description may require one or more additional lines. In this event, the
 * following line contains blanks in the columns 3-34, and the description
 * continues from column 35 onwards as in the line above. Thus a blank key
 * always denotes a continuation of the previous description.
 * 
 * Some features are associated with a unique and stable feature identifier
 * (FTId), which allows to construct links directly from position-specific
 * annotation in the feature table to specialized protein-related databases. The
 * FTId is always the last component of a feature in the description field. The
 * format of a feature with a feature identifier is:
 *
 * FT KEY_NAME x x [Description.] 
 * 
 * FT              /FTId=XXX_number.
 *
 * where XXX is the 3-letter code for the specific feature key, separated by an
 * understore from a 6 to 10-digit number.
 * 
 * Feature identifiers currently exist for the feature keys CARBOHYD, CHAIN,
 * PEPTIDE, PROPEP, VARIANT and VAR_SEQ . The format of the corresponding FTIds
 * is shown in the following table:
 * 
 * Key name			Format of the FTId	Availability 
 * CARBOHYD 		CAR_number			Currently only for residues attached to an 
 * 										oligosaccharide structure annotated in the
 * 										UniCarbKB database 
 * CHAIN, PEPTIDE	PRO_number			Any mature polypeptide
 * PROPEP			PRO_number			Any processed propeptide
 * VARIANT 			VAR_number			Currently only for protein sequence variants
 * 										of Hominidae (great apes and humans)
 * VAR_SEQ			VSP_number			Any sequence with a VAR_SEQ feature
 */
public interface EntryFeatureTable extends EntryLine {

	/**
	 * Gets the type of sequence feature.
	 * 
	 * @return the type of sequence feature.
	 */
	EntryFeatureType getFeatureType();

	/**
	 * Gets the 'from' end point.
	 * 
	 * @return the 'from' end point.
	 */
	String getFromEndPoint();
	
	void setFromEndPoint(String fromEndPoint);

	/**
	 * Gets the 'to' end point.
	 * 
	 * @return the 'to' end point.
	 */
	String getToEndPoint();

	void setToEndPoint(String toEndPoint);
	
	/**
	 * Gets the description of this sequence feature.
	 * 
	 * @return the description of this sequence feature.
	 */
	String getFeatureDescription();
	
	/**
	 * Sets the description of this sequence feature.
	 * @param description the description of this sequence feature.
	 */
	void setFeatureDescription(String description);
	
	/**
	 * Gets the feature identifier (FTId).
	 * @return the feature identifier (FTId).
	 */
	String getFeatureIdentifier();
	
	/**
	 * Sets the feature identifier (FTId).
	 * @param featureIdentifier the feature identifier (FTId).
	 */
	void setFeatureIdentifier(String featureIdentifier);
	
	/**
	 * Gets the feature description as list.
	 * @return the feature description as list.
	 */
	//List<String> getFeatureDescriptionList();

	String toReport();
}
