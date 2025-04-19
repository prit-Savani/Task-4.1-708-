package com.example.taskmanager;


import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanager.models.Task;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TaskDetailActivity extends AppCompatActivity {


    private TextView textTitle, textDescription, textDueDate;
    private TaskDatabase taskDatabase;
    private int taskId;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_detail);


        textTitle = findViewById(R.id.text_detail_title);
        textDescription = findViewById(R.id.text_detail_description);
        textDueDate = findViewById(R.id.text_detail_due_date);

        taskDatabase = TaskDatabase.getInstance(this);

        taskId = getIntent().getIntExtra("task_id", -1);
        if (taskId != -1) {
            Task task = taskDatabase.taskDao().getTaskById(taskId);
            if (task != null) {
                textTitle.setText(task.getTitle());
                textDescription.setText(task.getDescription());
                textDueDate.setText(dateFormat.format(task.getDueDate()));
            }
        }
    }
}