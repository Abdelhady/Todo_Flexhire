package com.example.todo_flexhire.screens.todosList.di

import com.example.todo_flexhire.screens.todosList.TodosViewModel
import dagger.Subcomponent

@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(viewModel: TodosViewModel)
}