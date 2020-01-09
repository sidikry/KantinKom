package com.timkontrakan.kantinkom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {
        Button btn_next;
        ImageView btn_back;

        DatabaseReference reference;
        EditText xusername, xemail_address, x_password;

        String USER_KEY ="usernamekey";
        String username_key ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
        btn_next = findViewById(R.id.btn_next);
        btn_back = findViewById(R.id.btn_back);
        xusername = findViewById(R.id.xusername);
        xemail_address = findViewById(R.id.xemail_address);
        x_password = findViewById(R.id.x_password);
//
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_next.setEnabled(false);
                btn_next.setText("Loading ...");

                //Simpan data ke lokal
                SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, xusername.getText().toString());
                editor.apply();

                //Simpan ke Database
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(xusername.getText().toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(xusername.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(xemail_address.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(x_password.getText().toString());
                        dataSnapshot.getRef().child("user_balance").setValue(100000);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent gotonext = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                startActivity(gotonext);
                finish();
            }
        });
//
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotologin = new Intent(RegisterOneAct.this, LoginAct.class);
                startActivity(gotologin);
                finish();
            }
        });
    }
}
