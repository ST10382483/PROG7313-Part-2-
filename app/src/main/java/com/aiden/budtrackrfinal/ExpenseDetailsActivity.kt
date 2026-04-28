package com.aiden.budtrackrfinal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aiden.budtrackrfinal.data.AppDatabase
import com.aiden.budtrackrfinal.data.Expense
import kotlinx.coroutines.launch

class ExpenseDetailsActivity : AppCompatActivity() {

    private lateinit var expense: Expense
    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)

        expense = intent.getSerializableExtra("expense") as Expense

        val btnBack = findViewById<TextView>(R.id.btnBack)
        val txtDate = findViewById<TextView>(R.id.txtDate)
        val txtCategory = findViewById<TextView>(R.id.txtCategory)
        val txtDescription = findViewById<TextView>(R.id.txtDescription)
        val txtAmount = findViewById<TextView>(R.id.txtAmount)
        val txtAddedOn = findViewById<TextView>(R.id.txtAddedOn)
        val imgReceipt = findViewById<ImageView>(R.id.imgReceipt)
        val btnBackToList = findViewById<Button>(R.id.btnBackToList)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        txtDate.text = "Date: ${expense.date}"
        txtCategory.text = "Category: ${expense.categoryName}"
        txtDescription.text = "Description: ${expense.description}"
        txtAmount.text = "Amount: R %.2f".format(expense.amount)
        txtAddedOn.text = "Added on: ${expense.addedOn}"

        if (!expense.imagePath.isNullOrEmpty()) {
            try {
                val uri = Uri.parse(expense.imagePath)
                imgReceipt.setImageURI(uri)

                imgReceipt.setOnClickListener {
                    val intent = Intent(this, ReceiptActivity::class.java)
                    intent.putExtra("imagePath", expense.imagePath)
                    startActivity(intent)
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Could not load receipt image", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener { finish() }
        btnBackToList.setOnClickListener { finish() }

        btnDelete.setOnClickListener {
            lifecycleScope.launch {
                db.expenseDao().deleteExpense(expense.expenseId)
                Toast.makeText(this@ExpenseDetailsActivity, "Expense deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}