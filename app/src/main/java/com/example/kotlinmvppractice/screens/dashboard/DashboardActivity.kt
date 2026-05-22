package com.example.kotlinmvppractice.screens.dashboard

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Bill
import com.example.kotlinmvppractice.helper.BillListViewAdapter

class DashboardActivity : Activity(), DashboardContract.View {

    private lateinit var dashboardPresenter: DashboardPresenter

    // views
    private lateinit var textviewWelcome: TextView
    private lateinit var listViewBills: ListView
    private lateinit var btnAddBill: LinearLayout
    private lateinit var btnCreateGroup: LinearLayout

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

        // Add Bill button → adds a predefined bill (like ListViewPrac's buttonAdd)
        btnAddBill.setOnClickListener {
            dashboardPresenter.addBill()
            Toast.makeText(this, "Bill has been added.", Toast.LENGTH_SHORT).show()
        }

        // Create Group button → placeholder for future implementation
        btnCreateGroup.setOnClickListener {
            Toast.makeText(this, "Create Group coming soon!", Toast.LENGTH_SHORT).show()
        }

        // click → show bill name and amount (like ListViewPrac's onClick)
        listViewBills.setOnItemClickListener { _, _, position, _ ->
            val bill = billList[position]
            Toast.makeText(this, "${bill.name}: ${bill.amount}", Toast.LENGTH_SHORT).show()
        }


        listViewBills.setOnItemLongClickListener { _, _, position, _ ->
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
        // sync the adapter's list then notify (same as notifyDataSetChanged in ListViewPrac)
        billList.clear()
        billList.addAll(bills)
        adapter.notifyDataSetChanged()
    }
}