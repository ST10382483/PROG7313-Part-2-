package com.boheshen.budtrackerst10440682main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import kotlin.text.category

class ExpenseDetailsActivity : AppCompatActivity() {

    private lateinit var expense: Expense
    private lateinit var imgReceipt: ImageView
    private lateinit var btnAddReceipt: Button

    private val imagePickerCode = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)

        expense = intent.getSerializableExtra("expense") as Expense

        findViewById<TextView>(R.id.txtDate).text = "Date                         ${expense.date}"
        findViewById<TextView>(R.id.txtCategory).text = "Category                  ${expense.category}"
        findViewById<TextView>(R.id.txtDescription).text = "Description              ${expense.description}"
        findViewById<TextView>(R.id.txtAmount).text = "Amount                    R${"%.2f".format(expense.amount)}"
        findViewById<TextView>(R.id.txtAddedOn).text = "Added On                 ${expense.addedOn}"

        imgReceipt = findViewById(R.id.imgReceipt)
        btnAddReceipt = findViewById(R.id.btnAddReceipt)

        if (!expense.imageUri.isNullOrEmpty() && expense.imageUri != "null") {
            imgReceipt.setImageURI(Uri.parse(expense.imageUri))
            btnAddReceipt.visibility = View.GONE
        } else {
            btnAddReceipt.visibility = View.VISIBLE
        }

        btnAddReceipt.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, imagePickerCode)
        }

        imgReceipt.setOnClickListener {
            if (!expense.imageUri.isNullOrEmpty() && expense.imageUri != "null") {
                val intent = Intent(this, ReceiptActivity::class.java)
                intent.putExtra("imageUri", expense.imageUri)
                startActivity(intent)
            }
        }

        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnBackToList).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            deleteExpense()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == imagePickerCode && resultCode == RESULT_OK) {
            val uri = data?.data

            uri?.let {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                imgReceipt.setImageURI(it)
                btnAddReceipt.visibility = View.GONE
                updateReceiptImage(it.toString())

                Toast.makeText(this, "Receipt added successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateReceiptImage(imageUri: String) {
        val prefs = getSharedPreferences("ExpenseData", MODE_PRIVATE)
        val json = prefs.getString("expenses", "[]")
        val array = JSONArray(json)

        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)

            if (
                obj.getString("date") == expense.date &&
                obj.getString("category") == expense.category &&
                obj.getString("description") == expense.description &&
                obj.getDouble("amount") == expense.amount &&
                obj.getString("addedOn") == expense.addedOn
            ) {
                obj.put("imageUri", imageUri)
                break
            }
        }

        prefs.edit()
            .putString("expenses", array.toString())
            .apply()
    }

    private fun deleteExpense() {
        val prefs = getSharedPreferences("ExpenseData", MODE_PRIVATE)
        val json = prefs.getString("expenses", "[]")
        val array = JSONArray(json)
        val updatedArray = JSONArray()

        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)

            val isSameExpense =
                obj.getString("date") == expense.date &&
                        obj.getString("category") == expense.category &&
                        obj.getString("description") == expense.description &&
                        obj.getDouble("amount") == expense.amount &&
                        obj.getString("addedOn") == expense.addedOn

            if (!isSameExpense) {
                updatedArray.put(obj)
            }
        }

        prefs.edit()
            .putString("expenses", updatedArray.toString())
            .apply()

        Toast.makeText(this, "Expense deleted", Toast.LENGTH_SHORT).show()
        finish()
    }
}