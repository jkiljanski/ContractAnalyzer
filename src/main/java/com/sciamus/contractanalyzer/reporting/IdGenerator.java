package com.sciamus.contractanalyzer.reporting;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    private long nextID;

    long getNextID() {

        this.nextID += 1;
        return nextID;

    }


}
