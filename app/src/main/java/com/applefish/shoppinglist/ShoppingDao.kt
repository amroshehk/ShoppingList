package com.applefish.shoppinglist

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(shoppingItem: ShoppingItem)

    @Delete
    suspend fun delete()

    @Query("select * from shopping_items")
    fun getAllShoppingItems() : LiveData<List<ShoppingItem>>
}