package com.marketplace.aggregation.dto;

public class Cart {
    private String id;
    private String kodeKeranjang;
    private Long kodeProduk;
    private Product productDetails;
    private Integer jumlahBarang;
    private double subtotal;
    private boolean status;
    
    private double hargaBarang;

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

    public Product getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Product productDetails) {
        this.productDetails = productDetails;
    }

    public Integer getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(Integer jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
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

    public double getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(double hargaBarang) {
        this.hargaBarang = hargaBarang;
    }
    

}
