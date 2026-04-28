package com.aiden.budtrackrfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var username: String = "USERNAME"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userId = intent.getIntExtra("userId", -1)
        username = intent.getStringExtra("username") ?: "USERNAME"

        val homeUsername = findViewById<TextView>(R.id.homeUsername)
        val settingsIcon = findViewById<ImageView>(R.id.imgSettings)
        val goalsIcon = findViewById<ImageButton>(R.id.navGoals)
        val expensesButton = findViewById<Button>(R.id.expensesButton)

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

        val transactionsIcon = findViewById<ImageButton>(R.id.navTransactions)

        transactionsIcon.setOnClickListener {
            val intent = Intent(this, GraphsActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }
}