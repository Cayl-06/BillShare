package com.example.kotlinmvppractice.screens.addbill

import com.example.kotlinmvppractice.data.Bill

class AddBillPresenter(private val view: AddBillContract.View, private val model: AddBillModel) : AddBillContract.Presenter {
	override fun onCalculateSplit(name: String, amount: String, dueDate: String, groupName: String) {
		if (name.isEmpty() || amount.isEmpty() || dueDate.isEmpty()) {
			view.showEmpty()
			return
		}

		if (groupName.isEmpty()) {
			view.showNoGroupSelected()
			return
		}

		// format amount to $X.XX
		val amtDouble = try { amount.toDouble() } catch (e: Exception) { 0.0 }
		val formatted = "$%.2f".format(amtDouble)

		val bill = Bill(name, dueDate, formatted)
		model.saveBill(bill)
		view.showSuccess()
		view.navigateToSplitCalculator(bill, groupName)
	}

	override fun loadGroups() {
		val groups = model.getGroups().map { it.name }
		if (groups.isNotEmpty()) {
			view.updateSelectedGroup(groups.first())
		}
	}

	override fun onGroupSelectorClicked() {
		val names = model.getGroups().map { it.name }
		view.showGroupSelectionDialog(names)
	}

	override fun onGroupSelected(groupName: String) {
		view.updateSelectedGroup(groupName)
	}
}