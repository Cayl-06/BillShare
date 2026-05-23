# BillShare — Phase 2 Implementation Prompt
# Fix existing bugs + implement all remaining screens

Paste this entire prompt to an AI to generate all the code.

---

## YOUR ROLE

You are completing a Kotlin Android MVP app called **BillShare**. I will give you the full current codebase and a list of bugs + missing screens. Generate complete, compilable Kotlin code for every file listed. Follow the existing patterns exactly.

---

## PROJECT RULES (NEVER BREAK THESE)

1. Every Activity extends `Activity()` — NOT `AppCompatActivity`
2. Use `toastEXT("message")` for all toasts — from `utils/extension.kt`
3. Use `getEditTextValueEXT(R.id.viewId)` to read EditText values
4. Every Presenter: `constructor(view: XxxContract.View, model: XxxModel)`
5. Every Model: `constructor(private val app: CustomApp)` unless no app data needed
6. Navigation: `startActivity(Intent(this, XxxActivity::class.java))`
7. Pass data between screens: `intent.putExtra("KEY", value)` / `intent.getStringExtra("KEY")`
8. Keep all data/logic in Model, never in Activity or Presenter directly
9. Package: `com.example.kotlinmvppractice`

---

## UTILITIES AVAILABLE (already implemented)

```kotlin
// utils/extension.kt
fun Activity.getEditTextValueEXT(id: Int): String
fun Activity.toastEXT(message: String)
```

---

## CURRENT DATA CLASSES

### data/User.kt
```kotlin
package com.example.kotlinmvppractice.data
data class User(var username: String = "", var password: String = "")
```

### data/Bill.kt — NEEDS MODIFICATION
Current:
```kotlin
data class Bill(
    var name: String = "",
    var dueDate: String = "",
    var amount: String = "",
    var status: String = "Unpaid"
)
```
**UPDATE to:**
```kotlin
package com.example.kotlinmvppractice.data

data class Bill(
    var name: String = "",
    var dueDate: String = "",
    var amount: String = "",
    var status: String = "Unpaid",
    var groupName: String = "",
    var splitMembers: MutableList<SplitMember> = mutableListOf()
)
```

### data/Group.kt — ALREADY CREATED
```kotlin
package com.example.kotlinmvppractice.data
data class Group(var name: String = "", var members: MutableList<String> = mutableListOf())
```

### data/SplitMember.kt — NEW FILE
```kotlin
package com.example.kotlinmvppractice.data

data class SplitMember(
    var name: String = "",
    var amount: String = "",
    var isPaid: Boolean = false
)
```

---

## CURRENT CustomApp.kt — NEEDS MODIFICATION

Current:
```kotlin
class CustomApp: Application() {
    val username = "Cayl Redublado"
    val password = "1234"
    var loginUser = User()
    override fun onCreate() {
        super.onCreate()
        Log.e("Custom App", "onCreate is called")
    }
}
```

**UPDATE to add shared state lists:**
```kotlin
package com.example.kotlinmvppractice.app

import android.app.Application
import android.util.Log
import com.example.kotlinmvppractice.data.Bill
import com.example.kotlinmvppractice.data.Group
import com.example.kotlinmvppractice.data.SplitMember
import com.example.kotlinmvppractice.data.User

class CustomApp : Application() {
    val username = "Cayl Redublado"
    val password = "1234"

    var loginUser = User()
    var registeredUsers = mutableListOf<User>()
    var bills = mutableListOf<Bill>()        // shared bill list across the whole app
    var groups = mutableListOf<Group>()      // shared group list across the whole app

    override fun onCreate() {
        super.onCreate()
        Log.e("Custom App", "onCreate is called")

        // Seed dummy groups so the app has data immediately
        groups.add(Group("Apartment 3B", mutableListOf("Alex Doe", "Jordan Smith", "Casey Lee")))
        groups.add(Group("Dorm Room A", mutableListOf("Sam", "You")))

        // Seed dummy bills with splitMembers so BillDetails works immediately
        val electricityMembers = mutableListOf(
            SplitMember("Alex Doe", "$40.00", false),
            SplitMember("Jordan Smith", "$40.00", false),
            SplitMember("Casey Lee", "$40.00", false)
        )
        val wifiMembers = mutableListOf(
            SplitMember("Alex Doe", "$30.00", false),
            SplitMember("Jordan Smith", "$30.00", false)
        )
        bills.add(Bill("Electricity", "3/15/2026", "$120.00", "Unpaid", "Apartment 3B", electricityMembers))
        bills.add(Bill("Wi-Fi",       "3/20/2026", "$60.00",  "Unpaid", "Apartment 3B", wifiMembers))
    }
}
```

---

## EXISTING ADAPTER (reference pattern — follow exactly)

### helper/BillListViewAdapter.kt — DO NOT CHANGE
```kotlin
class BillListViewAdapter(
    private val context: Context,
    private val billList: List<Bill>
) : BaseAdapter() {
    override fun getCount() = billList.size
    override fun getItem(position: Int): Any = billList[position]
    override fun getItemId(position: Int) = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false)
        val bill = billList[position]
        view.findViewById<TextView>(R.id.tvBillName).text   = bill.name
        view.findViewById<TextView>(R.id.tvBillDate).text   = bill.dueDate
        view.findViewById<TextView>(R.id.tvBillAmount).text = bill.amount
        view.findViewById<TextView>(R.id.tvBillStatus).text = bill.status
        return view
    }
}
```

