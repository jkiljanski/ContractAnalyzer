package com.sciamus.contractanalyzer.infrastructure.port;

import java.time.LocalDateTime;

public class ReportFilterParametersInfrastructureDTO {

    public final  String  result;
    public final  String  reportBody;
    public final LocalDateTime timestampFrom;
    public final  LocalDateTime  timestampTo;
    public final  String  nameOfCheck;
    public final  String  userName;


    public ReportFilterParametersInfrastructureDTO(String result, String reportBody, LocalDateTime timestampFrom, LocalDateTime timestampTo, String nameOfCheck, String userName) {
        this.result = result;
        this.reportBody = reportBody;
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
        this.nameOfCheck = nameOfCheck;
        this.userName = userName;
    }
}
