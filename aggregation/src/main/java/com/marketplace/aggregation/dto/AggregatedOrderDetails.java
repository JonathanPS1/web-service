package com.marketplace.aggregation.dto;

public class AggregatedOrderDetails {
    private Order order;
    private Customer customer;
    
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
    

}
