package com.example.kidapp.Repositories;

import android.util.Log;
import androidx.annotation.NonNull;

import com.example.kidapp.ExternalModels.boundaries.ObjectBoundary;
import com.example.kidapp.ExternalModels.boundaries.UserBoundary;
import com.example.kidapp.Models.Kid;
import com.example.kidapp.ExternalModels.boundaries.MiniAppCommandBoundary;
import com.example.kidapp.Models.Task;
import com.example.kidapp.Services.KidService;
import com.example.kidapp.Services.RetrofitClient;
import com.example.kidapp.Services.TaskService;
import com.example.kidapp.Services.UserService;
import com.google.gson.Gson;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataManager {
    private RetrofitClient database;
    private KidService kidService;
    private TaskService taskService;
    private UserService userService;
    private final String superapp = "2024b.yarden.cherry";
    private static DataManager instance;
    private Kid  kid;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private DataManager() {
        this.database = RetrofitClient.getInstance();
        this.kidService = database.getClient().create(KidService.class);
        this.taskService = database.getClient().create(TaskService.class);
        this.userService = database.getClient().create(UserService.class);
        this.kid = new Kid();
    }

    public void loginUser(String email, OnLoginListener listener) {
        userService.getUserById(superapp, email).enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(Call<UserBoundary> call, Response<UserBoundary> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserBoundary user = response.body();
                    fetchUserPhone(user, listener);
                } else {
                    listener.onFailure(new Exception("User not found"));
                }
            }

            @Override
            public void onFailure(Call<UserBoundary> call, Throwable t) {
                listener.onFailure(new Exception("Network error during user fetch: " + t.getMessage()));
            }
        });
    }

    private void fetchUserPhone(UserBoundary user, OnLoginListener listener) {
        kidService.getObjectById(user.getUsername(), superapp, user.getUserId().getSuperapp(), user.getUserId().getEmail()).enqueue(new Callback<ObjectBoundary>() {
            @Override
            public void onResponse(Call<ObjectBoundary> call, Response<ObjectBoundary> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ObjectBoundary object = response.body();
                    Log.d("object", new Gson().toJson(object));
                    if (object.getCreatedBy().getUserId().getEmail().equals(user.getUserId().getEmail())) {
                        if (object.getType().equals(Kid.class.getSimpleName())) {
                            kid = new Gson().fromJson(new Gson().toJson(object.getObjectDetails()), Kid.class);
                            Log.d("kid", new Gson().toJson(kid));
                            listener.onSuccess(kid);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ObjectBoundary> call, Throwable t) {
                listener.onFailure(new Exception("Network error during password fetch: " + t.getMessage()));
            }
        });
    }

    public void fetchTasks(String phoneNumber, OnTasksFetchedListener listener) {
        taskService.getTasks(phoneNumber).enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(@NonNull Call<List<Task>> call, @NonNull Response<List<Task>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onTasksFetched(response.body());
                } else {
                    listener.onFailure(new Exception("Failed to fetch tasks: " + getErrorMessage(response)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Task>> call, @NonNull Throwable t) {
                listener.onFailure(new Exception("Network error: " + t.getMessage()));
                Log.e("DataManager", "Error in fetchTasks: " + t.getMessage());
            }
        });
    }

//    public void fetchChildProfile(String phoneNumber, OnChildProfileFetchedListener listener) {
//        kidService.getChildProfile(phoneNumber).enqueue(new Callback<Kid>() {
//            @Override
//            public void onResponse(@NonNull Call<Kid> call, @NonNull Response<Kid> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    listener.onChildProfileFetched(response.body());
//                } else {
//                    listener.onFailure(new Exception("Failed to fetch profile: " + getErrorMessage(response)));
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Kid> call, @NonNull Throwable t) {
//                listener.onFailure(new Exception("Network error: " + t.getMessage()));
//                Log.e("DataManager", "Error in fetchChildProfile: " + t.getMessage());
//            }
//        });
//    }

    public Kid getKid() {
        return kid;
    }

    public DataManager setKid(Kid kid) {
        this.kid = kid;
        return this;
    }

    private String getErrorMessage(Response<?> response) {
        try {
            return response.errorBody() != null ? response.errorBody().string() : "Unknown error";
        } catch (Exception e) {
            return "Could not read error body";
        }
    }

    public interface OnUserLoginListener {
        void onUserLoginSuccess();
        void onFailure(Exception exception);
    }

    public interface OnTasksFetchedListener {
        void onTasksFetched(List<Task> tasks);
        void onFailure(Exception exception);
    }

    public interface OnChildProfileFetchedListener {
        void onChildProfileFetched(Kid kid);
        void onFailure(Exception exception);
    }

    public interface OnLoginListener {
        void onSuccess(Kid kid);

        void onFailure(Exception exception);
    }
}
