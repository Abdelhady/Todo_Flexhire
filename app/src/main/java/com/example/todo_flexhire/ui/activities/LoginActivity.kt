package com.example.todo_flexhire.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.todo_flexhire.R
import com.example.todo_flexhire.databinding.ActivityLoginBinding
import com.example.todo_flexhire.prefs
import com.example.todo_flexhire.ui.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    private fun openTodosActivity() {
        Timber.d("Auth Token: %s", prefs.authToken)
        val intent = Intent(this, TodosListActivity::class.java)
        startActivity(intent)
        finish()
    }

}
