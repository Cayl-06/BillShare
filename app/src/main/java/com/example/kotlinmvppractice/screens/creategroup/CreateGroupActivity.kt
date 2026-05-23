package com.example.kotlinmvppractice.screens.creategroup

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.app.CustomApp
import com.example.kotlinmvppractice.helper.GroupMemberAdapter
import com.example.kotlinmvppractice.utils.getEditTextValueEXT
import com.example.kotlinmvppractice.utils.toastEXT

class CreateGroupActivity : Activity(), CreateGroupContract.View {

    private lateinit var presenter: CreateGroupPresenter
    private lateinit var btnBack: ImageView
    private lateinit var etGroupName: EditText
    private lateinit var etMemberEmail: EditText
    private lateinit var btnAddMember: ImageView
    private lateinit var listViewGroupMembers: ListView
    private lateinit var btnCreateGroup: Button

    private lateinit var adapter: GroupMemberAdapter
    private val memberList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        btnBack = findViewById(R.id.btnBack)
        etGroupName = findViewById(R.id.etGroupName)
        etMemberEmail = findViewById(R.id.etMemberEmail)
        btnAddMember = findViewById(R.id.btnAddMember)
        listViewGroupMembers = findViewById(R.id.listViewGroupMembers)
        btnCreateGroup = findViewById(R.id.btnCreateGroup)

        presenter = CreateGroupPresenter(this, CreateGroupModel(application as CustomApp))

        adapter = GroupMemberAdapter(this, memberList)
        listViewGroupMembers.adapter = adapter

        // Setup the initial list containing at least the current user
        presenter.removeMember(-1) // Triggers a list update safely

        btnBack.setOnClickListener { finish() }

        btnAddMember.setOnClickListener {
            val email = getEditTextValueEXT(R.id.etMemberEmail)
            presenter.onAddMember(email)
        }

        btnCreateGroup.setOnClickListener {
            val groupName = getEditTextValueEXT(R.id.etGroupName)
            presenter.onCreateGroup(groupName)
        }

        listViewGroupMembers.setOnItemLongClickListener { _, _, position, _ ->
            showRemoveDialog(position)
            true
        }
    }

    private fun showRemoveDialog(position: Int) {
        val member = memberList[position]
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Remove Member")
        builder.setMessage("Are you sure you want to remove $member?")
        builder.setPositiveButton("Remove") { _, _ ->
            presenter.removeMember(position)
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    override fun showMemberAdded(member: String) {
        etMemberEmail.text.clear()
        toastEXT("$member added to group")
    }

    override fun showGroupCreated() {
        toastEXT("Group created!")
        finish()
    }

    override fun showEmptyGroupName() {
        toastEXT("Please enter a group name")
    }

    override fun showEmptyMemberField() {
        toastEXT("Please enter an email or username")
    }

    override fun showMemberAlreadyAdded() {
        toastEXT("Member is already in the list")
    }

    override fun updateMemberList(members: MutableList<String>) {
        memberList.clear()
        memberList.addAll(members)
        adapter.notifyDataSetChanged()
    }
}