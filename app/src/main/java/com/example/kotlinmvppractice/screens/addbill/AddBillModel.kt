package com.example.kotlinmvppractice.screens.addbill

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Bill

class AddBillModel(private val app: CustomApp) {
	fun saveBill(bill: Bill) {
		app.bills.add(bill)
	}

	fun getGroups() = app.groups
}