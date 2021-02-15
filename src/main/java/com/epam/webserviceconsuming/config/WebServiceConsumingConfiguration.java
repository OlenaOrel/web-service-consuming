package com.epam.webserviceconsuming.config;

import com.epam.webserviceconsuming.client.DefaultCommandLineRunner;
import com.epam.webserviceconsuming.client.OrderClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebServiceConsumingConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public OrderClient orderClient(RestTemplate restTemplate) {
        return new OrderClient(restTemplate);
    }

    @Bean
    public CommandLineRunner commandLineRunner(OrderClient orderClient) {
        return new DefaultCommandLineRunner(orderClient);
    }

}
