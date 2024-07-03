package com.example.kidapp.Models;

import java.util.HashMap;
import java.util.Map;

import com.example.kidapp.ExternalModels.boundaries.ObjectBoundary;
import com.example.kidapp.ExternalModels.utils.CreatedBy;
import com.example.kidapp.ExternalModels.utils.UserId;
import com.google.gson.Gson;

public class Kid {
    private String phone;

    public Kid() {
    }

    public String getPhone() {
        return phone;
    }
    public ObjectBoundary toBoundary() {
        ObjectBoundary objectBoundary = new ObjectBoundary();
        objectBoundary.setType(this.getClass().getSimpleName());
        objectBoundary.setActive(true);
        CreatedBy user=new CreatedBy();
        user.setUserId((new UserId()).setEmail(this.getPhone()));
        return objectBoundary;
    }

    public Kid toKid(String json){
        return new Gson().fromJson(json,Kid.class);
    }

}
