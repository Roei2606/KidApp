# KidApp

KidApp is a mobile application designed for kids, connected to the KinderKid application. It allows children to view their personal information, receive notifications from their parents, and manage tasks assigned to them. Both applications run on the same server and share the same database, ensuring seamless integration and data consistency.

## Table of Contents
1. [Introduction](#Introduction)
2. [Features](#features)
3. [Installation](#Installation)
4. [Configuration](#Configuration)
6. [Usage](#usage)
7. [API Integration](#API-Integration)
8. [Permissions](#Permissions)
9. [Screenshots](#Screenshots)
10. [Video Demonstration](#Video-Demonstration)

## Introduction

KidApp is developed to provide children with an engaging and secure platform to manage their daily tasks and view their personal information. The application ensures that children can stay connected with their parents through notifications and tasks assigned by their parents. It is designed with a user-friendly interface that is easy for kids to navigate. Parents manage the initial setup and input of all necessary information, ensuring that the child’s profile is accurate and up-to-date. KidApp helps in fostering a sense of responsibility in children by allowing them to manage their tasks efficiently.

## Features

- **Kid Profile**: Children can view their profile information, including their profile picture, birth date, and phone number. All information about the child is entered by the parent.
- **Notifications**: A bell icon in the KidProfileActivity shows new notifications sent by parents. It displays a badge count of unmarked tasks.
- **Task Management**: Children can view and manage tasks assigned by their parents in the TaskListActivity.

## Installation

1. Clone the repository:
```bash
git clone https://github.com/Roei2606/KidApp.git
```
2. Open the project in Android Studio.
3. Ensure you have the necessary SDKs and dependencies installed.

## Configuration

1. Update the `network_security_config.xml` file in the `res/xml` directory for your network security configurations.

## Usage

1. A parent adds a child to their repository in the KinderKid App, optionally entering a valid phone number.
2. The child can then log in to KidApp using their phone number in `LoginActivity`.
3. The child can view their profile information in `KidProfileActivity`.
4. Notifications from parents are shown via the bell icon in `KidProfileActivity`.
5. The child can manage tasks in `TaskListActivity`, approving or denying tasks as needed.

## API Integration

KidApp connects to the server using Retrofit for API calls. The shared server hosts both the KidApp and Kinderkit apps, ensuring data consistency.

### RetrofitClient.java

```java
public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
```

### KidService.java

```java
public interface KidService {
    @GET("/superapp/objects/{superapp}/{id}")
    Call<ObjectBoundary> getObjectById(@Path("id") String id,
                                       @Path("superapp") String superapp,
                                       @Query("userSuperapp") String userSuperapp,
                                       @Query("userEmail") String userEmail);
}
```

### UserService.java
```java
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
```

## Permissions

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## Screenshots

Here are some screenshots of the application:

<img width="297" alt="Screenshot 2024-07-14 at 22 51 30" src="https://github.com/user-attachments/assets/3113c30e-fb1c-4668-9756-1b91bd7bb732"> <img width="297" alt="Screenshot 2024-07-14 at 22 51 42" src="https://github.com/user-attachments/assets/7b31ad2c-621e-42c5-ad3f-88c2cc4501c1">
<img width="297" alt="Screenshot 2024-07-14 at 22 50 19" src="https://github.com/user-attachments/assets/96675857-a1d3-429e-9d1d-d8668c8e9da5"> <img width="297" alt="Screenshot 2024-07-14 at 22 50 12" src="https://github.com/user-attachments/assets/f94b1c10-f645-4f7f-b73f-de897a9f06bb">
<img width="297" alt="Screenshot 2024-07-14 at 22 50 07" src="https://github.com/user-attachments/assets/8751356b-19c0-4137-a59d-2a161019d8b5"> <img width="297" alt="Screenshot 2024-07-14 at 22 49 59" src="https://github.com/user-attachments/assets/9076b595-fdb9-41b1-8eed-8fa50532b5e4">

## Video Demonstration

https://github.com/user-attachments/assets/9362f708-fca6-4964-af83-b2941211471e










