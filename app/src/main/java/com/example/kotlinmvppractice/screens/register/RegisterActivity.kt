package com.example.kotlinmvppractice.screens.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.utils.getEditTextValueEXT
import com.example.kotlinmvppractice.utils.toastEXT
import com.example.kotlinmvppractice.screens.login.LoginActivity

class RegisterActivity : Activity(), RegisterContract.View {
	private lateinit var presenter: RegisterPresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_register)

		presenter = RegisterPresenter(this, RegisterModel(application as CustomApp))

		val etFullName = findViewById<EditText>(R.id.etFullName)
		val etRegisterEmail = findViewById<EditText>(R.id.etRegisterEmail)
		val etRegisterPassword = findViewById<EditText>(R.id.etRegisterPassword)
		val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
		val btnRegister = findViewById<Button>(R.id.btnRegister)
		val tvBackToLogin = findViewById<TextView>(R.id.tvBackToLogin)

		btnRegister.setOnClickListener {
			presenter.onRegister(
				getEditTextValueEXT(R.id.etFullName),
				getEditTextValueEXT(R.id.etRegisterEmail),
				getEditTextValueEXT(R.id.etRegisterPassword),
				getEditTextValueEXT(R.id.etConfirmPassword)
			)
		}

		tvBackToLogin.setOnClickListener { finish() }
	}

	override fun showEmpty() = toastEXT("Please fill in all fields!")

	override fun showSuccess() {
		toastEXT("Account created!")
		startActivity(Intent(this, LoginActivity::class.java))
		finish()
	}

	override fun showError(nameEmpty: Boolean, emailEmpty: Boolean, passEmpty: Boolean, confirmEmpty: Boolean) {
		toastEXT("Please check the highlighted fields")
	}

	override fun showPasswordMismatch() = toastEXT("Passwords do not match!")

	override fun navigateToLogin() {
		startActivity(Intent(this, LoginActivity::class.java))
	}
}