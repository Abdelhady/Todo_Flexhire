package com.example.todo_flexhire.services

import com.example.todo_flexhire.backend.WebServiceBuilder
import com.example.todo_flexhire.backend.model.SignupResult
import com.example.todo_flexhire.backend.model.TodoModel
import com.example.todo_flexhire.backend.model.User
import com.example.todo_flexhire.backend.model.getApiError
import com.example.todo_flexhire.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


object AuthService {

    private val todoService = WebServiceBuilder.getTodoService()

    fun signup(user: User, successCallback: () -> Unit, failureCallback: (error: String?) -> Unit) {
        Timber.d("result: before request, local authToken is: %s", prefs.authToken)

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

            override fun onFailure(call: Call<SignupResult>, t: Throwable) {
                val apiError = t.getApiError()
                Timber.d(
                    "result: response error, code: %s, message: %s",
                    apiError?.code,
                    apiError?.message
                )
                failureCallback(apiError?.message)
            }
        })
    }

    fun signin() {

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