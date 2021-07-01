package com.sciamus.contractanalyzer.domain.checks.reports;


import com.sciamus.contractanalyzer.infrastructure.port.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


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

        return reportRepository.findAll();

    }

    public List<CheckReport> findAllByName(String name) {
        return reportRepository.findAllByNameOfCheck(name);
    }

}
