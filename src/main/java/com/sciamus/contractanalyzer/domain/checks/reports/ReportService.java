package com.sciamus.contractanalyzer.domain.checks.reports;


import com.sciamus.contractanalyzer.infrastructure.adapter.RepositoryConfigurable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;


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
//    public List<Report> getAllReports() {
//
//        return repositoryConfigurable.getReportsRepository().findAll();
//
//    }

    public List<Report> getFilteredReports(String result, String reportBody, String timestamp, String nameOfCheck, String userName) {
        List<Report> filteredReports = repositoryConfigurable.getReportsRepository().findAll();
        return filteredReports
                .stream()
                .filter(r -> (result.isEmpty() || r.getResult().equals(ReportResults.valueOf(result))) &&
                        r.getReportBody().contains(reportBody) &&
                        r.getNameOfCheck().contains(nameOfCheck) &&
                        r.getUserName().contains(userName))
                .collect(Collectors.toList());
    }

    public List<Report> findAllByName(String name) {
        return repositoryConfigurable.getReportsRepository().findAllByNameOfCheck(name);
    }

    public Page<Report> findByPageSize(int numPages) {
        return repositoryConfigurable.getReportsRepository().findAll(Pageable.ofSize(numPages));

    }


}
