package com.timkontrakan.kantinkom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;

public class DrinkAct extends AppCompatActivity {

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        getUsernameLocal();

        ImageView btn_jeruk = findViewById(R.id.btn_jeruk);
        ImageView btn_kopi = findViewById(R.id.btn_kopi);
        ImageView btn_sirup = findViewById(R.id.btn_sirup);
        ImageView btn_stroberi = findViewById(R.id.btn_stoberi);
        ImageView btn_lemon = findViewById(R.id.btn_lemon);
        ImageView btn_back = findViewById(R.id.btn_back);

        btn_jeruk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodrinkdetail = new Intent(DrinkAct.this, DrinkDetailAct.class);
                gotodrinkdetail.putExtra("jenis_minuman", "Jeruk");
                startActivity(gotodrinkdetail);
                finish();
            }
        });
        btn_kopi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodrinkdetail = new Intent(DrinkAct.this, DrinkDetailAct.class);
                gotodrinkdetail.putExtra("jenis_minuman", "Kopi");
                startActivity(gotodrinkdetail);
                finish();
            }
        });
        btn_sirup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodrinkdetail = new Intent(DrinkAct.this, DrinkDetailAct.class);
                gotodrinkdetail.putExtra("jenis_minuman", "Sirup");
                startActivity(gotodrinkdetail);
                finish();
            }
        });
        btn_stroberi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodrinkdetail = new Intent(DrinkAct.this, DrinkDetailAct.class);
                gotodrinkdetail.putExtra("jenis_minuman", "Stroberi");
                startActivity(gotodrinkdetail);
                finish();
            }
        });
        btn_lemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodrinkdetail = new Intent(DrinkAct.this, DrinkDetailAct.class);
                gotodrinkdetail.putExtra("jenis_minuman", "Lemon");
                startActivity(gotodrinkdetail);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomenustart = new Intent(DrinkAct.this, MenuActStart.class);
                startActivity(gotomenustart);
                finish();
            }
        });
    }

    private void getUsernameLocal() {
        String USER_KEY = "usernamekey";
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        String username_key = "";
        String username_key_new = sharedPreferences.getString(username_key, "");
    }
}
