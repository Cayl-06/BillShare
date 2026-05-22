package com.example.kotlinmvppractice.screens.login

class LoginContract {
    interface View {
        fun showEmpty()
        fun showSuccess()
        fun showError()
        fun showDashboardScreen()

    }

    interface Presenter {
        fun onLogin(username: String, password: String)
    }
}