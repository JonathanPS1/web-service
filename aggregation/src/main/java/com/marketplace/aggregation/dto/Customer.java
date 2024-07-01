package com.marketplace.aggregation.dto;

public class Customer {
    private Long id;
    private String nama;
    private String email;
    private String alamat;
    private String no_hp;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public String getNo_hp() {
        return no_hp;
    }
    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }


}
