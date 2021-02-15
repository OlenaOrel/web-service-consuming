package com.epam.webserviceconsuming.client;

import com.epam.webserviceconsuming.entity.CustomPage;
import com.epam.webserviceconsuming.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class DefaultCommandLineRunner implements CommandLineRunner {

    private static final Long GET_ORDER_ID = 3L;
    private static final Long SEND_ORDER_ID = 2L;
    private static final Long DELETE_ORDER_ID = 4L;
    private static final Long PRODUCT_ID = 2L;

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCommandLineRunner.class);

    private OrderClient orderClient;

    @Autowired
    public DefaultCommandLineRunner(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @Override
    public void run(String... args) {
        LOG.info("Get order by id = " + GET_ORDER_ID);
        LOG.info(orderClient.getOrderById(GET_ORDER_ID).toString());

        LOG.info("Get first page of order");
        printAllOrders(orderClient.getAllOrders());

        LOG.info("Create new order");
        LOG.info(orderClient.createOrder().toString());

        LOG.info("Send order with id = " + SEND_ORDER_ID);
        LOG.info(orderClient.sendOrder(SEND_ORDER_ID).toString());

        LOG.info("Delete order with id = " + DELETE_ORDER_ID);
        LOG.info(orderClient.deleteOrder(DELETE_ORDER_ID).toString());

        LOG.info("Add product with id = " + PRODUCT_ID + " to order with id = " + GET_ORDER_ID);
        LOG.info(orderClient.addProductToOrder(GET_ORDER_ID, PRODUCT_ID).toString());

        LOG.info("Delete product with id = " + PRODUCT_ID + " from order with id = " + GET_ORDER_ID);
        LOG.info(orderClient.deleteProductFromOrder(GET_ORDER_ID, PRODUCT_ID).toString());
    }

    private void printAllOrders(CustomPage<Order> orderPage) {
        orderPage.getContent()
                .forEach(order -> LOG.info(order.toString()));
    }

}
