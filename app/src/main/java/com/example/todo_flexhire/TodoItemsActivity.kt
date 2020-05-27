package com.example.todo_flexhire

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_flexhire.databinding.ActivityTodoItemsBinding
import com.example.todo_flexhire.ui.adapters.ItemsAdapter
import com.example.todo_flexhire.viewmodels.TodoItemsViewModel
import kotlinx.android.synthetic.main.activity_todo_items.*

class TodoItemsActivity : AppCompatActivity() {

    val viewModel by viewModels<TodoItemsViewModel>()
    private val adapter = ItemsAdapter()
    var parentRefreshNeeded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTodoItemsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_todo_items)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val id: Int = intent.extras?.get("id") as Int
        viewModel.fetchTodo(id)
        initItemsList()
        initFab()
        initObservers()
    }

    private fun initObservers() {
        viewModel.deletionDone.observe(this, Observer {
            if (it) {
                parentRefreshNeeded = true
                onBackPressed()
            }
        })
        viewModel.editingDone.observe(this, Observer {
            if (it) {
                parentRefreshNeeded = true
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (parentRefreshNeeded) {
            NavUtils.navigateUpFromSameTask(this)// To restart parent activity to refresh its list
        }
    }

    private fun initFab() {
        fab.setOnClickListener {
            fab.isExpanded = true
            // TODO focus on the textfield, with opening keyboard
        }
        cancelButton.setOnClickListener { fab.isExpanded = false }
    }

    private fun initItemsList() {
        itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        itemsRecyclerView.adapter = adapter
        viewModel.todoModel.observe(this, Observer {
            adapter.items = it?.items?.toMutableList()!!
            fab.isExpanded = false
            // TODO hide keyboard here
        })
    }

}