---

## XML LAYOUT IDs REFERENCE

### activity_dashboard.xml
- `tvDashboardUsername` (TextView)
- `listViewBills` (ListView) — upcoming bills
- `btnAddBill` (LinearLayout)
- `btnCreateGroup` (LinearLayout)
- Bottom nav: no IDs on nav items in dashboard (no changes needed there)
- Bell icon: `@android:drawable/ic_popup_reminder` in header (add id `ivNotificationBell`)

### activity_all_bills.xml
- `listViewAllBills` (ListView)
- `navHome`, `navGroups`, `navBills`, `navProfile` (LinearLayout)

### activity_add_bill.xml
- `btnBack` (ImageView)
- `etBillName` (EditText)
- `etTotalAmount` (EditText)
- `etDueDate` (EditText)
- `tvSelectedGroup` (TextView) — tappable, shows selected group name
- `btnCalculateSplit` (Button)

### activity_split_calculator.xml
- `btnBack` (ImageView)
- `tvTotalBill` (TextView)
- `tabEqualSplit`, `tabCustomSplit` (TextView)
- `listViewMembers` (ListView)
- `btnConfirmSave` (Button)

### activity_groups.xml
- `listViewGroups` (ListView)
- `navHome`, `navGroups`, `navBills`, `navProfile` (LinearLayout)

### activity_group_details.xml
- `btnBack` (ImageView)
- `tvGroupDetailName` (TextView)
- `btnGroupSettings` (ImageView)
- `layoutMemberAvatars` (LinearLayout) — add member avatars here programmatically
- `btnAddMemberToGroup` (LinearLayout)
- `btnAddBillToGroup` (TextView)
- `listViewGroupBills` (ListView)

### activity_notifications.xml
- `listViewNotifications` (ListView)

### activity_bill_details.xml
- `btnBack` (ImageView)
- `tvBillDetailName` (TextView)
- `tvBillDetailGroup` (TextView)
- `tvBillDetailAmount` (TextView)
- `tvBillDetailDueDate` (TextView)
- `listViewSplitDetails` (ListView)

### activity_profile.xml
- `tvProfileInitial` (TextView — big avatar letter)
- `tvProfileName` (TextView)
- `tvProfileEmail` (TextView)
- `tvStatGroups` (TextView)
- `tvStatBills` (TextView)
- `tvStatUnpaid` (TextView)
- `menuMyBills` (RelativeLayout)
- `menuMyGroups` (RelativeLayout)
- `menuNotifications` (RelativeLayout)
- `menuChangePassword` (RelativeLayout)
- `menuLogout` (RelativeLayout)
- `navHome`, `navGroups`, `navBills`, `navProfile` (LinearLayout)

### item_split_detail.xml
- `tvSplitMemberInitial` (TextView)
- `tvSplitMemberName` (TextView)
- `tvSplitMemberAmount` (TextView)
- `btnMarkPaid` (TextView — acts as button, shows "Mark Paid" or "✓ Paid")

### item_bill.xml (existing — no change needed)
- `tvBillName`, `tvBillDate`, `tvBillAmount`, `tvBillStatus`

### item_group.xml (existing)
- `tvGroupName`, `tvMemberCount`

### item_group_member.xml (existing)
- `tvMemberInitial`, `tvMemberName`, `btnRemoveMember`

### item_notification.xml (existing)
- `icNotification`, `tvNotificationTitle`, `tvNotificationBody`, `tvNotificationTime`

### item_split_member.xml (existing)
- `tvMemberInitial`, `tvMemberName`, `etMemberAmount`

---

## EXISTING WORKING SCREENS (DO NOT MODIFY)

- `welcome/` — complete, working
- `login/` — complete, working

---

## CURRENT BROKEN DASHBOARD — needs fixes

### Current DashboardActivity.kt problems:
1. `btnAddBill` just adds a hardcoded bill — must navigate to `AddBillActivity`
2. `btnCreateGroup` shows toast — must navigate to `CreateGroupActivity`
3. `listViewBills` item click shows toast — must navigate to `BillDetailsActivity` passing `BILL_NAME`
4. `loadBills()` loads from hardcoded Model list — must load from `app.bills`
5. Bell icon is not clickable — must navigate to `NotificationsActivity`
6. Dashboard has no bottom nav clicks wired — navGroups → `GroupsActivity`, navBills → `AllBillsActivity`, navProfile → `ProfileActivity`

### Current DashboardModel.kt problems:
- `getBills()` returns hardcoded list — must return `app.bills`
- Remove `getNewBill()` — no longer needed

---

## WHAT TO GENERATE — complete code for every file below

---

### 1. data/SplitMember.kt (NEW)
As shown above in data classes section.

### 2. data/Bill.kt (MODIFY)
Add `groupName` and `splitMembers` fields as shown above.

### 3. app/CustomApp.kt (MODIFY)
Add `registeredUsers`, `bills`, `groups` + seed data as shown above.

---

### 4. screens/dashboard/ — FIX ALL 4 FILES

**DashboardContract.kt** — keep same interface, no changes needed

