package com.timkontrakan.kantinkom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TentangKamiAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_kami);

        ImageView btn_back = findViewById(R.id.btn_back);
        LinearLayout ig_apenk = findViewById(R.id.ig_apenk);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(TentangKamiAct.this, MenuActStart.class);
                startActivity(gotohome);
                finish();
            }
        });
    }

    public void instagram1() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/apejakno"));
        startActivity(intent);
    }

    public void instagram2() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/puasa_ngoding"));
        startActivity(intent);
    }

    public void instagram3() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/ssidik.28"));
        startActivity(intent);
    }
}
