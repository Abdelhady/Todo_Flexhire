package com.example.todo_flexhire.di

import com.example.todo_flexhire.screens.todoItems.di.TodoItemsComponent
import com.example.todo_flexhire.screens.todosList.di.TodosListComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SubcomponentsModule::class
    ]
)
interface ApplicationComponent {

    fun todosListComponent(): TodosListComponent.Factory

    fun todoItemsComponent(): TodoItemsComponent.Factory

}