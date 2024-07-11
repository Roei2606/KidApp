package com.example.kidapp.Models;


import com.example.kidapp.ExternalModels.boundaries.ObjectBoundary;

public abstract class BasicUser {
    String mail;
    String uid;
    public abstract ObjectBoundary toBoundary();
    public abstract String getUid();

    public abstract BasicUser setUid(String uid);

    public abstract String getMail();
    public abstract String getPassword();

}
