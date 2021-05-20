package com.sciamus.contractanalyzer.control;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/")
    String Test() {
        return "Test";
    }

}
