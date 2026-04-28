package com.aiden.budtrackrfinal

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val btnHome = findViewById<Button>(R.id.btnHome)

        btnHome.setOnClickListener {
            finish()
        }
    }
}