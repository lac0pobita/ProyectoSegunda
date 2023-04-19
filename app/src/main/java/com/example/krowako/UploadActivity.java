package com.example.krowako;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    EditText uploadEmail, uploadName;
    ImageView uploadImage;
    Button saveButton;
    private Uri uri;
    private Bitmap bitmapImage;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadEmail=findViewById(R.id.uploadEmail);
        uploadImage=findViewById(R.id.uploadImage);
        uploadName=findViewById(R.id.uploadName);
        saveButton=findViewById(R.id.saveButton);
        dbHelper = new DBHelper(this);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult
                (new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode()== Activity.RESULT_OK){
                            Intent data = result.getData();
                            assert data!=null;
                            uri =data.getData();
                            try {
                                bitmapImage= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            }catch (IOException e){
                                Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            uploadImage.setImageBitmap(bitmapImage);
                        }else{
                            Toast.makeText(UploadActivity.this, "No Seleccionaste Imagen", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent objectIntent = new Intent();
                    objectIntent.setType("image/*");
                    objectIntent.setAction(Intent.ACTION_GET_CONTENT);
                    activityResultLauncher.launch(objectIntent);
                }catch (Exception e){
                    Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeImage();
            }
        });
    }

    private void storeImage(){
        if (!uploadEmail.getText().toString().isEmpty() &&
                !uploadName.getText().toString().isEmpty() &&
        uploadImage.getDrawable() != null && bitmapImage !=null){

    dbHelper.storeData(new ModelClass(uploadEmail.getText().toString(),
            uploadName.getText().toString(),bitmapImage));
    Intent intent = new Intent(UploadActivity.this,Principal.class);
    startActivity(intent);
        }else{
            Toast.makeText(this, "Campos obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
}