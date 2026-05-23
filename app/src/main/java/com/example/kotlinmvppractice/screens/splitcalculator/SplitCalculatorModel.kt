package com.example.kotlinmvppractice.screens.splitcalculator

import com.example.kotlinmvppractice.app.CustomApp

class SplitCalculatorModel(private val app: CustomApp) {
	fun calculateEqualSplit(totalAmount: Double, memberCount: Int): Double {
		if (memberCount == 0) return 0.0
		return totalAmount / memberCount
	}

	fun calculateCustomSplit(amounts: List<Double>): Double {
		return amounts.sum()
	}

	fun formatAmount(amount: Double): String = "$%.2f".format(amount)
}