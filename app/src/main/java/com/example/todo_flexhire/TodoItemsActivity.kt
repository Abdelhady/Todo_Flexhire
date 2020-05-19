package com.example.todo_flexhire

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_flexhire.databinding.ActivityTodoItemsBinding
import com.example.todo_flexhire.ui.adapters.ItemsAdapter
import com.example.todo_flexhire.viewmodels.TodoItemsViewModel
import kotlinx.android.synthetic.main.activity_todo_items.*

class TodoItemsActivity : AppCompatActivity() {

    val viewModel by viewModels<TodoItemsViewModel>()
    val adapter = ItemsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTodoItemsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_todo_items)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val id: Int = intent.extras?.get("id") as Int
        viewModel.fetchTodo(id)
        initItemsList()
    }

    private fun initItemsList() {
        itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        itemsRecyclerView.adapter = adapter
        viewModel.todoModel.observe(this, Observer {
            adapter.items = it?.items?.toMutableList()!!
        })
    }

}