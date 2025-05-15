package org.proteininformationresource.pirsitepredict.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.proteininformationresource.pirsitepredict.distribution.Release;
import org.proteininformationresource.pirsitepredict.distribution.ReleaseRepository;
import org.proteininformationresource.pirsitepredict.prediction.PredictionForm;
import org.proteininformationresource.pirsitepredict.prediction.PredictionTSVReport;
import org.proteininformationresource.pirsitepredict.prediction.PredictionTSVReportRepository;
import org.proteininformationresource.pirsitepredict.prediction.PredictionTSVReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;

@Configuration
@Controller
// @Scope("session")
@PropertySource({ "classpath:pirsitepredict.properties", "classpath:persistence.properties" })
public class PredictionController {
	@Autowired
	private PredictionTSVReportService reportService;

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private Environment env;

	@Autowired
	private ReleaseRepository releaseRepository;

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private PredictionTSVReportRepository reportRepository;

	@RequestMapping(value = "/prediction/service", method = RequestMethod.GET)
	public String index(@Valid @ModelAttribute("predictionForm") PredictionForm predictionForm, final BindingResult bindingResult, final ModelMap model) {
		List<Release> allReleases = releaseRepository.findAllReleases(env.getProperty("distributionDir"));
		model.addAttribute("allReleases", allReleases);

		predictionForm = new PredictionForm();
		model.addAttribute("organisms", predictionForm.getOrganisms());

		model.addAttribute("predictionForm", predictionForm);
		model.addAttribute("EValue", "0.0001");
		return "prediction/index";
	}

