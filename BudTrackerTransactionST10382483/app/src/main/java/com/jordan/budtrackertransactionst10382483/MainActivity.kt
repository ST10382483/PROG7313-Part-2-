package com.jordan.budtrackertransactionst10382483

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Link to your XML layout
        setContentView(R.layout.activity_main)

        // ONLY button that exists in your XML
        val btnViewGraphs = findViewById<Button>(R.id.btnViewGraphs)

        // Open Graphs screen
        btnViewGraphs.setOnClickListener {
            val intent = Intent(this, GraphsActivity::class.java)
            startActivity(intent)
        }
    }
}