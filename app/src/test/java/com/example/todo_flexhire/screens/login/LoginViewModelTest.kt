package com.example.todo_flexhire.screens.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todo_flexhire.R
import getOrAwaitValue
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var appContext: Context

    @Before
    fun setup(){
        appContext = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun emailError_empty_returnsEmptyMessage() {
        // Given
        val loginViewModel = LoginViewModel()
        // When
        loginViewModel.email.value = ""
        val message = loginViewModel.emailError.getOrAwaitValue()
        // Then
        assertThat(message.format(appContext), `is`(appContext.getString(R.string.cant_be_empty)))
    }

}