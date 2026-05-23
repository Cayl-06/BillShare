package com.example.kotlinmvppractice.screens.groupdetails

class GroupDetailsPresenter(
    private val view: GroupDetailsContract.View,
    private val model: GroupDetailsModel,
    private val groupName: String
) : GroupDetailsContract.Presenter {

    override fun loadGroupDetails() {
        val group = model.getGroup() ?: return
        view.displayGroupInfo(group)
        view.displayMemberAvatars(group.members)
        view.displayBills(model.getBillsForGroup())
    }

    override fun onAddBillClicked() {
        view.navigateToAddBill(groupName)
    }
}