**DashboardModel.kt** — REWRITE:
- `fun getUsername(): String` → `app.loginUser.username`
- `fun getBills(): MutableList<Bill>` → `return app.bills` (NOT hardcoded)
- Remove `getNewBill()`

**DashboardPresenter.kt** — REWRITE:
- Remove `addBill()` and `removeBill()` from interface and implementation
- Keep `initializeUsername()` and `loadBills()`
- `loadBills()` → calls `model.getBills()` → `view.displayBills(bills)`

**DashboardContract.kt** — REWRITE:
- View: `displayWelcomeUser(message: String)`, `displayBills(bills: MutableList<Bill>)`
- Presenter: `initializeUsername()`, `loadBills()`

**DashboardActivity.kt** — REWRITE completely:
- `setContentView(R.layout.activity_dashboard)`
- Bind: `tvDashboardUsername`, `listViewBills`, `btnAddBill`, `btnCreateGroup`
- Also bind the bell ImageView in header — find it by adding `android:id="@+id/ivNotificationBell"` note in comment
- Use `BillListViewAdapter`
- On `onResume()` call `dashboardPresenter.loadBills()` — so list refreshes when returning from AddBill
- `btnAddBill` → `startActivity(Intent(this, AddBillActivity::class.java))`
- `btnCreateGroup` → `startActivity(Intent(this, CreateGroupActivity::class.java))`
- `listViewBills` item click → navigate to `BillDetailsActivity` with `intent.putExtra("BILL_NAME", bill.name)`
- `listViewBills` long click → show remove AlertDialog (keep this, but now removes from `app.bills`)
- Bell icon click → `startActivity(Intent(this, NotificationsActivity::class.java))`
- Bottom nav: find `navGroups` LinearLayout by id → `GroupsActivity`, find `navBills` → `AllBillsActivity`, `navProfile` → `ProfileActivity`
- **IMPORTANT:** Override `onResume()` and call `dashboardPresenter.loadBills()` inside it so the list always refreshes

---

### 5. screens/register/ — ALL 4 FILES

**RegisterContract.kt:**
```
interface View:
  showEmpty()
  showPasswordMismatch()
  showSuccess()
  navigateToLogin()

interface Presenter:
  onRegister(name: String, email: String, password: String, confirmPassword: String)
```

**RegisterModel.kt:**
- `fun register(name: String, email: String, password: String)` → adds `User(name, password)` to `app.registeredUsers`, sets `app.loginUser = User(name, password)`

**RegisterPresenter.kt:**
- Validate: all fields not empty → else `view.showEmpty()`
- Validate: password == confirmPassword → else `view.showPasswordMismatch()`
- Call `model.register()` → `view.showSuccess()` → `view.navigateToLogin()`

**RegisterActivity.kt:**
- `setContentView(R.layout.activity_register)`
- Bind: `etFullName` (add this id to register XML note), `etRegisterEmail`, `etRegisterPassword`, `etConfirmPassword`, `btnRegister`, `tvBackToLogin`
- **NOTE:** The register XML EditText fields need these IDs assigned:
  - Full Name field → `android:id="@+id/etFullName"`
  - Email field → `android:id="@+id/etRegisterEmail"`
  - Password field → `android:id="@+id/etRegisterPassword"`
  - Confirm Password field → `android:id="@+id/etConfirmPassword"`
- `btnRegister` → calls presenter with `getEditTextValueEXT()`
- `tvBackToLogin` → `finish()`
- `showSuccess()` → `toastEXT("Account created!")` + `startActivity(Intent(this, LoginActivity::class.java))` + `finish()`
- `showPasswordMismatch()` → `toastEXT("Passwords do not match!")`
- `showEmpty()` → `toastEXT("Please fill in all fields!")`
- `navigateToLogin()` → `startActivity(Intent(this, LoginActivity::class.java))` + `finish()`

Also update **LoginActivity.kt** — add click listener on `tvCreateAccount`:
```kotlin
findViewById<TextView>(R.id.tvCreateAccount).setOnClickListener {
    startActivity(Intent(this, RegisterActivity::class.java))
}
```

---

### 6. screens/allbills/ — ALL 4 FILES

**AllBillsContract.kt:**
```
interface View: displayBills(bills: MutableList<Bill>)
interface Presenter: loadBills()
```

**AllBillsModel.kt:**
- `fun getBills(): MutableList<Bill>` → `return app.bills`

**AllBillsPresenter.kt:**
- `loadBills()` → `view.displayBills(model.getBills())`

**AllBillsActivity.kt:**
- `setContentView(R.layout.activity_all_bills)`
- Bind: `listViewAllBills`, `navHome`, `navGroups`, `navBills`, `navProfile`
- Use `BillListViewAdapter`
- Override `onResume()` → call `presenter.loadBills()` (so list refreshes)
- Item click → navigate to `BillDetailsActivity` with `intent.putExtra("BILL_NAME", bill.name)`
- Bottom nav: `navHome` → `DashboardActivity` + `finish()`, `navGroups` → `GroupsActivity` + `finish()`, `navProfile` → `ProfileActivity` + `finish()`

