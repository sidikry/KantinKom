package com.timkontrakan.kantinkom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeAct extends AppCompatActivity {

    private String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        ImageView btn_nasgor = findViewById(R.id.btn_nasgor);
        ImageView btn_bakso = findViewById(R.id.btn_bakso);
        ImageView btn_tahu = findViewById(R.id.btn_tahu);
        ImageView btn_mie = findViewById(R.id.btn_mie);
        ImageView btn_soto = findViewById(R.id.btn_soto);
        ImageView btn_back = findViewById(R.id.btn_back);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        btn_soto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotofooddetail = new Intent(HomeAct.this, FoodDetailAct.class);
                gotofooddetail.putExtra("jenis_makanan", "Soto");
                startActivity(gotofooddetail);
                finish();
            }
        });

        btn_nasgor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotofooddetail = new Intent(HomeAct.this, FoodDetailAct.class);
                gotofooddetail.putExtra("jenis_makanan", "Nasgor");
                startActivity(gotofooddetail);
                finish();
            }
        });

        btn_bakso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotofooddetail = new Intent(HomeAct.this, FoodDetailAct.class);
                gotofooddetail.putExtra("jenis_makanan", "Bakso");
                startActivity(gotofooddetail);
                finish();
            }
        });

        btn_tahu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotofooddetail = new Intent(HomeAct.this, FoodDetailAct.class);
                gotofooddetail.putExtra("jenis_makanan", "Tahu");
                startActivity(gotofooddetail);
                finish();
            }
        });

        btn_mie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotofooddetail = new Intent(HomeAct.this, FoodDetailAct.class);
                gotofooddetail.putExtra("jenis_makanan", "Mie");
                startActivity(gotofooddetail);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomenustart = new Intent(HomeAct.this, MenuActStart.class);
                startActivity(gotomenustart);
                finish();
            }
        });
    }

    private void getUsernameLocal() {
        String USER_KEY = "usernamekey";
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        String username_key = "";
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}

