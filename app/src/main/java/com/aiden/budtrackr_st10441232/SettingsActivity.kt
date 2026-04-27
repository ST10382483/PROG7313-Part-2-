package com.aiden.budtrackr_st10441232

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView

class SettingsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val privacy = findViewById<TextView>(R.id.btnPrivacy)
        val goals = findViewById<TextView>(R.id.btnGoals)
        val resetPassword = findViewById<TextView>(R.id.btnReset)
        val deleteAccount = findViewById<TextView>(R.id.btnDelete)
        val help = findViewById<TextView>(R.id.btnHelp)

        // Privacy Page
        privacy.setOnClickListener {
            startActivity(Intent(this, PrivacyActivity::class.java))
        }

        // Goals Page
        goals.setOnClickListener {
            startActivity(Intent(this, GoalsActivity::class.java))
        }

        // Reset Password Page
        resetPassword.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }

        // Delete Account Page
        deleteAccount.setOnClickListener {
            startActivity(Intent(this, DeleteAccountActivity::class.java))
        }

        // Help Page
        help.setOnClickListener {
            startActivity(Intent(this, HelpActivity::class.java))
        }
    }
}