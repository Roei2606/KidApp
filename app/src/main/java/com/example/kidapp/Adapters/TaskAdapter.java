package com.example.kidapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidapp.Interfaces.UpdateTaskCallBack;
import com.example.kidapp.Models.KidEvent;
import com.example.kidapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<KidEvent> taskList;
    private UpdateTaskCallBack updateTaskCallBack;

    public TaskAdapter(List<KidEvent> taskList) {
        this.taskList = taskList;
    }

    public void setUpdateTaskCallBack(UpdateTaskCallBack updateTaskCallBack) {
        this.updateTaskCallBack = updateTaskCallBack;
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
        if((task.getApproved() == null)&&task.getEventTitle().contains("Babysitter Event")) {
            holder.approveButton.setVisibility(View.GONE);
            holder.denyButton.setVisibility(View.GONE);
            holder.approvalStatus.setVisibility(View.VISIBLE);
            holder.approvalStatus.setText("Task is pending");
            holder.approvalStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.black));  // Set text color to orange
            holder.task_description.setText(task.getEventTitle());
            holder.task_date.setText(task.getEDate().toString());
        }else{
            holder.task_description.setText(task.getEventTitle());
            holder.task_date.setText(task.getEDate().toString());
            holder.taskIcon.setImageResource(R.drawable.ic_task);

            // Set initial visibility and color based on task state
            if (task.getApproved() != null) {
                holder.approveButton.setVisibility(View.GONE);
                holder.denyButton.setVisibility(View.GONE);
                holder.approvalStatus.setVisibility(View.VISIBLE);
                if (task.getApproved()) {
                    holder.approvalStatus.setText("Task is approved");
                    holder.approvalStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green));  // Set text color to green
                } else {
                    holder.approvalStatus.setText("Task is denied");
                    holder.approvalStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red));  // Set text color to red
                }
            } else {
                holder.approveButton.setVisibility(View.VISIBLE);
                holder.denyButton.setVisibility(View.VISIBLE);
                holder.approvalStatus.setVisibility(View.GONE);
            }

            holder.approveButton.setOnClickListener(v -> {
                task.setApproved(true);
                holder.approveButton.setVisibility(View.GONE);
                holder.denyButton.setVisibility(View.GONE);
                holder.approvalStatus.setVisibility(View.VISIBLE);
                holder.approvalStatus.setText("Task is approved");
                holder.approvalStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green));  // Set text color to green
                if (updateTaskCallBack != null) {
                    updateTaskCallBack.updateClicked(task, position);
                }
                notifyItemChanged(position);  // Ensure the item is updated
            });

            holder.denyButton.setOnClickListener(v -> {
                task.setApproved(false);
                holder.approveButton.setVisibility(View.GONE);
                holder.denyButton.setVisibility(View.GONE);
                holder.approvalStatus.setVisibility(View.VISIBLE);
                holder.approvalStatus.setText("Task is denied");
                holder.approvalStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red));  // Set text color to red
                if (updateTaskCallBack != null) {
                    updateTaskCallBack.updateClicked(task, position);
                }
                notifyItemChanged(position);  // Ensure the item is updated
            });
        }

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView task_description;
        MaterialTextView task_date;
        ShapeableImageView approveButton;
        ShapeableImageView denyButton;
        ImageView taskIcon;
        MaterialTextView approvalStatus;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            task_description = itemView.findViewById(R.id.task_description);
            task_date = itemView.findViewById(R.id.task_date);
            approveButton = itemView.findViewById(R.id.approve_button);
            denyButton = itemView.findViewById(R.id.deny_button);
            taskIcon = itemView.findViewById(R.id.task_icon);
            approvalStatus = itemView.findViewById(R.id.approval_status);
        }
    }
}
