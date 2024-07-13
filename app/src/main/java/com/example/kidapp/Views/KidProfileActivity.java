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
    private View refreshButton;
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
        updateBadgeCount(countUndoneTasks());
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

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateBadgeCount(countUndoneTasks());
    }

    private void initViews() {
        Intent intent = new Intent();
        phoneNumberTextView.setText(kid.getPhone());
        nameTextView.setText(kid.getfName() + " " + kid.getlName());
        dobTextView.setText(kid.getBirthDate().toString());
        Glide.with(this).load(kid.getProfilePhoto()).placeholder(R.drawable.ic_profile_placeholder).into(profileImageView);
    }
    private int countUndoneTasks(){
        int count = 0;
        for(int i=0;i<kid.getEvents().size();i++){
            if(kid.getEvents().get(i).getApproved() == null) {
                count++;
            }
        }
        return count;
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
        if(countUndoneTasks() == 0){
            textViewNoNewTasks.setText("No new tasks");
        }else{
            textViewNoNewTasks.setText("You have " + countUndoneTasks() + " uncompleted tasks");
        }
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

    private void logout() {
        Intent intent = new Intent(KidProfileActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    private void refreshData() {
        dataManager.reloadKidData(kid.getMail(), new DataManager.OnLoginListener() {
            @Override
            public void onSuccess(Kid kid) {
                KidProfileActivity.this.kid = kid;
                initViews();
                updateBadgeCount(countUndoneTasks());
            }

            @Override
            public void onFailure(Exception exception) {
                Log.e("KidProfileActivity", "Failed to refresh data: " + exception.getMessage());
            }
        });
    }
    private void findViewsById() {
        profileImageView = findViewById(R.id.profile_image);
        nameTextView = findViewById(R.id.name_text);
        dobTextView = findViewById(R.id.dob_text);
        phoneNumberTextView = findViewById(R.id.phone_number_text);
        buttonLogout = findViewById(R.id.buttonLogout);
        bellIcon = findViewById(R.id.notificationBell);
        badgeCount = findViewById(R.id.badgeCount);
        refreshButton = findViewById(R.id.refresh_button);
    }
}
