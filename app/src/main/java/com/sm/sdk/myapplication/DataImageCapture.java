package com.sm.sdk.myapplication;

import android.graphics.Bitmap;

public class DataImageCapture {
    private Bitmap image;

    public DataImageCapture(Bitmap image) {
        this.image = image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }
}
