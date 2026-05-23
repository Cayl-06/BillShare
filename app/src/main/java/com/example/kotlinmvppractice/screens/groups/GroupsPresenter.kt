package com.example.kotlinmvppractice.screens.groups

class GroupsPresenter(private val view: GroupsContract.View, private val model: GroupsModel) : GroupsContract.Presenter {
	override fun loadGroups() {
		view.displayGroups(model.getGroups())
	}
}