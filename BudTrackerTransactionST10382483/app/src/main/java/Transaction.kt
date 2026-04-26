package com.jordan.budtrackertransactionst10382483

// This is a data class used to represent a single transaction
// A data class is useful for storing and managing structured data
data class Transaction(

    // The category of the transaction (e.g., Transport, Groceries, etc.)
    val category: String,

    // The title or description of the transaction (e.g., "Taxi", "Lunch")
    val title: String,

    // The amount of money for the transaction
    // Double is used because it allows decimal values (e.g., 50.75)
    val amount: Double
)