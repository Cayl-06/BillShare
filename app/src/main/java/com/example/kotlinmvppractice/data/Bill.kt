package com.example.kotlinmvppractice.data

data class Bill(
    var name: String = "",
    var dueDate: String = "",
    var amount: String = "",
    var status: String = "Unpaid"
)