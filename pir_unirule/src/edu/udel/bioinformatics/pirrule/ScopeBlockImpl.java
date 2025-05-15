package edu.udel.bioinformatics.pirrule;

import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirrule.model.KingdomLine;
import org.proteininformationresource.pirrule.model.Line;
import org.proteininformationresource.pirrule.model.ScopeBlock;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: July 16, 2014<br>
 * <br>
 * 
 * The Scope Block that lists the taxonomic classes in which a rule match may be
 * found. The taxonomic classification is composed of the kingdom, optionally
 * followed by the name of a sub-taxon, to further limit the application of the
 * PIR Rule to any taxonomic level. Valid values for kingdom are: "Eukaryota",
 * "Bacteria", "Archaea", "Viruses", "Bacteriophage", "Plastid" and
 * "Mitochondrion". The two latter values designate proteins encoded in the
 * organelle genome but not proteins encoded in the nucleus and targeted to the
 * organelle. If it has been assessed with certainty that a PIR Rule is not
 * represented in: A taxonomic group, its name may be indicated in the "except"
 * field. A complete proteome, its taxcode may be indicated in the "not in"
 * field.
 * 
 */
public class ScopeBlockImpl implements ScopeBlock {

	private List<KingdomLine> kingdomLines;

	public ScopeBlockImpl() {
		super();

	}

	public ScopeBlockImpl(List<KingdomLine> kingdomLines) {
		super();
		this.kingdomLines = kingdomLines;
	}

	@Override
	public List<KingdomLine> getKingdomLines() {
		return this.kingdomLines;
	}

	@Override
	public void setKingdomLines(List<KingdomLine> kingdomLines) {
		this.kingdomLines = kingdomLines;
	}


	public void addLine(Line line) {
		if(this.kingdomLines == null) {
			this.kingdomLines = new ArrayList<KingdomLine>();
		}
		this.kingdomLines.add((KingdomLine) line);
	}
	
	public String toString() {
		String str = "Scope:\n";
		
		for (KingdomLine kingdomLine : kingdomLines) {
			String kingdom = kingdomLine.getKingdom();
			String kingdomSubTaxon = kingdomLine.getKingdomSubTaxon();
			List<String> exceptTaxonomicGroups = kingdomLine.getExceptTaxonomicGroups();
			List<String> completeProteomeNotInTaxCodes = kingdomLine.getCompleteProteomeNotInTaxCodes();
			if (kingdom != null && kingdom.length() > 0) {
				str += " " + kingdom;
			}
			if (kingdomSubTaxon != null && kingdomSubTaxon.length() > 0) {
				str += "; " + kingdomSubTaxon;
			}
			str += "\n";
			if (exceptTaxonomicGroups != null && exceptTaxonomicGroups.size() > 0) {
				for (String except : exceptTaxonomicGroups) {
					str += "  except " + except + "\n";
				}
			}
			if (completeProteomeNotInTaxCodes != null && completeProteomeNotInTaxCodes.size() > 0) {
				String notInList = "  not in ";
				for (String notIn : completeProteomeNotInTaxCodes) {
					notInList += notIn + ", ";
				}
				notInList = notInList.substring(0, notInList.length() - 2);
				str += notInList + "\n";
			}
		}
		return str;
	}
}
