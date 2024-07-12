package com.example.kidapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidapp.Models.KidEvent;
import com.example.kidapp.Models.Task;
import com.example.kidapp.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<KidEvent> taskList;

    public TaskAdapter(List<KidEvent> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        KidEvent task = taskList.get(position);
        holder.task_description.setText(task.getEventTitle());
        holder.task_date.setText(task.getEDate().toString());
        holder.task_checkbox.setOnClickListener(v->{
            if(holder.task_checkbox.isChecked()){
                task.setApproved(true);
            }else{
                task.setApproved(false);
            }
        });
        holder.taskIcon.setImageResource(R.drawable.ic_task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView task_description;
        MaterialTextView task_date;
        CheckBox task_checkbox;
        ImageView taskIcon;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            task_description = itemView.findViewById(R.id.task_description);
            task_date = itemView.findViewById(R.id.task_date);
            task_checkbox= itemView.findViewById(R.id.task_checkbox);
            taskIcon = itemView.findViewById(R.id.task_icon);
        }
    }
}


