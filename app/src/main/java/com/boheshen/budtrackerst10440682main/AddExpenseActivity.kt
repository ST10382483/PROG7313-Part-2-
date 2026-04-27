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

        edtDate.setOnClickListener {
            showDatePicker()
        }

        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, imagePickerCode)
        }

        btnSaveExpense.setOnClickListener {
            saveExpense()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = String.format(
                    Locale.getDefault(),
                    "%02d/%02d/%04d",
                    day,
                    month + 1,
                    year
                )
                edtDate.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }