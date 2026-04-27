package com.boheshen.budtrackerst10440682main

import java.io.Serializable

data class Expense(
    val date: String,
    val category: String,
    val description: String,
    val amount: Double,
    val addedOn: String,
    val imageUri: String?
) : Serializable