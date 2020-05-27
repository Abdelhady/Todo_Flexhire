package com.example.todo_flexhire.services

import com.example.todo_flexhire.backend.WebServiceBuilder
import com.example.todo_flexhire.backend.model.*
import com.example.todo_flexhire.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


object AuthService {

    private val todoService = WebServiceBuilder.getTodoService()

    fun signup(user: User, successCallback: () -> Unit, failureCallback: (error: String?) -> Unit) {
        Timber.d("result: before request, local authToken is: %s", prefs.authToken)
        val authCallback = getAuthCallback(failureCallback, successCallback)
        todoService.createUser(user).enqueue(authCallback)
    }

    fun signin(
        loginData: LoginData,
        successCallback: () -> Unit,
        failureCallback: (error: String?) -> Unit
    ) {
        val authCallback = getAuthCallback(failureCallback, successCallback)
        todoService.loginUser(loginData).enqueue(authCallback)
    }

    private fun getAuthCallback(
        failureCallback: (error: String?) -> Unit,
        successCallback: () -> Unit
    ): Callback<TokenResult> {
        return object : Callback<TokenResult> {
            override fun onFailure(call: Call<TokenResult>, t: Throwable) {
                val apiError = t.getApiError()
                Timber.d(
                    "result: response error, code: %s, message: %s",
                    apiError?.code,
                    apiError?.message
                )
                failureCallback(apiError?.message)
            }

            override fun onResponse(call: Call<TokenResult>, response: Response<TokenResult>) {
                if (response.isSuccessful) {
                    val signupResult = response.body()!!
                    Timber.d("result: message: %s", signupResult.message)
                    Timber.d("result: Token: %s", signupResult.auth_token)
                    prefs.authToken = signupResult.auth_token
                    successCallback()
                } else {
                    var apiError = response.getApiError()
                    Timber.d(
                        "result: not successful, with code: %s, message: %s",
                        apiError?.code,
                        apiError?.message
                    )
                    failureCallback(apiError?.message)
                }
            }

        }
    }

    fun ifTokenExpired(callback: () -> Unit) {
        todoService.getTodos().enqueue(object : Callback<List<TodoModel>> {
            override fun onResponse(
                call: Call<List<TodoModel>>,
                response: Response<List<TodoModel>>
            ) {
                if (response.isSuccessful) {
                    return
                }
                val apiError = response.getApiError()
                Timber.d("result: result is not successful with error code: ${apiError?.code} & message: ${apiError?.message}")
                if (apiError?.code == 422 && apiError?.message == "Signature has expired") {
                    callback()
                }
            }

            override fun onFailure(call: Call<List<TodoModel>>, t: Throwable) {
                val apiError = t.getApiError()
                Timber.d(
                    "result: response error, code: %s, message: %s",
                    apiError?.code,
                    apiError?.message
                )
            }

        })
    }

}