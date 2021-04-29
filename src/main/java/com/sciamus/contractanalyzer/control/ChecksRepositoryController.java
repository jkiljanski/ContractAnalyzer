package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.checks.RestContractCheckRepository;
import com.sciamus.contractanalyzer.checks.getlistof.GetListOfContractChecksCheckResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class ChecksRepositoryController {

    //TODO: dlaczego nie final
    private RestContractCheckRepository restContractCheckRepository;

    @Autowired
    public ChecksRepositoryController(RestContractCheckRepository restContractCheckRepository) {
        this.restContractCheckRepository = restContractCheckRepository;
    }

    @RolesAllowed({"writer"})
    @GetMapping("/restContractChecks")
    public GetListOfContractChecksCheckResponseDTO getRestContractCheckList(){

        GetListOfContractChecksCheckResponseDTO responseDTO = new GetListOfContractChecksCheckResponseDTO();
        responseDTO.listOfChecks = restContractCheckRepository.getAllChecks();
        return responseDTO;

    }




}