**ALSO UPDATE BillListViewAdapter.kt** — add group name display in `tvBillDate` field:
Change: `view.findViewById<TextView>(R.id.tvBillDate).text = bill.dueDate`
To: `view.findViewById<TextView>(R.id.tvBillDate).text = "${bill.groupName} • Due ${bill.dueDate}"`
This shows both group and due date for clarity as requested.

---

### 7. screens/addbill/ — ALL 4 FILES

**AddBillContract.kt:**
```
interface View:
  showEmpty()
  showNoGroupSelected()
  navigateToSplitCalculator(billName: String, amount: String, dueDate: String, groupName: String)
  showGroupSelectionDialog(groupNames: List<String>)
  updateSelectedGroup(groupName: String)

interface Presenter:
  loadGroups()
  onGroupSelectorClicked()
  onCalculateSplit(name: String, amount: String, dueDate: String, groupName: String)
  onGroupSelected(groupName: String)
```

**AddBillModel.kt:**
- `fun getGroups(): MutableList<Group>` → `return app.groups`
- `fun getGroupNames(): List<String>` → `return app.groups.map { it.name }`
- `fun hasGroups(): Boolean` → `return app.groups.isNotEmpty()`

**AddBillPresenter.kt:**
- Holds `var selectedGroupName: String = ""`
- `loadGroups()` → if groups exist, `selectedGroupName = model.getGroupNames().first()`, `view.updateSelectedGroup(selectedGroupName)` else `view.updateSelectedGroup("No Group Available")`
- `onGroupSelectorClicked()` → `view.showGroupSelectionDialog(model.getGroupNames())`
- `onGroupSelected(groupName)` → `selectedGroupName = groupName`, `view.updateSelectedGroup(groupName)`
- `onCalculateSplit(name, amount, dueDate, groupName)`:
  - if any field empty → `view.showEmpty()`
  - if groupName empty or "No Group Available" → `view.showNoGroupSelected()`
  - else → `view.navigateToSplitCalculator(name, amount, dueDate, groupName)`

**AddBillActivity.kt:**
- `setContentView(R.layout.activity_add_bill)`
- Bind: `btnBack`, `etBillName`, `etTotalAmount`, `etDueDate`, `tvSelectedGroup`, `btnCalculateSplit`
- `btnBack` → `finish()`
- `onCreate` → call `presenter.loadGroups()`
- `tvSelectedGroup` click → `presenter.onGroupSelectorClicked()`
- `btnCalculateSplit` click → call `presenter.onCalculateSplit(...)` with `getEditTextValueEXT()` for fields and `tvSelectedGroup.text.toString()` for group
- `showGroupSelectionDialog(groupNames)` → use `AlertDialog.Builder` with `setItems(groupNames.toTypedArray())` → on item selected call `presenter.onGroupSelected(groupName)`
- `updateSelectedGroup(name)` → `tvSelectedGroup.text = name`
- `navigateToSplitCalculator(billName, amount, dueDate, groupName)`:
  ```kotlin
  val intent = Intent(this, SplitCalculatorActivity::class.java)
  intent.putExtra("BILL_NAME", billName)
  intent.putExtra("BILL_AMOUNT", amount)
  intent.putExtra("BILL_DUE_DATE", dueDate)
  intent.putExtra("GROUP_NAME", groupName)
  startActivity(intent)
  ```
- `showEmpty()` → `toastEXT("Please fill in all fields!")`
- `showNoGroupSelected()` → `toastEXT("Please select a group first!")`

---

### 8. screens/splitcalculator/ — ALL 4 FILES

**SplitCalculatorContract.kt:**
```
interface View:
  displayTotalBill(amount: String)
  displayMembers(members: MutableList<SplitMember>)
  showSavedSuccess()
  updateTabUI(isEqualSplit: Boolean)

interface Presenter:
  loadData()
  onEqualSplitTab()
  onCustomSplitTab()
  onConfirmSave()
```

**SplitCalculatorModel.kt:**
- Receives `CustomApp`
- `fun calculateEqualSplit(totalAmount: Double, memberCount: Int): Double` → `totalAmount / memberCount`
- `fun formatAmount(amount: Double): String` → `"$%.2f".format(amount)`
- `fun saveBillWithSplit(billName: String, amount: String, dueDate: String, groupName: String, members: MutableList<SplitMember>)`:
  - Creates `Bill(billName, dueDate, amount, "Unpaid", groupName, members)`
  - Adds to `app.bills`
- `fun getGroupMembers(groupName: String): List<String>` → finds group in `app.groups` by name, returns its members list

**SplitCalculatorPresenter.kt:**
- Holds: `var isEqualSplit = true`, `val splitMembers = mutableListOf<SplitMember>()`
- `loadData()`:
  - Gets members from `model.getGroupMembers(groupName)`
  - Parses `totalAmount` to Double (strip `$` and commas)
  - Calculates equal split per member
  - Populates `splitMembers` list with `SplitMember(name, formattedAmount, false)`
  - Calls `view.displayTotalBill(totalAmount)` and `view.displayMembers(splitMembers)`
- `onEqualSplitTab()` → recalculate equal split → update splitMembers amounts → `view.displayMembers()` + `view.updateTabUI(true)`
- `onCustomSplitTab()` → `isEqualSplit = false`, `view.updateTabUI(false)` (amounts stay editable)
- `onConfirmSave()` → `model.saveBillWithSplit(billName, totalAmount, dueDate, groupName, splitMembers)` → `view.showSavedSuccess()`

