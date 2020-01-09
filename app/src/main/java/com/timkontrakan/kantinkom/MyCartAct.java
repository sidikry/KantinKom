package com.timkontrakan.kantinkom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Random;

public class MyCartAct extends AppCompatActivity {

    Button btn_bayar, btn_plus, btn_minus;
    TextView nama_item, penjual, harga, my_balance, harga_food, jumlah_food;
    ImageView notice_uang, btn_back;
    Integer valuejumlahfood = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargafood = 0;
    Integer sisa_balance = 0;

    String USER_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    //Generate Nomor agar dapat id
    Integer nomor_transaksi = new Random().nextInt();
    DatabaseReference reference, reference2, reference3, reference4,ref_username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        getUsernameLocal();

        //Ambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_makanan_baru = bundle.getString("jenis_makanan");

        btn_bayar = findViewById(R.id.btn_bayar);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_back = findViewById(R.id.btn_back);
        nama_item = findViewById(R.id.nama_item);
        penjual = findViewById(R.id.penjual);
        harga = findViewById(R.id.harga);
        my_balance = findViewById(R.id.my_balance);
        harga_food = findViewById(R.id.harga_food);
        notice_uang = findViewById(R.id.notice_uang);
        jumlah_food = findViewById(R.id.jumlah_food);

        //Setting Value Baru untuk beberapa komponen
        jumlah_food.setText(valuejumlahfood.toString());

        //Hide Button Minus
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        //Mengambil data user dari firebase 2
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                my_balance.setText("RP "+mybalance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Ambil data dari reference 1
        reference = FirebaseDatabase.getInstance().getReference().child("Food").child(jenis_makanan_baru);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_item.setText(dataSnapshot.child("nama_food").getValue().toString());
                penjual.setText(dataSnapshot.child("penjual").getValue().toString());
                valuehargafood = Integer.valueOf(dataSnapshot.child("harga").getValue().toString());
                valuetotalharga = valuehargafood * valuejumlahfood;
                harga_food.setText("RP "+valuetotalharga+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                valuejumlahfood+=1;
                jumlah_food.setText(valuejumlahfood.toString());
                if (valuejumlahfood > 1){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                valuetotalharga = valuehargafood * valuejumlahfood;
                harga_food.setText("RP "+ valuetotalharga+"");
                if (valuetotalharga > mybalance){
                    btn_bayar.animate().translationY(250)
                            .alpha(0).setDuration(350).start();
                    btn_bayar.setEnabled(false);
                    my_balance.setTextColor(Color.parseColor("#d1206b"));

                    notice_uang.setVisibility(View.VISIBLE);
                }

            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuejumlahfood-=1;
                jumlah_food.setText(valuejumlahfood.toString());

                if (valuejumlahfood < 2){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }
                valuetotalharga = valuehargafood * valuejumlahfood;
                harga_food.setText("RP "+ valuetotalharga+"");
                if (valuetotalharga < mybalance){
                    btn_bayar.animate().translationY(0)
                            .alpha(1).setDuration(350).start();
                    btn_bayar.setEnabled(true);
                    my_balance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }
            }
        });

        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyFoods")
                        .child(username_key_new).child("food").child(nama_item.getText().toString() + nomor_transaksi);
                reference3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_item.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_food").setValue(nama_item.getText().toString());
                        reference3.getRef().child("penjual").setValue(penjual.getText().toString());
                        reference3.getRef().child("jumlah_food").setValue(jumlah_food.getText().toString());

                        //Fungsi Intent
                        Intent gotosucces = new Intent(MyCartAct.this, SuccesBuyFoodAct.class);
                        startActivity(gotosucces);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ref_username = FirebaseDatabase.getInstance().getReference().child("MyFoods").child(username_key_new).child("username");
                ref_username.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ref_username.getRef().setValue(username_key_new);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Update Data Sisa Balance kepada users yang sedang login
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = mybalance - valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(MyCartAct.this, MenuActStart.class);
                startActivity(gotohome);
                finish();
            }
        });
    }


    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}

