package com.example.todo_flexhire.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoItemViewModel : ViewModel() {

    val title = MutableLiveData("Initial value")

}