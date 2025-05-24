package com.example.expirytrackerapp.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class ItemRepository(private val itemDao: ItemDao) {
    val allItems: Flow<List<Item>> = itemDao.getAllItems()

    suspend fun insert(item: Item) {
        itemDao.insert(item)
    }

    fun searchItems(query: String): Flow<List<Item>> {
        return itemDao.searchItems(query)
    }

    suspend fun deleteItemById(itemId: Int) {
        itemDao.deleteItemById(itemId)
    }

    suspend fun addItem(item: Item) {
        itemDao.insert(item)
    }
} 