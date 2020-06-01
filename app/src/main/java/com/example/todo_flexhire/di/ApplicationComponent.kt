package com.example.todo_flexhire.di

import com.example.todo_flexhire.screens.todosList.di.LoginComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SubcomponentsModule::class
    ]
)
interface ApplicationComponent {

    fun loginComponent(): LoginComponent.Factory

}