**SplitCalculatorActivity.kt:**
- `setContentView(R.layout.activity_split_calculator)`
- Receive from Intent: `billName`, `totalAmount`, `dueDate`, `groupName`
- Bind: `btnBack`, `tvTotalBill`, `tabEqualSplit`, `tabCustomSplit`, `listViewMembers`, `btnConfirmSave`
- Use `SplitMemberAdapter`
- `onCreate` → call `presenter.loadData()`
- `tabEqualSplit` click → `presenter.onEqualSplitTab()`
- `tabCustomSplit` click → `presenter.onCustomSplitTab()`
- `btnBack` → `finish()`
- `btnConfirmSave` click → `presenter.onConfirmSave()`
- `showSavedSuccess()` → `toastEXT("Split saved!")` + `finish()`
- `updateTabUI(isEqualSplit)`:
  - if equal: `tabEqualSplit` background = `@drawable/bg_button_blue`, textColor white; `tabCustomSplit` background = null, textColor `#94A3B8`
  - if custom: reverse

---

### 9. screens/groups/ — ALL 4 FILES

**GroupsContract.kt:**
```
interface View: displayGroups(groups: MutableList<Group>)
interface Presenter: loadGroups()
```

**GroupsModel.kt:**
- `fun getGroups(): MutableList<Group>` → `return app.groups`

**GroupsPresenter.kt:**
- `loadGroups()` → `view.displayGroups(model.getGroups())`

**GroupsActivity.kt:**
- `setContentView(R.layout.activity_groups)`
- Bind: `listViewGroups`, `navHome`, `navGroups`, `navBills`, `navProfile`
- Use `GroupAdapter`
- Override `onResume()` → `presenter.loadGroups()`
- **Item click** → navigate to `GroupDetailsActivity` with `intent.putExtra("GROUP_NAME", group.name)`
- Bottom nav: `navHome` → `DashboardActivity` + `finish()`, `navBills` → `AllBillsActivity` + `finish()`, `navProfile` → `ProfileActivity` + `finish()`

---

### 10. screens/creategroup/ — ALL 4 FILES

**CreateGroupContract.kt:**
```
interface View:
  updateMemberList(members: MutableList<String>)
  showGroupCreated()
  showEmptyGroupName()
  showEmptyMemberField()
  showMemberAlreadyAdded()
  clearMemberInput()

interface Presenter:
  onAddMember(name: String)
  onCreateGroup(groupName: String)
  removeMember(position: Int)
```

**CreateGroupModel.kt:**
- Holds `val sessionMembers = mutableListOf<String>()`
- `fun addMember(name: String)` → `sessionMembers.add(name)`
- `fun removeMember(position: Int)` → `sessionMembers.removeAt(position)`
- `fun isMemberAlreadyAdded(name: String): Boolean` → `sessionMembers.any { it.equals(name, true) }`
- `fun getMembers(): MutableList<String>` → `return sessionMembers`
- `fun createGroup(groupName: String)` → `app.groups.add(Group(groupName, sessionMembers.toMutableList()))`

**CreateGroupPresenter.kt:**
- `onAddMember(name)`:
  - if empty → `view.showEmptyMemberField()`
  - if already added → `view.showMemberAlreadyAdded()`
  - else → `model.addMember(name)`, `view.updateMemberList(model.getMembers())`, `view.clearMemberInput()`
- `onCreateGroup(groupName)`:
  - if empty → `view.showEmptyGroupName()`
  - else → `model.createGroup(groupName)`, `view.showGroupCreated()`
- `removeMember(position)` → `model.removeMember(position)`, `view.updateMemberList(model.getMembers())`

**CreateGroupActivity.kt:**
- `setContentView(R.layout.activity_create_group)`
- Bind: `btnBack`, `etGroupName`, `etMemberEmail`, `btnAddMember`, `listViewGroupMembers`, `btnCreateGroup`
- Use `GroupMemberAdapter`
- `btnBack` → `finish()`
- `btnAddMember` click → `presenter.onAddMember(getEditTextValueEXT(R.id.etMemberEmail))`
- `btnCreateGroup` click → `presenter.onCreateGroup(getEditTextValueEXT(R.id.etGroupName))`
- `updateMemberList(members)` → sync adapter list + `adapter.notifyDataSetChanged()`
- `clearMemberInput()` → `findViewById<EditText>(R.id.etMemberEmail).setText("")`
- `showGroupCreated()` → `toastEXT("Group created!")` + `finish()`
- `showEmptyGroupName()` → `toastEXT("Please enter a group name!")`
- `showEmptyMemberField()` → `toastEXT("Please enter a member name!")`
- `showMemberAlreadyAdded()` → `toastEXT("Member already added!")`
- Long click on list item → call `presenter.removeMember(position)`

---

### 11. screens/groupdetails/ — ALL 4 FILES (NEW PACKAGE)

**GroupDetailsContract.kt:**
```
interface View:
  displayGroupInfo(group: Group)
  displayBills(bills: MutableList<Bill>)
  displayMemberAvatars(members: List<String>)
  navigateToAddBill(groupName: String)

interface Presenter:
  loadGroupDetails()
  onAddBillClicked()
```

