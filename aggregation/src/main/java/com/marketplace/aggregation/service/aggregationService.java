package com.marketplace.aggregation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.marketplace.aggregation.dto.AggregatedOrderDetails;
import com.marketplace.aggregation.dto.Cart;
import com.marketplace.aggregation.dto.Customer;
import com.marketplace.aggregation.dto.Order;
import com.marketplace.aggregation.dto.Product;

@Service
public class aggregationService {

    @Autowired
    private RestTemplate restTemplate;

    private final String transactionsUrl = "http://127.0.0.1:8083/transactions/order/";
    private final String customerUrl = "http://127.0.0.1:8081/customer/";
    private final String productUrl = "http://127.0.0.1:8082/product/";

    public AggregatedOrderDetails getAggregatedOrderDetails(String kodeTransaksi) {
        // Get order details from transactions service
        ResponseEntity<Order> orderResponse = restTemplate.getForEntity(transactionsUrl + kodeTransaksi, Order.class);
        if (!orderResponse.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        Order order = orderResponse.getBody();

        // Get customer details
        ResponseEntity<Customer> customerResponse = restTemplate.getForEntity(customerUrl + order.getKodeCustomer(), Customer.class);
        if (!customerResponse.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        Customer customer = customerResponse.getBody();

        // Get product details for each item in cart
        for (Cart cart : order.getDetailCart()) {
            ResponseEntity<Product> productResponse = restTemplate.getForEntity(productUrl + cart.getKodeProduk(), Product.class);
            if (productResponse.getStatusCode().is2xxSuccessful()) {
                cart.setProductDetails(productResponse.getBody());
            }
        }

        // Create aggregated response
        AggregatedOrderDetails aggregatedOrderDetails = new AggregatedOrderDetails();
        aggregatedOrderDetails.setOrder(order);
        aggregatedOrderDetails.setCustomer(customer);

        return aggregatedOrderDetails;
    }
}
