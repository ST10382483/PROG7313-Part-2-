package com.boheshen.budtrackerst10440682main

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class CreateTransactionActivity : AppCompatActivity() {

    private lateinit var edtTitle: EditText
    private lateinit var edtAmount: EditText
    private lateinit var btnAddTransaction: Button

    private var selectedIcon = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)

        edtTitle = findViewById(R.id.edtTitle)
        edtAmount = findViewById(R.id.edtAmount)
        btnAddTransaction = findViewById(R.id.btnAddTransaction)

        setupIconButtons()

        btnAddTransaction.setOnClickListener {
            saveTransaction()
        }
    }

    private fun setupIconButtons() {
        findViewById<Button>(R.id.btnCar).setOnClickListener {
            selectedIcon = "🚗"
            Toast.makeText(this, "Transport selected", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnFood).setOnClickListener {
            selectedIcon = "🍽"
            Toast.makeText(this, "Food selected", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnShopping).setOnClickListener {
            selectedIcon = "🛒"
            Toast.makeText(this, "Shopping selected", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnBag).setOnClickListener {
            selectedIcon = "🛍"
            Toast.makeText(this, "Bag selected", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnMoney).setOnClickListener {
            selectedIcon = "💵"
            Toast.makeText(this, "Money selected", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnHome).setOnClickListener {
            selectedIcon = "🏠"
            Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show()
        }
    }