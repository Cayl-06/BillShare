package com.example.kotlinmvppractice.screens.splitcalculator

class SplitCalculatorContract {
	interface View {
		fun displayTotalBill(amount: String)
		fun displayMembers(members: MutableList<SplitMember>)
		fun showSavedSuccess()
		fun showTabSelected(isEqualSplit: Boolean)
	}

	interface Presenter {
		fun loadData(billName: String, totalAmount: String, dueDate: String, groupName: String, members: List<String>)
		fun onEqualSplitTab()
		fun onCustomSplitTab()
		fun onConfirmSave()
	}
}