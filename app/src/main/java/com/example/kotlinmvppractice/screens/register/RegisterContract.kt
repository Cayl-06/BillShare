package com.example.kotlinmvppractice.screens.register

class RegisterContract {
	interface View {
		fun showEmpty()
		fun showSuccess()
		fun showError(nameEmpty: Boolean, emailEmpty: Boolean, passEmpty: Boolean, confirmEmpty: Boolean)
		fun showPasswordMismatch()
		fun navigateToLogin()
	}

	interface Presenter {
		fun onRegister(name: String, email: String, password: String, confirmPassword: String)
	}
}