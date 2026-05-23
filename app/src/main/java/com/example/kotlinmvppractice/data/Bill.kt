package com.example.kotlinmvppractice.data

import com.example.kotlinmvppractice.data.SplitMember

data class Bill(
    var name: String = "",
    var dueDate: String = "",
    var amount: String = "",
    var status: String = "Unpaid",
    var groupName: String = "",
    var splitMembers: MutableList<SplitMember> = mutableListOf()
)