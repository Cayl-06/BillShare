package com.example.kotlinmvppractice.screens.dashboard

import com.example.kotlinmvppractice.data.Bill

class DashboardPresenter(
    private val view: DashboardContract.View,
    private val model: DashboardModel
) : DashboardContract.Presenter {

    // holds the live bill list so add/remove mutate the same reference
    private val bills: MutableList<Bill> = mutableListOf()

    override fun initializeUsername() {
        val username = model.getUsername()
        if (username.isNotEmpty()) {
            view.displayWelcomeUser(username)
        } else {
            view.displayWelcomeUser("User")
        }
    }

    override fun loadBills() {
        bills.clear()
        bills.addAll(model.getBills())
        view.displayBills(bills)
    }

    // adds a predefined bill (like the dummy Student add in ListViewPrac)
    override fun addBill() {
        bills.add(model.getNewBill())
        view.displayBills(bills)
    }

    // removes the bill at the given position (triggered by long-click dialog)
    override fun removeBill(position: Int) {
        if (position in bills.indices) {
            bills.removeAt(position)
            view.displayBills(bills)
        }
    }
}