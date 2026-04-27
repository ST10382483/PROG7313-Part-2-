package com.aiden.budtrackr_st10441232

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView

class SettingsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val goals = findViewById<TextView>(R.id.btnGoals)
        val deleteAccount = findViewById<TextView>(R.id.btnDelete)
        val resetPassword = findViewById<TextView>(R.id.btnReset)
        val privacy = findViewById<TextView>(R.id.btnPrivacy)

        goals.setOnClickListener {
            startActivity(Intent(this, GoalsActivity::class.java))
        }

        deleteAccount.setOnClickListener {
            startActivity(Intent(this, DeleteAccountActivity::class.java))
        }

        resetPassword.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }

        privacy.setOnClickListener {
            startActivity(Intent(this, PrivacyActivity::class.java))
        }
    }
}