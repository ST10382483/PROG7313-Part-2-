package com.aiden.budtrackrfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aiden.budtrackrfinal.data.AppDatabase
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var username: String = "USERNAME"

    private lateinit var txtTransactionBalance: TextView
    private lateinit var txtCurrentBalance: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userId = intent.getIntExtra("userId", -1)
        username = intent.getStringExtra("username") ?: "USERNAME"

        val homeUsername = findViewById<TextView>(R.id.homeUsername)
        val settingsIcon = findViewById<ImageView>(R.id.imgSettings)
        val goalsIcon = findViewById<ImageButton>(R.id.navGoals)
        val expensesButton = findViewById<Button>(R.id.expensesButton)
        val transactionsIcon = findViewById<ImageButton>(R.id.navTransactions)
        val accountCard = findViewById<LinearLayout>(R.id.accountCard)
        val accountIcon = findViewById<ImageButton>(R.id.navAccount)


        txtTransactionBalance = findViewById(R.id.txtTransactionBalance)
        txtCurrentBalance = findViewById(R.id.txtCurrentBalance)

        homeUsername.text = username.uppercase()


        settingsIcon.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        goalsIcon.setOnClickListener {
            val intent = Intent(this, GoalsActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        expensesButton.setOnClickListener {
            val intent = Intent(this, ViewExpensesActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        transactionsIcon.setOnClickListener {
            val intent = Intent(this, GraphsActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        accountCard.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        accountIcon.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }


        loadHomeBalances()
    }

    override fun onResume() {
        super.onResume()
        loadHomeBalances()
    }


    private fun loadHomeBalances() {
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val incomeTotal = db.incomeDao().getIncomeForUser(userId).sumOf { it.amount }
            val expenseTotal = db.expenseDao().getExpensesForUser(userId).sumOf { it.amount }
            val balance = incomeTotal - expenseTotal

            txtTransactionBalance.text = "R %.2f".format(expenseTotal)
            txtCurrentBalance.text = "R %.2f".format(balance)
        }
    }
}