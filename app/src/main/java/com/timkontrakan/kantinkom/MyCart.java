package com.timkontrakan.kantinkom;

class MyCart {

    private String nama_item;
    private String harga;
    private String penjual;

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
