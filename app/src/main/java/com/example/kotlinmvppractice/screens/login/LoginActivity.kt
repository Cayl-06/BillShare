package com.example.kotlinmvppractice.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.screens.dashboard.DashboardActivity
import com.example.kotlinmvppractice.utils.getEditTextValueEXT
import com.example.kotlinmvppractice.utils.toastEXT

class LoginActivity : Activity(), LoginContract.View {
    lateinit var loginpresenter : LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) //controls the elements in the xml

        loginpresenter = LoginPresenter(this, LoginModel(application as CustomApp))

        val editTextUserName = findViewById<EditText>(R.id.etUsername)
        val editTextPassword = findViewById<EditText>(R.id.etPassword)
        val buttonLogin = findViewById<Button>(R.id.btnLogin)
        val tvCreateAccount = findViewById<android.widget.TextView>(R.id.tvCreateAccount)

        tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, com.example.kotlinmvppractice.screens.register.RegisterActivity::class.java))
        }

        //when btn login is pressed
        //basic / long way instead of using extensions for cleaner code
        buttonLogin.setOnClickListener{
            val username = getEditTextValueEXT(R.id.etUsername) //getEditTextValue from extensions file
            val password = getEditTextValueEXT(R.id.etPassword)


            loginpresenter.onLogin(username, password)
        }
    }

    override fun showEmpty() = toastEXT("Fields are empty!")

    override fun showSuccess() = toastEXT("Success!")

    override fun showError() = toastEXT("Invalid Credentials")
    override fun showDashboardScreen() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}