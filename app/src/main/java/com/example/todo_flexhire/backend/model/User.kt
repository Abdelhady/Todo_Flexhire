package com.example.todo_flexhire.backend.model

data class User(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)