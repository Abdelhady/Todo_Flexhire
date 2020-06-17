package com.example.todo_flexhire.screens.register

import android.util.Patterns
import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_flexhire.R
import com.example.todo_flexhire.backend.model.User
import com.example.todo_flexhire.services.AuthService
import com.example.todo_flexhire.utils.IdResourceString
import com.example.todo_flexhire.utils.ResourceString
import javax.inject.Inject

class SignupViewModel : ViewModel() {

    @Inject
    lateinit var authService: AuthService

    val name = MutableLiveData("")
    val nameError = MediatorLiveData<ResourceString>().apply {
        addSource(name) {
            value = if (it.isEmpty()) IdResourceString(R.string.cant_be_empty) else null
        }
    }
    val email = MutableLiveData("")
    val emailError = MediatorLiveData<ResourceString>().apply {
        addSource(email) {
            value = if (it.isEmpty()) {
                IdResourceString(R.string.cant_be_empty)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                IdResourceString(R.string.invalid_email_address)
            } else {
                null
            }
        }
    }
    val password = MutableLiveData("")
    val passwordError = MediatorLiveData<ResourceString>().apply {
        addSource(password) {
            value = if (it.isEmpty()) IdResourceString(R.string.invalid_email_address) else null
        }
    }
    val passwordConfirm = MutableLiveData("")
    val passwordConfirmError = MediatorLiveData<ResourceString>().apply {
        addSource(password) {
            value =
                if (!isMatchingPasswords()) IdResourceString(R.string.passwords_must_be_identical)
                else null
        }
        addSource(passwordConfirm) {
            value = if (it.isEmpty()) IdResourceString(R.string.invalid_email_address)
            else if (!isMatchingPasswords()) IdResourceString(R.string.passwords_must_be_identical)
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
        // Just emptying this error when any field changes
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