package com.example.kidapp.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
            KidEvent updatedEvent = kid.getEvents().get(position);
            Log.d("TaskListActivity", "Updated Event: " + updatedEvent.getEventTitle() + " isApproved: " + updatedEvent.getApproved());
            dataManager.updateKidObject(kid, new DataManager.OnUserUpdateListener() {
                @Override
                public void onSuccess() {
                    Log.d("TaskListActivity", "Kid object updated successfully");
                    Toast.makeText(TaskListActivity.this, "Kid object updated successfully", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Exception exception) {
                    Log.d("TaskListActivity", "Error in updateKidObject: " + exception.getMessage());
                    Toast.makeText(TaskListActivity.this, "Error in updateKidObject: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
