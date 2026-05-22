package com.example.kotlinmvppractice.screens.welcome

class WelcomeContract {
    interface View {
        fun navigateToLogin()
    }

    interface Presenter {
        fun onGetStartedClicked()
    }
}