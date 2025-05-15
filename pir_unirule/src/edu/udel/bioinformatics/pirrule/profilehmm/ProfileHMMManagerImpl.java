package edu.udel.bioinformatics.pirrule.profilehmm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.proteininformationresource.pirrule.profilehmm.ProfileHMMManager;
import org.proteininformationresource.pirrule.profilehmm.Template;
import org.proteininformationresource.pirrule.profilehmm.TemplateResidue;
import org.proteininformationresource.pirrule.propagation.PropagationManager;


public class ProfileHMMManagerImpl implements ProfileHMMManager {

	@Override
	public Template getTemplateFromHMMSearch(String hmmsearchOutputFile) {

        String accession = null;
        String domain = null;
        List<String> lines = new ArrayList<String>();
        int targetLength = 0;
		LinkedHashMap<String, List<TemplateResidue>> domainAlignments = new LinkedHashMap<String, List<TemplateResidue>>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(hmmsearchOutputFile));

			
	        String line = null;
	        boolean modelStart = false;
	      
	        while ((line = br.readLine()) != null) {
	        		//line = line.trim();
	        		if(line.startsWith(">>")) {
	        			String[] acline = line.split(" ");
	        			accession = acline[1];
	        		}
	            	if(line.startsWith("  Alignments for each domain:")) {
	            		modelStart = true;
	            	}
	            	if(line.startsWith("Internal pipeline statistics summary:")) {
	            		modelStart = false;
	            	}
	            	if(line.startsWith("  == domain")) {
	            		if(lines.size() > 0) {
	            			domainAlignments.put(domain, parseAlignment(domain, lines));
	            			lines = new ArrayList<String>();
	            		}
	            		domain = line.replace("  == ", "").trim();
	            	}
	            	if(modelStart) {
	            		if(!line.startsWith("  == domain") && line.length() > 0 && !line.startsWith("  Alignments for each domain:")) {
	            			//System.out.println(line);
	            			lines.add(line);
	            		}
	            	}
	            //	Target sequences:                          1  (210 residues)
	            	if(line.startsWith("Target sequences:")) {
	            		String[] targetSeqInfo = line.split("\\s+");
	            		String len = targetSeqInfo[3].replace("(", "");
	            		targetLength = Integer.parseInt(len);
	            	}
	        }
	      	br.close();
	      	if(lines.size() > 0) {
	      		domainAlignments.put(domain, parseAlignment(domain, lines));
	      	}
	      	//System.out.println(accession);
	      	//System.out.println(lines);
	       
			br.close();
		} catch (IOException e) {
			Logger.getLogger(PropagationManager.class.getName()).log(Level.SEVERE, null, e);
		}
//		System.out.println(accession);
//		
//		for(String key : domainAlignments.keySet()) {
//			System.out.println(key);
//			for(TemplateResidue residue: domainAlignments.get(key)) {
//				System.out.println(residue);
//			}
//		}
		return new TemplateImpl(accession, targetLength, domainAlignments);
		//return null;
	}


	private List<TemplateResidue> parseAlignment(String domain, List<String> lines) {
		List<TemplateResidue> templateResidues = new ArrayList<TemplateResidue>();
	
		String[] alignments = lines.toArray(new String[lines.size()]);
        for(int i = 0; i < alignments.length; i=i+4) {
        		String[] profile = alignments[i].split("\\s+");
        		char[] profileResidues = profile[3].toCharArray();
        		//System.out.println("profile: |" + alignments[i] + "|");
        		String[] target = alignments[i+2].split("\\s+");
        		//System.out.println("target: |" + alignments[i+2] + "|");
        		String targetAccession = target[0];
        		int start = Integer.parseInt(target[2]);
        		char[] targetResidues = target[3].toCharArray();
        		//System.out.println("targetResidue: |" + target[3] + "|"+target[3].length());
        		int strStart = alignments[i+2].indexOf(target[3]);
        		char[] matches = alignments[i+1].substring(strStart).toCharArray();
        		//System.out.println("match:         |" + alignments[i+1].substring(strStart) + "|" + matches.length);
        		char[] posteriorProabilities = alignments[i+3].substring(strStart).toCharArray();
        		int end = Integer.parseInt(target[4]);
        		int position = start;
        		for(int j = 0; j < targetResidues.length; j++) {
        			char residue = targetResidues[j];
        			char match = matches[j];
        			char profileResidue = profileResidues[j];
        			char posteriorProability = posteriorProabilities[j];
        			boolean matchStatus = false;
        			if(match != ' ') {
    					matchStatus = true;
    				}
        			
        			if(residue !='-') {
        				TemplateResidue tr = new TemplateResidueImpl(residue, position, match, matchStatus, profileResidue, posteriorProability, domain);
        				templateResidues.add(tr);
        				position++;
        			}
        		}
        }
		return templateResidues;
	}
}
