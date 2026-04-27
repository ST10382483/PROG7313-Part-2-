package com.aiden.budtrackr_st10441232

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreateGoalActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_goal)

        val name = findViewById<EditText>(R.id.goalName)
        val target = findViewById<EditText>(R.id.goalTarget)
        val current = findViewById<EditText>(R.id.goalCurrent)
        val saveBtn = findViewById<Button>(R.id.saveGoalBtn)

        saveBtn.setOnClickListener {
            if (
                name.text.toString().isEmpty() ||
                target.text.toString().isEmpty() ||
                current.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}