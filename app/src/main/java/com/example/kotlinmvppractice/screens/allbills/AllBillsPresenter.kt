package com.example.kotlinmvppractice.screens.allbills

class AllBillsPresenter(private val view: AllBillsContract.View, private val model: AllBillsModel) : AllBillsContract.Presenter {
	override fun loadBills() {
		val bills = model.getBills()
		view.displayBills(bills)
	}

	override fun removeBill(position: Int) {
		model.removeBill(position)
		loadBills()
	}
}