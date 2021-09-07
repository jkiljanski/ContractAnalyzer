package config;

import com.sciamus.contractanalyzer.infrastructure.port.ReportInfrastructureDTO;

import java.time.LocalDateTime;

public class TestReportInfrastructureDTO extends ReportInfrastructureDTO {

    public TestReportInfrastructureDTO() {
        super("0", "PASSED", "test body", LocalDateTime.now(), "nameOfCheck", "userName");
    }
}
