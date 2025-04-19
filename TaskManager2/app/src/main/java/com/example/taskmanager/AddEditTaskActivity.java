package com.example.taskmanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanager.models.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditTaskActivity extends AppCompatActivity {



    private EditText editTitle, editDescription, editDueDate;
    private Button buttonSave;
    private TaskDatabase taskDatabase;
    private com.example.taskmanager.models.Task task;
    private int taskId = -1;
    private Calendar calendar;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_task);

        editTitle = findViewById(R.id.edit_text_title);
        editDescription = findViewById(R.id.edit_text_description);
        editDueDate = findViewById(R.id.edit_text_due_date);
        buttonSave = findViewById(R.id.button_save);

        taskDatabase = TaskDatabase.getInstance(this);
        calendar = Calendar.getInstance();

        taskId = getIntent().getIntExtra("task_id", -1);
        if (taskId != -1) {
            task = taskDatabase.taskDao().getTaskById(taskId);
            if (task != null) {
                editTitle.setText(task.getTitle());
                editDescription.setText(task.getDescription());
                editDueDate.setText(dateFormat.format(task.getDueDate()));
            }
        }

        editDueDate.setOnClickListener(v -> showDatePickerDialog());

        buttonSave.setOnClickListener(v -> saveTask());
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            editDueDate.setText(dateFormat.format(calendar.getTime()));
        };

        new DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveTask() {
        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String dueDateStr = editDueDate.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            editTitle.setError("Title is required");
            return;
        }
        if (TextUtils.isEmpty(dueDateStr)) {
            editDueDate.setError("Due date is required");
            return;
        }

        Date dueDate;
        try {
            dueDate = dateFormat.parse(dueDateStr);
        } catch (Exception e) {
            editDueDate.setError("Invalid date format");
            return;
        }

        if (task == null) {
            task = new Task();
        }
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);

        if (taskId == -1) {
            taskDatabase.taskDao().insert(task);
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        } else {
            task.setId(taskId);
            taskDatabase.taskDao().update(task);
            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
        }
        finish();


    }
}