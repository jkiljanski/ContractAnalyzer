package com.sciamus.contractanalyzer.infrastructure.port;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ReportPersistancePort {

    ReportInfrastructureDTO save(ReportInfrastructureDTO report);

    ReportInfrastructureDTO findById(String id);

    List<ReportInfrastructureDTO> findAll();

    Page<ReportInfrastructureDTO> findAll(int pageSize);

    Page<ReportInfrastructureDTO> findAll(int pageSize, String sortingProperty, String sortingOrder);



}
