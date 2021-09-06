package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.application.mapper.report.ReportInfrastructureMapper;
import com.sciamus.contractanalyzer.application.mapper.report.ReportViewMapper;
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




    public Page<ReportViewDTO> findAllByPageNumber(int pageNumber) {

        Page<ReportInfrastructureDTO> page = reportPersistancePort.findAll(pageNumber);

        return page.map(this::convertInfrastractureDTOToViewDTO);
    }



    private ReportInfrastructureDTO convertInterfaceDTOToInfrastructureDTO(ReportViewDTO dto) {
        return reportInfrastructureMapper.mapToDTO(reportViewMapper.mapFromDTO(dto));
    }


    public Page<ReportViewDTO> getFilteredReports(ReportFilterParameters reportFilterParameters, int pageNumber) {
        Page<ReportInfrastructureDTO> page = reportPersistancePort.findAll(reportFilterParameters, pageNumber);
        return  page.map(this::convertInfrastractureDTOToViewDTO);
    }
}
