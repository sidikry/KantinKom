package com.timkontrakan.kantinkom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.TextView;

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

import java.util.ArrayList;

public class MyProfileAct extends AppCompatActivity {
    Button btn_edit;
    ImageView btn_add_photo, btn_back, photo_edit_profile;
    EditText xnama_lengkap, xemail_address, x_password, x_status;

    Uri photo_location;
    Integer photo_max = 1;


    DatabaseReference reference;
    StorageReference storageReference;

    String USER_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        xnama_lengkap = findViewById(R.id.xnama_lengkap);
        xemail_address = findViewById(R.id.xemail_address);
        x_password = findViewById(R.id.x_password);
        x_status = findViewById(R.id.x_status);
        btn_add_photo = findViewById(R.id.btn_add_photo);
        photo_edit_profile = findViewById(R.id.photo_edit_profile);
        btn_edit = findViewById(R.id.btn_edit);

        storageReference = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                xemail_address.setText(dataSnapshot.child("email_address").getValue().toString());
                x_password.setText(dataSnapshot.child("password").getValue().toString());
                x_status.setText(dataSnapshot.child("bio").getValue().toString());

                try {
                    Picasso.with(MyProfileAct.this).load(dataSnapshot.child("url_photo_profile")
                            .getValue().toString())
                            .centerCrop()
                            .fit().into(photo_edit_profile);
                }catch (NullPointerException ignored){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ubah State menjadi Loading
                btn_edit.setEnabled(false);
                btn_edit.setText("Loading ...");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_lengkap").setValue(xnama_lengkap.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(xemail_address.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(x_password.getText().toString());
                        dataSnapshot.getRef().child("bio").setValue(x_status.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if (photo_location !=null){
                    final StorageReference storageReference1 = storageReference
                            .child(System.currentTimeMillis()
                                    + "." + getFileExtension(photo_location));

                    storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String uri_photo = taskSnapshot.getUploadSessionUri().toString();
                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        }
                    });
                }
                //Berpindah Activity
                Intent gotomyprofile = new Intent(MyProfileAct.this, MyProfileAct.class);
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
                Intent gotoprofile = new Intent(MyProfileAct.this, MenuActStart.class);
                startActivity(gotoprofile);
                finish();
            }
        });


    }
    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
