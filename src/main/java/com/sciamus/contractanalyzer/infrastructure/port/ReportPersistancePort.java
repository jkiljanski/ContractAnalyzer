package com.sciamus.contractanalyzer.infrastructure.port;

import com.sciamus.contractanalyzer.application.ReportFilterParameters;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReportPersistancePort {

    ReportInfrastructureDTO save(ReportInfrastructureDTO report);

    ReportInfrastructureDTO findById(String id);

    List<ReportInfrastructureDTO> findAll();

    Page<ReportInfrastructureDTO> findAll(int pageSize);

    Page<ReportInfrastructureDTO> findAll(int pageSize, String sortingProperty, String sortingOrder);

    Page<ReportInfrastructureDTO> findAll(ReportFilterParameters reportFilterParameters, int pageNumber);


}
