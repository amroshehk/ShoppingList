package com.applefish.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.applefish.shoppinglist.data.ShoppingDao
import com.applefish.shoppinglist.data.ShoppingDataBase
import com.applefish.shoppinglist.data.db.entities.ShoppingItem
import com.google.common.truth.Truth.assertThat
//import com.google.common.truth.Truth.assertThat
//import com.perfecta.unittesting.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var shoppingItemDatabase: ShoppingDataBase
    private lateinit var shoppingDao: ShoppingDao

    @Before
    fun setup() {
        shoppingItemDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingDataBase::class.java
        ).allowMainThreadQueries().build()
        shoppingDao = shoppingItemDatabase.getShoppingDao()
    }

    @After
    fun teardown() {
        shoppingItemDatabase.close()
    }

    @Test
    fun testInsertShoppingItem() = runTest {
        val shoppingItem = ShoppingItem("item", 1)
        shoppingDao.upsert(shoppingItem)
//
        val shoppingItems = shoppingDao.getAllShoppingItems().getOrAwaitValue()
        assertThat(shoppingItems).contains(shoppingItem)
    }
}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {

        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}