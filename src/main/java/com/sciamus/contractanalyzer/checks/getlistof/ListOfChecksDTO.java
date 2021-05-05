package com.sciamus.contractanalyzer.checks.getlistof;

import java.util.List;

public class ListOfChecksDTO {

    public List<String> listOfChecks;

    public List<String> getListOfChecks() {
        return listOfChecks;
    }

    public void setListOfChecks(List<String> listOfChecks) {
        this.listOfChecks = listOfChecks;
    }

    @Override
    public String toString() {
        return "ListOfChecksDTO{" +
                "listOfChecks=" + listOfChecks +
                '}';
    }
}
