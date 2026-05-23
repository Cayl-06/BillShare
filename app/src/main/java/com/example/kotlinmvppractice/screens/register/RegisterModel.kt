package com.example.kotlinmvppractice.screens.register

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.User

class RegisterModel(private val app: CustomApp) {
	fun register(name: String, email: String, password: String) {
		app.registeredUsers.add(User(email, password))
		app.loginUser = User(name, password)
	}

	fun isAlreadyRegistered(email: String): Boolean {
		return app.registeredUsers.any { it.username.equals(email, true) }
	}
}