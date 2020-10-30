package com.timkontrakan.kantinkom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;

public class HematAct extends AppCompatActivity {

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hemat);
        getUsernameLocal();
        ImageView btn_telur = findViewById(R.id.btn_telur);
        ImageView btn_rolur = findViewById(R.id.btn_rolur);
        ImageView btn_roti = findViewById(R.id.btn_roti);
        ImageView btn_sate = findViewById(R.id.btn_sate);
        ImageView btn_kolak = findViewById(R.id.btn_kolak);
        ImageView btn_back = findViewById(R.id.btn_back);

        btn_telur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohematdetail = new Intent(HematAct.this, HematDetailAct.class);
                gotohematdetail.putExtra("jenis_hemat", "Telur");
                startActivity(gotohematdetail);
                finish();
            }
        });
        btn_rolur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohematdetail = new Intent(HematAct.this, HematDetailAct.class);
                gotohematdetail.putExtra("jenis_hemat", "Rolur");
                startActivity(gotohematdetail);
                finish();
            }
        });
        btn_roti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohematdetail = new Intent(HematAct.this, HematDetailAct.class);
                gotohematdetail.putExtra("jenis_hemat", "Roti");
                startActivity(gotohematdetail);
                finish();
            }
        });
        btn_sate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohematdetail = new Intent(HematAct.this, HematDetailAct.class);
                gotohematdetail.putExtra("jenis_hemat", "Sate");
                startActivity(gotohematdetail);
                finish();
            }
        });
        btn_kolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohematdetail = new Intent(HematAct.this, HematDetailAct.class);
                gotohematdetail.putExtra("jenis_hemat", "Kolak");
                startActivity(gotohematdetail);
                finish();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohematdetail = new Intent(HematAct.this, MenuActStart.class);
                startActivity(gotohematdetail);
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
