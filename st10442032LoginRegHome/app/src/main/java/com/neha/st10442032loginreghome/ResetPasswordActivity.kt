package com.neha.st10442032loginreghome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Reset Password screen — allows users to request a password reset via email
class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Link to the reset password layout
        setContentView(R.layout.activity_reset_password)

        // Get references to UI elements
        val emailField = findViewById<EditText>(R.id.resetEmailField)
        val resetBtn = findViewById<Button>(R.id.resetAccountBtn)

        // Bottom navigation buttons
        val navHome = findViewById<ImageButton>(R.id.navHome)
        val navTransactions = findViewById<ImageButton>(R.id.navTransactions)
        val navAccount = findViewById<ImageButton>(R.id.navAccount)
        val navNotifications = findViewById<ImageButton>(R.id.navNotifications)

        // Reset button — validate email then send reset notification
        resetBtn.setOnClickListener {
            val email = emailField.text.toString().trim()

            // Validate email is not empty
            if (email.isEmpty()) {
                emailField.error = "Please enter your email address"
                return@setOnClickListener
            }

            // Validate email format
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailField.error = "Please enter a valid email address"
                return@setOnClickListener
            }

            // TODO: In a full implementation, send a real password reset email here
            // For now, show a confirmation message to the user
            Toast.makeText(
                this,
                "A confirmation email has been sent to $email",
                Toast.LENGTH_LONG
            ).show()

            // Go back to settings after sending
            finish()
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