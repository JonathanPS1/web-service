package com.marketplace.order.model;

import org.springframework.data.annotation.Id;

public class cart {
    @Id
    private String id;

    private String kodeKeranjang;
    private Long kodeProduk;
    private Integer jumlahBarang;
    private double hargaBarang;
    private double subtotal;
    private boolean status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodeKeranjang() {
        return kodeKeranjang;
    }

    public void setKodeKeranjang(String kodeKeranjang) {
        this.kodeKeranjang = kodeKeranjang;
    }

    public Long getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(Long kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public Integer getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(Integer jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public double getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(double hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
