package com.example.kotlinmvppractice.screens.addbill

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.EditText
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.utils.getEditTextValueEXT
import com.example.kotlinmvppractice.utils.toastEXT
import com.example.kotlinmvppractice.screens.splitcalculator.SplitCalculatorActivity

class AddBillActivity : Activity(), AddBillContract.View {
	private lateinit var presenter: AddBillPresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_bill)

		presenter = AddBillPresenter(this, AddBillModel(application as CustomApp))

		val btnBack = findViewById<ImageView>(R.id.btnBack)
		val etBillName = findViewById<EditText>(R.id.etBillName)
		val etTotalAmount = findViewById<EditText>(R.id.etTotalAmount)
		val etDueDate = findViewById<EditText>(R.id.etDueDate)
		val tvSelectedGroup = findViewById<TextView>(R.id.tvSelectedGroup)
		val btnCalculateSplit = findViewById<Button>(R.id.btnCalculateSplit)

		val groups = (application as CustomApp).groups
		tvSelectedGroup.text = if (groups.isNotEmpty()) groups[0].name else "No Group"

		btnBack.setOnClickListener { finish() }

		btnCalculateSplit.setOnClickListener {
			presenter.onCalculateSplit(
				getEditTextValueEXT(R.id.etBillName),
				getEditTextValueEXT(R.id.etTotalAmount),
				getEditTextValueEXT(R.id.etDueDate),
				tvSelectedGroup.text.toString()
			)
		}
	}

	override fun showEmpty() = toastEXT("Please fill all bill fields")

	override fun showSuccess() = toastEXT("Bill saved")

	override fun showNoGroupSelected() = toastEXT("No group selected")

	override fun navigateToSplitCalculator(bill: com.example.kotlinmvppractice.data.Bill, groupName: String) {
		val intent = Intent(this, SplitCalculatorActivity::class.java)
		intent.putExtra("BILL_NAME", bill.name)
		intent.putExtra("BILL_AMOUNT", bill.amount)
		intent.putExtra("GROUP_NAME", groupName)
		startActivity(intent)
	}
}