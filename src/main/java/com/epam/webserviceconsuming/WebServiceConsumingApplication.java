package com.epam.webserviceconsuming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.epam.webserviceconsuming.config")
public class WebServiceConsumingApplication {


    public static void main(String[] args) {
        SpringApplication.run(WebServiceConsumingApplication.class, args);
    }

}
