package com.example.todo_flexhire.di

import com.example.todo_flexhire.screens.login.di.LoginComponent
import com.example.todo_flexhire.screens.register.di.RegisterComponent
import com.example.todo_flexhire.screens.todoItems.di.TodoItemsComponent
import com.example.todo_flexhire.screens.todosList.di.TodosListComponent
import dagger.Component
import javax.inject.Singleton

// https://developer.android.com/training/dependency-injection/dagger-android
@Singleton
@Component(
    modules = [
        SubcomponentsModule::class
    ]
)
interface ApplicationComponent {

    fun todosListComponent(): TodosListComponent.Factory

    fun todoItemsComponent(): TodoItemsComponent.Factory

    fun registerComponent(): RegisterComponent.Factory

    fun loginComponent(): LoginComponent.Factory

}