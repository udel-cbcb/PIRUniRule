package edu.udel.bioinformatics.pirsr.prediction.annotation;

import java.util.List;

import org.proteininformationresource.pirsr.model.FeatureDescriptionLine;
import org.proteininformationresource.pirsr.model.FeatureType;
import org.proteininformationresource.pirsr.prediction.Prediction;
import org.proteininformationresource.pirsr.prediction.annotation.ProteinFeatureTableAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleAnnotation;
import org.proteininformationresource.pirsr.propagation.annotation.RuleFeatureTableAnnotation;
import org.proteininformationresource.pirsr.uniprot.model.EntryComment;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureTable;
import org.proteininformationresource.pirsr.uniprot.model.EntryFeatureType;

import edu.udel.bioinformatics.pirsr.prediction.PredictionImpl;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: April 28, 2015<br>
 * <br>
 * 
 * The implementation of <code>ProteinFeatureTableAnnotation</code>.
 */
public class ProteinFeatureTableAnnotationImpl extends ProteinAnnotationImpl implements ProteinFeatureTableAnnotation {

	// private EntryFeatureTable feature;
	// private EntryFeatureType featureType;
	private FeatureType featureType;
	private FeatureDescriptionLine featureDescriptionLine;
	private String missMatchReason;

	// public ProteinFeatureTableAnnotationImpl(String proteinId,
	// EntryFeatureTable feature) {
	// super(proteinId);
	// this.feature = feature;
	// if(feature != null) {
	// this.featureType = feature.getFeatureType();
	// }
	// }

	public ProteinFeatureTableAnnotationImpl(String proteinId) {
		super(proteinId);

	}

	// public EntryFeatureTable getFeature() {
	// return this.feature;
	// }
	//
	// public void setFeature(EntryFeatureTable feature) {
	// this.feature = feature;
	// }

	// public String getFeatureDescription() {
	// return this.feature.getFeatureDescription();
	// }

	public String toKraken() {

		String record = "";

		if (featureDescriptionLine != null) {
			// Query
			record += super.getProteinId() + "\t";
			record += featureDescriptionLine.getFeatureFromPosition() + "\t";
			record += featureDescriptionLine.getFeatureToPosition();

//			record += "FEATURE_" + featureDescriptionLine.getFeatureKey() + "\t";
//
//			String description = featureDescriptionLine.getFeatureDescription().replaceAll("\\.$", "");
//			description = description.replace("\\;$", "");
//			record += description + "\t";
			
			Prediction prediction = this.getPrediction();
			if(prediction.getType() != null) {
				record += prediction.getType()+"\t";
			}
			else {
				record += ".\t";
			}
			
			if(prediction.getCategory() != null) {
				record += prediction.getCategory()+"\t";
			}
			else {
				record += ".\t";
			}
			if(prediction.getDescription() != null) {
				record += prediction.getDescription()+"\t";
			}
			else {
				record += ".\t";
			}

			if (getNucleotideSequenceId() != null) {
				record += getNucleotideSequenceId() + "\t";
			} else {
				record += ".\t";
			}
			if (getNucleotideORFStart() > 0) {
				record += getNucleotideORFStart() + "\t";
			} else {
				record += ".\t";
			}
			if (getNucleotideORFEnd() > 0) {
				record += getNucleotideORFEnd() + "\t";
			} else {
				record += "\t";
			}
			if (getNucleotideStrand() != null) {
				record += getNucleotideStrand() + "\t";
			} else {
				record += ".\t";
			}
		}
		return record;
	}

	public ProteinFeatureTableAnnotationImpl(String proteinId, FeatureDescriptionLine feature) {
		super(proteinId);
		this.featureDescriptionLine = feature;
	}

	public String getMissMatchReason() {
		return this.missMatchReason;
	}

