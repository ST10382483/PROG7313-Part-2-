package com.neha.st10442032loginreghome

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Rewards screen — shows the user their savings achievement level (gamification)
class RewardsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Link to the rewards screen layout
        setContentView(R.layout.activity_rewards)

        // Get references to UI elements
        val levelTitle = findViewById<TextView>(R.id.rewardLevelTitle)
        val levelDescription = findViewById<TextView>(R.id.rewardDescription)

        // Bottom navigation buttons
        val navHome = findViewById<ImageButton>(R.id.navHome)
        val navTransactions = findViewById<ImageButton>(R.id.navTransactions)
        val navAccount = findViewById<ImageButton>(R.id.navAccount)
        val navNotifications = findViewById<ImageButton>(R.id.navNotifications)

        // Get the total saved amount passed from the Notifications screen
        val totalSaved = intent.getDoubleExtra("TOTAL_SAVED", 0.0)

        // Determine which reward level the user has reached based on savings
        when {
            totalSaved >= 100000 -> {
                levelTitle.text = "GOLD LEVEL"
                levelDescription.text = "YOU HAVE SAVED OVER\nR 100 000 SINCE USING THE APP!"
            }
            totalSaved >= 50000 -> {
                levelTitle.text = "SILVER LEVEL"
                levelDescription.text = "YOU HAVE SAVED OVER\nR 50 000 SINCE USING THE APP!"
            }
            else -> {
                // Default to Bronze level
                levelTitle.text = "BRONZE LEVEL"
                levelDescription.text = "YOU HAVE SAVED OVER\nR 30 000 SINCE USING THE APP!"
            }
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
            finish() // Go back to notifications
        }
    }
}