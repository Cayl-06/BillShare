package com.example.kotlinmvppractice.screens.allbills

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Bill
import com.example.kotlinmvppractice.helper.BillListViewAdapter
import com.example.kotlinmvppractice.screens.dashboard.DashboardActivity
import com.example.kotlinmvppractice.screens.groups.GroupsActivity

class AllBillsActivity : Activity(), AllBillsContract.View {

	private lateinit var presenter: AllBillsPresenter
	private lateinit var listViewAllBills: ListView
	private lateinit var navHome: LinearLayout
	private lateinit var navGroups: LinearLayout
	private lateinit var navBills: LinearLayout
	private lateinit var navProfile: LinearLayout

	private lateinit var adapter: BillListViewAdapter
	private val billList: MutableList<Bill> = mutableListOf()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_all_bills)

		listViewAllBills = findViewById(R.id.listViewAllBills)
		navHome = findViewById(R.id.navHome)
		navGroups = findViewById(R.id.navGroups)
		navBills = findViewById(R.id.navBills)
		navProfile = findViewById(R.id.navProfile)

		presenter = AllBillsPresenter(this, AllBillsModel(application as CustomApp))

		adapter = BillListViewAdapter(this, billList)
		listViewAllBills.adapter = adapter

		presenter.loadBills()

		navHome.setOnClickListener { startActivity(Intent(this, DashboardActivity::class.java)); finish() }
		navGroups.setOnClickListener { startActivity(Intent(this, GroupsActivity::class.java)); finish() }
		navBills.setOnClickListener { /* stay on bills */ }
		navProfile.setOnClickListener { Toast.makeText(this, "Profile coming soon", Toast.LENGTH_SHORT).show() }

		listViewAllBills.setOnItemLongClickListener { _, _, position, _ ->
			showRemoveDialog(position)
			true
		}
	}

	private fun showRemoveDialog(position: Int) {
		val bill = billList[position]
		val builder = AlertDialog.Builder(this)
		builder.setTitle("Remove Bill")
		builder.setMessage("Are you sure you want to remove \"${bill.name}\"?")
		builder.setPositiveButton("Remove") { _, _ ->
			presenter.removeBill(position)
			Toast.makeText(this, "\"${bill.name}\" has been removed.", Toast.LENGTH_SHORT).show()
		}
		builder.setNegativeButton("Cancel", null)
		builder.show()
	}

	override fun displayBills(bills: MutableList<Bill>) {
		billList.clear()
		billList.addAll(bills)
		adapter.notifyDataSetChanged()
	}
}