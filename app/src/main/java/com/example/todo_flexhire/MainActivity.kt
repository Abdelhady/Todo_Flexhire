package com.example.todo_flexhire

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.todo_flexhire.databinding.ActivityMainBinding
import com.example.todo_flexhire.viewmodels.SignupViewModel
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<SignupViewModel>() // https://developer.android.com/kotlin/ktx#fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        Timber.d("validation: MainActivity")
    }

}
