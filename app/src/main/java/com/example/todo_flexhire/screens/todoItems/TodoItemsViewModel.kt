package com.example.todo_flexhire.screens.todoItems

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_flexhire.backend.model.TodoItemModelForPost
import com.example.todo_flexhire.backend.model.TodoModel
import com.example.todo_flexhire.backend.model.TodoModelForPost
import com.example.todo_flexhire.repositories.TodoRepo
import timber.log.Timber
import javax.inject.Inject

class TodoItemsViewModel : ViewModel() {

    @Inject
    lateinit var todoRepo: TodoRepo

    var todoModel = MutableLiveData<TodoModel>()
    val deletionDone = MutableLiveData(false)
    val editingDone = MutableLiveData(false)

    // Edit form
    val editMode = MutableLiveData(false)
    val newTitle = MutableLiveData("")
    val canSaveEdit = MediatorLiveData<Boolean>().apply {
        addSource(newTitle) {
            value = it.isNotEmpty()
        }
    }

    // Create form
    val newName = MutableLiveData("")
    val doneChecked = MutableLiveData(false)
    val canCreate = MediatorLiveData<Boolean>().apply {
        addSource(newName) {
            value = it.isNotEmpty()
        }
    }

    fun startEditMode(view: View) {
        editMode.value = true
        newTitle.value = todoModel.value!!.title
    }

    fun cancelEditMode(view: View) {
        editMode.value = false
        // Because when starting edit mode again, setting the value will move the placeholder with a nice animation
        newTitle.value = ""
    }

    fun deleteTodo(view: View) {
        todoModel.value?.id?.let {
            todoRepo.deleteTodo(it) {
                deletionDone.value = true
            }
        }
    }

    fun fetchTodo(id: Int) {
        todoModel = todoRepo.getTodo(id)
    }

    /**
     * TODO this function won't be needed after fetching from Room
     */
    fun refreshTodo(id: Int) {
        todoRepo.getTodo(id) {
            todoModel.value = it
        }
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

    fun saveTitle(view: View) {
        Timber.d("Will update the title to: ${newTitle.value}")
        val model = TodoModelForPost(newTitle.value!!)
        todoRepo.updateTitle(todoModel.value!!.id, model) {
            editMode.value = false
            newTitle.value = ""
            refreshTodo(todoModel.value!!.id)// getting a fresh copy
            editingDone.value = true
        }
    }

}