package com.applefish.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.applefish.shoppinglist.adapter.ShoppingItemAdapter
import com.applefish.shoppinglist.data.ShoppingDataBase
import com.applefish.shoppinglist.data.db.entities.ShoppingItem
import com.applefish.shoppinglist.data.repositories.ShoppingRepository
import com.applefish.shoppinglist.databinding.ActivityShoppingBinding
import com.applefish.shoppinglist.databinding.DialogAddShoppingItemBinding
import com.applefish.shoppinglist.ui.shoppingList.AddDialogListener
import com.applefish.shoppinglist.ui.shoppingList.AddShoppingItemDialog
import com.applefish.shoppinglist.ui.shoppingList.ShoppingViewModel
import com.applefish.shoppinglist.ui.shoppingList.ShoppingViewModelFactory

class ShoppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val dataBase = ShoppingDataBase(this)
        val repository = ShoppingRepository(dataBase)
        val factory = ShoppingViewModelFactory(repository)

        val viewModel = ViewModelProvider(this,factory)[ShoppingViewModel::class.java]

        val adapter = ShoppingItemAdapter(listOf(), viewModel)

        viewModel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        binding.fab.setOnClickListener {
            AddShoppingItemDialog(
                this,
                object : AddDialogListener {
                    override fun onAddButtonClicked(item: ShoppingItem) {
                        viewModel.upsert(item)
                    }
                }).show()
        }
    }
}