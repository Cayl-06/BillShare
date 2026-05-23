package com.example.kotlinmvppractice.screens.creategroup

import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Group

class CreateGroupModel(private val app: CustomApp) {

    private val members = mutableListOf<String>()

    init {
        // Add creator automatically
        members.add(app.username) 
    }

    fun addMember(name: String) {
        members.add(name)
    }

    fun removeMember(position: Int) {
        if (position in members.indices) {
            members.removeAt(position)
        }
    }

    fun isMemberAlreadyAdded(name: String): Boolean {
        return members.contains(name)
    }

    fun createGroup(groupName: String) {
        app.groups.add(Group(groupName, members.toMutableList()))
    }

    fun getMembers(): MutableList<String> {
        return members
    }
}