package com.example.krowako;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class mapTab extends AppCompatActivity {

    Button showMap;

    DrawerLayout drawerLayout;

    ImageView menu;

    LinearLayout home, api, map, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tab);

        showMap = findViewById(R.id.showMap);

        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mapTab.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        api = findViewById(R.id.api);
        map = findViewById(R.id.map);
        logout = findViewById(R.id.logout);

        ImageView navImage = (ImageView) findViewById(R.id.navImage);
        TextView navName = (TextView) findViewById(R.id.navName);
        TextView navEmail = (TextView) findViewById(R.id.navEmail);

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getUser();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Sin detalles de perfil", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                navName.setText(""+cursor.getString(0));
                navEmail.setText(""+cursor.getString(1));
                byte[] imageByte = cursor.getBlob(2);

                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
                navImage.setImageBitmap(bitmap);
            }

        }


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(mapTab.this, Principal.class);

            }
        });
        api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(mapTab.this, apiTab.class);

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(mapTab.this, Start.class);
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