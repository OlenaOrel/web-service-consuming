package com.epam.webserviceconsuming.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum OrderStatus {

    CREATED, UPDATED, SENT
}
