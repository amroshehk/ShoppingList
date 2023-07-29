package com.applefish.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.applefish.shoppinglist.data.ShoppingDataBase
import com.applefish.shoppinglist.data.repositories.ShoppingRepository
import com.applefish.shoppinglist.ui.shoppingList.ShoppingViewModel
import com.applefish.shoppinglist.ui.shoppingList.ShoppingViewModelFactory

class ShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val dataBase = ShoppingDataBase(this)
        val repository = ShoppingRepository(dataBase)
        val factory = ShoppingViewModelFactory(repository)

        val viewModel = ViewModelProvider(this,factory)[ShoppingViewModel::class.java]
    }
}