package org.proteininformationresource.pirsitepredict.prediction;




public class PredictionTSVReport {
	
	private String jobId;
	
	//PIRSRID	PeptideID/ProteinID	Start	End	Type	Category	Description	NucleotideID	NucleotideORFStart	NucleotideORFEnd	ORFStrand
	private String pirsrId;
	
	private String proteinId;
	
	private String start;
	
	private String end;
	
	private String type;
	
	private String category;
	
	private String description;
	
	private String nucleotideId;
	
	private String nucleotideORFStart;
	
	private String nucleotideORFEnd;
	
	private String nucleotideORFStrand;

	public String getPirsrId() {
		return pirsrId;
	}

	public void setPirsrId(String pirsrId) {
		this.pirsrId = pirsrId;
	}

	public String getProteinId() {
		return proteinId;
	}

	public void setProteinId(String proteinId) {
		this.proteinId = proteinId;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNucleotideId() {
		return nucleotideId;
	}

	public void setNucleotideId(String nucleotideId) {
		this.nucleotideId = nucleotideId;
	}

	public String getNucleotideORFStart() {
		return nucleotideORFStart;
	}

	public void setNucleotideORFStart(String nucleotideORFStart) {
		this.nucleotideORFStart = nucleotideORFStart;
	}

	public String getNucleotideORFEnd() {
		return nucleotideORFEnd;
	}

	public void setNucleotideORFEnd(String nucleotideORFEnd) {
		this.nucleotideORFEnd = nucleotideORFEnd;
	}

	public String getNucleotideORFStrand() {
		return nucleotideORFStrand;
	}

	public void setNucleotideORFStrand(String nucleotideORFStrand) {
		this.nucleotideORFStrand = nucleotideORFStrand;
	}
	
	public String toString() {
		String report = "";
		report += this.pirsrId+"\t";
		report += this.proteinId+"\t";
		report += this.start+"\t";
		report += this.end+"\t";
		report += this.type+"\t";
		report += this.category+"\t";
		report += this.description+"\t";
		report += this.nucleotideId+"\t";
		report += this.nucleotideORFStart+"\t";
		report += this.nucleotideORFEnd+"\t";
		report += this.nucleotideORFStrand;
		return report;
			
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
}
