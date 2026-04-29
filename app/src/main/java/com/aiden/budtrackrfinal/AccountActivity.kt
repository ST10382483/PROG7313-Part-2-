package com.aiden.budtrackrfinal

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aiden.budtrackrfinal.data.AppDatabase
import com.aiden.budtrackrfinal.data.Income
import kotlinx.coroutines.launch

class AccountActivity : AppCompatActivity() {

    private var userId: Int = -1
    private val db by lazy { AppDatabase.getDatabase(this) }

    private lateinit var txtIncomeTotal: TextView
    private lateinit var txtExpenseTotal: TextView
    private lateinit var txtBalance: TextView
    private lateinit var edtIncomeMonth: EditText
    private lateinit var edtIncomeAmount: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        userId = intent.getIntExtra("userId", -1)

        txtIncomeTotal = findViewById(R.id.txtIncomeTotal)
        txtExpenseTotal = findViewById(R.id.txtExpenseTotal)
        txtBalance = findViewById(R.id.txtBalance)
        edtIncomeMonth = findViewById(R.id.edtIncomeMonth)
        edtIncomeAmount = findViewById(R.id.edtIncomeAmount)

        val btnAddIncome = findViewById<Button>(R.id.btnAddIncome)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnAddIncome.setOnClickListener {
            addIncome()
        }

        btnBack.setOnClickListener {
            finish()
        }

        loadSummary()
    }

    private fun addIncome() {
        val month = edtIncomeMonth.text.toString().trim()
        val amount = edtIncomeAmount.text.toString().trim().toDoubleOrNull()

        if (month.isEmpty() || amount == null) {
            Toast.makeText(this, "Enter month and income amount", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            db.incomeDao().insertIncome(
                Income(
                    userId = userId,
                    month = month,
                    amount = amount
                )
            )

            Toast.makeText(this@AccountActivity, "Income added", Toast.LENGTH_SHORT).show()
            edtIncomeMonth.text.clear()
            edtIncomeAmount.text.clear()
            loadSummary()
        }
    }

    private fun loadSummary() {
        lifecycleScope.launch {
            val incomeTotal = db.incomeDao().getIncomeForUser(userId).sumOf { it.amount }
            val expenseTotal = db.expenseDao().getExpensesForUser(userId).sumOf { it.amount }
            val balance = incomeTotal - expenseTotal

            txtIncomeTotal.text = "+ Income: R %.2f".format(incomeTotal)
            txtExpenseTotal.text = "- Expenses: R %.2f".format(expenseTotal)
            txtBalance.text = "Balance: R %.2f".format(balance)
        }
    }
}