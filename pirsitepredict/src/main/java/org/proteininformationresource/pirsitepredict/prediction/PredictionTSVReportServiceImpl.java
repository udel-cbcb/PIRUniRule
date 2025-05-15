package org.proteininformationresource.pirsitepredict.prediction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

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
	public DataSet<PredictionTSVReport> findPredictionTSVReportsWithDatatablesCriterias(
			String reportId, DatatablesCriterias criterias) {
		List<PredictionTSVReport> reports = reportRepository.findPredictionTSVReportWithDatatablesCriterias(reportId, criterias);
		Long count = reportRepository.getTotalCount(reportId);
		
		Long countFiltered = reportRepository.getFilteredCount(reportId, criterias);
		return new DataSet<PredictionTSVReport>(reports, count, countFiltered);
	}

}
