package com.aiden.budtrackr_st10441232

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView

class SettingsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val goals = findViewById<TextView>(R.id.btnGoals)

        goals.setOnClickListener {
            startActivity(Intent(this, GoalsActivity::class.java))
        }
    }
}