package com.jordan.budtrackertransactionst10382483

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class GraphsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphs)

        val pieChart = findViewById<PieChart>(R.id.pieChart)
        val btnAddMore = findViewById<Button>(R.id.btnAddMore)

        // Sample data (you will replace this later)
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(500f, "Transport"))
        entries.add(PieEntry(300f, "Takeout"))
        entries.add(PieEntry(700f, "Groceries"))
        entries.add(PieEntry(200f, "Shopping"))

        val dataSet = PieDataSet(entries, "Expenses")
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 14f

        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.centerText = "Total\nR1700"
        pieChart.animateY(1000)

        // Button does nothing for now
        btnAddMore.setOnClickListener {
            // Future feature
        }
    }
}