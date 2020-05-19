package com.example.todo_flexhire.backend.model

data class TodoItemModel(
    val id: Int,
    val name: String,
    var done: Boolean,
    val todoId: Int,
    val createdAt: String,
    val updatedAt: String
)