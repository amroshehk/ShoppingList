package com.applefish.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.applefish.shoppinglist.data.db.entities.ShoppingItem

interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(shoppingItem: ShoppingItem)

    @Delete
    suspend fun delete(shoppingItem: ShoppingItem)

    @Query("select * from shopping_items")
    fun getAllShoppingItems() : LiveData<List<ShoppingItem>>
}