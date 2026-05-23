package com.example.kotlinmvppractice.screens.billdetails

import com.example.kotlinmvppractice.data.Bill
import com.example.kotlinmvppractice.data.SplitMember

class BillDetailsContract {
	interface View {
		fun displayBillInfo(bill: Bill)
		fun displaySplitMembers(members: MutableList<SplitMember>)
		fun showMemberMarkedPaid(memberName: String)
		fun showBillFullyPaid()
		fun refreshList()
	}

	interface Presenter {
		fun loadBillDetails()
		fun onMarkPaid(memberPosition: Int)
	}
}