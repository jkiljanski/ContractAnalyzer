package com.sciamus.contractanalyzer.domain.checks.reports;


import com.sciamus.contractanalyzer.infrastructure.port.ReportRepository;

import java.util.List;


public class ReportService {

    private final ReportRepository reportRepository;

    private final ReportIdGenerator reportIdGenerator;

    public ReportService(ReportRepository reportRepository, ReportIdGenerator reportIdGenerator) {
        this.reportRepository = reportRepository;
        this.reportIdGenerator = reportIdGenerator;
    }

    //pls review
    public Report addReportToRepository(Report report) {
        report.addId(reportIdGenerator.getNextID());
        return reportRepository.save(report);
    }

    public Report getReportByID(String id) {

        return reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("" + id));
    }

    //watchout: żeby nie zaciągnęło całej bazy danych, to trzeba zrobić jakieś warunki
    public List<Report> getAllReports() {

        return reportRepository.findAll();

    }

    public List<Report> findAllByName(String name) {
        return reportRepository.findAllByNameOfCheck(name);
    }

}
