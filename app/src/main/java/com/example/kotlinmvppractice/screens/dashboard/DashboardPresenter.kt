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
        calculateBalances()
    }

    // adds a predefined bill (like the dummy Student add in ListViewPrac)
    override fun addBill() {
        bills.add(model.getNewBill())
        view.displayBills(bills)
        calculateBalances()
    }

    // removes the bill at the given position (triggered by long-click dialog)
    override fun removeBill(position: Int) {
        if (position in bills.indices) {
            model.removeBill(position)
            bills.removeAt(position)
            view.displayBills(bills)
            calculateBalances()
        }
    }

    override fun calculateBalances() {
        var toPay = 0.0
        var toReceive = 0.0

        for (bill in bills) {
            val amountStr = bill.amount.replace("$", "")
            val amount = amountStr.toDoubleOrNull() ?: 0.0
            
            // Dummy logic: Unpaid is "To Pay", others don't count, or just assign something generic.
            // Let's divide arbitrary amounts for the sake of the dashboard.
            if (bill.status == "Unpaid") {
                toPay += amount
            } else {
                toReceive += amount
            }
        }

        view.displayToPay(String.format("$%.2f", toPay))
        view.displayToReceive(String.format("$%.2f", toReceive))
    }
}