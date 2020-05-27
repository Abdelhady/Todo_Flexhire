package com.example.todo_flexhire.ui.viewmodels

import android.util.Patterns
import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_flexhire.backend.model.LoginData
import com.example.todo_flexhire.services.AuthService
import timber.log.Timber

class LoginViewModel : ViewModel() {

    val isLoggedin = MutableLiveData(false)
    val loading = MutableLiveData(false)

    val email = MutableLiveData("")
    val emailError = MediatorLiveData<String>().apply {
        addSource(email) {
            value = if (it.isEmpty()) {
                "Can't be empty"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                "Invalid Email Address"
            } else {
                null
            }
        }
    }
    val password = MutableLiveData("")
    val passwordError = MediatorLiveData<String>().apply {
        addSource(password) {
            value = if (it.isEmpty()) "Can't be empty" else null
        }
    }

    val canLogin = MediatorLiveData<Boolean>().apply {
        addSource(emailError) {
            value = isFormValid()
        }
        addSource(passwordError) {
            value = isFormValid()
        }
    }

    private fun isFormValid(): Boolean {
        return emailError.value == null &&
                passwordError.value == null
    }

    fun loginUser(view: View) {
        loading.value = true
        val loginData = LoginData(email.value!!, password.value!!)
        AuthService.signin(loginData, {
            isLoggedin.value = true
        }, {
            loading.value = false
            // TODO show the returned error message
            Timber.d("Login failed with message $it")
        })
    }

}