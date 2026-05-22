package com.example.kotlinmvppractice.screens.login

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.utils.toastEXT

class LoginPresenter(
    val view: LoginContract.View,
    val model: LoginModel
): LoginContract.Presenter {
    override fun onLogin(username: String, password: String) {
        if(!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
                if(model.validateCredentials(username, password)) {
                    model.saveData(username, password)
                    view.showSuccess()
                    view.showDashboardScreen()
                } else {
                    view.showError()
                }
            } else {
                view.showEmpty()
            }

    }
}