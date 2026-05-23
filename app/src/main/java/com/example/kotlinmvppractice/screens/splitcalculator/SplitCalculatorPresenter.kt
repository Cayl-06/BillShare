package com.example.kotlinmvppractice.screens.splitcalculator

class SplitCalculatorPresenter(
    private val view: SplitCalculatorContract.View,
    private val model: SplitCalculatorModel
) : SplitCalculatorContract.Presenter {

    private var isEqualSplit = true
    private var totalAmountDouble = 0.0
    private var currentMembers = mutableListOf<SplitMember>()

    override fun loadData(totalAmount: String, members: List<String>) {
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
        // Validate custom if needed
        view.showSavedSuccess()
    }
}