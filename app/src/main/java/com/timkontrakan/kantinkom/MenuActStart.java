package com.timkontrakan.kantinkom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MenuActStart extends AppCompatActivity {

    private ImageView photo_home;
    private TextView nama_lengkap;
    private TextView userbalance;

    private final String USER_KEY = "usernamekey";
    private final String username_key = "";
    private String username_key_new = "";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_act_start);

        getUsernameLocal();
        ImageView makanan = findViewById(R.id.makanan);
        ImageView minuman = findViewById(R.id.minuman);
        ImageView hemat = findViewById(R.id.hemat);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        photo_home = findViewById(R.id.photo_home);
        userbalance = findViewById(R.id.userbalance);
        ImageView btn_tentang = findViewById(R.id.btn_tentang);
        ImageView btn_power = findViewById(R.id.btn_power);

        photo_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(MenuActStart.this, MyProfileAct.class);
                startActivity(gotoprofile);
                finish();
            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                userbalance.setText("RP " + dataSnapshot.child("user_balance").getValue().toString());
                try {
                    Picasso.with(MenuActStart.this).load(dataSnapshot.child("url_photo_profile")
                            .getValue().toString())
                            .centerCrop()
                            .fit().into(photo_home);
                } catch (NullPointerException ignored) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gototentang = new Intent(MenuActStart.this, TentangKamiAct.class);
                startActivity(gototentang);
                finish();
            }
        });

        btn_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Menghapus Isi / Nilai / Value dari username lokal
                SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);

                editor.apply();
                Intent gotoout = new Intent(MenuActStart.this, LoginAct.class);
                startActivity(gotoout);
                finish();
            }
        });

        makanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomakanan = new Intent(MenuActStart.this, HomeAct.class);
                startActivity(gotomakanan);
                finish();
            }
        });

        minuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotominuman = new Intent(MenuActStart.this, DrinkAct.class);
                startActivity(gotominuman);
                finish();
            }
        });

        hemat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohemat = new Intent(MenuActStart.this, HematAct.class);
                startActivity(gotohemat);
                finish();
            }
        });
    }

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }


}
