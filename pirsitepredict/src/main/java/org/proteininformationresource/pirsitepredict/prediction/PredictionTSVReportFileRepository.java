package org.proteininformationresource.pirsitepredict.prediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.github.dandelion.core.utils.StringUtils;
import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

@Repository
@PropertySource({ "classpath:pirsitepredict.properties",
		"classpath:persistence.properties" })
public class PredictionTSVReportFileRepository implements
		PredictionTSVReportRepository {

	@Autowired
	private Environment env;

	private List<PredictionTSVReport> reports;

	@Override
	public List<PredictionTSVReport> findAll(String reportId) {
		String cwd = env.getProperty("currentWorkingDir");
		String fileSeparator = System.getProperty("file.separator");
		String jobDir = cwd + fileSeparator + reportId.substring(0, 8)
				+ fileSeparator + reportId;
		String tsvFile = jobDir + fileSeparator
				+ "outputDir/prediction/pirsr_prediction.tsv";
		File tsv = new File(tsvFile);
		// if (tsv.exists() && tsv.length() > 0) {
		List<PredictionTSVReport> tsvReports = new ArrayList<PredictionTSVReport>();
		if (tsv.exists() && tsv.length() > 0) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(tsv);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						fis));
				String line = null;
				while ((line = br.readLine()) != null) {
					if (!line.startsWith("PIRSRID")) {
						String[] fields = line.split("\t");
						PredictionTSVReport tsvReport = new PredictionTSVReport();
						tsvReport.setJobId(reportId);
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
		reports = tsvReports;
		// }
		return reports;
	}

	@Override
	public List<PredictionTSVReport> findLimited(String reportId, int maxResult) {
		// if (reports == null) {
		// reports = findAll(reportId);
		// }

		return findAll(reportId).subList(0, maxResult);
	}

	@Override
	public List<PredictionTSVReport> findPredictionTSVReportWithDatatablesCriterias(
			String reportId, DatatablesCriterias criterias) {
		// if (reports == null) {
		// reports = findAll(reportId);
		// }
		List<PredictionTSVReport> runningReports = findAll(reportId);

		/**
		 * Step 1: global and individual column filtering
		 */
		// for (ColumnDef columnDef : criterias.getColumnDefs()) {
		// System.out.println(columnDef.getName());
		// }
		List<PredictionTSVReport> filteredReports = columnFiltering(criterias,
				runningReports);

		runningReports = filteredReports;
		/**
		 * Step 2: sorting
		 */
		List<PredictionTSVReport> sortedReports = sortReports(runningReports,
				criterias);

		/**
		 * Step 3: paging
		 */
		
		return sortedReports;
	}

	private List<PredictionTSVReport> sortReports(
			List<PredictionTSVReport> runningReports,
			DatatablesCriterias criterias) {

		class PIRSRIdComparator implements Comparator<PredictionTSVReport> {
			public String sortOrder;

			public PIRSRIdComparator(String sortOrder) {
				this.sortOrder = sortOrder;
			}

			public int compare(PredictionTSVReport report1,
					PredictionTSVReport report2) {
				int compare = 0;
				if (sortOrder.equalsIgnoreCase("ASC")) {
					compare = report1.getPirsrId().compareTo(
							report2.getPirsrId());
				}
				if (sortOrder.equalsIgnoreCase("DESC")) {
					compare = report2.getPirsrId().compareTo(
							report1.getPirsrId());
				}
				return compare;
			}
		}

		class ProteinIdComparator implements Comparator<PredictionTSVReport> {
			public String sortOrder;

			public ProteinIdComparator(String sortOrder) {
				this.sortOrder = sortOrder;
			}

			public int compare(PredictionTSVReport report1,
					PredictionTSVReport report2) {
				int compare = 0;
				if (sortOrder.equalsIgnoreCase("ASC")) {
					compare = report1.getProteinId().compareTo(
							report2.getProteinId());
				}
				if (sortOrder.equalsIgnoreCase("DESC")) {
					compare = report2.getProteinId().compareTo(
							report1.getProteinId());
				}
				return compare;
			}
		}

		class StartComparator implements Comparator<PredictionTSVReport> {
			public String sortOrder;

			public StartComparator(String sortOrder) {
				this.sortOrder = sortOrder;
			}

			public int compare(PredictionTSVReport report1,
					PredictionTSVReport report2) {
				Integer value1 = null;
				Integer value2 = null;
				int compare = 0;
				if (report1.getStart().length() == 0) {
					value1 = 0;
				} else {
					value1 = Integer.valueOf(report1.getStart());
				}
				if (report2.getStart().length() == 0) {
					value2 = 0;
				} else {
					value2 = Integer.valueOf(report2.getStart());
				}
				if (sortOrder.equalsIgnoreCase("ASC")) {
					compare = value1.compareTo(value2);
				}
				if (sortOrder.equalsIgnoreCase("DESC")) {
					compare = value2.compareTo(value1);
				}
				return compare;
			}
		}
		class EndComparator implements Comparator<PredictionTSVReport> {
			public String sortOrder;

			public EndComparator(String sortOrder) {
				this.sortOrder = sortOrder;
			}

			public int compare(PredictionTSVReport report1,
					PredictionTSVReport report2) {
				Integer value1 = null;
				Integer value2 = null;
				int compare = 0;
				if (report1.getEnd().length() == 0) {
					value1 = 0;
				} else {
					value1 = Integer.valueOf(report1.getEnd());
				}
				if (report2.getEnd().length() == 0) {
					value2 = 0;
				} else {
					value2 = Integer.valueOf(report2.getEnd());
				}
				if (sortOrder.equalsIgnoreCase("ASC")) {
					compare = value1.compareTo(value2);
				}
				if (sortOrder.equalsIgnoreCase("DESC")) {
					compare = value2.compareTo(value1);
				}
				return compare;
			}
		}

		class TypeComparator implements Comparator<PredictionTSVReport> {
			public String sortOrder;

			public TypeComparator(String sortOrder) {
				this.sortOrder = sortOrder;
			}

			public int compare(PredictionTSVReport report1,
					PredictionTSVReport report2) {
				int compare = 0;
				if (sortOrder.equalsIgnoreCase("ASC")) {
					compare = report1.getType().compareTo(report2.getType());
				}
				if (sortOrder.equalsIgnoreCase("DESC")) {
					compare = report2.getType().compareTo(report1.getType());
				}
				return compare;
			}
		}

		class CategoryComparator implements Comparator<PredictionTSVReport> {
			public String sortOrder;

			public CategoryComparator(String sortOrder) {
				this.sortOrder = sortOrder;
			}

			public int compare(PredictionTSVReport report1,
					PredictionTSVReport report2) {
				int compare = 0;
				if (sortOrder.equalsIgnoreCase("ASC")) {
					compare = report1.getCategory().compareTo(
							report2.getCategory());
				}
				if (sortOrder.equalsIgnoreCase("DESC")) {
					compare = report2.getCategory().compareTo(
							report1.getCategory());
				}
				return compare;
			}
		}

		class DescriptionComparator implements Comparator<PredictionTSVReport> {
			public String sortOrder;

			public DescriptionComparator(String sortOrder) {
				this.sortOrder = sortOrder;
			}

			public int compare(PredictionTSVReport report1,
					PredictionTSVReport report2) {
				int compare = 0;
				if (sortOrder.equalsIgnoreCase("ASC")) {
					compare = report1.getDescription().compareTo(
							report2.getDescription());
				}
				if (sortOrder.equalsIgnoreCase("DESC")) {
					compare = report2.getDescription().compareTo(
							report1.getDescription());
				}
				return compare;
			}
		}

		class NucleotideIdComparator implements Comparator<PredictionTSVReport> {
			public String sortOrder;

			public NucleotideIdComparator(String sortOrder) {
				this.sortOrder = sortOrder;
			}

			public int compare(PredictionTSVReport report1,
					PredictionTSVReport report2) {
				int compare = 0;
				if (sortOrder.equalsIgnoreCase("ASC")) {
					compare = report1.getNucleotideId().compareTo(
							report2.getNucleotideId());
				}
				if (sortOrder.equalsIgnoreCase("DESC")) {
					compare = report2.getNucleotideId().compareTo(
							report1.getNucleotideId());
				}
				return compare;
			}
		}

		class NucleotideORFStartComparator implements
				Comparator<PredictionTSVReport> {
			public String sortOrder;

			public NucleotideORFStartComparator(String sortOrder) {
				this.sortOrder = sortOrder;
			}

			public int compare(PredictionTSVReport report1,
					PredictionTSVReport report2) {
				Integer value1 = null;
				Integer value2 = null;
				int compare = 0;
				if (report1.getNucleotideORFStart().length() == 0) {
					value1 = 0;
				} else {
					value1 = Integer.valueOf(report1.getNucleotideORFStart());
				}
				if (report2.getNucleotideORFStart().length() == 0) {
					value2 = 0;
				} else {
					value2 = Integer.valueOf(report2.getNucleotideORFStart());
				}
				if (sortOrder.equalsIgnoreCase("ASC")) {
					compare = value1.compareTo(value2);
				}
				if (sortOrder.equalsIgnoreCase("DESC")) {
					compare = value2.compareTo(value1);
				}
				return compare;
			}
		}

		class NucleotideORFEndComparator implements
				Comparator<PredictionTSVReport> {
			public String sortOrder;

			public NucleotideORFEndComparator(String sortOrder) {
				this.sortOrder = sortOrder;
			}

			public int compare(PredictionTSVReport report1,
					PredictionTSVReport report2) {
				Integer value1 = null;
				Integer value2 = null;
				int compare = 0;
				if (report1.getNucleotideORFEnd().length() == 0) {
					value1 = 0;
				} else {
					value1 = Integer.valueOf(report1.getNucleotideORFEnd());
				}
				if (report2.getNucleotideORFEnd().length() == 0) {
					value2 = 0;
				} else {
					value2 = Integer.valueOf(report2.getNucleotideORFEnd());
				}
				if (sortOrder.equalsIgnoreCase("ASC")) {
					compare = value1.compareTo(value2);
				}
				if (sortOrder.equalsIgnoreCase("DESC")) {
					compare = value2.compareTo(value1);
				}
				return compare;
			}
		}

		class NucleotideORFStrandComparator implements
				Comparator<PredictionTSVReport> {
			public String sortOrder;

			public NucleotideORFStrandComparator(String sortOrder) {
				this.sortOrder = sortOrder;
			}

			public int compare(PredictionTSVReport report1,
					PredictionTSVReport report2) {
				int compare = 0;
				if (sortOrder.equalsIgnoreCase("ASC")) {
					compare = report1.getNucleotideORFStrand().compareTo(
							report2.getNucleotideORFStrand());
				}
				if (sortOrder.equalsIgnoreCase("DESC")) {
					compare = report2.getNucleotideORFStrand().compareTo(
							report1.getNucleotideORFStrand());
				}
				return compare;
			}
		}

		if (criterias.hasOneSortedColumn()) {
			for (ColumnDef columnDef : criterias.getSortingColumnDefs()) {
				if (columnDef.getName().equals("pirsrId")) {
					// if(columnDef.getSortDirection().equals("ASC")) {
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("asc")) {
						Collections.sort(runningReports, new PIRSRIdComparator(
								"asc"));
					}
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("desc")) {
						Collections.sort(runningReports, new PIRSRIdComparator(
								"desc"));
					}
				}
				if (columnDef.getName().equals("proteinId")) {
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("asc")) {
						Collections.sort(runningReports,
								new ProteinIdComparator("asc"));
					}
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("desc")) {
						Collections.sort(runningReports,
								new ProteinIdComparator("desc"));
					}
				}
				if (columnDef.getName().equals("start")) {
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("asc")) {
						Collections.sort(runningReports, new StartComparator(
								"asc"));
					}
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("desc")) {
						Collections.sort(runningReports, new StartComparator(
								"desc"));
					}
				}
				if (columnDef.getName().equals("end")) {
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("asc")) {
						Collections.sort(runningReports, new EndComparator(
								"asc"));
					}
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("desc")) {
						Collections.sort(runningReports, new EndComparator(
								"desc"));
					}
				}
				if (columnDef.getName().equals("type")) {
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("asc")) {
						Collections.sort(runningReports, new TypeComparator(
								"asc"));
					}
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("desc")) {
						Collections.sort(runningReports, new TypeComparator(
								"desc"));
					}
				}
				if (columnDef.getName().equals("category")) {
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("asc")) {
						Collections.sort(runningReports,
								new CategoryComparator("asc"));
					}
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("desc")) {
						Collections.sort(runningReports,
								new CategoryComparator("desc"));
					}
				}
				if (columnDef.getName().equals("description")) {
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("asc")) {
						Collections.sort(runningReports,
								new DescriptionComparator("asc"));
					}
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("desc")) {
						Collections.sort(runningReports,
								new DescriptionComparator("desc"));
					}
				}
				if (columnDef.getName().equals("nucleotideId")) {
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("asc")) {
						Collections.sort(runningReports,
								new NucleotideIdComparator("asc"));
					}
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("desc")) {
						Collections.sort(runningReports,
								new NucleotideIdComparator("desc"));
					}
				}
				if (columnDef.getName().equals("nucleotideORFStart")) {
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("asc")) {
						Collections.sort(runningReports,
								new NucleotideORFStartComparator("asc"));
					}
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("desc")) {
						Collections.sort(runningReports,
								new NucleotideORFStartComparator("desc"));
					}
				}
				if (columnDef.getName().equals("nucleotideORFEnd")) {
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("asc")) {
						Collections.sort(runningReports,
								new NucleotideORFEndComparator("asc"));
					}
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("desc")) {
						Collections.sort(runningReports,
								new NucleotideORFEndComparator("desc"));
					}

				}
				if (columnDef.getName().equals("nucleotideORFStrand")) {
					if (columnDef.getSortDirection().name()
							.equalsIgnoreCase("asc")) {
					Collections.sort(runningReports,
							new NucleotideORFStrandComparator("asc"));
					}
				}
			}

		}
		return runningReports;
	}

	private List<PredictionTSVReport> columnFiltering(
			DatatablesCriterias criterias, List<PredictionTSVReport> myReports) {
		List<PredictionTSVReport> globalFilteredReports = new ArrayList<PredictionTSVReport>();
		List<PredictionTSVReport> columnFilteredReports = new ArrayList<PredictionTSVReport>();
		List<PredictionTSVReport> runningFilteredReports = myReports;
		/**
		 * Step 1.1: global filtering
		 */

		if (StringUtils.isNotBlank(criterias.getSearch())
				&& criterias.hasOneFilterableColumn()) {
			for (PredictionTSVReport report : myReports) {
				boolean hasMatch = false;
				for (ColumnDef columnDef : criterias.getColumnDefs()) {
					if (columnDef.isFilterable()
							&& StringUtils.isBlank(columnDef.getSearch())) {
						if (columnDef.getName().equals("pirsrId")) {
							if (report
									.getPirsrId()
									.toLowerCase()
//									.matches(
//											criterias.getSearch().toLowerCase())) {
									.matches(
											"(.*)"+
											criterias.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("proteinId")) {
							if (report
									.getProteinId()
									.toLowerCase()
									.matches(
											"(.*)"+
											criterias.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("start")) {
							if (report
									.getStart()
									.toLowerCase()
									.matches(
											"(.*)"+
											criterias.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("end")) {
							if (report
									.getEnd()
									.toLowerCase()
								.matches(
											"(.*)"+
											criterias.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("type")) {
							if (report
									.getType()
									.toLowerCase()
									.matches(
											"(.*)"+
											criterias.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("category")) {
							if (report
									.getCategory()
									.toLowerCase()
									.matches(
											"(.*)"+
											criterias.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("description")) {
							if (report
									.getDescription()
									.toLowerCase()
									.matches(
											"(.*)"+
											criterias.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("nucleotideId")) {
							if (report
									.getNucleotideId()
									.toLowerCase()
									.matches(
											"(.*)"+
											criterias.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("nucleotideORFStart")) {
							if (report
									.getNucleotideORFStart()
									.toLowerCase()
									.matches(
											"(.*)"+
											criterias.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("nucleotideORFEnd")) {
							if (report
									.getNucleotideORFEnd()
									.toLowerCase()
									.matches(
											"(.*)"+
											criterias.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("nucleotideORFStrand")) {
							if (report
									.getNucleotideORFStrand()
									.toLowerCase()
									.matches(
											"(.*)"+
											criterias.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
					}
				}
				if (hasMatch) {
					globalFilteredReports.add(report);
				}
			}
			runningFilteredReports = globalFilteredReports;
		}
		/**
		 * Step 1.2: individual column filtering
		 */
		if (criterias.hasOneFilterableColumn()
				&& criterias.hasOneFilteredColumn()) {
			for (PredictionTSVReport report : runningFilteredReports) {
				boolean hasMatch = false;
				for (ColumnDef columnDef : criterias.getColumnDefs()) {
					if (columnDef.isFilterable()
							&& StringUtils.isNotBlank(columnDef.getSearch())) {
						if (columnDef.getName().equals("pirsrId")) {
							if (report
									.getPirsrId()
									.toLowerCase()
//									.contains(
//											columnDef.getSearch().toLowerCase())) {
									.matches(
											"(.*)"+
											columnDef.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("proteinId")) {
							if (report
									.getProteinId()
									.toLowerCase()
									.matches(
											"(.*)"+
											columnDef.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("start")) {
							if (report
									.getStart()
									.toLowerCase()
									.matches(
											"(.*)"+
											columnDef.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("end")) {
							if (report
									.getEnd()
									.toLowerCase()
									.matches(
											"(.*)"+
											columnDef.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("type")) {
							if (report
									.getType()
									.toLowerCase()
									.matches(
											"(.*)"+
											columnDef.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("category")) {
							if (report
									.getCategory()
									.toLowerCase()
									.matches(
											"(.*)"+
											columnDef.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("description")) {
							if (report
									.getDescription()
									.toLowerCase()
									.matches(
											"(.*)"+
											columnDef.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("nucleotideId")) {
							if (report
									.getNucleotideId()
									.toLowerCase()
									.matches(
											"(.*)"+
											columnDef.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("nucleotideORFStart")) {
							if (report
									.getNucleotideORFStart()
									.toLowerCase()
									.matches(
											"(.*)"+
											columnDef.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("nucleotideORFEnd")) {
							if (report
									.getNucleotideORFEnd()
									.toLowerCase()
									.matches(
											"(.*)"+
											columnDef.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
						if (columnDef.getName().equals("nucleotideORFStrand")) {
							if (report
									.getNucleotideORFStrand()
									.toLowerCase()
									.matches(
											"(.*)"+
											columnDef.getSearch().toLowerCase()
											+"(.*)"
											)) {
								hasMatch = true;
							}
						}
					}
				}
				if (hasMatch) {
					columnFilteredReports.add(report);
				}
			}
			runningFilteredReports = columnFilteredReports;
		}

		return runningFilteredReports;
	}

	@Override
	public Long getFilteredCount(String reportId, DatatablesCriterias criterias) {
		// if (reports == null) {
		// reports = findAll(reportId);
		// }
		List<PredictionTSVReport> myFilteredReports = findPredictionTSVReportWithDatatablesCriterias(
				reportId, criterias);

		return (long) myFilteredReports.size();
	}

	@Override
	public Long getTotalCount(String reportId) {
		return (long) findAll(reportId).size();
	}

}
