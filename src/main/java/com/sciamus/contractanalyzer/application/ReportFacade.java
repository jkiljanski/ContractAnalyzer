package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.application.mapper.ReportMapper;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;

import java.util.List;
import java.util.stream.Collectors;

public class ReportFacade {

    private final ReportService reportService;
    private final ReportMapper reportMapper;

    public ReportFacade(ReportService reportService, ReportMapper reportMapper) {
        this.reportService = reportService;
        this.reportMapper = reportMapper;
    }


    public ReportDTO getReportByID(String id) {
        return  reportMapper.mapToDTO(reportService.getReportByID(id));
    }

    public List<ReportDTO> getAllReports() {
        return reportService.getAllReports().stream().map(report -> reportMapper.mapToDTO(report)).collect(Collectors.toList());
    }
}
