package com.example.todo_flexhire

import android.app.Application
import com.example.todo_flexhire.utils.Prefs
import timber.log.Timber

class TodoApplication : Application() {

    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
        Timber.plant(Timber.DebugTree());
    }

}

val prefs: Prefs by lazy {
    TodoApplication.prefs!!
}