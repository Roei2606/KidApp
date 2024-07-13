package com.example.kidapp.Services;


import com.example.kidapp.ExternalModels.boundaries.MiniAppCommandBoundary;
import com.example.kidapp.ExternalModels.boundaries.UserBoundary;
import com.example.kidapp.ExternalModels.boundaries.ObjectBoundary;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @POST("/superapp/miniapp/{miniapp}")
    Call<Void> loginUser(@Path("miniapp") String miniapp, @Body MiniAppCommandBoundary boundaryCommand);

    @GET("superapp/users/login/{superapp}/{email}")
    Call<UserBoundary> getUserById(@Path("superapp") String superapp, @Path("email") String email);

    @PUT("/superapp/objects/{superapp}/{id}")
    Call<Void> updateObject(@Path("id") String id,
                            @Path("superapp") String superapp,
                            @Query("userSuperapp") String userSuperapp,
                            @Query("userEmail") String userEmail,
                            @Body ObjectBoundary objectBoundary);

    @PUT("/superapp/users/{superapp}/{email}")
    Call<Void> updateUser(@Path("superapp") String superapp, @Path("email") String email, @Body UserBoundary update);
}

