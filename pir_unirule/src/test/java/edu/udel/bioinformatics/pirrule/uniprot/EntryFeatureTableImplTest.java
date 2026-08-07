package edu.udel.bioinformatics.pirrule.uniprot;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.proteininformationresource.pirrule.uniprot.model.EntryFeatureType;

import edu.udel.bioinformatics.pirrule.uniprot.io.UniProtFlatFileConstants;

public class EntryFeatureTableImplTest {

	@Test
	public void toStringFormatsFeatureLineWithEndPoints() {
		EntryFeatureTableImpl feature = new EntryFeatureTableImpl(EntryFeatureType.MOD_RES, "1", "10");
		feature.setFeatureDescription("Phosphoserine (By similarity)");
		String value = feature.toString();
		assertTrue("Unexpected feature line: " + value,
				value.matches("FT   MOD_RES\\s+1\\s+10\\s+Phosphoserine"));
	}

	@Test
	public void toReportFormatsFeatureLineAndWrapsLongDescription() {
		EntryFeatureTableImpl feature = new EntryFeatureTableImpl(EntryFeatureType.MOD_RES, "1", "10");
		feature.setFeatureDescription(
				"Phosphoserine in the calcium binding loop coordinating two calcium ions at high affinity");
		String report = feature.toReport();
		assertTrue("Report should start with the feature key, got: " + report,
				report.startsWith("FT   MOD_RES"));
		assertTrue("Report should contain the from end point, got: " + report, report.contains("1"));
		assertTrue("Report should contain the to end point, got: " + report, report.contains("10"));
		assertTrue("Report should contain the description, got: " + report,
				report.contains("Phosphoserine in the calcium binding loop"));
		assertTrue("Report should wrap long descriptions, got: " + report,
				report.contains("\n" + UniProtFlatFileConstants.FT_DESCRIPTION_START));
	}
}
