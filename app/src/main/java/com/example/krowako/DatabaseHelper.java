package com.example.krowako;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

import kotlin.UByteArray;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "Signup.db";

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] byteImage;



    public DatabaseHelper(@Nullable Context context) {
        super(context, "Signup.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table allusers(email TEXT primary key, password TEXT, fame TEXT, lame TEXT, image BLOB) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists allusers");

    }









    public Boolean insertData(String email, String password, String fame, String lame ){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("fame", fame);
        contentValues.put("lame", lame);

        long result = MyDatabase.insert("allusers", null, contentValues);

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Boolean checkEmail(String email){

        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ?", new String[]{email});

        if(cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }


    public Boolean checkEmailPassword(String email, String password){

        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ? and password = ?", new String[]{email, password});

        if (cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }

    public Boolean updateData(String email, String fame, String lame, byte image ){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put("fame", fame);
        contentValues.put("lame", lame);
        contentValues.put("image", image);

        Cursor cursor = MyDatabase.rawQuery("Select * from Userdetails where email = ?" , new String[]{email});
        if (cursor.getCount()>0) {
            long result = MyDatabase.update("allusers", contentValues , "email=?",new String[]{email});

            if(result == -1){
                return false;
            }else {
                return true;
            }
        }else{
            return false;
        }

    }



}
