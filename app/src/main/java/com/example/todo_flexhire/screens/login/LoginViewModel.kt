package com.example.todo_flexhire.screens.login

import android.util.Patterns
import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_flexhire.backend.model.LoginData
import com.example.todo_flexhire.services.AuthService
import com.example.todo_flexhire.utils.ResourceString
import com.example.todo_flexhire.utils.SingleLiveEvent
import com.example.todo_flexhire.utils.TextResourceString
import timber.log.Timber

class LoginViewModel : ViewModel() {

    var authService = AuthService()

    internal val toastMessage = SingleLiveEvent<ResourceString>()
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
    val authError = MediatorLiveData<String>().apply {
        addSource(email) {
            value = ""
        }
        addSource(password) {
            value = ""
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
        authService.signin(loginData, {
            isLoggedin.value = true
        }, { message ->
            loading.value = false
            // TODO show the returned error message
            Timber.d("Login failed with message $message")
            toastMessage.value = message?.let { it1 -> TextResourceString(it1) }
            authError.value = message
        })
    }

}