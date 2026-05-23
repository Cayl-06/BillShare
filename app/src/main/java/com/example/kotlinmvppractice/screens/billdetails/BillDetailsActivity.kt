package com.example.kotlinmvppractice.screens.billdetails

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.SplitMember
import com.example.kotlinmvppractice.helper.SplitDetailAdapter
import com.example.kotlinmvppractice.utils.toastEXT

class BillDetailsActivity : Activity(), BillDetailsContract.View {

	private lateinit var presenter: BillDetailsPresenter
	private lateinit var btnBack: ImageView
	private lateinit var tvBillDetailName: TextView
	private lateinit var tvBillDetailGroup: TextView
	private lateinit var tvBillDetailAmount: TextView
	private lateinit var tvBillDetailDueDate: TextView
	private lateinit var listViewSplitDetails: ListView

	private lateinit var adapter: SplitDetailAdapter
	private val memberList = mutableListOf<SplitMember>()
	private var billName: String = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_bill_details)

		billName = intent.getStringExtra("BILL_NAME") ?: ""

		btnBack = findViewById(R.id.btnBack)
		tvBillDetailName = findViewById(R.id.tvBillDetailName)
		tvBillDetailGroup = findViewById(R.id.tvBillDetailGroup)
		tvBillDetailAmount = findViewById(R.id.tvBillDetailAmount)
		tvBillDetailDueDate = findViewById(R.id.tvBillDetailDueDate)
		listViewSplitDetails = findViewById(R.id.listViewSplitDetails)

		presenter = BillDetailsPresenter(this, BillDetailsModel(application as CustomApp), billName)

		adapter = SplitDetailAdapter(this, memberList) { pos -> presenter.onMarkPaid(pos) }
		listViewSplitDetails.adapter = adapter

		btnBack.setOnClickListener { finish() }

		presenter.loadBillDetails()
	}

	override fun displayBillInfo(bill: com.example.kotlinmvppractice.data.Bill) {
		tvBillDetailName.text = bill.name
		tvBillDetailGroup.text = bill.groupName
		tvBillDetailAmount.text = bill.amount
		tvBillDetailDueDate.text = bill.dueDate
	}

	override fun displaySplitMembers(members: MutableList<SplitMember>) {
		memberList.clear()
		memberList.addAll(members)
		adapter.notifyDataSetChanged()
	}

	override fun showMemberMarkedPaid(memberName: String) {
		toastEXT("$memberName marked as paid!")
	}

	override fun showBillFullyPaid() {
		toastEXT("All members paid! Bill fully settled.")
	}

	override fun refreshList() {
		adapter.notifyDataSetChanged()
	}
}