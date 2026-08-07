package edu.udel.bioinformatics.pirsr.uniprot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EntryUtilTest {

	@Test
	public void cleanupRemovesInlineEvidenceTag() {
		String description = "Required for the activity Evidence={ECO:0000303|Ref.6};";
		assertEquals("Required for the activity", EntryUtil.cleanup(description));
	}

	@Test
	public void cleanupRemovesWhitespaceDelimitedEvidenceTag() {
		String description = "Binds calcium (By similarity) {ECO:0000256|HAMAP-Rule:MF_03176}.";
		assertEquals("Binds calcium", EntryUtil.cleanup(description));
	}

	@Test
	public void cleanupRemovesQualifierSuffixes() {
		String description = "Interacts with ATP (Potential)";
		assertEquals("Interacts with ATP", EntryUtil.cleanup(description));
	}

	@Test
	public void cleanupNormalizesTrailingSemicolonPeriod() {
		String description = "Catalytic activity.;";
		assertEquals("Catalytic activity.", EntryUtil.cleanup(description));
	}

	@Test
	public void cleanupLeavesPlainDescriptionUntouched() {
		String description = "This text needs no cleanup.";
		assertEquals("This text needs no cleanup.", EntryUtil.cleanup(description));
	}
}
