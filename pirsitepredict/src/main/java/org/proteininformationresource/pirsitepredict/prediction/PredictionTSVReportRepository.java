package org.proteininformationresource.pirsitepredict.prediction;

import java.util.List;

import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

public interface PredictionTSVReportRepository {

	/**
	 * @param the report Id.
	 * @return the complete list of predictionTSVReports.
	 */
	public List<PredictionTSVReport> findAll(String reportId);
	
	/**
	 * @param the report Id.
	 * @param maxResult Max number of predictionTSVReports.
	 * @return a maxResult limited list of predictionTSVReports.
	 */
	public List<PredictionTSVReport> findLimited(String reportId, int maxResult);
	
	/**
	 * Query used to populate the DataTables that display the list of predictionTSVReports.
	 * @param the report Id.
	 * @param criterias The DataTables criterias used to filter the predictionTSVReports. (maxResult, filtering, paging ..)
	 * @return a filtered list of predictionTSVReports.
	 */
	public List<PredictionTSVReport> findPredictionTSVReportWithDatatablesCriterias(String reportId, DatatablesCriterias criterias);
	
	/**
	 * Query used to return the number of filtered predictionTSVReports.
	 * @param the report Id.
	 * @param criterias The DataTables criterias used to filter the predictionTSVReports. (maxResult, filtering, paging ..)
	 * @return the number of filtered predictionTSVReports.
	 */
	public Long getFilteredCount(String reportId, DatatablesCriterias criterias);
	
	/**
	 * @param the report Id.
	 * @return the total count of predictionTSVReports.
	 */
	public Long getTotalCount(String reportId);
}
