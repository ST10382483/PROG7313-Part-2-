package com.jordan.budtrackertransactionst10382483

// This object acts as a temporary storage (like a simple database)
// "object" means there will only be ONE shared instance used across the whole app
object TransactionData {

    // This is a list that stores all transactions
    // mutableListOf means we can add, remove, and change items in the list
    val transactionList = mutableListOf<Transaction>()
}