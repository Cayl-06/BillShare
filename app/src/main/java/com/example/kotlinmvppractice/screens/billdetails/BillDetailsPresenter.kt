package com.example.kotlinmvppractice.screens.billdetails

import com.example.kotlinmvppractice.data.SplitMember

class BillDetailsPresenter(
    private val view: BillDetailsContract.View,
    private val model: BillDetailsModel,
    private val billName: String
) : BillDetailsContract.Presenter {

    override fun loadBillDetails() {
        val bill = model.getBill(billName) ?: return
        view.displayBillInfo(bill)
        view.displaySplitMembers(model.getMembers(billName))
    }

    override fun onMarkPaid(memberPosition: Int) {
        val members = model.getMembers(billName)
        if (memberPosition !in members.indices) return
        val member = members[memberPosition]
        if (member.isPaid) return
        model.markMemberPaid(billName, memberPosition)
        view.showMemberMarkedPaid(member.name)
        view.refreshList()
        if (model.isFullyPaid(billName)) {
            view.showBillFullyPaid()
        }
    }
}
package com.example.kotlinmvppractice.screens.billdetails

class BillDetailsPresenter {
}