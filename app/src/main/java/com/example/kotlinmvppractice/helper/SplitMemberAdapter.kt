package com.example.kotlinmvppractice.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.screens.splitcalculator.SplitMember

class SplitMemberAdapter(private val context: Context, private val memberList: List<SplitMember>) : BaseAdapter() {
	override fun getCount() = memberList.size
	override fun getItem(position: Int): Any = memberList[position]
	override fun getItemId(position: Int) = position.toLong()
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_split_member, parent, false)
		val member = memberList[position]
		val initial = if (member.name.isNotEmpty()) member.name[0].toString().uppercase() else ""
		view.findViewById<TextView>(R.id.tvMemberInitial).text = initial
		view.findViewById<TextView>(R.id.tvMemberName).text = member.name
		val et = view.findViewById<EditText>(R.id.etMemberAmount)
		et.setText(member.amount)
		et.isEnabled = true
		return view
	}
}