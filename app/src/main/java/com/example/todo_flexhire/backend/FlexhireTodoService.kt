package com.example.todo_flexhire.backend

import com.example.todo_flexhire.backend.model.SignupResult
import com.example.todo_flexhire.backend.model.TodoModel
import com.example.todo_flexhire.backend.model.TodoModelForPost
import com.example.todo_flexhire.backend.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

public interface FlexhireTodoService {

    @POST("signup")
    fun createUser(@Body user: User): Call<SignupResult>

    @GET("todos")
    fun getTodos(): Call<List<TodoModel>>

    @POST("todos")
    fun createTodo(@Body model: TodoModelForPost): Call<TodoModel>

}