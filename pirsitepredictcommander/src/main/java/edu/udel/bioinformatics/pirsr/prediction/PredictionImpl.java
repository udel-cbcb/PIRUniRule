package edu.udel.bioinformatics.pirsr.prediction;

import org.proteininformationresource.pirsr.prediction.Prediction;

public class PredictionImpl implements Prediction{
	String type;
	String category;
	String description;

	public PredictionImpl(String type, String category, String description) {
		this.type = type;
		this.category = category;
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
