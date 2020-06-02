package com.example.todo_flexhire.screens.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.todo_flexhire.R
import com.example.todo_flexhire.TodoApplication
import com.example.todo_flexhire.databinding.ActivityLoginBinding
import com.example.todo_flexhire.prefs
import com.example.todo_flexhire.screens.register.RegisterActivity
import com.example.todo_flexhire.screens.todosList.TodosListActivity
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as TodoApplication).appComponent
            .loginComponent()
            .create()
            .inject(viewModel)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.isLoggedin.observe(this, Observer {
            if (it) {
                openTodosActivity()
            }
        })
        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        initToastListener()
    }

    private fun initToastListener() {
        viewModel.toastMessage.observe(this, Observer { res ->
            if (res != null) {
                val message = res.format(this)
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun openTodosActivity() {
        Timber.d("Auth Token: %s", prefs.authToken)
        val intent = Intent(this, TodosListActivity::class.java)
        startActivity(intent)
        finish()
    }

}
