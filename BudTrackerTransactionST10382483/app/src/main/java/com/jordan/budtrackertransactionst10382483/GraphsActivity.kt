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

        // Connects this Kotlin file to the XML layout (activity_graphs.xml)
        setContentView(R.layout.activity_graphs)

        // Link UI elements from XML to Kotlin code
        val pieChart = findViewById<PieChart>(R.id.pieChart)
        val btnAddMore = findViewById<Button>(R.id.btnAddMore)

        // Create a list to hold chart data (each slice of the pie chart)
        val entries = ArrayList<PieEntry>()

        // Adding sample data for the pie chart
        // Value = amount, Label = category name
        entries.add(PieEntry(500f, "Transport"))
        entries.add(PieEntry(300f, "Takeout"))
        entries.add(PieEntry(700f, "Groceries"))
        entries.add(PieEntry(200f, "Personal"))

        // Create a dataset from the entries
        val dataSet = PieDataSet(entries, "Expenses")

        // Set colours for the pie chart slices
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        // Style for numbers shown on the chart
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 14f

        // Attach dataset to the PieChart
        pieChart.data = PieData(dataSet)

        // Remove default description text under chart
        pieChart.description.isEnabled = false

        // Text shown in the middle of the pie chart
        pieChart.centerText = "Total"

        // Animation when chart loads
        pieChart.animateY(1000)

        // Button click (currently does nothing - placeholder)
        btnAddMore.setOnClickListener {
            // add here
        }
    }
}