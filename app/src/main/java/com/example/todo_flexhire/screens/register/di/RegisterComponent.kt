package com.example.todo_flexhire.screens.register.di

import com.example.todo_flexhire.screens.register.SignupViewModel
import dagger.Subcomponent

@Subcomponent
interface RegisterComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RegisterComponent
    }

    fun inject(viewModel: SignupViewModel)

}