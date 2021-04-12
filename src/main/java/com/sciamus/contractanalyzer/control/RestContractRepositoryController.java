package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.checks.RestContractCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestContractRepositoryController {

    //TODO: dlaczego nie final
    private RestContractCheckRepository restContractCheckRepository;

    @Autowired
    public RestContractRepositoryController(RestContractCheckRepository restContractCheckRepository) {
        this.restContractCheckRepository = restContractCheckRepository;
    }

    @GetMapping("/restContractChecks")
    public List<String> getRestContractCheckList(){

        return restContractCheckRepository.getAllChecks();

    }




}
