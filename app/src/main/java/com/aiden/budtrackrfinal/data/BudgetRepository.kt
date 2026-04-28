package com.aiden.budtrackrfinal.data

class BudgetRepository(private val db: AppDatabase) {

    suspend fun registerUser(user: User) {
        db.userDao().insertUser(user)
    }

    suspend fun loginUser(username: String, password: String): User? {
        return db.userDao().login(username, password)
    }

    suspend fun updatePassword(userId: Int, newPassword: String) {
        db.userDao().updatePassword(userId, newPassword)
    }

    suspend fun deleteUser(userId: Int) {
        db.userDao().deleteUserById(userId)
    }

    suspend fun addCategory(category: Category) {
        db.categoryDao().insertCategory(category)
    }

    suspend fun getCategories(): List<Category> {
        return db.categoryDao().getAllCategories()
    }

    suspend fun addExpense(expense: Expense) {
        db.expenseDao().insertExpense(expense)
    }

    suspend fun getExpensesForUser(userId: Int): List<Expense> {
        return db.expenseDao().getExpensesForUser(userId)
    }

    suspend fun updateExpense(expense: Expense) {
        db.expenseDao().updateExpense(expense)
    }

    suspend fun deleteExpense(expenseId: Int) {
        db.expenseDao().deleteExpense(expenseId)
    }

    suspend fun setMonthlyGoal(goal: MonthlyGoal) {
        db.monthlyGoalDao().insertGoal(goal)
    }

    suspend fun getMonthlyGoalForUser(userId: Int): MonthlyGoal? {
        return db.monthlyGoalDao().getLatestGoalForUser(userId)
    }

    suspend fun addGoal(goal: Goal) {
        db.goalDao().insertGoal(goal)
    }

    suspend fun getGoalsForUser(userId: Int): List<Goal> {
        return db.goalDao().getGoalsForUser(userId)
    }

    suspend fun deleteGoal(goalId: Int) {
        db.goalDao().deleteGoal(goalId)
    }
}