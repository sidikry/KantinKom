package com.timkontrakan.kantinkom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyFoodDetailAct extends AppCompatActivity {

    Button btn_back;
    TextView xnama_food, xis_komposisi, x_isby, xketentuan, xharga;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food_detail);

        btn_back = findViewById(R.id.btn_back);
        xnama_food = findViewById(R.id.xnama_food);
        xis_komposisi = findViewById(R.id.xis_komposisi);
        x_isby = findViewById(R.id.x_isby);
        xketentuan = findViewById(R.id.xketentuan);
        xharga = findViewById(R.id.xharga);

        //Mengambil data dari bundle
        Bundle bundle = getIntent().getExtras();
        final String nama_makanan_baru = bundle.getString("nama_food");

        //Database
        reference = FirebaseDatabase.getInstance().getReference().child("Food").child(nama_makanan_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    xnama_food.setText(dataSnapshot.child("nama_food").getValue().toString());
                    x_isby.setText(dataSnapshot.child("is_by").getValue().toString());
                    xketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                    xharga.setText(dataSnapshot.child("harga").getValue().toString());
                    xis_komposisi.setText(dataSnapshot.child("is_komposisi").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(MyFoodDetailAct.this, MyProfileAct.class);
                startActivity(gotoprofile);
                finish();
            }
        });


    }
}
