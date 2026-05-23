package com.example.kotlinmvppractice.screens.groups

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Group

class GroupsModel(private val app: CustomApp) {
	fun getGroups(): MutableList<Group> = app.groups
}