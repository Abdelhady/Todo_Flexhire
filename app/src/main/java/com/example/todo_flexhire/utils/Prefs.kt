package com.example.todo_flexhire.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * https://blog.teamtreehouse.com/making-sharedpreferences-easy-with-kotlin
 */
class Prefs(context: Context) {
    val PREFS_FILENAME = "com.example.todo_flexhire.prefs"
    val AUTH_TOKEN = "auth_token"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var authToken: String
        get() = prefs.getString(AUTH_TOKEN, "")!! // TODO should the defValue be empty or null!
        set(value) = prefs.edit().putString(AUTH_TOKEN, value).apply()

}