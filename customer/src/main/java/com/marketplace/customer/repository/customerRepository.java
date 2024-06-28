package com.marketplace.customer.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketplace.customer.model.customer;

@Repository
public interface customerRepository extends JpaRepository<customer, Long>{
    public List<customer> findByNama(String nama);
}
