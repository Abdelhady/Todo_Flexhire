package com.example.todo_flexhire.screens.todosList.di

import com.example.todo_flexhire.screens.todosList.TodosListActivity
import com.example.todo_flexhire.screens.todosList.TodosViewModel
import dagger.Subcomponent

@Subcomponent
interface TodosListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TodosListComponent
    }

    fun inject(viewModel: TodosViewModel)
    fun inject(activity: TodosListActivity)
}