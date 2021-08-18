package com.sciamus.contractanalyzer.infrastructure.port;

import java.util.List;

public interface ReportPersistancePort {

    ReportInfrastructureDTO save(ReportInfrastructureDTO report);

    ReportInfrastructureDTO findById(String id);

    List<ReportInfrastructureDTO> findAll();

    List<ReportInfrastructureDTO> findAll(int pageSize);


}
