package com.darsh.roomdb_st10442633

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

import com.darsh.roomdb_st10442633.models.*
import com.darsh.roomdb_st10442633.dao.*

@RunWith(AndroidJUnit4::class)
class DaoTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var expensesDao: ExpensesDao
    private lateinit var budgetGoalDao: BudgetGoalDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
        categoryDao = database.categoryDao()
        expensesDao = database.expensesDao()
        budgetGoalDao = database.budgetGoalDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    //USER DAO TESTS

    @Test
    fun testInsertAndGetUser() = runBlocking {
        val user = User(
            username = "testuser",
            password = "password123",
            email = "test@example.com"
        )

        val userId = userDao.insertUser(user)
        assertTrue(userId > 0)

        val retrievedUser = userDao.getUserById(userId)
        assertNotNull(retrievedUser)
        assertEquals("testuser", retrievedUser?.username)
        assertEquals("password123", retrievedUser?.password)
    }

    @Test
    fun testLogin() = runBlocking {
        userDao.insertUser(User(username = "loginuser", password = "pass123"))

        val loggedInUser = userDao.login("loginuser", "pass123")
        assertNotNull(loggedInUser)
        assertEquals("loginuser", loggedInUser?.username)

        val failedLogin = userDao.login("loginuser", "wrongpass")
        assertNull(failedLogin)
    }

    @Test
    fun testUpdateUser() = runBlocking {
        val user = User(username = "updateuser", password = "oldpass")
        val userId = userDao.insertUser(user)

        val updatedUser = user.copy(password = "newpass", email = "new@email.com")
        userDao.updateUser(updatedUser)

        val retrievedUser = userDao.getUserById(userId)
        assertEquals("newpass", retrievedUser?.password)
        assertEquals("new@email.com", retrievedUser?.email)
    }

    @Test
    fun testDeleteUser() = runBlocking {
        val user = User(username = "deleteuser", password = "pass123")
        val userId = userDao.insertUser(user)

        userDao.deleteUserById(userId)

        val deletedUser = userDao.getUserById(userId)
        assertNull(deletedUser)
    }

    @Test
    fun testGetAllUsers() = runBlocking {
        userDao.insertUser(User(username = "user1", password = "pass1"))
        userDao.insertUser(User(username = "user2", password = "pass2"))
        userDao.insertUser(User(username = "user3", password = "pass3"))

        val users = userDao.getAllUsers().first()

        assertEquals(3, users.size)
        assertTrue(users.any { it.username == "user1" })
        assertTrue(users.any { it.username == "user2" })
        assertTrue(users.any { it.username == "user3" })
    }

    @Test
    fun testIsUsernameExists() = runBlocking {
        userDao.insertUser(User(username = "existinguser", password = "pass"))

        val exists = userDao.isUsernameExists("existinguser")
        assertTrue(exists)

        val notExists = userDao.isUsernameExists("nonexistent")
        assertFalse(notExists)
    }

    @Test
    fun testGetUserCount() = runBlocking {
        var count = userDao.getUserCount()
        assertEquals(0, count)

        userDao.insertUser(User(username = "user1", password = "pass"))
        userDao.insertUser(User(username = "user2", password = "pass"))

        count = userDao.getUserCount()
        assertEquals(2, count)
    }

    //CATEGORY DAO TESTS

    @Test
    fun testInsertAndGetCategory() = runBlocking {
        val userId = userDao.insertUser(User(username = "catuser", password = "pass"))

        val category = Category(
            userId = userId,
            name = "Groceries",
            description = "Food and household items",
            isDefault = false
        )

        val categoryId = categoryDao.insertCategory(category)
        assertTrue(categoryId > 0)

        val retrievedCategory = categoryDao.getCategoryById(categoryId)
        assertNotNull(retrievedCategory)
        assertEquals("Groceries", retrievedCategory?.name)
    }

    @Test
    fun testGetCategoriesByUser() = runBlocking {
        val userId = userDao.insertUser(User(username = "multicatuser", password = "pass"))

        categoryDao.insertCategory(Category(userId = userId, name = "Food"))
        categoryDao.insertCategory(Category(userId = userId, name = "Transport"))
        categoryDao.insertCategory(Category(userId = userId, name = "Entertainment"))

        val categories = categoryDao.getCategoriesByUser(userId).first()

        assertEquals(3, categories.size)
        assertTrue(categories.any { it.name == "Food" })
        assertTrue(categories.any { it.name == "Transport" })
        assertTrue(categories.any { it.name == "Entertainment" })
    }

    @Test
    fun testUpdateCategory() = runBlocking {
        val userId = userDao.insertUser(User(username = "updatecatuser", password = "pass"))
        val category = Category(userId = userId, name = "Old Name")
        val categoryId = categoryDao.insertCategory(category)

        val updatedCategory = category.copy(name = "New Name", description = "Updated desc")
        categoryDao.updateCategory(updatedCategory)

        val retrieved = categoryDao.getCategoryById(categoryId)
        assertEquals("New Name", retrieved?.name)
        assertEquals("Updated desc", retrieved?.description)
    }

    @Test
    fun testDeleteCategory() = runBlocking {
        val userId = userDao.insertUser(User(username = "delcatuser", password = "pass"))
        val category = Category(userId = userId, name = "To Delete")
        val categoryId = categoryDao.insertCategory(category)

        categoryDao.deleteCategory(category)

        val deleted = categoryDao.getCategoryById(categoryId)
        assertNull(deleted)
    }

    @Test
    fun testSearchCategories() = runBlocking {
        val userId = userDao.insertUser(User(username = "searchuser", password = "pass"))

        categoryDao.insertCategory(Category(userId = userId, name = "Fast Food"))
        categoryDao.insertCategory(Category(userId = userId, name = "Fine Dining"))
        categoryDao.insertCategory(Category(userId = userId, name = "Transport"))

        val results = categoryDao.searchCategories(userId, "Food").first()

        assertEquals(2, results.size)
        assertTrue(results.all { it.name.contains("Food") })
    }

    //EXPENSES DAO TESTS

    @Test
    fun testInsertAndGetExpense() = runBlocking {
        val userId = userDao.insertUser(User(username = "expuser", password = "pass"))
        val categoryId = categoryDao.insertCategory(Category(userId = userId, name = "Food"))

        val expense = Expenses(
            userId = userId,
            categoryId = categoryId,
            date = Date(),
            startTime = Date(),
            endTime = Date(),
            description = "Lunch",
            amount = 15.50,
            photoUri = "/test/photo.jpg"
        )

        val expenseId = expensesDao.insertExpense(expense)
        assertTrue(expenseId > 0)

        val retrievedExpense = expensesDao.getExpenseById(expenseId)
        assertNotNull(retrievedExpense)
        assertEquals("Lunch", retrievedExpense?.description)
        assertEquals(15.50, retrievedExpense?.amount)
    }

    @Test
    fun testGetExpensesByDateRange() = runBlocking {
        val userId = userDao.insertUser(User(username = "dateuser", password = "pass"))
        val categoryId = categoryDao.insertCategory(Category(userId = userId, name = "Bills"))

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        val oldExpense = Expenses(
            userId = userId, categoryId = categoryId,
            date = calendar.time, startTime = calendar.time, endTime = calendar.time,
            description = "Old bill", amount = 100.0
        )
        expensesDao.insertExpense(oldExpense)

        calendar.add(Calendar.MONTH, 1)
        val newExpense = Expenses(
            userId = userId, categoryId = categoryId,
            date = calendar.time, startTime = calendar.time, endTime = calendar.time,
            description = "New bill", amount = 200.0
        )
        expensesDao.insertExpense(newExpense)

        val startDate = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, 1) }.time
        val endDate = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, 31) }.time

        val expenses = expensesDao.getExpensesByDateRange(userId, startDate, endDate).first()

        assertEquals(1, expenses.size)
        assertEquals("New bill", expenses[0].description)
    }

    @Test
    fun testGetTotalSpentInDateRange() = runBlocking {
        val userId = userDao.insertUser(User(username = "totaluser", password = "pass"))
        val categoryId = categoryDao.insertCategory(Category(userId = userId, name = "Shopping"))

        val startDate = Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000)
        val endDate = Date()

        expensesDao.insertExpense(Expenses(
            userId = userId, categoryId = categoryId,
            date = Date(), startTime = Date(), endTime = Date(),
            description = "Item 1", amount = 100.0
        ))
        expensesDao.insertExpense(Expenses(
            userId = userId, categoryId = categoryId,
            date = Date(), startTime = Date(), endTime = Date(),
            description = "Item 2", amount = 50.0
        ))
        expensesDao.insertExpense(Expenses(
            userId = userId, categoryId = categoryId,
            date = Date(), startTime = Date(), endTime = Date(),
            description = "Item 3", amount = 25.0
        ))

        val total = expensesDao.getTotalSpentInDateRange(userId, startDate, endDate)
        assertEquals(175.0, total ?: 0.0)
    }

    @Test
    fun testGetTotalSpentByCategory() = runBlocking {
        val userId = userDao.insertUser(User(username = "bycategoryuser", password = "pass"))

        val foodId = categoryDao.insertCategory(Category(userId = userId, name = "Food"))
        val transportId = categoryDao.insertCategory(Category(userId = userId, name = "Transport"))

        val startDate = Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000)
        val endDate = Date()

        expensesDao.insertExpense(Expenses(
            userId = userId, categoryId = foodId,
            date = Date(), startTime = Date(), endTime = Date(),
            description = "Groceries", amount = 150.0
        ))
        expensesDao.insertExpense(Expenses(
            userId = userId, categoryId = foodId,
            date = Date(), startTime = Date(), endTime = Date(),
            description = "Restaurant", amount = 75.0
        ))
        expensesDao.insertExpense(Expenses(
            userId = userId, categoryId = transportId,
            date = Date(), startTime = Date(), endTime = Date(),
            description = "Bus pass", amount = 50.0
        ))

        val spending = expensesDao.getTotalSpentByCategory(userId, startDate, endDate).first()

        assertEquals(2, spending.size)

        val foodSpending = spending.find { it.categoryName == "Food" }
        assertNotNull(foodSpending)
        assertEquals(225.0, foodSpending?.totalSpent)
        assertEquals(2, foodSpending?.expenseCount)

        val transportSpending = spending.find { it.categoryName == "Transport" }
        assertNotNull(transportSpending)
        assertEquals(50.0, transportSpending?.totalSpent)
        assertEquals(1, transportSpending?.expenseCount)
    }

    @Test
    fun testPaginatedExpenses() = runBlocking {
        val userId = userDao.insertUser(User(username = "paginateuser", password = "pass"))
        val categoryId = categoryDao.insertCategory(Category(userId = userId, name = "Misc"))

        for (i in 1..10) {
            expensesDao.insertExpense(Expenses(
                userId = userId, categoryId = categoryId,
                date = Date(), startTime = Date(), endTime = Date(),
                description = "Expense $i", amount = i * 10.0
            ))
        }

        val page1 = expensesDao.getExpensesPaginated(userId, 5, 0)
        assertEquals(5, page1.size)

        val page2 = expensesDao.getExpensesPaginated(userId, 5, 5)
        assertEquals(5, page2.size)

        val totalCount = expensesDao.getTotalExpenseCount(userId)
        assertEquals(10, totalCount)
    }

    @Test
    fun testUpdateExpense() = runBlocking {
        val userId = userDao.insertUser(User(username = "updateexpuser", password = "pass"))
        val categoryId = categoryDao.insertCategory(Category(userId = userId, name = "Misc"))

        val expense = Expenses(
            userId = userId, categoryId = categoryId,
            date = Date(), startTime = Date(), endTime = Date(),
            description = "Original", amount = 100.0
        )
        val expenseId = expensesDao.insertExpense(expense)

        val updatedExpense = expense.copy(description = "Updated", amount = 150.0)
        expensesDao.updateExpense(updatedExpense)

        val retrieved = expensesDao.getExpenseById(expenseId)
        assertEquals("Updated", retrieved?.description)
        assertEquals(150.0, retrieved?.amount)
    }

    @Test
    fun testDeleteExpense() = runBlocking {
        val userId = userDao.insertUser(User(username = "delexpuser", password = "pass"))
        val categoryId = categoryDao.insertCategory(Category(userId = userId, name = "Misc"))

        val expense = Expenses(
            userId = userId, categoryId = categoryId,
            date = Date(), startTime = Date(), endTime = Date(),
            description = "To Delete", amount = 50.0
        )
        val expenseId = expensesDao.insertExpense(expense)

        expensesDao.deleteExpense(expense)

        val deleted = expensesDao.getExpenseById(expenseId)
        assertNull(deleted)
    }

    //BUDGET GOAL DAO TESTS

    @Test
    fun testInsertAndGetBudgetGoal() = runBlocking {
        val userId = userDao.insertUser(User(username = "budgetuser", password = "pass"))

        val goal = BudgetGoal(
            userId = userId,
            year = 2024,
            month = 11,
            minGoal = 500.0,
            maxGoal = 1000.0
        )

        val goalId = budgetGoalDao.insertBudgetGoal(goal)
        assertTrue(goalId > 0)

        val retrievedGoal = budgetGoalDao.getBudgetGoalById(goalId)
        assertNotNull(retrievedGoal)
        assertEquals(500.0, retrievedGoal?.minGoal)
        assertEquals(1000.0, retrievedGoal?.maxGoal)
    }

    @Test
    fun testGetOverallBudgetGoalForMonth() = runBlocking {
        val userId = userDao.insertUser(User(username = "monthuser", password = "pass"))

        val goal = BudgetGoal(
            userId = userId, year = 2024, month = 11,
            minGoal = 600.0, maxGoal = 1200.0
        )
        budgetGoalDao.insertBudgetGoal(goal)

        val retrievedGoal = budgetGoalDao.getOverallBudgetGoalForMonth(userId, 2024, 11)
        assertNotNull(retrievedGoal)
        assertEquals(600.0, retrievedGoal?.minGoal)
        assertEquals(1200.0, retrievedGoal?.maxGoal)

        val nullGoal = budgetGoalDao.getOverallBudgetGoalForMonth(userId, 2024, 12)
        assertNull(nullGoal)
    }

    @Test
    fun testUpdateBudgetGoal() = runBlocking {
        val userId = userDao.insertUser(User(username = "updategoaluser", password = "pass"))

        val goal = BudgetGoal(
            userId = userId, year = 2024, month = 11,
            minGoal = 500.0, maxGoal = 1000.0
        )
        val goalId = budgetGoalDao.insertBudgetGoal(goal)

        val updatedGoal = goal.copy(minGoal = 700.0, maxGoal = 1500.0)
        budgetGoalDao.updateBudgetGoal(updatedGoal)

        val retrieved = budgetGoalDao.getBudgetGoalById(goalId)
        assertEquals(700.0, retrieved?.minGoal)
        assertEquals(1500.0, retrieved?.maxGoal)
    }

    @Test
    fun testDeleteBudgetGoal() = runBlocking {
        val userId = userDao.insertUser(User(username = "delgoaluser", password = "pass"))

        val goal = BudgetGoal(
            userId = userId, year = 2024, month = 11,
            minGoal = 500.0, maxGoal = 1000.0
        )
        val goalId = budgetGoalDao.insertBudgetGoal(goal)

        budgetGoalDao.deleteBudgetGoal(goal)

        val deleted = budgetGoalDao.getBudgetGoalById(goalId)
        assertNull(deleted)
    }

    @Test
    fun testGetAllBudgetGoals() = runBlocking {
        val userId = userDao.insertUser(User(username = "allgoalsuser", password = "pass"))

        budgetGoalDao.insertBudgetGoal(BudgetGoal(userId = userId, year = 2024, month = 1, minGoal = 100.0, maxGoal = 200.0))
        budgetGoalDao.insertBudgetGoal(BudgetGoal(userId = userId, year = 2024, month = 2, minGoal = 150.0, maxGoal = 250.0))
        budgetGoalDao.insertBudgetGoal(BudgetGoal(userId = userId, year = 2024, month = 3, minGoal = 200.0, maxGoal = 300.0))

        val goals = budgetGoalDao.getAllBudgetGoals(userId).first()
        assertEquals(3, goals.size)
    }

    @Test
    fun testCategorySpecificBudgetGoal() = runBlocking {
        val userId = userDao.insertUser(User(username = "catgoaluser", password = "pass"))
        val categoryId = categoryDao.insertCategory(Category(userId = userId, name = "Food"))

        val goal = BudgetGoal(
            userId = userId, year = 2024, month = 11,
            minGoal = 200.0, maxGoal = 400.0,
            categoryId = categoryId
        )
        budgetGoalDao.insertBudgetGoal(goal)

        val retrievedGoal = budgetGoalDao.getCategoryBudgetGoalForMonth(userId, 2024, 11, categoryId)

        assertNotNull(retrievedGoal)
        assertEquals(categoryId, retrievedGoal?.categoryId)
        assertEquals(200.0, retrievedGoal?.minGoal)
        assertEquals(400.0, retrievedGoal?.maxGoal)
    }
}