	// @RequestMapping(value="rawJsonTest/{name}", method = RequestMethod.GET,
	// produces=MediaType.APPLICATION_JSON_VALUE)
	// public @ResponseBody String rawJsonTest(@PathVariable String name) {
	// return "{\"test\":5}";
	//
	// }
	@RequestMapping(value = "/prediction/service", method = RequestMethod.POST, produces = "Application/json")
	@ResponseBody
	public String processPredictionForm(@Valid @ModelAttribute("predictionForm") final PredictionForm form,
			@RequestParam(value = "interProScanXMLFileUploaded", required = false) MultipartFile interProScanXMLFileUploaded, BindingResult bindingResult,
			final ModelMap model, HttpServletRequest request) {

		bindingResult = validateForm(form, interProScanXMLFileUploaded, bindingResult);

		if (bindingResult.hasErrors()) {

			List<Release> allReleases = releaseRepository.findAllReleases(env.getProperty("distributionDir"));
			model.addAttribute("allReleases", allReleases);
			model.addAttribute("organisms", form.getOrganisms());
			model.addAttribute("predictionForm", form);
			model.addAttribute("EValue", form.getEValue().trim());
			model.addAttribute("Email", form.getEmail().trim());
			model.addAttribute("JobTitle", form.getJobTitle().trim());
			// System.out.println(bindingResult+"?");
			// for(FieldError error : bindingResult.getFieldErrors()) {
			// System.out.println(error.getDefaultMessage());
			// }
			String errorMsg = "";
			for (ObjectError error : bindingResult.getAllErrors()) {
				errorMsg += error.getObjectName() + ": " + error.getDefaultMessage() + "<br/>";
			}
			// return "prediction/index1";
			// return errorMsg;
			return "{\"errorMsg\" : \"" + errorMsg + "\"}";
		} else {
			form.setInterProScanXMLFile(interProScanXMLFileUploaded.getOriginalFilename());
			String jobId = createJobId(request);
			String cwd = env.getProperty("currentWorkingDir");
			String dataDir = env.getProperty("dataDir");
			String hmmsearch = env.getProperty("hmmsearch");
			String hmmalign = env.getProperty("hmmalign");
			String fileSeparator = System.getProperty("file.separator");
			File jobDir = new File(cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId);
			if (!jobDir.exists()) {
				jobDir.mkdirs();
			}
			String logFile = jobDir.getAbsolutePath() + fileSeparator + "run.log";

			String interProScanXMLFileUploadedPath = saveUploadedFile(interProScanXMLFileUploaded, jobDir, jobId);

			String script = dataDir + fileSeparator + "PIRSitePredict_" + form.getRelease() + fileSeparator + "pirsitepredict.sh";

			script += " -d " + dataDir + fileSeparator + "PIRSitePredict_" + form.getRelease() + fileSeparator + "data " + " -i "
					+ interProScanXMLFileUploadedPath + " -S " + hmmsearch + " -A " + hmmalign + " -O " + form.getOrganism() + " -o " + jobDir + fileSeparator
					+ "outputDir -f TSV,XML,GFF3 -F -l " + logFile;
			final String scriptFile = jobDir + fileSeparator + "run.sh";
			writeToFile(scriptFile, script, false);

			String reldate = jobDir.getAbsolutePath() + fileSeparator + "reldate.txt";
			writeToFile(reldate, form.getRelease() + "\n", false);

			if (form.getEmail() != null && form.getEmail().length() > 0) {
				String userEmail = jobDir.getAbsolutePath() + fileSeparator + "userEmail.txt";
				writeToFile(userEmail, form.getEmail() + "\n", false);
			}
			if (form.getJobTitle() != null && form.getJobTitle().length() > 0) {
				String jobTitleFile = jobDir.getAbsolutePath() + fileSeparator + "jobTitle.txt";
				writeToFile(jobTitleFile, form.getJobTitle() + "\n", false);
			}
			model.addAttribute("release", form.getRelease());
			model.addAttribute("jobId", jobId);
			if (taskExecutor != null) {
				taskExecutor.execute(new Runnable() {
					public void run() {
						try {
							Runtime.getRuntime().exec("/bin/bash " + scriptFile).waitFor();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			// runPrediction(jobId, cwd, dataDir, hmmsearch, hmmalign, form);
			// return "redirect:job/";
			// String url = "job/"+jobId;
			//

			// System.out.println(jobURL);
			// return "prediction/job";
			// return jobURL;
			// return "{\"jobId\": \""+jobId+"\"}";
			// String jobURL =
			// request.getRequestURL().toString().replace("service",
			// "job")+"/"+jobId;
			// String jobURL =
			// request.getContextPath().toString().replace("service",
			// "job")+"/"+jobId;
			// String jobURL =
			// request.getContextPath().toString()+"/prediction/job/"+jobId;
			String jobURL = env.getProperty("siteBaseURL") + "/prediction/job/" + jobId;

			return "{\"redirectURL\" : \"" + jobURL + "\"}";
		}

	}

	private static String getCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
		Date date = new Date();
		return dateFormat.format(date);
	}

	@RequestMapping(value = "/prediction/job/{jobId}", method = { RequestMethod.POST, RequestMethod.GET })
	public String getPredictionJobStatus(@PathVariable String jobId, Model model) {
		model.addAttribute("jobId", jobId);
		return "prediction/job";
	}

	@RequestMapping(value = "/prediction/detailView/job/{jobId}/nucleotide/{nucleotideId}", method = RequestMethod.GET)
	public String getPredictionDetailViewByNucleotide(@PathVariable String jobId, @PathVariable String nucleotideId, Model model, HttpServletRequest request) {
		model.addAttribute("jobId", jobId);
		model.addAttribute("nucleotideId", nucleotideId);
		model.addAttribute("proteinList", getProteinListByNucleotide(jobId, nucleotideId));
		model.addAttribute("proteinDetailView", getProteinDetailViewByNucleotide(jobId, nucleotideId, request));
		return "prediction/nucleotideCentricView";
	}

	private String getProteinDetailViewByNucleotide(String jobId, String nucleotideId, HttpServletRequest request) {
		String cwd = env.getProperty("currentWorkingDir");
		String fileSeparator = System.getProperty("file.separator");
		String jobDir = cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId;
		String nucleotideSeqFile = jobDir + fileSeparator + "outputDir" + fileSeparator + "iprscan" + fileSeparator + "seq" + fileSeparator
				+ "IPRScanMatchedNucleotideSeqs.fasta";
		String proteinSeqFile = jobDir + fileSeparator + "outputDir" + fileSeparator + "iprscan" + fileSeparator + "seq" + fileSeparator
				+ "IPRScanMatchedProteinSeqs.fasta";
		String tsvReportFile = jobDir + fileSeparator + "outputDir" + fileSeparator + "prediction" + fileSeparator + "pirsr_prediction.tsv";
		List<PredictionTSVReport> reports = parsePredictionTSVReport(jobId, tsvReportFile);
		String view = "";
		// view +=
		// "<a href=\"#nuclSeq\" class=\"toggle btn btn-info\">Show/Hide Nucleotide Sequences</a><br/>";
		// view += "<div id=\"nuclSeq\" class=\"hidden\">";
		view += this.getSequenceView(nucleotideId, nucleotideSeqFile) + "<br/><br/>";
		// view += "</div><br/>";
		List<String> proteinList = new ArrayList<String>();
		for (PredictionTSVReport report : reports) {
			if (report.getNucleotideId() != null && report.getNucleotideId().equals(nucleotideId)) {
				if (!proteinList.contains(report.getProteinId())) {
					proteinList.add(report.getProteinId());
				}
			}
		}

		for (String protein : proteinList) {
			view += getProteinDetailView(protein, reports, proteinSeqFile, request);
		}
		return view;
	}

	private String getProteinListByNucleotide(String jobId, String nucleotideId) {

		String cwd = env.getProperty("currentWorkingDir");
		String fileSeparator = System.getProperty("file.separator");
		String jobDir = cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId;
		String tsvReportFile = jobDir + fileSeparator + "outputDir" + fileSeparator + "prediction" + fileSeparator + "pirsr_prediction.tsv";
		List<PredictionTSVReport> reports = parsePredictionTSVReport(jobId, tsvReportFile);

		List<String> proteinList = new ArrayList<String>();
		for (PredictionTSVReport report : reports) {
			if (report.getNucleotideId() != null && report.getNucleotideId().equals(nucleotideId)) {
				if (!proteinList.contains(report.getProteinId())) {
					proteinList.add(report.getProteinId());
				}
			}
		}

		String proteinListStr = "";
		if (proteinList.size() > 0) {
			proteinListStr += "<br/><br/><h4>Proteins</h4><ul>";
			for (String protein : proteinList) {
				proteinListStr += "<li><a href=\"#" + protein + "\">" + protein + "</a></li>";
			}
			proteinListStr += "</ul>";
		}
		return proteinListStr;
	}

	@RequestMapping(value = "/prediction/detailView/job/{jobId}/rule/{ruleId}", method = RequestMethod.GET)
	public String getPredictionDetailViewByRule(@PathVariable String jobId, @PathVariable String ruleId, Model model, HttpServletRequest request) {
		model.addAttribute("jobId", jobId);
		model.addAttribute("ruleId", ruleId);
		model.addAttribute("proteinList", getProteinListByRule(jobId, ruleId));
		model.addAttribute("proteinDetailView", getProteinDetailViewByRule(jobId, ruleId, request));
		return "prediction/ruleCentricView";
	}

	@RequestMapping(value = "/prediction/detailView/job/{jobId}/protein/{proteinId}", method = RequestMethod.GET)
	public String getPredictionDetailViewByProtein(@PathVariable String jobId, @PathVariable String proteinId, Model model, HttpServletRequest request) {
		model.addAttribute("jobId", jobId);
		model.addAttribute("proteinId", proteinId);

		model.addAttribute("proteinDetailView", getProteinDetailViewByAllRules(jobId, proteinId, request));

		return "prediction/proteinCentricView";
	}

	private String getProteinDetailViewByAllRules(String jobId, String proteinId, HttpServletRequest request) {
		String detailView = "";
		String cwd = env.getProperty("currentWorkingDir");
		String fileSeparator = System.getProperty("file.separator");
		String jobDir = cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId;
		String tsvReportFile = jobDir + fileSeparator + "outputDir" + fileSeparator + "prediction" + fileSeparator + "pirsr_prediction.tsv";
		String proteinSeqFile = jobDir + fileSeparator + "outputDir" + fileSeparator + "iprscan" + fileSeparator + "seq" + fileSeparator
				+ "IPRScanMatchedProteinSeqs.fasta";

		List<PredictionTSVReport> reports = parsePredictionTSVReport(jobId, tsvReportFile);

		// List<String> proteinList = new ArrayList<String>();
		// for (PredictionTSVReport report : reports) {
		// if (!proteinList.contains(report.getProteinId())) {
		// proteinList.add(report.getProteinId());
		// }
		// }
		// for (String protein : proteinList) {
		detailView += getProteinDetailView(proteinId, reports, proteinSeqFile, request);
		// }
		// System.out.println("I am here");
		return detailView;
	}

	private String getProteinDetailViewByRule(String jobId, String ruleId, HttpServletRequest request) {
		String detailView = "";
		String cwd = env.getProperty("currentWorkingDir");
		String fileSeparator = System.getProperty("file.separator");
		String jobDir = cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId;
		String tsvReportFile = jobDir + fileSeparator + "outputDir" + fileSeparator + "prediction" + fileSeparator + ruleId + fileSeparator + ruleId
				+ "_report.tsv";
		// jobDir/20150704/201507040826209337496634/outputDir/iprscan/seq/IPRScanMatchedProteinSeqs.fasta
		String proteinSeqFile = jobDir + fileSeparator + "outputDir" + fileSeparator + "iprscan" + fileSeparator + "seq" + fileSeparator
				+ "IPRScanMatchedProteinSeqs.fasta";
		List<PredictionTSVReport> reports = parsePredictionTSVReport(jobId, tsvReportFile);
		List<String> proteinList = new ArrayList<String>();
		for (PredictionTSVReport report : reports) {
			if (!proteinList.contains(report.getProteinId())) {
				proteinList.add(report.getProteinId());
			}
		}
		for (String protein : proteinList) {
			detailView += getProteinDetailView(protein, reports, proteinSeqFile, request);
		}

		return detailView;
	}

	private String getProteinDetailView(String proteinId, List<PredictionTSVReport> reports, String proteinSeqFile, HttpServletRequest request) {
		String nucleotideLink = getNucleotideLink(reports, proteinId, request);
		String detailView = "<fieldSet><a name=\"" + proteinId + "\"></a>";
		if (nucleotideLink.length() > 0) {
			detailView += "<legend style=\"font-size: 150%; font-weight: bold;\">" + proteinId + " (" + nucleotideLink + ")" + "</legend>";
		} else {
			detailView += "<legend style=\"font-size: 150%; font-weight: bold;\">" + proteinId + "</legend>";
		}

		String keywords = "";
		String comments = "";
		String features = "";
		List<String> keywordList = new ArrayList<String>();
		Map<String, List<String>> commentMap = new HashMap<String, List<String>>();
		for (PredictionTSVReport report : reports) {
			if (report.getProteinId().equals(proteinId)) {
				// String ruleURL =
				// request.getRequestURI().replace("detailView",
				// "ruleView");
				String[] fields = request.getRequestURI().split("detailView");
				String ruleURL = fields[0] + "ruleView" + "/job/" + report.getJobId() + "/rule/" + report.getPirsrId();

				String sourceLink = "<a data-toggle=\"popover\" data-content=\"source: <a href='"
						+ ruleURL
						+ "'>"
						+ report.getPirsrId()
						+ "</a>\">"
						+ "<img src=\"../../../../../resources/images/info.png\" style=\"width: 10px; vertical-align: top;\" th:src=\"@{/resources/images/info.png}\" />"
						+ "</a>";
				if (report.getType().equals("Keyword")) {
					keywordList.add(report.getDescription() + " " + sourceLink);
				}
				if (report.getType().equals("Comment")) {
					String category = report.getCategory();
					String desc = report.getDescription();
					List<String> commentDescs = commentMap.get(category);
					if (commentDescs == null) {
						commentDescs = new ArrayList<String>();
					}
					commentDescs.add(desc + " " + sourceLink);
					commentMap.put(category, commentDescs);
				}
				if (report.getType().equals("Feature")) {
					if (features.length() == 0) {
						features += "<h4>Features</h4>";
						features += "<table class=\"table table-bordered table-nonfluid table-striped\">";
						features += "<tr><th>Feature key</th><th>Position(s)</th><th>Description</th></tr>";
					}

					features += "<tr><td width=\"30%\">" + report.getCategory() + " " + sourceLink + "" + "</td><td width=\"30%\">" + report.getStart() + "-"
							+ report.getEnd() + "</td>";
					if (report.getDescription() != null && report.getDescription().length() > 0) {
						features += "<td width=\"40\">" + report.getDescription() + "</td>";
					} else {
						features += "<td>&nbsp;</td>";
					}
					features += "</tr>";
				}
			}

		}
		features += "</table>";
		if (keywordList.size() > 0) {
			keywords += "<h4>Keywords</h4>";
			keywords += "<ul>";
			for (String keyword : keywordList) {
				keywords += "<li>" + keyword + "</li>";
			}
			keywords += "</ul>";
		}
		if (commentMap.keySet().size() > 0) {
			for (String category : commentMap.keySet()) {
				comments += "<h4>" + category + "</h4>";
				List<String> commentsByCategory = commentMap.get(category);
				if (commentsByCategory.size() > 0) {
					comments += "<ul>";
					for (String comment : commentMap.get(category)) {
						comments += "<li>" + comment + "</li>";
					}
				}
			}
			comments += "</ul>";
		}
		detailView += features;
		detailView += comments;
		detailView += keywords;
		detailView += getSequenceView(proteinId, proteinSeqFile);
		detailView += "</fieldSet><br/><br/>";
		return detailView;
	}

	private String getNucleotideLink(List<PredictionTSVReport> reports, String proteinId, HttpServletRequest request) {
		String finalLink = "";
		for (PredictionTSVReport report : reports) {
			if (report.getProteinId().equals(proteinId)) {
				if (report.getNucleotideId() != null && !report.getNucleotideId().equals(".")) {
					// System.out.println("|"+report.getNucleotideId()+"|");
					String[] fields = request.getRequestURI().split("detailView");
					String nuclURL = fields[0] + "detailView" + "/job/" + report.getJobId() + "/nucleotide/" + report.getNucleotideId();
					String link = "<a href=\"" + nuclURL + "\">" + report.getNucleotideId() + "</a>: " + report.getNucleotideORFStart() + "-"
							+ report.getNucleotideORFEnd();
					if (report.getNucleotideORFStrand().equals("SENSE")) {
						link += " [+]";
					} else {
						link += " [-]";
					}
					if (!finalLink.contains(link)) {
						finalLink += link;
					}
				}
			}
		}
		return finalLink;
	}

	private String getSequenceView(String proteinId, String proteinSeqFile) {
		String proteinSeqView = "<h4>Sequence</h4>";
		File file = new File(proteinSeqFile);
		Map<String, String> seqMap = new HashMap<String, String>();
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line;
			String seqId = "";

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				if (line.startsWith(">")) {
					String[] fields = line.split("\t");
					seqId = fields[0].replaceAll(">", "");
				} else {
					// String seq = seqMap.get(seqId);
					// if (seq == null) {
					// seqMap.put(seqId, line);
					// } else {
					// seqMap.put(seqId, seq + line);
					// }
					seqMap.put(seqId, line);
				}
			}

			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String seq = seqMap.get(proteinId);
		proteinSeqView += "<b>Length:</b> " + seq.length() + "; ";
		proteinSeqView += "<b>MD5:</b> " + this.createMD5Sum(seq) + "<br/>";
		// String[] seqArray = seq.split("");
		proteinSeqView += "<pre style=\"background-color: transparent;\">";
		String[] seqArray = null;
		seqArray = seq.split("(?<=\\G.{10})");

		// for(int i=0; i<seqArray.length; i++)
		// System.out.print(seqArray[i]+" ");
		// System.out.println();
		proteinSeqView += getSeqView(seqArray) + "</pre>";
		return proteinSeqView;
	}

	private String getSeqView(String[] seqArray) {
		String view = "";
		String number = "";
		String seq = "";
		int sum = 0;
		for (int i = 0; i < seqArray.length; i++) {
			sum += seqArray[i].length();
			number += padLeftSpaces(Integer.toString(sum), 10) + " ";
			seq += seqArray[i] + " ";
			if (sum % 80 == 0) {
				view += number + "\n";
				view += seq + "\n";
				number = "";
				seq = "";
			}
		}
		view += number.replace(padLeftSpaces(Integer.toString(sum), 10), "") + "\n";
		view += seq + "\n";

		return view;
	}

	private String padLeftSpaces(String str, int n) {
		return String.format("%1$" + n + "s", str);
	}

	private String createMD5Sum(String input) {

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");

			md.update(input.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String getProteinListByRule(String jobId, String ruleId) {

		String cwd = env.getProperty("currentWorkingDir");
		String fileSeparator = System.getProperty("file.separator");
		String jobDir = cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId;
		String tsvReportFile = jobDir + fileSeparator + "outputDir" + fileSeparator + "prediction" + fileSeparator + ruleId + fileSeparator + ruleId
				+ "_report.tsv";
		List<PredictionTSVReport> reports = parsePredictionTSVReport(jobId, tsvReportFile);

		List<String> proteinList = new ArrayList<String>();
		for (PredictionTSVReport report : reports) {
			if (!proteinList.contains(report.getProteinId())) {
				proteinList.add(report.getProteinId());
			}
		}

		String proteinListStr = "";
		if (proteinList.size() > 0) {
			proteinListStr += "<br/><h4>Proteins</h4><ul>";
			for (String protein : proteinList) {
				proteinListStr += "<li><a href=\"#" + protein + "\">" + protein + "</a></li>";
			}
			proteinListStr += "</ul>";
		}
		return proteinListStr;
	}

	@RequestMapping(value = "/prediction/ruleView/job/{jobId}/rule/{ruleId}", method = RequestMethod.GET)
	public String getPredictionRuleView(@PathVariable String jobId, @PathVariable String ruleId, Model model) {
		model.addAttribute("jobId", jobId);
		model.addAttribute("ruleId", ruleId);
		String ruleHTMLView = getRuleHTMLView(jobId, ruleId);
		model.addAttribute("ruleHTMLView", ruleHTMLView);
		return "prediction/rule";
	}

	private String getRuleHTMLView(String jobId, String ruleId) {
		String cwd = env.getProperty("currentWorkingDir");
		String pirunirulejar = env.getProperty("pirunirulejar");
		String fileSeparator = System.getProperty("file.separator");
		String jobDir = cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId;
		// outputDir/prediction/PIRSR036665-50/PIRSR036665-50.uru
		String ruleFile = jobDir + fileSeparator + "outputDir" + fileSeparator + "prediction" + fileSeparator + ruleId + fileSeparator + ruleId + ".uru";

		String cmd = "java -jar " + pirunirulejar + " -a printRule  -F HTML -f " + ruleFile;

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();
	}

	@RequestMapping(value = "/prediction/retrieveForm", method = RequestMethod.GET)
	public String retrievePredictionResults(@Valid @ModelAttribute("jobId") String jobId, final BindingResult bindingResult, final ModelMap model) {
		model.addAttribute("jobId", jobId);
		return "prediction/retrieveForm";
	}
	
	@RequestMapping(value = "/prediction/retrieve", method = RequestMethod.GET)
	public String retrievePredictionResultsWithoutID(@Valid @ModelAttribute("jobId") String jobId, final BindingResult bindingResult, final ModelMap model) {
		model.addAttribute("jobId", jobId);
		return "prediction/retrieveForm";
	}

	@RequestMapping(value = "/prediction/retrieve/{jobId}", method = { RequestMethod.GET })
	public String getPredictionResults(@PathVariable String jobId, Model model) {
		// System.out.println(jobId);
		model.addAttribute("jobId", jobId);
		String cwd = env.getProperty("currentWorkingDir");
		String fileSeparator = System.getProperty("file.separator");
		String jobDir = cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId;

		String tsvFile = jobDir + fileSeparator + "outputDir/prediction/pirsr_prediction.tsv";
		System.out.println(tsvFile);
		File tsv = new File(tsvFile);
		if (tsv.exists() && tsv.length() > 0) {
			String resutls = "";
			List<PredictionTSVReport> tsvReports = new ArrayList<PredictionTSVReport>();
			if (tsv.exists() && tsv.length() > 0) {
				FileInputStream fis;
				try {
					fis = new FileInputStream(tsv);

					// Construct BufferedReader from InputStreamReader
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					// PIRSRID PeptideID/ProteinID Start End Type Category
					// Description
					// NucleotideID NucleotideORFStart NucleotideORFEnd
					// ORFStrand

					String line = null;
					while ((line = br.readLine()) != null) {
						if (!line.startsWith("PIRSRID")) {
							// System.out.println(line);
							String[] fields = line.split("\t");
							// System.out.println(fields.length);
							PredictionTSVReport tsvReport = new PredictionTSVReport();
							tsvReport.setPirsrId(fields[0]);
							tsvReport.setProteinId(fields[1]);
							tsvReport.setStart(fields[2]);
							tsvReport.setEnd(fields[3]);
							tsvReport.setType(fields[4]);
							tsvReport.setCategory(fields[5]);
							tsvReport.setDescription(fields[6]);

							tsvReport.setNucleotideId(fields[7]);
							tsvReport.setNucleotideORFStart(fields[8]);
							tsvReport.setNucleotideORFEnd(fields[9]);
							tsvReport.setNucleotideORFStrand(fields[10]);

							tsvReports.add(tsvReport);
						}
						resutls += line + "\n";
					}
					br.close();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			model.addAttribute("results", "<pre>" + resutls + "</pre>");
			model.addAttribute("tsvReports", tsvReports);
			String reldateFile = jobDir + fileSeparator + "reldate.txt";
			String reldate = readFileContent(reldateFile);
			model.addAttribute("release", reldate);
		} else {
			model.addAttribute("jobIdError", "Invalid Job ID.");
		}
		return "prediction/retrieve";
	}

	@RequestMapping(value = "/prediction/retrieve", method = RequestMethod.POST)
	public String retrievePredictionResults(@RequestParam("jobId") String jobId, Model model) {
		if (jobId == null || jobId.length() == 0) {
			model.addAttribute("jobIdError", "Job ID is required.");
			model.addAttribute("jobId", jobId);
			return "prediction/retrieveForm";
		} 
		else if(!StringUtils.isNumeric(jobId)) {
			model.addAttribute("jobIdError", "Invalid Job ID.");
			model.addAttribute("jobId", jobId);
			return "prediction/retrieveForm";
		}
		else if(StringUtils.isNumeric(jobId) && jobId.length()<8) {
			model.addAttribute("jobIdError", "Invalid Job ID.");
			model.addAttribute("jobId", jobId);
			return "prediction/retrieveForm";
		}
		else {

			model.addAttribute("jobId", jobId);
			String cwd = env.getProperty("currentWorkingDir");
			String fileSeparator = System.getProperty("file.separator");
			String jobDir = cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId;

			String tsvFile = jobDir + fileSeparator + "outputDir/prediction/pirsr_prediction.tsv";
			File tsv = new File(tsvFile);
			// System.out.println(tsvFile);
			String resutls = "";
			List<PredictionTSVReport> tsvReports = new ArrayList<PredictionTSVReport>();
			if (tsv.exists() && tsv.length() > 0) {
				FileInputStream fis;

				try {
					fis = new FileInputStream(tsv);
					// Construct BufferedReader from InputStreamReader
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));

					String line = null;
					while ((line = br.readLine()) != null) {
						if (!line.startsWith("PIRSRID")) {
							String[] fields = line.split("\t");
							PredictionTSVReport tsvReport = new PredictionTSVReport();
							tsvReport.setPirsrId(fields[0]);
							tsvReport.setProteinId(fields[1]);
							if (fields[2].equals("?")) {
								tsvReport.setStart("");
							} else {
								tsvReport.setStart(fields[2]);
							}
							if (fields[3].equals("?")) {
								tsvReport.setEnd("");
							} else {
								tsvReport.setEnd(fields[3]);
							}
							tsvReport.setType(fields[4]);
							if (fields[5].equals(".")) {
								tsvReport.setCategory("");
							} else {
								tsvReport.setCategory(fields[5]);
							}
							if (fields[6].equals(".")) {
								tsvReport.setDescription("");
							} else {
								tsvReport.setDescription(fields[6]);
							}

							if (fields[7].equals(".")) {
								tsvReport.setNucleotideId("");
							} else {
								tsvReport.setNucleotideId(fields[7]);
							}

							if (fields[8].equals(".")) {
								tsvReport.setNucleotideORFStart("");
							} else {
								tsvReport.setNucleotideORFStart(fields[8]);
							}

							if (fields[9].equals(".")) {
								tsvReport.setNucleotideORFEnd("");
							} else {
								tsvReport.setNucleotideORFEnd(fields[9]);
							}

							if (fields[10].equals(".")) {
								tsvReport.setNucleotideORFStrand("");
							} else {
								tsvReport.setNucleotideORFStrand(fields[10]);
							}
							tsvReports.add(tsvReport);
						}
						resutls += line + "\n";
					}
					br.close();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// model.addAttribute("results", "<pre>" + results + "</pre>");
			if (tsvReports.size() > 0) {
				model.addAttribute("tsvReports", tsvReports);
				String reldateFile = jobDir + fileSeparator + "reldate.txt";
				String reldate = readFileContent(reldateFile);
				model.addAttribute("release", reldate);
			} else {
				model.addAttribute("jobIdError", "Invalid Job ID.");
				return "prediction/retrieveForm";
			}

			// }
		}

		return "prediction/retrieve";
	}

	@RequestMapping(value = "/prediction/jobdetails/{jobId}", method = RequestMethod.GET)
	public @ResponseBody String getPredictionJobDetails(@PathVariable String jobId, Model model, HttpServletRequest request, HttpServletResponse response) {
		String details = "";
		String logs = "";
		String cwd = env.getProperty("currentWorkingDir");
		// String dataDir = env.getProperty("dataDir");
		// String hmmsearch = env.getProperty("hmmsearch");
		// String hmmalign = env.getProperty("hmmalign");
		String fileSeparator = System.getProperty("file.separator");

		String logFile = cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId + fileSeparator + "run.log";
		String jobDir = cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId;
		FileInputStream fis;
		File log = new File(logFile);
		boolean finished = false;
		if (log.exists()) {
			try {
				fis = new FileInputStream(log);

				// Construct BufferedReader from InputStreamReader
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));

				String line = null;
				while ((line = br.readLine()) != null) {
					// System.out.println(line);
					if ((line.contains("Prediction results in XML format") || line.contains("Prediction results in TSV format") || line
							.contains("Prediction results in GFF3 format"))) {
					} else {

						if (line.indexOf("Prediction is finished") != -1) {
							finished = true;
							// System.out.println(line);
						}
						logs = makeLineShort(line, jobDir) + "<br/>" + logs;
					}
				}

				br.close();
				// logs += "</pre>";
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String retrievalURL = null;
		if (finished) {
			String jobTitleFile = jobDir + fileSeparator + "jobTitle.txt";
			String jobTitle = readFileContent(jobTitleFile);
			retrievalURL = request.getContextPath().toString() + "/prediction/retrieve/" + jobId;
			details += "<p>Prediction job [" + jobTitle + "] is finished. Please follow this <a href=\"" + retrievalURL
					+ "\">link</a> to view the resutls.<br/><br/></p>";

			String userEmailFile = jobDir + fileSeparator + "userEmail.txt";
			String userEmailFileSent = jobDir + fileSeparator + "userEmailSent.txt";
			File emailFile = new File(userEmailFile);
			File emailFileSent = new File(userEmailFileSent);
			if (emailFile.exists() && !emailFileSent.exists()) {
				String userEmail = readFileContent(userEmailFile);
				retrievalURL = env.getProperty("siteBaseURL") + "/prediction/retrieve/" + jobId;
				sendNotificationEmail(userEmail, jobId, jobTitle, retrievalURL);
				writeToFile(userEmailFileSent, userEmail, false);
			}

		}
		details += logs;
		return details;
	}

	private void sendNotificationEmailAWS(String userEmail, String jobId, String jobTitle, String retrievalURL) {
		// "From" address and name. This address must be verified.
		String FROM = env.getProperty("adminEmail");
		String FROMNAME = env.getProperty("adminName");

		// "To" address. If your account is still in the sandbox, this address
		// must be verified.
		String TO = userEmail;

		// Your Amazon SES SMTP user name.
		String SMTP_USERNAME = env.getProperty("awsSesSmtpUserName");

		// Your Amazon SES SMTP password.
		String SMTP_PASSWORD = env.getProperty("awsSesSmtpPassword");

		// The name of the Configuration Set to use for this message.
		// If you comment out or remove this variable, you will also need to
		// comment out or remove the header below.
		String CONFIGSET = "ConfigSet";

		// Amazon SES SMTP host name. This example uses the US West (Oregon)
		// region.
		// See
		// http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
		// for more information.
		String HOST = env.getProperty("awsSesSmtpHost");

		// The port you will connect to on the Amazon SES SMTP endpoint.
		int PORT = 587;

		String SUBJECT = jobTitle;

		// Create a Properties object to contain connection configuration
		// information.
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		String BODY = "";
		BODY += "Dear User: <br/><br/>";
		BODY += "<p>Your prediction job [" + jobTitle.trim() + "] is finished. Please follow this <a href=\"" + retrievalURL
				+ "\">link</a> to view the resutls.<br/><br/></p>";
		BODY += "<p>Best,<br/><br/>PIRSitePredict";
		try {
			// Create a Session object to represent a mail session with the
			// specified properties.
			Session session = Session.getDefaultInstance(props);

			// Create a message with the specified information.
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(FROM, FROMNAME));

			//message.addRecipients(Message.RecipientType.CC, InternetAddress.parse("abc@abc.com,abc@def.com,ghi@abc.com"));
			//msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(TO));
			msg.setSubject(SUBJECT);
			msg.setContent(BODY, "text/html");

			// Add a configuration set header. Comment or delete the
			// next line if you are not using a configuration set
			//msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

			// Create a transport.
			Transport transport = session.getTransport();

			// Send the message.
			try {
				System.out.println("Sending...");

				// Connect to Amazon SES using the SMTP username and password
				// you specified above.
				transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

				// Send the email.
				transport.sendMessage(msg, msg.getAllRecipients());
				System.out.println("Email sent!");
			} catch (Exception ex) {
				System.out.println("The email was not sent.");
				System.out.println("Error message: " + ex.getMessage());
			} finally {
				// Close and terminate the connection.
				transport.close();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendNotificationEmail(String userEmail, String jobId, String jobTitle, String retrievalURL) {
		System.out.println(retrievalURL);
		// Recipient's email ID needs to be mentioned.
		String to = userEmail;

		// Sender's email ID needs to be mentioned
		// String from = "pirsitepredict@proteininformationresource.org";
		String from = env.getProperty("adminEmail");
		// Assuming you are sending email from localhost
		// String host = "newmail.dbi.udel.edu";
		String host = env.getProperty("smtpHost");

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			// message.addRecipient(Message.RecipientType.TO,
			// new InternetAddress(to));
			message.setRecipients(Message.RecipientType.TO, to);
			if (jobTitle != null && jobTitle.length() > 0) {
				message.setSubject("PIRSitePredict Job: " + jobId + " - " + jobTitle.trim());
			} else {
				message.setSubject("PIRSitePredict Job: " + jobId);
			}

			// Send the actual HTML message, as big as you like
			String mailContent = "";
			mailContent += "Dear User: <br/><br/>";
			mailContent += "<p>Your prediction job [" + jobTitle.trim() + "] is finished. Please follow this <a href=\"" + retrievalURL
					+ "\">link</a> to view the resutls.<br/><br/></p>";
			mailContent += "<p>Best,<br/><br/>PIRSitePredict";
			message.setContent(mailContent, "text/html");

			// Send message
			Transport.send(message);
			// System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}

	// private void sendNotificationEmail1(String userEmail, String jobId,
	// String retrievalURL) {
	//
	// // String SMPT_HOSTNAME = "smtp.gmail.com";
	// final String USERNAME = "";
	// final String PASSWORD = "";
	//
	// // Recipient's email ID needs to be mentioned.
	// String to = userEmail;
	//
	// // Sender's email ID needs to be mentioned
	// String from = "pirsitepredict@proteininformationresource.org";
	//
	// // Assuming you are sending email from localhost
	// // String host = "localhost";
	//
	// // Get system properties
	// // Properties properties = System.getProperties();
	// //
	// // // Setup mail server
	// // properties.setProperty("mail.smtp.host", SMPT_HOSTNAME);
	//
	// Properties props = new Properties();
	// props.put("mail.smtp.starttls.enable", "true");
	// props.put("mail.smtp.auth", "true");
	// props.put("mail.smtp.host", "smtp.gmail.com");
	// props.put("mail.smtp.port", "587");
	//
	// // Get the default Session object.
	// // Session session = Session.getDefaultInstance(properties);
	// // create a session with an Authenticator
	// Session session = Session.getInstance(props, new Authenticator() {
	// @Override
	// protected PasswordAuthentication getPasswordAuthentication() {
	// return new PasswordAuthentication(USERNAME, PASSWORD);
	// }
	// });
	//
	// try {
	// // Create a default MimeMessage object.
	// MimeMessage message = new MimeMessage(session);
	//
	// // Set From: header field of the header.
	// message.setFrom(new InternetAddress(from));
	//
	// // Set To: header field of the header.
	// message.addRecipient(Message.RecipientType.TO, new InternetAddress(
	// to));
	//
	// // Set Subject: header field
	// message.setSubject("PIRSitePredict prediction results are ready (Job ID: "
	// + jobId + ")");
	//
	// // Send the actual HTML message, as big as you like
	// String mailContent = "";
	// mailContent += "Dear User: <br/><br/>";
	// mailContent +=
	// "<p>Your prediction job is finished. Please follow this <a href=\""
	// + retrievalURL
	// + "\">link</a> to view the resutls.<br/><br/></p>";
	// mailContent += "<p>Best,<br/><br/>PIRSitePredict";
	// message.setContent(mailContent, "text/html");
	//
	// // Send message
	// Transport.send(message);
	// //System.out.println("Sent message successfully....");
	// } catch (MessagingException mex) {
	// mex.printStackTrace();
	// }
	//
	// }

	private String makeLineShort(String line, String jobDir) {
		String cwd = env.getProperty("currentWorkingDir");
		String dataDir = env.getProperty("dataDir");
		String hmmsearch = env.getProperty("hmmsearch");
		String hmmalign = env.getProperty("hmmalign");

		return line.replaceAll(jobDir, "jobDir").replaceAll(dataDir, "dataDir").replaceAll(hmmsearch, "hmmsearch").replaceAll(hmmalign, "hmmalign");
	}

	// private void runPrediction(String jobId, String cwd, String data,
	// String hmmsearch, String hmmalign, PredictionForm form) {
	// // TODO Auto-generated method stub
	//
	// }

	private static void writeToFile(String file, String content, boolean append) {
		FileWriter fw;
		try {
			fw = new FileWriter(file, append);

			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<PredictionTSVReport> parsePredictionTSVReport(String jobId, String tsvReportFile) {
		List<PredictionTSVReport> reports = new ArrayList<PredictionTSVReport>();
		File file = new File(tsvReportFile);
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split("\t");
				if (fields != null && fields.length > 0) {
					PredictionTSVReport report = new PredictionTSVReport();
					report.setJobId(jobId);
					if (fields[0] != null && fields[0].length() > 0) {
						report.setPirsrId(fields[0]);
					}
					if (fields[1] != null && fields[1].length() > 0) {
						report.setProteinId(fields[1]);
					}
					if (fields[2] != null && fields[2].length() > 0) {
						report.setStart(fields[2]);
					}
					if (fields[3] != null && fields[3].length() > 0) {
						report.setEnd(fields[3]);
					}
					if (fields[4] != null && fields[4].length() > 0) {
						report.setType(fields[4]);
					}
					if (fields[5] != null && fields[5].length() > 0) {
						report.setCategory(fields[5]);
					}
					if (fields[6] != null && fields[6].length() > 0) {
						report.setDescription(fields[6]);
					}
					if (fields[7] != null && fields[7].length() > 0) {
						report.setNucleotideId(fields[7]);
					}
					if (fields[8] != null && fields[8].length() > 0) {
						report.setNucleotideORFStart(fields[8]);
					}
					if (fields[9] != null && fields[9].length() > 0) {
						report.setNucleotideORFEnd(fields[9]);
					}
					if (fields[10] != null && fields[10].length() > 0) {
						report.setNucleotideORFStrand(fields[10]);
					}
					reports.add(report);

				}
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reports;
	}

	private static String readFileContent(String filePath) {
		File file = new File(filePath);
		StringBuffer stringBuffer = new StringBuffer();
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

	private String createJobId(HttpServletRequest request) {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		long number = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
		String jobId = timeStamp + "" + number/* randomGenerator(8) */;
		// System.out.println(timeStamp + " | " + number);
		return jobId;
	}

	private int randomGenerator(int length) {

		Random r = new Random();

		String number = "";

		int counter = 0;

		while (counter++ < length)
			number += r.nextInt(9);

		return Integer.parseInt(number);

	}

	private long ipToLong(String ipAddress) {
		// ipAddressInArray[0] = 192
		String[] ipAddressInArray = ipAddress.split("\\.");

		long result = 0;
		for (int i = 0; i < ipAddressInArray.length; i++) {

			int power = 3 - i;
			int ip = Integer.parseInt(ipAddressInArray[i]);

			// 1. 192 * 256^3
			// 2. 168 * 256^2
			// 3. 1 * 256^1
			// 4. 2 * 256^0
			result += ip * Math.pow(256, power);
		}
		return result;
	}

	private String longToIp(long i) {

		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);

	}

	private String saveUploadedFile(MultipartFile interProScanXMLFileUploaded, File jobDir, String jobId) {
		String interProScanXMLFileUploadedPath = null;
		try {

			// Create the file on server
			File serverFile = new File(jobDir + File.separator + jobId + ".xml");

			interProScanXMLFileUploadedPath = serverFile.getAbsolutePath();
			interProScanXMLFileUploaded.transferTo(serverFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return interProScanXMLFileUploadedPath;
	}

	// private String saveUploadedFile1(MultipartFile
	// interProScanXMLFileUploaded,
	// File jobDir, String jobId) {
	// String interProScanXMLFileUploadedPath = null;
	// try {
	// byte[] bytes = interProScanXMLFileUploaded.getBytes();
	// System.out.println("Upload file in bytes: "+bytes.length);
	// // Create the file on server
	// File serverFile = new File(jobDir + File.separator + jobId + ".xml");
	//
	// BufferedOutputStream stream = new BufferedOutputStream(
	// new FileOutputStream(serverFile));
	// stream.write(bytes);
	// interProScanXMLFileUploadedPath = serverFile.getAbsolutePath();
	// stream.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return interProScanXMLFileUploadedPath;
	// }

	private BindingResult validateForm(PredictionForm form, MultipartFile interProScanXMLFileUploaded, BindingResult bindingResult)
			throws MaxUploadSizeExceededException {
		if (interProScanXMLFileUploaded == null) {
			ObjectError error = new ObjectError("inputFile", "An InterProScan XML file is required.");
			bindingResult.addError(error);
		} else if (interProScanXMLFileUploaded.getOriginalFilename().equals("")) {
			ObjectError error = new ObjectError("inputFile", "An InterProScan XML file is required.");
			bindingResult.addError(error);
		} else if (interProScanXMLFileUploaded.getSize() == 0) {
			ObjectError error = new ObjectError("inputFile", "InterProScan XML file is empty.");
			bindingResult.addError(error);
		}
		// System.out.println(interProScanXMLFileUploaded.getOriginalFilename());
		// System.out.println(interProScanXMLFileUploaded.getName());
		// System.out.println(interProScanXMLFileUploaded.getSize());

		// if(form.getCheckedOutputFormats() == null) {
		// ObjectError error = new ObjectError("outputFormat",
		// "Please select at least one of the ouput formats.");
		// bindingResult.addError(error);
		// }
		if (form.getEValue() == null || form.getEValue().equals("")) {
			ObjectError error = new ObjectError("EValue", "Please specify HMMER E-value cutoff.");
			bindingResult.addError(error);
		} else {
			if (Float.parseFloat(form.getEValue()) < 0) {
				ObjectError error = new ObjectError("EValue", "E-value must be >=0");
				bindingResult.addError(error);
			}
		}
		return bindingResult;
	}

	@RequestMapping(value = "/prediction/ajax/reports/{reportId}", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<PredictionTSVReport> findAllForDataTablesFullSpring(@PathVariable("reportId") String reportId,
			@DatatablesParams DatatablesCriterias criterias) {
		// System.out.println(reportId);
		DataSet<PredictionTSVReport> dataSet = reportService.findPredictionTSVReportsWithDatatablesCriterias(reportId, criterias);
		return DatatablesResponse.build(dataSet, criterias);
	}

	@RequestMapping(value = "/prediction/ajax/export/{jobId}/gff3", method = RequestMethod.GET)
	public void downloadFilteredGFF3Report(@PathVariable("jobId") String jobId, @DatatablesParams DatatablesCriterias criterias, HttpServletResponse response) {
		String fileSeparator = System.getProperty("file.separator");
		String cwd = env.getProperty("currentWorkingDir");
		File jobDir = new File(cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId);

		List<PredictionTSVReport> reports = reportRepository.findPredictionTSVReportWithDatatablesCriterias(jobId, criterias);
		Map<String, List<PredictionTSVReport>> ruleMap = new LinkedHashMap<String, List<PredictionTSVReport>>();
		List<String> landmarksNucl = new ArrayList<String>();
		StringBuffer nucls = new StringBuffer();
		StringBuffer pros = new StringBuffer();
		List<String> landmarksPro = new ArrayList<String>();
		for (PredictionTSVReport report : reports) {
			if (report.getNucleotideId() != null) {
				if (!landmarksNucl.contains(report.getNucleotideId())) {
					landmarksNucl.add(report.getNucleotideId());
				}
			}
			if (report.getProteinId() != null) {
				if (!landmarksPro.contains(report.getProteinId())) {
					landmarksPro.add(report.getProteinId());
				}
			}
			String pirsrId = report.getPirsrId();
			if (ruleMap.get(pirsrId) == null) {
				List<PredictionTSVReport> filteredReportByRule = new ArrayList<PredictionTSVReport>();
				filteredReportByRule.add(report);
				ruleMap.put(pirsrId, filteredReportByRule);
			} else {
				List<PredictionTSVReport> filteredReportByRule = ruleMap.get(pirsrId);
				filteredReportByRule.add(report);
				ruleMap.put(pirsrId, filteredReportByRule);
			}
		}
		StringBuffer gff3 = new StringBuffer();
		String gff3ReportFile = jobDir + fileSeparator + "outputDir" + fileSeparator + "prediction" + fileSeparator + "pirsr_prediction.gff3";
		gff3.append(getFilteredGff3(gff3ReportFile, landmarksNucl, landmarksPro, reports));

		// gff3.append("##gff-version 3\n");
		// gff3.append("##feature-ontology http://song.cvs.sourceforge.net/viewvc/song/ontology/sofa.obo?revision=1.275\n");
		// gff3.append("##seqid|source|type|start|end|score|strand|phase|attributes\n");

		response.setContentType("text/plain");
		response.setContentLength((int) gff3.toString().length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + jobId + "_report.gff3\"");

		try {
			response.getWriter().write(gff3.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getFilteredGff3(String gff3ReportFile, List<String> landmarksNucl, List<String> landmarksPro, List<PredictionTSVReport> reports) {
		FileReader fileReader;
		StringBuffer gff3 = new StringBuffer();
		try {
			fileReader = new FileReader(gff3ReportFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			String seqId = null;
			boolean fastaSeqStart = false;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("##gff-version 3")) {
					gff3.append(line + "\n");
				} else if (line.startsWith("##feature-ontology")) {
					gff3.append(line + "\n");
				} else if (line.startsWith("##seqid|source|type|start|end|score|strand|phase|attributes")) {
					gff3.append(line + "\n");
				} else if (line.startsWith("##FASTA")) {
					fastaSeqStart = true;
					gff3.append(line + "\n");
				} else if (line.startsWith("##")) {
					String[] fields = line.split(" ");
					String landmark = fields[0].replace("##", "");
					if (landmarksNucl.contains(landmark) || landmarksPro.contains(landmark)) {
						gff3.append(line + "\n");
					}
				} else {
					if (fastaSeqStart) {
						if (line.startsWith(">")) {
							String landmark = line.replace(">", "");
							if (landmarksNucl.contains(landmark) || landmarksPro.contains(landmark)) {
								gff3.append(line + "\n");
								seqId = landmark;
							} else {
								seqId = null;
							}
						} else {
							if (seqId != null) {
								gff3.append(line + "\n");
							}
						}
					} else {
						String[] fields = line.split("\t");
						String type = fields[2];
						String seqid = fields[0];
						if (type.equals("nucleic_acid") || type.equals("ORF") || type.equals("polypeptide")) {
							if (landmarksNucl.contains(seqid) || landmarksPro.contains(seqid)) {
								gff3.append(line + "\n");
							}
						} else {
							if (matchReports(line, reports)) {
								gff3.append(line + "\n");
							}
						}
					}
				}
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gff3.toString();
	}

	private String escapeForGFF3Attribute(String description) {
		/*
		 * ; semicolon (%3B) = equals (%3D) & ampersand (%26) , comma (%2C)
		 */
		return description.replaceAll(";", "%3B").replaceAll("=", "%3D").replaceAll("&", "%26").replaceAll(",", "%2C");
	}

	private boolean matchReports(String line, List<PredictionTSVReport> reports) {
		String[] fields = line.split("\t");
		String type = fields[2];
		String seqid = fields[0];
		String start = fields[3];
		String end = fields[4];
		String attributes = fields[8];
		for (PredictionTSVReport report : reports) {
			if (report.getProteinId().equals(seqid)) {
				if (report.getType().equals("Feature")) {
					if (report.getStart().equals(start) && report.getEnd().equals(end)) {
						if (attributes.contains("rule=" + report.getPirsrId())) {
							return true;
						}
					}
				}
				if (report.getType().equals("Keyword")) {
					if (attributes.contains("keyword=" + escapeForGFF3Attribute(report.getDescription()))) {
						if (attributes.contains("rule=" + report.getPirsrId())) {
							return true;
						}
					}
				}
				if (report.getType().equals("Comment")) {
					if (attributes.contains("comment_" + report.getCategory() + "=" + escapeForGFF3Attribute(report.getDescription()))) {
						if (attributes.contains("rule=" + report.getPirsrId())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@RequestMapping(value = "/prediction/ajax/export/{jobId}/xml", method = RequestMethod.GET)
	public void downloadFilteredXMLReport(@PathVariable("jobId") String jobId, @DatatablesParams DatatablesCriterias criterias, HttpServletResponse response) {
		String fileSeparator = System.getProperty("file.separator");
		String cwd = env.getProperty("currentWorkingDir");
		File jobDir = new File(cwd + fileSeparator + jobId.substring(0, 8) + fileSeparator + jobId);

		List<PredictionTSVReport> reports = reportRepository.findPredictionTSVReportWithDatatablesCriterias(jobId, criterias);

		HashMap<String, List<PredictionTSVReport>> ruleMap = new LinkedHashMap<String, List<PredictionTSVReport>>();
		for (PredictionTSVReport report : reports) {
			String pirsrId = report.getPirsrId();
			if (ruleMap.get(pirsrId) == null) {
				List<PredictionTSVReport> filteredReportByRule = new ArrayList<PredictionTSVReport>();
				filteredReportByRule.add(report);
				ruleMap.put(pirsrId, filteredReportByRule);
			} else {
				List<PredictionTSVReport> filteredReportByRule = ruleMap.get(pirsrId);
				filteredReportByRule.add(report);
				ruleMap.put(pirsrId, filteredReportByRule);
			}
		}
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		xml.append("<pirsr-predictions xmlns=\"http://research.bioinformatics.udel.edu/schemas/PIRSRPredictor\">\n");

		for (String rule : ruleMap.keySet()) {
			String ruleXMLReport = jobDir + fileSeparator + "outputDir" + fileSeparator + "prediction" + fileSeparator + rule + fileSeparator + rule
					+ "_report.xml";
			xml.append(getRuleInfo(ruleXMLReport) + "\n");
			Map<String, List<PredictionTSVReport>> proteinPredictionMap = getProteinPredictionMap(ruleMap.get(rule));
			for (String protein : proteinPredictionMap.keySet()) {
				xml.append(getProteinInfo(ruleXMLReport, protein));
				xml.append("                <predictions>\n");

				for (PredictionTSVReport report : proteinPredictionMap.get(protein)) {
					if (report.getType().equals("Keyword")) {
						xml.append("                   <keyword>" + report.getDescription() + "</keyword>\n");
					}
					if (report.getType().equals("Comment")) {
						xml.append("                  <comment type=\"" + report.getCategory() + "\">\n");
						xml.append("                   <description>\n");
						xml.append("                     " + report.getDescription() + "\n");
						xml.append("                   </description>\n");
						xml.append("                  </comment>\n");
					}
					if (report.getType().equals("Feature")) {
						xml.append("                  <feature type=\"" + report.getCategory() + "\" description=\"" + report.getDescription() + "\">\n");
						xml.append("                   <location>\n");
						xml.append("                     <begin position=\"" + report.getStart() + "\"/>\n");
						xml.append("                     <end position=\"" + report.getEnd() + "\"/>\n");
						xml.append("                   </location>\n");
						xml.append("                  </feature>\n");
					}
				}
				xml.append("                </predictions>\n");
				xml.append("        </protein>\n");
			}
			xml.append(" </pirsr>\n");

		}
		xml.append("</pirsr-predictions>\n");

		response.setContentType("application/xml");
		response.setContentLength((int) xml.toString().length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + jobId + "_report.xml\"");

		try {
			response.getWriter().write(xml.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map<String, List<PredictionTSVReport>> getProteinPredictionMap(List<PredictionTSVReport> reports) {
		Map<String, List<PredictionTSVReport>> proteinPredictionMap = new LinkedHashMap<String, List<PredictionTSVReport>>();
		for (PredictionTSVReport report : reports) {
			String protein = report.getProteinId();
			if (proteinPredictionMap.get(protein) == null) {
				List<PredictionTSVReport> proteinReports = new ArrayList<PredictionTSVReport>();
				proteinReports.add(report);
				proteinPredictionMap.put(protein, proteinReports);
			} else {
				List<PredictionTSVReport> proteinReports = proteinPredictionMap.get(protein);
				proteinReports.add(report);
				proteinPredictionMap.put(protein, proteinReports);
			}
		}
		return proteinPredictionMap;
	}

	private String getProteinInfo(String ruleXMLReport, String protein) {
		// System.out.println(ruleXMLReport + " " + protein);
		FileReader fileReader;
		String proteinInfo = "";
		try {
			fileReader = new FileReader(ruleXMLReport);

			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			boolean found = false;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains("<protein id=\"" + protein + "\"")) {
					// System.out.println(line);
					// System.out.println("        <protein id=\""+protein+"\"");
					proteinInfo += line + "\n";
					found = true;
				} else {
					if (found && line.contains("<predictions")) {
						found = false;
					}
					if (found) {
						proteinInfo += line + "\n";
					}
				}
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return proteinInfo;
	}

	private String getRuleInfo(String ruleXMLReport) {
		FileReader fileReader;
		String ruleInfo = "";
		try {
			fileReader = new FileReader(ruleXMLReport);

			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("  <pirsr id=")) {
					ruleInfo = line;
				}
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ruleInfo;
	}

	private Map readSeqs(String file) {
		HashMap<String, String> seqMap = null;
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);

			BufferedReader bufferedReader = new BufferedReader(fileReader);
			// >comp74447_c0_seq1_120 PIRSF001199 PIRSR001199-1 P13497
			// comp74447_c0_seq1 1259 4405 ANTISENSE
			// GAPVPSSSPLPSLVPLFPGVIHPTLSVSVSPSLLATAPMAPAHFSLLLLLLLALHRPALGFDYEANLYEYFPDQDDPADVDYKDPCKAVAFLGDIALDEDDLKLFKIDRVLDFNNHKIQTFGHSSGQLTSSHWNGTSLPKGGRAGLSGSEQQRGNRGRKGKRKAERRRGRKFRKNDTRSRVRRASTSRPERVWPEGVIPYVIGGNFTGSQRAIFRQAMRHWEKNTCVTFLERTEEESYIVFTYRPCGCCSYVGRRGGGPQAISIGKNCDKFGIVVHELGHVIGFWHEHTRPDRDEHVSIVRENIQPGQEYNFLKMEPEEVDSLGEMYDFDSIMHYARNTFSRGIFLDTILPRYDDNGVRPPIGQRTRLSKGDISQARKLYRCPACGDTLQDSMGNFSSPGFPNGYSSFTHCVWRISVTPGEKIILNFTAMDTFRSRLCWYDYVEVRDGYWKKAPLRGRFCGDKIPPSLISTDSRLWIEFRSSSSWVGKGFSAVYEAICGGDISKDLGQIQSPNYPDDYRPSKVCIWRIRVSDTFHVGLAFQSFEIERHDSCAYDYLEVRDGSAEDSPLIGRYCGYDKPDDIKSSSNKLWMKFVSDGSINKAGFSANFFKEVDECSRPDNGGCEQRCVNTLGSYKCACEPGYELANDKRSCEAACGGFLTKLNGSITSPGWPKEYPPNKNCVWQVVAPTQYRISLEFESFEVEGNDVCKYDYLEVRSGLSADSKLHGKFCGPERPEVITSQYNNMRIEFKSDNTVSKKGFKAHFFSDKDECSKDNGGCQHECANTFGSYTCQCRSGFMLHENKHDCKEAGCEHKINSVSGVITSPNWPDKYTSRKECTWEIVTTPGHRVKLAFNELDIEQHQECAYDHLELYDGKDAKAKVIGRFCGSKKPEPLTSTFNRMFIKFFSDNSVQKRGFEATHTSECGGRLKAEVKTKDVYSHSQFGDNNYPEGADCQWVIQAEEGYGVELIFQTFEIEEEADCGYDYMELFDGSDDTAPRLGRFCGSGPPEEIYSAGDSIMIRFHTDDTINKKGFHARYTSTKFQDTLHLRK

			String line;
			String id = "";
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith(">")) {
					String[] fields = line.split("\t");
					id = fields[0].replace(">", "");
					seqMap = new HashMap<String, String>();
					seqMap.put(id, "");
				} else {
					seqMap.put(id, seqMap.get(id) + line);
				}
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return seqMap;
	}

	@RequestMapping(value = "/prediction/ajax/export/{jobId}/tsv", method = RequestMethod.GET)
	public void downloadFilteredTSVReport(@PathVariable("jobId") String jobId, @DatatablesParams DatatablesCriterias criterias, HttpServletResponse response) {
		// String fileSeparator = System.getProperty("file.separator");
		// String cwd = env.getProperty("currentWorkingDir");
		// File jobDir = new File(cwd + fileSeparator + jobId.substring(0, 8)
		// + fileSeparator + jobId);
		// String reldateFile = jobDir + fileSeparator + "reldate.txt";
		// String reldate = readFileContent(reldateFile);

		List<PredictionTSVReport> reports = reportRepository.findPredictionTSVReportWithDatatablesCriterias(jobId, criterias);
		StringBuffer sb = new StringBuffer();
		// sb.append("#JobId: "+jobId+"\n");
		// sb.append("#PIRSitePredict: "+reldate);
		sb.append("PIRSR\tProtein ID\tStart\tEnd\tType\tCategory\tDescription\tNucleotide ID\tORF Start\tORF End\tORF Strand\n");
		for (PredictionTSVReport report : reports) {
			sb.append(report.toString() + "\n");
		}

		response.setContentType("text/tab-separated-values");
		response.setContentLength((int) sb.toString().length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + jobId + "_report.txt\"");
		try {
			response.getWriter().write(sb.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(sb.toString());
		// System.out.println(jobId+": " + criterias.toString());
	}
}
