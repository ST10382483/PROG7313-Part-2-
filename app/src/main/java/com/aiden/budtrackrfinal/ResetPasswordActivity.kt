package com.aiden.budtrackrfinal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aiden.budtrackrfinal.data.AppDatabase
import com.aiden.budtrackrfinal.data.BudgetRepository
import kotlinx.coroutines.launch

class ResetPasswordActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        userId = intent.getIntExtra("userId", -1)
        username = intent.getStringExtra("username") ?: ""

        val currentPasswordInput = findViewById<EditText>(R.id.emailInput)
        val resetBtn = findViewById<Button>(R.id.resetAccountBtn)

        currentPasswordInput.hint = "Enter Current Password"

        val repo = BudgetRepository(AppDatabase.getDatabase(this))

        resetBtn.setOnClickListener {
            val currentPassword = currentPasswordInput.text.toString().trim()

            if (currentPassword.isEmpty()) {
                Toast.makeText(this, "Please enter current password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = repo.loginUser(username, currentPassword)

                if (user != null && user.userId == userId) {
                    repo.updatePassword(userId, "1234")
                    Toast.makeText(this@ResetPasswordActivity, "Password reset to 1234", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this@ResetPasswordActivity, "Incorrect password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
