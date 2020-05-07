package com.example.todo_flexhire.backend.model

data class Todos(
    val id: Int,
    val title: String,
    val createdBy: String,
    val createdAt: String,
    val updatedAt: String
)