package com.example.kotlinmvppractice.app
import android.app.Application
import android.util.Log
import com.example.kotlinmvppractice.data.User

class CustomApp: Application() {
    //onCreate lifecyle
    //global variables
    val username = "Cayl Redublado"
    val password = "1234"

    var loginUser = User()
    override fun onCreate() {
        super.onCreate()
        Log.e("Custom App", "onCreate is called")
    }
}