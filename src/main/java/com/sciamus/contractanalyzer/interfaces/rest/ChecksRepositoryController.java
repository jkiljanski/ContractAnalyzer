package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.application.ChecksFacade;
import com.sciamus.contractanalyzer.domain.checks.rest.getlistof.ListOfChecksDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChecksRepositoryController {

    private final ChecksFacade checksFacade;


    public ChecksRepositoryController(ChecksFacade checksFacade) {
        this.checksFacade = checksFacade;

    }


//    @RolesAllowed("reader")
    @GetMapping("/restContractChecks")
    public ListOfChecksDTO getRestContractCheckList() {

        ListOfChecksDTO responseDTO = new ListOfChecksDTO();
        responseDTO.listOfChecks = checksFacade.returnAllChecksList();
        return responseDTO;

    }


}
