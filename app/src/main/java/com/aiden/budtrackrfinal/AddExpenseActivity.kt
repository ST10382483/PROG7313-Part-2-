package com.aiden.budtrackrfinal

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.aiden.budtrackrfinal.data.AppDatabase
import com.aiden.budtrackrfinal.data.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private lateinit var db: AppDatabase
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        db = AppDatabase.getDatabase(this)
        userId = intent.getIntExtra("userId", -1)

        edtDate = findViewById(R.id.edtDate)
        edtCategory = findViewById(R.id.edtCategory)
        edtDescription = findViewById(R.id.edtDescription)
        edtAmount = findViewById(R.id.edtAmount)
        btnSelectImage = findViewById(R.id.btnSelectImage)
        btnSaveExpense = findViewById(R.id.btnSaveExpense)
        imgPreview = findViewById(R.id.imgPreview)

        edtDate.setOnClickListener { showDatePicker() }

        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            startActivityForResult(intent, imagePickerCode)
        }

        btnSaveExpense.setOnClickListener { saveExpense() }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                edtDate.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveExpense() {
        val date = edtDate.text.toString()
        val category = edtCategory.text.toString()
        val description = edtDescription.text.toString()
        val amountText = edtAmount.text.toString()

        if (date.isEmpty() || category.isEmpty() || description.isEmpty() || amountText.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val expense = Expense(
            userId = userId,
            date = date,
            description = description,
            amount = amountText.toDouble(),
            categoryName = category,
            addedOn = date,
            imagePath = selectedImageUri?.toString()
        )

        CoroutineScope(Dispatchers.IO).launch {
            db.expenseDao().insertExpense(expense)

            runOnUiThread {
                Toast.makeText(this@AddExpenseActivity, "Expense saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == imagePickerCode && resultCode == RESULT_OK) {
            selectedImageUri = data?.data

            selectedImageUri?.let { uri ->
                try {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (_: Exception) {
                }

                imgPreview.setImageURI(uri)
            }
        }
    }
}