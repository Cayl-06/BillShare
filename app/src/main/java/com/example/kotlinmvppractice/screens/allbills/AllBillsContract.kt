package com.example.kotlinmvppractice.screens.allbills

import com.example.kotlinmvppractice.data.Bill

class AllBillsContract {
	interface View {
		fun displayBills(bills: MutableList<Bill>)
	}

	interface Presenter {
		fun loadBills()
		fun removeBill(position: Int)
	}
}