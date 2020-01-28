package com.jdc.lokerriau.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import com.jdc.lokerriau.Activities.Lowongan;
import com.jdc.lokerriau.R;

public class UploadActivity extends AppCompatActivity{

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button chooseImageBtn;
    private Button uploadBtn;
    private EditText instansiEditText;
    private EditText namaEditText;
    private EditText jenisEditText;
    private EditText posisiEditText;
    private EditText gajiEditText;
    private EditText bukaEditText;
    private EditText tutupEditText;
    private EditText ketEditText;
    private ImageView chosenImageView;
    private ProgressBar uploadProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_upload );

        chooseImageBtn = findViewById(R.id.button_choose_image);
        uploadBtn = findViewById(R.id.uploadBtn);

        instansiEditText = findViewById(R.id.instansiEditText);
        namaEditText = findViewById(R.id.namaEditText);
        jenisEditText = findViewById ( R.id.jenisEditText );
        posisiEditText = findViewById ( R.id.posisiEditText );
        gajiEditText = findViewById ( R.id.gajiEditText );
        bukaEditText = findViewById ( R.id.bukaEditText );
        tutupEditText = findViewById ( R.id.tutupEditText );
        ketEditText = findViewById ( R.id.ketEditText );
        chosenImageView = findViewById(R.id.chosenImageView);
        uploadProgressBar = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("lowongan");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("lowongan");

        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UploadActivity.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(chosenImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            uploadProgressBar.setVisibility(View.VISIBLE);
            uploadProgressBar.setIndeterminate(true);

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot> () {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgressBar.setVisibility(View.VISIBLE);
                                    uploadProgressBar.setIndeterminate(false);
                                    uploadProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(UploadActivity.this, "Upload Lowongan Berhasil!", Toast.LENGTH_LONG).show();
                            Lowongan upload = new Lowongan(instansiEditText.getText().toString(),
                                    namaEditText.getText().toString(),
                                    posisiEditText.getText().toString(),
                                    gajiEditText.getText().toString(),
                                    jenisEditText.getText().toString(),
                                    bukaEditText.getText().toString(),
                                    tutupEditText.getText().toString(),
                                    ketEditText.getText().toString(),
                                    taskSnapshot.getUploadSessionUri().toString());

                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);

                            uploadProgressBar.setVisibility(View.INVISIBLE);
                            openImagesActivity ();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener () {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            uploadProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot> () {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            uploadProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "You haven't Selected Any file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void openImagesActivity(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
