package com.example.todo_flexhire.screens.login.di

import com.example.todo_flexhire.screens.login.LoginViewModel
import dagger.Subcomponent

@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(viewModel: LoginViewModel)

}