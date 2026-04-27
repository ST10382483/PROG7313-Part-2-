package com.aiden.budtrackr_st10441232

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class CreateGoalActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_goal)

        val name = findViewById<EditText>(R.id.goalName)
        val typeSpinner = findViewById<Spinner>(R.id.goalTypeSpinner)
        val target = findViewById<EditText>(R.id.goalTarget)
        val current = findViewById<EditText>(R.id.goalCurrent)
        val saveBtn = findViewById<Button>(R.id.saveGoalBtn)

        val goalTypes = arrayOf("Special Goal", "Smart Goal", "Consumer Goal")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            goalTypes
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = adapter

        saveBtn.setOnClickListener {
            if (
                name.text.toString().isEmpty() ||
                target.text.toString().isEmpty() ||
                current.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, GoalsActivity::class.java)
                intent.putExtra("name", name.text.toString())
                intent.putExtra("type", typeSpinner.selectedItem.toString())
                intent.putExtra("target", target.text.toString())
                intent.putExtra("current", current.text.toString())
                startActivity(intent)
            }
        }
    }
}