package edu.udel.bioinformatics.pirsr.uniprot;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.proteininformationresource.pirsr.uniprot.model.EntryComment;
import org.proteininformationresource.pirsr.uniprot.model.EntryCommentType;
import org.proteininformationresource.pirsr.uniprot.model.EntryType;

import edu.udel.bioinformatics.pirsr.uniprot.io.UniProtFlatFileConstants;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 28, 2014<br>
 * <br>
 * 
 * The free text comments on the entry, and are used to convey any useful
 * information. The comments always appear below the last reference line and are
 * grouped together in comment blocks; a block is made up of 1 or more comment
 * lines. The first line of a block starts with the characters '-!-'.
 * 
 * The format of a comment block is:
 * 
 * 
 * CC -!- TOPIC: First line of a comment block;
 * 
 * CC second and subsequent lines of a comment block.
 * 
 * The comment blocks are arranged according to what we designate as 'topics'.
 */
public class EntryCommentImpl implements EntryComment {
	private EntryCommentType commentType;
	private String commentDescription;
	private List<String> commentDescriptionList;

	public EntryCommentImpl(EntryCommentType commentType, List<String> commentDescriptionList) {
		super();
		this.commentType = commentType;
		// this.commentDescription = commentDescription;
		this.commentDescriptionList = commentDescriptionList;
	}

	
	public EntryCommentType getCommentType() {
		return this.commentType;
	}

	// String lastCommentDescription = lastComment.getCommentDescription();
	// if (lastCommentDescription.length() > 0) {
	// if(lastCommentDescription.endsWith("-")) {
	// lastCommentDescription += commentDescription.trim();
	// }
	// else {
	// lastCommentDescription += " " + commentDescription.trim();
	// }
	// } else if (lastCommentDescription.length() == 0) {
	// lastCommentDescription = commentDescription.trim();
	// }
	// lastComment.setCommentDescription(lastCommentDescription);
	
	public String getCommentDescription() {
		this.commentDescription = "";
		for (String description : this.commentDescriptionList) {
			if (this.commentDescription.equals("")) {
				this.commentDescription += description.trim();
			} else {
				if (this.commentDescription.endsWith("-")) {
					this.commentDescription += description.trim();
				} else {
					this.commentDescription += " " + description.trim();
				}
			}
		}
		return EntryUtil.cleanup(this.commentDescription.trim());
	}

	
	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
		if(commentDescription.equals("")) {
			this.commentDescriptionList = new ArrayList<String>();
		}
	}

	public String toString() {
		String record = UniProtFlatFileConstants.CC_LINE_TOPIC_START;
		record += this.commentType.name().replaceAll("_", " ") + ": ";
		// record += WordUtils.wrap(EntryUtil.cleanup(this.commentDescription),
		// 60, "\n"+UniProtFlatFileConstants.CC_LINE_DESCRIPTION_START, true);
		record += EntryUtil.cleanup(this.commentDescription);
		return record;
	}

	public String toReport() {
		String report = UniProtFlatFileConstants.CC_LINE_TOPIC_START;
		report += this.commentType.name().replaceAll("_", " ") + ": ";
		// report += this.commentDescriptionList.get(0) + "\n";
		// for(int i = 1; i < this.commentDescriptionList.size(); i++) {
		// report +=
		// UniProtFlatFileConstants.CC_LINE_DESCRIPTION_START+this.commentDescriptionList.get(i).trim()+"\n";
		// }
		if (this.commentType.equals(EntryCommentType.COFACTOR)) {
			report += "\n" + getCofactorDescription(EntryUtil.cleanup(this.commentDescription));
		} else {
			report += WordUtils
					.wrap(EntryUtil.cleanup(this.commentDescription), 66, "\n" + UniProtFlatFileConstants.CC_LINE_DESCRIPTION_START, false);
		}
		return report;
	}

	private String getCofactorDescription(String description) {
		String report = "";
		if (description.contains("Note=")) {
			String[] fields = description.split("Note=");
			// report += UniProtFlatFileConstants.CC_LINE_DESCRIPTION_START +
			// fields[0].trim()+"\n";
			report += getCofactorNameXrefs(fields[0].trim());
			report += WordUtils.wrap(UniProtFlatFileConstants.CC_LINE_DESCRIPTION_START + "Note=" + fields[1], 66, "\n"
					+ UniProtFlatFileConstants.CC_LINE_DESCRIPTION_START, false);

		} else {
			report += getCofactorNameXrefs(description);
		}

		return report;
	}

	private String getCofactorNameXrefs(String description) {
		String report = "";
		String[] fields = description.split("; ");
		// System.out.println("??" + description);
		for (int i = 0; i < fields.length; i += 2) {
			report += UniProtFlatFileConstants.CC_LINE_DESCRIPTION_START + fields[i].trim() + "; " + fields[i + 1].trim() + "\n";
		}
		return report;
	}

	
	public List<String> getDescripitonList() {
		return this.commentDescriptionList;
	}

	// private String cleanup(String description) {
	// String cleanDescription = description;
	// cleanDescription =
	// cleanDescription.replaceAll(" Evidence=\\{ECO:.*?\\};", "");
	// cleanDescription = cleanDescription.replaceAll(" \\{ECO:.*?\\}\\.", "");
	// cleanDescription = cleanDescription.replaceAll(" \\{ECO:.*?\\}", "");
	// cleanDescription = cleanDescription.replaceAll("\\.$", "");
	//
	// cleanDescription = cleanDescription.replaceAll(" \\(By similarity\\)",
	// "");
	// cleanDescription = cleanDescription.replaceAll(" \\(Potential\\)", "");
	// cleanDescription = cleanDescription.replaceAll(" \\(Experimental\\)",
	// "");
	// return cleanDescription;
	// }
}
