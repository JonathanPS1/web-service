package com.marketplace.order.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class order {
    @Id
    private String id;

    private String kodeTransaksi;
    private String kodeCustomer;
    private String kodeKeranjang; // Ambil cart
    private String tanggalPembelian;
    private List<cart> detailCart;
    private Double total;
    private Double pembayaran;
    private Double kembalian;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getKodeCustomer() {
        return kodeCustomer;
    }

    public void setKodeCustomer(String kodeCustomer) {
        this.kodeCustomer = kodeCustomer;
    }

    public String getTanggalPembelian() {
        return tanggalPembelian;
    }

    public void setTanggalPembelian(String tanggalPembelian) {
        this.tanggalPembelian = tanggalPembelian;
    }

    public List<cart> getDetailCart() {
        return detailCart;
    }

    public void setDetailCart(List<cart> detailCart) {
        this.detailCart = detailCart;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(Double pembayaran) {
        this.pembayaran = pembayaran;
    }

    public Double getKembalian() {
        return kembalian;
    }

    public void setKembalian(Double kembalian) {
        this.kembalian = kembalian;
    }

    public String getKodeKeranjang() {
        return kodeKeranjang;
    }

    public void setKodeKeranjang(String kodeKeranjang) {
        this.kodeKeranjang = kodeKeranjang;
    }
}
