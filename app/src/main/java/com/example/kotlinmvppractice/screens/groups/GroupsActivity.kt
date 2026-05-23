package com.example.kotlinmvppractice.screens.groups

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Group
import com.example.kotlinmvppractice.helper.GroupAdapter
import com.example.kotlinmvppractice.screens.dashboard.DashboardActivity
import com.example.kotlinmvppractice.screens.allbills.AllBillsActivity

class GroupsActivity : Activity(), GroupsContract.View {
	private lateinit var presenter: GroupsPresenter
	private lateinit var listViewGroups: ListView
	private lateinit var navHome: LinearLayout
	private lateinit var navGroups: LinearLayout
	private lateinit var navBills: LinearLayout
	private lateinit var navProfile: LinearLayout

	private lateinit var adapter: GroupAdapter
	private val groupList: MutableList<Group> = mutableListOf()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_groups)

		listViewGroups = findViewById(R.id.listViewGroups)
		navHome = findViewById(R.id.navHome)
		navGroups = findViewById(R.id.navGroups)
		navBills = findViewById(R.id.navBills)
		navProfile = findViewById(R.id.navProfile)

		presenter = GroupsPresenter(this, GroupsModel(application as CustomApp))

		adapter = GroupAdapter(this, groupList)
		listViewGroups.adapter = adapter

		presenter.loadGroups()

		navHome.setOnClickListener { startActivity(Intent(this, DashboardActivity::class.java)); finish() }
		navGroups.setOnClickListener { /* stay */ }
		navBills.setOnClickListener { startActivity(Intent(this, AllBillsActivity::class.java)); finish() }
		navProfile.setOnClickListener { Toast.makeText(this, "Profile coming soon", Toast.LENGTH_SHORT).show() }

		listViewGroups.setOnItemClickListener { _, _, position, _ ->
			val g = groupList[position]
			Toast.makeText(this, g.name, Toast.LENGTH_SHORT).show()
		}
	}

	override fun displayGroups(groups: MutableList<Group>) {
		groupList.clear()
		groupList.addAll(groups)
		adapter.notifyDataSetChanged()
	}
}