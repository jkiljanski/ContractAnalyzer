package com.sciamus.contractanalyzer.domain.contracts.rest;


import lombok.Builder;
import lombok.Data;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class RestContract {

    private MultipartFile file;
    private String name;
    private String description;
    private String version;
    private String alias;
}

