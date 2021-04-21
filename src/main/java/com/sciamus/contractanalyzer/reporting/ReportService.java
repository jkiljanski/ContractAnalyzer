package com.sciamus.contractanalyzer.reporting;


import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ReportService {

    //dlaczego to zwraca błąd:

    // private final io.vavr.collection.Map<Long, TestReport> reportRepository = new HashMap<Long,TestReport>();
    private final ReportRepository reportRepository;

    private final IdGenerator idGenerator;

    public ReportService(ReportRepository reportRepository, IdGenerator idGenerator) {
        this.reportRepository = reportRepository;
        this.idGenerator = idGenerator;
    }

    //pls review
    public TestReport addReportToRepository(TestReport testReport) {
        testReport.addId(idGenerator.getNextID());
        return reportRepository.save(testReport);
    }

    public TestReport getReportByID(String id) {

        return reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("" + id));
    }

    //watchout: żeby nie zaciągnęło całej bazy danych, to trzeba zrobić jakieś warunki
    public List<TestReport> getAllReports() {

        return reportRepository.findAll();

    }

    public List<TestReport> findAllByName(String name) {
        return reportRepository.findAllByNameOfCheck(name);
    }



}
