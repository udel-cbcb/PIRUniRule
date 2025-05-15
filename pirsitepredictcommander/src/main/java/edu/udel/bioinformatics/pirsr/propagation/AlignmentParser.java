package edu.udel.bioinformatics.pirsr.propagation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.propagation.Alignment;
import org.proteininformationresource.pirsr.propagation.PropagationManager;

/**
 * Author: Chuming Chen<br>
 * University of Delaware<br>
 * Center for Bioinformatics and Computational Biology<br>
 * Date: March 8, 2014<br>
 * <br>
 * 
 * 
 * A parser to parse an alignment record.
 */
public class AlignmentParser {

	private PropagationManager annotationManager;
	private BufferedReader br;
	private List<Alignment> alignments;


	public AlignmentParser(PropagationManager annotationManager, BufferedReader br) {
		this.annotationManager = annotationManager;
		this.br = br;
		this.alignments = new ArrayList<Alignment>();
	}

	public void parse() {
		try {
			while (br.ready()) {
				String s;
				s = br.readLine();
				if (s.indexOf("SRHMM") != -1) {
					String[] gff = s.split("\t");
					String entryId = gff[0];
					String featureGenerator = gff[1];
					String trigger = gff[2];
					String[] triggerArr = trigger.split("-");
					String srhmm = triggerArr[0];
					String ruleAC = trigger.replace("SRHMM", "PIRSR");
					String pirsfID = srhmm.replace("SRHMM", "PIRSF");
					String seqStart = gff[3];
					String seqEnd = gff[4];
//					String score = gff[5];
//					String strand = gff[6];
//					String frame = gff[7];
					String selected = gff[8];
					String alignment = gff[9];

					String[] alignmentArr = alignment.split(" ;");
					String sequence = alignmentArr[0].trim();
					String template = alignmentArr[1].trim();
					String seqAln = sequence.replaceFirst("Sequence \"", "").replaceAll("\"", "");

					String[] featureTemplate = template.split(" ");
					String templateEntryAC = featureTemplate[1].replaceAll("\"", "");
					String templateAln = featureTemplate[2].replaceAll("\"", "");
					Alignment aln = this.annotationManager.getPropagationDataFactory().buildAlignment();
					aln.setEntryId(entryId);
					aln.setFeatureGenerator(featureGenerator);
					aln.setTrigger(trigger);
					aln.setRuleAC(ruleAC);
					aln.setPIRSFID(pirsfID);
					aln.setSeqStart(Integer.parseInt(seqStart));
					aln.setSeqEnd(Integer.parseInt(seqEnd));
					aln.setSelected(selected);
					aln.setAlignedSeq(seqAln);
					aln.setTemplateEntryAC(templateEntryAC);
					aln.setAlignedTemplateSeq(templateAln);
					
					this.alignments.add(aln);
					//System.out.println(aln.getEntryId() + "\t" +aln.getRuleAC()+"\t" + aln.getTrigger() + "\t"+aln.getTemplateEntryAC());
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Alignment> getAlignments() {
		return this.alignments;
	}

}
