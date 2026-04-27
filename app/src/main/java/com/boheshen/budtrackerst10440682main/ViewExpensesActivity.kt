package com.boheshen.budtrackerst10440682main

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class ViewExpensesActivity : AppCompatActivity() {

    private lateinit var edtStartDate: EditText
    private lateinit var edtEndDate: EditText
    private lateinit var btnFilter: Button
    private lateinit var btnReset: Button
    private lateinit var btnAddExpense: Button
    private lateinit var txtTotalExpenses: TextView
    private lateinit var txtTransactions: TextView
    private lateinit var txtPeriod: TextView
    private lateinit var expenseContainer: LinearLayout

    private val expenses = mutableListOf<Expense>()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        edtStartDate = findViewById(R.id.edtStartDate)
        edtEndDate = findViewById(R.id.edtEndDate)
        btnFilter = findViewById(R.id.btnFilter)
        btnReset = findViewById(R.id.btnReset)
        btnAddExpense = findViewById(R.id.btnAddExpense)
        txtTotalExpenses = findViewById(R.id.txtTotalExpenses)
        txtTransactions = findViewById(R.id.txtTransactions)
        txtPeriod = findViewById(R.id.txtPeriod)
        expenseContainer = findViewById(R.id.expenseContainer)

        loadExpenses()

        edtStartDate.setOnClickListener { showDatePicker(edtStartDate) }
        edtEndDate.setOnClickListener { showDatePicker(edtEndDate) }

        btnFilter.setOnClickListener { filterExpenses() }

        btnReset.setOnClickListener {
            edtStartDate.setText("")
            edtEndDate.setText("")
            showExpenses(expenses)
            txtPeriod.text = "Period\nAll"
        }

        btnAddExpense.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        showExpenses(expenses)
        txtPeriod.text = "Period\nAll"
    }

    override fun onResume() {
        super.onResume()
        loadExpenses()
        showExpenses(expenses)
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()

        DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = String.format("%02d/%02d/%04d", day, month + 1, year)
                editText.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun filterExpenses() {
        val start = edtStartDate.text.toString().trim()
        val end = edtEndDate.text.toString().trim()

        if (start.isEmpty() || end.isEmpty()) {
            Toast.makeText(this, "Select both dates", Toast.LENGTH_SHORT).show()
            return
        }

        val startDate = dateFormat.parse(start)
        val endDate = dateFormat.parse(end)

        if (startDate == null || endDate == null) {
            Toast.makeText(this, "Invalid date selected", Toast.LENGTH_SHORT).show()
            return
        }

        if (startDate.after(endDate)) {
            Toast.makeText(this, "Start date cannot be after end date", Toast.LENGTH_SHORT).show()
            return
        }

        val filtered = expenses.filter {
            val d = dateFormat.parse(it.date)
            d != null && !d.before(startDate) && !d.after(endDate)
        }

        showExpenses(filtered)
        txtPeriod.text = "Period\n$start - $end"
    }

    private fun showExpenses(list: List<Expense>) {
        expenseContainer.removeAllViews()

        txtTotalExpenses.text = "Total Expenses\nR %.2f".format(list.sumOf { it.amount })
        txtTransactions.text = "Transactions\n${list.size}"

        if (list.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "No expenses added yet."
            emptyText.textSize = 15f
            emptyText.gravity = Gravity.CENTER
            emptyText.setTextColor(Color.parseColor("#607D6B"))
            emptyText.setPadding(10, 30, 10, 30)

            expenseContainer.addView(emptyText)
            return
        }

        for (expense in list) {

            val card = LinearLayout(this)
            card.orientation = LinearLayout.VERTICAL
            card.setPadding(16, 14, 16, 14)
            card.setBackgroundColor(Color.parseColor("#F7FBF8"))

            val topRow = LinearLayout(this)
            topRow.orientation = LinearLayout.HORIZONTAL
            topRow.gravity = Gravity.CENTER_VERTICAL