package com.aiden.budtrackr_st10441232

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class HelpActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val homeBtn = findViewById<Button>(R.id.btnHome)

        homeBtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}