package com.example.todo_flexhire.backend.model

import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response

/**
 * https://stackoverflow.com/a/61475050/905801
 */
class ApiError(var code: Int, val message: String = "General error occurred") // TODO string here should be extracted for localization

// To be used in `onFailure()`
fun Throwable.getApiError(): ApiError? {
    if (this is HttpException) {
        try {
            val errorJsonString = this.response()?.errorBody()?.string()
            val apiError = Gson().fromJson(errorJsonString, ApiError::class.java)
            apiError.code = this.response()?.code()!!
            return apiError
        } catch (exception: Exception) {
            return ApiError(this.response()?.code()!!)
        }
    } else {
        return ApiError(0)
    }
}

// To be used in `onResponse()`
fun Response<out Any>.getApiError(): ApiError? {
    try {
        val errorJsonString = this?.errorBody()?.string()
        val apiError = Gson().fromJson(errorJsonString, ApiError::class.java)
        apiError.code = this?.code()!!
        return apiError
    } catch (exception: Exception) {
        return ApiError(this?.code()!!)
    }
}