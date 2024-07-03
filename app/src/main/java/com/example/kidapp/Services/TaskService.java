package com.example.kidapp.Services;

import com.example.kidapp.Models.Task;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import java.util.List;

public interface TaskService {
    @GET("/child/{phoneNumber}/tasks")
    Call<List<Task>> getTasks(@Path("phoneNumber") String phoneNumber);
}
