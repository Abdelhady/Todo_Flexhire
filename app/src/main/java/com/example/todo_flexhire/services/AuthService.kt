package com.example.todo_flexhire.services

import com.example.todo_flexhire.backend.WebServiceBuilder
import com.example.todo_flexhire.backend.model.SignupResult
import com.example.todo_flexhire.backend.model.User
import com.example.todo_flexhire.backend.model.getApiError
import com.example.todo_flexhire.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


object AuthService {

    fun signup(user: User) {
        Timber.d("result: before request, local authToken is: %s", prefs.authToken)

        val todoService = WebServiceBuilder.getTodoService()

        val call = todoService.createUser(user)
        call.enqueue(object : Callback<SignupResult> {
            override fun onResponse(
                call: Call<SignupResult>,
                response: Response<SignupResult>
            ) {
                if (response.isSuccessful) {
                    val signupResult = response.body()!!
                    Timber.d("result: message: %s", signupResult.message)
                    Timber.d("result: Token: %s", signupResult.auth_token)
                    prefs.authToken = signupResult.auth_token
                } else {
                    Timber.d(
                        "result: not successful, with code: %s, message: %s",
                        response.getApiError()?.code,
                        response.getApiError()?.message
                    )
                }
            }

            override fun onFailure(call: Call<SignupResult>, t: Throwable) {
                Timber.d(
                    "result: response error, code: %s, message: %s",
                    t.getApiError()?.code,
                    t.getApiError()?.message
                )
            }
        })
    }

    fun signin() {

    }

}