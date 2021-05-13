package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.checks.RestContractCheckRepository;
import com.sciamus.contractanalyzer.checks.getlistof.ListOfChecksDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class ChecksRepositoryController {

    //TODO: dlaczego nie final
    private RestContractCheckRepository restContractCheckRepository;

    @Autowired
    public ChecksRepositoryController(RestContractCheckRepository restContractCheckRepository) {
        this.restContractCheckRepository = restContractCheckRepository;
    }

//    @RolesAllowed("reader")
    @GetMapping("/restContractChecks")
    public ListOfChecksDTO getRestContractCheckList() {

        ListOfChecksDTO responseDTO = new ListOfChecksDTO();
        responseDTO.listOfChecks = restContractCheckRepository.getAllChecks();
        return responseDTO;

    }


}
