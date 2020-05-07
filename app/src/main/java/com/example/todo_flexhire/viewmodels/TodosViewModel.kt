package com.example.todo_flexhire.viewmodels

import android.view.View
import androidx.lifecycle.*
import com.example.todo_flexhire.backend.model.TodoModel
import com.example.todo_flexhire.backend.model.TodoModelForPost
import com.example.todo_flexhire.repositories.TodoRepo

class TodosViewModel : ViewModel() {

    val todoRepo = TodoRepo() // TODO should be injected when DI is implemented

    val newTitle = MutableLiveData("")
    val canCreate = MediatorLiveData<Boolean>().apply {
        addSource(newTitle) {
            value = it.isNotEmpty()
        }
    }

    var newlyAddedTodo = MutableLiveData<TodoModel>()

    fun createTodo(view: View) {
        val model = TodoModelForPost(newTitle.value!!)
//        todoRepo.createTodo(model).observeForever(Observer {
//            newlyAddedTodo.value = it
//        })
        todoRepo.createTodo(model) {
            newlyAddedTodo.value = it
        }

    }

    fun getTodosList(): LiveData<List<TodoModel>> {
        return todoRepo.getTodos()
    }

}