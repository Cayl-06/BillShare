package com.example.kotlinmvppractice.screens.billdetails

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Bill
import com.example.kotlinmvppractice.data.SplitMember

class BillDetailsModel(private val app: CustomApp) {
    fun getBill(billName: String): Bill? = app.bills.find { it.name == billName }

    fun getMembers(billName: String): MutableList<SplitMember> = getBill(billName)?.splitMembers ?: mutableListOf()

    fun markMemberPaid(billName: String, position: Int) {
        val bill = getBill(billName) ?: return
        if (position in bill.splitMembers.indices) {
            bill.splitMembers[position].isPaid = true
            if (bill.splitMembers.all { it.isPaid }) {
                bill.status = "Paid"
            }
        }
    }

    fun isFullyPaid(billName: String): Boolean = getMembers(billName).all { it.isPaid }
}
package com.example.kotlinmvppractice.screens.billdetails

class BillDetailsModel {
}