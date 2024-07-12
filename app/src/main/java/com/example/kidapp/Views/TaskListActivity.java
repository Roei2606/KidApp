package com.example.kidapp.Views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidapp.Adapters.TaskAdapter;
import com.example.kidapp.Models.Kid;
import com.example.kidapp.R;
import com.example.kidapp.Repositories.DataManager;

public class TaskListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private TextView badgeCount;
    private ImageView bellIcon;
    private DataManager dataManager= DataManager.getInstance();
    private Kid kid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        kid = dataManager.getKid();
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        TaskAdapter taskAdapter = new TaskAdapter(kid.getEvents());
        badgeCount = findViewById(R.id.badgeCount);
        recyclerViewTasks.setAdapter(taskAdapter);
        bellIcon = findViewById(R.id.bellIcon);
        updateBadgeCount(2);
        bellIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewTasksDialog();
            }
        });
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

        dialog.show();
    }
}