	public String toString() {
//		String record = "";
//		record += super.getRuleAC() + "\t";
//		record += super.getTemplateAC() + "\t";
//		if (this.featureDescriptionLine != null) {
//			record += featureType + "\t";
//			String description = "";
//
//			if (this.getDescription() != null && this.getDescription().length() > 0) {
//				description += "Description: " + this.getDescription() + " | ";
//			}
//			// description = description.trim();
//			description += "Condition Pattern: " + this.featureDescriptionLine.getFeatureConditionPattern() + " | Group: "
//					+ this.featureDescriptionLine.getFeatureGroupNumber();
//			record += description + "\t";
//			record += this.featureDescriptionLine.getFeatureFromPosition() + "\t";
//			record += this.featureDescriptionLine.getFeatureToPosition();
//		} else {
//			record += "\t\t?\t?";
//		}
//		return record;
		String record = "";

		if (featureDescriptionLine != null) {
			// Query
			record += super.getProteinId() + "\t";
			record += featureDescriptionLine.getFeatureFromPosition() + "\t";
			record += featureDescriptionLine.getFeatureToPosition()+"\t";
			Prediction prediction = this.getPrediction();
			if(prediction.getType() != null) {
				record += prediction.getType()+"\t";
			}
			else {
				record += "\t";
			}
			
			if(prediction.getCategory() != null) {
				record += prediction.getCategory()+"\t";
			}
			else {
				record += "\t";
			}
			if(prediction.getDescription() != null) {
				record += prediction.getDescription()+"\t";
			}
			else {
				record += "\t";
			}

//			record += "FEATURE_" + featureDescriptionLine.getFeatureKey() + "\t";
//
//			String description = featureDescriptionLine.getFeatureDescription().replaceAll("\\.$", "");
//			description = description.replace("\\;$", "");
//			record += description + "\t";

			if (getNucleotideSequenceId() != null && getNucleotideSequenceId().length() > 0) {
				record += getNucleotideSequenceId() + "\t";
			} else {
				record += ".\t";
			}
			if (getNucleotideORFStart() > 0) {
				record += getNucleotideORFStart() + "\t";
			} else {
				record += ".\t";
			}
			if (getNucleotideORFEnd() > 0) {
				record += getNucleotideORFEnd() + "\t";
			} else {
				record += ".\t";
			}
			if (getNucleotideStrand() != null && getNucleotideStrand().length() > 0) {
				record += getNucleotideStrand() + "\t";
			} else {
				record += ".\t";
			}
		}
		return record;
	}

	@Override
	public String toReport() {
		return toString();
//		String report = "";
//		if (this.featureDescriptionLine != null) {
//			report += featureType + "\t";
//			String description = "";
//			List<String> descriptionList = this.featureDescriptionLine.getDescriptions();
//			if (descriptionList != null && descriptionList.size() > 0) {
//
//				description += "Description: ";
//				for (int i = 0; i < descriptionList.size(); i++) {
//					if (i == 0) {
//						description += descriptionList.get(i);
//					} else {
//						description += " " + descriptionList.get(i);
//					}
//				}
//			}
//			description = description.trim();
//			description += " | Condition Pattern: " + this.featureDescriptionLine.getFeatureConditionPattern() + " | Group: "
//					+ this.featureDescriptionLine.getFeatureGroupNumber();
//			report += description + "\t";
//			report += this.featureDescriptionLine.getFeatureFromPosition() + "\t";
//			report += this.featureDescriptionLine.getFeatureToPosition();
//		} else {
//			report += "\t\t?\t?";
//		}
//		return report;
	}

	// @Override
	// public String toKraken() {
	// String kraken = super.getRuleAC();
	// return kraken;
	// }

	public String getDescription() {
		String description = "";
		if (this.featureDescriptionLine != null) {
			List<String> descriptionList = this.featureDescriptionLine.getDescriptions();
			// System.out.println(descriptionList);
			if (descriptionList != null && descriptionList.size() > 0) {

				for (int i = 0; i < descriptionList.size(); i++) {
					if (i == 0) {
						description += descriptionList.get(i).trim();
					} else {
						if (description.endsWith("-")) {
							description += descriptionList.get(i).trim();
						} else {
							description += " " + descriptionList.get(i).trim();
						}
					}
				}
			}
		}
		description = description.trim();
		return description;
	}

	public FeatureType getFeatureType() {
		return this.featureType;
	}

	public FeatureDescriptionLine getFeatureDescriptionLine() {
		return this.featureDescriptionLine;
	}

	public void setFeatureType(FeatureType featureType) {
		this.featureType = featureType;
	}

	public void setFeatureDescriptionLine(FeatureDescriptionLine featureDescriptionLine) {
		this.featureDescriptionLine = featureDescriptionLine;
	}

	public void setMissMatchReason(String missMatchReason) {
		this.missMatchReason = missMatchReason;
	}

	public Prediction getPrediction() {
		return new PredictionImpl("Feature", this.getFeatureDescriptionLine().getFeatureKey().getFeature(), this.getDescription().replaceAll("\\.$", ""));
	}

	public String toXML() {
		StringBuffer xml = new StringBuffer();
		//xml.append("\t\t <prediction>\n");
		xml.append("\t\t  <feature type=\""+this.getFeatureDescriptionLine().getFeatureKey().getFeature()+"\" description=\""+ this.getDescription().replaceAll("\\.$", "")+"\">\n");
		xml.append("\t\t   <location>\n");
		//if(this.featureDescriptionLine.getFeatureFromPosition().equals(this.featureDescriptionLine.getFeatureToPosition())) {
		xml.append("\t\t     <begin position=\""+this.featureDescriptionLine.getFeatureFromPosition()+"\"/>\n");
		xml.append("\t\t     <end position=\""+this.featureDescriptionLine.getFeatureToPosition()+"\"/>\n");
		//}
		xml.append("\t\t   </location>\n");
		xml.append("\t\t  </feature>\n");
		//xml.append("\t\t </prediction>\n");
		return xml.toString();
	}
}
