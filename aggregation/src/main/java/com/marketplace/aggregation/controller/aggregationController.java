package com.marketplace.aggregation.controller;

import com.marketplace.aggregation.dto.Customer;
import com.marketplace.aggregation.dto.Order;
import com.marketplace.aggregation.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/aggregation")
public class aggregationController {

    private final String customerServiceUrl = "http://localhost:8081/customer";
    private final String productServiceUrl = "http://localhost:8082/product/{productId}";
    private final String orderServiceUrl = "http://localhost:8083/order/{orderId}";

    @Autowired
    private RestTemplate restTemplate;

    // Endpoint untuk mendapatkan detail customer berdasarkan ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Object> getCustomerDetails(@PathVariable String customerId) {
        try {
            Customer customer = restTemplate.getForObject(customerServiceUrl + "/{customerId}", Customer.class, customerId);
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

    // Endpoint untuk menambah customer
    @PostMapping("/customer/addCustomer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(customerServiceUrl + "/addCustomer", customer, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Customer berhasil ditambahkan");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menambahkan customer");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk mengupdate customer berdasarkan ID
    @PutMapping("/customer/updateCustomer/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable String customerId, @RequestBody Customer customer) {
        try {
            restTemplate.put(customerServiceUrl + "/updateCustomer/{customerId}", customer, customerId);
            return ResponseEntity.ok("Customer berhasil diperbarui");
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menghapus customer berdasarkan ID
    @DeleteMapping("/customer/deleteCustomer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customerId) {
        try {
            restTemplate.delete(customerServiceUrl + "/deleteCustomer/{customerId}", customerId);
            return ResponseEntity.ok("Customer berhasil dihapus");
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk mendapatkan detail product berdasarkan ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<Object> getProductDetails(@PathVariable String productId) {
        try {
            Product product = restTemplate.getForObject(productServiceUrl, Product.class, productId);
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

    // Endpoint untuk mendapatkan detail order berdasarkan ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Object> getOrderDetails(@PathVariable String orderId) {
        try {
            Order order = restTemplate.getForObject(orderServiceUrl, Order.class, orderId);
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
