package com.aiden.budtrackrfinal

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aiden.budtrackrfinal.data.AppDatabase
import com.aiden.budtrackrfinal.data.Expense
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ViewExpensesActivity : AppCompatActivity() {

    private var userId: Int = -1
    private lateinit var listExpenses: LinearLayout
    private lateinit var edtStartDate: EditText
    private lateinit var edtEndDate: EditText
    private lateinit var txtTotalExpenses: TextView
    private lateinit var txtTransactions: TextView
    private lateinit var txtPeriod: TextView

    private val db by lazy { AppDatabase.getDatabase(this) }
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        userId = intent.getIntExtra("userId", -1)

        val btnAddExpense = findViewById<Button>(R.id.btnAddExpense)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnFilter = findViewById<Button>(R.id.btnFilter)
        val btnReset = findViewById<Button>(R.id.btnReset)

        listExpenses = findViewById(R.id.listExpenses)
        edtStartDate = findViewById(R.id.edtStartDate)
        edtEndDate = findViewById(R.id.edtEndDate)
        txtTotalExpenses = findViewById(R.id.txtTotalExpenses)
        txtTransactions = findViewById(R.id.txtTransactions)
        txtPeriod = findViewById(R.id.txtPeriod)

        btnAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            finish()
        }

        edtStartDate.setOnClickListener {
            showDatePicker(edtStartDate)
        }

        edtEndDate.setOnClickListener {
            showDatePicker(edtEndDate)
        }

        btnFilter.setOnClickListener {
            loadExpenses(true)
        }

        btnReset.setOnClickListener {
            edtStartDate.text.clear()
            edtEndDate.text.clear()
            loadExpenses(false)
        }
    }

    override fun onResume() {
        super.onResume()
        loadExpenses(false)
    }

    private fun showDatePicker(target: EditText) {
        val calendar = Calendar.getInstance()

        DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                target.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun loadExpenses(applyFilter: Boolean) {
        lifecycleScope.launch {
            var expenses = db.expenseDao().getExpensesForUser(userId)

            if (applyFilter) {
                val start = edtStartDate.text.toString()
                val end = edtEndDate.text.toString()

                if (start.isNotEmpty() && end.isNotEmpty()) {
                    val startDate = dateFormat.parse(start)
                    val endDate = dateFormat.parse(end)

                    expenses = expenses.filter {
                        val expenseDate = dateFormat.parse(it.date)

                        expenseDate != null &&
                                startDate != null &&
                                endDate != null &&
                                !expenseDate.before(startDate) &&
                                !expenseDate.after(endDate)
                    }
                }
            }

            updateSummary(expenses)
            showExpenses(expenses)
        }
    }

    private fun updateSummary(expenses: List<Expense>) {
        val total = expenses.sumOf { it.amount }

        txtTotalExpenses.text = "Total Expenses\nR %.2f".format(total)
        txtTransactions.text = "Transactions\n${expenses.size}"

        val start = edtStartDate.text.toString()
        val end = edtEndDate.text.toString()

        txtPeriod.text = if (start.isNotEmpty() && end.isNotEmpty()) {
            "Period\n$start - $end"
        } else {
            "Period\nAll"
        }
    }

    private fun showExpenses(expenses: List<Expense>) {
        listExpenses.removeAllViews()

        if (expenses.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "No expenses found."
            emptyText.setTextColor(Color.rgb(96, 125, 107))
            emptyText.textSize = 15f
            emptyText.setPadding(12, 12, 12, 12)
            listExpenses.addView(emptyText)
            return
        }

        for (expense in expenses) {
            val item = TextView(this)
            item.text =
                "${expense.categoryName}\n" +
                        "${expense.description}\n" +
                        "R %.2f | %s".format(expense.amount, expense.date)

            item.setTextColor(Color.rgb(16, 35, 61))
            item.textSize = 16f
            item.setPadding(16, 16, 16, 16)
            item.setBackgroundColor(Color.rgb(234, 245, 238))

            item.setOnClickListener {
                val intent = Intent(this, ExpenseDetailsActivity::class.java)
                intent.putExtra("expense", expense)
                startActivity(intent)
            }

            val space = View(this)
            space.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                12
            )

            listExpenses.addView(item)
            listExpenses.addView(space)
        }
    }
}