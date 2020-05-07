package com.example.todo_flexhire.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo_flexhire.backend.WebServiceBuilder
import com.example.todo_flexhire.backend.model.Todos
import com.example.todo_flexhire.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class TodoRepo {

    fun getTodos(): LiveData<List<Todos>> {
        val data = MutableLiveData<List<Todos>>()
        val todoService = WebServiceBuilder.getTodoService()

        todoService.getTodos().enqueue(object : Callback<List<Todos>> {
            override fun onResponse(call: Call<List<Todos>>, response: Response<List<Todos>>) {
                data.value = response.body()
                Timber.d("result: result title is: %s", data.value?.get(0)?.title)
            }

            override fun onFailure(call: Call<List<Todos>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return data
    }

}