**GroupDetailsModel.kt:**
- `fun getGroup(groupName: String): Group?` → `app.groups.find { it.name == groupName }`
- `fun getBillsForGroup(groupName: String): MutableList<Bill>` → `app.bills.filter { it.groupName == groupName }.toMutableList()`

**GroupDetailsPresenter.kt:**
- `loadGroupDetails()`:
  - Gets group from model
  - `view.displayGroupInfo(group)`
  - `view.displayMemberAvatars(group.members)`
  - `view.displayBills(model.getBillsForGroup(groupName))`
- `onAddBillClicked()` → `view.navigateToAddBill(groupName)`

**GroupDetailsActivity.kt:**
- `setContentView(R.layout.activity_group_details)`
- Receive: `groupName = intent.getStringExtra("GROUP_NAME") ?: ""`
- Bind: `btnBack`, `tvGroupDetailName`, `layoutMemberAvatars`, `btnAddBillToGroup`, `listViewGroupBills`
- Use `BillListViewAdapter`
- Override `onResume()` → `presenter.loadGroupDetails()` so bills refresh after adding
- `btnBack` → `finish()`
- `btnAddBillToGroup` click → `presenter.onAddBillClicked()`
- Bill item click → navigate to `BillDetailsActivity` with `BILL_NAME`
- `displayGroupInfo(group)` → `tvGroupDetailName.text = group.name`
- `displayMemberAvatars(members)`:
  - Loop through members
  - For each, inflate a small LinearLayout with a `TextView` (circle bg, first letter) + name label
  - Add to `layoutMemberAvatars` programmatically before the static "Add" button
  - Use this pattern per member:
    ```kotlin
    val memberView = LinearLayout(this)
    memberView.orientation = LinearLayout.VERTICAL
    memberView.gravity = android.view.Gravity.CENTER
    val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    params.marginEnd = 12.dpToPx()
    memberView.layoutParams = params

    val avatar = TextView(this)
    avatar.text = member.first().uppercase()
    avatar.setTextColor(0xFF539CFF.toInt())
    avatar.textSize = 16f
    avatar.typeface = android.graphics.Typeface.DEFAULT_BOLD
    avatar.gravity = android.view.Gravity.CENTER
    val avatarSize = 48.dpToPx()
    avatar.layoutParams = LinearLayout.LayoutParams(avatarSize, avatarSize)
    avatar.setBackgroundResource(R.drawable.bg_circle_icon)

    val nameLabel = TextView(this)
    nameLabel.text = member.split(" ").first()
    nameLabel.setTextColor(0xFFCCE4FF.toInt())
    nameLabel.textSize = 11f
    nameLabel.gravity = android.view.Gravity.CENTER
    nameLabel.layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply { topMargin = 4.dpToPx() }

    memberView.addView(avatar)
    memberView.addView(nameLabel)
    layoutMemberAvatars.addView(memberView, layoutMemberAvatars.childCount - 1)
    ```
  - Add extension in `utils/extension.kt`: `fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()`
- `navigateToAddBill(groupName)`:
  ```kotlin
  val intent = Intent(this, AddBillActivity::class.java)
  intent.putExtra("PRESET_GROUP", groupName)
  startActivity(intent)
  ```
- In `AddBillActivity.onCreate()`, check: `intent.getStringExtra("PRESET_GROUP")?.let { presenter.onGroupSelected(it) }`

---

### 12. screens/notifications/ — ALL 4 FILES

Create a data class inside the notifications package:
```kotlin
// screens/notifications/NotificationItem.kt
data class NotificationItem(
    var title: String = "",
    var body: String = "",
    var time: String = "",
    var iconType: String = "bill"  // "bill", "payment", "add"
)
```

**NotificationsContract.kt:**
```
interface View: displayNotifications(items: MutableList<NotificationItem>)
interface Presenter: loadNotifications()
```

**NotificationsModel.kt** (no CustomApp needed):
- `fun getNotifications(): MutableList<NotificationItem>` → returns hardcoded list:
  1. `NotificationItem("Upcoming Due Date", "Electricity bill is due tomorrow.", "2 hours ago", "bill")`
  2. `NotificationItem("Payment Received", "Alex paid their share for Wi-Fi.", "Yesterday", "payment")`
  3. `NotificationItem("New Bill Added", "Sam added 'Groceries' in Dorm Room A.", "Yesterday", "add")`

**NotificationsPresenter.kt:**
- `loadNotifications()` → `view.displayNotifications(model.getNotifications())`

**NotificationsActivity.kt:**
- `setContentView(R.layout.activity_notifications)`
- Bind: `listViewNotifications`
- Use `NotificationAdapter`
- `onCreate` → `presenter.loadNotifications()`

---

### 13. screens/billdetails/ — ALL 4 FILES (NEW PACKAGE)

**BillDetailsContract.kt:**
```
interface View:
  displayBillInfo(bill: Bill)
  displaySplitMembers(members: MutableList<SplitMember>)
  showMemberMarkedPaid(memberName: String)
  showBillFullyPaid()
  refreshList()

interface Presenter:
  loadBillDetails()
  onMarkPaid(memberPosition: Int)
```

