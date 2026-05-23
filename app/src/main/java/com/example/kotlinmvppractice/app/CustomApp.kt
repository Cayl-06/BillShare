package com.example.kotlinmvppractice.app

import android.app.Application
import android.util.Log
import com.example.kotlinmvppractice.data.User
import com.example.kotlinmvppractice.data.Group
import com.example.kotlinmvppractice.data.Bill

class CustomApp: Application() {
    //onCreate lifecyle
    //global variables
    val username = "Cayl Redublado"
    val password = "1234"

    var loginUser = User()
    var registeredUsers = mutableListOf<User>()
    var groups = mutableListOf<Group>()
    var bills = mutableListOf<Bill>()

    override fun onCreate() {
        super.onCreate()
        Log.e("Custom App", "onCreate is called")
    }
}