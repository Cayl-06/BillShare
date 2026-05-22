package com.example.kotlinmvppractice.screens.welcome

class WelcomePresenter(
    val view: WelcomeContract.View
) : WelcomeContract.Presenter {
    override fun onGetStartedClicked() {
        view.navigateToLogin()
    }
}