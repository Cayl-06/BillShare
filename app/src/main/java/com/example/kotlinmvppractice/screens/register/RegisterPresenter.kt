package com.example.kotlinmvppractice.screens.register

class RegisterPresenter(private val view: RegisterContract.View, private val model: RegisterModel) : RegisterContract.Presenter {
	override fun onRegister(name: String, email: String, password: String, confirmPassword: String) {
		val nameEmpty = name.isEmpty()
		val emailEmpty = email.isEmpty()
		val passEmpty = password.isEmpty()
		val confirmEmpty = confirmPassword.isEmpty()

		if (nameEmpty || emailEmpty || passEmpty || confirmEmpty) {
			view.showError(nameEmpty, emailEmpty, passEmpty, confirmEmpty)
			view.showEmpty()
			return
		}

		if (password != confirmPassword) {
			view.showPasswordMismatch()
			return
		}

		if (model.isAlreadyRegistered(email)) {
			view.showError(false, true, false, false)
			return
		}

		model.register(name, email, password)
		view.showSuccess()
		view.navigateToLogin()
	}
}