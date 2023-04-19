package com.example.krowako;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DBHelper extends SQLiteOpenHelper{

    Context context;
    private static String DB_Name = "Profile.db";
    private static int DB_VERSION = 1;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte [] byteImage;

    private static String createTableQuery = "Create table ProfileUser(email Text" +
            ",name TEXT" +
            ",image BLOB)";


    public DBHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void storeData(ModelClass modelClass){
       SQLiteDatabase database = this.getWritableDatabase();
       Bitmap bitmapImage = modelClass.getImage();

       byteArrayOutputStream = new ByteArrayOutputStream();
       bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
       byteImage = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("email", modelClass.getEmail());
        contentValues.put("name", modelClass.getName());
        contentValues.put("image", byteImage);

        long checkQuery = database.insert("ProfileUser",null, contentValues);
        if(checkQuery != -1){
            Toast.makeText(context, "Guardado", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Error ", Toast.LENGTH_SHORT).show();
        }

    }
    public Cursor getUser(){
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery("Select * from ProfileUser",null);
        return cursor;
    }
}
