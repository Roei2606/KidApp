package com.example.kidapp.Views;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidapp.Adapters.TaskAdapter;
import com.example.kidapp.Models.Kid;
import com.example.kidapp.Models.KidEvent;
import com.example.kidapp.R;
import com.example.kidapp.Repositories.DataManager;

public class TaskListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private TextView textViewNoTasks;

    private DataManager dataManager = DataManager.getInstance();
    private Kid kid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        kid = dataManager.getKid();
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        textViewNoTasks = findViewById(R.id.textViewNoTasks);
        if (!kid.getEvents().isEmpty()) {
            textViewNoTasks.setVisibility(View.GONE);
        }
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        TaskAdapter taskAdapter = new TaskAdapter(kid.getEvents());
        recyclerViewTasks.setAdapter(taskAdapter);
        taskAdapter.setUpdateTaskCallBack((event, position) -> {
            kid.setCurrnetEvent(event, position);
            taskAdapter.notifyItemChanged(position);
            // Log the updated event to verify if the approval status is being updated correctly
            KidEvent updatedEvent = kid.getEvents().get(position);
            Log.d("TaskListActivity", "Updated Event: " + updatedEvent.getEventTitle() + " isApproved: " + updatedEvent.getApproved());
        });
    }
}
