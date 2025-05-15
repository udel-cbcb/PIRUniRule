package edu.udel.bioinformatics.pirsr.uniprot;

import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseCrossReference;
import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseType;

import edu.udel.bioinformatics.pirsr.uniprot.io.UniProtFlatFileConstants;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * Used as pointers to information in external data resources that is related to
 * UniProtKB entries. The full list of all databases to which UniProtKB is
 * cross-referenced can be found in the document dbxref.txt. It also contains
 * references describing these resources and provides links to their web sites.
 */
public class EntryDatabaseCrossReferenceImpl implements EntryDatabaseCrossReference {
	private EntryDatabaseType databaseType;
	private String identifier;
	private String optionalInfo1;
	private String optionalInfo2;
	private String optionalInfo3;
	
	
	public EntryDatabaseCrossReferenceImpl(EntryDatabaseType databaseType, String identifier, String optionalInfo1, String optionalInfo2,
			String optionalInfo3) {
		super();
		this.databaseType = databaseType;
		this.identifier = identifier;
		this.optionalInfo1 = optionalInfo1;
		this.optionalInfo2 = optionalInfo2;
		this.optionalInfo3 = optionalInfo3;
	}

	
	public EntryDatabaseType getDatabseType() {
		return this.databaseType;
	}

	
	public String getIdentifier() {
		return this.identifier;
	}

	
	public String getOptionalInfo1() {
		return this.optionalInfo1;
	}

	
	public String getOptionalInfo2() {
		return this.optionalInfo2;
	}

	
	public String getOptionalInfo3() {
		return this.optionalInfo3;
	}

	
	public boolean hasOptionalInfo2() {
		if(this.optionalInfo2 == null) {
			return false;
		}
		else {
			return true;
		}
	}

	
	public boolean hasOptionalInfo3() {
		if(this.optionalInfo3 == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public String toString() {
		String record = UniProtFlatFileConstants.DR_LINE_START;
		record += this.databaseType + ", ";
		record += this.identifier + ", ";
		record += this.optionalInfo1;
		if(this.hasOptionalInfo2()) {
			record += ", "+this.optionalInfo2;
		}
		if(this.hasOptionalInfo3()) {
			record += ", "+this.optionalInfo3;
		}
		return record;
	}
}
