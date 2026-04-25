package com.jordan.budtrackertransactionst10382483

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btnViewGraphs = findViewById<Button>(R.id.btnViewGraphs)

        btnViewGraphs.setOnClickListener {
            // add here
            startActivity(Intent(this, GraphsActivity::class.java)) // add here
        }
    }
}