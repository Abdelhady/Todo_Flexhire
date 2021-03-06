package com.example.todo_flexhire.backend

import com.example.todo_flexhire.backend.model.*
import retrofit2.Call
import retrofit2.http.*

public interface FlexhireTodoService {

    @POST("signup")
    fun createUser(@Body user: User): Call<TokenResult>

    @POST("auth/login")
    fun loginUser(@Body loginData: LoginData): Call<TokenResult>

    @GET("todos")
    fun getTodos(): Call<List<TodoModel>>

    @POST("todos")
    fun createTodo(@Body model: TodoModelForPost): Call<TodoModel>

    @GET("todos/{id}")
    fun getTodo(@Path("id") id: Int): Call<TodoModel>

    @POST("todos/{id}/items")
    fun createTodoItem(
        @Path("id") id: Int,
        @Body model: TodoItemModelForPost
    ): Call<TodoModel> // Should have returned the only created item instead

    @PUT("todos/{todoId}/items/{itemId}")
    fun updateTodoItem(
        @Path("todoId") todoId: Int,
        @Path("itemId") itemId: Int,
        @Body model: TodoItemModelForPost
    ): Call<Void>

    @PUT("todos/{todoId}")
    fun updateTodo(@Path("todoId") todoId: Int, @Body model: TodoModelForPost): Call<Void>

    @DELETE("todos/{todoId}")
    fun deleteTodo(@Path("todoId") todoId: Int): Call<Void>

    @DELETE("todos/{todoId}/items/{itemId}")
    fun deleteItem(@Path("todoId") todoId: Int, @Path("itemId") itemId: Int): Call<Void>

}