package com.example.todo_flexhire

import android.app.Application
import android.content.Context
import com.example.todo_flexhire.di.DaggerApplicationComponent
import com.example.todo_flexhire.utils.Prefs
import timber.log.Timber

class TodoApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()

    companion object {
        var prefs: Prefs? = null
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
        Timber.plant(Timber.DebugTree())
        TodoApplication.appContext = applicationContext
    }

}

val prefs: Prefs by lazy {
    TodoApplication.prefs!!
}