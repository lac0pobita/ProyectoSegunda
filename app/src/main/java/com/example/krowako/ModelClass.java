package com.example.krowako;

import android.graphics.Bitmap;

public class ModelClass {
    private String email, name;
    private Bitmap image;

    public ModelClass(String email, String name, Bitmap image) {
        this.email = email;
        this.name = name;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {

        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
