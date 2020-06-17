package com.example.todo_flexhire.screens.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import getOrAwaitValue
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun emailError_empty_returnsEmptyMessage() {
        // Given
        val loginViewModel = LoginViewModel()
        // When
        loginViewModel.email.value = ""
        val message = loginViewModel.emailError.getOrAwaitValue()
        // Then
        assertThat(message, `is`("Can't be empty"))
    }

}