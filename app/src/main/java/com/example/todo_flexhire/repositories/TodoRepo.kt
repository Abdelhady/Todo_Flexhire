package com.example.todo_flexhire.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo_flexhire.backend.WebServiceBuilder
import com.example.todo_flexhire.backend.model.TodoModel
import com.example.todo_flexhire.backend.model.TodoModelForPost
import com.example.todo_flexhire.backend.model.getApiError
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

}