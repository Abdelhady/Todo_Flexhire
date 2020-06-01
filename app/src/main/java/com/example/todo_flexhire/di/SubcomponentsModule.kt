package com.example.todo_flexhire.di

import com.example.todo_flexhire.screens.todosList.di.TodosListComponent
import dagger.Module

@Module(
    subcomponents = [
        TodosListComponent::class
    ]
)
class SubcomponentsModule {
}