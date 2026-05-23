package com.example.kotlinmvppractice.screens.groupdetails

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Bill
import com.example.kotlinmvppractice.data.Group

class GroupDetailsModel(private val app: CustomApp, private val groupName: String) {
    fun getGroup(): Group? = app.groups.find { it.name == groupName }

    fun getBillsForGroup(): MutableList<Bill> = app.bills.filter { it.groupName == groupName }.toMutableList()
}
package com.example.kotlinmvppractice.screens.groupdetails

class GroupDetailsModel {
}