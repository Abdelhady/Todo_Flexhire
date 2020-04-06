package com.example.todo_flexhire.ui.bindingAdapters

import android.widget.EditText
import androidx.databinding.BindingAdapter


object CustomBindingAdaptors {

//    @BindingAdapter("validators_email")
//    @JvmStatic
//    fun emailValidator(editText: EditText, value: String) {
//        if (value?.isEmpty()) {
//            editText.error = "Can't be empty"
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
//            editText.error = "Invalid Email Address"
//        } else {
//            editText.error = null
//        }
//    }


    /**
     * I had to make this `errorText` because it doesn't exist in Android
     */
    @BindingAdapter("errorText")
    @JvmStatic
    fun showError(editText: EditText, value: String?) {
        editText.error = value
    }

}