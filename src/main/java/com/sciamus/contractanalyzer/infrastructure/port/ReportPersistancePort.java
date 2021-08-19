package com.sciamus.contractanalyzer.infrastructure.port;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportPersistancePort {

    ReportInfrastructureDTO save(ReportInfrastructureDTO report);

    ReportInfrastructureDTO findById(String id);

    List<ReportInfrastructureDTO> findAll();

    List<ReportInfrastructureDTO> findAll(int pageSize);


    List<ReportInfrastructureDTO> findAllByTimestampBetweenAndNameOfCheckAndResultAndUserNameAndReportBodyContaining(LocalDateTime timestampFrom,
                                                                                                            LocalDateTime timestampTo,
                                                                                                            String nameOfCheck,
                                                                                                            String result,
                                                                                                            String userName,
                                                                                                            String reportBody);


}
