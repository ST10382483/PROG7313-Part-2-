package com.boheshen.budtrackerst10440682main

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

        val imgFullReceipt = findViewById<ImageView>(R.id.imgFullReceipt)
        val btnClose = findViewById<Button>(R.id.btnClose)
        val btnCloseTop = findViewById<TextView>(R.id.btnCloseTop)

        val imageUri = intent.getStringExtra("imageUri")

        if (!imageUri.isNullOrEmpty() && imageUri != "null") {
            imgFullReceipt.setImageURI(Uri.parse(imageUri))
        } else {
            Toast.makeText(this, "No receipt image was added", Toast.LENGTH_SHORT).show()
        }

        btnClose.setOnClickListener {
            finish()
        }

        btnCloseTop.setOnClickListener {
            finish()
        }
    }
}