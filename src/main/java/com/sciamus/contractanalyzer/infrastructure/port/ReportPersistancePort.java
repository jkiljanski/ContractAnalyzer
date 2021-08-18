package com.sciamus.contractanalyzer.infrastructure.port;

import org.springframework.data.domain.Sort;

import java.util.List;

public interface ReportPersistancePort {

    ReportInfrastructureDTO save(ReportInfrastructureDTO report);

    ReportInfrastructureDTO findById(String id);

    List<ReportInfrastructureDTO> findAll();

    List<ReportInfrastructureDTO> findAll(int pageSize);


    List<ReportInfrastructureDTO> findAll(Sort sortBy);
}