**BillDetailsModel.kt:**
- `fun getBill(billName: String): Bill?` → `app.bills.find { it.name == billName }`
- `fun getMembers(billName: String): MutableList<SplitMember>` → `getBill(billName)?.splitMembers ?: mutableListOf()`
- `fun markMemberPaid(billName: String, position: Int)`:
  - Sets `bill.splitMembers[position].isPaid = true`
  - If all members paid → `bill.status = "Paid"`
- `fun isFullyPaid(billName: String): Boolean` → all splitMembers isPaid == true

**BillDetailsPresenter.kt:**
- `loadBillDetails()`:
  - `val bill = model.getBill(billName) ?: return`
  - `view.displayBillInfo(bill)`
  - `view.displaySplitMembers(model.getMembers(billName))`
- `onMarkPaid(position)`:
  - Get member at position from model
  - If already paid → return (do nothing)
  - `model.markMemberPaid(billName, position)`
  - `view.showMemberMarkedPaid(member.name)`
  - `view.refreshList()`
  - if `model.isFullyPaid(billName)` → `view.showBillFullyPaid()`

**BillDetailsActivity.kt:**
- `setContentView(R.layout.activity_bill_details)`
- Receive: `billName = intent.getStringExtra("BILL_NAME") ?: ""`
- Bind: `btnBack`, `tvBillDetailName`, `tvBillDetailGroup`, `tvBillDetailAmount`, `tvBillDetailDueDate`, `listViewSplitDetails`
- Use `SplitDetailAdapter` with lambda: `{ position -> presenter.onMarkPaid(position) }`
- `btnBack` → `finish()`
- `onCreate` → `presenter.loadBillDetails()`
- `displayBillInfo(bill)` → bind all TextViews
- `displaySplitMembers(members)` → sync memberList + `adapter.notifyDataSetChanged()`
- `refreshList()` → `adapter.notifyDataSetChanged()`
- `showMemberMarkedPaid(name)` → `toastEXT("$name marked as paid!")`
- `showBillFullyPaid()` → `toastEXT("All members paid! Bill fully settled.")`

---

### 14. screens/profile/ — ALL 4 FILES (NEW PACKAGE)

**ProfileContract.kt:**
```
interface View:
  displayProfile(name: String, initial: String)
  displayStats(groupCount: Int, billCount: Int, unpaidCount: Int)
  showLogoutConfirmDialog()
  navigateToWelcome()

interface Presenter:
  loadProfile()
  onLogoutClicked()
  onLogoutConfirmed()
```

**ProfileModel.kt:**
- `fun getUsername(): String` → `app.loginUser.username`
- `fun getInitial(): String` → `app.loginUser.username.firstOrNull()?.uppercase() ?: "?"`
- `fun getGroupCount(): Int` → `app.groups.size`
- `fun getBillCount(): Int` → `app.bills.size`
- `fun getUnpaidCount(): Int` → `app.bills.count { it.status == "Unpaid" }`
- `fun logout()` → `app.loginUser = User()`

**ProfilePresenter.kt:**
- `loadProfile()`:
  - `view.displayProfile(model.getUsername(), model.getInitial())`
  - `view.displayStats(model.getGroupCount(), model.getBillCount(), model.getUnpaidCount())`
- `onLogoutClicked()` → `view.showLogoutConfirmDialog()`
- `onLogoutConfirmed()` → `model.logout()` → `view.navigateToWelcome()`

**ProfileActivity.kt:**
- `setContentView(R.layout.activity_profile)`
- Bind: `tvProfileInitial`, `tvProfileName`, `tvProfileEmail`, `tvStatGroups`, `tvStatBills`, `tvStatUnpaid`
- Bind menu items: `menuMyBills`, `menuMyGroups`, `menuNotifications`, `menuChangePassword`, `menuLogout`
- Bind bottom nav: `navHome`, `navGroups`, `navBills`, `navProfile`
- `onCreate` → `presenter.loadProfile()`
- `displayProfile(name, initial)`:
  - `tvProfileInitial.text = initial`
  - `tvProfileName.text = name`
  - `tvProfileEmail.text = name.lowercase().replace(" ", ".") + "@email.com"` *(derived since no email field yet)*
- `displayStats(groups, bills, unpaid)`:
  - `tvStatGroups.text = groups.toString()`
  - `tvStatBills.text = bills.toString()`
  - `tvStatUnpaid.text = unpaid.toString()`
- Menu clicks:
  - `menuMyBills` → `startActivity(Intent(this, AllBillsActivity::class.java))`
  - `menuMyGroups` → `startActivity(Intent(this, GroupsActivity::class.java))`
  - `menuNotifications` → `startActivity(Intent(this, NotificationsActivity::class.java))`
  - `menuChangePassword` → `toastEXT("Coming soon!")`
  - `menuLogout` → `presenter.onLogoutClicked()`
- `showLogoutConfirmDialog()`:
  ```kotlin
  AlertDialog.Builder(this)
      .setTitle("Logout")
      .setMessage("Are you sure you want to logout?")
      .setPositiveButton("Logout") { _, _ -> presenter.onLogoutConfirmed() }
      .setNegativeButton("Cancel", null)
      .show()
  ```
- `navigateToWelcome()`:
  ```kotlin
  val intent = Intent(this, WelcomeActivity::class.java)
  intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
  startActivity(intent)
  ```
