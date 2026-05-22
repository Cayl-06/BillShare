package com.example.kotlinmvppractice.screens.welcome

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.screens.login.LoginActivity

class WelcomeActivity : Activity(), WelcomeContract.View {

    private lateinit var welcomePresenter: WelcomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        welcomePresenter = WelcomePresenter(this)

        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)
        btnGetStarted.setOnClickListener {
            welcomePresenter.onGetStartedClicked()
        }
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
//        finish()
    }
}