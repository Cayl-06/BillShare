package com.example.kotlinmvppractice.screens.groups

import com.example.kotlinmvppractice.data.Group

class GroupsContract {
	interface View {
		fun displayGroups(groups: MutableList<Group>)
	}

	interface Presenter {
		fun loadGroups()
	}
}