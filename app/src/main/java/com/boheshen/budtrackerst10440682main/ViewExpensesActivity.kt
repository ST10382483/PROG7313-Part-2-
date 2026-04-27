package com.boheshen.budtrackerst10440682main

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class ViewExpensesActivity : AppCompatActivity() {

    private lateinit var edtStartDate: EditText
    private lateinit var edtEndDate: EditText
    private lateinit var btnFilter: Button
    private lateinit var btnReset: Button
    private lateinit var btnAddExpense: Button
    private lateinit var txtTotalExpenses: TextView
    private lateinit var txtTransactions: TextView
    private lateinit var txtPeriod: TextView
    private lateinit var expenseContainer: LinearLayout