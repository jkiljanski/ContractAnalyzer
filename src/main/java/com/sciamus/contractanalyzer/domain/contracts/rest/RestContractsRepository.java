package com.sciamus.contractanalyzer.domain.contracts.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Info;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class RestContractsRepository {

    public RestContractsRepository() { }

    public Info mapFileToOpenApiInfo(MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = null;
        is = file.getInputStream();
        return mapper.readValue(is, Info.class);
    }

    public RestContract mapOpenApiInfoToDTO(MultipartFile file, Info openApiInfo) {
        return RestContract.builder()
                .name(openApiInfo.getContact().getName())
                .description(openApiInfo.getDescription())
                .file(file)
                .version(openApiInfo.getVersion())
                .build();
    }
}
