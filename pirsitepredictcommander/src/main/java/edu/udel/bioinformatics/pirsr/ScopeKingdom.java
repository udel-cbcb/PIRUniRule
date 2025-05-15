package edu.udel.bioinformatics.pirsr;

public enum ScopeKingdom {
//	Archaea("Archaea"),
//	Bacteria("Bacteria"),
//	Bacillaceae("Bacillaceae"),
//	Bacteriaophage("Bacteriophage"),
//	Caenorhabditis("Caenorhabditis"),
//	Enterobacteriaceae("Enterobacteriaceae"),
//	Cupriavidus("Cupriavidus"),
//	Eukaryota("Eukaryota"),
//	Fungi("Fungi"),
//	Gammaproteobacteria("Gammaproteobacteria"),
//	Mammalia("Mammalia"),
//	Metazoa("Metazoa"),
//	Mitochondrion("Mitochondrion"),
//	Neurospora("Neurospora"),
//	Shimwellia("Shimwellia"),
//	Plastid("Plastid"),
//	Vertebrata("Vertebrata"),
//	Viridiplantae("Viridiplantae"),
//	Viruses("Viruses");
	Amoebozoa("Amoebozoa"),
	Archaea("Archaea"),
	Ascomycota("Ascomycota"),
	Bacillales("Bacillales"),
	Bacteria("Bacteria"),
	Bilateria("Bilateria"),
	Chlamydomonadales("Chlamydomonadales"),
	Chordata("Chordata"),
	Dikarya("Dikarya"),
	Ecdysozoa("Ecdysozoa"),
	Embryophyta("Embryophyta"),
	Eukaryota("Eukaryota"),
	Firmicutes("Firmicutes"),
	Fungi("Fungi"),
	Gammaproteobacteria("Gammaproteobacteria"),
	Helicobacter("Helicobacter"),
	Magnoliophyta("Magnoliophyta"),
	Mammalia("Mammalia"),
	Metazoa("Metazoa"),
	Proteobacteria("Proteobacteria"),
	Saccharomycetes("Saccharomycetes"),
	Spermatophyta("Spermatophyta"),
	Unclassified("Unclassified"),
	Vertebrata("Vertebrata"),
	Viridiplantae("Viridiplantae"),
	Viruses("Viruses");
	
	private String kingdom;
	
	ScopeKingdom(String kingdom) {
		this.kingdom = kingdom;
	}
	
	public String getKingdom() {
		return this.kingdom;
	}
}
