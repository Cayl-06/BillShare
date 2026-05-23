package com.example.kotlinmvppractice.helper

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.kotlinmvppractice.R
import com.example.kotlinmvppractice.data.Bill

// step 4: custom adapter which is a child of BaseAdapter
// step 4.1: parameters are context (like 'this') and the list of Bill data
// step 4.2: implement members used in BaseAdapter parent
class BillListViewAdapter(
    private val context: Context,
    private val billList: List<Bill>
) : BaseAdapter() {

    // size of the list
    override fun getCount(): Int = billList.size

    // specific item of the list using its position
    override fun getItem(position: Int): Any = billList[position]

    // get the id of the item by returning the position in long format
    override fun getItemId(position: Int): Long = position.toLong()

    // Important: get the specific view called every time an item needs to be displayed
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // 1. inflate the view with the bill item layout
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_bill, parent, false
        )

        // 2. find elements in the inflated view
        val tvBillName   = view.findViewById<TextView>(R.id.tvBillName)
        val tvBillDate   = view.findViewById<TextView>(R.id.tvBillDate)
        val tvBillAmount = view.findViewById<TextView>(R.id.tvBillAmount)
        val tvBillStatus = view.findViewById<TextView>(R.id.tvBillStatus)

        // 3. get data using position and bind to views
        val bill = billList[position]
        tvBillName.text   = bill.name
        // show group name and due date for clarity
        tvBillDate.text   = if (bill.groupName.isNotEmpty()) "${bill.groupName} • Due ${bill.dueDate}" else bill.dueDate
        tvBillAmount.text = bill.amount
        tvBillStatus.text = bill.status
        tvBillStatus.setTextColor(
            if (bill.status.equals("Paid", ignoreCase = true)) {
                Color.parseColor("#10B981")
            } else {
                Color.parseColor("#EF4444")
            }
        )

        return view
    }
}