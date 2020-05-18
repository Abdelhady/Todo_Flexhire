package com.example.todo_flexhire

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.todo_flexhire.databinding.ActivityTodoItemBinding
import com.example.todo_flexhire.viewmodels.TodoItemViewModel

class TodoItemActivity : AppCompatActivity() {

    val viewModel by viewModels<TodoItemViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTodoItemBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_todo_item)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.title.value = intent.extras?.get("id").toString()
    }

}
