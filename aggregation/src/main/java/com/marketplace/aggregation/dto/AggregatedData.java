package com.marketplace.aggregation.dto;

import java.util.List;

public class AggregatedData {
    private Order order;
    private Customer customer;
    private List<Product> products; // Ubah dari Product menjadi List<Product>

    public AggregatedData(Order order, Customer customer, List<Product> products) {
        this.order = order;
        this.customer = customer;
        this.products = products;
    }

    // Getters and Setters
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
