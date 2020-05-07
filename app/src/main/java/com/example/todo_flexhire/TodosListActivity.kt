package com.example.todo_flexhire

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_flexhire.databinding.ActivityTodosListBinding
import com.example.todo_flexhire.ui.adapters.TodosAdapter
import com.example.todo_flexhire.viewmodels.TodosViewModel
import kotlinx.android.synthetic.main.activity_todos_list.*

class TodosListActivity : AppCompatActivity() {

    val viewModel by viewModels<TodosViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTodosListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_todos_list)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // https://medium.com/@lcdsmao/material-design-transition-for-floating-action-button-in-android-dc8304343cfe
        fab.setOnClickListener { fab.isExpanded = true }
        cancelButton.setOnClickListener { fab.isExpanded = false }

        initTodosList()
    }

    private fun initTodosList() {
        todosRecyclerView.layoutManager = LinearLayoutManager(this)
        todosRecyclerView.setHasFixedSize(true)
        val adapter = TodosAdapter()
        todosRecyclerView.adapter = adapter
        viewModel.getTodosList().observe(this, Observer {
            adapter.items = it
        })
    }
}
