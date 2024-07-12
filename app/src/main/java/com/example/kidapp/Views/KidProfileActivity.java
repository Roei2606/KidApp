package com.example.kidapp.Views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import androidx.appcompat.app.AppCompatActivity;

import com.example.kidapp.Models.Kid;
import com.example.kidapp.R;
import com.example.kidapp.Repositories.DataManager;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.imageview.ShapeableImageView;

public class KidProfileActivity extends AppCompatActivity {
    private ShapeableImageView profileImageView;
    private MaterialTextView nameTextView;
    private MaterialTextView dobTextView;
    private MaterialTextView phoneNumberTextView;
    private MaterialTextView buttonLogout;
    private View bellIcon;
    private TextView badgeCount;
    private DataManager dataManager= DataManager.getInstance();
    private String phoneNumber;
    private Kid kid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_profile);
        findViewsById();
        kid = dataManager.getKid();
        initViews();

        badgeCount.setVisibility(View.GONE);



        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        bellIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewTasksDialog();
            }
        });

        updateBadgeCount(2); // Example: Update badge count to 2 for demonstration
    }


    private void initViews() {
        Intent intent = new Intent();
        phoneNumberTextView.setText(kid.getPhone());
        nameTextView.setText(kid.getfName() + " " + kid.getlName());
        dobTextView.setText(kid.getBirthDate().toString());

        Glide.with(this).load(kid.getProfilePhoto()).placeholder(R.drawable.ic_profile_placeholder).into(profileImageView);
        //Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/kinderkit-68d4c.appspot.com/o/Ariel.jpg?alt=media&token=3a1bc07a-b643-4a44-9769-b62b2eb7001b").into(profileImageView);

    }

    private void findViewsById() {
        profileImageView = findViewById(R.id.profile_image);
        nameTextView = findViewById(R.id.name_text);
        dobTextView = findViewById(R.id.dob_text);
        phoneNumberTextView = findViewById(R.id.phone_number_text);
        buttonLogout = findViewById(R.id.buttonLogout);
        bellIcon = findViewById(R.id.notificationBell);
        badgeCount = findViewById(R.id.badgeCount);
    }

    private void updateBadgeCount(int count) {
        if (count > 0) {
            badgeCount.setText(String.valueOf(count));
            badgeCount.setVisibility(View.VISIBLE);
        } else {
            badgeCount.setVisibility(View.GONE);
        }
    }

    private void showNewTasksDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_new_tasks);
        dialog.setTitle("New Tasks");
        TextView textViewNoNewTasks = dialog.findViewById(R.id.textViewNoNewTasks);
        textViewNoNewTasks.setText("No new tasks");

        TextView buttonViewTasks = dialog.findViewById(R.id.buttonViewTasks);
        buttonViewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KidProfileActivity.this, TaskListActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

//    private void fetchChildProfile() {
//        dataManager.fetchChildProfile(phoneNumber, new DataManager.OnChildProfileFetchedListener() {
//            @Override
//            public void onChildProfileFetched(Kid kid) {
//                nameTextView.setText(kid.getfName() + " " + kid.getlName());
//                dobTextView.setText(kid.getBirthDate().toString());
//                phoneNumberTextView.setText(kid.getPhoneNumber());
//                Glide.with(KidProfileActivity.this)
//                        .load(kid.getProfilePhotoUri().toString())
//                        .placeholder(R.drawable.ic_profile_placeholder) // Add a placeholder if needed
//                        .circleCrop() // To make the image circular
//                        .into(profileImageView);
//            }
//
//            @Override
//            public void onFailure(Exception exception) {
//                Toast.makeText(KidProfileActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void logout() {
        // Handle logout logic here (e.g., clearing shared preferences or any stored user data)
        Intent intent = new Intent(KidProfileActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
