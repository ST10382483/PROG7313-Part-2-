package com.neha.st10442032loginreghome

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Home screen — shown after successful login or registration
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Link to the home screen layout
        setContentView(R.layout.activity_home)

        // Get references to UI elements
        val usernameText = findViewById<TextView>(R.id.homeUsername)
        val expensesButton = findViewById<Button>(R.id.expensesButton)
        val settingsIcon = findViewById<ImageView>(R.id.settingsIcon)
        val navHome = findViewById<ImageButton>(R.id.navHome)
        val navTransactions = findViewById<ImageButton>(R.id.navTransactions)
        val navAccount = findViewById<ImageButton>(R.id.navAccount)
        val navNotifications = findViewById<ImageButton>(R.id.navNotifications)

        // Get user details passed from Login or Register
        val username = intent.getStringExtra("USER_USERNAME")
        val email = intent.getStringExtra("USER_EMAIL")

        // Show username if registered, otherwise show email from login
        if (!username.isNullOrEmpty()) {
            usernameText.text = username.uppercase()
        } else if (!email.isNullOrEmpty()) {
            usernameText.text = email.uppercase()
        }

        // Settings icon — opens the Settings screen
        settingsIcon.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Expenses button — placeholder for now
        expensesButton.setOnClickListener {
            Toast.makeText(this, "Expenses coming soon!", Toast.LENGTH_SHORT).show()
        }

        // Bottom nav buttons
        navHome.setOnClickListener {
            // Already on home screen, do nothing
        }
        navTransactions.setOnClickListener {
            Toast.makeText(this, "Transactions coming soon!", Toast.LENGTH_SHORT).show()
        }
        navAccount.setOnClickListener {
            Toast.makeText(this, "Account coming soon!", Toast.LENGTH_SHORT).show()
        }
        navNotifications.setOnClickListener {
            Toast.makeText(this, "Notifications coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
}