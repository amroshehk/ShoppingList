package com.applefish.shoppinglist.ui.shoppingList

import com.applefish.shoppinglist.data.db.entities.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClicked(item: ShoppingItem)
}