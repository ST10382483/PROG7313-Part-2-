package com.aiden.budtrackr_st10441232

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ResetPasswordActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val resetBtn = findViewById<Button>(R.id.resetAccountBtn)

        resetBtn.setOnClickListener {
            val email = emailInput.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Password reset email sent to $email", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}