package com.example.kotlinmvppractice.screens.splitcalculator

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Bill
import com.example.kotlinmvppractice.data.SplitMember

class SplitCalculatorModel(private val app: CustomApp) {

	fun calculateEqualSplit(totalAmount: Double, memberCount: Int): Double {
		if (memberCount == 0) return 0.0
		return totalAmount / memberCount
	}

	fun calculateCustomSplit(amounts: List<Double>): Double = amounts.sum()

	fun formatAmount(amount: Double): String = "$%.2f".format(amount)

	fun saveBillWithSplit(
		billName: String,
		amount: String,
		dueDate: String,
		groupName: String,
		members: MutableList<SplitMember>   // ← now uses the imported one, no conflict
	) {
		val bill = Bill(billName, dueDate, amount, "Unpaid", groupName, members)
		app.bills.add(bill)
	}

	fun getGroupMembers(groupName: String): List<String> {
		return app.groups.find { it.name == groupName }?.members
			?: listOf(app.loginUser.username)
	}
}