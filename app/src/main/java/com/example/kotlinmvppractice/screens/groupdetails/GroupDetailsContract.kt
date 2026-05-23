package com.example.kotlinmvppractice.screens.groupdetails

import com.example.kotlinmvppractice.data.Bill
import com.example.kotlinmvppractice.data.Group

class GroupDetailsContract {
	interface View {
		fun displayGroupInfo(group: Group)
		fun displayBills(bills: MutableList<Bill>)
		fun displayMemberAvatars(members: List<String>)
		fun navigateToAddBill(groupName: String)
	}

	interface Presenter {
		fun loadGroupDetails()
		fun onAddBillClicked()
	}
}