- Bottom nav: `navHome` → `DashboardActivity` + `finish()`, `navGroups` → `GroupsActivity` + `finish()`, `navBills` → `AllBillsActivity` + `finish()`, `navProfile` → stays (already here)
- Override `onResume()` → `presenter.loadProfile()` so stats refresh

---

### 15. helper/GroupAdapter.kt — IMPLEMENT

Follow `BillListViewAdapter` pattern:
- Constructor: `(context: Context, groupList: List<Group>)`
- Inflates `R.layout.item_group`
- Binds: `tvGroupName` = `group.name`, `tvMemberCount` = `"${group.members.size} members"`

### 16. helper/GroupMemberAdapter.kt — IMPLEMENT

- Constructor: `(context: Context, memberList: List<String>)`
- Inflates `R.layout.item_group_member`
- Binds: `tvMemberInitial` = first char uppercased, `tvMemberName` = name
- `btnRemoveMember` → set visibility to `View.VISIBLE`

### 17. helper/NotificationAdapter.kt — IMPLEMENT

- Constructor: `(context: Context, notificationList: List<NotificationItem>)`
- Inflates `R.layout.item_notification`
- Binds: `tvNotificationTitle`, `tvNotificationBody`, `tvNotificationTime`
- Icon tint based on iconType: "bill" → `0xFFEF4444.toInt()` red, "payment" → `0xFF10B981.toInt()` green, "add" → `0xFF539CFF.toInt()` blue

### 18. helper/SplitMemberAdapter.kt — IMPLEMENT

- Constructor: `(context: Context, memberList: List<SplitMember>)`
- Inflates `R.layout.item_split_member`
- Binds: `tvMemberInitial`, `tvMemberName`, `etMemberAmount`
- `etMemberAmount` stays editable

### 19. helper/SplitDetailAdapter.kt — NEW ADAPTER

- Constructor: `(context: Context, memberList: List<SplitMember>, onMarkPaid: (Int) -> Unit)`
- Inflates `R.layout.item_split_detail`
- Binds: `tvSplitMemberInitial`, `tvSplitMemberName`, `tvSplitMemberAmount`, `btnMarkPaid`
- If `member.isPaid`:
  - `btnMarkPaid.text = "✓ Paid"`
  - `btnMarkPaid.setTextColor(0xFF10B981.toInt())` green
  - `btnMarkPaid.isEnabled = false`
- Else:
  - `btnMarkPaid.text = "Mark Paid"`
  - `btnMarkPaid.setTextColor(0xFF539CFF.toInt())` blue
  - `btnMarkPaid.isEnabled = true`
  - `btnMarkPaid.setOnClickListener { onMarkPaid(position) }`

---

### 20. AndroidManifest.xml — ADD missing activities

Add inside `<application>`:
```xml
<activity android:name=".screens.register.RegisterActivity" android:exported="false" />
<activity android:name=".screens.allbills.AllBillsActivity" android:exported="false" />
<activity android:name=".screens.groups.GroupsActivity" android:exported="false" />
<activity android:name=".screens.notifications.NotificationsActivity" android:exported="false" />
<activity android:name=".screens.addbill.AddBillActivity" android:exported="false" />
<activity android:name=".screens.splitcalculator.SplitCalculatorActivity" android:exported="false" />
<activity android:name=".screens.creategroup.CreateGroupActivity" android:exported="false" />
<activity android:name=".screens.billdetails.BillDetailsActivity" android:exported="false" />
<activity android:name=".screens.groupdetails.GroupDetailsActivity" android:exported="false" />
<activity android:name=".screens.profile.ProfileActivity" android:exported="false" />
```

### 21. utils/extension.kt — ADD dpToPx

Add to the existing file:
```kotlin
fun Int.dpToPx(): Int = (this * android.content.res.Resources.getSystem().displayMetrics.density).toInt()
```

---

## COMPLETE NAVIGATION FLOW SUMMARY

```
Welcome → Login ←→ Register
Login → Dashboard

Dashboard:
  btnAddBill → AddBill
  btnCreateGroup → CreateGroup
  navGroups → Groups
  navBills → AllBills
  navProfile → Profile
  bell → Notifications
  bill item click → BillDetails(BILL_NAME)

AddBill → SplitCalculator (via Intent extras)
SplitCalculator → (finish, back to wherever)

Groups:
  item click → GroupDetails(GROUP_NAME)
  navHome → Dashboard
  navBills → AllBills
  navProfile → Profile

GroupDetails:
  btnAddBillToGroup → AddBill (with PRESET_GROUP)
  bill item click → BillDetails(BILL_NAME)
  btnBack → finish

AllBills:
  item click → BillDetails(BILL_NAME)
  navHome → Dashboard
  navGroups → Groups
  navProfile → Profile

BillDetails:
  Mark Paid button → marks member paid in app.bills
  btnBack → finish

Profile:
  menuMyBills → AllBills
  menuMyGroups → Groups
  menuNotifications → Notifications
  menuLogout → confirm dialog → Welcome (clear stack)
  navHome → Dashboard
  navGroups → Groups
  navBills → AllBills
```

---

## OUTPUT FORMAT

Generate each file with a clear header:
```
// ===== package/FileName.kt =====
```
Then the complete file. Generate ALL files. Do not skip any file. Do not use placeholder comments like "// implement here" — write real working code.