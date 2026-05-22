package com.example.kotlinmvppractice.screens.login

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.User

class LoginModel(val app: CustomApp) {
    fun validateCredentials(username: String, password: String): Boolean {
        return username.equals(app.username, false) && password.equals(app.password, false)
    }

    fun saveData(username: String, password: String) {
        app.loginUser = User(username, password)
    }


}