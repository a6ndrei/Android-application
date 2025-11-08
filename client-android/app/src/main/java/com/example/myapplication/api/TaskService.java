package com.example.myapplication.api;

import com.example.myapplication.models.Task;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaskService {


    @GET("api/tasks")
    Call<List<Task>> getTasks();


    @POST("api/tasks")
    Call<Task> createTask(@Body Task task);


    @PUT("api/tasks/{id}/resolve")
    Call<Task> resolveTask(@Path("id") int taskId);


    @PUT("api/tasks/{id}")
    Call<Task> updateTask(@Path("id") int taskId, @Body Task task);
}