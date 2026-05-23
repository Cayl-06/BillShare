package com.example.kotlinmvppractice.screens.profile

class ProfileContract {
	interface View {
		fun displayProfile(name: String, initial: String)
		fun displayStats(groupCount: Int, billCount: Int, unpaidCount: Int)
		fun showLogoutConfirmDialog()
		fun navigateToWelcome()
	}

	interface Presenter {
		fun loadProfile()
		fun onLogoutClicked()
		fun onLogoutConfirmed()
	}
}