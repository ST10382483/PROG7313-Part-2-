package com.darsh.roomdb_st10442633

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class DatabaseTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: BudgetRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        // You'll need to create a test version or use the real one
        repository = BudgetRepository(context)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testUserCreation() = runBlocking {
        val userId = repository.registerUser("testuser", "password123")
        assertTrue(userId > 0)
    }