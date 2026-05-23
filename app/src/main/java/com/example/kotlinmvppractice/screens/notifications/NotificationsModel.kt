package com.example.kotlinmvppractice.screens.notifications

data class NotificationItem(var title: String, var body: String, var time: String, var iconType: String = "bill")

class NotificationsModel {
	fun getNotifications(): MutableList<NotificationItem> {
		return mutableListOf(
			NotificationItem("Upcoming Due Date", "Electricity bill is due tomorrow.", "2 hours ago", "bill"),
			NotificationItem("Payment Received", "Alex paid their share for Wi-Fi.", "Yesterday", "payment"),
			NotificationItem("New Bill Added", "Sam added 'Groceries' in Dorm Room A.", "Yesterday", "add")
		)
	}
}