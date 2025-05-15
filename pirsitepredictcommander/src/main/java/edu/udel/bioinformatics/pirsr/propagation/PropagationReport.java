package edu.udel.bioinformatics.pirsr.propagation;

import java.util.List;

import org.proteininformationresource.pirsr.propagation.PropagationRecord;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: October 22, 2014<br>
 * <br>
 * 
 */
public class PropagationReport {
	private int swissProtTruePositiveCount;
	private int swissProtFalsePositiveCount;
	private int swissProtFalseNegativeCount;
//	private int tremblTruePositiveCount;
//	private int tremblFalsePositiveCount;
//	private int tremblFalseNegativeCount;
	
	private String swissProtTruePositiveAnnotations;
	private String swissProtFalsePositiveAnnotations;
	private String swissProtFalseNegativeAnnotations;
	private String tremblTruePositiveAnnotations;
	private String tremblFalsePositiveAnnotations;
	private String tremblFalseNegativeAnnotations;
	private String otherAnnotations;
	private List<PropagationRecord> tremblKrakenRecord;
	private List<PropagationRecord> swissProtKrakenRecord;

	public PropagationReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSwissProtTruePositiveCount() {
		return swissProtTruePositiveCount;
	}

	public void setSwissProtTruePositiveCount(int swissProtTruePositiveCount) {
		this.swissProtTruePositiveCount = swissProtTruePositiveCount;
	}

	public int getSwissProtFalsePositiveCount() {
		return swissProtFalsePositiveCount;
	}

	public void setSwissProtFalsePositiveCount(int swissProtFalsePositiveCount) {
		this.swissProtFalsePositiveCount = swissProtFalsePositiveCount;
	}

	public int getSwissProtFalseNegativeCount() {
		return swissProtFalseNegativeCount;
	}

	public void setSwissProtFalseNegativeCount(int swissProtFalseNegativeCount) {
		this.swissProtFalseNegativeCount = swissProtFalseNegativeCount;
	}

	public String getSwissProtTruePositiveAnnotations() {
		return swissProtTruePositiveAnnotations;
	}

	public void setSwissProtTruePositiveAnnotations(String swissProtTruePositiveAnnotations) {
		this.swissProtTruePositiveAnnotations = swissProtTruePositiveAnnotations;
	}

	public String getSwissProtFalsePositiveAnnotations() {
		return swissProtFalsePositiveAnnotations;
	}

	public void setSwissProtFalsePositiveAnnotations(String swissProtFalsePositiveAnnotations) {
		this.swissProtFalsePositiveAnnotations = swissProtFalsePositiveAnnotations;
	}

	public String getSwissProtFalseNegativeAnnotations() {
		return swissProtFalseNegativeAnnotations;
	}

	public void setSwissProtFalseNegativeAnnotations(String swissProtFalseNegativeAnnotations) {
		this.swissProtFalseNegativeAnnotations = swissProtFalseNegativeAnnotations;
	}

	public String getTremblTruePositiveAnnotations() {
		return tremblTruePositiveAnnotations;
	}

	public void setTremblTruePositiveAnnotations(String tremblTruePositiveAnnotations) {
		this.tremblTruePositiveAnnotations = tremblTruePositiveAnnotations;
	}

	public String getTremblFalsePositiveAnnotations() {
		return tremblFalsePositiveAnnotations;
	}

	public void setTremblFalsePositiveAnnotations(String tremblFalsePositiveAnnotations) {
		this.tremblFalsePositiveAnnotations = tremblFalsePositiveAnnotations;
	}

	public String getTremblFalseNegativeAnnotations() {
		return tremblFalseNegativeAnnotations;
	}

	public void setTremblFalseNegativeAnnotations(String tremblFalseNegativeAnnotations) {
		this.tremblFalseNegativeAnnotations = tremblFalseNegativeAnnotations;
	}

	

	

	public List<PropagationRecord> getTremblKrakenRecord() {
		return tremblKrakenRecord;
	}

	public void setTremblKrakenRecord(List<PropagationRecord> tremblKrakenRecord) {
		this.tremblKrakenRecord = tremblKrakenRecord;
	}

	public List<PropagationRecord> getSwissProtKrakenRecord() {
		return swissProtKrakenRecord;
	}

	public void setSwissProtKrakenRecord(List<PropagationRecord> swissProtKrakenRecord) {
		this.swissProtKrakenRecord = swissProtKrakenRecord;
	}
	public String getOtherAnnotations() {
		return otherAnnotations;
	}

	public void setOtherAnnotations(String otherAnnotations) {
		this.otherAnnotations = otherAnnotations;
	}
	
	
}
