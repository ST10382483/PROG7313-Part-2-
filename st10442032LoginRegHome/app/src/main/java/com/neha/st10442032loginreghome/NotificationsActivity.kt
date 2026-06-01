package com.neha.st10442032loginreghome

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Notifications screen — Aiden's page, with trophy button leading to Rewards
class NotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        // Get references to UI elements
        val trophyBtn = findViewById<TextView>(R.id.trophyBtn)
        val navHome = findViewById<ImageButton>(R.id.navHome)
        val navTransactions = findViewById<ImageButton>(R.id.navTransactions)
        val navAccount = findViewById<ImageButton>(R.id.navAccount)
        val navNotifications = findViewById<ImageButton>(R.id.navNotifications)

        // Trophy button — navigate to the Rewards screen
        trophyBtn.setOnClickListener {
            startActivity(Intent(this, RewardsActivity::class.java))
        }

        // Bottom navigation
        navHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        navTransactions.setOnClickListener {
            Toast.makeText(this, "Transactions coming soon!", Toast.LENGTH_SHORT).show()
        }
        navAccount.setOnClickListener {
            Toast.makeText(this, "Account coming soon!", Toast.LENGTH_SHORT).show()
        }
        navNotifications.setOnClickListener {
            // Already on notifications, do nothing
        }
    }
}