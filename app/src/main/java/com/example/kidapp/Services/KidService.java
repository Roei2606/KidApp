package com.example.kidapp.Services;

import com.example.kidapp.ExternalModels.boundaries.ObjectBoundary;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KidService {
    @GET("/superapp/objects/{superapp}/{id}")
    Call<ObjectBoundary> getObjectById(@Path("id") String id,
                                       @Path("superapp") String superapp,
                                       @Query("userSuperapp") String userSuperapp,
                                       @Query("userEmail") String userEmail);
}
