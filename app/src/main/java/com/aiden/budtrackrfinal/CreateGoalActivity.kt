package com.aiden.budtrackrfinal

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aiden.budtrackrfinal.data.AppDatabase
import com.aiden.budtrackrfinal.data.BudgetRepository
import com.aiden.budtrackrfinal.data.Goal
import kotlinx.coroutines.launch

class CreateGoalActivity : AppCompatActivity() {

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_goal)

        userId = intent.getIntExtra("userId", -1)

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

        val repo = BudgetRepository(AppDatabase.getDatabase(this))

        saveBtn.setOnClickListener {
            val goalName = name.text.toString().trim()
            val goalType = typeSpinner.selectedItem.toString()
            val goalTarget = target.text.toString().trim().toDoubleOrNull()
            val goalCurrent = current.text.toString().trim().toDoubleOrNull()

            if (userId == -1) {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (goalName.isEmpty() || goalTarget == null || goalCurrent == null) {
                Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                repo.addGoal(
                    Goal(
                        userId = userId,
                        goalName = goalName,
                        goalType = goalType,
                        targetAmount = goalTarget,
                        currentAmount = goalCurrent
                    )
                )

                Toast.makeText(this@CreateGoalActivity, "Goal saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}