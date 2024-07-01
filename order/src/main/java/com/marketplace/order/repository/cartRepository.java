package com.marketplace.order.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marketplace.order.model.cart;

public interface cartRepository extends MongoRepository<cart, String> {
    public boolean existsByKodeKeranjang(String kodeKeranjang);
   

    public boolean existsByKodeProduk(String kodeProduk);

    public List<cart> findByKodeKeranjang(String kodeKeranjang);

    public void deleteByKodeKeranjang(String kodeKeranjang);
    
}
