package com.example.kotlinmvppractice.screens.groupdetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.utils.dpToPx
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.data.Group
import com.example.kotlinmvppractice.helper.BillListViewAdapter
import com.example.kotlinmvppractice.screens.addbill.AddBillActivity

class GroupDetailsActivity : Activity(), GroupDetailsContract.View {

	private lateinit var presenter: GroupDetailsPresenter
	private lateinit var btnBack: ImageView
	private lateinit var tvGroupDetailName: TextView
	private lateinit var layoutMemberAvatars: LinearLayout
	private lateinit var btnAddBillToGroup: LinearLayout
	private lateinit var listViewGroupBills: ListView

	private lateinit var adapter: BillListViewAdapter
	private val billList = mutableListOf<com.example.kotlinmvppractice.data.Bill>()
	private var groupName: String = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_group_details)

		groupName = intent.getStringExtra("GROUP_NAME") ?: ""

		btnBack = findViewById(R.id.btnBack)
		tvGroupDetailName = findViewById(R.id.tvGroupDetailName)
		layoutMemberAvatars = findViewById(R.id.layoutMemberAvatars)
		btnAddBillToGroup = findViewById(R.id.tvAddBillToGroup)
		listViewGroupBills = findViewById(R.id.listViewGroupBills)

		presenter = GroupDetailsPresenter(this, GroupDetailsModel(application as CustomApp, groupName), groupName)

		adapter = BillListViewAdapter(this, billList)
		listViewGroupBills.adapter = adapter

		btnBack.setOnClickListener { finish() }
		btnAddBillToGroup.setOnClickListener { presenter.onAddBillClicked() }

		listViewGroupBills.setOnItemClickListener { _, _, position, _ ->
			val bill = billList[position]
			val intent = Intent(this, com.example.kotlinmvppractice.screens.billdetails.BillDetailsActivity::class.java)
			intent.putExtra("BILL_NAME", bill.name)
			startActivity(intent)
		}
	}

	override fun onResume() {
		super.onResume()
		presenter.loadGroupDetails()
	}

	override fun displayGroupInfo(group: Group) {
		tvGroupDetailName.text = group.name
	}

	override fun displayBills(bills: MutableList<com.example.kotlinmvppractice.data.Bill>) {
		billList.clear()
		billList.addAll(bills)
		adapter.notifyDataSetChanged()
	}

	override fun displayMemberAvatars(members: List<String>) {
		layoutMemberAvatars.removeAllViews()
		for (member in members) {
			val memberView = LinearLayout(this)
			memberView.orientation = LinearLayout.VERTICAL
			memberView.gravity = Gravity.CENTER
			val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
			params.marginEnd = 12.dpToPx()
			memberView.layoutParams = params

			val avatar = TextView(this)
			avatar.text = member.firstOrNull()?.uppercase() ?: ""
			avatar.setTextColor(0xFF539CFF.toInt())
			avatar.textSize = 16f
			avatar.typeface = android.graphics.Typeface.DEFAULT_BOLD
			avatar.gravity = Gravity.CENTER
			val avatarSize = 48.dpToPx()
			avatar.layoutParams = LinearLayout.LayoutParams(avatarSize, avatarSize)
			avatar.setBackgroundResource(R.drawable.bg_circle_icon)

			val nameLabel = TextView(this)
			nameLabel.text = member.split(" ").first()
			nameLabel.setTextColor(0xFFCCE4FF.toInt())
			nameLabel.textSize = 11f
			nameLabel.gravity = Gravity.CENTER
			nameLabel.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { topMargin = 4.dpToPx() }

			memberView.addView(avatar)
			memberView.addView(nameLabel)
			layoutMemberAvatars.addView(memberView, layoutMemberAvatars.childCount - 1)
		}
	}

	override fun navigateToAddBill(groupName: String) {
		val intent = Intent(this, AddBillActivity::class.java)
		intent.putExtra("PRESET_GROUP", groupName)
		startActivity(intent)
	}
}