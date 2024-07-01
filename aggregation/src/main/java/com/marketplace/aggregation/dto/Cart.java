package com.marketplace.aggregation.dto;

public class Cart {
    private String id;
    private String kodeKeranjang;
    private String kodeProduk;
    private Integer jumlahBarang;
    private double subtotal;
    private boolean status;
    private Product productDetails;
    
    private double hargaBarang;
    
    public double getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(double hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

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
    public String getKodeProduk() {
        return kodeProduk;
    }
    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
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
    public Product getProductDetails() {
        return productDetails;
    }
    public void setProductDetails(Product productDetails) {
        this.productDetails = productDetails;
    }

}
