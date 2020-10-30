package com.timkontrakan.kantinkom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.util.Objects;

public class EditProfileAct extends AppCompatActivity {
    private Button btn_save;
    private EditText nama_lengkap;
    private EditText username;
    private EditText email_address;
    private EditText password;
    private ImageView photo_edit_profile;

    private Uri photo_location;

    private DatabaseReference reference;
    private StorageReference storageReference;
    private String username_key_new = "";

    public EditProfileAct(Uri photo_location) {
        this.photo_location = photo_location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getUsernameLocal();

        Button btn_back = findViewById(R.id.btn_back);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        username = findViewById(R.id.username);
        email_address = findViewById(R.id.email_address);
        password = findViewById(R.id.password);
        Button btn_add_photo = findViewById(R.id.btn_add_photo);
        btn_save = findViewById(R.id.btn_save);
        photo_edit_profile = findViewById(R.id.photo_edit_profile);

        storageReference = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(Objects.requireNonNull(dataSnapshot.child("nama_lengkap").getValue()).toString());
                username.setText(Objects.requireNonNull(dataSnapshot.child("username").getValue()).toString());
                email_address.setText(Objects.requireNonNull(dataSnapshot.child("email_address").getValue()).toString());
                password.setText(Objects.requireNonNull(dataSnapshot.child("password").getValue()).toString());
                try {
                    Picasso.with(EditProfileAct.this).load(Objects.requireNonNull(dataSnapshot.child("url_photo_profile")
                            .getValue()).toString())
                            .centerCrop()
                            .fit().into(photo_edit_profile);
                } catch (NullPointerException ignored) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                //Ubah State menjadi Loading
                btn_save.setEnabled(false);
                btn_save.setText("Loading ...");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if (photo_location != null) {
                    final StorageReference storageReference1 = storageReference
                            .child(System.currentTimeMillis()
                                    + "." + getFileExtension(photo_location));

                    storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String uri_photo = Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString();
                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        }
                    });
                }
                //Berpindah Activity
                Intent gotomyprofile = new Intent(EditProfileAct.this, MyProfileAct.class);
                startActivity(gotomyprofile);
                finish();


            }
        });

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPhoto();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(EditProfileAct.this, MyProfileAct.class);
                startActivity(gotoprofile);
                finish();
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
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        int photo_max = 1;
        startActivityForResult(pic, photo_max);
    }

    private void getUsernameLocal() {
        String USER_KEY = "usernamekey";
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        String username_key = "";
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
