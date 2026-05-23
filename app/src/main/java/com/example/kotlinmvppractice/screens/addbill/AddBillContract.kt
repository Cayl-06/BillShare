package com.example.kotlinmvppractice.screens.addbill

import com.example.kotlinmvppractice.data.Bill

class AddBillContract {
	interface View {
		fun showEmpty()
		fun showSuccess()
		fun showNoGroupSelected()
		fun navigateToSplitCalculator(bill: Bill, groupName: String)
		fun showGroupSelectionDialog(groupNames: List<String>)
		fun updateSelectedGroup(groupName: String)
	}

	interface Presenter {
		fun onCalculateSplit(name: String, amount: String, dueDate: String, groupName: String)
		fun loadGroups()
		fun onGroupSelectorClicked()
		fun onGroupSelected(groupName: String)
	}
}