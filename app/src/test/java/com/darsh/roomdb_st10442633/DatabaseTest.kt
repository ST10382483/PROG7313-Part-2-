package com.darsh.roomdb_st10442633

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: BudgetRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        //Use in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = BudgetRepository(context)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testUserRegistrationAndLogin() = runBlocking {
        //Register a user
        val userId = repository.registerUser("testuser", "password123", "test@email.com")
        assertTrue("User ID should be greater than 0", userId > 0)

        //Login with the user
        val user = repository.login("testuser", "password123")
        assertNotNull("User should not be null", user)
        assertEquals("Username should match", "testuser", user?.username)
    }

    @Test
    fun testCategoryCreation() = runBlocking {
        //Register user first
        val userId = repository.registerUser("categoryuser", "pass123")

        //Add a category
        val categoryId = repository.addCategory(userId, "Food")
        assertTrue("Category ID should be greater than 0", categoryId > 0)

        //Get categories
        var categories: List<Category>? = null
        repository.getCategoriesByUser(userId).collect {
            categories = it
        }

        assertNotNull("Categories list should not be null", categories)
        assertTrue("Categories list should not be empty", categories?.isNotEmpty() == true)
        assertEquals("Category name should match", "Food", categories?.first()?.name)
    }

    @Test
    fun testExpenseCreation() = runBlocking {
        //Register user
        val userId = repository.registerUser("expenseuser", "pass123")

        //Add category
        val categoryId = repository.addCategory(userId, "Transport")

        //Add expense
        val expenseId = repository.addExpense(
            userId = userId,
            categoryId = categoryId,
            date = Date(),
            startTime = Date(),
            endTime = Date(),
            description = "Bus fare",
            amount = 50.0,
            photoUri = "/test/photo.jpg"
        )

        assertTrue("Expense ID should be greater than 0", expenseId > 0)
    }

    @Test
    fun testBudgetGoalSetting() = runBlocking {
        // Register user
        val userId = repository.registerUser("budgetuser", "pass123")
        val year = 2024
        val month = 11

        // et budget goal
        repository.setMonthlyBudgetGoal(userId, year, month, 100.0, 500.0)

        //Get budget goal
        val goal = repository.getMonthlyBudgetGoal(userId, year, month)

        assertNotNull("Budget goal should not be null", goal)
        assertEquals("Min goal should match", 100.0, goal?.minGoal)
        assertEquals("Max goal should match", 500.0, goal?.maxGoal)
    }

    @Test
    fun testSimpleAddition() {
        assertEquals(4, 2 + 2)
    }
}