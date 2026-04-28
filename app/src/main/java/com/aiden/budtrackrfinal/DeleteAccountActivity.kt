package com.aiden.budtrackrfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aiden.budtrackrfinal.data.AppDatabase
import com.aiden.budtrackrfinal.data.BudgetRepository
import kotlinx.coroutines.launch

class DeleteAccountActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account)

        userId = intent.getIntExtra("userId", -1)
        username = intent.getStringExtra("username") ?: ""

        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val backHomeBtn = findViewById<Button>(R.id.backHomeBtn)
        val deleteAccountBtn = findViewById<Button>(R.id.deleteAccountBtn)

        val repo = BudgetRepository(AppDatabase.getDatabase(this))

        backHomeBtn.setOnClickListener {
            finish()
        }

        deleteAccountBtn.setOnClickListener {
            val password = passwordInput.text.toString().trim()

            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = repo.loginUser(username, password)

                if (user != null && user.userId == userId) {
                    repo.deleteUser(userId)

                    Toast.makeText(this@DeleteAccountActivity, "Account deleted successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@DeleteAccountActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this@DeleteAccountActivity, "Incorrect password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}