package com.example.kidapp.Models;

import android.net.Uri;

import java.util.UUID;

public class MyPhoto {

    String pId;
    Uri photoUri;

    public MyPhoto() {
        pId = UUID.randomUUID().toString().toLowerCase();
    }


    public String getpId() {
        return pId;
    }

    public MyPhoto setpId(String pId) {
        this.pId = pId;
        return this;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public MyPhoto setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
        return this;
    }
}
