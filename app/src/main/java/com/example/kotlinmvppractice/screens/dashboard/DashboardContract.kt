package com.example.kotlinmvppractice.screens.dashboard

import com.example.kotlinmvppractice.data.Bill

class DashboardContract {

    interface View {
        fun displayWelcomeUser(message: String)
        fun displayBills(bills: MutableList<Bill>)
    }

    interface Presenter {
        fun initializeUsername()
        fun loadBills()
        fun addBill()
        fun removeBill(position: Int)
    }
}