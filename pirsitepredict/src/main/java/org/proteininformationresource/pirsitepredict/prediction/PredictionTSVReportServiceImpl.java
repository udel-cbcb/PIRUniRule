package org.proteininformationresource.pirsitepredict.prediction;

import java.util.List;

import org.proteininformationresource.pirsitepredict.prediction.datatables.DataTablesResponse;
import org.proteininformationresource.pirsitepredict.prediction.datatables.DatatablesCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PredictionTSVReportServiceImpl implements PredictionTSVReportService {

	@Autowired
	private PredictionTSVReportRepository reportRepository;
	
	@Override
	public List<PredictionTSVReport> findAll(String reportId) {
		return reportRepository.findAll(reportId);
	}

	@Override
	public List<PredictionTSVReport> findLimited(String reportId, int maxResult) {
		return reportRepository.findLimited(reportId, maxResult);
	}

	@Override
	public DataTablesResponse<PredictionTSVReport> findPredictionTSVReportsWithDatatablesCriterias(
			String reportId, DatatablesCriteria criterias) {
		List<PredictionTSVReport> reports = reportRepository.findPredictionTSVReportWithDatatablesCriterias(reportId, criterias);
		Long count = reportRepository.getTotalCount(reportId);
		
		Long countFiltered = reportRepository.getFilteredCount(reportId, criterias);
		DataTablesResponse<PredictionTSVReport> response = new DataTablesResponse<PredictionTSVReport>();
		response.setDraw(criterias.getDraw());
		response.setRecordsTotal(count);
		response.setRecordsFiltered(countFiltered);
		response.setData(reports);
		return response;
	}

}
