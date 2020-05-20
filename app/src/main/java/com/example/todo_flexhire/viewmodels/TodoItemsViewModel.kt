package com.example.todo_flexhire.viewmodels

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_flexhire.backend.model.TodoItemModelForPost
import com.example.todo_flexhire.backend.model.TodoModel
import com.example.todo_flexhire.repositories.TodoRepo
import timber.log.Timber

class TodoItemsViewModel : ViewModel() {

    val todoRepo = TodoRepo()
    var todoModel = MutableLiveData<TodoModel>()

    // Create form
    val newName = MutableLiveData("")
    val doneChecked = MutableLiveData(false)
    val canCreate = MediatorLiveData<Boolean>().apply {
        addSource(newName) {
            value = it.isNotEmpty()
        }
    }

    fun fetchTodo(id: Int) {
        todoModel = todoRepo.getTodo(id)
    }

    fun createItem(view: View) {
        Timber.d("Will create new item with name: ${newName.value}, and done: ${doneChecked.value}")
        val model = TodoItemModelForPost(newName.value!!, doneChecked.value!!)
        todoModel.value?.id?.let { todoId ->
            todoRepo.createItem(todoId, model) {
                todoModel.value = it
                newName.value = ""
                doneChecked.value = false
            }
        }
    }

}