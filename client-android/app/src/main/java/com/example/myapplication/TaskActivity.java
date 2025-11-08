package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.api.TaskService;
import com.example.myapplication.models.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private TaskService taskService;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        FloatingActionButton fabAddTask = findViewById(R.id.fabAddTask);


        taskAdapter = new TaskAdapter(new ArrayList<>());
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);


        taskService = RetrofitClient.createService(TaskService.class);


        loadTasks();

        fabAddTask.setOnClickListener(v -> {

            Toast.makeText(this, "Deschide formularul de creare Task", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadTasks() {

        taskService.getTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Task> tasks = response.body();


                    taskAdapter.setTasks(tasks);

                    Toast.makeText(TaskActivity.this, "S-au incarcat " + tasks.size() + " task-uri.", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 401) {

                    Toast.makeText(TaskActivity.this, "Sesiune expirata. Relogati-va.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(TaskActivity.this, "Eroare la incarcarea task-urilor.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Toast.makeText(TaskActivity.this, "Eroare de retea: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}