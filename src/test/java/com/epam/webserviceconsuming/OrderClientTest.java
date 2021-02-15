package com.epam.webserviceconsuming;

import com.epam.webserviceconsuming.client.OrderClient;
import com.epam.webserviceconsuming.entity.CustomPage;
import com.epam.webserviceconsuming.entity.Order;
import com.epam.webserviceconsuming.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class OrderClientTest {

    private static final long ORDER_ID = 0L;
    private static final long PRODUCT_ID = 0L;

    @InjectMocks
    private OrderClient testInstance;

    @Mock
    private Order order;

    @Mock
    private Product product;

    @Mock
    private Product product1;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CustomPage<Order> orderPage;

    @Mock
    private List<Order> orderList;

    @Mock
    private List<Product> productList;

    @Mock
    private ResponseEntity<Order> response;

    @Test
    void shouldReturnOrderWhenGetOrderById() {
        doReturn(order).when(restTemplate).getForObject(anyString(), any());

        Order result = testInstance.getOrderById(ORDER_ID);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(order);
    }

    @Test
    void shouldReturnListOfOrderWhenGetAllOrders() {
        doReturn(response).when(restTemplate)
                .exchange(anyString(), any(HttpMethod.class), any(), any(ParameterizedTypeReference.class));
        doReturn(orderPage).when(response).getBody();
        doReturn(orderList).when(orderPage).getContent();

        CustomPage<Order> result = testInstance.getAllOrders();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(orderPage);
        assertThat(result.getContent()).isEqualTo(orderList);
    }

    @Test
    void shouldReturnHttpStatusWhenCreateOrder() {
        doReturn(response).when(restTemplate).postForEntity(anyString(), any(Order.class), any(Order.class.getClass()));
        doReturn(HttpStatus.CREATED).when(response).getStatusCode();

        HttpStatus result = testInstance.createOrder();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnHttpStatusWhenSendOrder() {
        doReturn(response).when(restTemplate)
                .exchange(anyString(), any(HttpMethod.class), any(), any(Order.class.getClass()));
        doReturn(HttpStatus.OK).when(response).getStatusCode();

        HttpStatus result = testInstance.sendOrder(ORDER_ID);

        assertThat(result).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnHttpStatusWhenDeleteOrder() {
        doReturn(response).when(restTemplate)
                .exchange(anyString(), any(HttpMethod.class), any(), any(Order.class.getClass()));
        doReturn(HttpStatus.NO_CONTENT).when(response).getStatusCode();

        HttpStatus result = testInstance.deleteOrder(ORDER_ID);

        assertThat(result).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnOrderWhenAddProductToOrder() {
        doReturn(response).when(restTemplate)
                .exchange(anyString(), any(HttpMethod.class), any(), any(Order.class.getClass()), anyMap());
        doReturn(order).when(response).getBody();
        doReturn(productList).when(order).getProducts();
        doReturn(product).when(productList).get(0);
        doReturn(PRODUCT_ID).when(product).getId();

        Order result = testInstance.addProductToOrder(ORDER_ID, PRODUCT_ID);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(order);
        assertThat(result.getProducts().get(0)).isEqualTo(product);
        assertThat(result.getProducts().get(0).getId()).isEqualTo(PRODUCT_ID);
    }

    @Test
    void shouldReturnOrderWhenDeleteProductFromOrder() {
        doReturn(response).when(restTemplate)
                .exchange(anyString(), any(HttpMethod.class), any(), any(Order.class.getClass()), anyMap());
        doReturn(order).when(response).getBody();
        doReturn(productList).when(order).getProducts();
        doReturn(product1).when(productList).get(0);

        Order result = testInstance.deleteProductFromOrder(ORDER_ID, PRODUCT_ID);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(order);
        assertThat(result.getProducts().get(0)).isNotEqualTo(product);
    }

}
