package com.jordan.budtrackertransactionst10382483

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateTransactionActivity : AppCompatActivity() {

    // Stores which category the user selects
    private var selectedCategory: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connects Kotlin file to XML layout
        setContentView(R.layout.activity_create_transaction)

        // Input fields for transaction details
        val edtTitle = findViewById<EditText>(R.id.edtTitle)
        val edtAmount = findViewById<EditText>(R.id.edtAmount)

        // Category buttons
        val btnTransport = findViewById<Button>(R.id.btnTransport)
        val btnGroceries = findViewById<Button>(R.id.btnGroceries)
        val btnTakeout = findViewById<Button>(R.id.btnTakeout)
        val btnPersonal = findViewById<Button>(R.id.btnPersonal)
        val btnBills = findViewById<Button>(R.id.btnBills)
        val btnOther = findViewById<Button>(R.id.btnOther)

        // Main save button
        val btnAddTransaction = findViewById<Button>(R.id.btnAddTransaction)

        // Resets all category button colours (used when selecting a new category)
        fun resetButtons() {
            val defaultColor = Color.LTGRAY

            btnTransport.setBackgroundColor(defaultColor)
            btnGroceries.setBackgroundColor(defaultColor)
            btnTakeout.setBackgroundColor(defaultColor)
            btnPersonal.setBackgroundColor(defaultColor)
            btnBills.setBackgroundColor(defaultColor)
            btnOther.setBackgroundColor(defaultColor)
        }

        // Handles category selection and highlights the chosen button
        fun selectCategory(button: Button, category: String) {
            resetButtons()
            button.setBackgroundColor(Color.parseColor("#1976D2"))
            selectedCategory = category
        }

        // Category button click listeners
        btnTransport.setOnClickListener { selectCategory(btnTransport, "Transport") }
        btnGroceries.setOnClickListener { selectCategory(btnGroceries, "Groceries") }
        btnTakeout.setOnClickListener { selectCategory(btnTakeout, "Takeout") }
        btnPersonal.setOnClickListener { selectCategory(btnPersonal, "Personal") }
        btnBills.setOnClickListener { selectCategory(btnBills, "Bills") }
        btnOther.setOnClickListener { selectCategory(btnOther, "Other") }

        // Save transaction button logic
        btnAddTransaction.setOnClickListener {

            // Read user input
            val title = edtTitle.text.toString()
            val amount = edtAmount.text.toString()

            // Validate input fields
            if (title.isEmpty() || amount.isEmpty() || selectedCategory.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please fill all fields and select a category",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                // Create new Transaction object
                val newTransaction = Transaction(
                    category = selectedCategory,
                    title = title,
                    amount = amount.toDouble()
                )

                // Save transaction into shared list
                TransactionData.transactionList.add(newTransaction)

                // Clear input fields after saving
                edtTitle.text.clear()
                edtAmount.text.clear()
                selectedCategory = ""

                Toast.makeText(
                    this,
                    "Saved successfully!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}