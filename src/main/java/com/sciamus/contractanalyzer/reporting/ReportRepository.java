package com.sciamus.contractanalyzer.reporting;


import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ReportRepository {

    private final Map<Long, TestReport> reportRepository = new ConcurrentHashMap<>();

    private final IdGenerator idGenerator;

    public ReportRepository(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public int getCountOfReports() {
        return reportRepository.size();
    }

    //pls review
    public TestReport addReportToRepository(TestReport testReport) {
        testReport.addId(idGenerator.getNextID());
        return reportRepository.put(testReport.getReportId(), testReport);
    }

    public TestReport getReportByID(long id) {
        return reportRepository.get(id);
    }


}
