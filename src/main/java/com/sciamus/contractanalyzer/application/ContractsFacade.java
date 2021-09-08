package com.sciamus.contractanalyzer.application;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.contracts.rest.RestContract;
import com.sciamus.contractanalyzer.domain.contracts.rest.RestContractsRepository;
import com.sciamus.contractanalyzer.infrastructure.port.ReportInfrastructureDTO;
import io.swagger.models.Info;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class ContractsFacade {

    private final RestContractsRepository restContractRepository;

    Logger logger = LogManager.getLogger(this.getClass());

    public ContractsFacade(RestContractsRepository restContractRepository) {
        this.restContractRepository = restContractRepository;
    }

    public void importNewContractFile(MultipartFile file) {
        try {
            Info openApiInfo = restContractRepository.mapFileToOpenApiInfo(file);
            logger.info(String.format("Received new OpenApi JSON: %s", openApiInfo.toString()));
            RestContract contract = restContractRepository.mapOpenApiInfoToDTO(file, openApiInfo);
            // TODO save contract to DB
        } catch (IOException e) {
            logger.error("IOException while parsing file.");
            e.printStackTrace();
        }
    }
}
