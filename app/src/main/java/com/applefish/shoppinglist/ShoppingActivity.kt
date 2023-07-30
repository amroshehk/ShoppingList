package com.applefish.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
class ShoppingActivity : AppCompatActivity(),KodeinAware {
    private lateinit var binding: ActivityShoppingBinding
    override val kodein by kodein()
    private val factory: ShoppingViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val viewModel = ViewModelProvider(this,factory)[ShoppingViewModel::class.java]

        val adapter = ShoppingItemAdapter(listOf(), viewModel)


        binding.rvShoppingItems.layoutManager = LinearLayoutManager(this)
        binding.rvShoppingItems.adapter = adapter

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