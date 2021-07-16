package com.sciamus.contractanalyzer.domain.reporting.checks;


import com.sciamus.contractanalyzer.domain.reporting.idGenerator.ReportIdGenerator;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;


public class ReportService {

    private final ReportRepository reportRepository;

    private final ReportIdGenerator reportIdGenerator;

    @Autowired
    public ReportService(ReportRepository reportRepository, ReportIdGenerator reportIdGenerator) {
        this.reportRepository = reportRepository;
        this.reportIdGenerator = reportIdGenerator;
    }

    //pls review
    public CheckReport addReportToRepository(CheckReport checkReport) {
        checkReport.addId(reportIdGenerator.getNextID());
        return reportRepository.save(checkReport);
    }

    public CheckReport getReportByID(String id) {

        return reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("" + id));
    }

    //watchout: żeby nie zaciągnęło całej bazy danych, to trzeba zrobić jakieś warunki
    public List<CheckReport> getAllReports() {

        return List.ofAll(reportRepository.findAll());

    }

    public List<CheckReport> findAllByName(String name) {
        return List.ofAll(reportRepository.findAllByNameOfCheck(name));
    }

}
