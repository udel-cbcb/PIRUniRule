package org.proteininformationresource.pirsitepredict.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.proteininformationresource.pirsitepredict.distribution.Release;
import org.proteininformationresource.pirsitepredict.distribution.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

@Configuration
@Controller
@PropertySource({ "classpath:pirsitepredict.properties",
		"classpath:persistence.properties" })
public class DownloadController {

	@Autowired
	private Environment env;

	@Autowired
	private ReleaseRepository releaseRepository;

	@Autowired
	private ApplicationContext ctx;

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String index(Model model) {
		List<Release> allReleases = releaseRepository.findAllReleases(env
				.getProperty("distributionDir"));
		model.addAttribute("releases", allReleases);
		return "download/index";
	}

	@RequestMapping(value = " /download/release/{releaseNumber}/{releaseFileName}", method = RequestMethod.GET)
	public void getReleaseTarFile(@PathVariable String releaseNumber, @PathVariable String releaseFileName,
			HttpServletRequest request, HttpServletResponse response) {
		String releaseFile = env.getProperty("distributionDir")
				+ System.getProperty("file.separator") + "PIRSitePredict_"
				+ releaseNumber + ".tar.gz";
		File file = new File(releaseFile);
		System.out.println(releaseNumber);
		System.out.println(releaseFileName);
		response.setContentType("application/x-gzip");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ releaseFileName + "\"");
//		response.setHeader("Content-Disposition", "attachment; filename=\""
//				+ "PIRSitePredict_"
//				+ releaseNumber + ".tar.gz" + "\"");
		try {
			FileCopyUtils.copy(new FileInputStream(releaseFile),
					response.getOutputStream());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = " /download/example_prediction/{type}", method = RequestMethod.GET)
	public void getExamplePrediction(@PathVariable String type,
			HttpServletRequest request, HttpServletResponse response) {
		String examplePredictionFile = env.getProperty("exampleDir")
				+ System.getProperty("file.separator")
				+ "example_interproscan_prediction." + type;
		File file = new File(examplePredictionFile);
		if (type.equals("tsv")) {
			response.setContentType("text/tab-separated-values");
		} else if (type.equals("xml")) {
			response.setContentType("application/xml");
		} else if (type.equals("gff3")) {
			response.setContentType("text/plain");
		}

		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ file.getName() + "\"");
		try {
			FileCopyUtils.copy(new FileInputStream(examplePredictionFile),
					response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = " /download/releaseNotes", method = RequestMethod.GET)
	public String getReleaseNotes(Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String releaseNoteFile = env.getProperty("distributionDir")
				+ System.getProperty("file.separator")
				+ "README.txt";
		String releaseNotes = "";
		try {
			releaseNotes = readFile(releaseNoteFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("releaseNotes", releaseNotes);
		return "download/releaseNotes";
	}

	@RequestMapping(value = " /download/example_interproscan", method = RequestMethod.GET)
	public void getExampleInterProScan(HttpServletRequest request,
			HttpServletResponse response) {
		String exampleInterProScanFile = env.getProperty("exampleDir")
				+ System.getProperty("file.separator")
				+ "example_interproscan.xml";
		File file = new File(exampleInterProScanFile);
		response.setContentType("application/xml");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ file.getName() + "\"");
		try {
			FileCopyUtils.copy(new FileInputStream(exampleInterProScanFile),
					response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
