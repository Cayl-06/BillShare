package com.example.kotlinmvppractice.screens.notifications

class NotificationsContract {
	interface View {
		fun displayNotifications(notifications: MutableList<NotificationItem>)
	}

	interface Presenter {
		fun loadNotifications()
	}
}