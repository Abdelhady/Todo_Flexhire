package com.example.todo_flexhire.backend

import com.example.todo_flexhire.prefs
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * https://dev.to/paulodhiambo/kotlin-and-retrofit-network-calls-2353
 *
 * https://developer.android.com/training/dependency-injection/dagger-android#dagger-modules
 */
@Module
class NetworkModule {

    // For bearer authorization ; https://stackoverflow.com/a/41082979/905801
    // TODO should we disable this when we don't have token ? !
    var client = OkHttpClient.Builder().addInterceptor(object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response? {
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${prefs.authToken}")
                .build()
            return chain.proceed(newRequest)
        }
    }).build()

    // https://stackoverflow.com/a/11810183/905801
    // https://futurestud.io/tutorials/retrofit-2-adding-customizing-the-gson-converter
    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)// to convert `created_at` to `createdAt`
        .create()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://todos.flexhire.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideTodoService(): FlexhireTodoService {
        return retrofit.create(FlexhireTodoService::class.java)
    }

}