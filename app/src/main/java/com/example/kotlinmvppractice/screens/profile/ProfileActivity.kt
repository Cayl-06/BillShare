package com.example.kotlinmvppractice.screens.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.screens.allbills.AllBillsActivity
import com.example.kotlinmvppractice.screens.groups.GroupsActivity
import com.example.kotlinmvppractice.screens.notifications.NotificationsActivity
import com.example.kotlinmvppractice.screens.welcome.WelcomeActivity
import com.example.kotlinmvppractice.utils.toastEXT

class ProfileActivity : Activity(), ProfileContract.View {

	private lateinit var presenter: ProfilePresenter

	private lateinit var tvProfileInitial: TextView
	private lateinit var tvProfileName: TextView
	private lateinit var tvProfileEmail: TextView
	private lateinit var tvStatGroups: TextView
	private lateinit var tvStatBills: TextView
	private lateinit var tvStatUnpaid: TextView

	private lateinit var menuMyBills: LinearLayout
	private lateinit var menuMyGroups: LinearLayout
	private lateinit var menuNotifications: LinearLayout
	private lateinit var menuChangePassword: LinearLayout
	private lateinit var menuLogout: LinearLayout

	private lateinit var navHome: LinearLayout
	private lateinit var navGroups: LinearLayout
	private lateinit var navBills: LinearLayout
	private lateinit var navProfile: LinearLayout

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_profile)

		presenter = ProfilePresenter(this, ProfileModel(application as CustomApp))

		tvProfileInitial = findViewById(R.id.tvProfileInitial)
		tvProfileName = findViewById(R.id.tvProfileName)
		tvProfileEmail = findViewById(R.id.tvProfileEmail)
		tvStatGroups = findViewById(R.id.tvStatGroups)
		tvStatBills = findViewById(R.id.tvStatBills)
		tvStatUnpaid = findViewById(R.id.tvStatUnpaid)

		menuMyBills = findViewById(R.id.menuMyBills)
		menuMyGroups = findViewById(R.id.menuMyGroups)
		menuNotifications = findViewById(R.id.menuNotifications)
		menuChangePassword = findViewById(R.id.menuChangePassword)
		menuLogout = findViewById(R.id.menuLogout)

		navHome = findViewById(R.id.navHome)
		navGroups = findViewById(R.id.navGroups)
		navBills = findViewById(R.id.navBills)
		navProfile = findViewById(R.id.navProfile)

		menuMyBills.setOnClickListener { startActivity(Intent(this, AllBillsActivity::class.java)) }
		menuMyGroups.setOnClickListener { startActivity(Intent(this, GroupsActivity::class.java)) }
		menuNotifications.setOnClickListener { startActivity(Intent(this, NotificationsActivity::class.java)) }
		menuChangePassword.setOnClickListener { toastEXT("Coming soon!") }
		menuLogout.setOnClickListener { presenter.onLogoutClicked() }

		navHome.setOnClickListener { startActivity(Intent(this, com.example.kotlinmvppractice.screens.dashboard.DashboardActivity::class.java)); finish() }
		navGroups.setOnClickListener { startActivity(Intent(this, GroupsActivity::class.java)); finish() }
		navBills.setOnClickListener { startActivity(Intent(this, AllBillsActivity::class.java)); finish() }

		presenter.loadProfile()
	}

	override fun onResume() {
		super.onResume()
		presenter.loadProfile()
	}

	override fun displayProfile(name: String, initial: String) {
		tvProfileInitial.text = initial
		tvProfileName.text = name
		tvProfileEmail.text = name.lowercase().replace(" ", ".") + "@email.com"
	}

	override fun displayStats(groupCount: Int, billCount: Int, unpaidCount: Int) {
		tvStatGroups.text = groupCount.toString()
		tvStatBills.text = billCount.toString()
		tvStatUnpaid.text = unpaidCount.toString()
	}

	override fun showLogoutConfirmDialog() {
		AlertDialog.Builder(this)
			.setTitle("Logout")
			.setMessage("Are you sure you want to logout?")
			.setPositiveButton("Logout") { _, _ -> presenter.onLogoutConfirmed() }
			.setNegativeButton("Cancel", null)
			.show()
	}

	override fun navigateToWelcome() {
		val intent = Intent(this, WelcomeActivity::class.java)
		intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		startActivity(intent)
	}
}