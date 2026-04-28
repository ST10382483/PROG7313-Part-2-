package com.aiden.budtrackrfinal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aiden.budtrackrfinal.data.AppDatabase
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.launch

class GraphsActivity : AppCompatActivity() {

    private var userId: Int = -1
    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphs)

        userId = intent.getIntExtra("userId", -1)

        val pieChart = findViewById<PieChart>(R.id.pieChart)
        val btnAddMore = findViewById<Button>(R.id.btnAddMore)
        val txtNoData = findViewById<TextView>(R.id.txtNoData)

        val txtTransport = findViewById<TextView>(R.id.txtTransport)
        val txtTakeout = findViewById<TextView>(R.id.txtTakeout)
        val txtGroceries = findViewById<TextView>(R.id.txtGroceries)
        val txtPersonal = findViewById<TextView>(R.id.txtPersonal)

        lifecycleScope.launch {
            val expenses = db.expenseDao().getExpensesForUser(userId)

            val totals = expenses
                .groupBy { it.categoryName.trim() }
                .mapValues { entry -> entry.value.sumOf { it.amount } }

            val transport = totals["Transport"] ?: 0.0
            val takeout = totals["Takeout"] ?: 0.0
            val groceries = totals["Groceries"] ?: 0.0
            val personal = totals["Personal"] ?: totals["Personal Shopping"] ?: 0.0

            txtTransport.text = "R %.2f".format(transport)
            txtTakeout.text = "R %.2f".format(takeout)
            txtGroceries.text = "R %.2f".format(groceries)
            txtPersonal.text = "R %.2f".format(personal)

            val entries = ArrayList<PieEntry>()

            for ((category, total) in totals) {
                if (total > 0) {
                    entries.add(PieEntry(total.toFloat(), category))
                }
            }

            if (entries.isEmpty()) {
                txtNoData.visibility = View.VISIBLE
                entries.add(PieEntry(1f, "No expenses"))
            } else {
                txtNoData.visibility = View.GONE
            }

            val dataSet = PieDataSet(entries, "Expenses")
            dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
            dataSet.valueTextColor = Color.BLACK
            dataSet.valueTextSize = 14f

            pieChart.data = PieData(dataSet)
            pieChart.description.isEnabled = false
            pieChart.centerText = "Total"
            pieChart.animateY(1000)
            pieChart.invalidate()
        }

        btnAddMore.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }
}