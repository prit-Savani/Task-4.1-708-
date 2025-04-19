package com.example.taskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.models.Task;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<com.example.taskmanager.models.Task> taskList;
    private OnTaskClickListener listener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public interface OnTaskClickListener {
        void onTaskClick(com.example.taskmanager.models.Task task);
        void onTaskDelete(com.example.taskmanager.models.Task task);

        void onTaskEdit(Task task);
    }

    public TaskAdapter(List<com.example.taskmanager.models.Task> taskList, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        com.example.taskmanager.models.Task task = taskList.get(position);
        holder.textTitle.setText(task.getTitle());
        holder.textDueDate.setText(dateFormat.format(task.getDueDate()));

        holder.itemView.setOnClickListener(v -> listener.onTaskClick(task));
        holder.buttonDelete.setOnClickListener(v -> listener.onTaskDelete(task));
        holder.buttonEdit.setOnClickListener(v -> listener.onTaskEdit(task));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDueDate;
        ImageButton buttonDelete , buttonEdit;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_task_title);
            textDueDate = itemView.findViewById(R.id.text_task_due_date);
            buttonDelete = itemView.findViewById(R.id.button_delete_task);
            buttonEdit = itemView.findViewById(R.id.button_edit_task);
        }
    }
}
