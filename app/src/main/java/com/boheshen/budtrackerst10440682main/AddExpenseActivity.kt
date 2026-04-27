package com.boheshen.budtrackerst10440682main

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var edtDate: EditText
    private lateinit var edtCategory: EditText
    private lateinit var edtDescription: EditText
    private lateinit var edtAmount: EditText
    private lateinit var btnSelectImage: Button
    private lateinit var btnSaveExpense: Button
    private lateinit var imgPreview: ImageView

    private var selectedImageUri: Uri? = null

    private val imagePickerCode = 100
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        edtDate = findViewById(R.id.edtDate)
        edtCategory = findViewById(R.id.edtCategory)
        edtDescription = findViewById(R.id.edtDescription)
        edtAmount = findViewById(R.id.edtAmount)
        btnSelectImage = findViewById(R.id.btnSelectImage)
        btnSaveExpense = findViewById(R.id.btnSaveExpense)
        imgPreview = findViewById(R.id.imgPreview)