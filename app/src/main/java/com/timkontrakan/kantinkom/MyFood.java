package com.timkontrakan.kantinkom;

public class MyFood {
    String nama_food, is_by;
    String jumlah_food;

    public MyFood() {
    }

    public MyFood(String nama_food, String is_by, String jumlah_food) {
        this.nama_food = nama_food;
        this.is_by = is_by;
        this.jumlah_food = jumlah_food;
    }

    public String getNama_food() {

        return nama_food;
    }

    public void setNama_food(String nama_food) {
        this.nama_food = nama_food;
    }

    public String getIs_by() {

        return is_by;
    }

    public void setIs_by(String is_by) {
        this.is_by = is_by;
    }

    public String getJumlah_food() {

        return jumlah_food;
    }

    public void setJumlah_food(String jumlah_food) {
        this.jumlah_food = jumlah_food;
    }
}


