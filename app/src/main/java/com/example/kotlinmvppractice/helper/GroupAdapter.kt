package com.example.kotlinmvppractice.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.data.Group

class GroupAdapter(private val context: Context, private val groupList: List<Group>) : BaseAdapter() {
	override fun getCount() = groupList.size
	override fun getItem(position: Int): Any = groupList[position]
	override fun getItemId(position: Int) = position.toLong()
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_group, parent, false)
		val group = groupList[position]
		view.findViewById<TextView>(R.id.tvGroupName).text = group.name
		view.findViewById<TextView>(R.id.tvMemberCount).text = "${group.members.size} members"
		return view
	}
}