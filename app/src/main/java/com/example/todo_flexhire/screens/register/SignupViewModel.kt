package com.example.todo_flexhire.screens.register

import android.util.Patterns
import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_flexhire.backend.model.User
import com.example.todo_flexhire.services.AuthService
import javax.inject.Inject

class SignupViewModel : ViewModel() {

    @Inject
    lateinit var authService: AuthService

    val name = MutableLiveData("")
    val nameError = MediatorLiveData<String>().apply {
        addSource(name) {
            value = if (it.isEmpty()) "Can't be empty" else null
        }
    }
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
    val passwordConfirm = MutableLiveData("")
    val passwordConfirmError = MediatorLiveData<String>().apply {
        addSource(password) {
            value = if (!isMatchingPasswords()) "Passwords must be identical"
            else null
        }
        addSource(passwordConfirm) {
            value = if (it.isEmpty()) "Can't be empty"
            else if (!isMatchingPasswords()) "Passwords must be identical"
            else null
        }
    }

    val loading = MutableLiveData(false)
    val isRegistered = MutableLiveData(false)

    val canRegister = MediatorLiveData<Boolean>().apply {
        addSource(nameError) {
            value = isFormValid()
        }
        addSource(emailError) {
            value = isFormValid()
        }
        addSource(passwordError) {
            value = isFormValid()
        }
        addSource(passwordConfirmError) {
            value = isFormValid()
        }
    }

    val authError = MediatorLiveData<String>().apply {
        addSource(name) {
            value = ""
        }
        addSource(email) {
            value = ""
        }
        addSource(password) {
            value = ""
        }
        addSource(passwordConfirm) {
            value = ""
        }
    }

    private fun isFormValid(): Boolean {
        return nameError.value == null &&
                emailError.value == null &&
                passwordError.value == null &&
                passwordConfirmError.value == null
    }

    private fun isMatchingPasswords(): Boolean {
        return password.value == passwordConfirm.value
    }

    fun registerUser(view: View) {
        loading.value = true
        val user = User(name.value!!, email.value!!, password.value!!, passwordConfirm.value!!)
        authService.signup(user, {
            isRegistered.value = true
        }, { message ->
            loading.value = false
            authError.value = message
        })
    }

}