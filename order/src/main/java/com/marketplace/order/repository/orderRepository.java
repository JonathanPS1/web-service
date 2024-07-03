package com.marketplace.order.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.marketplace.order.model.order;

public interface orderRepository extends MongoRepository<order, String> {
    public boolean existsByKodeTransaksi(String kodeTransaksi);

    public List<order> findByKodeTransaksi(String kodeTransaksi);

    public void deleteByKodeTransaksi(String kodeTransaksi);

    @Query("{'kodeCustomer' : ?0 }") // ?0 indeks parameter pertama
    List<order> findByPelanggan(Integer kodeCustomer);

    @Query("{'tanggalPembelian' : ?0 }")
    List<order> findByTglPembelian(String tanggalPembelian);
}
