package com.marketplace.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketplace.product.model.product;

@Repository
public interface productRepository extends JpaRepository<product, Long> {
}
