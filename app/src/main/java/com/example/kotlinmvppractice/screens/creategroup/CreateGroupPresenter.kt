package com.example.kotlinmvppractice.screens.creategroup

class CreateGroupPresenter(
    private val view: CreateGroupContract.View,
    private val model: CreateGroupModel
) : CreateGroupContract.Presenter {

    override fun onAddMember(name: String) {
        if (name.isEmpty()) {
            view.showEmptyMemberField()
            return
        }

        if (model.isMemberAlreadyAdded(name)) {
            view.showMemberAlreadyAdded()
            return
        }

        model.addMember(name)
        view.showMemberAdded(name)
        view.updateMemberList(model.getMembers())
    }

    override fun onCreateGroup(groupName: String) {
        if (groupName.isEmpty()) {
            view.showEmptyGroupName()
            return
        }

        model.createGroup(groupName)
        view.showGroupCreated()
    }

    override fun removeMember(position: Int) {
        model.removeMember(position)
        view.updateMemberList(model.getMembers())
    }
}