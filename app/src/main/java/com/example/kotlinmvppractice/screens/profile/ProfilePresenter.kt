package com.example.kotlinmvppractice.screens.profile

class ProfilePresenter(private val view: ProfileContract.View, private val model: ProfileModel) : ProfileContract.Presenter {
    override fun loadProfile() {
        view.displayProfile(model.getUsername(), model.getInitial())
        view.displayStats(model.getGroupCount(), model.getBillCount(), model.getUnpaidCount())
    }

    override fun onLogoutClicked() {
        view.showLogoutConfirmDialog()
    }

    override fun onLogoutConfirmed() {
        model.logout()
        view.navigateToWelcome()
    }
}
package com.example.kotlinmvppractice.screens.profile

class ProfilePresenter {
}