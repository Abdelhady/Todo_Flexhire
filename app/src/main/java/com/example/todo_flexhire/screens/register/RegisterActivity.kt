package com.example.todo_flexhire.screens.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.todo_flexhire.R
import com.example.todo_flexhire.databinding.ActivityRegisterBinding
import com.example.todo_flexhire.prefs
import com.example.todo_flexhire.screens.login.LoginActivity
import com.example.todo_flexhire.screens.todosList.TodosListActivity
import kotlinx.android.synthetic.main.activity_register.*
import timber.log.Timber


class RegisterActivity : AppCompatActivity() {

    val viewModel by viewModels<SignupViewModel>() // https://developer.android.com/kotlin/ktx#fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!prefs.authToken.isNullOrBlank()) {
            openTodosActivity()
            return
        }
        val binding: ActivityRegisterBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_register
            )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.isRegistered.observe(this, Observer {
            if (it) {
                openTodosActivity()
            }
        })
        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun openTodosActivity() {
        Timber.d("Auth Token: %s", prefs.authToken)
        val intent = Intent(this, TodosListActivity::class.java)
        startActivity(intent)
        finish()
    }

}
