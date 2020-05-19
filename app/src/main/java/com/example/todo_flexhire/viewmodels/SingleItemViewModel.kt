package com.example.todo_flexhire.viewmodels

import android.widget.CompoundButton
import androidx.lifecycle.ViewModel
import com.example.todo_flexhire.backend.model.TodoItemModel
import com.example.todo_flexhire.repositories.TodoRepo
import timber.log.Timber

class SingleItemViewModel(val model: TodoItemModel) : ViewModel() {

    val todoRepo = TodoRepo()

    fun onDoneChanged(buttonView: CompoundButton, isChecked: Boolean) {
        Timber.d("Check box for ${model.name} has just been clicked, newValue: $isChecked")
        if (model.done == isChecked){
            return
        }
        model.done = isChecked
        todoRepo.updateItem(model)
    }

}