package com.example.kotlinmvppractice.screens.dashboard

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Bill

class DashboardModel(private val app: CustomApp) {

    fun getUsername(): String {
        return app.loginUser.username
    }

    // step 2: pre-defined bill list (static/dummy data like the Student list in ListViewPrac)
    fun getBills(): MutableList<Bill> {
        return mutableListOf(
            Bill("Electricity", "5/15/2026", "$120.00", "Unpaid"),
            Bill("Wi-Fi",       "5/20/2026", "$60.00",  "Unpaid"),
            Bill("Water",       "5/22/2026", "$35.00",  "Unpaid"),
            Bill("Rent",        "5/01/2026", "$500.00", "Paid"),
            Bill("Gas",         "5/18/2026", "$45.00",  "Unpaid")
        )
    }

    // pre-defined bill used when Add Bill is tapped (like the dummy Student in ListViewPrac)
    fun getNewBill(): Bill {
        return Bill("New Bill", "5/30/2026", "$0.00", "Unpaid")
    }
}