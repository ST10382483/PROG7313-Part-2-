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