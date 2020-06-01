package com.example.todo_flexhire.di

import com.example.todo_flexhire.screens.register.di.RegisterComponent
import com.example.todo_flexhire.screens.todoItems.di.TodoItemsComponent
import com.example.todo_flexhire.screens.todosList.di.TodosListComponent
import dagger.Module

@Module(
    subcomponents = [
        TodosListComponent::class,
        TodoItemsComponent::class,
        RegisterComponent::class
    ]
)
class SubcomponentsModule {
}