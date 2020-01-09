package com.timkontrakan.kantinkom;

public class MyCart {

    String nama_item, harga, penjual;

    public MyCart() {
    }

    public MyCart(String nama_item, String harga, String penjual) {
        this.nama_item = nama_item;
        this.harga = harga;
        this.penjual = penjual;
    }

    public String getNama_item() {
        return nama_item;
    }

    public void setNama_item(String nama_item) {
        this.nama_item = nama_item;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getPenjual() {
        return penjual;
    }

    public void setPenjual(String penjual) {
        this.penjual = penjual;
    }
}
