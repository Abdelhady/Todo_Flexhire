package com.example.todo_flexhire.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_flexhire.backend.model.TodoModel
import com.example.todo_flexhire.repositories.TodoRepo

class TodoItemsViewModel : ViewModel() {

    val todoRepo = TodoRepo()
    var todoModel = MutableLiveData<TodoModel>()

    fun fetchTodo(id: Int){
        todoModel = todoRepo.getTodo(id)
    }

}