package com.example.kotlinmvppractice.screens.dashboard

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import android.widget.ImageView
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Bill
import com.example.kotlinmvppractice.helper.BillListViewAdapter
import com.example.kotlinmvppractice.screens.billdetails.BillDetailsActivity
import com.example.kotlinmvppractice.screens.groups.GroupsActivity
import com.example.kotlinmvppractice.screens.allbills.AllBillsActivity
import com.example.kotlinmvppractice.screens.notifications.NotificationsActivity
import com.example.kotlinmvppractice.screens.addbill.AddBillActivity
import com.example.kotlinmvppractice.screens.creategroup.CreateGroupActivity
import com.example.kotlinmvppractice.screens.profile.ProfileActivity

class DashboardActivity : Activity(), DashboardContract.View {

    private lateinit var dashboardPresenter: DashboardPresenter

    // views
    private lateinit var textviewWelcome: TextView
    private lateinit var listViewBills: ListView
    private lateinit var btnAddBill: LinearLayout
    private lateinit var btnCreateGroup: LinearLayout
    private lateinit var tvToPayAmount: TextView
    private lateinit var tvToReceiveAmount: TextView
    private lateinit var btnSeeAllBills: TextView
    private lateinit var btnNotifications: ImageView
    private lateinit var navGroups: LinearLayout
    private lateinit var navBills: LinearLayout
    private lateinit var navProfile: LinearLayout

    // step 3: adapter declared at class level (same pattern as ListViewPrac)
    private lateinit var adapter: BillListViewAdapter
    private val billList: MutableList<Bill> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // bind views
        textviewWelcome = findViewById(R.id.tvDashboardUsername)
        listViewBills   = findViewById(R.id.listViewBills)
        btnAddBill      = findViewById(R.id.btnAddBill)
        btnCreateGroup  = findViewById(R.id.btnCreateGroup)
        tvToPayAmount = findViewById(R.id.tvToPayAmount)
        tvToReceiveAmount = findViewById(R.id.tvToReceiveAmount)
        btnSeeAllBills = findViewById(R.id.btnSeeAllBills)
        btnNotifications = findViewById(R.id.btnNotifications)
        navGroups = findViewById(R.id.navGroups)
        navBills = findViewById(R.id.navBills)
        navProfile = findViewById(R.id.navProfile)

        // set up presenter (MVP)
        dashboardPresenter = DashboardPresenter(
            this,
            DashboardModel(application as CustomApp)
        )

        // step 3: create adapter and attach to ListView
        adapter = BillListViewAdapter(this, billList)
        listViewBills.adapter = adapter

        // load initial data via presenter
        dashboardPresenter.initializeUsername()
        dashboardPresenter.loadBills()

        // Navigate to AddBillActivity
        btnAddBill.setOnClickListener {
            startActivity(Intent(this, AddBillActivity::class.java))
        }

        // Navigate to CreateGroupActivity
        btnCreateGroup.setOnClickListener {
            startActivity(Intent(this, CreateGroupActivity::class.java))
        }

        btnNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        btnSeeAllBills.setOnClickListener {
            startActivity(Intent(this, AllBillsActivity::class.java))
            finish()
        }

        navGroups.setOnClickListener {
            startActivity(Intent(this, GroupsActivity::class.java))
            finish()
        }

        navBills.setOnClickListener {
            startActivity(Intent(this, AllBillsActivity::class.java))
            finish()
        }
        
        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

        listViewBills.setOnItemClickListener { _, _, position, _ ->
            val bill = billList[position]
            val intent = Intent(this, BillDetailsActivity::class.java)
            intent.putExtra("BILL_NAME", bill.name)
            startActivity(intent)
        }


        listViewBills.setOnItemLongClickListener { _, _, position, _ ->
            showRemoveDialog(position)
            true
        }
    }

    override fun onResume() {
        super.onResume()
        dashboardPresenter.loadBills()
    }


    private fun showRemoveDialog(position: Int) {
        val bill = billList[position]
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Remove Bill")
        builder.setMessage("Are you sure you want to remove \"${bill.name}\"?")
        builder.setPositiveButton("Remove") { _, _ ->
            dashboardPresenter.removeBill(position)
            Toast.makeText(this, "\"${bill.name}\" has been removed.", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }


    override fun displayWelcomeUser(message: String) {
        textviewWelcome.text = message
    }

    override fun displayBills(bills: MutableList<Bill>) {
        billList.clear()
        billList.addAll(bills)
        adapter.notifyDataSetChanged()
    }

    override fun displayToPay(amount: String) {
        tvToPayAmount.text = amount
    }

    override fun displayToReceive(amount: String) {
        tvToReceiveAmount.text = amount
    }
}