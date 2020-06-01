package com.example.todo_flexhire.di

import com.example.todo_flexhire.screens.todosList.di.LoginComponent
import dagger.Module

@Module(
    subcomponents = [
        LoginComponent::class
    ]
)
class SubcomponentsModule {
}