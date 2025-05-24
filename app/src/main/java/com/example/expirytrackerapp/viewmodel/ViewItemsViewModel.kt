package com.example.expirytrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.expirytrackerapp.data.AppDatabase
import com.example.expirytrackerapp.data.Item
import com.example.expirytrackerapp.data.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*

class ViewItemsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ItemRepository
    private val _searchQuery = MutableStateFlow("")
    private val _items = MutableStateFlow<List<Item>>(emptyList())

    val items: StateFlow<List<Item>> = _items.asStateFlow()
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ItemRepository(database.itemDao())
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            repository.allItems.collect { items ->
                _items.value = items
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isBlank()) {
                repository.allItems.collect { items ->
                    _items.value = items
                }
            } else {
                repository.searchItems(query).collect { items ->
                    _items.value = items
                }
            }
        }
    }

    fun deleteItemById(itemId: Int) {
        viewModelScope.launch {
            repository.deleteItemById(itemId)
            // Optionally refresh items after deletion
            if (_searchQuery.value.isBlank()) {
                repository.allItems.collect { items ->
                    _items.value = items
                }
            } else {
                repository.searchItems(_searchQuery.value).collect { items ->
                    _items.value = items
                }
            }
        }
    }

    fun addItem(item: com.example.expirytrackerapp.data.Item) {
        viewModelScope.launch {
            repository.addItem(item)
            // Optionally refresh items after addition
            if (_searchQuery.value.isBlank()) {
                repository.allItems.collect { items ->
                    _items.value = items
                }
            } else {
                repository.searchItems(_searchQuery.value).collect { items ->
                    _items.value = items
                }
            }
        }
    }
} 