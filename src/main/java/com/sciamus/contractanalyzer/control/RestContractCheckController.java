package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.check.RestContractCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestContractCheckController {

    @Autowired
    private RestContractCheckRepository restContractCheckRepository;

    @GetMapping("/")
    public String index(){
        return "Krowa";
    }


    @GetMapping("/restContractCheck")
    public List<String> getRestContractCheck(){

        return restContractCheckRepository.getNames();
//        System.out.println("Krowa");
    }

}
