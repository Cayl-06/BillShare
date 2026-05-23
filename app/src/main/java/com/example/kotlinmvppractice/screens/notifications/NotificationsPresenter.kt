package com.example.kotlinmvppractice.screens.notifications

class NotificationsPresenter(private val view: NotificationsContract.View, private val model: NotificationsModel) : NotificationsContract.Presenter {
	override fun loadNotifications() {
		view.displayNotifications(model.getNotifications())
	}
}