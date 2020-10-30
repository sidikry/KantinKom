package com.timkontrakan.kantinkom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwoAct extends AppCompatActivity {

    private Button btn_daftar;
    private EditText xnama_lengkap;
    private EditText x_bio;
    private ImageView photo_register;

    private Uri photo_location;
    private final Integer photo_max = 1;

    private DatabaseReference reference;
    private StorageReference storage;

    private String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
        getUsernameLocal();

        xnama_lengkap = findViewById(R.id.xnama_lengkap);
        x_bio = findViewById(R.id.x_bio);
        photo_register = findViewById(R.id.photo_register);
        Button btn_add_photo = findViewById(R.id.btn_add_photo);
        btn_daftar = findViewById(R.id.btn_daftar);


        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ubah State Menjadi Loading
                btn_daftar.setEnabled(false);
                btn_daftar.setText("Loading ...");

                //simpan ke database
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);

                //Validasi untuk file(Apakah Ada ?)
                if (photo_location != null) {
                    final StorageReference storageReference1 = storage
                            .child(System.currentTimeMillis() + "." + getFileExtension(photo_location));

                    storageReference1.putFile(photo_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    String uri_photo = taskSnapshot.getUploadSessionUri().toString();
                                    reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                    reference.getRef().child("nama_lengkap").setValue(xnama_lengkap);
                                    reference.getRef().child("bio").setValue(x_bio);

                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent gotosucces = new Intent(RegisterTwoAct.this, LoginAct.class);
                            startActivity(gotosucces);
                            finish();
                        }
                    });
                }


            }
        });
        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPhoto();
            }
        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void findPhoto() {
        Intent pic = new Intent();
        pic.setType("Image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
            ;
        {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(photo_register);
        }
    }

    private void getUsernameLocal() {
        String USER_KEY = "usernamekey";
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        String username_key = "";
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
