package com.epam.webserviceconsuming.client;


import com.epam.webserviceconsuming.entity.CustomPage;
import com.epam.webserviceconsuming.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderClient implements CommandLineRunner {

    private static final String ORDER_RESOURCE_URL = "http://localhost:8088/orders";
    private static final String SLASH = "/";
    private static final String PRODUCT_RESOURCE_URL = "/{orderId}/products/{productId}";
    private static final Long GET_ORDER_ID = 3L;
    private static final Long SEND_ORDER_ID = 2L;
    private static final Long DELETE_ORDER_ID = 6L;
    private static final Long PRODUCT_ID = 2L;


    private static final Logger LOG = LoggerFactory.getLogger(OrderClient.class);

    private RestTemplate restTemplate;

    @Autowired
    public OrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) {
        LOG.info("Get order by id = " + GET_ORDER_ID);
        LOG.info(getOrderById(GET_ORDER_ID).toString());
        LOG.info("Get first page of order");
        getAllOrders();
        LOG.info("Create new order");
        createOrder();
        LOG.info("Send order with id = " + SEND_ORDER_ID);
        sendOrder(SEND_ORDER_ID);
        LOG.info("Delete order with id = " + DELETE_ORDER_ID);
        deleteOrder(DELETE_ORDER_ID);
        LOG.info("Add product with id = " + PRODUCT_ID + " to order with id = " + GET_ORDER_ID);
        addProductToOrder(GET_ORDER_ID, PRODUCT_ID);
        LOG.info("Delete product with id = " + PRODUCT_ID + " from order with id = " + GET_ORDER_ID);
        deleteProductFromOrder(GET_ORDER_ID, PRODUCT_ID);
    }

    private Order getOrderById(Long id) {
        Order response = restTemplate.getForObject(ORDER_RESOURCE_URL + SLASH + id, Order.class);
        return response;
    }

    private void getAllOrders() {
        CustomPage<Order> orders = restTemplate.exchange(
                ORDER_RESOURCE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CustomPage<Order>>() {
                }
        ).getBody();
        orders.getContent().forEach(order -> LOG.info(order.toString()));
    }

    private void createOrder() {
        Order order = new Order();
        order.setUserName("New order");
        HttpStatus response = restTemplate.postForEntity(ORDER_RESOURCE_URL, order, Order.class).getStatusCode();
        LOG.info(response.toString());
    }

    private void sendOrder(Long id) {
        Order order = getOrderById(id);
        HttpStatus response = restTemplate
                .exchange(ORDER_RESOURCE_URL + SLASH + id, HttpMethod.PUT, new HttpEntity<>(order), Order.class)
                .getStatusCode();
        LOG.info(response.toString());
    }

    private void deleteOrder(Long id) {
        Order order = getOrderById(id);
        HttpStatus response = restTemplate
                .exchange(ORDER_RESOURCE_URL + SLASH + id, HttpMethod.DELETE, new HttpEntity<>(order), Order.class)
                .getStatusCode();
        LOG.info(response.toString());
    }

    private void addProductToOrder(Long orderId, Long productId) {
        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("orderId", orderId);
        pathVariables.put("productId", productId);
        Order response = restTemplate
                .exchange(
                        ORDER_RESOURCE_URL + PRODUCT_RESOURCE_URL,
                        HttpMethod.PUT,
                        null,
                        Order.class,
                        pathVariables
                ).getBody();
        LOG.info(response.toString());
    }

    private void deleteProductFromOrder(Long orderId, Long productId) {
        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("orderId", orderId);
        pathVariables.put("productId", productId);
        Order response = restTemplate
                .exchange(
                        ORDER_RESOURCE_URL + PRODUCT_RESOURCE_URL,
                        HttpMethod.DELETE,
                        null,
                        Order.class,
                        pathVariables
                ).getBody();
        LOG.info(response.toString());
    }

}
