package com.marketplace.aggregation.controller;

import com.marketplace.aggregation.dto.Customer;
import com.marketplace.aggregation.dto.Order;
import com.marketplace.aggregation.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/aggregation")
public class aggregationController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Object> getCustomerDetails(@PathVariable String customerId) {
        try {
            Customer customer = restTemplate.getForObject("http://localhost:8081/customer/{customerId}", Customer.class, customerId);
            if (customer != null) {
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Object> getProductDetails(@PathVariable String productId) {
        try {
            Product product = restTemplate.getForObject("http://localhost:8082/product/{productId}", Product.class, productId);
            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Produk Tidak Ditemukan");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Produk Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Object> getOrderDetails(@PathVariable String orderId) {
        try {
            Order order = restTemplate.getForObject("http://localhost:8083/order/{orderId}", Order.class, orderId);
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Order Tidak Ditemukan");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Order Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }
}
