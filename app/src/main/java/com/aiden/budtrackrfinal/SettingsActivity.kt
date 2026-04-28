package com.aiden.budtrackrfinal

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        userId = intent.getIntExtra("userId", -1)
        username = intent.getStringExtra("username") ?: ""

        val btnPrivacy = findViewById<TextView>(R.id.btnPrivacy)
        val btnResetPassword = findViewById<TextView>(R.id.btnResetPassword)
        val btnDeleteAccount = findViewById<TextView>(R.id.btnDeleteAccount)
        val btnHelp = findViewById<TextView>(R.id.btnHelp)

        btnPrivacy.setOnClickListener {
            startActivity(Intent(this, PrivacyActivity::class.java))
        }

        btnResetPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        btnDeleteAccount.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        btnHelp.setOnClickListener {
            startActivity(Intent(this, HelpActivity::class.java))
        }
    }
}