package com.timkontrakan.kantinkom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class DrinkDetailAct extends AppCompatActivity {


    TextView nama_drink, short_desc, harga, penjual;
    ImageView bg_drink, minuman, btn_back;
    Button btn_checkout;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);

        btn_back = findViewById(R.id.btn_back);
        nama_drink = findViewById(R.id.nama_drink);
        short_desc = findViewById(R.id.short_desc);
        harga = findViewById(R.id.harga);
        penjual = findViewById(R.id.penjual);
        bg_drink = findViewById(R.id.bg_drink);
        minuman = findViewById(R.id.minuman);
        btn_checkout = findViewById(R.id.btn_checkout);

        //Mengambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_minuman_baru = bundle.getString("jenis_minuman");
        reference = FirebaseDatabase.getInstance().getReference().child("Drink").child(jenis_minuman_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_drink.setText(dataSnapshot.child("nama_drink").getValue().toString());
                harga.setText(dataSnapshot.child("harga").getValue().toString() + "/ Porsi");
                penjual.setText(dataSnapshot.child("penjual").getValue().toString());
                short_desc.setText(dataSnapshot.child("short_desc").getValue().toString());

                try {
                    Picasso.with(DrinkDetailAct.this)
                            .load(dataSnapshot.child("url_thumbnail").getValue().toString())
                            .centerCrop().fit().into(minuman);
                    Picasso.with(DrinkDetailAct.this)
                            .load(dataSnapshot.child("bg_drink").getValue().toString())
                            .centerCrop().fit().into(bg_drink);
                }catch (NullPointerException ignored){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodrinkcheckout = new Intent(DrinkDetailAct.this, DrinkCheckoutAct.class);
                gotodrinkcheckout.putExtra("jenis_minuman", jenis_minuman_baru);
                startActivity(gotodrinkcheckout);
                finish();
            }
        });



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomenustart = new Intent(DrinkDetailAct.this, DrinkAct.class);
                startActivity(gotomenustart);
                finish();
            }
        });
    }
}
