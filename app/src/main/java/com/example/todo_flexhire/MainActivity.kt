package com.example.todo_flexhire

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.todo_flexhire.databinding.ActivityMainBinding
import com.example.todo_flexhire.viewmodels.SignupViewModel


class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<SignupViewModel>() // https://developer.android.com/kotlin/ktx#fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!prefs.authToken.isNullOrBlank()) {
            openTodosActivity()
            return
        }
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.isRegistered.observe(this, Observer {
            if (it) {
                openTodosActivity()
            }
        })
    }

    private fun openTodosActivity() {
        val intent = Intent(this, TodosListActivity::class.java)
        startActivity(intent)
        finish()
    }

}
