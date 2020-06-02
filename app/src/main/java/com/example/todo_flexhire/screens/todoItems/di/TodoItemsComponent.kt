package com.example.todo_flexhire.screens.todoItems.di

import com.example.todo_flexhire.screens.todoItems.SingleItemViewModel
import com.example.todo_flexhire.screens.todoItems.TodoItemsViewModel
import dagger.Subcomponent

@Subcomponent
interface TodoItemsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TodoItemsComponent
    }

    fun inject(viewModel: TodoItemsViewModel)

    fun inject(viewModel: SingleItemViewModel)

}