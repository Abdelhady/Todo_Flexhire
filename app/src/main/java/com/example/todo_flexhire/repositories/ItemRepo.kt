package com.example.todo_flexhire.repositories

import com.example.todo_flexhire.backend.FlexhireTodoService
import com.example.todo_flexhire.backend.model.TodoItemModel
import com.example.todo_flexhire.backend.model.TodoItemModelForPost
import com.example.todo_flexhire.backend.model.TodoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class ItemRepo @Inject constructor(
    private val todoService: FlexhireTodoService
) {

    fun updateItem(itemModel: TodoItemModel) {
        val newItem = TodoItemModelForPost(itemModel.name, itemModel.done)
        todoService.updateTodoItem(itemModel.todoId, itemModel.id, newItem)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (!response.isSuccessful) {
                        Timber.d("Item update failed")
                        return
                    }
                    Timber.d("Item updated successfully on the backend")
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Timber.e(t)
                }

            })
    }

    fun deleteItem(todoId: Int, itemId: Int) {
        todoService.deleteItem(todoId, itemId).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                // TODO handle this
                Timber.e(t)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                // TODO handle this and return a callback or something
            }

        })
    }

    fun createItem(todoId: Int, model: TodoItemModelForPost, successCallback: (TodoModel) -> Unit) {
        todoService.createTodoItem(todoId, model).enqueue(object : Callback<TodoModel> {
            override fun onFailure(call: Call<TodoModel>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<TodoModel>, response: Response<TodoModel>) {
                if (!response.isSuccessful) {
                    // TODO handle failure
                    return
                }
                response.body()?.let { successCallback(it) }
            }

        })
    }

}