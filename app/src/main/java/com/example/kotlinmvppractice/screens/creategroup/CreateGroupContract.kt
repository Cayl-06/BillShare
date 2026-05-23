package com.example.kotlinmvppractice.screens.creategroup

class CreateGroupContract {
    interface View {
        fun showMemberAdded(member: String)
        fun showGroupCreated()
        fun showEmptyGroupName()
        fun showEmptyMemberField()
        fun showMemberAlreadyAdded()
        fun updateMemberList(members: MutableList<String>)
    }

    interface Presenter {
        fun onAddMember(name: String)
        fun onCreateGroup(groupName: String)
        fun removeMember(position: Int)
    }
}