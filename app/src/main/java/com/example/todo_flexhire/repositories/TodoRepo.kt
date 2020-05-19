package com.example.todo_flexhire.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo_flexhire.backend.WebServiceBuilder
import com.example.todo_flexhire.backend.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class TodoRepo {

    private val todoService = WebServiceBuilder.getTodoService()

    fun getTodos(): LiveData<List<TodoModel>> {
        val data = MutableLiveData<List<TodoModel>>()
        todoService.getTodos().enqueue(object : Callback<List<TodoModel>> {
            override fun onResponse(
                call: Call<List<TodoModel>>,
                response: Response<List<TodoModel>>
            ) {
                if (!response.isSuccessful) {
                    Timber.d("result: result is not successful with error code ${response.getApiError()?.code}")
                    return
                }
                data.value = response.body()
                Timber.d("result: result title is: %s", data.value?.get(0)?.title)
            }

            override fun onFailure(call: Call<List<TodoModel>>, t: Throwable) {
                val apiError = t.getApiError()
                Timber.d(
                    "result: response error, code: %s, message: %s",
                    apiError?.code,
                    apiError?.message
                )
            }

        })
        return data
    }

    fun createTodo(
        model: TodoModelForPost,
        successCallback: (TodoModel) -> Unit
    ): MutableLiveData<TodoModel> {
        val data = MutableLiveData<TodoModel>()
        todoService.createTodo(model).enqueue(object : Callback<TodoModel> {
            override fun onResponse(call: Call<TodoModel>, response: Response<TodoModel>) {
                // TODO should be saved in DB here when Room DB is implemented
                data.value = response.body()
                successCallback(data.value!!)
                Timber.d("result: value returned inside the repo for posting new todo")
            }

            override fun onFailure(call: Call<TodoModel>, t: Throwable) {
                val apiError = t.getApiError()
                Timber.d(
                    "result: response error, code: %s, message: %s",
                    apiError?.code,
                    apiError?.message
                )
            }

        })
        return data
    }

    fun getTodo(id: Int): MutableLiveData<TodoModel>{
        val data = MutableLiveData<TodoModel>()
        todoService.getTodo(id).enqueue(object : Callback<TodoModel>{
            override fun onFailure(call: Call<TodoModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<TodoModel>, response: Response<TodoModel>) {
                if (!response.isSuccessful){
                    // TODO handle this
                    return
                }
                data.value = response.body()
            }

        })
        return data
    }

    fun updateItem(itemModel: TodoItemModel){
        val newItem = TodoItemModelForPost(itemModel.name, itemModel.done)
        todoService.updateTodoItem(itemModel.todoId, itemModel.id, newItem).enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful){
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

}