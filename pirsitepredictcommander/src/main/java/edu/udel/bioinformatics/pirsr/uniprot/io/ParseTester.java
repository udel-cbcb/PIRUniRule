package edu.udel.bioinformatics.pirsr.uniprot.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.proteininformationresource.pirsr.uniprot.model.EntryDataFactory;
import org.proteininformationresource.pirsr.uniprot.model.EntryDatabaseType;
import org.proteininformationresource.pirsr.uniprot.model.UniProtEntry;

import edu.udel.bioinformatics.pirsr.uniprot.EntryDataFactoryImpl;



public class ParseTester {

	public static void main(String[] args) {
		//System.out.println(EntryDatabaseType.valueOf("RefSeq"));
		
		String file = args[0];
		List<UniProtEntry> entryList = new ArrayList<UniProtEntry>();
		BufferedReader br;
		EntryDataFactory factory = new EntryDataFactoryImpl();
		StringBuffer entryBuffer = new StringBuffer();
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				// if(line.startsWith("DR   ExpressionAtlas")) {
				//
				// }
				// else {
				entryBuffer.append(line + "\n");
				// }

				if (line.startsWith("//")) {
				
					UniProtEntry entry = UniProtFlatFileParser.parse(entryBuffer.toString(), factory);
					entryList.add(entry);
					entryBuffer = new StringBuffer();
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		

	}

}
