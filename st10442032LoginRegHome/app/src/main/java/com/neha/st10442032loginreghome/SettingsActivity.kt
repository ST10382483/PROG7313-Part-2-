package com.neha.st10442032loginreghome

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Settings screen — allows users to manage their account preferences
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Link to the settings screen layout
        setContentView(R.layout.activity_settings)

        // Get references to all menu items
        val privacyOption = findViewById<TextView>(R.id.settingsPrivacy)
        val goalsOption = findViewById<TextView>(R.id.settingsGoals)
        val resetPasswordOption = findViewById<TextView>(R.id.settingsResetPassword)
        val deleteAccountOption = findViewById<TextView>(R.id.settingsDeleteAccount)
        val helpOption = findViewById<TextView>(R.id.settingsHelp)

        // Bottom navigation buttons
        val navHome = findViewById<ImageButton>(R.id.navHome)
        val navTransactions = findViewById<ImageButton>(R.id.navTransactions)
        val navAccount = findViewById<ImageButton>(R.id.navAccount)
        val navNotifications = findViewById<ImageButton>(R.id.navNotifications)

        // Privacy — placeholder for now
        privacyOption.setOnClickListener {
            Toast.makeText(this, "Privacy settings coming soon!", Toast.LENGTH_SHORT).show()
        }

        // Goals — placeholder for now
        goalsOption.setOnClickListener {
            Toast.makeText(this, "Goals coming soon!", Toast.LENGTH_SHORT).show()
        }

        // Reset Password — navigate to Reset Password screen
        resetPasswordOption.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }

        // Delete Account — navigate to Delete Account screen
        deleteAccountOption.setOnClickListener {
            startActivity(Intent(this, DeleteAccountActivity::class.java))
        }

        // Help — navigate to Help screen
        helpOption.setOnClickListener {
            startActivity(Intent(this, HelpActivity::class.java))
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
            Toast.makeText(this, "Notifications coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
}