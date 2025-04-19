package com.example.taskmanager;

import android.os.Bundle;
import com.example.taskmanager.models.Task;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TaskListFragment extends Fragment implements TaskAdapter.OnTaskClickListener {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private FloatingActionButton fabAddTask;
    private TaskDatabase taskDatabase;

        public TaskListFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_task_list_fragment, container, false);

            recyclerView = view.findViewById(R.id.recycler_view_tasks);
            fabAddTask = view.findViewById(R.id.fab_add_task);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            taskDatabase = TaskDatabase.getInstance(getContext());
            loadTasks();

            fabAddTask.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AddEditTaskActivity.class);
                startActivity(intent);
            });

            return view;
        }

        private void loadTasks() {
            List<Task> tasks = taskDatabase.taskDao().getAllTasksSortedByDueDate();
            taskAdapter = new TaskAdapter(tasks, this);
            recyclerView.setAdapter(taskAdapter);
        }

        @Override
        public void onTaskClick(com.example.taskmanager.models.Task task) {
            Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
            intent.putExtra("task_id", task.getId());
            startActivity(intent);
        }

        @Override
        public void onTaskDelete(com.example.taskmanager.models.Task task) {
            taskDatabase.taskDao().delete(task);
            Toast.makeText(getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
            loadTasks();


        }

        @Override
        public void onTaskEdit(Task task) {
                Intent intent = new Intent(getActivity(), AddEditTaskActivity.class);
                intent.putExtra("task_id", task.getId());
                startActivity(intent);
                }

}