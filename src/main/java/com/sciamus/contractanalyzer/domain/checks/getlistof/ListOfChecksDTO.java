package com.sciamus.contractanalyzer.domain.checks.getlistof;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ListOfChecksDTO {

    @Getter
    @Setter
    public List<String> listOfChecks;

    @Override
    public String toString() {
        return "ListOfChecksDTO{" +
                "listOfChecks=" + listOfChecks +
                '}';
    }
}
