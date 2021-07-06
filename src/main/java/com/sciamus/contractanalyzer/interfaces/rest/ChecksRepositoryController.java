package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.getlistof.ListOfChecksDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class ChecksRepositoryController {

    //TODO: fix me: this cannot use domain object directly -> it always must got through application layer
    private final RestCheckRepository restCheckRepository;

    public ChecksRepositoryController(RestCheckRepository restCheckRepository) {
        this.restCheckRepository = restCheckRepository;
    }

//    @RolesAllowed("reader")
    @GetMapping("/restContractChecks")
    public ListOfChecksDTO getRestContractCheckList() {

        ListOfChecksDTO responseDTO = new ListOfChecksDTO();
        responseDTO.listOfChecks = restCheckRepository.getAllChecks();
        return responseDTO;

    }


}
