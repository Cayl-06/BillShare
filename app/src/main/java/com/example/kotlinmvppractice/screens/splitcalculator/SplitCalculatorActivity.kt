package com.example.kotlinmvppractice.screens.splitcalculator

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.helper.SplitMemberAdapter
import com.example.kotlinmvppractice.utils.toastEXT

class SplitCalculatorActivity : Activity(), SplitCalculatorContract.View {

    private lateinit var presenter: SplitCalculatorPresenter
    private lateinit var tvTotalBill: TextView
    private lateinit var tabEqualSplit: TextView
    private lateinit var tabCustomSplit: TextView
    private lateinit var listViewMembers: ListView
    private lateinit var btnConfirmSave: Button
    private lateinit var btnBack: ImageView

    private lateinit var adapter: SplitMemberAdapter
    private var memberList = mutableListOf<SplitMember>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_split_calculator)

        tvTotalBill = findViewById(R.id.tvTotalBill)
        tabEqualSplit = findViewById(R.id.tabEqualSplit)
        tabCustomSplit = findViewById(R.id.tabCustomSplit)
        listViewMembers = findViewById(R.id.listViewMembers)
        btnConfirmSave = findViewById(R.id.btnConfirmSave)
        btnBack = findViewById(R.id.btnBack)

        presenter = SplitCalculatorPresenter(this, SplitCalculatorModel(application as CustomApp))

        adapter = SplitMemberAdapter(this, memberList)
        listViewMembers.adapter = adapter

        val billName = intent.getStringExtra("BILL_NAME") ?: ""
        val billAmount = intent.getStringExtra("BILL_AMOUNT") ?: "0.00"
        val groupName = intent.getStringExtra("GROUP_NAME") ?: ""

        val app = application as CustomApp
        val group = app.groups.find { it.name == groupName }
        val members = group?.members?.toList() ?: listOf((application as CustomApp).username)

        presenter.loadData(billAmount, members)

        tabEqualSplit.setOnClickListener { presenter.onEqualSplitTab() }
        tabCustomSplit.setOnClickListener { presenter.onCustomSplitTab() }

        btnConfirmSave.setOnClickListener { presenter.onConfirmSave() }
        btnBack.setOnClickListener { finish() }
    }

    override fun displayTotalBill(amount: String) {
        tvTotalBill.text = amount
    }

    override fun displayMembers(members: MutableList<SplitMember>) {
        memberList.clear()
        memberList.addAll(members)
        adapter.notifyDataSetChanged()
    }

    override fun showSavedSuccess() {
        toastEXT("Split saved!")
        finish()
    }

    override fun showTabSelected(isEqualSplit: Boolean) {
        if (isEqualSplit) {
            tabEqualSplit.setBackgroundResource(R.drawable.bg_button_blue)
            tabEqualSplit.setTextColor(Color.WHITE)
            tabCustomSplit.setBackgroundColor(Color.TRANSPARENT)
            tabCustomSplit.setTextColor(Color.parseColor("#94A3B8"))
        } else {
            tabCustomSplit.setBackgroundResource(R.drawable.bg_button_blue)
            tabCustomSplit.setTextColor(Color.WHITE)
            tabEqualSplit.setBackgroundColor(Color.TRANSPARENT)
            tabEqualSplit.setTextColor(Color.parseColor("#94A3B8"))
        }
    }
}