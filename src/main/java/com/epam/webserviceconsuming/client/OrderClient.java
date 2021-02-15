package com.epam.webserviceconsuming.client;


import com.epam.webserviceconsuming.entity.CustomPage;
import com.epam.webserviceconsuming.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderClient {

    private static final String ORDER_RESOURCE_URL = "http://localhost:8088/orders";
    private static final String SLASH = "/";
    private static final String PRODUCT_RESOURCE_URL = "/{orderId}/products/{productId}";

    private static final Logger LOG = LoggerFactory.getLogger(OrderClient.class);

    private RestTemplate restTemplate;

    @Autowired
    public OrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Order getOrderById(Long id) {
        return restTemplate
                .getForObject(ORDER_RESOURCE_URL + SLASH + id, Order.class);
    }

    public CustomPage<Order> getAllOrders() {
        return restTemplate
                .exchange(
                        ORDER_RESOURCE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<CustomPage<Order>>() {
                        }
                ).getBody();
    }

    public HttpStatus createOrder() {
        Order order = new Order();
        order.setUserName("New order");

        return restTemplate
                .postForEntity(ORDER_RESOURCE_URL, order, Order.class)
                .getStatusCode();
    }

    public HttpStatus sendOrder(Long id) {
        Order order = getOrderById(id);
        return restTemplate
                .exchange(ORDER_RESOURCE_URL + SLASH + id, HttpMethod.PUT, new HttpEntity<>(order), Order.class)
                .getStatusCode();
    }

    public HttpStatus deleteOrder(Long id) {
        Order order = getOrderById(id);
        return restTemplate
                .exchange(ORDER_RESOURCE_URL + SLASH + id, HttpMethod.DELETE, new HttpEntity<>(order), Order.class)
                .getStatusCode();
    }

    public Order addProductToOrder(Long orderId, Long productId) {
        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("orderId", orderId);
        pathVariables.put("productId", productId);
        return restTemplate
                .exchange(
                        ORDER_RESOURCE_URL + PRODUCT_RESOURCE_URL,
                        HttpMethod.PUT,
                        null,
                        Order.class,
                        pathVariables
                ).getBody();
    }

    public Order deleteProductFromOrder(Long orderId, Long productId) {
        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("orderId", orderId);
        pathVariables.put("productId", productId);
        return restTemplate
                .exchange(
                        ORDER_RESOURCE_URL + PRODUCT_RESOURCE_URL,
                        HttpMethod.DELETE,
                        null,
                        Order.class,
                        pathVariables
                ).getBody();
    }

}
