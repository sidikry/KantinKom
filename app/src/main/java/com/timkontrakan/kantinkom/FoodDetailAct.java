package com.timkontrakan.kantinkom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class FoodDetailAct extends AppCompatActivity {

    TextView nama_food, short_desc, harga, penjual;
    ImageView bg_food, makanan, btn_back;
    Button btn_checkout;
    DatabaseReference reference, reference2, ref_username;

    String USER_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    //Generate Nomor agar dapat id
    Integer nomor_transaksi = new Random().nextInt();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        getUsernameLocal();

        makanan = findViewById(R.id.makanan);
        bg_food = findViewById(R.id.bg_food);
        nama_food = findViewById(R.id.nama_food);
        short_desc = findViewById(R.id.short_desc);
        harga = findViewById(R.id.harga);
        penjual = findViewById(R.id.penjual);
        btn_checkout = findViewById(R.id.btn_checkout);
        btn_back = findViewById(R.id.btn_back);

        //Mengambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_makanan_baru = bundle.getString("jenis_makanan");

       //Ambil Data dari Firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Food").child(jenis_makanan_baru);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_food.setText(dataSnapshot.child("nama_food").getValue().toString());
                harga.setText(dataSnapshot.child("harga").getValue().toString() + "/ Porsi");
                penjual.setText(dataSnapshot.child("penjual").getValue().toString());
                short_desc.setText(dataSnapshot.child("short_desc").getValue().toString());

                    try {
                        Picasso.with(FoodDetailAct.this)
                                .load(dataSnapshot.child("url_thumbnail").getValue().toString())
                                .centerCrop().fit().into(makanan);
                        Picasso.with(FoodDetailAct.this)
                                .load(dataSnapshot.child("bg_food").getValue().toString())
                                .centerCrop().fit().into(bg_food);
                    }catch (NullPointerException ignored){

                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(FoodDetailAct.this, MenuActStart.class);
                startActivity(gotohome);
                finish();
            }
        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocheckout = new Intent(FoodDetailAct.this, MyCartAct.class);
                gotocheckout.putExtra("jenis_makanan", jenis_makanan_baru);
                startActivity(gotocheckout);
                finish();
            }
        });
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
