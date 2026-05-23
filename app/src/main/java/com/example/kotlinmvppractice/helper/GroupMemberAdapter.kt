package com.example.kotlinmvppractice.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlinmvppractice.R

class GroupMemberAdapter(private val context: Context, private val memberList: List<String>) : BaseAdapter() {
	override fun getCount() = memberList.size
	override fun getItem(position: Int): Any = memberList[position]
	override fun getItemId(position: Int) = position.toLong()
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_group_member, parent, false)
		val name = memberList[position]
		val initial = if (name.isNotEmpty()) name[0].toString().uppercase() else ""
		view.findViewById<TextView>(R.id.tvMemberInitial).text = initial
		view.findViewById<TextView>(R.id.tvMemberName).text = name
		val btnRemove = view.findViewById<ImageView>(R.id.btnRemoveMember)
		btnRemove.visibility = View.VISIBLE
		return view
	}
}