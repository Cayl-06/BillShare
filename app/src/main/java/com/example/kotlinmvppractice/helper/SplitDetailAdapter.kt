package com.example.kotlinmvppractice.helper

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.data.SplitMember

class SplitDetailAdapter(
	private val context: Context,
	private val memberList: List<SplitMember>,
	private val onMarkPaid: (Int) -> Unit
) : BaseAdapter() {
	override fun getCount() = memberList.size
	override fun getItem(position: Int): Any = memberList[position]
	override fun getItemId(position: Int) = position.toLong()
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_split_detail, parent, false)
		val member = memberList[position]
		val initial = if (member.name.isNotEmpty()) member.name[0].toString().uppercase() else ""
		view.findViewById<TextView>(R.id.tvSplitMemberInitial).text = initial
		view.findViewById<TextView>(R.id.tvSplitMemberName).text = member.name
		view.findViewById<TextView>(R.id.tvSplitMemberAmount).text = member.amount
		val btn = view.findViewById<TextView>(R.id.btnMarkPaid)
		if (member.isPaid) {
			btn.text = "✓ Paid"
			btn.setTextColor(0xFF10B981.toInt())
			btn.isEnabled = false
		} else {
			btn.text = "Mark Paid"
			btn.setTextColor(0xFF539CFF.toInt())
			btn.isEnabled = true
			btn.setOnClickListener { onMarkPaid(position) }
		}
		return view
	}
}