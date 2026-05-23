package com.example.kotlinmvppractice.screens.splitcalculator

import com.example.kotlinmvppractice.data.SplitMember

class SplitCalculatorPresenter(
    private val view: SplitCalculatorContract.View,
    private val model: SplitCalculatorModel
) : SplitCalculatorContract.Presenter {

    private var isEqualSplit = true
    private var totalAmountDouble = 0.0
    private var currentMembers = mutableListOf<com.example.kotlinmvppractice.data.SplitMember>()
    private var billName: String = ""
    private var dueDate: String = ""
    private var groupName: String = ""

    override fun loadData(billName: String, totalAmount: String, dueDate: String, groupName: String, members: List<String>) {
        this.billName = billName
        this.dueDate = dueDate
        this.groupName = groupName

        val amountStr = totalAmount.replace("$", "")
        totalAmountDouble = amountStr.toDoubleOrNull() ?: 0.0

        currentMembers.clear()
        for (member in members) {
            currentMembers.add(SplitMember(name = member, amount = "0.00"))
        }

        view.displayTotalBill("$%.2f".format(totalAmountDouble))
        onEqualSplitTab()
    }

    override fun onEqualSplitTab() {
        isEqualSplit = true
        view.showTabSelected(true)
        val splitAmount = model.calculateEqualSplit(totalAmountDouble, currentMembers.size)
        val splitAmountFormatted = model.formatAmount(splitAmount)
        for (member in currentMembers) {
            member.amount = splitAmountFormatted
        }
        view.displayMembers(currentMembers)
    }

    override fun onCustomSplitTab() {
        isEqualSplit = false
        view.showTabSelected(false)
        view.displayMembers(currentMembers)
    }

    override fun onConfirmSave() {
        // Save bill with split members via model
        model.saveBillWithSplit(billName, "$%.2f".format(totalAmountDouble), dueDate, groupName, currentMembers)
        view.showSavedSuccess()
    }
}