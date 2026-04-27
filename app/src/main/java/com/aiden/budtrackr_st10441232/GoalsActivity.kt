package com.aiden.budtrackr_st10441232

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class GoalsActivity : Activity() {

    private val goalList = mutableListOf<GoalItem>()

    data class GoalItem(
        var name: String,
        var type: String,
        var target: Float,
        var current: Float
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)

        val addGoal = findViewById<TextView>(R.id.btnAddGoal)
        val viewBudget = findViewById<TextView>(R.id.btnViewBudget)
        val budgetSummary = findViewById<TextView>(R.id.budgetSummary)
        val container = findViewById<LinearLayout>(R.id.goalsContainer)
        val emptyText = findViewById<TextView>(R.id.emptyGoalsText)

        loadGoals()

        val newName = intent.getStringExtra("name")
        val newType = intent.getStringExtra("type")
        val newTarget = intent.getStringExtra("target")?.toFloatOrNull()
        val newCurrent = intent.getStringExtra("current")?.toFloatOrNull()

        if (!newName.isNullOrEmpty() && !newType.isNullOrEmpty() && newTarget != null && newCurrent != null) {
            goalList.add(GoalItem(newName, newType, newTarget, newCurrent))
            saveGoals()
        }

        displayGoals(container, emptyText)

        addGoal.setOnClickListener {
            startActivity(Intent(this, CreateGoalActivity::class.java))
        }

        viewBudget.setOnClickListener {
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
                    val min = minInput.text.toString().toFloatOrNull()
                    val max = maxInput.text.toString().toFloatOrNull()
                    val spent = goalList.sumOf { it.current.toDouble() }.toFloat()

                    if (min == null || max == null || max <= min) {
                        resultText.text = "Enter valid minimum and maximum amounts."
                        return@setOnClickListener
                    }

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

            dialog.show()
        }
    }

    private fun displayGoals(container: LinearLayout, emptyText: TextView) {
        container.removeAllViews()

        if (goalList.isEmpty()) {
            emptyText.visibility = View.VISIBLE
        } else {
            emptyText.visibility = View.GONE
        }

        for (i in goalList.indices) {
            addGoalCard(container, emptyText, i)
        }
    }

    private fun addGoalCard(container: LinearLayout, emptyText: TextView, index: Int) {
        val goal = goalList[index]

        var percentage = (goal.current / goal.target) * 100
        if (percentage > 100) percentage = 100f

        val remaining = goal.target - goal.current

        val goalBox = LinearLayout(this)
        goalBox.orientation = LinearLayout.VERTICAL
        goalBox.setPadding(18, 18, 18, 18)
        goalBox.setBackgroundColor(Color.rgb(238, 242, 243))

        val title = TextView(this)
        title.text = "${goal.type}\n${goal.name}\nCurrent: R${goal.current}\nLeft: R$remaining"
        title.textSize = 16f
        title.setTextColor(Color.rgb(100, 100, 100))

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

        goalBox.addView(title)
        goalBox.addView(bar)

        val spacing = View(this)
        spacing.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            20
        )

        container.addView(goalBox)
        container.addView(spacing)
    }

    private fun saveGoals() {
        val prefs = getSharedPreferences("GoalPrefs", MODE_PRIVATE)

        val savedText = goalList.joinToString("|") {
            "${it.name},${it.type},${it.target},${it.current}"
        }

        prefs.edit().putString("goals", savedText).apply()
    }

    private fun loadGoals() {
        val prefs = getSharedPreferences("GoalPrefs", MODE_PRIVATE)
        val savedText = prefs.getString("goals", "") ?: ""

        goalList.clear()

        if (savedText.isNotEmpty()) {
            val goals = savedText.split("|")

            for (goal in goals) {
                val parts = goal.split(",")

                if (parts.size == 4) {
                    val name = parts[0]
                    val type = parts[1]
                    val target = parts[2].toFloatOrNull()
                    val current = parts[3].toFloatOrNull()

                    if (target != null && current != null) {
                        goalList.add(GoalItem(name, type, target, current))
                    }
                }
            }
        }
    }
}