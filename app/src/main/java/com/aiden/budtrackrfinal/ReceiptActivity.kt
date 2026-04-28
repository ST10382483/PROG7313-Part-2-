package com.aiden.budtrackrfinal

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReceiptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        val imgReceiptFull = findViewById<ImageView>(R.id.imgReceiptFull)
        val btnClose = findViewById<Button>(R.id.btnClose)
        val btnCloseTop = findViewById<TextView>(R.id.btnCloseTop)

        val imagePath = intent.getStringExtra("imagePath")

        if (!imagePath.isNullOrEmpty()) {
            try {
                imgReceiptFull.setImageURI(Uri.parse(imagePath))
            } catch (e: Exception) {
                Toast.makeText(this, "Could not open receipt image", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No receipt image found", Toast.LENGTH_SHORT).show()
        }

        btnClose.setOnClickListener {
            finish()
        }

        btnCloseTop.setOnClickListener {
            finish()
        }
    }
}