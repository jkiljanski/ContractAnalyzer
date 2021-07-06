package com.sciamus.contractanalyzer.domain.checks.reports;


import com.sciamus.contractanalyzer.infrastructure.adapter.RepositoryConfigurable;
import com.sciamus.contractanalyzer.infrastructure.port.ReportsRepository;

import java.util.List;


public class ReportService {

    public ReportService(RepositoryConfigurable repositoryConfigurable, ReportIdGenerator reportIdGenerator) {
        this.repositoryConfigurable = repositoryConfigurable;
        this.reportIdGenerator = reportIdGenerator;
    }

    private final RepositoryConfigurable repositoryConfigurable;

    private final ReportIdGenerator reportIdGenerator;


    //pls review
    public Report addReportToRepository(Report report) {
        report.addId(reportIdGenerator.getNextID());
        return repositoryConfigurable.getReportsRepository().save(report);
    }

    public Report getReportByID(String id) {

        return repositoryConfigurable.getReportsRepository().findById(id)
                .orElseThrow(() -> new ReportNotFoundException("" + id));
    }

    //watchout: żeby nie zaciągnęło całej bazy danych, to trzeba zrobić jakieś warunki
    public List<Report> getAllReports() {

        return repositoryConfigurable.getReportsRepository().findAll();

    }

    public List<Report> findAllByName(String name) {
        return repositoryConfigurable.getReportsRepository().findAllByNameOfCheck(name);
    }

}
