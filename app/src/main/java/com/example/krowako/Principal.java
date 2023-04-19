package com.example.krowako;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;

public class Principal extends AppCompatActivity {

    DrawerLayout drawerLayout;

    ImageView menu;

    LinearLayout home, api, map, logout;


    Button fab;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);




        DatabaseHelper databaseHelper;


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult
                (new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    }
                });






        drawerLayout = findViewById(R.id.drawerLayout);

        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        api = findViewById(R.id.api);
        map = findViewById(R.id.map);
        logout = findViewById(R.id.logout);
        fab = findViewById(R.id.fab);
        databaseHelper = new DatabaseHelper(this);

        //Under onCreate


        ImageView navImage = (ImageView) findViewById(R.id.navImage);
        TextView navName = (TextView) findViewById(R.id.navName);
        TextView navEmail = (TextView) findViewById(R.id.navEmail);

        ImageView pImage = (ImageView) findViewById(R.id.pImage);
        TextView pName = (TextView) findViewById(R.id.pName);
        TextView pEmail = (TextView) findViewById(R.id.pEmail);

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getUser();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Sin detalles de perfil", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                navName.setText(""+cursor.getString(0));
                navEmail.setText(""+cursor.getString(1));

                pName.setText(""+cursor.getString(0));
                pEmail.setText(""+cursor.getString(1));

                byte[] imageByte = cursor.getBlob(2);

                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
                navImage.setImageBitmap(bitmap);
                pImage.setImageBitmap(bitmap);
            }

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this,UploadActivity.class);
                startActivity(intent);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Principal.this, apiTab.class);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Principal.this, mapTab.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Principal.this, Start.class);
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);

    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}