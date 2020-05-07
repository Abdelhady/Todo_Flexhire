package com.example.todo_flexhire.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_flexhire.backend.model.Todos
import com.example.todo_flexhire.repositories.TodoRepo

class TodosViewModel: ViewModel() {

    val newTitle = MutableLiveData("")
    val canCreate = MediatorLiveData<Boolean>().apply {
        addSource(newTitle){
            value = it.isNotEmpty()
        }
    }

    fun createTodo(view: View){
        // TODO call todo repo here to create on the backend (and local)
    }

    fun getTodosList(): LiveData<List<Todos>> {
        val todoRepo = TodoRepo() // TODO should be injected when DI is implemented
        return todoRepo.getTodos()
    }

}