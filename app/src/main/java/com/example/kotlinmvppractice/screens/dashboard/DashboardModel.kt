package com.example.kotlinmvppractice.screens.dashboard

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Bill

class DashboardModel(private val app: CustomApp) {

    fun getUsername(): String {
        return app.loginUser.username
    }

    // step 2: pre-defined bill list (static/dummy data like the Student list in ListViewPrac)
    fun getBills(): MutableList<Bill> {
        return app.bills
    }

    fun removeBill(position: Int) {
        if (position >= 0 && position < app.bills.size) {
            app.bills.removeAt(position)
        }
    }

    // pre-defined bill used when Add Bill is tapped (like the dummy Student in ListViewPrac)
    fun getNewBill(): Bill {
        return Bill("New Bill", "5/30/2026", "$0.00", "Unpaid")
    }
}