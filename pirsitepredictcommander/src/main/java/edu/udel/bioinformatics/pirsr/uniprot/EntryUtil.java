package edu.udel.bioinformatics.pirsr.uniprot;

public class EntryUtil {

	public static String cleanup(String description) {
		String cleanDescription = description;
		cleanDescription = cleanDescription.replaceAll(" Evidence=\\{ECO:.*?\\};", "");
		cleanDescription = cleanDescription.replaceAll("\\s+\\{ECO:.*?\\}\\.", "");
		cleanDescription = cleanDescription.replaceAll("\\s+\\{ECO:.*?\\}\\;", "");
		cleanDescription = cleanDescription.replaceAll("\\s+\\{ECO:.*?\\}", "");
		cleanDescription = cleanDescription.replaceAll("\\{ECO:.*?\\}\\.", "");
		cleanDescription = cleanDescription.replaceAll("\\{ECO:.*?\\}\\;", "");
		cleanDescription = cleanDescription.replaceAll("\\{ECO:.*?\\}", "");
		cleanDescription = cleanDescription.replaceAll("\\.;$", "\\.");
		
		cleanDescription = cleanDescription.replaceAll(" \\(By similarity\\)", "");
		cleanDescription = cleanDescription.replaceAll(" \\(Potential\\)", "");
		cleanDescription = cleanDescription.replaceAll(" \\(Experimental\\)", "");
		return cleanDescription;
	}
}
