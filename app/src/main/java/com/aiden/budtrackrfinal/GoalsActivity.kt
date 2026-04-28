package com.aiden.budtrackrfinal

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aiden.budtrackrfinal.data.AppDatabase
import com.aiden.budtrackrfinal.data.BudgetRepository
import kotlinx.coroutines.launch

class GoalsActivity : AppCompatActivity() {

    private var userId: Int = -1
    private lateinit var repo: BudgetRepository

    private lateinit var goalsContainer: LinearLayout
    private lateinit var emptyGoalsText: TextView
    private lateinit var budgetSummary: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)

        userId = intent.getIntExtra("userId", -1)

        repo = BudgetRepository(AppDatabase.getDatabase(this))

        val addGoal = findViewById<TextView>(R.id.btnAddGoal)
        val viewBudget = findViewById<TextView>(R.id.btnViewBudget)

        budgetSummary = findViewById(R.id.budgetSummary)
        goalsContainer = findViewById(R.id.goalsContainer)
        emptyGoalsText = findViewById(R.id.emptyGoalsText)

        addGoal.setOnClickListener {
            val intent = Intent(this, CreateGoalActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        viewBudget.setOnClickListener {
            showBudgetDialog()
        }

        loadGoals()
    }

    override fun onResume() {
        super.onResume()
        loadGoals()
    }

    private fun showBudgetDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_budget, null)

        val minInput = dialogView.findViewById<EditText>(R.id.minBudgetInput)
        val maxInput = dialogView.findViewById<EditText>(R.id.maxBudgetInput)
        val resultText = dialogView.findViewById<TextView>(R.id.budgetResult)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Monthly Budget")
            .setView(dialogView)
            .setPositiveButton("Calculate", null)
            .setNegativeButton("Close", null)
            .create()

        dialog.setOnShowListener {
            val calculateButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

            calculateButton.setOnClickListener {
                val min = minInput.text.toString().toDoubleOrNull()
                val max = maxInput.text.toString().toDoubleOrNull()

                if (min == null || max == null || max <= min) {
                    resultText.text = "Enter valid minimum and maximum amounts."
                    return@setOnClickListener
                }

                lifecycleScope.launch {
                    val goals = repo.getGoalsForUser(userId)
                    val spent = goals.sumOf { it.currentAmount }

                    val result = when {
                        spent > max -> "Over budget by R${spent - max}"
                        spent < min -> "Under minimum by R${min - spent}"
                        else -> "Within budget. R${max - spent} left before maximum."
                    }

                    resultText.text =
                        "Total Spent: R$spent\n" +
                                "Minimum Goal: R$min\n" +
                                "Maximum Goal: R$max\n\n" +
                                result

                    budgetSummary.text =
                        "Spent: R$spent\n" +
                                "Min: R$min | Max: R$max\n" +
                                result
                }
            }
        }

        dialog.show()
    }

    private fun loadGoals() {
        lifecycleScope.launch {
            val goals = repo.getGoalsForUser(userId)

            goalsContainer.removeAllViews()

            if (goals.isEmpty()) {
                emptyGoalsText.visibility = View.VISIBLE
            } else {
                emptyGoalsText.visibility = View.GONE
            }

            for (goal in goals) {
                addGoalCard(goal.goalId, goal.goalName, goal.goalType, goal.targetAmount, goal.currentAmount)
            }
        }
    }

    private fun addGoalCard(
        goalId: Int,
        name: String,
        type: String,
        target: Double,
        current: Double
    ) {
        var percentage = if (target > 0) ((current / target) * 100).toFloat() else 0f
        if (percentage > 100f) percentage = 100f

        val remaining = target - current

        val goalBox = LinearLayout(this)
        goalBox.orientation = LinearLayout.VERTICAL
        goalBox.setPadding(18, 18, 18, 18)
        goalBox.setBackgroundColor(Color.rgb(238, 242, 243))

        val topRow = LinearLayout(this)
        topRow.orientation = LinearLayout.HORIZONTAL

        val title = TextView(this)
        title.text = "$type\n$name\nCurrent: R$current\nLeft: R$remaining"
        title.textSize = 16f
        title.setTextColor(Color.rgb(100, 100, 100))
        title.layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )

        val deleteButton = TextView(this)
        deleteButton.text = "Delete"
        deleteButton.setTextColor(Color.rgb(239, 83, 80))
        deleteButton.textSize = 14f
        deleteButton.setPadding(10, 0, 0, 0)

        topRow.addView(title)
        topRow.addView(deleteButton)

        val bar = LinearLayout(this)
        bar.orientation = LinearLayout.HORIZONTAL
        bar.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            18
        )

        val green = View(this)
        green.layoutParams = LinearLayout.LayoutParams(0, 18, percentage)
        green.setBackgroundColor(Color.rgb(46, 204, 113))

        val red = View(this)
        red.layoutParams = LinearLayout.LayoutParams(0, 18, 100 - percentage)
        red.setBackgroundColor(Color.rgb(239, 83, 80))

        bar.addView(green)
        bar.addView(red)

        deleteButton.setOnClickListener {
            lifecycleScope.launch {
                repo.deleteGoal(goalId)
                Toast.makeText(this@GoalsActivity, "Goal deleted", Toast.LENGTH_SHORT).show()
                loadGoals()
            }
        }

        goalBox.addView(topRow)
        goalBox.addView(bar)

        val spacing = View(this)
        spacing.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            20
        )

        goalsContainer.addView(goalBox)
        goalsContainer.addView(spacing)
    }
}