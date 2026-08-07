package edu.udel.bioinformatics.pirrule.uniprot;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.proteininformationresource.pirrule.uniprot.model.EntryCommentType;

import edu.udel.bioinformatics.pirrule.uniprot.io.UniProtFlatFileConstants;

public class EntryCommentImplTest {

	@Test
	public void commentDescriptionJoinsListWithSpace() {
		EntryCommentImpl comment = new EntryCommentImpl(EntryCommentType.FUNCTION,
				Arrays.asList("Enables the", "binding of ATP", "(By similarity)"));
		String description = comment.getCommentDescription();
		assertTrue("Expected stripped description, got: " + description,
				description.equals("Enables the binding of ATP"));
	}

	@Test
	public void commentDescriptionJoinsWithoutSpaceAfterHyphen() {
		EntryCommentImpl comment = new EntryCommentImpl(EntryCommentType.DOMAIN,
				Arrays.asList("Contains a long-", "lived domain"));
		String description = comment.getCommentDescription();
		assertTrue("Expected hyphen-joined description, got: " + description,
				description.equals("Contains a long-lived domain"));
	}

	@Test
	public void toStringIncludesTopicName() {
		EntryCommentImpl comment = new EntryCommentImpl(EntryCommentType.CATALYTIC_ACTIVITY,
				Arrays.asList("Catalytic activity"));
		comment.setCommentDescription("Catalytic activity");
		assertTrue(comment.toString().startsWith("CC   -!- CATALYTIC ACTIVITY: "));
	}

	@Test
	public void toReportWrapsLongDescriptionOntoContinuationLines() {
		EntryCommentImpl comment = new EntryCommentImpl(EntryCommentType.FUNCTION,
				Arrays.asList("placeholder"));
		comment.setCommentDescription(
				"This is a fairly long description that should be wrapped onto multiple lines within the comment block at sixty six characters");
		String report = comment.toReport();
		assertTrue("Report should start with topic line, got: " + report,
				report.startsWith("CC   -!- FUNCTION: "));
		assertTrue("Report should wrap long descriptions, got: " + report,
				report.contains("\n" + UniProtFlatFileConstants.CC_LINE_DESCRIPTION_START));
	}

	@Test
	public void cofactorReportFormatsNameXrefAndNote() {
		EntryCommentImpl comment = new EntryCommentImpl(EntryCommentType.COFACTOR,
				Arrays.asList("placeholder"));
		comment.setCommentDescription("Name=Zn(2+); Xref=ChEBI:CHEBI:29105; Note=Part of the active site.");
		String report = comment.toReport();
		assertTrue("Report should start with cofactor topic line, got: " + report,
				report.startsWith("CC   -!- COFACTOR: "));
		assertTrue("Report should contain name/xref pair, got: " + report,
				report.contains("CC       Name=Zn(2+); Xref=ChEBI:CHEBI:29105;"));
		assertTrue("Report should contain note, got: " + report,
				report.contains("CC       Note=Part of the active site."));
	}
}
