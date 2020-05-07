package com.example.todo_flexhire.viewmodels

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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

}