package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.application.mapper.report.ReportInfrastructureMapper;
import com.sciamus.contractanalyzer.application.mapper.report.ReportViewMapper;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import com.sciamus.contractanalyzer.infrastructure.port.ReportInfrastructureDTO;
import com.sciamus.contractanalyzer.infrastructure.port.ReportPersistancePort;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ReportFacade {

    private final ReportViewMapper reportViewMapper;

    private final ReportInfrastructureMapper reportInfrastructureMapper;

    private final ReportPersistancePort reportPersistancePort;


    public ReportFacade(ReportViewMapper reportViewMapper, ReportInfrastructureMapper reportInfrastructureMapper, ReportPersistancePort reportPersistancePort) {
        this.reportViewMapper = reportViewMapper;
        this.reportInfrastructureMapper = reportInfrastructureMapper;
        this.reportPersistancePort = reportPersistancePort;
    }

    // change to Try:
    public ReportViewDTO getReportByID(String id) {
        return convertInfrastractureDTOToViewDTO(reportPersistancePort.findById(id));
    }

    // change to Try
    public List<ReportViewDTO> getAllReports() {
        return reportPersistancePort.findAll().stream().map(this::convertInfrastractureDTOToViewDTO).collect(Collectors.toList());
    }

    private ReportViewDTO convertInfrastractureDTOToViewDTO(ReportInfrastructureDTO dto) {
        return reportViewMapper.mapToDTO(reportInfrastructureMapper.mapFromDTO(dto));
    }

    public List<ReportViewDTO> getFilteredReports(String result, String reportBody, String timestamp, String nameOfCheck, String userName) {
        return innerGetFilteredReports(result, reportBody, timestamp, nameOfCheck, userName)
                .stream()
                .map(reportViewMapper::mapToDTO).collect(Collectors.toList());
    }

    public Page<ReportViewDTO> findByPageSize(int documentsPerPage) {

        return reportPersistancePort.findAll(documentsPerPage).stream()
                .map(reportInfrastructureMapper::mapFromDTO)
                .map(reportViewMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    private ReportInfrastructureDTO convertInterfaceDTOToInfrastructureDTO(ReportViewDTO dto) {
        return reportInfrastructureMapper.mapToDTO(reportViewMapper.mapFromDTO(dto));
    }

    public List<Report> innerGetFilteredReports(String result, String reportBody, String timestamp, String nameOfCheck, String userName) {
        List<ReportInfrastructureDTO> filteredReports = reportPersistancePort.findAll();
        return filteredReports
                .stream()
                .map(reportInfrastructureMapper::mapFromDTO)
                .filter(r -> (result.isEmpty() || r.getResult().equals(ReportResults.valueOf(result))) &&
                        r.getReportBody().contains(reportBody) &&
                        r.getNameOfCheck().contains(nameOfCheck) &&
                        r.getUserName().contains(userName))
                .collect(Collectors.toList());
    }

}
