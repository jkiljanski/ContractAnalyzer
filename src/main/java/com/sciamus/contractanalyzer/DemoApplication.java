
package com.sciamus.contractanalyzer;

import com.sciamus.contractanalyzer.control.TestClient;
import com.sciamus.contractanalyzer.control.TestClientController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication()
@EnableFeignClients

public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);


    }


}

//TODO: podzielić na package