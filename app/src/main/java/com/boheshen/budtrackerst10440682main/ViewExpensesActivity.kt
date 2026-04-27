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