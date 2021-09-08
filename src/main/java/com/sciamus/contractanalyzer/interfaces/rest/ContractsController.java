package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.application.ContractsFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ContractsController {

    private final ContractsFacade contractsFacade;

    public ContractsController(ContractsFacade contractsFacade) {
        this.contractsFacade = contractsFacade;
    }

    @CrossOrigin("*")
    @PostMapping("/contracts/importFile")
    @ResponseBody
    public void importNewContract(@RequestParam("file") MultipartFile file) {
        contractsFacade.importNewContractFile(file);
    }
}
