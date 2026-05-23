package com.example.kotlinmvppractice.screens.notifications

import android.app.Activity
import android.os.Bundle
import android.widget.ListView
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.helper.NotificationAdapter

class NotificationsActivity : Activity(), NotificationsContract.View {
	private lateinit var presenter: NotificationsPresenter
	private lateinit var listView: ListView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_notifications)

		listView = findViewById(R.id.listViewNotifications)

		presenter = NotificationsPresenter(this, NotificationsModel())
		presenter.loadNotifications()
	}

	override fun displayNotifications(notifications: MutableList<NotificationItem>) {
		listView.adapter = NotificationAdapter(this, notifications)
	}
}