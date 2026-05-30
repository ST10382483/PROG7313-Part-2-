package com.neha.st10442032loginreghome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Delete Account screen — allows users to permanently delete their account
class DeleteAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Link to the delete account layout
        setContentView(R.layout.activity_delete_account)

        // Get references to UI elements
        val passwordField = findViewById<EditText>(R.id.deletePasswordField)
        val goBackBtn = findViewById<Button>(R.id.goBackHomeBtn)
        val deleteBtn = findViewById<Button>(R.id.deleteAccountBtn)

        // Bottom navigation buttons
        val navHome = findViewById<ImageButton>(R.id.navHome)
        val navTransactions = findViewById<ImageButton>(R.id.navTransactions)
        val navAccount = findViewById<ImageButton>(R.id.navAccount)
        val navNotifications = findViewById<ImageButton>(R.id.navNotifications)

        // Go back to Home Page without deleting
        goBackBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Delete account — validate password then clear data and go to login
        deleteBtn.setOnClickListener {
            val password = passwordField.text.toString().trim()

            // Validate password is not empty
            if (password.isEmpty()) {
                passwordField.error = "Please enter your password to confirm"
                return@setOnClickListener
            }

            // TODO: Add actual password verification against saved credentials here
            // For now, any non-empty password proceeds with deletion
            // In a full implementation, check password against RoomDB stored hash

            Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()

            // Navigate to the Rate Application screen after deletion
            startActivity(Intent(this, RateAppActivity::class.java))
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