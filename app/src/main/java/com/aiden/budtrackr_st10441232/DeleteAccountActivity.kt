package com.aiden.budtrackr_st10441232

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class DeleteAccountActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account)

        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val backHomeBtn = findViewById<Button>(R.id.backHomeBtn)
        val deleteAccountBtn = findViewById<Button>(R.id.deleteAccountBtn)

        backHomeBtn.setOnClickListener {
            finish()
        }

        deleteAccountBtn.setOnClickListener {
            val password = passwordInput.text.toString()

            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            } else {

                getSharedPreferences("GoalPrefs", MODE_PRIVATE).edit().clear().apply()

                Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}