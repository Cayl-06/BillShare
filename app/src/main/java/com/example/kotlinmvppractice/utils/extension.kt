package com.example.kotlinmvppractice.utils

import android.app.Activity
import android.widget.EditText
import android.widget.Toast

fun Activity.getEditTextValueEXT(id: Int): String {
    return(findViewById<EditText>(id).text.toString())
}

fun Activity.toastEXT(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
}