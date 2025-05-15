package org.proteininformationresource.pirsitepredict.prediction;

import java.util.List;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

public interface PredictionTSVReportService {
	/**
	 * 
	 * @param reportId 
	 * @return the complete list of predictionTSVReports.
	 */
	public List<PredictionTSVReport> findAll(String reportId);
	
	/**
	 * 
	 * @param maxResult Max number of predictionTSVReports.
	 * @return a maxResult limited list of predictionTSVReports.
	 */
	public List<PredictionTSVReport> findLimited(String reportId, int maxResult);
	
	/**
	 * Query used to populate the DataTables that display the list of predictionTSVReports.
	 * @param reportId
	 * @param criterias
	 *            The DataTables criterias used to filter the persons.
	 *            (maxResult, filtering, paging, ...)
	 * @return a bean that wraps all the needed information by DataTables to
	 *         redraw the table with the data.
	 */
	public DataSet<PredictionTSVReport> findPredictionTSVReportsWithDatatablesCriterias(String reportId, DatatablesCriterias criterias);
	
}
