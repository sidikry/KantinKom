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

public class HematDetailAct extends AppCompatActivity {
    private TextView nama_hemat;
    private TextView short_desc;
    private TextView harga;
    private TextView penjual;
    private ImageView bg_hemat;
    private ImageView hemat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hemat_detail);

        nama_hemat = findViewById(R.id.nama_hemat);
        short_desc = findViewById(R.id.short_desc);
        harga = findViewById(R.id.harga);
        penjual = findViewById(R.id.penjual);
        bg_hemat = findViewById(R.id.bg_hemat);
        ImageView btn_back = findViewById(R.id.btn_back);
        hemat = findViewById(R.id.hemat);
        Button btn_checkout = findViewById(R.id.btn_checkout);

        //Mengambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_hemat_baru = bundle.getString("jenis_hemat");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Hemat").child(jenis_hemat_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_hemat.setText(dataSnapshot.child("nama_hemat").getValue().toString());
                harga.setText(dataSnapshot.child("harga").getValue().toString() + "/ Porsi");
                penjual.setText(dataSnapshot.child("penjual").getValue().toString());
                short_desc.setText(dataSnapshot.child("short_desc").getValue().toString());

                try {
                    Picasso.with(HematDetailAct.this)
                            .load(dataSnapshot.child("url_thumbnail").getValue().toString())
                            .centerCrop().fit().into(hemat);
                    Picasso.with(HematDetailAct.this)
                            .load(dataSnapshot.child("bg_hemat").getValue().toString())
                            .centerCrop().fit().into(bg_hemat);
                } catch (NullPointerException ignored) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodrinkcheckoout = new Intent(HematDetailAct.this, HematCheckoutAct.class);
                gotodrinkcheckoout.putExtra("jenis_hemat", jenis_hemat_baru);
                startActivity(gotodrinkcheckoout);
                finish();
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohemat = new Intent(HematDetailAct.this, HematAct.class);
                startActivity(gotohemat);
                finish();
            }
        });
    }
}
