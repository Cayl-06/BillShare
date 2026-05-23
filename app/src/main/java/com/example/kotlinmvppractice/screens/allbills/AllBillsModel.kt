package com.example.kotlinmvppractice.screens.allbills

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Bill

class AllBillsModel(private val app: CustomApp) {
	fun getBills(): MutableList<Bill> = app.bills

	fun removeBill(position: Int) {
		if (position >= 0 && position < app.bills.size) app.bills.removeAt(position)
	}
}