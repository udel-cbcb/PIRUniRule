package org.proteininformationresource.pirsitepredict.distribution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

@Repository
//@Configuration
//@Controller
//@PropertySource({ "classpath:pirsitepredict.properties",
//		"classpath:persistence.properties" })
public class ReleaseRepository {
	

	public List<Release> findAllReleases(String distributionDir) {
		List<Release> releases = new ArrayList<Release>();
		String releaseNoteFile = distributionDir
				+ System.getProperty("file.separator") + "release_note.txt";
		try {
			String[] releaseEntries = readFile(releaseNoteFile).split("\n");
			for (String releaseInfo : releaseEntries) {
				if (releaseInfo.length() > 0) {
					String[] fields = releaseInfo.split("\t");
					Release release = new Release();
					release.setReleaseNumber(fields[0]);
					release.setNumberOfRules(Integer.parseInt(fields[1]));
					release.setReleaseDate(fields[2]);
					release.setTarFileName(fields[3]);
					release.setTarFileSize(fields[4]);
					release.setMd5(fields[5]);
					releases.add(release);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println(releases);
		return releases;

	}

	private String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

}
