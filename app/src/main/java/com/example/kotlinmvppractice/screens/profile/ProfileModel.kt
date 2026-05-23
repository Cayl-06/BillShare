package com.example.kotlinmvppractice.screens.profile

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.User

class ProfileModel(private val app: CustomApp) {
    fun getUsername(): String = app.loginUser.username
    fun getInitial(): String = app.loginUser.username.firstOrNull()?.uppercase() ?: "?"
    fun getGroupCount(): Int = app.groups.size
    fun getBillCount(): Int = app.bills.size
    fun getUnpaidCount(): Int = app.bills.count { it.status == "Unpaid" }
    fun logout() { app.loginUser = User() }
}
