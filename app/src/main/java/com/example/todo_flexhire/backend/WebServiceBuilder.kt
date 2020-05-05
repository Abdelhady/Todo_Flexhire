package com.example.todo_flexhire.backend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * https://dev.to/paulodhiambo/kotlin-and-retrofit-network-calls-2353
 */
object WebServiceBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://todos.flexhire.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> build(service: Class<T>): T {
        return retrofit.create(service)
    }

    fun getTodoService(): FlexhireTodoService {
        return build(FlexhireTodoService::class.java)
    }
}