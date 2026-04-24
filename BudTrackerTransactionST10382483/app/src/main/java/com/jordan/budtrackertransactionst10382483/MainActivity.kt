package com.jordan.budtrackertransactionst10382483

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect to XML layout
        setContentView(R.layout.activity_main)

        // Buttons (we will use later)
        val btnAddTransaction = findViewById<Button>(R.id.btnAddTransaction)
        val btnViewTransactions = findViewById<Button>(R.id.btnViewTransactions)

        btnAddTransaction.setOnClickListener {
            // TODO: Add Transaction Page
        }

        btnViewTransactions.setOnClickListener {
            // TODO: View Transactions Page
        }
    }
}