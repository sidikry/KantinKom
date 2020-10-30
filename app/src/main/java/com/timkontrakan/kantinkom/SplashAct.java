package com.timkontrakan.kantinkom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getUsernameLocal();

        //Load Animation
        Animation app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        Animation btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //load Element
        ImageView app_logo = findViewById(R.id.app_logo);
        TextView app_sub_title = findViewById(R.id.app_sub_title);

        //run animation
        app_logo.startAnimation(app_splash);
        app_sub_title.startAnimation(btt);

    }

    private void getUsernameLocal() {
        String USER_KEY = "usernamekey";
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        String username_key = "";
        String username_key_new = sharedPreferences.getString(username_key, "");

        if (username_key_new.isEmpty()) {
            // Membuat timer untuk pindah activity secara otomatis
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Menuju halaman lain
                    Intent abc = new Intent(SplashAct.this, MainActivity.class);
                    startActivity(abc);
                    finish();
                }
            }, 2000); //2000 ms = 2s

        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent gogethome = new Intent(SplashAct.this, MenuActStart.class);
                    startActivity(gogethome);
                    finish();
                }
            }, 2000);
        }
    }
}
