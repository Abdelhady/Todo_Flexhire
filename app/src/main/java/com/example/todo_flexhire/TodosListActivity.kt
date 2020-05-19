package com.example.todo_flexhire

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_flexhire.databinding.ActivityTodosListBinding
import com.example.todo_flexhire.services.AuthService
import com.example.todo_flexhire.ui.adapters.TodosAdapter
import com.example.todo_flexhire.viewmodels.TodosViewModel
import kotlinx.android.synthetic.main.activity_todos_list.*
import timber.log.Timber


class TodosListActivity : AppCompatActivity() {

    private val viewModel by viewModels<TodosViewModel>()
    private val adapter = TodosAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTodosListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_todos_list)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initTodosList()
        initFab()
        AuthService.ifTokenExpired {
            Timber.d("Token has just been expired *********")
            prefs.authToken = ""
            val intent = baseContext.packageManager
                .getLaunchIntentForPackage(baseContext.packageName)
            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    private fun initFab() {
        // https://medium.com/@lcdsmao/material-design-transition-for-floating-action-button-in-android-dc8304343cfe
        fab.setOnClickListener { fab.isExpanded = true }
        cancelButton.setOnClickListener { fab.isExpanded = false }

        viewModel.newlyAddedTodo.observe(this, Observer {
            Timber.d("result: in activity, saved title is: %s", it.title)
            adapter.items.add(it)
            adapter.notifyItemInserted(adapter.items.indexOf(it))
        })
    }

    private fun initTodosList() {
        todosRecyclerView.layoutManager = LinearLayoutManager(this)
        todosRecyclerView.setHasFixedSize(true)
        todosRecyclerView.adapter = adapter
        // TODO items should be sorted (recent first or something)
        viewModel.getTodosList().observe(this, Observer {
            adapter.items = it.toMutableList()
        })
    }
}
