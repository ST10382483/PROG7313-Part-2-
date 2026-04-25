package com.jordan.budtrackertransactionst10382483

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateTransactionActivity : AppCompatActivity() {

    private var selectedCategory: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)

        // Inputs
        val edtTitle = findViewById<EditText>(R.id.edtTitle)
        val edtAmount = findViewById<EditText>(R.id.edtAmount)

        // Buttons (categories)
        val btnTransport = findViewById<Button>(R.id.btnTransport)
        val btnGroceries = findViewById<Button>(R.id.btnGroceries)
        val btnTakeout = findViewById<Button>(R.id.btnTakeout)
        val btnPersonal = findViewById<Button>(R.id.btnPersonal)
        val btnBills = findViewById<Button>(R.id.btnBills)
        val btnOther = findViewById<Button>(R.id.btnOther)

        // Main button
        val btnAddTransaction = findViewById<Button>(R.id.btnAddTransaction)

        // CATEGORY CLICK HANDLER
        fun resetButtons() {
            val defaultColor = Color.LTGRAY
            btnTransport.setBackgroundColor(defaultColor)
            btnGroceries.setBackgroundColor(defaultColor)
            btnTakeout.setBackgroundColor(defaultColor)
            btnPersonal.setBackgroundColor(defaultColor)
            btnBills.setBackgroundColor(defaultColor)
            btnOther.setBackgroundColor(defaultColor)
        }

        fun selectCategory(button: Button, category: String) {
            resetButtons()
            button.setBackgroundColor(Color.parseColor("#1976D2"))
            selectedCategory = category
        }

        btnTransport.setOnClickListener {
            selectCategory(btnTransport, "Transport")
        }

        btnGroceries.setOnClickListener {
            selectCategory(btnGroceries, "Groceries")
        }

        btnTakeout.setOnClickListener {
            selectCategory(btnTakeout, "Takeout")
        }

        btnPersonal.setOnClickListener {
            selectCategory(btnPersonal, "Personal")
        }

        btnBills.setOnClickListener {
            selectCategory(btnBills, "Bills")
        }

        btnOther.setOnClickListener {
            selectCategory(btnOther, "Other")
        }

        // ADD TRANSACTION BUTTON
        btnAddTransaction.setOnClickListener {

            val title = edtTitle.text.toString()
            val amount = edtAmount.text.toString()

            if (title.isEmpty() || amount.isEmpty() || selectedCategory.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please fill all fields and select a category",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Transaction Added: $selectedCategory - $title - R$amount",
                    Toast.LENGTH_SHORT
                ).show()

                // add here
            }
        }
    }
}