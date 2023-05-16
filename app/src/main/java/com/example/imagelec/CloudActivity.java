package com.example.imagelec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Timer;
import java.util.TimerTask;

public class CloudActivity extends AppCompatActivity {

    ImageView imageView;
    Button uploadImage;
    Button addImage;
    Uri imageUri;
    StorageReference storageReference;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud);
        imageView = findViewById(R.id.imageview);
        uploadImage = findViewById(R.id.btn_upload);
        addImage = findViewById(R.id.btn_add_image);
        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.GONE);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();

            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();

                progressBar.setVisibility(View.VISIBLE);

//
            }
        });
        }


            public void selectImage() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 100);
            }


            public void uploadImage() {
            storageReference = FirebaseStorage.getInstance().getReference("images/");
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);

                            imageView.setImageURI(null);
                            Toast.makeText(CloudActivity.this, "Uploaded", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CloudActivity.this, "Failed", Toast.LENGTH_LONG).show();

                        }
                    });
        }


                @Override
                protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
                    super.onActivityResult(requestCode, resultCode, data);
                    if (requestCode == 100 && data != null && data.getData() != null) {
                        imageUri = data.getData();
                        imageView.setImageURI(imageUri);
                    }
                }
    }