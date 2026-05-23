package com.example.kotlinmvppractice.app

import android.app.Application
import android.util.Log
import com.example.kotlinmvppractice.data.User
import com.example.kotlinmvppractice.data.Group
import com.example.kotlinmvppractice.data.Bill
import com.example.kotlinmvppractice.data.SplitMember

class CustomApp: Application() {
    //onCreate lifecyle
    //global variables
    val username = "Cayl Redublado"
    val password = "1234"

    var loginUser = User()
    var registeredUsers = mutableListOf<User>()
    var groups = mutableListOf<Group>()
    var bills = mutableListOf<Bill>()

    override fun onCreate() {
        super.onCreate()
        Log.e("Custom App", "onCreate is called")
        // Seed dummy groups so the app has data immediately
        groups.add(Group("Apartment 3B", mutableListOf("Alex Doe", "Jordan Smith", "Casey Lee")))
        groups.add(Group("Dorm Room A", mutableListOf("Sam", "You")))

        // Seed dummy bills with splitMembers so BillDetails works immediately
        val electricityMembers = mutableListOf(
            SplitMember("Alex Doe", "$40.00", false),
            SplitMember("Jordan Smith", "$40.00", false),
            SplitMember("Casey Lee", "$40.00", false)
        )
        val wifiMembers = mutableListOf(
            SplitMember("Alex Doe", "$30.00", false),
            SplitMember("Jordan Smith", "$30.00", false)
        )
        bills.add(Bill("Electricity", "3/15/2026", "$120.00", "Unpaid", "Apartment 3B", electricityMembers))
        bills.add(Bill("Wi-Fi",       "3/20/2026", "$60.00",  "Unpaid", "Apartment 3B", wifiMembers))
    }
}