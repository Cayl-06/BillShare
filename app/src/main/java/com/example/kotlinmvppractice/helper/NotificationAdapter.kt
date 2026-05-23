package com.example.kotlinmvppractice.helper

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.screens.notifications.NotificationItem

class NotificationAdapter(private val context: Context, private val notificationList: List<NotificationItem>) : BaseAdapter() {
	override fun getCount() = notificationList.size
	override fun getItem(position: Int): Any = notificationList[position]
	override fun getItemId(position: Int) = position.toLong()
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false)
		val item = notificationList[position]
		view.findViewById<TextView>(R.id.tvNotificationTitle).text = item.title
		view.findViewById<TextView>(R.id.tvNotificationBody).text = item.body
		view.findViewById<TextView>(R.id.tvNotificationTime).text = item.time
		val icon = view.findViewById<ImageView>(R.id.icNotification)
		val color = when (item.iconType) {
			"bill" -> "#EF4444"
			"payment" -> "#10B981"
			"add" -> "#539CFF"
			else -> "#94A3B8"
		}
		icon.setColorFilter(Color.parseColor(color))
		return view
	}
}