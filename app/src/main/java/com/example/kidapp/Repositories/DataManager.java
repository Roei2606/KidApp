package com.example.kidapp.Repositories;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.kidapp.ExternalModels.boundaries.ObjectBoundary;
import com.example.kidapp.ExternalModels.boundaries.UserBoundary;
import com.example.kidapp.ExternalModels.utils.Role;
import com.example.kidapp.Models.BasicUser;
import com.example.kidapp.Models.Kid;
import com.example.kidapp.ExternalModels.boundaries.MiniAppCommandBoundary;
import com.example.kidapp.Models.Task;
import com.example.kidapp.Services.KidService;
import com.example.kidapp.Services.RetrofitClient;
import com.example.kidapp.Services.TaskService;
import com.example.kidapp.Services.UserService;
import com.example.kidapp.Views.KidProfileActivity;
import com.example.kidapp.Views.LoginActivity;
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
    //    public void updateObject(ObjectBoundary objectBoundary, DataManager.OnUserUpdateListener listenerUpdate, BasicUser user) {
//        if (user.getClass().getSimpleName().equals("Parent"))
//            Log.d("Da", "Updating object with id: " + objectBoundary.toString());
//        userService.updateObject(objectBoundary.getObjectId().getId(), objectBoundary.getObjectId().getSuperapp(), objectBoundary.getObjectId().getSuperapp(), objectBoundary.getCreatedBy().getUserId().getEmail(), objectBoundary)
//                .enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
//                        if (response.isSuccessful()) {
//                            listenerUpdate.onSuccess();
//                        } else {
//                            logError(response, "updateUser");
//                            listenerUpdate.onFailure(new Exception("Failed to update user: " + getErrorMessage(response)));
//                            Log.d("DataManager", "Error in updateObject: " + getErrorMessage(response));
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
//                        listenerUpdate.onFailure(new Exception("Failed to update user: " + t.getMessage()));
//                        Log.d("12w2", "Error in updateObject: " + t.getMessage());
//                    }
//                });
//    }
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

    public void updateKidObject(Kid kid, OnUserUpdateListener listenerUpdate) {
        ObjectBoundary objectBoundary = kid.toBoundary();
        objectBoundary.getObjectId().setSuperapp(superapp);
        objectBoundary.getObjectId().setId(kid.getUid());
        objectBoundary.getCreatedBy().getUserId().setEmail(kid.getMail());
        Log.d("DataManager1111", "Updating kid object with id: " + objectBoundary);
        // Step 1: Update the user role to SUPERAPP_USER
        updateUserRole(kid.getMail(), Role.SUPERAPP_USER, new OnUserUpdateListener() {
            @Override
            public void onSuccess() {
                // Step 2: Update the kid object
                updateObject(objectBoundary, new OnUserUpdateListener() {
                    @Override
                    public void onSuccess() {
                        // Step 3: Update the user role back to MINIAPP_USER
                        updateUserRole(kid.getMail(), Role.MINIAPP_USER, new OnUserUpdateListener() {
                            @Override
                            public void onSuccess() {
                                // Notify the original listener that the whole operation was successful
                                Log.d("DataManager", "Kid object updated successfully");
                                listenerUpdate.onSuccess();
                            }
                            @Override
                            public void onFailure(Exception exception) {
                                // Notify the original listener about the failure in the final step
                                Log.d("DataManager", "Error in updateKidObject: " + exception.getMessage());
                                listenerUpdate.onFailure(exception);
                            }
                        });
                    }
                    @Override
                    public void onFailure(Exception exception) {
                        // Notify the original listener about the failure in the event update step
                        Log.d("DataManager", "Error in updateKidObject: " + exception.getMessage());
                        listenerUpdate.onFailure(exception);
                    }
                });
            }
            @Override
            public void onFailure(Exception exception) {
                // Notify the original listener about the failure in the initial user role update step
                Log.d("DataManager", "Error in updateKidObject: " + exception.getMessage());
                listenerUpdate.onFailure(exception);
            }
        });
    }

    private void updateObject(ObjectBoundary objectBoundary, OnUserUpdateListener listenerUpdate) {
        userService.updateObject(objectBoundary.getObjectId().getId(), objectBoundary.getObjectId().getSuperapp(), objectBoundary.getObjectId().getSuperapp(), objectBoundary.getCreatedBy().getUserId().getEmail(), objectBoundary)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("DataManager", "Kid object updated successfully");
                            listenerUpdate.onSuccess();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.d("DataManager", "Error in updateObject: " + t.getMessage());
                        listenerUpdate.onFailure(new Exception("Failed to update user: " + t.getMessage()));
                    }
                });
    }

    private void updateUserRole(String email, Role role, OnUserUpdateListener listener) {
        userService.getUserById(superapp, email).enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(@NonNull Call<UserBoundary> call, @NonNull Response<UserBoundary> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserBoundary userBoundary = response.body();
                    userBoundary.setRole(role);
                    userService.updateUser(userBoundary.getUserId().getSuperapp(), email, userBoundary).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                            if (response.isSuccessful()) {
                                listener.onSuccess();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            Log.d("DataManager", "Error in updateUserRole: " + t.getMessage());
                            listener.onFailure(new Exception("Failed to update user role: " + t.getMessage()));
                        }
                    });
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserBoundary> call, @NonNull Throwable t) {
                Log.d("DataManager", "Error in updateUserRole: " + t.getMessage());
                listener.onFailure(new Exception("Network error during role update: " + t.getMessage()));
            }
        });
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
    public interface OnUserUpdateListener {
        void onSuccess();

        void onFailure(Exception exception);
    }
}
