//package com.sciamus.contractanalyzer.domain.checks.reports;
//
//
//import com.sciamus.contractanalyzer.infrastructure.port.PersistanceConfigurable;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//
//
//public class ReportService {
//
//    public ReportService(PersistanceConfigurable persistanceConfigurable, ReportIdGenerator reportIdGenerator) {
//        this.persistanceConfigurable = persistanceConfigurable;
//        this.reportIdGenerator = reportIdGenerator;
//    }
//
//    private final PersistanceConfigurable persistanceConfigurable;
//
//    private final ReportIdGenerator reportIdGenerator;
//
//
//    //pls review
//    public Report addReportToRepository(Report report) {
//        report.addId(reportIdGenerator.getNextID());
//        return persistanceConfigurable.getReportsRepository().save(report);
//    }
//
//    public Report getReportByID(String id) {
//
//        return persistanceConfigurable.getReportsRepository().findById(id)
//                .orElseThrow(() -> new ReportNotFoundException("" + id));
//    }
//
//    public List<Report> getAllReports() {
//
//        return persistanceConfigurable.getReportsRepository().findAll();
//
//    }
//
//    public List<Report> findAllByName(String name) {
//        return persistanceConfigurable.getReportsRepository().findAllByNameOfCheck(name);
//    }
//
//    public Page<Report> findByPageSize(int numPages) {
//        return persistanceConfigurable.getReportsRepository().findAll(Pageable.ofSize(numPages));
//
//    }
//
//
//}
