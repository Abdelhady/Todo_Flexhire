package com.example.todo_flexhire.screens.todosList

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_flexhire.backend.model.TodoModel
import com.example.todo_flexhire.backend.model.TodoModelForPost
import com.example.todo_flexhire.repositories.TodoRepo
import javax.inject.Inject

class TodosViewModel : ViewModel() {

    // Using field injection here instead of constructor injection, for easier viewmodel creations
    // as per the 3rd option in this article: https://proandroiddev.com/injecting-viewmodel-from-hard-to-easy-c06c0fe1c8e9
    // or we would have to use a more sophisticated approach like this official sample: https://github.com/android/architecture-samples/tree/dev-dagger
    @Inject
    lateinit var todoRepo: TodoRepo

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
        newTitle.value = ""
        todoRepo.createTodo(model) {
            newlyAddedTodo.value = it
        }

    }

    fun getTodosList(): LiveData<List<TodoModel>> {
        return todoRepo.getTodos()
    }

}